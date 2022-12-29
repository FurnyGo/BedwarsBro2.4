// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import net.minecraft.item.ItemStack;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiCrosshairBlocks extends Gui
{
    public static boolean isActive;
    private static Minecraft mc;
    
    public GuiCrosshairBlocks() {
        GuiCrosshairBlocks.mc = Minecraft.func_71410_x();
        updateBooleans();
    }
    
    public static void updateBooleans() {
        GuiCrosshairBlocks.isActive = HintsValidator.isCrosshairBlocksActive();
    }
    
    public void draw() {
        if (!GuiCrosshairBlocks.isActive) {
            return;
        }
        if (GuiCrosshairBlocks.mc.field_71439_g == null) {
            return;
        }
        final MyChatListener chatListener = Main.chatListener;
        if (!MyChatListener.IS_IN_GAME) {
            return;
        }
        if (GuiCrosshairBlocks.mc.field_71439_g.field_70125_A < 60.0f) {
            return;
        }
        final ItemStack is = GuiCrosshairBlocks.mc.field_71439_g.func_71045_bC();
        if (is == null || is.func_77973_b() != Item.func_150898_a(Blocks.field_150325_L)) {
            return;
        }
        if (is.field_77994_a > 10) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(GuiCrosshairBlocks.mc);
        final int screen_width = sr.func_78326_a();
        final int screen_height = sr.func_78328_b();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(screen_width / 2.0f, screen_height / 2.0f - 5.0f - GuiCrosshairBlocks.mc.field_71466_p.field_78288_b, 0.0f);
        final float scale = 1.0f;
        float opacity = 1.0f;
        if (is.field_77994_a > 5) {
            opacity = (11 - is.field_77994_a) * 0.2f;
        }
        final Color color = new Color(1.0f, 0.0f, 0.0f, opacity);
        GlStateManager.func_179152_a(scale, scale, scale);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        final String text = "" + is.field_77994_a;
        GuiCrosshairBlocks.mc.field_71466_p.func_175065_a(text, (float)(-GuiCrosshairBlocks.mc.field_71466_p.func_78256_a(text) / 2), 0.0f, color.getRGB(), true);
        GlStateManager.func_179121_F();
    }
    
    static {
        GuiCrosshairBlocks.isActive = false;
    }
}
