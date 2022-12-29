// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import net.minecraft.item.Item;
import java.util.Iterator;
import java.util.List;
import com.dimchig.bedwarsbro.Main;
import java.awt.Color;
import net.minecraft.init.Items;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import net.minecraft.util.Vec3;
import net.minecraft.client.Minecraft;

public class GuiResourceHologram
{
    static Minecraft mc;
    private int[] last_item_cnt;
    private int[] last_item_time_counter;
    
    public GuiResourceHologram() {
        this.last_item_cnt = new int[] { 0, 0, 0, 0 };
        this.last_item_time_counter = new int[] { 0, 0, 0, 0 };
        GuiResourceHologram.mc = Minecraft.func_71410_x();
    }
    
    public void draw(final Vec3 playerPos) {
        final List<Entity> items = (List<Entity>)GuiResourceHologram.mc.field_71441_e.field_72996_f;
        final ArrayList<GuiMinimap.PosItem> my_items = new ArrayList<GuiMinimap.PosItem>();
        final int total_iron = 0;
        final int cnt_iron = 0;
        for (final Entity en : items) {
            if (en instanceof EntityItem) {
                final EntityItem itemEntity = (EntityItem)en;
                final Item item = itemEntity.func_92059_d().func_77973_b();
                if (item == null) {
                    continue;
                }
                int item_type = -1;
                if (item == Items.field_151166_bC) {
                    item_type = 0;
                }
                else if (item == Items.field_151045_i) {
                    item_type = 1;
                }
                else if (item == Items.field_151043_k) {
                    item_type = 2;
                }
                else if (item == Items.field_151042_j) {
                    item_type = 3;
                }
                final int cnt = itemEntity.func_92059_d().field_77994_a;
                if (en.field_70128_L) {
                    continue;
                }
                boolean isFound = false;
                for (final GuiMinimap.PosItem p : my_items) {
                    if (p.type != item_type) {
                        continue;
                    }
                    final double dist = Math.sqrt(Math.pow(p.x - en.field_70165_t, 2.0) + Math.pow(p.z - en.field_70161_v, 2.0));
                    if (dist < 3.0) {
                        final GuiMinimap.PosItem posItem = p;
                        posItem.cnt += cnt;
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    continue;
                }
                my_items.add(new GuiMinimap.PosItem(en.field_70165_t, en.field_70163_u, en.field_70161_v, item_type, cnt));
            }
        }
        for (final GuiMinimap.PosItem item2 : my_items) {
            if (item2.type < 0) {
                continue;
            }
            Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
            if (item2.type == 3) {
                color = new Color(0.5f, 0.5f, 0.5f, 1.0f);
            }
            else if (item2.type == 2) {
                color = new Color(1.0f, 0.85f, 0.0f, 1.0f);
            }
            else if (item2.type == 1) {
                color = new Color(0.0f, 1.0f, 1.0f, 1.0f);
            }
            else if (item2.type == 0) {
                color = new Color(0.0f, 1.0f, 0.0f, 1.0f);
            }
            Main.draw3DText.drawText(playerPos, new Vec3(item2.x, item2.y + 1.5, item2.z), GuiResourceHologram.mc.field_71439_g, "" + item2.cnt, color.getRGB());
        }
    }
}
