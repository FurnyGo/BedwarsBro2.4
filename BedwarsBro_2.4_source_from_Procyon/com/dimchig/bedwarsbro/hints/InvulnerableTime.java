// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import java.text.DecimalFormat;
import java.util.Date;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.util.Vec3;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class InvulnerableTime extends Gui
{
    static Minecraft mc;
    public static int INVULNERABLE_TICKS_AMOUNT;
    public static long time_last_sound;
    
    public InvulnerableTime() {
        InvulnerableTime.mc = Minecraft.func_71410_x();
    }
    
    public static void scan(final List<EntityPlayer> players, final Vec3 pos, final float partialTicks, final boolean areSoundsActive) {
        final EntityPlayerSP player = InvulnerableTime.mc.field_71439_g;
        if (players.size() < 2 || player == null) {
            return;
        }
        final BWBed bed = MyChatListener.GAME_BED;
        if (bed != null) {
            final double dist = player.func_70011_f((double)bed.part1_posX, (double)bed.part1_posY, (double)bed.part1_posZ);
            if (dist < 30.0) {
                return;
            }
        }
        for (final EntityPlayer en : players) {
            if (en != player && en.func_96124_cp() != player.func_96124_cp()) {
                if (en.field_70163_u > 100.0) {
                    continue;
                }
                if (en.field_70173_aa > InvulnerableTime.INVULNERABLE_TICKS_AMOUNT) {
                    continue;
                }
                final double dist2 = Math.sqrt(Math.pow(player.field_70165_t - en.field_70165_t, 2.0) + Math.pow(player.field_70161_v - en.field_70161_v, 2.0));
                if (dist2 > 15.0) {
                    continue;
                }
                if (Main.shopManager.findItemInHotbar("\u041d\u0430\u0431\u043b\u044e\u0434\u0435\u043d\u0438\u0435 \u0437\u0430") != -1) {
                    return;
                }
                final double x = en.field_70169_q + (en.field_70165_t - en.field_70169_q) * partialTicks;
                double y = en.field_70167_r + (en.field_70163_u - en.field_70167_r) * partialTicks;
                final double z = en.field_70166_s + (en.field_70161_v - en.field_70166_s) * partialTicks;
                y += en.field_70131_O + 1.3;
                if (areSoundsActive) {
                    final String sound_name = "note.hat";
                    final long t = new Date().getTime();
                    if (t - InvulnerableTime.time_last_sound > 100L) {
                        if (en.field_70173_aa == 2) {
                            InvulnerableTime.mc.field_71441_e.func_72980_b(x, y, z, sound_name, 0.5f, 0.6f, false);
                            InvulnerableTime.time_last_sound = t;
                        }
                        else if (en.field_70173_aa == 20) {
                            InvulnerableTime.mc.field_71441_e.func_72980_b(x, y, z, sound_name, 0.75f, 1.0f, false);
                            InvulnerableTime.time_last_sound = t;
                        }
                        else if (en.field_70173_aa == 40) {
                            InvulnerableTime.mc.field_71441_e.func_72980_b(x, y, z, sound_name, 1.0f, 1.4f, false);
                            InvulnerableTime.time_last_sound = t;
                        }
                    }
                }
                final int ticks_cnt = InvulnerableTime.INVULNERABLE_TICKS_AMOUNT - en.field_70173_aa;
                final String text = new DecimalFormat("0.0").format(ticks_cnt / 20.0f);
                drawText(pos, new Vec3(x, y, z), player, (float)ticks_cnt, text);
            }
        }
    }
    
    static void drawText(final Vec3 pos, final Vec3 text_pos, final EntityPlayerSP player, final float ticks_cnt, final String text) {
        final float green = Math.min((InvulnerableTime.INVULNERABLE_TICKS_AMOUNT - ticks_cnt) / InvulnerableTime.INVULNERABLE_TICKS_AMOUNT, 1.0f);
        final Color color = new Color(1.0f - green, green, 0.0f);
        Main.draw3DText.drawText(pos, text_pos, player, text, color.getRGB());
    }
    
    static {
        InvulnerableTime.INVULNERABLE_TICKS_AMOUNT = 40;
        InvulnerableTime.time_last_sound = -1L;
    }
}
