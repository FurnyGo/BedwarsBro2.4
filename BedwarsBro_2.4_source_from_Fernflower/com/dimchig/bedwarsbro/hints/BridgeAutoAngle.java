package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class BridgeAutoAngle {

   public static float rotation_godbridge_pitch = 75.0F;
   public static boolean prop_show_chat_messages = true;
   public static String mod_prefix = "&6&lAutoAngle: &r";


   public static void aim() {
      rotation_godbridge_pitch = (float)Main.getConfigDouble(Main.CONFIG_MSG.BRIDGE_AUTOANGLE_PITCH);
      prop_show_chat_messages = Main.getConfigBool(Main.CONFIG_MSG.BRIDGE_AUTOANGLE_MESSAGES);
      EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
      if(Math.abs(player.field_70125_A - rotation_godbridge_pitch) > 10.0F) {
         if(prop_show_chat_messages) {
            ChatSender.addText(mod_prefix + "&cПосмотри на угол блока!");
         }

      } else {
         float[] directions = new float[]{45.0F, -45.0F, 135.0F, -135.0F, 225.0F, -225.0F, 315.0F, -315.0F};
         float min_dist = 1000.0F;
         float direction = 0.0F;
         float[] yaw = directions;
         int var5 = directions.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            float dir = yaw[var6];
            float dist = Math.abs(getPlayerYaw(player) - dir);
            if(dist < min_dist) {
               min_dist = dist;
               direction = dir;
            }
         }

         if(direction != 0.0F || !prop_show_chat_messages) {
            setPlayerPitchAndYaw(player, direction, rotation_godbridge_pitch);
            if(prop_show_chat_messages) {
               int var9 = (int)direction % 180;
               ChatSender.addText(mod_prefix + "&fУгол установлен в (&b" + var9 + ", " + rotation_godbridge_pitch + "&f)");
            }

         }
      }
   }

   public static float getPlayerYaw(Entity player) {
      return player.field_70177_z % 360.0F;
   }

   public static void setPlayerPitchAndYaw(Entity player, float target_angle_yaw, float target_angle_pitch) {
      float prev_rot_yaw = player.field_70177_z;
      float prev_rot_pitch = player.field_70125_A;
      float angle_yaw = target_angle_yaw - prev_rot_yaw;
      float angle_pitch = target_angle_pitch - prev_rot_pitch;
      rotateAngles(player, angle_yaw, angle_pitch);
      double delta_yaw = (double)(player.field_70177_z - prev_rot_yaw);
      double delta_pitch = (double)(player.field_70125_A - prev_rot_pitch);
      if(target_angle_yaw != player.field_70177_z || target_angle_pitch != player.field_70125_A) {
         setPlayerPitchAndYaw(player, target_angle_yaw, target_angle_pitch);
      }

   }

   public static void rotateAngles(Entity player, float angle_yaw, float angle_pitch) {
      player.func_70082_c(angle_yaw / 0.15F, angle_pitch / -0.15F);
   }

}
