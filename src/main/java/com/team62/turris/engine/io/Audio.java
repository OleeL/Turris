package com.team62.turris.engine.io;

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

import com.team62.turris.Main_menu;

/**
 * @author Team 62
 * 
 * Kieran Baker - sgkbaker - 201234727
 */
public class Audio{
	
	//Audio file references
	
	//Sound effects
	public static final String SND_TURRET_PLACE = "effects/turret_place.wav";
	public static final String SND_TURRET_UPGRADE = "effects/turret_upgrade.wav";
	public static final String SND_TURRET_SHOOT = "effects/turret_shoot.wav";
	public static final String SND_ENEMY_DEATH = "effects/enemy_death.wav";
	public static final String SND_ENEMY_DEATH_1 = "effects/splat1.wav";
	public static final String SND_ENEMY_DEATH_2 = "effects/splat2.wav";
	public static final String SND_ENEMY_HIT_1 = "effects/hit1.wav";
	public static final String SND_ENEMY_HIT_2 = "effects/hit2.wav";
	public static final String SND_DAMAGE_TAKEN = "effects/damage_taken.wav";
	public static final String SND_ROUND_COMPLETE = "effects/round_complete.wav";
	public static final String SND_VICTORY = "effects/victory.wav";
	public static final String SND_DEFEAT = "effects/defeat.wav";
	public static final String SND_MENU_CLICK = "effects/menu_click.wav";
	
	//Background music
	public static final String MSC_MENU = "music/menu.wav";
	public static final String MSC_GAME = "music/game.wav";
	
	//Path to all the sound files
	public static final String PATH = "assets/sounds/";
	
	//Stores the current background music Sound object
	private static Sound background_music = null;
	
	//Stores the device id for playing audio
	private static long device;
	
	//Used to determine if audio should be muted or not
	private static boolean muted = false;

	
	//Play audio file in a given position
	public static void play(String filename) {
		float volume;
		
		//Determine if sound is effect or music
		if (muted) {
			volume = 0;
		} else {
			//Takes the first letter of the audio file name
			//'m' Means it will be music, any other name will represent sound effect
			switch (filename.substring(0, 2)) {
				case "m":
					volume = Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth();
					break;
				default:
					volume = Main_menu.volume_sfx.getSliderWidth() / Main_menu.volume_sfx.getMaxWidth();
					break;
			}
		}
		
		Sound snd = new Sound(filename,volume);
		snd.start();
	}
	
	/**
	 * Overrides game volume settings
	 * 
	 */
	public static void play(String filename, float volume) {
		Sound snd = new Sound(filename, 1f);
		snd.start();
	}
	
	/**
	 * Play an audio file on loop, only works if no loop is currently running
	 * Call stop to end the current audio loop
	 * 
	 */
	public static void playLoop(String filename) {
		if (background_music == null) {
			if (muted) {
				background_music = new Sound(filename,0);
			} else {
				background_music = new Sound(filename,Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth());
			}
			
			background_music.setLooping(true);
			background_music.start();
		}
	}
		
	/**
	 * Stops current audio loop
	 * @param restart : Determines if the audio should restart afterwards
	 */
	public static void stop(boolean restart) {
		if (background_music != null) {
			String name = background_music.getFileName();
			background_music.stopSound();
			background_music = null;
			if (restart) {
				playLoop(name);
			}
		}

	}
	
	//Updates the volume of the background music
	public static void updateVolume() {
		if (background_music != null) {
			if (muted) {
				AL10.alSourcef(background_music.getSource(), AL10.AL_GAIN, 0);
			} else {
				AL10.alSourcef(background_music.getSource(), AL10.AL_GAIN, Main_menu.volume_music.getSliderWidth() / Main_menu.volume_music.getMaxWidth());
			}
			
		}
	}
	
	public static void toggleMute() {
		muted = !muted;
		updateVolume();
	}
	
	public static void setMute(boolean m) {
		muted = m;
		updateVolume();
	}
	
	public static boolean isMuted() {
		return muted;
	}
	
	//Setup the device for playing audio
	public static void setup() throws Exception {
		//Create a new audio device
		device = ALC10.alcOpenDevice((CharSequence)null);
		
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
			throw new Exception("Failed to make audio context current");
		}
		
		AL.createCapabilities(deviceCaps);
	}
	
	//Close the audio device and cleanup
	public static void destroy() {
		stop(false);
		ALC10.alcCloseDevice(device);
		ALC.destroy();
	}
	
}

//Creates a sound object on a new thread
class Sound extends Thread {
	private String name;
	private float volume;
	private boolean terminate = false;
	private boolean loop = false;
	private int src;
	
	public Sound(String name, float volume) {
		this.name = name;
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
		
		//Audio looping
		AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_TRUE);
		
		//Gain/Volume
		AL10.alSourcef(source, AL10.AL_GAIN, volume);	
		
		src = source;
		
		//Play the audio
		AL10.alSourcePlay(source);
		
		//Make thread sleep until the audio is complete
		try {
			if (loop) {
				while (!terminate) {
					//Keep looping until told to terminate
					Thread.sleep(10);
					Thread.yield();
				}
			} else {
				//Make thread sleep until audio complete
				Thread.sleep(time);
			}

		} catch(InterruptedException ex) {
			
			AL10.alSourceStop(source);
			AL10.alDeleteSources(source);
			throw new InterruptedException("Thread interrupted");
	    }
			
		//Stop audio and delete source
		AL10.alSourceStop(source);		
		AL10.alDeleteSources(source);
		
	}
	
	//Loads the audio file into buffers
	//Returns length of audio
	private long createBufferData(int p) throws UnsupportedAudioFileException {
		final int MONO = 1, STEREO = 2;
		
		//Load the audio file
		File audio = new File(Audio.PATH + name);
		
		//Create a stream out of the audio file
		AudioInputStream stream = null;
		try {
			stream = AudioSystem.getAudioInputStream(audio);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AudioFormat format = stream.getFormat();
		if (format.isBigEndian()) throw new UnsupportedAudioFileException("Big Endian files are not supported");
		
		//Determine the format of the audio
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
		
		//Loads the audio data into buffer
		AL10.alBufferData(p, openALFormat, data, (int)format.getSampleRate());
		
		return (long)(1000f * stream.getFrameLength() / format.getFrameRate());		
	}
	
	public String getFileName() {
		return name;
	}
	
	public int getSource() {
		return src;
	}
	
	public void setLooping(boolean loop) {
		this.loop = loop;
	}
}

