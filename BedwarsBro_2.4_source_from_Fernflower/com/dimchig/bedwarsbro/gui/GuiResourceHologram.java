package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.gui.GuiMinimap;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.Vec3;

public class GuiResourceHologram {

   static Minecraft mc;
   private int[] last_item_cnt = new int[]{0, 0, 0, 0};
   private int[] last_item_time_counter = new int[]{0, 0, 0, 0};


   public GuiResourceHologram() {
      mc = Minecraft.func_71410_x();
   }

   public void draw(Vec3 playerPos) {
      List items = mc.field_71441_e.field_72996_f;
      ArrayList my_items = new ArrayList();
      boolean total_iron = false;
      boolean cnt_iron = false;
      Iterator var6 = items.iterator();

      while(var6.hasNext()) {
         Entity item = (Entity)var6.next();
         if(item instanceof EntityItem) {
            EntityItem color = (EntityItem)item;
            Item item1 = color.func_92059_d().func_77973_b();
            if(item1 != null) {
               byte item_type = -1;
               if(item1 == Items.field_151166_bC) {
                  item_type = 0;
               } else if(item1 == Items.field_151045_i) {
                  item_type = 1;
               } else if(item1 == Items.field_151043_k) {
                  item_type = 2;
               } else if(item1 == Items.field_151042_j) {
                  item_type = 3;
               }

               int cnt = color.func_92059_d().field_77994_a;
               if(!item.field_70128_L) {
                  boolean isFound = false;
                  Iterator var13 = my_items.iterator();

                  while(true) {
                     if(var13.hasNext()) {
                        GuiMinimap.PosItem p = (GuiMinimap.PosItem)var13.next();
                        if(p.type != item_type) {
                           continue;
                        }

                        double dist = Math.sqrt(Math.pow(p.x - item.field_70165_t, 2.0D) + Math.pow(p.z - item.field_70161_v, 2.0D));
                        if(dist >= 3.0D) {
                           continue;
                        }

                        p.cnt += cnt;
                        isFound = true;
                     }

                     if(!isFound) {
                        my_items.add(new GuiMinimap.PosItem(item.field_70165_t, item.field_70163_u, item.field_70161_v, item_type, cnt));
                     }
                     break;
                  }
               }
            }
         }
      }

      var6 = my_items.iterator();

      while(var6.hasNext()) {
         GuiMinimap.PosItem item2 = (GuiMinimap.PosItem)var6.next();
         if(item2.type >= 0) {
            Color color1 = new Color(1.0F, 1.0F, 1.0F, 1.0F);
            if(item2.type == 3) {
               color1 = new Color(0.5F, 0.5F, 0.5F, 1.0F);
            } else if(item2.type == 2) {
               color1 = new Color(1.0F, 0.85F, 0.0F, 1.0F);
            } else if(item2.type == 1) {
               color1 = new Color(0.0F, 1.0F, 1.0F, 1.0F);
            } else if(item2.type == 0) {
               color1 = new Color(0.0F, 1.0F, 0.0F, 1.0F);
            }

            Main.draw3DText.drawText(playerPos, new Vec3(item2.x, item2.y + 1.5D, item2.z), mc.field_71439_g, "" + item2.cnt, color1.getRGB());
         }
      }

   }
}
