package com.dimchig.bedwarsbro.particles;

import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.particles.ParticleController;
import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class ParticlesAlwaysSharpness {

   private Minecraft mc = Minecraft.func_71410_x();
   private boolean hasSharpness;
   private boolean flag = false;
   private boolean areCritsRemoved = false;


   public ParticlesAlwaysSharpness() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public void onMyLeftClick() {
      if(ParticleController.isActive) {
         if(!this.areCritsRemoved) {
            this.areCritsRemoved = true;
            Minecraft.func_71410_x().field_71452_i.func_178929_a(9, (IParticleFactory)null);
         }

         if(this.mc.field_71415_G && this.mc.field_71476_x != null) {
            this.attemptParticleSpawn();
         }
      }
   }

   private boolean shouldSpawnParticles() {
      Entity entity = this.mc.field_71476_x.field_72308_g;
      return entity instanceof EntityLiving || entity instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP)entity).func_70089_S();
   }

   private void attemptParticleSpawn() {
      try {
         Entity ent = this.mc.field_71476_x.field_72308_g;
         if(!(ent instanceof EntityPlayer)) {
            return;
         }

         EntityPlayer en = (EntityPlayer)ent;
         float color_r = 1.0F;
         float color_g = 1.0F;
         float color_b = 1.0F;
         if(en == null || en.func_145748_c_() == null || en.func_70005_c_() == null) {
            return;
         }

         CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor(en);
         new Random();
         Color color = new Color(0, 0, 0);
         color_r = (float)color.getRed() / 255.0F;
         color_g = (float)color.getGreen() / 255.0F;
         color_b = (float)color.getBlue() / 255.0F;
         if(team_color == CustomScoreboard.TEAM_COLOR.NONE) {
            color = Main.rainbowColorSynchronizer.getColor();
         } else {
            color = ParticleController.getParticleColorForTeam(team_color);
         }

         color_r = (float)color.getRed() / 255.0F;
         color_g = (float)color.getGreen() / 255.0F;
         color_b = (float)color.getBlue() / 255.0F;

         for(int i = 0; i < 20; ++i) {
            double x = this.mc.field_71476_x.field_72308_g.field_70165_t + (Math.random() - 0.5D) * 0.1D;
            double y = this.mc.field_71476_x.field_72308_g.field_70163_u + (double)this.mc.field_71476_x.field_72308_g.func_70047_e() - 0.3D + (Math.random() - 0.5D) * 0.5D;
            double z = this.mc.field_71476_x.field_72308_g.field_70161_v + (Math.random() - 0.5D) * 0.1D;
            double speed = 0.2D - Math.random() * 0.1D;
            double xOffset = Math.random() > 0.5D?speed:-speed;
            double yOffset = Math.random() > 0.5D?speed:-speed;
            double zOffset = Math.random() > 0.5D?speed:-speed;
            if(this.mc.field_71441_e.field_72995_K) {
               ParticleController.spawnColorParticleSharpness(x, y, z, xOffset, yOffset, zOffset, color_r, color_g, color_b);
            }
         }
      } catch (Exception var24) {
         ;
      }

   }
}
