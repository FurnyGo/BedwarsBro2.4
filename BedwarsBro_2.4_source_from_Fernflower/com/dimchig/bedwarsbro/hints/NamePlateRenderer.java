package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NamePlateRenderer {

   static Minecraft mc;
   static boolean isCrossedNicknamesActive = true;
   public static ArrayList friends = new ArrayList();


   public NamePlateRenderer() {
      mc = Minecraft.func_71410_x();
   }

   public void updateBooleans() {
      isCrossedNicknamesActive = Main.getConfigBool(Main.CONFIG_MSG.BETTER_TAB_CROSSED_NICKNAMES);
   }

   @SubscribeEvent
   public void onMyRender(NameFormat event) {
      String color = "&f";
      CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor(event.entityPlayer);
      if(team_color != CustomScoreboard.TEAM_COLOR.NONE) {
         color = "&" + CustomScoreboard.getCodeByTeamColor(team_color);
      }

      String prefix = this.getPrefixByName(event.username);
      String display_name = event.displayname;
      display_name = ChatSender.parseText(display_name);
      if(prefix.length() > 0 && display_name.length() > 2) {
         MyChatListener var10000 = Main.chatListener;
         if(!MyChatListener.IS_IN_GAME) {
            color = "";
         }
      }

      event.displayname = ColorCodesManager.replaceColorCodesInString(prefix + color + display_name);
      this.updateGameTab();
   }

   public void updateGameTab() {
      if(mc.func_147114_u() != null && mc.func_147114_u().func_175106_d() != null) {
         Collection players = mc.func_147114_u().func_175106_d();
         int cnt = 0;
         MyChatListener var10000 = Main.chatListener;
         boolean isInGame = MyChatListener.IS_IN_GAME;
         Iterator var4 = players.iterator();

         while(var4.hasNext()) {
            NetworkPlayerInfo info = (NetworkPlayerInfo)var4.next();
            ++cnt;
            if(cnt > 500) {
               return;
            }

            if(info.func_178845_a() != null && info.func_178850_i() != null) {
               String player_name = info.func_178845_a().getName();
               String player_prefix = this.getPrefixByName(player_name);
               String color_code = "&7";
               String donation = "";
               boolean hasFlag = Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175243_a(info).contains("⚑") || info.func_178850_i().func_96668_e().contains("⚑");
               if(hasFlag) {
                  ;
               }

               String new_name;
               if(!isInGame && !hasFlag) {
                  donation = info.func_178850_i().func_96668_e().trim();
                  if(donation.length() > 4) {
                     donation = donation.replace("§0", "&f");
                     color_code = donation.substring(donation.length() - 2, donation.length());
                     donation = donation + " ";
                  } else {
                     donation = "";
                  }
               } else {
                  color_code = "&7";
                  if(isCrossedNicknamesActive) {
                     color_code = color_code + "&m";
                  }

                  if(info.func_178850_i() == null) {
                     continue;
                  }

                  new_name = info.func_178850_i().func_96661_b();
                  var10000 = Main.chatListener;
                  CustomScoreboard.TEAM_COLOR c = MyChatListener.getEntityTeamColorByTeam(new_name);
                  if(c != CustomScoreboard.TEAM_COLOR.NONE) {
                     color_code = "&" + CustomScoreboard.getCodeByTeamColor(c);
                  }
               }

               if(player_prefix.length() > 0) {
                  var10000 = Main.chatListener;
                  if(!MyChatListener.IS_IN_GAME) {
                     color_code = "";
                  }
               }

               new_name = player_prefix + donation + color_code + player_name;
               new_name = ChatSender.parseText(new_name);
               if(friends.contains(player_name)) {
                  StringBuilder var13 = new StringBuilder();
                  MyChatListener var10001 = Main.chatListener;
                  new_name = var13.append(MyChatListener.PREFIX_FRIEND_IN_CHAT).append(new_name).toString();
               }

               var10000 = Main.chatListener;
               MyChatListener.PREFIX_FRIEND_IN_CHAT = "&c&l<&6&lД&e&lр&a&lу&b&lг&d&l> &r";
               info.func_178859_a(new ChatComponentText(ColorCodesManager.replaceColorCodesInString(new_name)));
            }
         }

      }
   }

   public void printSameUsersInGame() {
      if(mc != null && mc.field_71439_g != null) {
         if(!Main.getPropAuthorPrefix().equals("none")) {
            if(mc.func_147114_u() != null && mc.func_147114_u().func_175106_d() != null) {
               Collection players = mc.func_147114_u().func_175106_d();
               if(players != null && players.size() != 0) {
                  EntityPlayerSP mod_player = mc.field_71439_g;
                  int cnt = 0;
                  ArrayList arr = new ArrayList();
                  Iterator text = players.iterator();

                  while(text.hasNext()) {
                     NetworkPlayerInfo info = (NetworkPlayerInfo)text.next();
                     if(info.func_178845_a() != null && info.func_178850_i() != null) {
                        String player_name = info.func_178845_a().getName();
                        String player_prefix = this.getPrefixByName(player_name);
                        if(player_prefix.length() != 0 && !mod_player.func_70005_c_().equals(player_name)) {
                           String team_name = info.func_178850_i().func_96661_b();
                           MyChatListener var10000 = Main.chatListener;
                           CustomScoreboard.TEAM_COLOR c = MyChatListener.getEntityTeamColorByTeam(team_name);
                           String color_code = "&f";
                           if(c != CustomScoreboard.TEAM_COLOR.NONE) {
                              color_code = "&" + CustomScoreboard.getCodeByTeamColor(c);
                           }

                           ++cnt;
                           arr.add(color_code + ColorCodesManager.removeColorCodes(player_name));
                        }
                     }
                  }

                  if(cnt > 0 && arr.size() != 0) {
                     String var12 = MyChatListener.PREFIX_BEDWARSBRO + "С тобой в катке играет &aDimChig &fпод ником \"" + (String)arr.get(0) + "&f\"! Это автор и создатель мода &r&cBedwars&fBro!";
                     ChatSender.addText(var12);
                  }
               }
            }
         }
      }
   }

   public String getPrefixByName(String player_name) {
      if(!Main.isPropUserAdmin(player_name)) {
         return "";
      } else {
         String prefix = Main.getPropAuthorPrefix();
         return prefix.equals("none")?"":(prefix != null && prefix.length() != 0?prefix:"&c&l[&6&lС&e&lо&a&lз&b&lд&d&lа&c&lт&6&lе&e&lл&a&lь&c&l]&r ");
      }
   }

}
