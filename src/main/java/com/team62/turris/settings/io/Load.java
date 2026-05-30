package com.team62.turris.settings.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.team62.turris.engine.io.Audio;
import static com.team62.turris.Main_menu.volume_sfx;
import static com.team62.turris.Main_menu.volume_music;
import static com.team62.turris.Main.window;

/**
 * @author Team 62
 * 
 * Oliver Legg - 201244658 - sgolegg
 *
 */

public class Load {
	
	private final static String MUSIC = "music";
	private final static String MUTE = "mute";
	private final static String SFX = "sfx";
	private final static String FULLSCREEN = "fullscreen";
	
	private static String FILENAME = "./assets/settings/settings.csv";
	
	// For loading the game
	public static void load() {
		try {
			File file = new File(FILENAME);
			Scanner inputStream;
			inputStream = new Scanner(file);
			String data;
			String[] values;		
			float width, value;
			
			// Until EOF (Turrets)
			while (inputStream.hasNext()) {
				data = inputStream.next();
				values = data.split(",");
				
				switch (values[0]) {
					case MUSIC:
						volume_music.update("Music: "+values[1]);
						width = volume_music.getMaxWidth();
						value = Integer.parseInt(values[1]);
						volume_music.setSliderWidth((int)((width / 100)*value));
						Audio.updateVolume();
						break;
					case SFX:
						volume_sfx.update("Sound Effects: "+values[1]);
						width = volume_sfx.getMaxWidth();
						value = Integer.parseInt(values[1]);
						volume_sfx.setSliderWidth((int)((width / 100)*value));
						Audio.updateVolume();
						break;
					case MUTE:
						Audio.setMute(Boolean.parseBoolean(values[1]));
						break;
					case FULLSCREEN:
						window.setFullscreen(Boolean.parseBoolean(values[1]));
						break;
				}
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
