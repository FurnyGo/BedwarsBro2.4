package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.hints.LightningLocator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class LobbyFly {

   static Minecraft mc;
   public static boolean isActive = false;
   public static LightningLocator.MyLightning last_lightning = null;
   public static float speed = 1.0F;


   public LobbyFly() {
      mc = Minecraft.func_71410_x();
      isActive = false;
      speed = 1.0F;
   }

   @SubscribeEvent
   public void playerTick(ClientTickEvent event) {
      if(isActive) {
         if(speed > 1.0F) {
            speed = (float)Math.floor((double)speed);
         }

         double yaw = (double)mc.field_71439_g.field_70177_z;
         double pitch = (double)mc.field_71439_g.field_70125_A - 15.0D;
         if(mc.field_71439_g.field_70125_A == -90.0F) {
            pitch = (double)mc.field_71439_g.field_70125_A;
         }

         double motionX = (double)(-MathHelper.func_76126_a((float)(yaw * 0.01745329238474369D)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369D)));
         double motionY = (double)(-MathHelper.func_76126_a((float)(pitch * 0.01745329238474369D)));
         double motionZ = (double)(MathHelper.func_76134_b((float)(yaw * 0.01745329238474369D)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369D)));
         mc.field_71439_g.func_70016_h(motionX * (double)speed, motionY * (double)speed, motionZ * (double)speed);
      } else {
         speed = 1.0F;
      }

   }

}
