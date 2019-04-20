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

/* Todo
 * Work out how to control audio volume?
 * Make audio not sound like the beat of a song??
 * Speed up audio when game speed is sped up???
 * Add limit to audio sources - Too many turrets shooting on high game speeds
 */

public class Audio{
	
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
	
	public static final String MSC_MENU = "music/menu.wav";
	public static final String MSC_GAME = "music/game.wav";
	
	
	//Plays an audio file
	public  void playAudio(String filename) {
		Sound test = new Sound(filename);
		test.start();
	}

//Creates a sound object on a new thread
class Sound extends Thread {
	private String name;
	
	public Sound(String name) {
		this.name = name;
	}
	
	//Runs the thread
	public void run() {
		try {
			play();
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Reads and plays the audio file
	public void play() throws UnsupportedAudioFileException{
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
			try {
				throw new Exception("Failed to make context current");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AL.createCapabilities(deviceCaps);
		
		AL10.alListener3f(AL10.AL_VELOCITY, 0f, 0f, 0f);
		AL10.alListener3f(AL10.AL_ORIENTATION, 0f, 0f, -1f);
		
		
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		AL10.alGenBuffers(buffer);
		
		long time = createBufferData(buffer.get(0));
			
		int source = AL10.alGenSources();
		
		
		AL10.alSourcei(source, AL10.AL_BUFFER, buffer.get(0));
		AL10.alSource3f(source, AL10.AL_POSITION, 0f, 0f, 0f);
		AL10.alSource3f(source,AL10.AL_VELOCITY,0f,0f,0f);
		
		AL10.alSourcef(source, AL10.AL_PITCH,1);
		AL10.alSourcef(source, AL10.AL_GAIN, 1f);
		AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_FALSE);
		
		AL10.alSourcePlay(source);
		
		  try {
	            Thread.sleep(time); //Wait for the sound to finish
	        } catch(InterruptedException ex) {}
		
		AL10.alSourceStop(source);
		
		AL10.alDeleteSources(source);
		
		ALC10.alcCloseDevice(device);	
	}
	
	private long createBufferData(int p) throws UnsupportedAudioFileException {
		final int MONO = 1, STEREO = 2;
		
		File audio = new File("assets/sounds/" + name);
		
		AudioInputStream stream = null;
		try {
			stream = AudioSystem.getAudioInputStream(audio);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AudioFormat format = stream.getFormat();
		if (format.isBigEndian()) throw new UnsupportedAudioFileException("Big endian");
		
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
