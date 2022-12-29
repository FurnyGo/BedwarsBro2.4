// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.util.MovingObjectPosition;
import java.util.Iterator;
import net.minecraft.world.World;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.util.Vec3;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;
import java.util.Date;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.ArrayList;

public class DangerAlert
{
    private static int danger_zone_radius;
    private static int max_ray_distance;
    private static long prev_sound_time;
    private static long sound_freq;
    private static long prev_message_time;
    private static long message_freq;
    
    public void scan(final ArrayList<HintsPlayerScanner.BWPlayer> players, final EntityPlayerSP mod_player) {
        final long t = new Date().getTime();
        final World world = (World)Minecraft.func_71410_x().field_71441_e;
        Main.playerFocus.clearLines();
        for (final HintsPlayerScanner.BWPlayer p : players) {
            if (p.en.func_70005_c_().equals(mod_player.func_70005_c_())) {
                continue;
            }
            if (mod_player.func_96124_cp() == p.en.func_96124_cp()) {
                continue;
            }
            if (p.item_in_hand == null) {
                continue;
            }
            if (p.item_in_hand.type != BWItemsHandler.BWItemType.BOW && p.item_in_hand.type != BWItemsHandler.BWItemType.FIREBALL) {
                continue;
            }
            MovingObjectPosition ray = null;
            for (int i = 1; i < DangerAlert.max_ray_distance; ++i) {
                ray = p.en.func_174822_a((double)i, 1.0f);
                if (ray != null) {
                    final boolean isInDanger = this.isPlayerInDangerZone(mod_player, ray.field_72307_f.field_72450_a, ray.field_72307_f.field_72448_b, ray.field_72307_f.field_72449_c);
                    if (isInDanger) {
                        if (GuiPlayerFocus.STATE) {
                            final Vec3 p2 = null;
                            final Vec3 p3 = new Vec3(p.en.field_70165_t, p.en.field_70163_u + p.en.eyeHeight, p.en.field_70161_v);
                            final GuiPlayerFocus playerFocus = Main.playerFocus;
                            final Vec3 pos1 = p2;
                            final Vec3 pos2 = p3;
                            final GuiPlayerFocus playerFocus2 = Main.playerFocus;
                            final MyChatListener chatListener = Main.chatListener;
                            playerFocus.addLine(pos1, pos2, playerFocus2.getColorByTeam(MyChatListener.getEntityTeamColor(p.en)));
                        }
                        if (t - DangerAlert.prev_sound_time > DangerAlert.sound_freq && Main.getConfigBool(Main.CONFIG_MSG.DANGER_ALERT_SOUND)) {
                            DangerAlert.prev_sound_time = t;
                            final float volume = mod_player.func_70032_d((Entity)p.en) / 12.0f;
                            world.func_72980_b(p.en.field_70165_t, p.en.field_70163_u + p.en.eyeHeight, p.en.field_70161_v, "note.pling", volume, 1.0f, false);
                        }
                        if (t - DangerAlert.prev_message_time <= DangerAlert.message_freq) {
                            break;
                        }
                        DangerAlert.prev_message_time = t;
                        if (p.item_in_hand.type == BWItemsHandler.BWItemType.BOW) {
                            ChatSender.addText(MyChatListener.PREFIX_DANGER_ALERT + "&f\u041d\u0430 \u0442\u0435\u0431\u044f \u0446\u0435\u043b\u044f\u0442\u0441\u044f \u0438\u0437 &c\u041b\u0423\u041a\u0410");
                            break;
                        }
                        if (p.item_in_hand.type == BWItemsHandler.BWItemType.FIREBALL) {
                            ChatSender.addText(MyChatListener.PREFIX_DANGER_ALERT + "&f\u041d\u0430 \u0442\u0435\u0431\u044f \u0446\u0435\u043b\u044f\u0442\u0441\u044f &6\u0424\u0410\u0415\u0420\u0411\u041e\u041b\u041e\u041c");
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    boolean isPlayerInDangerZone(final EntityPlayerSP mod_player, final double x, final double y, final double z) {
        final double dist = Math.sqrt(Math.pow(mod_player.field_70165_t - x, 2.0) + Math.pow(mod_player.field_70163_u + mod_player.func_70047_e() - y, 2.0) + Math.pow(mod_player.field_70161_v - z, 2.0));
        return dist < DangerAlert.danger_zone_radius;
    }
    
    static {
        DangerAlert.danger_zone_radius = 5;
        DangerAlert.max_ray_distance = 100;
        DangerAlert.prev_sound_time = 0L;
        DangerAlert.sound_freq = 150L;
        DangerAlert.prev_message_time = 0L;
        DangerAlert.message_freq = 3000L;
    }
}
