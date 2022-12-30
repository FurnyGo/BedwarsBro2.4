package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.Main;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class FireballSpread {

   static Minecraft mc;
   public boolean isActive = false;
   public boolean isOffsetActive = false;
   private static double MAIN_CALCULATED_VARIABLE = 0.00115D;
   private static double MAIN_CALCULATED_RADIUS_MAX = 100.0D;
   private static double MAIN_CALCULATED_RADIUS_MID = 50.0D;
   private static double MAIN_CALCULATED_RADIUS_MIN = 25.0D;
   private static Color color_zone_max = new Color(1.0F, 0.0F, 0.0F, 0.2F);
   private static Color color_zone_mid = new Color(1.0F, 1.0F, 0.0F, 0.25F);
   private static Color color_zone_min = new Color(0.0F, 1.0F, 0.0F, 0.3F);
   private static double PI_TIMES_2 = 6.283185307179586D;
   private static double RADIANS_180 = Math.toRadians(180.0D);
   Tessellator tessellator;
   WorldRenderer worldRenderer;


   public FireballSpread() {
      mc = Minecraft.func_71410_x();
      this.updateBooleans();
      this.tessellator = Tessellator.func_178181_a();
      this.worldRenderer = this.tessellator.func_178180_c();
   }

   public void updateBooleans() {
      this.isActive = Main.getConfigBool(Main.CONFIG_MSG.FIREBALL_SPREAD);
      this.isOffsetActive = Main.getConfigBool(Main.CONFIG_MSG.FIREBALL_SPREAD_OFFSET_X);
   }

   @SubscribeEvent
   public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
      if(this.isActive) {
         EntityPlayerSP player = mc.field_71439_g;
         if(mc.field_71474_y.field_74320_O == 0) {
            if(player.func_71045_bC() != null && player.func_71045_bC().func_77973_b() == Items.field_151059_bz) {
               float player_yaw = (player.field_70177_z % 360.0F + 360.0F) % 360.0F;
               float player_pitch = player.field_70125_A;
               GL11.glPushMatrix();
               GL11.glPushAttrib(8192);
               GL11.glDisable(2896);
               GL11.glDisable(3553);
               GL11.glDisable(2929);
               GL11.glLineWidth(3.0F);
               GL11.glRotatef(-player.field_70177_z, 0.0F, 1.0F, 0.0F);
               GL11.glTranslated(0.0D, (double)mc.field_71439_g.func_70047_e(), 0.0D);
               GL11.glRotated((double)mc.field_71439_g.field_70125_A, 1.0D, 0.0D, 0.0D);
               GL11.glTranslated(0.0D, 0.0D, 1.0D);
               GL11.glEnable(3042);
               GL11.glEnable(2884);
               GL11.glBlendFunc(770, 771);
               double r = 0.01D;
               double bx = 0.1D;
               double by = 0.0D;
               double bz = 0.0D;
               boolean sides = true;
               double x = 0.0D;
               double y = 0.0D;
               double z = 0.0D;
               double offset_x = -0.002D;
               double offset_y = 0.0D;
               if(this.isOffsetActive) {
                  offset_x = -0.004D;
                  offset_y = -0.002D;
               }

               this.drawCircle(offset_x, offset_y, 0.0D, 64.0D, MAIN_CALCULATED_RADIUS_MAX * MAIN_CALCULATED_VARIABLE, color_zone_max);
               this.drawCircle(offset_x, offset_y, 0.0D, 32.0D, MAIN_CALCULATED_RADIUS_MID * MAIN_CALCULATED_VARIABLE, color_zone_mid);
               this.drawCircle(offset_x, offset_y, 0.0D, 16.0D, MAIN_CALCULATED_RADIUS_MIN * MAIN_CALCULATED_VARIABLE, color_zone_min);
               GL11.glPopAttrib();
               GL11.glPopMatrix();
            }
         }
      }
   }

   public void drawCircle(double x, double y, double z, double sides, double radius, Color color) {
      GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      this.worldRenderer.func_181668_a(6, DefaultVertexFormats.field_181705_e);
      this.worldRenderer.func_181662_b(x, y, z).func_181675_d();

      for(int i = 0; (double)i <= sides; ++i) {
         double angle = PI_TIMES_2 * (double)i / sides + RADIANS_180;
         this.worldRenderer.func_181662_b(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, z).func_181675_d();
      }

      this.tessellator.func_78381_a();
   }

   private void drawSquare(int range, EntityPlayerSP player, Vec3 playerPos, float partialTicks) {
      float p_yaw = mc.field_71439_g.field_70177_z;
      float p_pitch = mc.field_71439_g.field_70125_A;
      p_yaw = 0.0F;
      p_pitch = 0.0F;
      double posX = 0.0D;
      double posY = (double)(0.0F + mc.field_71439_g.func_70047_e());
      double posZ = 0.0D;
      double yaw = (double)p_yaw;
      double pitch = (double)p_pitch;
      double pitchWithOffset = (double)(p_pitch + 0.0F);
      double var10000 = (double)(-MathHelper.func_76126_a((float)(yaw * 0.01745329238474369D)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369D)));
      double motionY = (double)(-MathHelper.func_76126_a((float)(pitchWithOffset * 0.01745329238474369D)));
      var10000 = (double)(MathHelper.func_76134_b((float)(yaw * 0.01745329238474369D)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369D)));
      double v = (double)range;
      double accelX = mc.field_71441_e.field_73012_v.nextGaussian() * 0.4D;
      double accelY = mc.field_71441_e.field_73012_v.nextGaussian() * 0.4D;
      double accelZ = mc.field_71441_e.field_73012_v.nextGaussian() * 0.4D;
      if(range == 1) {
         accelX = 8.0D;
         accelY = 0.0D;
         accelZ = 0.0D;
         GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
      }

      double d0 = (double)MathHelper.func_76133_a(accelX * accelX + accelY * accelY + accelZ * accelZ);
      double accelerationX = accelX / d0 * 0.1D;
      double accelerationY = accelY / d0 * 0.1D;
      double accelerationZ = accelZ / d0 * 0.1D;
      if(range == 1) {
         accelerationX = 0.0D;
      }

      posX += accelerationX;
      posY += accelerationY;
      ++posZ;
      Vec3 pos = new Vec3(posX, posY, posZ);
      if(pos != null) {
         double hitX = pos.field_72450_a;
         double hitY = pos.field_72448_b;
         double hitZ = pos.field_72449_c;
         double area = 0.005D;
         Main.playerFocus.drawBox(hitX - area, hitY - area, hitZ - area, hitX + area, hitY + area, hitZ + area);
      }

   }

   private double myMap(double value, double leftMin, double leftMax, double rightMin, double rightMax) {
      double leftSpan = leftMax - leftMin;
      double rightSpan = rightMax - rightMin;
      double valueScaled = (value - leftMin) / leftSpan;
      return rightMin + valueScaled * rightSpan;
   }

   private Vec3 getVectorWithPartialTicks(Entity en, float partialTicks) {
      double x = en.field_70169_q + (en.field_70165_t - en.field_70169_q) * (double)partialTicks;
      double y = en.field_70167_r + (en.field_70163_u - en.field_70167_r) * (double)partialTicks;
      double z = en.field_70166_s + (en.field_70161_v - en.field_70166_s) * (double)partialTicks;
      return new Vec3(x, y, z);
   }

}
