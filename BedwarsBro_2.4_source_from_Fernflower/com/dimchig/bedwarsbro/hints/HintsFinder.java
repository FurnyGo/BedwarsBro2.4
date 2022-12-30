package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.commands.CommandHintsFinderLookAtPlayer;
import com.dimchig.bedwarsbro.hints.BWItemsHandler;
import com.dimchig.bedwarsbro.hints.HintsPlayerScanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.util.ChatComponentText;

public class HintsFinder {

   static Minecraft mc;


   public HintsFinder() {
      mc = Minecraft.func_71410_x();
   }

   public static void findAll(boolean withMessageOnFail) {
      ArrayList players = HintsPlayerScanner.scanPlayers();
      if(players == null) {
         ChatSender.addText(MyChatListener.PREFIX_HINT_FINDER + "&cОшибка");
      } else if(players.size() <= 1) {
         OnMyTickEvent.FINDER_IS_SEARCH_LOOP = true;
      } else {
         String mod_player_name = ColorCodesManager.removeColorCodes(mc.field_71439_g.func_70005_c_());
         List teams = CustomScoreboard.readBedwarsGame();
         CustomScoreboard.TEAM_COLOR mod_team_color = CustomScoreboard.TEAM_COLOR.NONE;
         double mod_pos_x = Minecraft.func_71410_x().field_71439_g.field_70165_t;
         double mod_pos_y = Minecraft.func_71410_x().field_71439_g.field_70163_u;
         double mod_pos_z = Minecraft.func_71410_x().field_71439_g.field_70161_v;
         Iterator str = teams.iterator();

         Iterator closest_player;
         label131:
         while(str.hasNext()) {
            CustomScoreboard.BedwarsTeam cnt_found = (CustomScoreboard.BedwarsTeam)str.next();
            closest_player = cnt_found.players.iterator();

            while(closest_player.hasNext()) {
               CustomScoreboard.BedwarsPlayer isFirst = (CustomScoreboard.BedwarsPlayer)closest_player.next();
               if(isFirst.name.equals(mod_player_name)) {
                  mod_team_color = cnt_found.color;
                  break label131;
               }
            }
         }

         String var29 = "";
         int var30 = 0;
         closest_player = null;
         boolean var31 = true;
         int min_distance = 9999;
         Iterator var16 = players.iterator();

         while(var16.hasNext()) {
            HintsPlayerScanner.BWPlayer player = (HintsPlayerScanner.BWPlayer)var16.next();
            if(!player.name.equals(mod_player_name)) {
               boolean isTeamFound = false;
               Iterator dist = teams.iterator();

               while(true) {
                  if(dist.hasNext()) {
                     CustomScoreboard.BedwarsTeam stars = (CustomScoreboard.BedwarsTeam)dist.next();
                     Iterator player_color_code = stars.players.iterator();

                     while(player_color_code.hasNext()) {
                        CustomScoreboard.BedwarsPlayer hoverText = (CustomScoreboard.BedwarsPlayer)player_color_code.next();
                        if(hoverText.name.equals(player.name)) {
                           player.team_color = stars.color;
                           isTeamFound = true;
                           break;
                        }
                     }

                     if(!isTeamFound) {
                        continue;
                     }
                  }

                  if(player.team_color != CustomScoreboard.TEAM_COLOR.NONE && player.team_color != mod_team_color) {
                     int var32 = (int)Math.sqrt(Math.pow(mod_pos_x - player.posX, 2.0D) + Math.pow(mod_pos_z - player.posZ, 2.0D));
                     if(var32 < min_distance) {
                        min_distance = var32;
                     }

                     if(var31) {
                        var31 = false;
                     }

                     String var33 = "";
                     String var34 = "&" + CustomScoreboard.getCodeByTeamColor(player.team_color);
                     var29 = MyChatListener.PREFIX_HINT_FINDER + var34 + "" + player.name;
                     String var35 = "&7(&f" + (int)player.posX + "&7, &f" + (int)player.posY + "&7, &f" + (int)player.posY + "&7, &c" + var32 + "&7)";
                     if(player.armourLevel == BWItemsHandler.BWItemArmourLevel.LEATHER) {
                        var35 = var35 + " &7Без брони";
                        var33 = var33 + "○";
                     }

                     if(player.armourLevel == BWItemsHandler.BWItemArmourLevel.CHAIN) {
                        var35 = var35 + " &7Кольчуга";
                     }

                     if(player.armourLevel == BWItemsHandler.BWItemArmourLevel.IRON) {
                        var35 = var35 + " &fЖелезник";
                     }

                     if(player.armourLevel == BWItemsHandler.BWItemArmourLevel.DIAMOND) {
                        var35 = var35 + " &bАлмазник";
                        var33 = var33 + "&b&l*";
                     }

                     if(player.item_in_hand != null) {
                        if(player.item_in_hand.type == BWItemsHandler.BWItemType.BOW) {
                           var35 = var35 + "&8, &cЛучник";
                           var33 = var33 + "&c&l*";
                        }

                        if(player.item_in_hand.type == BWItemsHandler.BWItemType.SWORD) {
                           if(player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.WOOD) {
                              var35 = var35 + "&8, &7Деревянный меч";
                           }

                           if(player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.STONE) {
                              var35 = var35 + "&8, &7Каменный меч";
                           }

                           if(player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.IRON) {
                              var35 = var35 + "&8, &fЖелезный меч";
                           }

                           if(player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.DIAMOND) {
                              var35 = var35 + "&8, &bАлмазный меч";
                              var33 = var33 + "&6&l*";
                           }
                        }

                        if(player.item_in_hand.type == BWItemsHandler.BWItemType.POTION_STRENGTH) {
                           var35 = var35 + "&8, &cСилка";
                           var33 = var33 + "&4&l*";
                        }

                        if(player.item_in_hand.type == BWItemsHandler.BWItemType.PEARL) {
                           var35 = var35 + "&8, &aПерл";
                           var33 = var33 + "&9&l*";
                        }
                     }

                     var29 = var29 + var33;
                     EntityPlayerSP mod_player = mc.field_71439_g;
                     var29 = var29 + " " + var34 + getArrowDirection(player.posX, player.posZ);
                     String commandText = "/" + CommandHintsFinderLookAtPlayer.command_text + " " + player.posX + " " + player.posY + " " + player.posZ + " " + player.name;
                     ChatComponentText mainComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(var29));
                     ChatComponentText hoverComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(var35));
                     HoverEvent hover = new HoverEvent(Action.SHOW_TEXT, hoverComponent);
                     ClickEvent click = new ClickEvent(net.minecraft.event.ClickEvent.Action.RUN_COMMAND, commandText);
                     mainComponent.func_150256_b().func_150209_a(hover);
                     mainComponent.func_150256_b().func_150241_a(click);
                     Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
                     ++var30;
                  }
                  break;
               }
            }
         }

