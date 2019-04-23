package engine.io;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;

import main.Main_menu;

/**
 * @author Team 62
 * 
 * Kieran Baker - sgkbaker - 201234727
 */
public class Audio{
	
	//Audio file references
	
	public static final String SND_TURRET_PLACE = "effects/turret_place.wav";
	public static final String SND_TURRET_UPGRADE = "effects/turret_upgrade.wav";
	public static final String SND_TURRET_SHOOT = "effects/turret_shoot.wav";
	public static final String SND_ENEMY_STEP = "effects/enemy_step.wav";
	public static final String SND_ENEMY_DEATH = "effects/enemy_death.wav";
	public static final String SND_DAMAGE_TAKEN = "effects/damage_taken.wav";
	public static final String SND_ROUND_COMPLETE = "effects/round_complete.wav";
	public static final String SND_VICTORY = "effects/victory.wav";
	public static final String SND_DEFEAT = "effects/defeat.wav";
	public static final String SND_MENU_CLICK = "effects/menu_click.wav";
	
	public static final String MSC_MENU = "music/menu1.wav";
	public static final String MSC_GAME = "music/game.wav";
	
	private static final String PATH = "assets/sounds/";
	
	private static Sound looped = null;
	
	public static long device;
	
	public static long id;
	
	private static boolean muted = false;

	public static void play(String filename) {
			play(filename, 0f,0f);
	}
	
	//Play audio file in a given position
	public static void play(String filename, float x, float y) {
		float volume;
		
		//Determine if sound is effect or music
		if (muted) {
			volume = 0;
		} else {
			switch (filename.substring(0, 2)) {
				case "m":
					volume = Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth();
					break;
				default:
					volume = Main_menu.volume_sfx.getSliderWidth() / Main_menu.volume_sfx.getMaxWidth();
					break;
			}
		}
		
		//Fit coordinates into range of -1 to 1
		x = Math.min(Math.max(-1, x), 1);
		y = Math.min(Math.max(-1, y), 1);
		
		Sound snd = new Sound(filename, x,y, volume);
		snd.start();
	}
	
	public static void toggleMute() {
		muted = !muted;
		updateVolume();
	}
	public static boolean isMuted() {
		return muted;
	}
	
	/**
	 * Overrides game volume settings
	 * 
	 */
	public static void play(String filename, float x, float y, float volume) {
		Sound snd = new Sound(filename, x, y, 1f);
		snd.start();
	}
	
	/**
	 * Play an audio file on loop, only works if no loop is currently running
	 * Call stop to end the current audio loop
	 * 
	 */
	public static void playLoop(String filename) {
		if (looped == null) {
			if (muted) {
				looped = new Sound(filename,0f,0f,0);
			} else {
				looped = new Sound(filename,0f,0f,Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth());
			}
			
			looped.loop = true;
			looped.start();
		}
	}
		
	/**
	 * Stops current audio loop
	 * @param restart : Determines if the audio should restart afterwards
	 */
	public static void stop(boolean restart) {
		if (looped != null) {
			String name = looped.name;
			looped.stopSound();
			looped = null;
			if (restart) {
				playLoop(name);
			}
		}

	}
	
	public static void updateVolume() {
		if (looped != null) {
			if (muted) {
				AL10.alSourcef(looped.s, AL10.AL_GAIN, 0);
			} else {
				AL10.alSourcef(looped.s, AL10.AL_GAIN, Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth());
			}
			
		}


	}
	
	//Setup the device for playing audio
	public static void setup() throws Exception {
		//Create a new audio device
		long device = ALC10.alcOpenDevice((CharSequence)null);
		id = device;
		
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		
		IntBuffer contextAttribList = BufferUtils.createIntBuffer(16);
		
		contextAttribList.put(4104);
		contextAttribList.put(60);
		
		contextAttribList.put(4105);
		contextAttribList.put(0);
		
		contextAttribList.put(131075);
		contextAttribList.put(2);
		
		contextAttribList.put(0);
		contextAttribList.flip();
		
		long newContext = ALC10.alcCreateContext(device, contextAttribList);
		
		if (!ALC10.alcMakeContextCurrent(newContext)) {
			throw new Exception("Failed to make context current");
		}
		
		AL.createCapabilities(deviceCaps);
	}
	
	//Close the audio device
	public static void destroy() {
		stop(false);
		ALC10.alcCloseDevice(id);
		ALC.destroy();


	}

//Creates a sound object on a new thread
static class Sound extends Thread {
	private String name;
	private float x;
	private float y;
	private float volume;
	private boolean terminate = false;
	private boolean loop = false;
	private int s;
	
	public Sound(String name, float x, float y, float volume) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.volume = volume;
	}
	
	//Runs the thread
	public void run() {
		try {
			play();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void stopSound() {
		terminate = true;
	}
	
	//Reads and plays the audio file
	public void play() throws Exception{
		
		//Creates a buffer to store the sound data
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		AL10.alGenBuffers(buffer);
		
		//Gets the length of the audio file
		long time = createBufferData(buffer.get(0));
		
		//Generate a source to play the audio through
		int source = AL10.alGenSources();	
		
		//Setup the source
		AL10.alSourcei(source, AL10.AL_BUFFER, buffer.get(0));	
		
		//Audio looping - Not implemented yet
		AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_TRUE);
		
		//Pitch
		AL10.alSourcef(source, AL10.AL_PITCH,1f);
		
		//Position of audio source in 3d space
		AL10.alSource3f(source, AL10.AL_POSITION, x, y, 0f);
		
		//Gain/Volume
		AL10.alSourcef(source, AL10.AL_GAIN, volume);	
		
		s = source;
		
		//Play the audio
		AL10.alSourcePlay(source);
		
		//Make thread sleep until the audio is complete
		try {
			if (loop) {
				while (!terminate) {
					Thread.sleep(10);
					Thread.yield();
				}
			} else {
				Thread.sleep(time);
			}

		} catch(InterruptedException ex) {
			
			AL10.alSourceStop(source);
			AL10.alDeleteSources(source);
			throw new InterruptedException("Thread interrupted");
	    }
			
		AL10.alSourceStop(source);
		
		AL10.alDeleteSources(source);
		
	}
	
	//Loads the audio file into buffers
	private long createBufferData(int p) throws UnsupportedAudioFileException {
		final int MONO = 1, STEREO = 2;
		
		File audio = new File(PATH + name);
		
		AudioInputStream stream = null;
		try {
			stream = AudioSystem.getAudioInputStream(audio);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AudioFormat format = stream.getFormat();
		if (format.isBigEndian()) throw new UnsupportedAudioFileException("Big Endian files are not supported");
		
		int openALFormat = -1;
		switch(format.getChannels()) {
		case MONO:
			switch(format.getSampleSizeInBits()) {
			case 8:
				openALFormat = AL10.AL_FORMAT_MONO8;
				break;
			case 16:
				openALFormat = AL10.AL_FORMAT_MONO16;
				break;
			}
			break;
		case STEREO:
			switch(format.getSampleSizeInBits()) {
			case 8:
				openALFormat = AL10.AL_FORMAT_STEREO8;
				break;
			case 16:
				openALFormat = AL10.AL_FORMAT_STEREO16;
				break;
			}
			break;
		}
		byte[] b = null;
		try {
			b = stream.readAllBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
		data.flip();
		
		AL10.alBufferData(p, openALFormat, data, (int)format.getSampleRate());
		
		return (long)(1000f * stream.getFrameLength() / format.getFrameRate());
		
	}
}

}
