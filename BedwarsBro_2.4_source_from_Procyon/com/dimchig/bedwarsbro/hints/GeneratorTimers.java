// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.entity.item.EntityArmorStand;
import java.util.Date;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GeneratorTimers extends Gui
{
    static Minecraft mc;
    static boolean isActive;
    static int start_diamond_time;
    static long time_last_diamond;
    static int time_diamond_max;
    static int time_diamond_buffer;
    static int start_emerald_time;
    static long time_last_emerald;
    static int time_emerald_max;
    static int time_emerald_buffer;
    static long time_game_start;
    static int corner_position;
    static boolean isAdvanced;
    static boolean isGameTime;
    static boolean isTimeline;
    static int timeline_width_percentage;
    ResourceLocation resourceLoc_textures;
    
    public GeneratorTimers() {
        GeneratorTimers.mc = Minecraft.func_71410_x();
        this.resourceLoc_textures = new ResourceLocation("bedwarsbro:textures/gui/timeline_icons.png");
    }
    
    public void updateBooleans() {
        GeneratorTimers.isActive = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS);
        GeneratorTimers.corner_position = Main.getConfigInt(Main.CONFIG_MSG.GENERATOR_TIMERS_POSITION);
        GeneratorTimers.isAdvanced = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS_ADVANCED);
        GeneratorTimers.isTimeline = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS_TIMELINE);
        GeneratorTimers.isGameTime = Main.getConfigBool(Main.CONFIG_MSG.GENERATOR_TIMERS_GAME_TIME);
        GeneratorTimers.timeline_width_percentage = Main.getConfigInt(Main.CONFIG_MSG.GENERATOR_TIMERS_TIMELINE_WIDTH);
    }
    
    public void setMaxTimeDiamonds(final int new_time) {
        GeneratorTimers.time_diamond_buffer = new_time;
    }
    
    public void setMaxTimeEmeralds(final int new_time) {
        GeneratorTimers.time_emerald_buffer = new_time;
    }
    
    public void setHardTimeDiamonds(final int hard_time) {
        GeneratorTimers.time_last_diamond = new Date().getTime() - (GeneratorTimers.time_diamond_max - hard_time + 1) * 1000;
    }
    
    public void setHardTimeEmeralds(final int hard_time) {
        GeneratorTimers.time_last_emerald = new Date().getTime() - (GeneratorTimers.time_emerald_max - hard_time + 1) * 1000;
    }
    
    public void onTick() {
        try {
            final List<EntityArmorStand> entities = (List<EntityArmorStand>)GeneratorTimers.mc.field_71441_e.func_175644_a((Class)EntityArmorStand.class, EntitySelectors.field_94557_a);
            if (entities != null) {
                boolean isDiamondGenSet = false;
                boolean isEmeraldGenSet = false;
                for (final EntityArmorStand en : entities) {
                    if (en != null) {
                        if (en.func_145748_c_() == null) {
                            continue;
                        }
                        final String name = en.func_145748_c_().func_150260_c();
                        if (!name.contains("\u0435\u043d\u0435\u0440\u0430\u0442\u043e\u0440")) {
                            continue;
                        }
                        String connected_stand_text = null;
                        final BlockPos minPos = new BlockPos(en.field_70165_t - 1.0, en.field_70163_u - 1.0, en.field_70161_v - 1.0);
                        final BlockPos maxPos = new BlockPos(en.field_70165_t + 1.0, en.field_70163_u + 1.0, en.field_70161_v + 1.0);
                        final AxisAlignedBB box = new AxisAlignedBB(minPos, maxPos);
                        final List<EntityArmorStand> armorStands = (List<EntityArmorStand>)GeneratorTimers.mc.field_71441_e.func_72872_a((Class)EntityArmorStand.class, box);
                        if (armorStands == null) {
                            continue;
                        }
                        for (final EntityArmorStand en2 : armorStands) {
                            if (en2 != null) {
                                if (en2.func_145748_c_() == null) {
                                    continue;
                                }
                                final String en2_name = en2.func_145748_c_().func_150254_d();
                                if (en2_name.contains("\u0447\u0435\u0440\u0435\u0437")) {
                                    connected_stand_text = en2_name;
                                    break;
                                }
                                continue;
                            }
                        }
                        if (connected_stand_text == null) {
                            continue;
                        }
                        int hard_time = -1;
                        try {
                            hard_time = Integer.parseInt(connected_stand_text.split("§c")[1].trim().split(" ")[0].trim());
                        }
                        catch (Exception ex2) {}
                        if (hard_time == -1) {
                            continue;
                        }
                        if (!isDiamondGenSet && name.contains("\u0430\u043b\u043c\u0430\u0437")) {
                            Main.generatorTimers.setHardTimeDiamonds(hard_time);
                            isDiamondGenSet = true;
                        }
                        else {
                            if (isEmeraldGenSet || !name.contains("\u0438\u0437\u0443\u043c\u0440")) {
                                continue;
                            }
                            Main.generatorTimers.setHardTimeEmeralds(hard_time);
                            isEmeraldGenSet = true;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setStartTimesOnGameStart() {
        GeneratorTimers.start_diamond_time = 30;
        final long t = GeneratorTimers.time_last_diamond = new Date().getTime();
        GeneratorTimers.time_diamond_max = GeneratorTimers.start_diamond_time;
        GeneratorTimers.time_diamond_buffer = GeneratorTimers.start_diamond_time;
        GeneratorTimers.time_last_emerald = t;
        GeneratorTimers.time_emerald_max = GeneratorTimers.start_emerald_time;
        GeneratorTimers.time_emerald_buffer = GeneratorTimers.start_emerald_time;
        GeneratorTimers.time_game_start = t;
    }
    
    public void draw(final int screen_width, final int screen_height) {
        if (GeneratorTimers.isActive) {
            final MyChatListener chatListener = Main.chatListener;
            if (MyChatListener.IS_IN_GAME) {
                int gameTime_posX;
                int gameTime_posY;
                int padding = gameTime_posY = (gameTime_posX = 6);
                if (GeneratorTimers.corner_position == 1) {
                    gameTime_posX = 2;
                    gameTime_posY = padding;
                }
                else if (GeneratorTimers.corner_position == 2) {
                    gameTime_posX = screen_width - 23 - padding;
                    gameTime_posY = padding;
                }
                else if (GeneratorTimers.corner_position == 3) {
                    gameTime_posX = screen_width - 23 - padding;
                    gameTime_posY = screen_height - 24 - padding + (GeneratorTimers.isAdvanced ? -16 : 0) + (GeneratorTimers.isGameTime ? -16 : 0) - 18;
                }
                else {
                    gameTime_posX = 2;
                    gameTime_posY = screen_height - 24 - padding + (GeneratorTimers.isAdvanced ? -16 : 0) + (GeneratorTimers.isGameTime ? -16 : 0);
                }
                final float opacity = 0.1f;
                final long t = new Date().getTime();
                final boolean isAliningToLeft = GeneratorTimers.corner_position == 1 || GeneratorTimers.corner_position == 4;
                if (GeneratorTimers.isGameTime) {
                    final int game_time = Math.max((int)((t - GeneratorTimers.time_game_start) / 1000.0f) - 1, 0);
                    final int seconds = game_time % 60;
                    final String text_gameTime = game_time / 60 + ":" + ((seconds >= 10) ? "" : "0") + seconds;
                    final int total_width = GeneratorTimers.mc.field_71466_p.func_78256_a(text_gameTime) + padding + GeneratorTimers.mc.field_71466_p.func_78256_a(text_gameTime) / 2;
                    int px = gameTime_posX + 1;
                    if (!isAliningToLeft) {
                        px = screen_width - total_width - 3;
                    }
                    Gui.func_73734_a(px - 1, gameTime_posY - 4, px + total_width, gameTime_posY + 12, new Color(0.0f, 0.0f, 0.0f, opacity).getRGB());
                    final int color_gameTime = new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB();
                    final float scale_clock = 0.8f;
                    GeneratorTimers.mc.field_71446_o.func_110577_a(this.resourceLoc_textures);
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179141_d();
                    GlStateManager.func_179109_b((float)(px + 7), (float)(gameTime_posY + 3), 0.0f);
                    GlStateManager.func_179152_a(scale_clock, scale_clock, scale_clock);
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    this.func_73729_b(-6, -6, 0, 0, 12, 12);
                    GlStateManager.func_179121_F();
                    GeneratorTimers.mc.field_71466_p.func_175065_a(text_gameTime, (float)(px + 15), (float)gameTime_posY, color_gameTime, true);
                }
                final int diamonds_posX = gameTime_posX;
                final int diamonds_posY = gameTime_posY + (GeneratorTimers.isGameTime ? 16 : 0);
                final int time_diamonds = (int)((t - GeneratorTimers.time_last_diamond) / 1000.0f);
                if (time_diamonds > GeneratorTimers.time_diamond_max) {
                    GeneratorTimers.time_last_diamond = t;
                    GeneratorTimers.time_diamond_max = GeneratorTimers.time_diamond_buffer;
                }
                final int time_diamonds_diff = GeneratorTimers.time_diamond_max - time_diamonds + 1;
                String text_diamonds = "" + time_diamonds_diff;
                int color_diamonds = new Color(0.0f, 1.0f, 1.0f, 1.0f).getRGB();
                if (time_diamonds_diff > GeneratorTimers.time_diamond_max) {
                    text_diamonds = "0";
                    color_diamonds = new Color(1.0f, 0.0f, 0.0f, 1.0f).getRGB();
                }
                Gui.func_73734_a(diamonds_posX, diamonds_posY - 4, diamonds_posX + 26, diamonds_posY + 12, new Color(0.0f, 0.0f, 0.0f, opacity).getRGB());
                GeneratorTimers.mc.func_175599_af().func_175042_a(new ItemStack(Items.field_151045_i), diamonds_posX - 2, diamonds_posY - 5);
                GeneratorTimers.mc.field_71466_p.func_175065_a(text_diamonds, (float)(diamonds_posX + 19 - GeneratorTimers.mc.field_71466_p.func_78256_a(text_diamonds) / 2), (float)diamonds_posY, color_diamonds, true);
                final int time_emeralds = (int)((t - GeneratorTimers.time_last_emerald) / 1000.0f);
                if (time_emeralds > GeneratorTimers.time_emerald_max) {
                    GeneratorTimers.time_last_emerald = t;
                    GeneratorTimers.time_emerald_max = GeneratorTimers.time_emerald_buffer;
                }
                final int time_emeralds_diff = GeneratorTimers.time_emerald_max - time_emeralds + 1;
                String text_emeralds = "" + time_emeralds_diff;
                int color_emeralds = new Color(0.0f, 1.0f, 0.0f, 1.0f).getRGB();
                if (time_emeralds_diff > GeneratorTimers.time_emerald_max) {
                    text_emeralds = "0";
                    color_emeralds = new Color(1.0f, 0.0f, 0.0f, 1.0f).getRGB();
                }
                final int emeralds_posX = diamonds_posX;
                final int emeralds_posY = diamonds_posY + 16;
                Gui.func_73734_a(emeralds_posX, emeralds_posY - 4, emeralds_posX + 26, emeralds_posY + 12, new Color(0.0f, 0.0f, 0.0f, opacity).getRGB());
                GeneratorTimers.mc.func_175599_af().func_175042_a(new ItemStack(Items.field_151166_bC), emeralds_posX - 2, emeralds_posY - 4);
                GeneratorTimers.mc.field_71466_p.func_175065_a(text_emeralds, (float)(emeralds_posX + 19 - GeneratorTimers.mc.field_71466_p.func_78256_a(text_emeralds) / 2), (float)emeralds_posY, color_emeralds, true);
                final int[] times = { 300, 450, 900, 1400, 2400, 3000 };
                final int diff = (int)((t - GeneratorTimers.time_game_start) / 1000.0f) - 1;
                String name = "";
                if (GeneratorTimers.isTimeline) {
                    this.drawTimeline(diff, times, screen_width, screen_height);
                }
                if (!GeneratorTimers.isAdvanced) {
                    return;
                }
                int nearest_upgrate_time = -1;
                for (int i = 0; i < times.length; ++i) {
                    if (diff < times[i]) {
                        nearest_upgrate_time = times[i] - diff - 1;
                        break;
                    }
                }
                if (nearest_upgrate_time == -1) {
                    return;
                }
                final int seconds2 = nearest_upgrate_time % 60;
                final String time = nearest_upgrate_time / 60 + ":" + ((seconds2 >= 10) ? "" : "0") + seconds2;
                int color = new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB();
                int advanced_posX = diamonds_posX;
                if (isAliningToLeft) {
                    advanced_posX = diamonds_posX;
                }
                final int advanced_posY = emeralds_posY + 16;
                final int offsetX = 0;
                ItemStack itemStack = new ItemStack(Items.field_151126_ay);
                double itemStackOffsetX = 0.0;
                double itemStackOffsetY = 0.0;
                int itemStackWidth = 0;
                float scale = 1.0f;
                if (diff < times[0]) {
                    name = "I";
                    color = new Color(0.0f, 1.0f, 1.0f, 1.0f).getRGB();
                    itemStack = new ItemStack(Items.field_151045_i);
                    itemStackOffsetX = -2.0;
                    itemStackOffsetY = -5.0;
                    itemStackWidth = 12;
                }
                else if (diff < times[1]) {
                    name = "II";
                    color = new Color(0.0f, 1.0f, 1.0f, 1.0f).getRGB();
                    itemStack = new ItemStack(Items.field_151045_i);
                    itemStackOffsetX = -2.0;
                    itemStackOffsetY = -5.0;
                    itemStackWidth = 12;
                }
                else if (diff < times[2]) {
                    name = "I";
                    color = new Color(0.0f, 1.0f, 0.0f, 1.0f).getRGB();
                    itemStack = new ItemStack(Items.field_151166_bC);
                    itemStackOffsetX = -2.0;
                    itemStackOffsetY = -4.0;
                    itemStackWidth = 11;
                }
                else if (diff < times[3]) {
                    name = "II";
                    color = new Color(0.0f, 1.0f, 0.0f, 1.0f).getRGB();
                    itemStack = new ItemStack(Items.field_151166_bC);
                    itemStackOffsetX = -2.0;
                    itemStackOffsetY = -4.0;
                    itemStackWidth = 11;
                }
                else if (diff < times[4]) {
                    name = "\u0411\u0435\u0437 \u043a\u0440\u043e\u0432\u0430\u0442\u0435\u0439";
                    color = new Color(1.0f, 0.0f, 0.0f, 1.0f).getRGB();
                    itemStack = new ItemStack(Items.field_151104_aV);
                    itemStackOffsetX = 0.0;
                    itemStackOffsetY = -5.0;
                    itemStackWidth = 15;
                }
                else if (diff < times[5]) {
                    name = "\u041a\u043e\u043d\u0435\u0446 \u0438\u0433\u0440\u044b";
                    color = new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB();
                    itemStack = new ItemStack(Item.func_150898_a(Blocks.field_180401_cv));
                    itemStackOffsetX = 1.0;
                    itemStackOffsetY = -2.5;
                    itemStackWidth = 10;
                    scale = 0.8f;
                }
                padding = 15;
                final int total_width2 = GeneratorTimers.mc.field_71466_p.func_78256_a(name) + itemStackWidth + padding + GeneratorTimers.mc.field_71466_p.func_78256_a(time) / 2;
                int px2 = advanced_posX + 1;
                if (!isAliningToLeft) {
                    px2 = screen_width - total_width2 - 3;
                }
                Gui.func_73734_a(px2 - 1, advanced_posY - 4, px2 + total_width2, advanced_posY + 12, new Color(0.0f, 0.0f, 0.0f, opacity).getRGB());
                GeneratorTimers.mc.field_71466_p.func_175065_a(name, (float)px2, (float)advanced_posY, color, true);
                px2 += GeneratorTimers.mc.field_71466_p.func_78256_a(name);
                if (scale != 1.0f) {
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179152_a(scale, scale, scale);
                }
                GeneratorTimers.mc.func_175599_af().func_175042_a(itemStack, (int)((px2 + itemStackOffsetX) / scale), (int)((advanced_posY + itemStackOffsetY) / scale));
                if (scale != 1.0f) {
                    GlStateManager.func_179121_F();
                }
                px2 += itemStackWidth;
                px2 += padding;
                GeneratorTimers.mc.field_71466_p.func_175065_a(time, (float)(px2 - GeneratorTimers.mc.field_71466_p.func_78256_a(time) / 2), (float)advanced_posY, color, true);
            }
        }
    }
    
    public void drawTimeline(int game_time, final int[] times, final int screen_width, final int screen_height) {
        final int margin_x = 40;
        int margin_y = 12;
        final int offset_from_right = 20;
        int tm_width = (int)(screen_width * GeneratorTimers.timeline_width_percentage / 100.0f) - margin_x;
        tm_width = tm_width * 2 / 2;
        final int tm_height = 2;
        final int x1 = (screen_width - tm_width) / 2;
        tm_width += -offset_from_right;
        final int x2 = x1 + tm_width;
        final List<EntityDragon> dragons = (List<EntityDragon>)GeneratorTimers.mc.field_71441_e.func_175644_a((Class)EntityDragon.class, EntitySelectors.field_94557_a);
        if (dragons != null && dragons.size() > 0) {
            margin_y += 17;
            int py = margin_y + 20;
            for (final EntityDragon dragon : dragons) {
                if (dragon != null) {
                    if (dragon.func_145748_c_() == null) {
                        continue;
                    }
                    final String health_s = "" + (int)(dragon.func_110143_aJ() / 2.0f) + "%";
                    GeneratorTimers.mc.field_71466_p.func_78276_b(dragon.func_145748_c_().func_150254_d() + "" + EnumChatFormatting.GRAY + " \u25b8 " + EnumChatFormatting.RED + health_s, x1, py, new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB());
                    py += 10;
                }
            }
        }
        final int y1 = margin_y;
        final int y2 = y1 + tm_height;
        Gui.func_73734_a(x1, y1, x2, y2, new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB());
        final int[] anchor_times = new int[times.length + 2];
        for (int i = 0; i < times.length; ++i) {
            anchor_times[i + 1] = times[i];
        }
        anchor_times[0] = 0;
        anchor_times[anchor_times.length - 1] = 4000;
        final float scaling_factor = tm_width * 1.0f / times[times.length - 1];
        final int stick_height = 10;
        final int stick_width = 2;
        final int color_lightblue = new Color(0.0f, 1.0f, 1.0f, 1.0f).getRGB();
        final int color_green = new Color(0.0f, 1.0f, 0.0f, 1.0f).getRGB();
        final int color_purple = new Color(0.69803923f, 0.32941177f, 1.0f, 1.0f).getRGB();
        final int color_white = new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB();
        final int color_black = new Color(0.0f, 0.0f, 0.0f, 1.0f).getRGB();
        for (int j = 1; j < anchor_times.length; ++j) {
            final int tx1 = x1 + (int)(scaling_factor * anchor_times[j - 1]);
            final int tx2 = x1 + (int)(scaling_factor * anchor_times[j]);
            String text_name = "-";
            ItemStack itemStack = new ItemStack(Blocks.field_150348_b);
            int color = color_black;
            if (j == 1) {
                color = color_white;
                text_name = "";
                itemStack = null;
            }
            else if (j == 2) {
                color = color_lightblue;
                text_name = "I";
                itemStack = new ItemStack(Items.field_151045_i);
            }
            else if (j == 3) {
                color = color_lightblue;
                text_name = "II";
                itemStack = new ItemStack(Items.field_151045_i);
            }
            else if (j == 4) {
                color = color_green;
                text_name = "I";
                itemStack = new ItemStack(Items.field_151166_bC);
            }
            else if (j == 5) {
                color = color_green;
                text_name = "II";
                itemStack = new ItemStack(Items.field_151166_bC);
            }
            else if (j >= 6) {
                color = color_purple;
                text_name = "\u0414\u0440\u0430\u043a\u043e\u043d\u044b";
                itemStack = new ItemStack(Item.func_150898_a(Blocks.field_150380_bt));
            }
            Gui.func_73734_a(tx1 - stick_width / 2, y1 - stick_height / 2, tx1 + stick_width / 2, y2 + stick_height / 2, color);
            final int label_time = anchor_times[j - 1];
            final int seconds = label_time % 60;
            final String text_time = label_time / 60 + ":" + ((seconds >= 10) ? "" : "0") + seconds;
            float width = (float)GeneratorTimers.mc.field_71466_p.func_78256_a(text_time);
            GeneratorTimers.mc.field_71466_p.func_175065_a(text_time, tx1 - width / 2.0f, (float)(y2 + stick_height / 2 + 1), color, true);
            if (j != 7) {
                Gui.func_73734_a(tx1, y1, tx2, y2, color);
                if (itemStack != null) {
                    width = (float)GeneratorTimers.mc.field_71466_p.func_78256_a(text_name);
                    final int px = (tx1 + tx2) / 2 - 4;
                    final int py2 = y1 - stick_height / 2 - 5;
                    GeneratorTimers.mc.field_71466_p.func_175065_a(text_name, px - width / 2.0f, (float)py2, color, true);
                    final float scale = 0.6f;
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179152_a(scale, scale, scale);
                    GeneratorTimers.mc.func_175599_af().func_175042_a(itemStack, (int)((px + width / 2.0f) / scale), (int)(py2 / scale) - 0);
                    GlStateManager.func_179121_F();
                }
            }
        }
        if (!GeneratorTimers.isGameTime) {
            game_time = ((game_time > 0) ? game_time : 0);
            final int offsetX = 10;
            final int seconds2 = game_time % 60;
            final String text_time2 = game_time / 60 + ":" + ((seconds2 >= 10) ? "" : "0") + seconds2;
            final float width2 = (float)GeneratorTimers.mc.field_71466_p.func_78256_a(text_time2);
            GeneratorTimers.mc.field_71466_p.func_175065_a(text_time2, (float)(x2 + offsetX + 7), (float)(y2 - 4), new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB(), true);
            final float scale2 = 0.8f;
            GeneratorTimers.mc.field_71446_o.func_110577_a(this.resourceLoc_textures);
            GlStateManager.func_179094_E();
            GlStateManager.func_179141_d();
            GlStateManager.func_179109_b((float)(x2 + offsetX), (float)y2, 0.0f);
            GlStateManager.func_179152_a(scale2, scale2, scale2);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_73729_b(-6, -6, 0, 0, 12, 12);
            GlStateManager.func_179121_F();
        }
        GeneratorTimers.mc.field_71446_o.func_110577_a(this.resourceLoc_textures);
        final float scale3 = 0.5f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(x1 + game_time * scaling_factor, (float)((y1 + y2) / 2), 0.0f);
        GlStateManager.func_179152_a(scale3, scale3, scale3);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_73729_b(-8, -8, 12, 0, 16, 16);
        GlStateManager.func_179121_F();
    }
    
    static {
        GeneratorTimers.isActive = false;
        GeneratorTimers.start_diamond_time = 30;
        GeneratorTimers.time_last_diamond = 0L;
        GeneratorTimers.time_diamond_max = GeneratorTimers.start_diamond_time;
        GeneratorTimers.time_diamond_buffer = GeneratorTimers.time_diamond_max;
        GeneratorTimers.start_emerald_time = 65;
        GeneratorTimers.time_last_emerald = 0L;
        GeneratorTimers.time_emerald_max = GeneratorTimers.start_emerald_time;
        GeneratorTimers.time_emerald_buffer = GeneratorTimers.time_emerald_max;
        GeneratorTimers.time_game_start = 0L;
        GeneratorTimers.corner_position = 2;
        GeneratorTimers.isAdvanced = false;
        GeneratorTimers.isGameTime = false;
        GeneratorTimers.isTimeline = false;
        GeneratorTimers.timeline_width_percentage = 80;
    }
}
