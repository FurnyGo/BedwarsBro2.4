// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.nolegit;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.util.EntitySelectors;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.Minecraft;

public class AimHelper
{
    static Minecraft mc;
    static double aim_speed;
    public static boolean isActive;
    static double aim_treshold;
    
    public AimHelper() {
        AimHelper.mc = Minecraft.func_71410_x();
    }
    
    public void toggle() {
        AimHelper.isActive = !AimHelper.isActive;
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        if (AimHelper.isActive) {
            final MyChatListener chatListener = Main.chatListener;
            if (MyChatListener.IS_IN_GAME) {
                try {
                    final EntityPlayerSP player = AimHelper.mc.field_71439_g;
                    final Vec3 playerPos = this.getVectorWithPartialTicks((Entity)player, event.partialTicks);
                    if (!this.isHoldingSword(player)) {
                        return;
                    }
                    final EntityPlayer closestPlayer = this.getClosestPlayer(player, playerPos, event.partialTicks);
                    if (closestPlayer == null) {
                        return;
                    }
                    final boolean isEnviromentSafe = this.isEnviromentSafe(player, playerPos);
                    if (!isEnviromentSafe) {
                        return;
                    }
                    final Vec3 closestPlayerPos = this.getVectorWithPartialTicks((Entity)closestPlayer, event.partialTicks);
                    final double dX = playerPos.field_72450_a - closestPlayerPos.field_72450_a;
                    final double dY = playerPos.field_72448_b - closestPlayerPos.field_72448_b;
                    final double dZ = playerPos.field_72449_c - closestPlayerPos.field_72449_c;
                    final double t_yaw = this.myMap(Math.atan2(dZ, dX), -3.141592653589793, 3.141592653589793, -180.0, 180.0) + 90.0;
                    final double t_pitch = this.myMap(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + 3.141592653589793, 3.141592653589793, 6.283185307179586, 90.0, -90.0);
                    this.smoothRotate(player, playerPos, closestPlayer, closestPlayerPos, t_yaw, t_pitch);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private boolean isEnviromentSafe(final EntityPlayerSP player, final Vec3 playerPos) {
        if (player.func_70093_af()) {
            return false;
        }
        if (AimHelper.mc.field_71462_r != null) {
            return false;
        }
        final int range = 1;
        final int x = (int)Math.floor(player.field_70165_t);
        final int y = (int)Math.floor(player.field_70163_u);
        final int z = (int)Math.floor(player.field_70161_v);
        int block_cnt = 0;
        final World world = (World)AimHelper.mc.field_71441_e;
        final BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(0, 0, 0);
        for (int xi = -range; xi <= range; ++xi) {
            for (int zi = -range; zi <= range; ++zi) {
                boolean isBlockFound = false;
                for (int yi = 0; yi >= -2; --yi) {
                    blockPos.func_181079_c(x + xi, y + yi, z + zi);
                    final IBlockState state = world.func_180495_p((BlockPos)blockPos);
                    if (state != null && state.func_177230_c() != Blocks.field_150350_a) {
                        isBlockFound = true;
                        break;
                    }
                }
                if (isBlockFound) {
                    ++block_cnt;
                }
            }
        }
        final int must_have_blocks = (int)Math.pow(range * 2 + 1, 2.0);
        return must_have_blocks == block_cnt;
    }
    
    private void smoothRotate(final EntityPlayerSP player, final Vec3 playerPos, final EntityPlayer closestPlayer, final Vec3 closestPlayerPos, double target_yaw, final double target_pitch) {
        final float player_yaw = (float)this.getRealYaw(player.field_70177_z);
        target_yaw = (float)this.getRealYaw(target_yaw);
        final float player_pitch = player.field_70125_A;
        final double dist = closestPlayerPos.func_72438_d(playerPos);
        if (dist > 5.0) {
            return;
        }
        final double yaw_distance = Math.abs(player_yaw - target_yaw);
        if (yaw_distance > AimHelper.aim_treshold) {
            return;
        }
        final double diff = (target_yaw > player_yaw) ? 1.0 : -1.0;
        final double new_yaw = player_yaw + Math.max(diff * 5.0, Math.abs(target_yaw - player_yaw)) * diff * AimHelper.aim_speed;
        final double new_pitch = player_pitch;
        HintsFinder.rotateTo((Entity)Minecraft.func_71410_x().field_71439_g, (float)new_yaw, (float)new_pitch);
    }
    
    private void GL_start(final Vec3 playerPos) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        GL11.glTranslated(-playerPos.field_72450_a, -playerPos.field_72448_b, -playerPos.field_72449_c);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GlStateManager.func_179084_k();
        GL11.glLineWidth(1.0f);
    }
    
    private void GL_end() {
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    private boolean isHoldingSword(final EntityPlayerSP player) {
        if (player.func_71045_bC() == null) {
            return false;
        }
        final String[] array;
        final String[] items = array = new String[] { "sword", "stick" };
        for (final String item : array) {
            if (player.func_71045_bC().func_77973_b().getRegistryName().contains(item)) {
                return true;
            }
        }
        return false;
    }
    
    private double getRealYaw(final double yaw) {
        return (yaw % 360.0 + 360.0) % 360.0;
    }
    
    private EntityPlayer getClosestPlayer(final EntityPlayerSP mod_player, final Vec3 mod_player_pos, final float partialTicks) {
        final List<EntityPlayer> players = (List<EntityPlayer>)AimHelper.mc.field_71441_e.func_175644_a((Class)EntityPlayer.class, EntitySelectors.field_94557_a);
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
                final MyChatListener chatListener = Main.chatListener;
                if (MyChatListener.getEntityTeamColor(en) == CustomScoreboard.TEAM_COLOR.NONE) {
                    continue;
                }
                final Vec3 p_pos = this.getVectorWithPartialTicks((Entity)en, partialTicks);
                if (p_pos.func_72438_d(mod_player_pos) > 5.0) {
                    continue;
                }
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
    
    private void setLineColor(final Color color) {
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 1.0f);
    }
    
    static {
        AimHelper.aim_speed = 0.02;
        AimHelper.aim_treshold = 50.0;
    }
}
