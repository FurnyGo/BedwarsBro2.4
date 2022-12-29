// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import java.awt.Color;
import java.util.Random;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.hints.HintsValidator;

public class ParticleController
{
    static boolean isActive;
    
    public void updateBooleans() {
        ParticleController.isActive = HintsValidator.isParticlesActive();
    }
    
    public ParticleController() {
        this.updateBooleans();
    }
    
    public static void spawnFinalKillParticles(final double posX, final double posY, final double posZ, final CustomScoreboard.TEAM_COLOR team_color) {
        if (!ParticleController.isActive) {
            return;
        }
        final Random rand = new Random();
        final int n = 150;
        final float motion_randomness = 0.2f;
        final float position_randomness = 0.1f;
        final Color color = getParticleColorForTeam(team_color);
        final float color_r = color.getRed() / 255.0f;
        final float color_g = color.getGreen() / 255.0f;
        final float color_b = color.getBlue() / 255.0f;
        for (int i = 0; i < n; ++i) {
            spawnColorParticle(motion_randomness, position_randomness, posX, posY, posZ, rand, color_r, color_g, color_b, false);
        }
    }
    
    public static Color getParticleColorForTeam(final CustomScoreboard.TEAM_COLOR team_color) {
        if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
            return new Color(255, 0, 0);
        }
        if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
            return new Color(255, 255, 0);
        }
        if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
            return new Color(0, 255, 0);
        }
        if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
            return new Color(0, 255, 255);
        }
        if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
            return new Color(0, 0, 255);
        }
        if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
            return new Color(255, 0, 255);
        }
        if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
            return new Color(128, 128, 128);
        }
        if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
            return new Color(255, 255, 255);
        }
        return new Color(0, 0, 0);
    }
    
    public static void spawnGenDiamondParticles(final double posX, final double posY, final double posZ, final int cnt_diamonds) {
        if (!ParticleController.isActive) {
            return;
        }
        final Random rand = new Random();
        final int n = 100 + 50 * cnt_diamonds;
        final float motion_randomness = 0.2f;
        final float position_randomness = 0.0f;
        final Color color = new Color(0, 255, 255);
        final float color_r = color.getRed() / 255.0f;
        final float color_g = color.getGreen() / 255.0f;
        final float color_b = color.getBlue() / 255.0f;
        for (int i = 0; i < n; ++i) {
            spawnColorParticle(motion_randomness, position_randomness, posX, posY, posZ, rand, color_r, color_g, color_b, true);
        }
    }
    
    public static void spawnGenEmeraldParticles(final double posX, final double posY, final double posZ, final int cnt_emeralds) {
        if (!ParticleController.isActive) {
            return;
        }
        final Random rand = new Random();
        final int n = 100 + 50 * cnt_emeralds;
        final float motion_randomness = 0.3f;
        final float position_randomness = 0.0f;
        final Color color = new Color(0, 255, 0);
        final float color_r = color.getRed() / 255.0f;
        final float color_g = color.getGreen() / 255.0f;
        final float color_b = color.getBlue() / 255.0f;
        for (int i = 0; i < n; ++i) {
            spawnColorParticle(motion_randomness, position_randomness, posX, posY, posZ, rand, color_r, color_g, color_b, true);
        }
    }
    
    public static void spawnColorParticle(final double motion_rnd, final double pos_rnd, double posX, double posY, double posZ, final Random rand, final float color_r, final float color_g, final float color_b, final boolean isOnlyTop) {
        posX += rand.nextGaussian() * pos_rnd;
        posY += rand.nextGaussian() * pos_rnd;
        posZ += rand.nextGaussian() * pos_rnd;
        double motionX = rand.nextGaussian() * motion_rnd;
        double motionY = rand.nextGaussian() * motion_rnd;
        double motionZ = rand.nextGaussian() * motion_rnd;
        if (motion_rnd == -1000.0) {
            motionX = -1000.0;
            motionY = -1000.0;
            motionZ = -1000.0;
        }
        if (isOnlyTop) {
            motionY = Math.abs(motionY);
        }
        spawnColorParticle(posX, posY, posZ, motionX, motionY, motionZ, color_r, color_g, color_b);
    }
    
    public static void spawnColorParticle(final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ, final float color_r, final float color_g, final float color_b) {
        final ParticleFinalKillEffect pe = new ParticleFinalKillEffect((World)Minecraft.func_71410_x().field_71441_e, posX, posY, posZ);
        if (motionX != -1000.0) {
            pe.field_70159_w = motionX;
        }
        if (motionY != -1000.0) {
            pe.field_70181_x = motionY;
        }
        if (motionZ != -1000.0) {
            pe.field_70179_y = motionZ;
        }
        pe.func_70538_b(color_r, color_g, color_b);
        Minecraft.func_71410_x().field_71452_i.func_78873_a((EntityFX)pe);
    }
    
    public static void spawnColorParticleSharpness(final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ, final float color_r, final float color_g, final float color_b) {
        if (!ParticleController.isActive) {
            return;
        }
        final ParticleSharpnessEffect pe = new ParticleSharpnessEffect((World)Minecraft.func_71410_x().field_71441_e, posX, posY, posZ);
        pe.field_70159_w = motionX;
        pe.field_70181_x = motionY;
        pe.field_70179_y = motionZ;
        pe.func_70538_b(color_r, color_g, color_b);
        Minecraft.func_71410_x().field_71452_i.func_78873_a((EntityFX)pe);
    }
    
    static {
        ParticleController.isActive = false;
    }
}
