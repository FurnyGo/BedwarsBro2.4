// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.Main;

public class BridgeAutoAngle
{
    public static float rotation_godbridge_pitch;
    public static boolean prop_show_chat_messages;
    public static String mod_prefix;
    
    public static void aim() {
        BridgeAutoAngle.rotation_godbridge_pitch = (float)Main.getConfigDouble(Main.CONFIG_MSG.BRIDGE_AUTOANGLE_PITCH);
        BridgeAutoAngle.prop_show_chat_messages = Main.getConfigBool(Main.CONFIG_MSG.BRIDGE_AUTOANGLE_MESSAGES);
        final EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        if (Math.abs(player.field_70125_A - BridgeAutoAngle.rotation_godbridge_pitch) > 10.0f) {
            if (BridgeAutoAngle.prop_show_chat_messages) {
                ChatSender.addText(BridgeAutoAngle.mod_prefix + "&c\u041f\u043e\u0441\u043c\u043e\u0442\u0440\u0438 \u043d\u0430 \u0443\u0433\u043e\u043b \u0431\u043b\u043e\u043a\u0430!");
            }
            return;
        }
        final float[] directions = { 45.0f, -45.0f, 135.0f, -135.0f, 225.0f, -225.0f, 315.0f, -315.0f };
        float min_dist = 1000.0f;
        float direction = 0.0f;
        for (final float dir : directions) {
            final float dist = Math.abs(getPlayerYaw((Entity)player) - dir);
            if (dist < min_dist) {
                min_dist = dist;
                direction = dir;
            }
        }
        if (direction == 0.0f && BridgeAutoAngle.prop_show_chat_messages) {
            return;
        }
        setPlayerPitchAndYaw((Entity)player, direction, BridgeAutoAngle.rotation_godbridge_pitch);
        if (BridgeAutoAngle.prop_show_chat_messages) {
            final int yaw = (int)direction % 180;
            ChatSender.addText(BridgeAutoAngle.mod_prefix + "&f\u0423\u0433\u043e\u043b \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d \u0432 (&b" + yaw + ", " + BridgeAutoAngle.rotation_godbridge_pitch + "&f)");
        }
    }
    
    public static float getPlayerYaw(final Entity player) {
        return player.field_70177_z % 360.0f;
    }
    
    public static void setPlayerPitchAndYaw(final Entity player, final float target_angle_yaw, final float target_angle_pitch) {
        final float prev_rot_yaw = player.field_70177_z;
        final float prev_rot_pitch = player.field_70125_A;
        final float angle_yaw = target_angle_yaw - prev_rot_yaw;
        final float angle_pitch = target_angle_pitch - prev_rot_pitch;
        rotateAngles(player, angle_yaw, angle_pitch);
        final double delta_yaw = player.field_70177_z - prev_rot_yaw;
        final double delta_pitch = player.field_70125_A - prev_rot_pitch;
        if (target_angle_yaw != player.field_70177_z || target_angle_pitch != player.field_70125_A) {
            setPlayerPitchAndYaw(player, target_angle_yaw, target_angle_pitch);
        }
    }
    
    public static void rotateAngles(final Entity player, final float angle_yaw, final float angle_pitch) {
        player.func_70082_c(angle_yaw / 0.15f, angle_pitch / -0.15f);
    }
    
    static {
        BridgeAutoAngle.rotation_godbridge_pitch = 75.0f;
        BridgeAutoAngle.prop_show_chat_messages = true;
        BridgeAutoAngle.mod_prefix = "&6&lAutoAngle: &r";
    }
}
