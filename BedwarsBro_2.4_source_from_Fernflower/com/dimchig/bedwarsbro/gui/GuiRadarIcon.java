package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.hints.HintsValidator;
import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class GuiRadarIcon extends Gui {

   public static boolean isActive = false;
   private ResourceLocation resourceLoc_other = new ResourceLocation("bedwarsbro:textures/gui/other.png");
   private static Minecraft mc;
   private TextureManager textureManager;
   private long time_started = -1L;
   private int time_visible = 1500;
   private static boolean isBedIcon = true;


   public GuiRadarIcon() {
      mc = Minecraft.func_71410_x();
      this.textureManager = mc.func_110434_K();
      updateBooleans();
   }

   public static void updateBooleans() {
      isActive = HintsValidator.isRadarIconActive();
   }

   public void show(boolean b) {
      if(isActive) {
         this.time_started = (new Date()).getTime();
         isBedIcon = b;
      }
   }

   public void draw() {
      if(isActive) {
         if(this.time_started >= 0L) {
            if(this.textureManager == null) {
               this.textureManager = mc.func_110434_K();
            }

            ScaledResolution sr = new ScaledResolution(mc);
            int screen_width = sr.func_78326_a();
            int screen_height = sr.func_78328_b();
            long t = (new Date()).getTime();
            if(t - this.time_started > (long)this.time_visible) {
               this.time_started = -1L;
            } else {
               float opacity = 1.0F;
               double dist = (double)Math.abs(((float)(t - this.time_started) - (float)this.time_visible / 2.0F) / (float)this.time_visible);
               opacity = (float)Math.pow(1.0D - dist * 2.0D, 0.5D);
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)(screen_width / 2), 25.0F, 0.0F);
               float scale = 0.33F;
               GlStateManager.func_179152_a(scale, scale, scale);
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, opacity);
               mc.field_71446_o.func_110577_a(this.resourceLoc_other);
               if(isBedIcon) {
                  this.func_73729_b(-88, -64, 0, 0, 176, 128);
               } else {
                  this.func_73729_b(-64, -64, 0, 128, 128, 128);
               }

               GlStateManager.func_179121_F();
            }
         }
      }
   }

}
