// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.MovingObjectPosition;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import net.minecraft.util.EntitySelectors;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;

public class TrajectoryFireball
{
    static Minecraft mc;
    public boolean isActive;
    
    public TrajectoryFireball() {
        this.isActive = false;
        TrajectoryFireball.mc = Minecraft.func_71410_x();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        this.isActive = Main.getConfigBool(Main.CONFIG_MSG.FIREBALL_PREDICTION);
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        if (!this.isActive) {
            return;
        }
        final EntityPlayerSP player = TrajectoryFireball.mc.field_71439_g;
        final float player_yaw = (player.field_70177_z % 360.0f + 360.0f) % 360.0f;
        final float player_pitch = player.field_70125_A;
        final Vec3 playerPos = this.getVectorWithPartialTicks((Entity)player, event.partialTicks);
        final ArrayList<Vec3> positions = new ArrayList<Vec3>();
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        GL11.glTranslated(-playerPos.field_72450_a, -playerPos.field_72448_b, -playerPos.field_72449_c);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GlStateManager.func_179084_k();
        GL11.glLineWidth(3.0f);
        final List<EntityFireball> fireballs = (List<EntityFireball>)TrajectoryFireball.mc.field_71441_e.func_175644_a((Class)EntityFireball.class, EntitySelectors.field_94557_a);
        if (fireballs != null && fireballs.size() > 0) {
            for (final EntityFireball fireball : fireballs) {
                final String custom_nametag = fireball.func_95999_t();
                if (custom_nametag != null && custom_nametag.equals("fake_fireball")) {
                    continue;
                }
                final Vec3 vecStart = this.getVectorWithPartialTicks((Entity)fireball, event.partialTicks);
                double x = vecStart.field_72450_a;
                double y = vecStart.field_72448_b;
                double z = vecStart.field_72449_c;
                final double motionX = fireball.field_70159_w;
                final double motionY = fireball.field_70181_x;
                final double motionZ = fireball.field_70179_y;
                final double percision = 1.0;
                if (y > 300.0) {
                    continue;
                }
                if (y < -10.0) {
                    continue;
                }
                Vec3 prev_pos = vecStart;
                final int MAX_ITERATIONS = 100;
                boolean isCollision = false;
                GL11.glColor4f(1.0f, 0.5f, 0.0f, 1.0f);
                GL11.glEnable(2929);
                for (int i = 0; i < MAX_ITERATIONS; ++i) {
                    Vec3 pos = new Vec3(x, y, z);
                    x += motionX * percision;
                    y += motionY * percision;
                    z += motionZ * percision;
                    final MovingObjectPosition movingobjectposition = TrajectoryFireball.mc.field_71441_e.func_72933_a(pos, new Vec3(x, y, z));
                    if (movingobjectposition != null) {
                        isCollision = true;
                        break;
                    }
                    pos = new Vec3(x, y, z);
                    if (prev_pos == null) {
                        prev_pos = pos;
                    }
                    else {
                        final GuiPlayerFocus playerFocus = Main.playerFocus;
                        GuiPlayerFocus.drawLineWithGL(prev_pos, pos);
                        prev_pos = pos;
                    }
                }
                GL11.glDisable(2929);
                if (!isCollision) {
                    continue;
                }
                final double c = 0.4;
                GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                Main.playerFocus.drawBox(x - c, y - c, z - c, x + c, y + c, z + c);
            }
        }
        GL11.glPopAttrib();
        GL11.glPopMatrix();
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
    
    public static class MyPoint
    {
        public Vec3 pos;
        public int step_cnt;
        
        public MyPoint(final Vec3 pos, final int step_cnt) {
            this.pos = pos;
            this.step_cnt = step_cnt;
        }
    }
}
