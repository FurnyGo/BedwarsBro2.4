// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;

public class TakeMaxSlotBlocks
{
    static Minecraft mc;
    public boolean isActive;
    
    public TakeMaxSlotBlocks() {
        TakeMaxSlotBlocks.mc = Minecraft.func_71410_x();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        this.isActive = Main.getConfigBool(Main.CONFIG_MSG.TAKE_BLOCKS_FROM_MAX_SLOT);
    }
    
    public void handle() {
        if (!this.isActive) {
            return;
        }
        if (TakeMaxSlotBlocks.mc.field_71439_g == null) {
            return;
        }
        final MyChatListener chatListener = Main.chatListener;
        if (!MyChatListener.IS_IN_GAME) {
            return;
        }
        final ItemStack is = TakeMaxSlotBlocks.mc.field_71439_g.func_71045_bC();
        if (is == null || is.func_77973_b() != Item.func_150898_a(Blocks.field_150325_L)) {
            return;
        }
        int select_type = 0;
        final int treshold = 16;
        if (is.field_77994_a <= treshold) {
            select_type = 1;
        }
        if (is.field_77994_a <= 4) {
            select_type = 2;
        }
        int max_slot_idx = -1;
        int max_stack_size = -1;
        final int current_slot = TakeMaxSlotBlocks.mc.field_71439_g.field_71071_by.field_70461_c;
        int i = 0;
        while (true) {
            final int n = i;
            final InventoryPlayer field_71071_by = TakeMaxSlotBlocks.mc.field_71439_g.field_71071_by;
            if (n >= InventoryPlayer.func_70451_h()) {
                break;
            }
            final ItemStack stack = TakeMaxSlotBlocks.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null) {
                final Item item = stack.func_77973_b();
                if (item != null) {
                    if (stack.func_82833_r() != null) {
                        if (item.func_77658_a().contains("tile.cloth")) {
                            if (select_type == 0 && i >= current_slot && stack.field_77994_a > treshold) {
                                max_stack_size = stack.field_77994_a;
                                max_slot_idx = i;
                            }
                            else if (select_type == 1 && stack.field_77994_a > treshold && i != TakeMaxSlotBlocks.mc.field_71439_g.field_71071_by.field_70461_c) {
                                max_slot_idx = i;
                            }
                            else if (select_type == 2 && stack.field_77994_a > max_stack_size) {
                                max_stack_size = stack.field_77994_a;
                                max_slot_idx = i;
                            }
                        }
                    }
                }
            }
            ++i;
        }
        if (max_slot_idx == -1 || max_slot_idx == current_slot) {
            return;
        }
        TakeMaxSlotBlocks.mc.field_71439_g.field_71071_by.field_70461_c = max_slot_idx;
    }
}
