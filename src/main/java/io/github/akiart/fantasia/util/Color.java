package io.github.akiart.fantasia.util;

public class Color {

    public static int getARGB(int rgba) {
        return rgba >> 8 | 0xFF000000;
    }

    public static int getRGBA(int argb) {
        return argb << 8 | 0x000000FF;
    }

    public static double getR(int argb) { return (double)(argb >> 16 & 255) / 255; }
    public static double getG(int argb) { return (double)(argb >> 8 & 255) / 255; }
    public static double getB(int argb) { return (double)(argb & 255) / 255; }
}
