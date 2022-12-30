package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class AutoWaterDrop {

   static Minecraft mc;
   public boolean isPressed = false;
   public boolean isWaterDropStarted = false;
   KeyBinding keyUse;
   private int delay_cnt = -1;


   public AutoWaterDrop() {
      mc = Minecraft.func_71410_x();
      this.isPressed = false;
      this.keyUse = mc.field_71474_y.field_74313_G;
   }

   public void check(EntityPlayerSP player, Vec3 pos) {
      if(player.field_70181_x < -1.0D) {
         WorldClient world = mc.field_71441_e;
         double dist = -1.0D;

         for(int hasWater = (int)pos.field_72448_b - 1; hasWater > 0; --hasWater) {
            if(world.func_180495_p(new BlockPos((int)pos.field_72450_a, hasWater, (int)pos.field_72449_c)).func_177230_c() != Blocks.field_150350_a) {
               dist = pos.field_72448_b - (double)hasWater - 1.0D;
               break;
            }
         }

         if(dist == -1.0D) {
            return;
         }

         boolean var10 = false;
         if(player.field_71071_by != null && player.field_71071_by.field_70462_a != null) {
            if(player.field_71071_by.func_70448_g() != null && player.field_71071_by.func_70448_g().func_77973_b() == Items.field_151131_as) {
               if(player.field_71071_by.func_70448_g() != null && player.field_71071_by.func_70448_g().func_77973_b() == Items.field_151131_as) {
                  var10 = true;
               }
            } else {
               int object = 0;

               while(true) {
                  InventoryPlayer var10001 = player.field_71071_by;
                  if(object >= InventoryPlayer.func_70451_h()) {
                     break;
                  }

                  ItemStack stack = player.field_71071_by.field_70462_a[object];
                  if(stack != null) {
                     Item item = stack.func_77973_b();
                     if(item != null && item == Items.field_151131_as) {
                        if(dist < 20.0D) {
                           player.field_71071_by.field_70461_c = object;
                        }

                        var10 = true;
                        break;
                     }
                  }

                  ++object;
               }
            }
         }

         if(!var10) {
            return;
         }

         if(!this.isWaterDropStarted) {
            this.isWaterDropStarted = true;
            this.isPressed = false;
            ChatSender.addText(MyChatListener.PREFIX_WATER_DROP + "&fАктивирован");
         }

         if(dist < 10.0D) {
            HintsFinder.rotateTo(player, player.field_70177_z, 90.0F);
            MovingObjectPosition var11 = mc.field_71476_x;
            if(var11.field_72313_a == MovingObjectType.BLOCK && var11.field_178784_b == EnumFacing.UP) {
               this.placeWater();
            }
         }
      } else if(mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70181_x > -0.05D && this.isWaterDropStarted) {
         this.isWaterDropStarted = false;
         this.placeWater();
         this.isPressed = false;
      }

   }

   void placeWater() {
      if(mc.field_71439_g.func_71045_bC() != null) {
         this.isPressed = true;
         mc.field_71442_b.func_78769_a(mc.field_71439_g, mc.field_71441_e, mc.field_71439_g.func_71045_bC());
      }
   }

   void pressUp() {}
}
