package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiCrosshairBlocks extends Gui {

   public static boolean isActive = false;
   private static Minecraft mc;


   public GuiCrosshairBlocks() {
      mc = Minecraft.func_71410_x();
      updateBooleans();
   }

   public static void updateBooleans() {
      isActive = HintsValidator.isCrosshairBlocksActive();
   }

   public void draw() {
      if(isActive) {
         if(mc.field_71439_g != null) {
            MyChatListener var10000 = Main.chatListener;
            if(MyChatListener.IS_IN_GAME) {
               if(mc.field_71439_g.field_70125_A >= 60.0F) {
                  ItemStack is = mc.field_71439_g.func_71045_bC();
                  if(is != null && is.func_77973_b() == Item.func_150898_a(Blocks.field_150325_L)) {
                     if(is.field_77994_a <= 10) {
                        ScaledResolution sr = new ScaledResolution(mc);
                        int screen_width = sr.func_78326_a();
                        int screen_height = sr.func_78328_b();
                        GlStateManager.func_179094_E();
                        GlStateManager.func_179109_b((float)screen_width / 2.0F, (float)screen_height / 2.0F - 5.0F - (float)mc.field_71466_p.field_78288_b, 0.0F);
                        float scale = 1.0F;
                        float opacity = 1.0F;
                        if(is.field_77994_a > 5) {
                           opacity = (float)(11 - is.field_77994_a) * 0.2F;
                        }

                        Color color = new Color(1.0F, 0.0F, 0.0F, opacity);
                        GlStateManager.func_179152_a(scale, scale, scale);
                        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
                        String text = "" + is.field_77994_a;
                        mc.field_71466_p.func_175065_a(text, (float)(-mc.field_71466_p.func_78256_a(text) / 2), 0.0F, color.getRGB(), true);
                        GlStateManager.func_179121_F();
                     }
                  }
               }
            }
         }
      }
   }

}
