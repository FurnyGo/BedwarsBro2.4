// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.client.Minecraft;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;

public class RainbowColorSynchronizer
{
    public static ArrayList<Color> gradient_colors;
    static Random rnd;
    static Minecraft mc;
    public static int rainbowSpeed;
    
    public RainbowColorSynchronizer() {
        RainbowColorSynchronizer.mc = Minecraft.func_71410_x();
        RainbowColorSynchronizer.gradient_colors = new ArrayList<Color>();
        for (int r = 0; r < 100; ++r) {
            RainbowColorSynchronizer.gradient_colors.add(new Color(r * 255 / 100, 255, 0));
        }
        for (int g = 100; g > 0; --g) {
            RainbowColorSynchronizer.gradient_colors.add(new Color(255, g * 255 / 100, 0));
        }
        for (int b = 0; b < 100; ++b) {
            RainbowColorSynchronizer.gradient_colors.add(new Color(255, 0, b * 255 / 100));
        }
        for (int r = 100; r > 0; --r) {
            RainbowColorSynchronizer.gradient_colors.add(new Color(r * 255 / 100, 0, 255));
        }
        for (int g = 0; g < 100; ++g) {
            RainbowColorSynchronizer.gradient_colors.add(new Color(0, g * 255 / 100, 255));
        }
        for (int b = 100; b > 0; --b) {
            RainbowColorSynchronizer.gradient_colors.add(new Color(0, 255, b * 255 / 100));
        }
        RainbowColorSynchronizer.gradient_colors.add(new Color(0, 255, 0));
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        RainbowColorSynchronizer.rainbowSpeed = HintsValidator.getRainbowSpeed();
    }
    
    public Color getColor() {
        return this.getColor(0);
    }
    
    public Color getColor(final int x) {
        if (RainbowColorSynchronizer.gradient_colors.size() == 0) {
            return new Color(1.0f, 1.0f, 1.0f);
        }
        final int idx = (int)((RainbowColorSynchronizer.mc.field_71441_e.func_82737_E() * RainbowColorSynchronizer.rainbowSpeed + x + RainbowColorSynchronizer.gradient_colors.size()) % RainbowColorSynchronizer.gradient_colors.size());
        return RainbowColorSynchronizer.gradient_colors.get((RainbowColorSynchronizer.gradient_colors.size() - idx) % RainbowColorSynchronizer.gradient_colors.size());
    }
    
    public Color getRandomColor() {
        if (RainbowColorSynchronizer.rnd == null) {
            RainbowColorSynchronizer.rnd = RainbowColorSynchronizer.mc.field_71441_e.field_73012_v;
        }
        return RainbowColorSynchronizer.gradient_colors.get(RainbowColorSynchronizer.rnd.nextInt(RainbowColorSynchronizer.gradient_colors.size()));
    }
    
    static {
        RainbowColorSynchronizer.rainbowSpeed = 1;
    }
}
