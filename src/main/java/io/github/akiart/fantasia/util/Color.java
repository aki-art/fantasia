package io.github.akiart.fantasia.util;

public class Color {

    public static int getARGB(int rgba) {
        return rgba >> 8 | 0xFF000000;
    }

    public static int getRGBA(int argb) {
        return argb << 8 | 0x000000FF;
    }
}
