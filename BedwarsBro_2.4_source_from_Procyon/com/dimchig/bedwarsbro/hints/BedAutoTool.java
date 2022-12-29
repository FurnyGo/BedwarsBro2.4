// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.item.Item;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import com.dimchig.bedwarsbro.gui.GuiMinimap;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;

public class BedAutoTool
{
    static Minecraft mc;
    public boolean isActive;
    
    public BedAutoTool() {
        this.isActive = false;
        BedAutoTool.mc = Minecraft.func_71410_x();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        this.isActive = HintsValidator.isBedAutoToolActive();
    }
    
    public void handleTools() {
        if (!this.isActive) {
            return;
        }
        if (!Mouse.isButtonDown(0)) {
            return;
        }
        final MovingObjectPosition obj = BedAutoTool.mc.field_71476_x;
        if (obj == null || obj.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK) {
            return;
        }
        final IBlockState state = BedAutoTool.mc.field_71441_e.func_180495_p(obj.func_178782_a());
        if (state == null || state.func_177230_c() == null) {
            return;
        }
        final ItemStack is = BedAutoTool.mc.field_71439_g.func_71045_bC();
        if (is == null || is.func_77973_b() == null) {
            return;
        }
        final String name = is.func_77973_b().func_77658_a();
        if (!name.contains("pickaxe") && !name.contains("hatchet") && !name.contains("shears")) {
            return;
        }
        double min_dist = 99999.0;
        final GuiMinimap minimap = Main.minimap;
        for (final GuiMinimap.MyBed bed : GuiMinimap.bedsFound) {
            final double dist = Math.sqrt(Math.pow(BedAutoTool.mc.field_71439_g.field_70165_t - bed.pos.func_177958_n(), 2.0) + Math.pow(BedAutoTool.mc.field_71439_g.field_70161_v - bed.pos.func_177952_p(), 2.0));
            if (dist < min_dist) {
                min_dist = dist;
            }
        }
        if (min_dist > 20.0) {
            return;
        }
        final Block block = state.func_177230_c();
        final Block[] available_blocks = { Blocks.field_150325_L, Blocks.field_150344_f, Blocks.field_150468_ap, Blocks.field_150406_ce, Blocks.field_150377_bs, Blocks.field_150343_Z };
        boolean isFound = false;
        for (final Block b : available_blocks) {
            if (b == block) {
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            return;
        }
        int pickaxe_slot = -1;
        int axe_slot = -1;
        int shears_slot = -1;
        int i = 0;
        while (true) {
            final int n = i;
            final InventoryPlayer field_71071_by = BedAutoTool.mc.field_71439_g.field_71071_by;
            if (n >= InventoryPlayer.func_70451_h()) {
                break;
            }
            final ItemStack stack = BedAutoTool.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null) {
                final Item item = stack.func_77973_b();
                if (item != null) {
                    if (stack.func_82833_r() != null) {
                        if (item.func_77658_a().contains("pickaxe")) {
                            pickaxe_slot = i;
                        }
                        if (item.func_77658_a().contains("hatchet")) {
                            axe_slot = i;
                        }
                        if (item.func_77658_a().contains("shears")) {
                            shears_slot = i;
                        }
                    }
                }
            }
            ++i;
        }
        int slot = -1;
        if (shears_slot != -1 && block == Blocks.field_150325_L) {
            slot = shears_slot;
        }
        else if (pickaxe_slot != -1 && (block == Blocks.field_150377_bs || block == Blocks.field_150343_Z || block == Blocks.field_150406_ce)) {
            slot = pickaxe_slot;
        }
        else if (axe_slot != -1 && (block == Blocks.field_150344_f || block == Blocks.field_150468_ap)) {
            slot = axe_slot;
        }
        if (slot == -1) {
            return;
        }
        BedAutoTool.mc.field_71439_g.field_71071_by.field_70461_c = slot;
    }
}
