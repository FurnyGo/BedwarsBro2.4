package com.dimchig.bedwarsbro.particles;

import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import com.dimchig.bedwarsbro.particles.ParticleFinalKillEffect;
import com.dimchig.bedwarsbro.particles.ParticleSharpnessEffect;
import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class ParticleController {

   static boolean isActive = false;


   public void updateBooleans() {
      isActive = HintsValidator.isParticlesActive();
   }

   public ParticleController() {
      this.updateBooleans();
   }

   public static void spawnFinalKillParticles(double posX, double posY, double posZ, CustomScoreboard.TEAM_COLOR team_color) {
      if(isActive) {
         Random rand = new Random();
         short n = 150;
         float motion_randomness = 0.2F;
         float position_randomness = 0.1F;
         Color color = getParticleColorForTeam(team_color);
         float color_r = (float)color.getRed() / 255.0F;
         float color_g = (float)color.getGreen() / 255.0F;
         float color_b = (float)color.getBlue() / 255.0F;

         for(int i = 0; i < n; ++i) {
            spawnColorParticle((double)motion_randomness, (double)position_randomness, posX, posY, posZ, rand, color_r, color_g, color_b, false);
         }

      }
   }

   public static Color getParticleColorForTeam(CustomScoreboard.TEAM_COLOR team_color) {
      return team_color == CustomScoreboard.TEAM_COLOR.RED?new Color(255, 0, 0):(team_color == CustomScoreboard.TEAM_COLOR.YELLOW?new Color(255, 255, 0):(team_color == CustomScoreboard.TEAM_COLOR.GREEN?new Color(0, 255, 0):(team_color == CustomScoreboard.TEAM_COLOR.AQUA?new Color(0, 255, 255):(team_color == CustomScoreboard.TEAM_COLOR.BLUE?new Color(0, 0, 255):(team_color == CustomScoreboard.TEAM_COLOR.PINK?new Color(255, 0, 255):(team_color == CustomScoreboard.TEAM_COLOR.GRAY?new Color(128, 128, 128):(team_color == CustomScoreboard.TEAM_COLOR.WHITE?new Color(255, 255, 255):new Color(0, 0, 0))))))));
   }

   public static void spawnGenDiamondParticles(double posX, double posY, double posZ, int cnt_diamonds) {
      if(isActive) {
         Random rand = new Random();
         int n = 100 + 50 * cnt_diamonds;
         float motion_randomness = 0.2F;
         float position_randomness = 0.0F;
         Color color = new Color(0, 255, 255);
         float color_r = (float)color.getRed() / 255.0F;
         float color_g = (float)color.getGreen() / 255.0F;
         float color_b = (float)color.getBlue() / 255.0F;

         for(int i = 0; i < n; ++i) {
            spawnColorParticle((double)motion_randomness, (double)position_randomness, posX, posY, posZ, rand, color_r, color_g, color_b, true);
         }

      }
   }

   public static void spawnGenEmeraldParticles(double posX, double posY, double posZ, int cnt_emeralds) {
      if(isActive) {
         Random rand = new Random();
         int n = 100 + 50 * cnt_emeralds;
         float motion_randomness = 0.3F;
         float position_randomness = 0.0F;
         Color color = new Color(0, 255, 0);
         float color_r = (float)color.getRed() / 255.0F;
         float color_g = (float)color.getGreen() / 255.0F;
         float color_b = (float)color.getBlue() / 255.0F;

         for(int i = 0; i < n; ++i) {
            spawnColorParticle((double)motion_randomness, (double)position_randomness, posX, posY, posZ, rand, color_r, color_g, color_b, true);
         }

      }
   }

   public static void spawnColorParticle(double motion_rnd, double pos_rnd, double posX, double posY, double posZ, Random rand, float color_r, float color_g, float color_b, boolean isOnlyTop) {
      posX += rand.nextGaussian() * pos_rnd;
      posY += rand.nextGaussian() * pos_rnd;
      posZ += rand.nextGaussian() * pos_rnd;
      double motionX = rand.nextGaussian() * motion_rnd;
      double motionY = rand.nextGaussian() * motion_rnd;
      double motionZ = rand.nextGaussian() * motion_rnd;
      if(motion_rnd == -1000.0D) {
         motionX = -1000.0D;
         motionY = -1000.0D;
         motionZ = -1000.0D;
      }

      if(isOnlyTop) {
         motionY = Math.abs(motionY);
      }

      spawnColorParticle(posX, posY, posZ, motionX, motionY, motionZ, color_r, color_g, color_b);
   }

   public static void spawnColorParticle(double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float color_r, float color_g, float color_b) {
      ParticleFinalKillEffect pe = new ParticleFinalKillEffect(Minecraft.func_71410_x().field_71441_e, posX, posY, posZ);
      if(motionX != -1000.0D) {
         pe.field_70159_w = motionX;
      }

      if(motionY != -1000.0D) {
         pe.field_70181_x = motionY;
      }

      if(motionZ != -1000.0D) {
         pe.field_70179_y = motionZ;
      }

      pe.func_70538_b(color_r, color_g, color_b);
      Minecraft.func_71410_x().field_71452_i.func_78873_a(pe);
   }

   public static void spawnColorParticleSharpness(double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float color_r, float color_g, float color_b) {
      if(isActive) {
         ParticleSharpnessEffect pe = new ParticleSharpnessEffect(Minecraft.func_71410_x().field_71441_e, posX, posY, posZ);
         pe.field_70159_w = motionX;
         pe.field_70181_x = motionY;
         pe.field_70179_y = motionZ;
         pe.func_70538_b(color_r, color_g, color_b);
         Minecraft.func_71410_x().field_71452_i.func_78873_a(pe);
      }
   }

}
