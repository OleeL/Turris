package com.team62.turris;

import com.team62.turris.engine.io.Audio;
import com.team62.turris.engine.io.Window;
import com.team62.turris.playing.Playing;
import com.team62.turris.settings.io.Load;
import java.io.File;
import java.net.URISyntaxException;
import org.lwjgl.system.Platform;

/**
 * @author Team 62
 *
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Main {

    public static Window window;

    // Setting the states:
    public static final int MAIN_MENU = 0;
    public static final int PLAYING = 1;
    public static int state = MAIN_MENU;

    public static void main(String[] args) throws Exception {
        Thread.sleep(4000);
        // Setting up window settings
        int width = 800; // Screen Width
        int height = 600; // Screen Height
        int fps = 60; // Max Frame Rate
        boolean vsync = false; // Vsync settings
        String windowName = "Turris"; // Name of the window

        configureWorkingDirectoryForAssets();
        final String userDir = System.getProperty("user.dir");
        System.out.println("Working Directory = " + userDir);

        // Creates the game window
        window = new Window(width, height, fps, vsync, windowName);

        String iconPath = "assets/images/TurrisIcon.png";
        if (!new File(iconPath).isFile()) {
            throw new Exception("Icon file not found: " + iconPath);
        }

        if (Platform.get() != Platform.MACOSX) {
            window.setIcon(iconPath);
        }
        if (Platform.get() == Platform.MACOSX) {
            java.awt.Toolkit.getDefaultToolkit();
        }
        window.create();
        window.setFullscreen(false); // set fullscrn to false for testing

        // Creates the main menu
        Main_menu.create();

        try {
            Audio.setup();
        } catch (Exception e) {
            System.out.println("Failed to setup audio system");
        }

        Load.load();

        System.out.println("Turris Loaded");

        // While the windows isn't closed print to the screen
        while (!window.closed()) {
            // Organises the updating within the states
            if (window.processingLimitReady()) {
                double dt = window.getDelta();
                window.clear(); // Clears the previous frame
                window.update(); // Start update
                switch (state) {
                    case MAIN_MENU:
                        Audio.playLoop(Audio.MSC_MENU);
                        Main_menu.update(dt);
                        Main_menu.draw();
                        break;
                    case PLAYING:
                        Playing.update(dt);
                        Playing.draw();
                        break;
                }
                // printMouseCoordsOnClick();
                window.finishUpdate();
                // Finish update
                window.swapBuffers();
            }
        }

        Audio.destroy();
    }

    private static void configureWorkingDirectoryForAssets()
        throws URISyntaxException {
        if (new File("assets").isDirectory()) {
            return;
        }

        File codePath = new File(
            Main.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
        );
        File codeDir = codePath.isDirectory()
            ? codePath
            : codePath.getParentFile();
        File[] candidates = {
            codeDir,
            codeDir == null ? null : codeDir.getParentFile(),
            new File("target"),
        };

        for (File candidate : candidates) {
            if (
                candidate != null && new File(candidate, "assets").isDirectory()
            ) {
                System.setProperty("user.dir", candidate.getAbsolutePath());
                return;
            }
        }
    }

    public static void printMouseCoordsOnClick() {
        if (window.isMousePressed(window.LEFT_MOUSE)) System.out.println(
            "(" + window.getMouseX() + ", " + window.getMouseY() + ")"
        );
    }
}
