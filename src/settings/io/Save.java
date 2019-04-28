package settings.io;

import java.io.*;
import engine.io.Audio;
import static main.Main_menu.volume_music;
import static main.Main_menu.volume_sfx;
import static main.Main.window;

/**
 * @author Team 62
 * 
 * Oliver Legg - 201244658 - sgolegg
 *
 */
public class Save {
	
	private final static String MUSIC = "music";
	private final static String MUTE = "mute";
	private final static String SFX = "sfx";
	private final static String FULLSCREEN = "fullscreen";
	
	private static String FILENAME = "./assets/settings/settings.csv";
	private static char COMMA = ',';
	private static char NEWLINE = '\n';
	
	// Writes the variables to save.csv
	public static void write() {
		try (PrintWriter writer = new PrintWriter(new File(FILENAME))) {
	        StringBuilder sb = new StringBuilder();
	        float width, max_width;
	        
	        width = volume_music.getSliderWidth();
	        max_width = volume_music.getMaxWidth();
	        sb.append(MUSIC);
	        sb.append(COMMA);
	        sb.append((int)((width / max_width) * 100));
	        sb.append(NEWLINE);
	        
	        width = volume_sfx.getSliderWidth();
	        max_width = volume_sfx.getMaxWidth();
	        sb.append(SFX);
	        sb.append(COMMA);
	        sb.append((int)((width / max_width) * 100));
	        sb.append(NEWLINE);
	        
	        sb.append(FULLSCREEN);
	        sb.append(COMMA);
	        sb.append(Boolean.toString(window.isFullscreen()));
	        sb.append(NEWLINE);

	        sb.append(MUTE);
	        sb.append(COMMA);
	        sb.append(Boolean.toString(Audio.isMuted()));
	        sb.append(NEWLINE);  

	        writer.write(sb.toString());
	      } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	      }  
	}
}
