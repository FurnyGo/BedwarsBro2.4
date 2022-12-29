// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Date;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.Gui;

public class GuiRadarIcon extends Gui
{
    public static boolean isActive;
    private ResourceLocation resourceLoc_other;
    private static Minecraft mc;
    private TextureManager textureManager;
    private long time_started;
    private int time_visible;
    private static boolean isBedIcon;
    
    public GuiRadarIcon() {
        this.resourceLoc_other = new ResourceLocation("bedwarsbro:textures/gui/other.png");
        this.time_started = -1L;
        this.time_visible = 1500;
        GuiRadarIcon.mc = Minecraft.func_71410_x();
        this.textureManager = GuiRadarIcon.mc.func_110434_K();
        updateBooleans();
    }
    
    public static void updateBooleans() {
        GuiRadarIcon.isActive = HintsValidator.isRadarIconActive();
    }
    
    public void show(final boolean b) {
        if (!GuiRadarIcon.isActive) {
            return;
        }
        this.time_started = new Date().getTime();
        GuiRadarIcon.isBedIcon = b;
    }
    
    public void draw() {
        if (!GuiRadarIcon.isActive) {
            return;
        }
        if (this.time_started < 0L) {
            return;
        }
        if (this.textureManager == null) {
            this.textureManager = GuiRadarIcon.mc.func_110434_K();
        }
        final ScaledResolution sr = new ScaledResolution(GuiRadarIcon.mc);
        final int screen_width = sr.func_78326_a();
        final int screen_height = sr.func_78328_b();
        final long t = new Date().getTime();
        if (t - this.time_started > this.time_visible) {
            this.time_started = -1L;
            return;
        }
        float opacity = 1.0f;
        final double dist = Math.abs((t - this.time_started - this.time_visible / 2.0f) / this.time_visible);
        opacity = (float)Math.pow(1.0 - dist * 2.0, 0.5);
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)(screen_width / 2), 25.0f, 0.0f);
        final float scale = 0.33f;
        GlStateManager.func_179152_a(scale, scale, scale);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, opacity);
        GuiRadarIcon.mc.field_71446_o.func_110577_a(this.resourceLoc_other);
        if (GuiRadarIcon.isBedIcon) {
            this.func_73729_b(-88, -64, 0, 0, 176, 128);
        }
        else {
            this.func_73729_b(-64, -64, 0, 128, 128, 128);
        }
        GlStateManager.func_179121_F();
    }
    
    static {
        GuiRadarIcon.isActive = false;
        GuiRadarIcon.isBedIcon = true;
    }
}
