package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.BWBed;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ZeroDeathHandler {

   static Minecraft mc;
   public static boolean isActive = false;
   public static int height_treshold = 10;
   public static boolean fall_check = true;
   public static double health_treshold = 5.0D;
   public static boolean health_checker = true;
   public static boolean isWriteInChat = true;
   public static int MIN_TIME_INTERVAL = 9000;
   long last_time_rejoin = 0L;
   double min_fall_speed = 0.7D;
   double temp_val_1 = 0.0D;
   double temp_val_2 = 0.0D;
   double temp_val_3 = 0.0D;


   public ZeroDeathHandler() {
      mc = Minecraft.func_71410_x();
      isActive = false;
   }

   public void updateBooleans() {
      isActive = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH);
      height_treshold = Main.getConfigInt(Main.CONFIG_MSG.ZERO_DEATH_HEIGHT_TRESHOLD);
      fall_check = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH_FALL_CHECK);
      health_treshold = Main.getConfigDouble(Main.CONFIG_MSG.ZERO_DEATH_HEALTH_TRESHOLD);
      health_checker = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH_HEALTH_CHECK_NEARBY);
      isWriteInChat = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH_WRITE_IN_CHAT);
   }

   public void scan() {
      if(mc != null && mc.field_71439_g != null) {
         int bed_pos_y = 0;
         if(!mc.func_71356_B()) {
            label61: {
               if(isActive) {
                  MyChatListener var10000 = Main.chatListener;
                  if(MyChatListener.IS_IN_GAME) {
                     var10000 = Main.chatListener;
                     BWBed nearby_call = MyChatListener.GAME_BED;
                     if(nearby_call == null) {
                        return;
                     }

                     bed_pos_y = nearby_call.part1_posY;
                     if(!this.isPlayerHasArmour()) {
                        return;
                     }
                     break label61;
                  }
               }

               return;
            }
         }

         if(height_treshold != 999 && (double)bed_pos_y - mc.field_71439_g.field_70163_u >= (double)height_treshold && mc.field_71439_g.field_70181_x < -this.min_fall_speed && !this.isWaterUnderMe()) {
            this.rejoin(0, (double)bed_pos_y - mc.field_71439_g.field_70163_u);
         } else if(fall_check && !Main.autoWaterDrop.isWaterDropStarted && this.isDeadFromFall() && mc.field_71439_g.field_70181_x < -this.min_fall_speed && !this.isWaterUnderMe() && !this.isHoldingWaterBucket()) {
            this.rejoin(1, (double)mc.field_71439_g.field_70143_R);
         } else if(health_treshold != 999.0D && (double)mc.field_71439_g.func_110143_aJ() <= health_treshold) {
            if(health_checker) {
               int nearby_call1 = this.isHealthChecker();
               if(nearby_call1 != 0) {
                  this.rejoin(2, (double)mc.field_71439_g.func_110143_aJ(), (double)nearby_call1);
               }

            } else {
               this.rejoin(2, (double)mc.field_71439_g.func_110143_aJ());
            }
         }
      }
   }

   @SubscribeEvent
   public void onDamage(LivingHurtEvent e) {}

   public double calculateCurrentFallDamage() {
      double fall_damage = (double)mc.field_71439_g.field_70143_R;
      if(fall_damage <= 3.0D) {
         return 0.0D;
      } else {
         double epf = this.getInventoryEPF();
         double armour_defence = epf * 2.0D;
         fall_damage = (1.0D - armour_defence / 100.0D) * (double)(mc.field_71439_g.field_70143_R - 2.0F);
         Collection pe = mc.field_71439_g.func_70651_bq();
         if(pe.size() > 0) {
            Iterator var8 = pe.iterator();

            while(var8.hasNext()) {
               PotionEffect effect = (PotionEffect)var8.next();
               if(effect.func_76456_a() == 8) {
                  fall_damage -= (double)effect.func_76458_c();
                  break;
               }
            }
         }

         return fall_damage;
      }
   }

   public double getInventoryEPF() {
      if(mc.field_71439_g.func_70035_c() == null) {
         return 0.0D;
      } else {
         int eps_total = 0;

         try {
            for(int ex = 0; ex < 4; ++ex) {
               ItemStack itemStack = mc.field_71439_g.func_70035_c()[ex];
               if(itemStack != null && itemStack.func_77973_b() != null) {
                  Integer val = (Integer)EnchantmentHelper.func_82781_a(itemStack).get(Integer.valueOf(0));
                  if(val != null) {
                     eps_total += val.intValue();
                     if(val.intValue() == 4) {
                        ++eps_total;
                     }
                  }
               }
            }
         } catch (Exception var5) {
            return 0.0D;
         }

         return (double)eps_total;
      }
   }

   public boolean isDeadFromFall() {
      double damage = this.calculateCurrentFallDamage();
      return damage <= 0.0D?false:(double)mc.field_71439_g.func_110143_aJ() < damage;
   }

   public boolean isPlayerHasArmour() {
      return mc.field_71439_g.func_70035_c() != null && mc.field_71439_g.func_70035_c()[1] != null;
   }

   public int isHealthChecker() {
      byte nearby_player_radius = 15;
      byte nearby_projectile_radius = 10;
      List entities = mc.field_71441_e.field_73010_i;
      Iterator minPos = entities.iterator();

      while(minPos.hasNext()) {
         EntityPlayer maxPos = (EntityPlayer)minPos.next();
         if(maxPos != null && maxPos.func_70005_c_() != null && maxPos.func_145748_c_() != null && maxPos.func_96124_cp() != mc.field_71439_g.func_96124_cp() && !maxPos.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
            double box = maxPos.func_70011_f(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
            if(box < (double)nearby_player_radius) {
               return 1;
            }
         }
      }

      BlockPos minPos1 = new BlockPos(mc.field_71439_g.field_70165_t - (double)nearby_projectile_radius, mc.field_71439_g.field_70163_u - (double)nearby_projectile_radius, mc.field_71439_g.field_70161_v - (double)nearby_projectile_radius);
      BlockPos maxPos1 = new BlockPos(mc.field_71439_g.field_70165_t + (double)nearby_projectile_radius, mc.field_71439_g.field_70163_u + (double)nearby_projectile_radius, mc.field_71439_g.field_70161_v + (double)nearby_projectile_radius);
      AxisAlignedBB box1 = new AxisAlignedBB(minPos1, maxPos1);
      List arr_arrows = mc.field_71441_e.func_72872_a(EntityArrow.class, box1);
      if(arr_arrows != null && arr_arrows.size() > 0) {
         Iterator var8 = arr_arrows.iterator();

         while(var8.hasNext()) {
            EntityArrow en = (EntityArrow)var8.next();
            Entity sender = en.field_70250_c;
            if(sender != null && sender instanceof EntityPlayer && sender != mc.field_71439_g && en.field_70163_u - en.field_70167_r != 0.0D) {
               return 2;
            }
         }
      }

      return 0;
   }

   public boolean isHoldingWaterBucket() {
      ItemStack is = mc.field_71439_g.func_71045_bC();
      return is == null?false:is.func_77973_b() == Items.field_151131_as;
   }

   public boolean isWaterUnderMe() {
      EntityPlayerSP player = mc.field_71439_g;
      if(player == null) {
         return false;
      } else if(player.field_70163_u < 10.0D) {
         return false;
      } else {
         int px = (int)Math.floor(player.field_70165_t);
         int pz = (int)Math.floor(player.field_70161_v);
         MutableBlockPos pos = new MutableBlockPos();
         WorldClient world = mc.field_71441_e;

         for(int i = (int)player.field_70163_u; i > 0; --i) {
            pos = pos.func_181079_c(px, i, pz);
            IBlockState state = world.func_180495_p(pos);
            if(state != null) {
               Block b = state.func_177230_c();
               if(b == Blocks.field_150355_j) {
                  return true;
               }

               if(b != Blocks.field_150350_a) {
                  return false;
               }
            }
         }

         return false;
      }
   }

   public void rejoin(int reason, double val) {
      this.rejoin(reason, val, 0.0D);
   }

   public void rejoin(int reason, double val, double extra_val) {
      long t = (new Date()).getTime();
      if(t - this.last_time_rejoin >= (long)MIN_TIME_INTERVAL) {
         this.last_time_rejoin = t;
         ChatSender.sendText("/leave");
         Main.myTickEvent.zeroDeathHandlerRejoinVar = 10;
         if(isWriteInChat) {
            String hover = "&fПричина: ";
            StringBuilder var10000;
            MyChatListener var10001;
            if(reason == 0) {
               var10000 = (new StringBuilder()).append(hover).append("Падение ниже кровати на &e").append(height_treshold).append("&f блоков. В твоем случае было &c").append((int)val).append("&f блок");
               var10001 = Main.chatListener;
               hover = var10000.append(MyChatListener.getNumberEnding((int)val, "", "а", "ов")).toString();
            } else if(reason == 1) {
               var10000 = (new StringBuilder()).append(hover).append("Падение с &cсмертельной высоты&f. Ты падал с высоты в &c").append((int)val).append("&f блок");
               var10001 = Main.chatListener;
               hover = var10000.append(MyChatListener.getNumberEnding((int)val, "", "а", "ов")).toString();
            } else if(reason == 2) {
               hover = hover + "количество здоровья <= &e" + health_treshold + "&f. В твоем случае было &c" + (new DecimalFormat("0.0")).format(val) + "&f здоровья";
               if(extra_val == 1.0D) {
                  hover = hover + " и рядом был &cигрок";
               }

               if(extra_val == 2.0D) {
                  hover = hover + " и рядом летела &cстрела";
               }

               if(extra_val == 3.0D) {
                  hover = hover + " и рядом летел &cфаербол";
               }
            } else {
               hover = hover + "&cНеизвестна...";
            }

            MyChatListener var9 = Main.chatListener;
            MyChatListener.PREFIX_ZERO_DEATH = "&r&a&lZero&c&lDeath&8 ▸ §r";
            var10000 = new StringBuilder();
            var10001 = Main.chatListener;
            ChatSender.addHoverText(var10000.append(MyChatListener.PREFIX_ZERO_DEATH).append("&fПерезаход...").toString(), hover);
         }

      }
   }

}