         if(var30 > 0) {
            OnMyTickEvent.FINDER_IS_SEARCH_LOOP = false;
            if(!withMessageOnFail) {
               MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
            }
         } else {
            OnMyTickEvent.FINDER_IS_SEARCH_LOOP = true;
         }

      }
   }

   public static void findAndlookAtPlayer(double posX, double posY, double posZ, String p_name) {
      EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
      ArrayList players = HintsPlayerScanner.scanPlayers();
      if(players != null && players.size() > 1) {
         Iterator var9 = players.iterator();

         while(var9.hasNext()) {
            HintsPlayerScanner.BWPlayer p = (HintsPlayerScanner.BWPlayer)var9.next();
            if(p.name.equals(p_name)) {
               lookAtPlayer(player.field_70165_t, player.field_70163_u, player.field_70161_v, p.posX, p.posY, p.posZ);
               return;
            }
         }
      }

      lookAtPlayer(player.field_70165_t, player.field_70163_u, player.field_70161_v, posX, posY, posZ);
   }

   public static void lookAtPlayer(double x1, double y1, double z1, double x2, double y2, double z2) {
      double dX = x1 - x2;
      double dY = y1 - y2;
      double dZ = z1 - z2;
      float yaw = (float)Math.atan2(dZ, dX);
      float pitch = (float)(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + 3.141592653589793D);
      float t_yaw = myMap(yaw, -3.1415927F, 3.1415927F, -180.0F, 180.0F);
      float t_pitch = myMap(pitch, 3.1415927F, 6.2831855F, 90.0F, -90.0F);
      rotateTo(Minecraft.func_71410_x().field_71439_g, t_yaw + 90.0F, t_pitch);
   }

   public static String getArrowDirection(double x, double z) {
      float angle_diff = (float)Math.toDegrees(Math.atan2(z - mc.field_71439_g.field_70161_v, x - mc.field_71439_g.field_70165_t));
      angle_diff += (180.0F - mc.field_71439_g.field_70177_z) % 360.0F;
      angle_diff = (angle_diff + 90.0F + 720.0F) % 360.0F;
      int[] angles = new int[]{0, 45, 90, 135, 180, 225, 270, 315, 360};
      String[] angle_strings = new String[]{"↑", "↗", "→", "↘", "↓", "↙", "←", "↖", "↑"};
      double min_diff = 1000.0D;
      String angle_str = "-";

      for(int i = 0; i < angles.length; ++i) {
         double diff = (double)Math.abs((float)angles[i] - angle_diff);
         if(diff < min_diff) {
            min_diff = diff;
            angle_str = angle_strings[i];
         }
      }

      return angle_str;
   }

   public static float myMap(float value, float leftMin, float leftMax, float rightMin, float rightMax) {
      float leftSpan = leftMax - leftMin;
      float rightSpan = rightMax - rightMin;
      float valueScaled = (value - leftMin) / leftSpan;
      return rightMin + valueScaled * rightSpan;
   }

   public static void rotateTo(Entity player, float target_angle_yaw, float target_angle_pitch) {
      float prev_rot_yaw = player.field_70177_z;
      float prev_rot_pitch = player.field_70125_A;
      float angle_yaw = target_angle_yaw - prev_rot_yaw;
      float angle_pitch = target_angle_pitch - prev_rot_pitch;
      rotateAngles(player, angle_yaw, angle_pitch);
      double delta_yaw = (double)(player.field_70177_z - prev_rot_yaw);
      double delta_pitch = (double)(player.field_70125_A - prev_rot_pitch);
      if(Math.abs(target_angle_pitch) <= 90.0F) {
         if(target_angle_yaw != player.field_70177_z || target_angle_pitch != player.field_70125_A) {
            rotateTo(player, target_angle_yaw, target_angle_pitch);
         }

      }
   }

   public static void rotateAngles(Entity player, float angle_yaw, float angle_pitch) {
      player.func_70082_c(angle_yaw / 0.15F, angle_pitch / -0.15F);
   }
}
