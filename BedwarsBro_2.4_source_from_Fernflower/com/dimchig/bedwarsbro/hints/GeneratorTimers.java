package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.awt.Color;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GeneratorTimers extends Gui {

   static Minecraft mc;
   static boolean isActive = false;
   static int start_diamond_time = 30;
   static long time_last_diamond = 0L;
   static int time_diamond_max = start_diamond_time;
   static int time_diamond_buffer = time_diamond_max;
   static int start_emerald_time = 65;
   static long time_last_emerald = 0L;
   static int time_emerald_max = start_emerald_time;
   static int time_emerald_buffer = time_emerald_max;
   static long time_game_start = 0L;
   static int corner_position = 2;
   static boolean isAdvanced = false;
   static boolean isGameTime = false;
   static boolean isTimeline = false;
   static int timeline_width_percentage = 80;
   ResourceLocation resourceLoc_textures;


   public GeneratorTimers() {
      mc = Minecraft.func_71410_x();
      this.resourceLoc_textures = new ResourceLocation("bedwarsbro:textures/gui/timeline_icons.png");
   }

   public void updateBooleans() {
      isActive = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS);
      corner_position = Main.getConfigInt(Main.CONFIG_MSG.GENERATOR_TIMERS_POSITION);
      isAdvanced = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS_ADVANCED);
      isTimeline = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS_TIMELINE);
      isGameTime = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS_GAME_TIME);
      timeline_width_percentage = Main.getConfigInt(Main.CONFIG_MSG.GENERATOR_TIMERS_TIMELINE_WIDTH);
   }

   public void setMaxTimeDiamonds(int new_time) {
      time_diamond_buffer = new_time;
   }

   public void setMaxTimeEmeralds(int new_time) {
      time_emerald_buffer = new_time;
   }

   public void setHardTimeDiamonds(int hard_time) {
      time_last_diamond = (new Date()).getTime() - (long)((time_diamond_max - hard_time + 1) * 1000);
   }

   public void setHardTimeEmeralds(int hard_time) {
      time_last_emerald = (new Date()).getTime() - (long)((time_emerald_max - hard_time + 1) * 1000);
   }

   public void onTick() {
      try {
         List ex = mc.field_71441_e.func_175644_a(EntityArmorStand.class, EntitySelectors.field_94557_a);
         if(ex != null) {
            boolean isDiamondGenSet = false;
            boolean isEmeraldGenSet = false;
            Iterator var4 = ex.iterator();

            while(var4.hasNext()) {
               EntityArmorStand en = (EntityArmorStand)var4.next();
               if(en != null && en.func_145748_c_() != null) {
                  String name = en.func_145748_c_().func_150260_c();
                  if(name.contains("енератор")) {
                     String connected_stand_text = null;
                     BlockPos minPos = new BlockPos(en.field_70165_t - 1.0D, en.field_70163_u - 1.0D, en.field_70161_v - 1.0D);
                     BlockPos maxPos = new BlockPos(en.field_70165_t + 1.0D, en.field_70163_u + 1.0D, en.field_70161_v + 1.0D);
                     AxisAlignedBB box = new AxisAlignedBB(minPos, maxPos);
                     List armorStands = mc.field_71441_e.func_72872_a(EntityArmorStand.class, box);
                     if(armorStands != null) {
                        Iterator hard_time = armorStands.iterator();

                        while(hard_time.hasNext()) {
                           EntityArmorStand en2 = (EntityArmorStand)hard_time.next();
                           if(en2 != null && en2.func_145748_c_() != null) {
                              String en2_name = en2.func_145748_c_().func_150254_d();
                              if(en2_name.contains("через")) {
                                 connected_stand_text = en2_name;
                                 break;
                              }
                           }
                        }

                        if(connected_stand_text != null) {
                           int hard_time1 = -1;

                           try {
                              hard_time1 = Integer.parseInt(connected_stand_text.split("§c")[1].trim().split(" ")[0].trim());
                           } catch (Exception var15) {
                              ;
                           }

                           if(hard_time1 != -1) {
                              if(!isDiamondGenSet && name.contains("алмаз")) {
                                 Main.generatorTimers.setHardTimeDiamonds(hard_time1);
                                 isDiamondGenSet = true;
                              } else if(!isEmeraldGenSet && name.contains("изумр")) {
                                 Main.generatorTimers.setHardTimeEmeralds(hard_time1);
                                 isEmeraldGenSet = true;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var16) {
         var16.printStackTrace();
      }

   }

   public void setStartTimesOnGameStart() {
      start_diamond_time = 30;
      long t = (new Date()).getTime();
      time_last_diamond = t;
      time_diamond_max = start_diamond_time;
      time_diamond_buffer = start_diamond_time;
      time_last_emerald = t;
      time_emerald_max = start_emerald_time;
      time_emerald_buffer = start_emerald_time;
      time_game_start = t;
   }

   public void draw(int screen_width, int screen_height) {
      if(isActive) {
         MyChatListener var10000 = Main.chatListener;
         if(MyChatListener.IS_IN_GAME) {
            byte padding = 6;
            int gameTime_posX;
            int gameTime_posY;
            if(corner_position == 1) {
               gameTime_posX = 2;
               gameTime_posY = padding;
            } else if(corner_position == 2) {
               gameTime_posX = screen_width - 23 - padding;
               gameTime_posY = padding;
            } else if(corner_position == 3) {
               gameTime_posX = screen_width - 23 - padding;
               gameTime_posY = screen_height - 24 - padding + (isAdvanced?-16:0) + (isGameTime?-16:0) - 18;
            } else {
               gameTime_posX = 2;
               gameTime_posY = screen_height - 24 - padding + (isAdvanced?-16:0) + (isGameTime?-16:0);
            }

            float opacity = 0.1F;
            long t = (new Date()).getTime();
            boolean isAliningToLeft = corner_position == 1 || corner_position == 4;
            int diamonds_posY;
            int time_diamonds_diff;
            int color_diamonds;
            if(isGameTime) {
               int diamonds_posX = Math.max((int)((float)(t - time_game_start) / 1000.0F) - 1, 0);
               diamonds_posY = diamonds_posX % 60;
               String time_diamonds = diamonds_posX / 60 + ":" + (diamonds_posY >= 10?"":"0") + diamonds_posY;
               time_diamonds_diff = mc.field_71466_p.func_78256_a(time_diamonds) + padding + mc.field_71466_p.func_78256_a(time_diamonds) / 2;
               int text_diamonds = gameTime_posX + 1;
               if(!isAliningToLeft) {
                  text_diamonds = screen_width - time_diamonds_diff - 3;
               }

               Gui.func_73734_a(text_diamonds - 1, gameTime_posY - 4, text_diamonds + time_diamonds_diff, gameTime_posY + 12, (new Color(0.0F, 0.0F, 0.0F, opacity)).getRGB());
               color_diamonds = (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB();
               float time_emeralds = 0.8F;
               mc.field_71446_o.func_110577_a(this.resourceLoc_textures);
               GlStateManager.func_179094_E();
               GlStateManager.func_179141_d();
               GlStateManager.func_179109_b((float)(text_diamonds + 7), (float)(gameTime_posY + 3), 0.0F);
               GlStateManager.func_179152_a(time_emeralds, time_emeralds, time_emeralds);
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               this.func_73729_b(-6, -6, 0, 0, 12, 12);
               GlStateManager.func_179121_F();
               mc.field_71466_p.func_175065_a(time_diamonds, (float)(text_diamonds + 15), (float)gameTime_posY, color_diamonds, true);
            }

            diamonds_posY = gameTime_posY + (isGameTime?16:0);
            int var41 = (int)((float)(t - time_last_diamond) / 1000.0F);
            if(var41 > time_diamond_max) {
               time_last_diamond = t;
               time_diamond_max = time_diamond_buffer;
            }

            time_diamonds_diff = time_diamond_max - var41 + 1;
            String var42 = "" + time_diamonds_diff;
            color_diamonds = (new Color(0.0F, 1.0F, 1.0F, 1.0F)).getRGB();
            if(time_diamonds_diff > time_diamond_max) {
               var42 = "0";
               color_diamonds = (new Color(1.0F, 0.0F, 0.0F, 1.0F)).getRGB();
            }

            Gui.func_73734_a(gameTime_posX, diamonds_posY - 4, gameTime_posX + 26, diamonds_posY + 12, (new Color(0.0F, 0.0F, 0.0F, opacity)).getRGB());
            mc.func_175599_af().func_175042_a(new ItemStack(Items.field_151045_i), gameTime_posX - 2, diamonds_posY - 5);
            mc.field_71466_p.func_175065_a(var42, (float)(gameTime_posX + 19 - mc.field_71466_p.func_78256_a(var42) / 2), (float)diamonds_posY, color_diamonds, true);
            int var43 = (int)((float)(t - time_last_emerald) / 1000.0F);
            if(var43 > time_emerald_max) {
               time_last_emerald = t;
               time_emerald_max = time_emerald_buffer;
            }

            int time_emeralds_diff = time_emerald_max - var43 + 1;
            String text_emeralds = "" + time_emeralds_diff;
            int color_emeralds = (new Color(0.0F, 1.0F, 0.0F, 1.0F)).getRGB();
            if(time_emeralds_diff > time_emerald_max) {
               text_emeralds = "0";
               color_emeralds = (new Color(1.0F, 0.0F, 0.0F, 1.0F)).getRGB();
            }

            int emeralds_posY = diamonds_posY + 16;
            Gui.func_73734_a(gameTime_posX, emeralds_posY - 4, gameTime_posX + 26, emeralds_posY + 12, (new Color(0.0F, 0.0F, 0.0F, opacity)).getRGB());
            mc.func_175599_af().func_175042_a(new ItemStack(Items.field_151166_bC), gameTime_posX - 2, emeralds_posY - 4);
            mc.field_71466_p.func_175065_a(text_emeralds, (float)(gameTime_posX + 19 - mc.field_71466_p.func_78256_a(text_emeralds) / 2), (float)emeralds_posY, color_emeralds, true);
            int[] times = new int[]{300, 450, 900, 1400, 2400, 3000};
            int diff = (int)((float)(t - time_game_start) / 1000.0F) - 1;
            String name = "";
            if(isTimeline) {
               this.drawTimeline(diff, times, screen_width, screen_height);
            }

            if(!isAdvanced) {
               return;
            }

            int nearest_upgrate_time = -1;

            int seconds;
            for(seconds = 0; seconds < times.length; ++seconds) {
               if(diff < times[seconds]) {
                  nearest_upgrate_time = times[seconds] - diff - 1;
                  break;
               }
            }

            if(nearest_upgrate_time == -1) {
               return;
            }

            seconds = nearest_upgrate_time % 60;
            String time = nearest_upgrate_time / 60 + ":" + (seconds >= 10?"":"0") + seconds;
            int color = (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB();
            int advanced_posX = gameTime_posX;
            if(isAliningToLeft) {
               advanced_posX = gameTime_posX;
            }

            int advanced_posY = emeralds_posY + 16;
            boolean offsetX = false;
            ItemStack itemStack = new ItemStack(Items.field_151126_ay);
            double itemStackOffsetX = 0.0D;
            double itemStackOffsetY = 0.0D;
            byte itemStackWidth = 0;
            float scale = 1.0F;
            if(diff < times[0]) {
               name = "I";
               color = (new Color(0.0F, 1.0F, 1.0F, 1.0F)).getRGB();
               itemStack = new ItemStack(Items.field_151045_i);
               itemStackOffsetX = -2.0D;
               itemStackOffsetY = -5.0D;
               itemStackWidth = 12;
            } else if(diff < times[1]) {
               name = "II";
               color = (new Color(0.0F, 1.0F, 1.0F, 1.0F)).getRGB();
               itemStack = new ItemStack(Items.field_151045_i);
               itemStackOffsetX = -2.0D;
               itemStackOffsetY = -5.0D;
               itemStackWidth = 12;
            } else if(diff < times[2]) {
               name = "I";
               color = (new Color(0.0F, 1.0F, 0.0F, 1.0F)).getRGB();
               itemStack = new ItemStack(Items.field_151166_bC);
               itemStackOffsetX = -2.0D;
               itemStackOffsetY = -4.0D;
               itemStackWidth = 11;
            } else if(diff < times[3]) {
               name = "II";
               color = (new Color(0.0F, 1.0F, 0.0F, 1.0F)).getRGB();
               itemStack = new ItemStack(Items.field_151166_bC);
               itemStackOffsetX = -2.0D;
               itemStackOffsetY = -4.0D;
               itemStackWidth = 11;
            } else if(diff < times[4]) {
               name = "Без кроватей";
               color = (new Color(1.0F, 0.0F, 0.0F, 1.0F)).getRGB();
               itemStack = new ItemStack(Items.field_151104_aV);
               itemStackOffsetX = 0.0D;
               itemStackOffsetY = -5.0D;
               itemStackWidth = 15;
            } else if(diff < times[5]) {
               name = "Конец игры";
               color = (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB();
               itemStack = new ItemStack(Item.func_150898_a(Blocks.field_180401_cv));
               itemStackOffsetX = 1.0D;
               itemStackOffsetY = -2.5D;
               itemStackWidth = 10;
               scale = 0.8F;
            }

            padding = 15;
            int total_width = mc.field_71466_p.func_78256_a(name) + itemStackWidth + padding + mc.field_71466_p.func_78256_a(time) / 2;
            int px = advanced_posX + 1;
            if(!isAliningToLeft) {
               px = screen_width - total_width - 3;
            }

            Gui.func_73734_a(px - 1, advanced_posY - 4, px + total_width, advanced_posY + 12, (new Color(0.0F, 0.0F, 0.0F, opacity)).getRGB());
            mc.field_71466_p.func_175065_a(name, (float)px, (float)advanced_posY, color, true);
            px += mc.field_71466_p.func_78256_a(name);
            if(scale != 1.0F) {
               GlStateManager.func_179094_E();
               GlStateManager.func_179152_a(scale, scale, scale);
            }

            mc.func_175599_af().func_175042_a(itemStack, (int)(((double)px + itemStackOffsetX) / (double)scale), (int)(((double)advanced_posY + itemStackOffsetY) / (double)scale));
            if(scale != 1.0F) {
               GlStateManager.func_179121_F();
            }

            px += itemStackWidth;
            px += padding;
            mc.field_71466_p.func_175065_a(time, (float)(px - mc.field_71466_p.func_78256_a(time) / 2), (float)advanced_posY, color, true);
            return;
         }
      }

   }

   public void drawTimeline(int game_time, int[] times, int screen_width, int screen_height) {
      byte margin_x = 40;
      int margin_y = 12;
      byte offset_from_right = 20;
      int tm_width = (int)((float)(screen_width * timeline_width_percentage) / 100.0F) - margin_x;
      tm_width = tm_width * 2 / 2;
      byte tm_height = 2;
      int x1 = (screen_width - tm_width) / 2;
      tm_width += -offset_from_right;
      int x2 = x1 + tm_width;
      List dragons = mc.field_71441_e.func_175644_a(EntityDragon.class, EntitySelectors.field_94557_a);
      int y1;
      if(dragons != null && dragons.size() > 0) {
         margin_y += 17;
         y1 = margin_y + 20;
         Iterator y2 = dragons.iterator();

         while(y2.hasNext()) {
            EntityDragon anchor_times = (EntityDragon)y2.next();
            if(anchor_times != null && anchor_times.func_145748_c_() != null) {
               String scaling_factor = "" + (int)(anchor_times.func_110143_aJ() / 2.0F) + "%";
               mc.field_71466_p.func_78276_b(anchor_times.func_145748_c_().func_150254_d() + "" + EnumChatFormatting.GRAY + " ▸ " + EnumChatFormatting.RED + scaling_factor, x1, y1, (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB());
               y1 += 10;
            }
         }
      }

      y1 = margin_y;
      int var38 = margin_y + tm_height;
      Gui.func_73734_a(x1, margin_y, x2, var38, (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB());
      int[] var39 = new int[times.length + 2];

      for(int var40 = 0; var40 < times.length; ++var40) {
         var39[var40 + 1] = times[var40];
      }

      var39[0] = 0;
      var39[var39.length - 1] = 4000;
      float var41 = (float)tm_width * 1.0F / (float)times[times.length - 1];
      byte stick_height = 10;
      byte stick_width = 2;
      int color_lightblue = (new Color(0.0F, 1.0F, 1.0F, 1.0F)).getRGB();
      int color_green = (new Color(0.0F, 1.0F, 0.0F, 1.0F)).getRGB();
      int color_purple = (new Color(0.69803923F, 0.32941177F, 1.0F, 1.0F)).getRGB();
      int color_white = (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB();
      int color_black = (new Color(0.0F, 0.0F, 0.0F, 1.0F)).getRGB();

      int seconds;
      for(int scale = 1; scale < var39.length; ++scale) {
         seconds = x1 + (int)(var41 * (float)var39[scale - 1]);
         int text_time = x1 + (int)(var41 * (float)var39[scale]);
         String width = "-";
         ItemStack scale1 = new ItemStack(Blocks.field_150348_b);
         int color = color_black;
         if(scale == 1) {
            color = color_white;
            width = "";
            scale1 = null;
         } else if(scale == 2) {
            color = color_lightblue;
            width = "I";
            scale1 = new ItemStack(Items.field_151045_i);
         } else if(scale == 3) {
            color = color_lightblue;
            width = "II";
            scale1 = new ItemStack(Items.field_151045_i);
         } else if(scale == 4) {
            color = color_green;
            width = "I";
            scale1 = new ItemStack(Items.field_151166_bC);
         } else if(scale == 5) {
            color = color_green;
            width = "II";
            scale1 = new ItemStack(Items.field_151166_bC);
         } else if(scale >= 6) {
            color = color_purple;
            width = "Драконы";
            scale1 = new ItemStack(Item.func_150898_a(Blocks.field_150380_bt));
         }

         Gui.func_73734_a(seconds - stick_width / 2, y1 - stick_height / 2, seconds + stick_width / 2, var38 + stick_height / 2, color);
         int label_time = var39[scale - 1];
         int seconds1 = label_time % 60;
         String text_time1 = label_time / 60 + ":" + (seconds1 >= 10?"":"0") + seconds1;
         float width1 = (float)mc.field_71466_p.func_78256_a(text_time1);
         mc.field_71466_p.func_175065_a(text_time1, (float)seconds - width1 / 2.0F, (float)(var38 + stick_height / 2 + 1), color, true);
         if(scale != 7) {
            Gui.func_73734_a(seconds, y1, text_time, var38, color);
            if(scale1 != null) {
               width1 = (float)mc.field_71466_p.func_78256_a(width);
               int px = (seconds + text_time) / 2 - 4;
               int py = y1 - stick_height / 2 - 5;
               mc.field_71466_p.func_175065_a(width, (float)px - width1 / 2.0F, (float)py, color, true);
               float scale2 = 0.6F;
               GlStateManager.func_179094_E();
               GlStateManager.func_179152_a(scale2, scale2, scale2);
               mc.func_175599_af().func_175042_a(scale1, (int)(((float)px + width1 / 2.0F) / scale2), (int)((float)py / scale2) - 0);
               GlStateManager.func_179121_F();
            }
         }
      }

      if(!isGameTime) {
         game_time = game_time > 0?game_time:0;
         byte var42 = 10;
         seconds = game_time % 60;
         String var44 = game_time / 60 + ":" + (seconds >= 10?"":"0") + seconds;
         float var45 = (float)mc.field_71466_p.func_78256_a(var44);
         mc.field_71466_p.func_175065_a(var44, (float)(x2 + var42 + 7), (float)(var38 - 4), (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB(), true);
         float var46 = 0.8F;
         mc.field_71446_o.func_110577_a(this.resourceLoc_textures);
         GlStateManager.func_179094_E();
         GlStateManager.func_179141_d();
         GlStateManager.func_179109_b((float)(x2 + var42), (float)var38, 0.0F);
         GlStateManager.func_179152_a(var46, var46, var46);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(-6, -6, 0, 0, 12, 12);
         GlStateManager.func_179121_F();
      }

      mc.field_71446_o.func_110577_a(this.resourceLoc_textures);
      float var43 = 0.5F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)x1 + (float)game_time * var41, (float)((y1 + var38) / 2), 0.0F);
      GlStateManager.func_179152_a(var43, var43, var43);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_73729_b(-8, -8, 12, 0, 16, 16);
      GlStateManager.func_179121_F();
   }

}
