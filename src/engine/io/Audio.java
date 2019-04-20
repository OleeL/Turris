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
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import main.Main_menu;

/* TODO
 * Finish creating audio files
 * Make audio not sound like the beat of a song??
 * Speed up audio when game speed is sped up??? - Don't know if possible or even needed?
 * Add limit to audio sources - Too many turrets shooting on high game speeds
 * Audio looping?
 */

/**
 * @author Kieran
 *
 */
/**
 * @author Kieran
 *
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

	/**Plays a given audio file
	 * @param filename : Name of the audio file to play. Use the class-defined constants
	 * @param x(optional) : x coordinate of the audio in 2d space (-1 to 1)
	 * @param y(optional) : y coordinate of the audio in 2d space (-1 to 1)
	 * @param volume(optional) : Volume to play the audio at (0 to 1)
	 */
	public  static void play(String filename) {
			float volume;
			switch (filename.substring(0, 2)) {
			case "m":
				volume = Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth();
				break;
			default:
				volume = Main_menu.volume_sfx.getSliderWidth() / Main_menu.volume_sfx.getMaxWidth();
			}
			Sound snd = new Sound(filename, 0f,0f, volume);
			snd.start();

	}
	
	//Play an audio file with a given volume
	public static void play(String filename, float volume) {
		Sound snd = new Sound(filename, 0f,0f, 1f);
		snd.start();
	}
	
	//Play audio file in a given position
	public static void play(String filename, float x, float y) {
		Sound snd = new Sound(filename,x, y, 1f);
		snd.start();
	}
	
	//Play audio file in a given position and volume
	public static void play(String filename, float x, float y, float volume) {
		Sound snd = new Sound(filename, x, y, volume);
		snd.start();
	}
	
	public static void playLoop(String filename) {
		if (looped == null) {
			looped = new Sound(filename,0f,0f,Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth());
			looped.loop = true;
			looped.start();
			//concurrentSounds += 1;
		}

	}
	
	
	/**
	 * Stops current audio loop
	 * @param restart : Determines if the audio should restart afterwards
	 */
	public static void stop(boolean restart) {
		if (looped != null) {
			String name = looped.name;
			looped.terminate = true;
			looped = null;
			if (restart) {
				playLoop(name);
			}
		}

	}

//Creates a sound object on a new thread
static class Sound extends Thread {
	private String name;
	private float x;
	private float y;
	private float volume;
	boolean terminate = false;
	boolean loop = false;
	
	public Sound(String name, float x, float y, float volume) {
		this.name = name;
		this.x = Math.min(Math.max(-1, x), 1);
		this.y = Math.min(Math.max(-1, y), 1);;
		this.volume = Math.min(Math.max(0, volume), 1);
	}
	
	//Runs the thread
	public void run() {
		try {
			play();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Reads and plays the audio file
	public void play() throws Exception{
		//Create a new audio device
		long device = ALC10.alcOpenDevice((CharSequence)null);
		
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
		
		//Play the audio
		AL10.alSourcePlay(source);
		
		//Make thread sleep until the audio is complete
		try {
			if (loop) {
				while (!terminate) {
					//Thread.sleep(time);
					Thread.sleep(10);
					Thread.yield();
				}
			} else {
				Thread.sleep(time);
			}

		} catch(InterruptedException ex) {
			throw new InterruptedException("Thread interrupted");
	    }
		
		//Stop the audio
		//Delete the source
		//Close the device
		
		AL10.alSourceStop(source);
		
		AL10.alDeleteSources(source);
		
		ALC10.alcCloseDevice(device);	
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
