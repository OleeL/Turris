/**
 * 
 */
package com.team62.turris.engine.io;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Image {
    public ByteBuffer getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private ByteBuffer image;
    private int width, height;

    Image(int width, int height, ByteBuffer image) {
        this.image = image;
        this.height = height;
        this.width = width;
    }
    
    public static Image loadImage(String path) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) {
                System.err.println("Couldn't load " + path);
            }
            width = w.get();
            height = h.get();
        }
        return new Image(width, height, image);
    }
}