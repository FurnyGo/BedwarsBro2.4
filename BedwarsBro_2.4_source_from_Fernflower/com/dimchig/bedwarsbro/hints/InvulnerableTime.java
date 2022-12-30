package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.BWBed;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class InvulnerableTime extends Gui {

   static Minecraft mc;
   public static int INVULNERABLE_TICKS_AMOUNT = 40;
   public static long time_last_sound = -1L;


   public InvulnerableTime() {
      mc = Minecraft.func_71410_x();
   }

   public static void scan(List players, Vec3 pos, float partialTicks, boolean areSoundsActive) {
      EntityPlayerSP player = mc.field_71439_g;
      if(players.size() >= 2 && player != null) {
         BWBed bed = MyChatListener.GAME_BED;
         if(bed != null) {
            double dist = player.func_70011_f((double)bed.part1_posX, (double)bed.part1_posY, (double)bed.part1_posZ);
            if(dist < 30.0D) {
               return;
            }
         }

         Iterator dist2 = players.iterator();

         while(dist2.hasNext()) {
            EntityPlayer en = (EntityPlayer)dist2.next();
            if(en != player && en.func_96124_cp() != player.func_96124_cp() && en.field_70163_u <= 100.0D && en.field_70173_aa <= INVULNERABLE_TICKS_AMOUNT) {
               double dist1 = Math.sqrt(Math.pow(player.field_70165_t - en.field_70165_t, 2.0D) + Math.pow(player.field_70161_v - en.field_70161_v, 2.0D));
               if(dist1 <= 15.0D) {
                  if(Main.shopManager.findItemInHotbar("Наблюдение за") != -1) {
                     return;
                  }

                  double x = en.field_70169_q + (en.field_70165_t - en.field_70169_q) * (double)partialTicks;
                  double y = en.field_70167_r + (en.field_70163_u - en.field_70167_r) * (double)partialTicks;
                  double z = en.field_70166_s + (en.field_70161_v - en.field_70166_s) * (double)partialTicks;
                  y += (double)en.field_70131_O + 1.3D;
                  if(areSoundsActive) {
                     String ticks_cnt = "note.hat";
                     long text = (new Date()).getTime();
                     if(text - time_last_sound > 100L) {
                        if(en.field_70173_aa == 2) {
                           mc.field_71441_e.func_72980_b(x, y, z, ticks_cnt, 0.5F, 0.6F, false);
                           time_last_sound = text;
                        } else if(en.field_70173_aa == 20) {
                           mc.field_71441_e.func_72980_b(x, y, z, ticks_cnt, 0.75F, 1.0F, false);
                           time_last_sound = text;
                        } else if(en.field_70173_aa == 40) {
                           mc.field_71441_e.func_72980_b(x, y, z, ticks_cnt, 1.0F, 1.4F, false);
                           time_last_sound = text;
                        }
                     }
                  }

                  int ticks_cnt1 = INVULNERABLE_TICKS_AMOUNT - en.field_70173_aa;
                  String text1 = (new DecimalFormat("0.0")).format((double)((float)ticks_cnt1 / 20.0F));
                  drawText(pos, new Vec3(x, y, z), player, (float)ticks_cnt1, text1);
               }
            }
         }

      }
   }

   static void drawText(Vec3 pos, Vec3 text_pos, EntityPlayerSP player, float ticks_cnt, String text) {
      float green = Math.min(((float)INVULNERABLE_TICKS_AMOUNT - ticks_cnt) / (float)INVULNERABLE_TICKS_AMOUNT, 1.0F);
      Color color = new Color(1.0F - green, green, 0.0F);
      Main.draw3DText.drawText(pos, text_pos, player, text, color.getRGB());
   }

}
