// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.nolegit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.settings.KeyBinding;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.EntitySelectors;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.awt.Color;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class BowAimbot
{
    static Minecraft mc;
    public static boolean isActive;
    public static boolean isToggle;
    public static boolean isDrawActive;
    public static ArrayList<Double> avg;
    public static Vec3 last_movement_vector;
    public static double last_predict_ticks;
    public static double prefict_delta;
    public static ArrayList<Vec3> temp_arr;
    public static double gravity;
    static Color normal_hitbox;
    static Color predict_hitbox;
    static Color predict_target;
    static Color arrow_collider;
    
    public BowAimbot() {
        BowAimbot.mc = Minecraft.func_71410_x();
    }
    
    public void toggle() {
        BowAimbot.isActive = !BowAimbot.isActive;
    }
    
    public void toggleDraw() {
        BowAimbot.isDrawActive = !BowAimbot.isDrawActive;
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        if (!BowAimbot.isActive) {
            return;
        }
        try {
            final EntityPlayerSP player = BowAimbot.mc.field_71439_g;
            final float player_yaw = (player.field_70177_z % 360.0f + 360.0f) % 360.0f;
            final float player_pitch = player.field_70125_A;
            final Vec3 playerPos = this.getVectorWithPartialTicks((Entity)player, event.partialTicks);
            final ArrayList<Vec3> positions = new ArrayList<Vec3>();
            if (player.func_71045_bC() == null || player.func_71045_bC().func_77973_b() != Items.field_151031_f) {
                BowAimbot.isToggle = false;
                return;
            }
            final double timeLeft = player.func_71052_bv();
            if (timeLeft < 70000.0) {
                BowAimbot.isToggle = false;
                return;
            }
            if (Mouse.isButtonDown(0)) {
                BowAimbot.isToggle = true;
            }
            if (!BowAimbot.isToggle) {
                return;
            }
            final double charge = Items.field_151031_f.func_77626_a(player.func_71045_bC()) - timeLeft;
            final List<EntityPlayer> villagers = (List<EntityPlayer>)BowAimbot.mc.field_71441_e.func_175644_a((Class)EntityPlayer.class, EntitySelectors.field_94557_a);
            if (villagers == null || villagers.size() == 0) {
                return;
            }
            final EntityPlayer closestPlayer = this.getClosestPlayer(player, playerPos, event.partialTicks);
            if (closestPlayer == null) {
                return;
            }
            final Vec3 closestPlayerPos = this.getVectorWithPartialTicks((Entity)closestPlayer, event.partialTicks);
            double dX = playerPos.field_72450_a - closestPlayerPos.field_72450_a;
            double dY = playerPos.field_72448_b - closestPlayerPos.field_72448_b;
            double dZ = playerPos.field_72449_c - closestPlayerPos.field_72449_c;
            double t_yaw = this.myMap(Math.atan2(dZ, dX), -3.141592653589793, 3.141592653589793, -180.0, 180.0);
            double t_pitch = this.myMap(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + 3.141592653589793, 3.141592653589793, 6.283185307179586, 90.0, -90.0);
            GL11.glPushMatrix();
            GL11.glPushAttrib(8192);
            GL11.glTranslated(-playerPos.field_72450_a, -playerPos.field_72448_b, -playerPos.field_72449_c);
            GL11.glDisable(2896);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GlStateManager.func_179084_k();
            GL11.glLineWidth(1.0f);
            if (BowAimbot.isDrawActive) {
                final List<EntityArrow> arrows = (List<EntityArrow>)BowAimbot.mc.field_71441_e.func_175644_a((Class)EntityArrow.class, EntitySelectors.field_94557_a);
                if (arrows != null && arrows.size() > 0) {
                    this.setLineColor(BowAimbot.arrow_collider);
                    for (final EntityArrow en : arrows) {
                        final Vec3 pos = this.getVectorWithPartialTicks((Entity)en, event.partialTicks);
                        final double c = 0.1;
                        Main.playerFocus.drawBox(pos.field_72450_a - c, pos.field_72448_b - c, pos.field_72449_c - c, pos.field_72450_a + c, pos.field_72448_b + c, pos.field_72449_c + c);
                    }
                }
            }
            final GuiPlayerFocus playerFocus = Main.playerFocus;
            if (!GuiPlayerFocus.STATE) {
                this.setLineColor(BowAimbot.normal_hitbox);
                final AxisAlignedBB box = closestPlayer.func_174813_aQ();
                this.drawBox(box.field_72340_a, box.field_72338_b, box.field_72339_c, box.field_72336_d, box.field_72337_e, box.field_72334_f);
            }
            final Vec3 target_pos = new Vec3(closestPlayerPos.field_72450_a, closestPlayerPos.field_72448_b + closestPlayer.field_70131_O / 2.0f, closestPlayerPos.field_72449_c);
            ArrayList<MyHit> hits = new ArrayList<MyHit>();
            for (int i = -20; i <= 20; ++i) {
                final float new_pitch_delta = i / 1.0f;
                final MyHit hit = this.getHitTrajectory(player, (World)BowAimbot.mc.field_71441_e, t_yaw + 90.0, t_pitch + new_pitch_delta, playerPos, target_pos);
                if (hit != null) {
                    hits.add(hit);
                }
            }
            if (hits.size() > 0) {
                MyHit hit2 = hits.get(hits.size() / 2);
                Vec3 pos = hit2.pos;
                double c = 0.4;
                final double hit_length = hit2.step_cnt * 0.1;
                final double collider = 2.0;
                final BlockPos minPos = new BlockPos(hit2.pos.field_72450_a - collider, hit2.pos.field_72448_b - collider, hit2.pos.field_72449_c - collider);
                final BlockPos maxPos = new BlockPos(hit2.pos.field_72450_a + collider, hit2.pos.field_72448_b + collider, hit2.pos.field_72449_c + collider);
                final AxisAlignedBB box2 = new AxisAlignedBB(minPos, maxPos);
                final List<EntityPlayer> vs = (List<EntityPlayer>)BowAimbot.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, box2);
                if (vs == null || vs.size() == 0) {
                    return;
                }
                final EntityPlayer villager = vs.get(0);
                if (villager == null) {
                    return;
                }
                final double predict_ticks_extender = 4.0;
                final double speedX = villager.field_70165_t - villager.field_70169_q;
                double speedY = villager.field_70163_u - villager.field_70167_r;
                final double speedZ = villager.field_70161_v - villager.field_70166_s;
                double predict_ticks = hit_length + predict_ticks_extender;
                speedY = 0.0;
                BowAimbot.prefict_delta = 0.05;
                BowAimbot.last_predict_ticks += (predict_ticks - BowAimbot.last_predict_ticks) * BowAimbot.prefict_delta;
                predict_ticks = BowAimbot.last_predict_ticks;
                if (charge < 20.0) {
                    predict_ticks += (20.0 - charge) * 0.1;
                }
                final double min_ticks = 5.0;
                if (predict_ticks < min_ticks) {
                    predict_ticks = min_ticks;
                }
                this.setLineColor(BowAimbot.predict_hitbox);
                Vec3 predict_pos = new Vec3(closestPlayerPos.field_72450_a, closestPlayerPos.field_72448_b + villager.field_70131_O / 2.0f, closestPlayerPos.field_72449_c);
                final Vec3 movement_vector = new Vec3(speedX * predict_ticks, speedY * predict_ticks, speedZ * predict_ticks);
                final double dfx = BowAimbot.last_movement_vector.field_72450_a + (movement_vector.field_72450_a - BowAimbot.last_movement_vector.field_72450_a) * BowAimbot.prefict_delta;
                final double dfy = BowAimbot.last_movement_vector.field_72448_b + (movement_vector.field_72448_b - BowAimbot.last_movement_vector.field_72448_b) * BowAimbot.prefict_delta;
                final double dfz = BowAimbot.last_movement_vector.field_72449_c + (movement_vector.field_72449_c - BowAimbot.last_movement_vector.field_72449_c) * BowAimbot.prefict_delta;
                final Vec3 vec_difference = new Vec3(dfx, dfy, dfz);
                predict_pos = predict_pos.func_178787_e(vec_difference);
                BowAimbot.last_movement_vector = vec_difference;
                this.drawBox(predict_pos.field_72450_a - villager.field_70130_N / 2.0f, predict_pos.field_72448_b - villager.field_70131_O / 2.0f, predict_pos.field_72449_c - villager.field_70130_N / 2.0f, predict_pos.field_72450_a + villager.field_70130_N / 2.0f, predict_pos.field_72448_b + villager.field_70131_O / 2.0f, predict_pos.field_72449_c + villager.field_70130_N / 2.0f);
                dX = playerPos.field_72450_a - predict_pos.field_72450_a;
                dY = playerPos.field_72448_b - predict_pos.field_72448_b;
                dZ = playerPos.field_72449_c - predict_pos.field_72449_c;
                t_yaw = this.myMap(Math.atan2(dZ, dX), -3.1415927410125732, 3.1415927410125732, -180.0, 180.0);
                t_pitch = this.myMap(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + 3.141592653589793, 3.1415927410125732, 6.2831854820251465, 90.0, -90.0);
                hits = new ArrayList<MyHit>();
                for (int j = -20; j <= 20; ++j) {
                    final float new_pitch_delta2 = j / 1.0f;
                    hit2 = this.getHitTrajectory(player, (World)BowAimbot.mc.field_71441_e, t_yaw + 90.0, t_pitch + new_pitch_delta2, playerPos, predict_pos);
                    if (hit2 != null) {
                        hits.add(hit2);
                    }
                }
                if (hits.size() > 0) {
                    hit2 = hits.get(hits.size() / 2);
                    pos = hit2.pos;
                    c = 0.1;
                    this.setLineColor(BowAimbot.predict_target);
                    this.drawBox(pos.field_72450_a - c, pos.field_72448_b - c, pos.field_72449_c - c, pos.field_72450_a + c, pos.field_72448_b + c, pos.field_72449_c + c);
                    final KeyBinding key = Minecraft.func_71410_x().field_71474_y.field_74313_G;
                    if (charge <= 7000.0) {
                        HintsFinder.rotateTo((Entity)Minecraft.func_71410_x().field_71439_g, (float)hit2.yaw, (float)hit2.pitch);
                    }
                }
            }
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private double getRealYaw(final double yaw) {
        return (yaw % 360.0 + 360.0) % 360.0;
    }
    
    private EntityPlayer getClosestPlayer(final EntityPlayerSP mod_player, final Vec3 mod_player_pos, final float partialTicks) {
        final List<EntityPlayer> players = (List<EntityPlayer>)BowAimbot.mc.field_71441_e.func_175644_a((Class)EntityPlayer.class, EntitySelectors.field_94557_a);
        if (players == null || players.size() == 0) {
            return null;
        }
        final double mod_player_yaw = this.getRealYaw(mod_player.field_70177_z);
        EntityPlayer closestPlayer = null;
        double dist = 1000.0;
        for (final EntityPlayer en : players) {
            if (en != mod_player) {
                if (en.func_96124_cp() == mod_player.func_96124_cp()) {
                    continue;
                }
                final Vec3 p_pos = this.getVectorWithPartialTicks((Entity)en, partialTicks);
                final double dX = mod_player_pos.field_72450_a - p_pos.field_72450_a;
                final double dY = mod_player_pos.field_72448_b - p_pos.field_72448_b;
                final double dZ = mod_player_pos.field_72449_c - p_pos.field_72449_c;
                final double t_yaw = this.getRealYaw(this.myMap(Math.atan2(dZ, dX), -3.141592653589793, 3.141592653589793, -180.0, 180.0) + 90.0);
                final double d = Math.abs(mod_player_yaw - t_yaw);
                if (d >= dist) {
                    continue;
                }
                dist = d;
                closestPlayer = en;
            }
        }
        return closestPlayer;
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
    
    private MyHit getHitTrajectory(final EntityPlayerSP player, final World world, final double player_yaw, final double player_pitch, final Vec3 player_pos, final Vec3 target_pos) {
        final double yaw = player_yaw;
        final double pitch = player_pitch;
        final double pitchWithOffset = player_pitch + 0.0;
        double initialVelocity = 3.0;
        final double timeLeft = player.func_71052_bv();
        final double charge = Items.field_151031_f.func_77626_a(player.func_71045_bC()) - timeLeft;
        final double baseVelocity = Math.min(1.0f, HintsFinder.myMap((float)charge, 0.0f, 20.0f, 0.03f, 1.0f));
        initialVelocity = baseVelocity * 3.0;
        double motionX = -MathHelper.func_76126_a((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
        double motionY = -MathHelper.func_76126_a((float)(pitchWithOffset * 0.01745329238474369));
        double motionZ = MathHelper.func_76134_b((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
        final float length = (float)Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= length;
        motionY /= length;
        motionZ /= length;
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
        final int MAX_ITERATIONS = 300;
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
            final IBlockState iblockstate = world.func_180495_p((BlockPos)pos);
            if (iblockstate != null && iblockstate.func_177230_c() != Blocks.field_150350_a) {
                return null;
            }
            if (target_pos.func_72438_d(vec) <= collider) {
                return new MyHit(vec, i, player_yaw, player_pitch);
            }
        }
        return null;
    }
    
    private void setLineColor(final Color color) {
        if (!BowAimbot.isDrawActive) {
            return;
        }
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 1.0f);
    }
    
    public void drawBox(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        if (!BowAimbot.isDrawActive) {
            return;
        }
        drawLineWithGL(new Vec3(x1, y1, z1), new Vec3(x2, y1, z1));
        drawLineWithGL(new Vec3(x2, y1, z1), new Vec3(x2, y1, z2));
        drawLineWithGL(new Vec3(x2, y1, z2), new Vec3(x1, y1, z2));
        drawLineWithGL(new Vec3(x1, y1, z2), new Vec3(x1, y1, z1));
        drawLineWithGL(new Vec3(x1, y2, z1), new Vec3(x2, y2, z1));
        drawLineWithGL(new Vec3(x2, y2, z1), new Vec3(x2, y2, z2));
        drawLineWithGL(new Vec3(x2, y2, z2), new Vec3(x1, y2, z2));
        drawLineWithGL(new Vec3(x1, y2, z2), new Vec3(x1, y2, z1));
        drawLineWithGL(new Vec3(x1, y1, z1), new Vec3(x1, y2, z1));
        drawLineWithGL(new Vec3(x1, y1, z2), new Vec3(x1, y2, z2));
        drawLineWithGL(new Vec3(x2, y1, z1), new Vec3(x2, y2, z1));
        drawLineWithGL(new Vec3(x2, y1, z2), new Vec3(x2, y2, z2));
    }
    
    public static void drawLineWithGL(final Vec3 blockA, final Vec3 blockB) {
        GL11.glBegin(3);
        GL11.glVertex3d(blockA.field_72450_a, blockA.field_72448_b, blockA.field_72449_c);
        GL11.glVertex3d(blockB.field_72450_a, blockB.field_72448_b, blockB.field_72449_c);
        GL11.glEnd();
    }
    
    private Vec3 calcVelocityForArrow(final double yaw, final double pitch) {
        final double power = 1.0;
        final double actualPower = power * 3.0;
        final double xVel = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        final double yVel = -Math.sin(Math.toRadians(pitch));
        final double zVel = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        return new Vec3(xVel, yVel, zVel).func_72432_b();
    }
    
    static {
        BowAimbot.isToggle = false;
        BowAimbot.avg = new ArrayList<Double>();
        BowAimbot.last_movement_vector = new Vec3(0.0, 0.0, 0.0);
        BowAimbot.last_predict_ticks = 0.0;
        BowAimbot.prefict_delta = 0.05;
        BowAimbot.temp_arr = new ArrayList<Vec3>();
        BowAimbot.gravity = 0.00625;
        BowAimbot.normal_hitbox = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        BowAimbot.predict_hitbox = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        BowAimbot.predict_target = new Color(0.0f, 1.0f, 0.0f, 1.0f);
        BowAimbot.arrow_collider = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    }
    
    private class MyHit
    {
        public Vec3 pos;
        public int step_cnt;
        public double yaw;
        public double pitch;
        
        public MyHit(final Vec3 pos, final int step_cnt, final double player_yaw, final double player_pitch) {
            this.pos = pos;
            this.step_cnt = step_cnt;
            this.yaw = player_yaw;
            this.pitch = player_pitch;
        }
    }
}
