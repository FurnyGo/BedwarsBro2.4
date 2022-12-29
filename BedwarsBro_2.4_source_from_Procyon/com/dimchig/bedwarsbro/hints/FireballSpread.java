// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.opengl.GL11;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import java.awt.Color;
import net.minecraft.client.Minecraft;

public class FireballSpread
{
    static Minecraft mc;
    public boolean isActive;
    public boolean isOffsetActive;
    private static double MAIN_CALCULATED_VARIABLE;
    private static double MAIN_CALCULATED_RADIUS_MAX;
    private static double MAIN_CALCULATED_RADIUS_MID;
    private static double MAIN_CALCULATED_RADIUS_MIN;
    private static Color color_zone_max;
    private static Color color_zone_mid;
    private static Color color_zone_min;
    private static double PI_TIMES_2;
    private static double RADIANS_180;
    Tessellator tessellator;
    WorldRenderer worldRenderer;
    
    public FireballSpread() {
        this.isActive = false;
        this.isOffsetActive = false;
        FireballSpread.mc = Minecraft.func_71410_x();
        this.updateBooleans();
        this.tessellator = Tessellator.func_178181_a();
        this.worldRenderer = this.tessellator.func_178180_c();
    }
    
    public void updateBooleans() {
        this.isActive = Main.getConfigBool(Main.CONFIG_MSG.FIREBALL_SPREAD);
        this.isOffsetActive = Main.getConfigBool(Main.CONFIG_MSG.FIREBALL_SPREAD_OFFSET_X);
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        if (!this.isActive) {
            return;
        }
        final EntityPlayerSP player = FireballSpread.mc.field_71439_g;
        if (FireballSpread.mc.field_71474_y.field_74320_O != 0) {
            return;
        }
        if (player.func_71045_bC() == null || player.func_71045_bC().func_77973_b() != Items.field_151059_bz) {
            return;
        }
        final float player_yaw = (player.field_70177_z % 360.0f + 360.0f) % 360.0f;
        final float player_pitch = player.field_70125_A;
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glLineWidth(3.0f);
        GL11.glRotatef(-player.field_70177_z, 0.0f, 1.0f, 0.0f);
        GL11.glTranslated(0.0, (double)FireballSpread.mc.field_71439_g.func_70047_e(), 0.0);
        GL11.glRotated((double)FireballSpread.mc.field_71439_g.field_70125_A, 1.0, 0.0, 0.0);
        GL11.glTranslated(0.0, 0.0, 1.0);
        GL11.glEnable(3042);
        GL11.glEnable(2884);
        GL11.glBlendFunc(770, 771);
        final double r = 0.01;
        final double bx = 0.1;
        final double by = 0.0;
        final double bz = 0.0;
        final int sides = 40;
        final double x = 0.0;
        final double y = 0.0;
        final double z = 0.0;
        double offset_x = -0.002;
        double offset_y = 0.0;
        if (this.isOffsetActive) {
            offset_x = -0.004;
            offset_y = -0.002;
        }
        this.drawCircle(offset_x, offset_y, 0.0, 64.0, FireballSpread.MAIN_CALCULATED_RADIUS_MAX * FireballSpread.MAIN_CALCULATED_VARIABLE, FireballSpread.color_zone_max);
        this.drawCircle(offset_x, offset_y, 0.0, 32.0, FireballSpread.MAIN_CALCULATED_RADIUS_MID * FireballSpread.MAIN_CALCULATED_VARIABLE, FireballSpread.color_zone_mid);
        this.drawCircle(offset_x, offset_y, 0.0, 16.0, FireballSpread.MAIN_CALCULATED_RADIUS_MIN * FireballSpread.MAIN_CALCULATED_VARIABLE, FireballSpread.color_zone_min);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public void drawCircle(final double x, final double y, final double z, final double sides, final double radius, final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        this.worldRenderer.func_181668_a(6, DefaultVertexFormats.field_181705_e);
        this.worldRenderer.func_181662_b(x, y, z).func_181675_d();
        for (int i = 0; i <= sides; ++i) {
            final double angle = FireballSpread.PI_TIMES_2 * i / sides + FireballSpread.RADIANS_180;
            this.worldRenderer.func_181662_b(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, z).func_181675_d();
        }
        this.tessellator.func_78381_a();
    }
    
    private void drawSquare(final int range, final EntityPlayerSP player, final Vec3 playerPos, final float partialTicks) {
        float p_yaw = FireballSpread.mc.field_71439_g.field_70177_z;
        float p_pitch = FireballSpread.mc.field_71439_g.field_70125_A;
        p_yaw = 0.0f;
        p_pitch = 0.0f;
        double posX = 0.0;
        double posY = 0.0f + FireballSpread.mc.field_71439_g.func_70047_e();
        double posZ = 0.0;
        final double yaw = p_yaw;
        final double pitch = p_pitch;
        final double pitchWithOffset = p_pitch + 0.0f;
        final double motionX = -MathHelper.func_76126_a((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
        final double motionY = -MathHelper.func_76126_a((float)(pitchWithOffset * 0.01745329238474369));
        final double motionZ = MathHelper.func_76134_b((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
        final double v = range;
        double accelX = FireballSpread.mc.field_71441_e.field_73012_v.nextGaussian() * 0.4;
        double accelY = FireballSpread.mc.field_71441_e.field_73012_v.nextGaussian() * 0.4;
        double accelZ = FireballSpread.mc.field_71441_e.field_73012_v.nextGaussian() * 0.4;
        if (range == 1) {
            accelX = 8.0;
            accelY = 0.0;
            accelZ = 0.0;
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        }
        final double d0 = MathHelper.func_76133_a(accelX * accelX + accelY * accelY + accelZ * accelZ);
        double accelerationX = accelX / d0 * 0.1;
        final double accelerationY = accelY / d0 * 0.1;
        final double accelerationZ = accelZ / d0 * 0.1;
        if (range == 1) {
            accelerationX = 0.0;
        }
        posX += accelerationX;
        posY += accelerationY;
        ++posZ;
        final Vec3 pos = new Vec3(posX, posY, posZ);
        if (pos != null) {
            final double hitX = pos.field_72450_a;
            final double hitY = pos.field_72448_b;
            final double hitZ = pos.field_72449_c;
            final double area = 0.005;
            Main.playerFocus.drawBox(hitX - area, hitY - area, hitZ - area, hitX + area, hitY + area, hitZ + area);
        }
    }
    
    private double myMap(final double value, final double leftMin, final double leftMax, final double rightMin, final double rightMax) {
        final double leftSpan = leftMax - leftMin;
        final double rightSpan = rightMax - rightMin;
        final double valueScaled = (value - leftMin) / leftSpan;
        return rightMin + valueScaled * rightSpan;
    }
    
    private Vec3 getVectorWithPartialTicks(final Entity en, final float partialTicks) {
        final double x = en.field_70169_q + (en.field_70165_t - en.field_70169_q) * partialTicks;
        final double y = en.field_70167_r + (en.field_70163_u - en.field_70167_r) * partialTicks;
        final double z = en.field_70166_s + (en.field_70161_v - en.field_70166_s) * partialTicks;
        return new Vec3(x, y, z);
    }
    
    static {
        FireballSpread.MAIN_CALCULATED_VARIABLE = 0.00115;
        FireballSpread.MAIN_CALCULATED_RADIUS_MAX = 100.0;
        FireballSpread.MAIN_CALCULATED_RADIUS_MID = 50.0;
        FireballSpread.MAIN_CALCULATED_RADIUS_MIN = 25.0;
        FireballSpread.color_zone_max = new Color(1.0f, 0.0f, 0.0f, 0.2f);
        FireballSpread.color_zone_mid = new Color(1.0f, 1.0f, 0.0f, 0.25f);
        FireballSpread.color_zone_min = new Color(0.0f, 1.0f, 0.0f, 0.3f);
        FireballSpread.PI_TIMES_2 = 6.283185307179586;
        FireballSpread.RADIANS_180 = Math.toRadians(180.0);
    }
}
