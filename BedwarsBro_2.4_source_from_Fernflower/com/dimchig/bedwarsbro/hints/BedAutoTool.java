package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.gui.GuiMinimap;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.lwjgl.input.Mouse;

public class BedAutoTool {

   static Minecraft mc;
   public boolean isActive = false;


   public BedAutoTool() {
      mc = Minecraft.func_71410_x();
      this.updateBooleans();
   }

   public void updateBooleans() {
      this.isActive = HintsValidator.isBedAutoToolActive();
   }

   public void handleTools() {
      if(this.isActive) {
         if(Mouse.isButtonDown(0)) {
            MovingObjectPosition obj = mc.field_71476_x;
            if(obj != null && obj.field_72313_a == MovingObjectType.BLOCK) {
               IBlockState state = mc.field_71441_e.func_180495_p(obj.func_178782_a());
               if(state != null && state.func_177230_c() != null) {
                  ItemStack is = mc.field_71439_g.func_71045_bC();
                  if(is != null && is.func_77973_b() != null) {
                     String name = is.func_77973_b().func_77658_a();
                     if(name.contains("pickaxe") || name.contains("hatchet") || name.contains("shears")) {
                        double min_dist = 99999.0D;
                        GuiMinimap var10000 = Main.minimap;
                        Iterator block = GuiMinimap.bedsFound.iterator();

                        while(block.hasNext()) {
                           GuiMinimap.MyBed available_blocks = (GuiMinimap.MyBed)block.next();
                           double isFound = Math.sqrt(Math.pow(mc.field_71439_g.field_70165_t - (double)available_blocks.pos.func_177958_n(), 2.0D) + Math.pow(mc.field_71439_g.field_70161_v - (double)available_blocks.pos.func_177952_p(), 2.0D));
                           if(isFound < min_dist) {
                              min_dist = isFound;
                           }
                        }

                        if(min_dist <= 20.0D) {
                           Block var16 = state.func_177230_c();
                           Block[] var17 = new Block[]{Blocks.field_150325_L, Blocks.field_150344_f, Blocks.field_150468_ap, Blocks.field_150406_ce, Blocks.field_150377_bs, Blocks.field_150343_Z};
                           boolean var18 = false;
                           Block[] pickaxe_slot = var17;
                           int axe_slot = var17.length;

                           int shears_slot;
                           for(shears_slot = 0; shears_slot < axe_slot; ++shears_slot) {
                              Block slot = pickaxe_slot[shears_slot];
                              if(slot == var16) {
                                 var18 = true;
                                 break;
                              }
                           }

                           if(var18) {
                              int var19 = -1;
                              axe_slot = -1;
                              shears_slot = -1;
                              int var20 = 0;

                              while(true) {
                                 InventoryPlayer var10001 = mc.field_71439_g.field_71071_by;
                                 if(var20 >= InventoryPlayer.func_70451_h()) {
                                    var20 = -1;
                                    if(shears_slot != -1 && var16 == Blocks.field_150325_L) {
                                       var20 = shears_slot;
                                    } else if(var19 != -1 && (var16 == Blocks.field_150377_bs || var16 == Blocks.field_150343_Z || var16 == Blocks.field_150406_ce)) {
                                       var20 = var19;
                                    } else if(axe_slot != -1 && (var16 == Blocks.field_150344_f || var16 == Blocks.field_150468_ap)) {
                                       var20 = axe_slot;
                                    }

                                    if(var20 == -1) {
                                       return;
                                    } else {
                                       mc.field_71439_g.field_71071_by.field_70461_c = var20;
                                       return;
                                    }
                                 }

                                 ItemStack stack = mc.field_71439_g.field_71071_by.field_70462_a[var20];
                                 if(stack != null) {
                                    Item item = stack.func_77973_b();
                                    if(item != null && stack.func_82833_r() != null) {
                                       if(item.func_77658_a().contains("pickaxe")) {
                                          var19 = var20;
                                       }

                                       if(item.func_77658_a().contains("hatchet")) {
                                          axe_slot = var20;
                                       }

                                       if(item.func_77658_a().contains("shears")) {
                                          shears_slot = var20;
                                       }
                                    }
                                 }

                                 ++var20;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
