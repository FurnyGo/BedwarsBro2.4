// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.particles;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.CustomScoreboard;
import java.awt.Color;
import java.util.Random;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLiving;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;

public class ParticlesAlwaysSharpness
{
    private Minecraft mc;
    private boolean hasSharpness;
    private boolean flag;
    private boolean areCritsRemoved;
    
    public ParticlesAlwaysSharpness() {
        this.flag = false;
        this.areCritsRemoved = false;
        this.mc = Minecraft.func_71410_x();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onMyLeftClick() {
        if (!ParticleController.isActive) {
            return;
        }
        if (!this.areCritsRemoved) {
            this.areCritsRemoved = true;
            Minecraft.func_71410_x().field_71452_i.func_178929_a(9, (IParticleFactory)null);
        }
        if (!this.mc.field_71415_G || this.mc.field_71476_x == null) {
            return;
        }
        this.attemptParticleSpawn();
    }
    
    private boolean shouldSpawnParticles() {
        final Entity entity = this.mc.field_71476_x.field_72308_g;
        return entity instanceof EntityLiving || (entity instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP)entity).func_70089_S());
    }
    
    private void attemptParticleSpawn() {
        try {
            final Entity ent = this.mc.field_71476_x.field_72308_g;
            if (!(ent instanceof EntityPlayer)) {
                return;
            }
            final EntityPlayer en = (EntityPlayer)ent;
            float color_r = 1.0f;
            float color_g = 1.0f;
            float color_b = 1.0f;
            if (en == null || en.func_145748_c_() == null || en.func_70005_c_() == null) {
                return;
            }
            final CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor(en);
            final Random rnd = new Random();
            Color color = new Color(0, 0, 0);
            color_r = color.getRed() / 255.0f;
            color_g = color.getGreen() / 255.0f;
            color_b = color.getBlue() / 255.0f;
            if (team_color == CustomScoreboard.TEAM_COLOR.NONE) {
                color = Main.rainbowColorSynchronizer.getColor();
            }
            else {
                color = ParticleController.getParticleColorForTeam(team_color);
            }
            color_r = color.getRed() / 255.0f;
            color_g = color.getGreen() / 255.0f;
            color_b = color.getBlue() / 255.0f;
            for (int i = 0; i < 20; ++i) {
                final double x = this.mc.field_71476_x.field_72308_g.field_70165_t + (Math.random() - 0.5) * 0.1;
                final double y = this.mc.field_71476_x.field_72308_g.field_70163_u + this.mc.field_71476_x.field_72308_g.func_70047_e() - 0.3 + (Math.random() - 0.5) * 0.5;
                final double z = this.mc.field_71476_x.field_72308_g.field_70161_v + (Math.random() - 0.5) * 0.1;
                final double speed = 0.2 - Math.random() * 0.1;
                final double xOffset = (Math.random() > 0.5) ? speed : (-speed);
                final double yOffset = (Math.random() > 0.5) ? speed : (-speed);
                final double zOffset = (Math.random() > 0.5) ? speed : (-speed);
                if (this.mc.field_71441_e.field_72995_K) {
                    ParticleController.spawnColorParticleSharpness(x, y, z, xOffset, yOffset, zOffset, color_r, color_g, color_b);
                }
            }
        }
        catch (Exception ex) {}
    }
}
