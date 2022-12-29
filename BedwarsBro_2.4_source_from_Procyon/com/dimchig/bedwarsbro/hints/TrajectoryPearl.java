// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import com.dimchig.nolegit.BowAimbot;
import net.minecraft.util.MathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.world.World;
import net.minecraft.util.EntitySelectors;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;

public class TrajectoryPearl
{
    static Minecraft mc;
    public boolean isActive;
    public static double gravity;
    
    public TrajectoryPearl() {
        this.isActive = false;
        TrajectoryPearl.mc = Minecraft.func_71410_x();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        this.isActive = Main.getConfigBool(Main.CONFIG_MSG.PEARL_PREDICTION);
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        if (!this.isActive) {
            return;
        }
        final EntityPlayerSP player = TrajectoryPearl.mc.field_71439_g;
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
        final List<EntityEnderPearl> pearls = (List<EntityEnderPearl>)TrajectoryPearl.mc.field_71441_e.func_175644_a((Class)EntityEnderPearl.class, EntitySelectors.field_94557_a);
        if (pearls != null && pearls.size() > 0) {
            for (final EntityEnderPearl pearl : pearls) {
                final ArrayList<MyPoint> pearl_points = this.getHitTrajectoryPearl((World)TrajectoryPearl.mc.field_71441_e, pearl);
                if (pearl_points.size() > 10) {
                    final MyPoint point = pearl_points.get(pearl_points.size() - 1);
                    final Vec3 pos = point.pos;
                    final double c = 0.1;
                    final double hitX = Math.floor(pos.field_72450_a);
                    final double hitY = Math.floor(pos.field_72448_b);
                    final double hitZ = Math.floor(pos.field_72449_c);
                    GL11.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
                    Main.playerFocus.drawBox(hitX, hitY, hitZ, hitX + 1.0, hitY + 1.0, hitZ + 1.0);
                    final double dist = Math.sqrt(Math.pow(playerPos.field_72450_a - hitX - 0.5, 2.0) + Math.pow(playerPos.field_72449_c - hitZ - 0.5, 2.0));
                    if (dist < 20.0) {
                        final String text = new DecimalFormat("0.0").format(point.step_cnt / 150.0f);
                        GL11.glTranslated(playerPos.field_72450_a, playerPos.field_72448_b, playerPos.field_72449_c);
                        GL11.glEnable(3553);
                        Main.draw3DText.drawText(playerPos, new Vec3(hitX + 0.5, hitY + 2.0, hitZ + 0.5), player, text, new Color(0.0f, 1.0f, 1.0f, 1.0f).getRGB());
                        GL11.glDisable(3553);
                        GL11.glTranslated(-playerPos.field_72450_a, -playerPos.field_72448_b, -playerPos.field_72449_c);
                    }
                    GL11.glColor4f(0.0f, 0.5f, 1.0f, 1.0f);
                    Vec3 prev_p = null;
                    for (final MyPoint p : pearl_points) {
                        if (prev_p == null) {
                            prev_p = p.pos;
                        }
                        else {
                            final GuiPlayerFocus playerFocus = Main.playerFocus;
                            GuiPlayerFocus.drawLineWithGL(prev_p, p.pos);
                            prev_p = p.pos;
                        }
                    }
                }
            }
        }
        if (player.func_71045_bC() != null && player.func_71045_bC().func_77973_b() == Items.field_151079_bi) {
            final ArrayList<MyPoint> points = this.getHitTrajectory(player, (World)TrajectoryPearl.mc.field_71441_e, TrajectoryPearl.mc.field_71439_g.field_70177_z, TrajectoryPearl.mc.field_71439_g.field_70125_A, playerPos);
            if (points.size() > 10) {
                final MyPoint point2 = points.get(points.size() - 1);
                Vec3 pos2 = point2.pos;
                pos2 = this.dropPointOnGround(pos2);
                if (pos2 != null) {
                    GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
                    final double area = this.myMap(point2.step_cnt, 0.0, 400.0, 0.0, 4.0);
                    if (!this.checkHit(pos2, (int)Math.ceil(area))) {
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
                    }
                    final double hitX2 = Math.floor(pos2.field_72450_a);
                    final double hitY2 = Math.floor(pos2.field_72448_b);
                    final double hitZ2 = Math.floor(pos2.field_72449_c);
                    Main.playerFocus.drawFilledSquare(hitX2 - area, hitY2, hitZ2 - area, hitX2 + area + 1.0, hitY2, hitZ2 + area + 1.0);
                    final Vec3 c2 = new Vec3(hitX2 - area, hitY2, hitZ2 - area);
                    final Vec3 c3 = new Vec3(hitX2 - area, hitY2, hitZ2 + area + 1.0);
                    final Vec3 c4 = new Vec3(hitX2 + area + 1.0, hitY2, hitZ2 + area + 1.0);
                    final Vec3 c5 = new Vec3(hitX2 + area + 1.0, hitY2, hitZ2 - area);
                    final GuiPlayerFocus playerFocus2 = Main.playerFocus;
                    GuiPlayerFocus.drawLineWithGL(c2, c3);
                    final GuiPlayerFocus playerFocus3 = Main.playerFocus;
                    GuiPlayerFocus.drawLineWithGL(c3, c4);
                    final GuiPlayerFocus playerFocus4 = Main.playerFocus;
                    GuiPlayerFocus.drawLineWithGL(c4, c5);
                    final GuiPlayerFocus playerFocus5 = Main.playerFocus;
                    GuiPlayerFocus.drawLineWithGL(c5, c2);
                }
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
    
    private ArrayList<MyPoint> getHitTrajectoryPearl(final World world, final EntityEnderPearl en) {
        final ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        double motionX = en.field_70159_w;
        double motionY = en.field_70181_x;
        double motionZ = en.field_70179_y;
        final double gravity = 0.05000000074505806;
        double x = en.field_70165_t;
        double y = en.field_70163_u;
        double z = en.field_70161_v;
        final double drag = 1.0013;
        final int MAX_ITERATIONS = 500;
        final double percision = 0.1;
        final double collider = 1.0;
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(0, 0, 0);
        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            pos.func_181079_c((int)x, (int)y, (int)z);
            final Vec3 vec = new Vec3(x, y, z);
            final double lastX = x;
            final double lastY = y;
            final double lastZ = z;
            motionY -= gravity * percision;
            motionX *= drag;
            motionZ *= drag;
            x += motionX * percision;
            y += motionY * percision;
            z += motionZ * percision;
            final MovingObjectPosition movingobjectposition = world.func_72933_a(vec, new Vec3(x, y, z));
            if (movingobjectposition != null) {
                break;
            }
            points.add(new MyPoint(vec, i));
        }
        return points;
    }
    
    private Vec3 dropPointOnGround(final Vec3 pos) {
        final BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos((int)pos.field_72450_a, (int)Math.ceil(pos.field_72448_b), (int)pos.field_72449_c);
        for (int i = mp.func_177956_o(); i >= 0; --i) {
            mp.func_181079_c(mp.func_177958_n(), i, mp.func_177952_p());
            final IBlockState state = TrajectoryPearl.mc.field_71441_e.func_180495_p((BlockPos)mp);
            if (state != null && state.func_177230_c() != Blocks.field_150350_a) {
                return new Vec3(pos.field_72450_a, (double)(i + 1), pos.field_72449_c);
            }
        }
        return null;
    }
    
    private boolean checkHit(final Vec3 pos, final int area) {
        final double hitX = Math.floor(pos.field_72450_a);
        final double hitY = Math.floor(pos.field_72448_b);
        final double hitZ = Math.floor(pos.field_72449_c);
        for (int i = -area; i <= area; ++i) {
            for (int j = -area; j <= area; ++j) {
                if (this.dropPointOnGround(new Vec3(hitX + i, hitY, hitZ + j)) == null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private ArrayList<MyPoint> getHitTrajectory(final EntityPlayerSP player, final World world, final double player_yaw, final double player_pitch, final Vec3 player_pos) {
        final ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        final double yaw = player_yaw;
        final double pitch = player_pitch;
        final double pitchWithOffset = player_pitch + 0.0;
        final double initialVelocity = 1.8;
        double motionX = -MathHelper.func_76126_a((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
        double motionY = -MathHelper.func_76126_a((float)(pitchWithOffset * 0.01745329238474369));
        double motionZ = MathHelper.func_76134_b((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
        motionX *= initialVelocity;
        motionY *= initialVelocity;
        motionZ *= initialVelocity;
        if (player.field_70122_E) {
            motionY += (float)player.field_70181_x;
        }
        motionX += player.field_70159_w;
        motionZ += player.field_70179_y;
        double x = player_pos.field_72450_a;
        double y = player_pos.field_72448_b + player.func_70047_e() - 0.10000000149011612;
        double z = player_pos.field_72449_c;
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(0, 0, 0);
        final double gravity = 0.05000000074505806;
        final double drag = 0.99;
        final int MAX_ITERATIONS = 500;
        final double percision = 0.1;
        final double collider = 1.0;
        for (int i = 0; i < MAX_ITERATIONS; ++i) {
            pos.func_181079_c((int)x, (int)y, (int)z);
            final Vec3 vec = new Vec3(x, y, z);
            final double lastX = x;
            final double lastY = y;
            final double lastZ = z;
            motionY -= gravity * percision;
            x += motionX * percision;
            y += motionY * percision;
            z += motionZ * percision;
            final MovingObjectPosition movingobjectposition = world.func_72933_a(vec, new Vec3(x, y, z));
            if (movingobjectposition != null) {
                break;
            }
            points.add(new MyPoint(vec, i));
        }
        return points;
    }
    
    private Vec3 getVectorWithPartialTicks(final Entity en, final float partialTicks) {
        final double x = en.field_70169_q + (en.field_70165_t - en.field_70169_q) * partialTicks;
        final double y = en.field_70167_r + (en.field_70163_u - en.field_70167_r) * partialTicks;
        final double z = en.field_70166_s + (en.field_70161_v - en.field_70166_s) * partialTicks;
        return new Vec3(x, y, z);
    }
    
    static {
        TrajectoryPearl.gravity = BowAimbot.gravity;
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
