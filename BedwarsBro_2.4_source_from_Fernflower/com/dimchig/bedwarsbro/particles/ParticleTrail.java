package com.dimchig.bedwarsbro.particles;

import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import com.dimchig.bedwarsbro.particles.ParticleController;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class ParticleTrail {

   public static ParticleController particleController = Main.particleController;
   public static boolean isRainbowAlways = false;
   Minecraft mc;


   public ParticleTrail() {
      this.updateBooleans();
      this.mc = Minecraft.func_71410_x();
   }

   public void updateBooleans() {
      isRainbowAlways = HintsValidator.isParticleTrailRainbowActive();
   }

   public void drawPlayerTrail() {
      EntityPlayerSP player = this.mc.field_71439_g;
      if(player.field_70125_A <= 50.0F) {
         double px = player.field_70142_S + 4.0D;
         double py = player.field_70137_T + (double)player.func_70047_e() - (double)this.mc.field_71441_e.field_73012_v.nextFloat() * 0.5D;
         double pz = player.field_70136_U;
         double angle = Math.toRadians((double)(player.field_70177_z - 90.0F));
         double distance = 0.6D;
         px = (double)((float)(distance * Math.cos(angle))) + px - 4.0D;
         pz += (double)((float)(distance * Math.sin(angle)));
         if((player.field_70159_w != 0.0D || player.field_70179_y != 0.0D) && player.field_70181_x >= -2.0D) {
            new Color(0, 0, 0);
            float color_r = 0.0F;
            float color_g = 0.0F;
            float color_b = 0.0F;
            CustomScoreboard.TEAM_COLOR mod_team_color = MyChatListener.getEntityTeamColor(player);
            Color color;
            if(!isRainbowAlways && mod_team_color != CustomScoreboard.TEAM_COLOR.NONE) {
               color = ParticleController.getParticleColorForTeam(mod_team_color);
            } else {
               color = Main.rainbowColorSynchronizer.getColor();
            }

            color_r = (float)color.getRed() / 255.0F;
            color_g = (float)color.getGreen() / 255.0F;
            color_b = (float)color.getBlue() / 255.0F;
            ParticleController var10000 = particleController;
            ParticleController.spawnColorParticle(-1000.0D, 0.05D, px, py, pz, this.mc.field_71441_e.field_73012_v, color_r, color_g, color_b, false);
         }
      }
   }

}
