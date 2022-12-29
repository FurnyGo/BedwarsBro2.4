// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.particles;

import net.minecraft.client.entity.EntityPlayerSP;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import com.dimchig.bedwarsbro.MyChatListener;
import java.awt.Color;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.client.Minecraft;

public class ParticleTrail
{
    public static ParticleController particleController;
    public static boolean isRainbowAlways;
    Minecraft mc;
    
    public ParticleTrail() {
        this.updateBooleans();
        this.mc = Minecraft.func_71410_x();
    }
    
    public void updateBooleans() {
        ParticleTrail.isRainbowAlways = HintsValidator.isParticleTrailRainbowActive();
    }
    
    public void drawPlayerTrail() {
        final EntityPlayerSP player = this.mc.field_71439_g;
        if (player.field_70125_A > 50.0f) {
            return;
        }
        double px = player.field_70142_S + 4.0;
        final double py = player.field_70137_T + player.func_70047_e() - this.mc.field_71441_e.field_73012_v.nextFloat() * 0.5;
        double pz = player.field_70136_U;
        final double angle = Math.toRadians(player.field_70177_z - 90.0f);
        final double distance = 0.6;
        px = (float)(distance * Math.cos(angle)) + px - 4.0;
        pz += (float)(distance * Math.sin(angle));
        if ((player.field_70159_w == 0.0 && player.field_70179_y == 0.0) || player.field_70181_x < -2.0) {
            return;
        }
        Color color = new Color(0, 0, 0);
        float color_r = 0.0f;
        float color_g = 0.0f;
        float color_b = 0.0f;
        final CustomScoreboard.TEAM_COLOR mod_team_color = MyChatListener.getEntityTeamColor((EntityPlayer)player);
        if (!ParticleTrail.isRainbowAlways && mod_team_color != CustomScoreboard.TEAM_COLOR.NONE) {
            color = ParticleController.getParticleColorForTeam(mod_team_color);
        }
        else {
            color = Main.rainbowColorSynchronizer.getColor();
        }
        color_r = color.getRed() / 255.0f;
        color_g = color.getGreen() / 255.0f;
        color_b = color.getBlue() / 255.0f;
        final ParticleController particleController = ParticleTrail.particleController;
        ParticleController.spawnColorParticle(-1000.0, 0.05, px, py, pz, this.mc.field_71441_e.field_73012_v, color_r, color_g, color_b, false);
    }
    
    static {
        ParticleTrail.particleController = Main.particleController;
        ParticleTrail.isRainbowAlways = false;
    }
}
