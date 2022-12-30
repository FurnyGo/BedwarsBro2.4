package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.hints.BridgeAutoAngle;
import net.minecraft.client.Minecraft;

public class RotateBind {

   static Minecraft mc;
   public boolean isActive = false;
   public int degrees = 180;
   public float speed = 1.0F;
   public double startAngle = 0.0D;
   public int iteration = 0;


   public RotateBind() {
      mc = Minecraft.func_71410_x();
   }

   public void updateBooleans() {
      this.degrees = Main.getConfigInt(Main.CONFIG_MSG.ROTATE_BIND_DEGREES);
      this.speed = (float)Main.getConfigDouble(Main.CONFIG_MSG.ROTATE_BIND_SPEED) * 10.0F;
   }

   public void rotate() {
      if(this.isActive) {
         if((float)this.iteration < this.speed) {
            BridgeAutoAngle.setPlayerPitchAndYaw(mc.field_71439_g, mc.field_71439_g.field_70177_z + (float)this.degrees / this.speed, mc.field_71439_g.field_70125_A);
            ++this.iteration;
         } else {
            BridgeAutoAngle.setPlayerPitchAndYaw(mc.field_71439_g, (float)(this.startAngle + (double)this.degrees), mc.field_71439_g.field_70125_A);
            this.isActive = false;
         }

      }
   }

   public void startRotate() {
      float yaw = mc.field_71439_g.field_70177_z;
      if(this.speed == 0.0F) {
         BridgeAutoAngle.setPlayerPitchAndYaw(mc.field_71439_g, yaw + (float)this.degrees, mc.field_71439_g.field_70125_A);
      } else {
         this.startAngle = (double)yaw;
         this.iteration = 0;
         this.isActive = true;
      }

   }
}
