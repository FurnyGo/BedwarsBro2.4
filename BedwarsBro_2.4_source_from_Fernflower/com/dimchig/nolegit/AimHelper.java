package com.dimchig.nolegit;

import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.Vec3;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class AimHelper {

   static Minecraft mc;
   static double aim_speed = 0.02D;
   public static boolean isActive;
   static double aim_treshold = 50.0D;


   public AimHelper() {
      mc = Minecraft.func_71410_x();
   }

   public void toggle() {
      isActive = !isActive;
   }

   @SubscribeEvent
   public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
      if(isActive) {
         MyChatListener var10000 = Main.chatListener;
         if(MyChatListener.IS_IN_GAME) {
            try {
               EntityPlayerSP e = mc.field_71439_g;
               Vec3 playerPos = this.getVectorWithPartialTicks(e, event.partialTicks);
               if(!this.isHoldingSword(e)) {
                  return;
               }

               EntityPlayer closestPlayer = this.getClosestPlayer(e, playerPos, event.partialTicks);
               if(closestPlayer == null) {
                  return;
               }

               boolean isEnviromentSafe = this.isEnviromentSafe(e, playerPos);
               if(!isEnviromentSafe) {
                  return;
               }

               Vec3 closestPlayerPos = this.getVectorWithPartialTicks(closestPlayer, event.partialTicks);
               double dX = playerPos.field_72450_a - closestPlayerPos.field_72450_a;
               double dY = playerPos.field_72448_b - closestPlayerPos.field_72448_b;
               double dZ = playerPos.field_72449_c - closestPlayerPos.field_72449_c;
               double t_yaw = this.myMap(Math.atan2(dZ, dX), -3.141592653589793D, 3.141592653589793D, -180.0D, 180.0D) + 90.0D;
               double t_pitch = this.myMap(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + 3.141592653589793D, 3.141592653589793D, 6.283185307179586D, 90.0D, -90.0D);
               this.smoothRotate(e, playerPos, closestPlayer, closestPlayerPos, t_yaw, t_pitch);
            } catch (Exception var17) {
               var17.printStackTrace();
            }

            return;
         }
      }

   }

   private boolean isEnviromentSafe(EntityPlayerSP player, Vec3 playerPos) {
      if(player.func_70093_af()) {
         return false;
      } else if(mc.field_71462_r != null) {
         return false;
      } else {
         byte range = 1;
         int x = (int)Math.floor(player.field_70165_t);
         int y = (int)Math.floor(player.field_70163_u);
         int z = (int)Math.floor(player.field_70161_v);
         int block_cnt = 0;
         WorldClient world = mc.field_71441_e;
         MutableBlockPos blockPos = new MutableBlockPos(0, 0, 0);

         int must_have_blocks;
         for(must_have_blocks = -range; must_have_blocks <= range; ++must_have_blocks) {
            int zi = -range;

            while(zi <= range) {
               boolean isBlockFound = false;
               int yi = 0;

               while(true) {
                  if(yi >= -2) {
                     blockPos.func_181079_c(x + must_have_blocks, y + yi, z + zi);
                     IBlockState state = world.func_180495_p(blockPos);
                     if(state == null || state.func_177230_c() == Blocks.field_150350_a) {
                        --yi;
                        continue;
                     }

                     isBlockFound = true;
                  }

                  if(isBlockFound) {
                     ++block_cnt;
                  }

                  ++zi;
                  break;
               }
            }
         }

         must_have_blocks = (int)Math.pow((double)(range * 2 + 1), 2.0D);
         return must_have_blocks == block_cnt;
      }
   }

   private void smoothRotate(EntityPlayerSP player, Vec3 playerPos, EntityPlayer closestPlayer, Vec3 closestPlayerPos, double target_yaw, double target_pitch) {
      float player_yaw = (float)this.getRealYaw((double)player.field_70177_z);
      target_yaw = (double)((float)this.getRealYaw(target_yaw));
      float player_pitch = player.field_70125_A;
      double dist = closestPlayerPos.func_72438_d(playerPos);
      if(dist <= 5.0D) {
         double yaw_distance = Math.abs((double)player_yaw - target_yaw);
         if(yaw_distance <= aim_treshold) {
            double diff = target_yaw > (double)player_yaw?1.0D:-1.0D;
            double new_yaw = (double)player_yaw + Math.max(diff * 5.0D, Math.abs(target_yaw - (double)player_yaw)) * diff * aim_speed;
            double new_pitch = (double)player_pitch;
            HintsFinder.rotateTo(Minecraft.func_71410_x().field_71439_g, (float)new_yaw, (float)new_pitch);
         }
      }
   }

   private void GL_start(Vec3 playerPos) {
      GL11.glPushMatrix();
      GL11.glPushAttrib(8192);
      GL11.glTranslated(-playerPos.field_72450_a, -playerPos.field_72448_b, -playerPos.field_72449_c);
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GlStateManager.func_179084_k();
      GL11.glLineWidth(1.0F);
   }

   private void GL_end() {
      GL11.glPopAttrib();
      GL11.glPopMatrix();
   }

   private boolean isHoldingSword(EntityPlayerSP player) {
      if(player.func_71045_bC() == null) {
         return false;
      } else {
         String[] items = new String[]{"sword", "stick"};
         String[] var3 = items;
         int var4 = items.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            if(player.func_71045_bC().func_77973_b().getRegistryName().contains(item)) {
               return true;
            }
         }

         return false;
      }
   }

   private double getRealYaw(double yaw) {
      return (yaw % 360.0D + 360.0D) % 360.0D;
   }

   private EntityPlayer getClosestPlayer(EntityPlayerSP mod_player, Vec3 mod_player_pos, float partialTicks) {
      List players = mc.field_71441_e.func_175644_a(EntityPlayer.class, EntitySelectors.field_94557_a);
      if(players != null && players.size() != 0) {
         double mod_player_yaw = this.getRealYaw((double)mod_player.field_70177_z);
         EntityPlayer closestPlayer = null;
         double dist = 1000.0D;
         Iterator var10 = players.iterator();

         while(var10.hasNext()) {
            EntityPlayer en = (EntityPlayer)var10.next();
            if(en != mod_player && en.func_96124_cp() != mod_player.func_96124_cp()) {
               MyChatListener var10000 = Main.chatListener;
               if(MyChatListener.getEntityTeamColor(en) != CustomScoreboard.TEAM_COLOR.NONE) {
                  Vec3 p_pos = this.getVectorWithPartialTicks(en, partialTicks);
                  if(p_pos.func_72438_d(mod_player_pos) <= 5.0D) {
                     double dX = mod_player_pos.field_72450_a - p_pos.field_72450_a;
                     double var23 = mod_player_pos.field_72448_b - p_pos.field_72448_b;
                     double dZ = mod_player_pos.field_72449_c - p_pos.field_72449_c;
                     double t_yaw = this.getRealYaw(this.myMap(Math.atan2(dZ, dX), -3.141592653589793D, 3.141592653589793D, -180.0D, 180.0D) + 90.0D);
                     double d = Math.abs(mod_player_yaw - t_yaw);
                     if(d < dist) {
                        dist = d;
                        closestPlayer = en;
                     }
                  }
               }
            }
         }

         return closestPlayer;
      } else {
         return null;
      }
   }

   private double myMap(double value, double leftMin, double leftMax, double rightMin, double rightMax) {
      double leftSpan = leftMax - leftMin;
      double rightSpan = rightMax - rightMin;
      double valueScaled = (value - leftMin) / leftSpan;
      return rightMin + valueScaled * rightSpan;
   }

   private Vec3 getVectorWithPartialTicks(Entity en, float partialTicks) {
      double x = en.field_70169_q + (en.field_70165_t - en.field_70169_q) * (double)partialTicks;
      double y = en.field_70167_r + (en.field_70163_u - en.field_70167_r) * (double)partialTicks;
      double z = en.field_70166_s + (en.field_70161_v - en.field_70166_s) * (double)partialTicks;
      return new Vec3(x, y, z);
   }

   private void setLineColor(Color color) {
      GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 1.0F);
   }

}
