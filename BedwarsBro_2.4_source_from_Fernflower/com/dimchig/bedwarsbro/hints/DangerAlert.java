package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import com.dimchig.bedwarsbro.hints.BWItemsHandler;
import com.dimchig.bedwarsbro.hints.HintsPlayerScanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class DangerAlert {

   private static int danger_zone_radius = 5;
   private static int max_ray_distance = 100;
   private static long prev_sound_time = 0L;
   private static long sound_freq = 150L;
   private static long prev_message_time = 0L;
   private static long message_freq = 3000L;


   public void scan(ArrayList players, EntityPlayerSP mod_player) {
      long t = (new Date()).getTime();
      WorldClient world = Minecraft.func_71410_x().field_71441_e;
      Main.playerFocus.clearLines();
      Iterator var6 = players.iterator();

      while(var6.hasNext()) {
         HintsPlayerScanner.BWPlayer p = (HintsPlayerScanner.BWPlayer)var6.next();
         if(!p.en.func_70005_c_().equals(mod_player.func_70005_c_()) && mod_player.func_96124_cp() != p.en.func_96124_cp() && p.item_in_hand != null && (p.item_in_hand.type == BWItemsHandler.BWItemType.BOW || p.item_in_hand.type == BWItemsHandler.BWItemType.FIREBALL)) {
            MovingObjectPosition ray = null;

            for(int i = 1; i < max_ray_distance; ++i) {
               ray = p.en.func_174822_a((double)i, 1.0F);
               if(ray != null) {
                  boolean isInDanger = this.isPlayerInDangerZone(mod_player, ray.field_72307_f.field_72450_a, ray.field_72307_f.field_72448_b, ray.field_72307_f.field_72449_c);
                  if(isInDanger) {
                     if(GuiPlayerFocus.STATE) {
                        Object volume = null;
                        Vec3 p2 = new Vec3(p.en.field_70165_t, p.en.field_70163_u + (double)p.en.eyeHeight, p.en.field_70161_v);
                        MyChatListener var10004 = Main.chatListener;
                        Main.playerFocus.addLine((Vec3)volume, p2, Main.playerFocus.getColorByTeam(MyChatListener.getEntityTeamColor(p.en)));
                     }

                     if(t - prev_sound_time > sound_freq && Main.getConfigBool(Main.CONFIG_MSG.DANGER_ALERT_SOUND)) {
                        prev_sound_time = t;
                        float var13 = mod_player.func_70032_d(p.en) / 12.0F;
                        world.func_72980_b(p.en.field_70165_t, p.en.field_70163_u + (double)p.en.eyeHeight, p.en.field_70161_v, "note.pling", var13, 1.0F, false);
                     }

                     if(t - prev_message_time > message_freq) {
                        prev_message_time = t;
                        if(p.item_in_hand.type == BWItemsHandler.BWItemType.BOW) {
                           ChatSender.addText(MyChatListener.PREFIX_DANGER_ALERT + "&fНа тебя целятся из &cЛУКА");
                        } else if(p.item_in_hand.type == BWItemsHandler.BWItemType.FIREBALL) {
                           ChatSender.addText(MyChatListener.PREFIX_DANGER_ALERT + "&fНа тебя целятся &6ФАЕРБОЛОМ");
                        }
                     }
                     break;
                  }
               }
            }
         }
      }

   }

   boolean isPlayerInDangerZone(EntityPlayerSP mod_player, double x, double y, double z) {
      double dist = Math.sqrt(Math.pow(mod_player.field_70165_t - x, 2.0D) + Math.pow(mod_player.field_70163_u + (double)mod_player.func_70047_e() - y, 2.0D) + Math.pow(mod_player.field_70161_v - z, 2.0D));
      return dist < (double)danger_zone_radius;
   }

}
