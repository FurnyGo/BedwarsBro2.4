// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

public class AutoWaterDrop
{
    static Minecraft mc;
    public boolean isPressed;
    public boolean isWaterDropStarted;
    KeyBinding keyUse;
    private int delay_cnt;
    
    public AutoWaterDrop() {
        this.isPressed = false;
        this.isWaterDropStarted = false;
        this.delay_cnt = -1;
        AutoWaterDrop.mc = Minecraft.func_71410_x();
        this.isPressed = false;
        this.keyUse = AutoWaterDrop.mc.field_71474_y.field_74313_G;
    }
    
    public void check(final EntityPlayerSP player, final Vec3 pos) {
        if (player.field_70181_x < -1.0) {
            final World world = (World)AutoWaterDrop.mc.field_71441_e;
            double dist = -1.0;
            for (int y = (int)pos.field_72448_b - 1; y > 0; --y) {
                if (world.func_180495_p(new BlockPos((int)pos.field_72450_a, y, (int)pos.field_72449_c)).func_177230_c() != Blocks.field_150350_a) {
                    dist = pos.field_72448_b - y - 1.0;
                    break;
                }
            }
            if (dist == -1.0) {
                return;
            }
            boolean hasWater = false;
            if (player.field_71071_by != null && player.field_71071_by.field_70462_a != null) {
                if (player.field_71071_by.func_70448_g() == null || player.field_71071_by.func_70448_g().func_77973_b() != Items.field_151131_as) {
                    int i = 0;
                    while (true) {
                        final int n = i;
                        final InventoryPlayer field_71071_by = player.field_71071_by;
                        if (n >= InventoryPlayer.func_70451_h()) {
                            break;
                        }
                        final ItemStack stack = player.field_71071_by.field_70462_a[i];
                        if (stack != null) {
                            final Item item = stack.func_77973_b();
                            if (item != null && item == Items.field_151131_as) {
                                if (dist < 20.0) {
                                    player.field_71071_by.field_70461_c = i;
                                }
                                hasWater = true;
                                break;
                            }
                        }
                        ++i;
                    }
                }
                else if (player.field_71071_by.func_70448_g() != null && player.field_71071_by.func_70448_g().func_77973_b() == Items.field_151131_as) {
                    hasWater = true;
                }
            }
            if (!hasWater) {
                return;
            }
            if (!this.isWaterDropStarted) {
                this.isWaterDropStarted = true;
                this.isPressed = false;
                ChatSender.addText(MyChatListener.PREFIX_WATER_DROP + "&f\u0410\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d");
            }
            if (dist < 10.0) {
                HintsFinder.rotateTo((Entity)player, player.field_70177_z, 90.0f);
                final MovingObjectPosition object = AutoWaterDrop.mc.field_71476_x;
                if (object.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && object.field_178784_b == EnumFacing.UP) {
                    this.placeWater();
                }
            }
        }
        else if (AutoWaterDrop.mc.field_71439_g.field_70122_E && AutoWaterDrop.mc.field_71439_g.field_70181_x > -0.05 && this.isWaterDropStarted) {
            this.isWaterDropStarted = false;
            this.placeWater();
            this.isPressed = false;
        }
    }
    
    void placeWater() {
        if (AutoWaterDrop.mc.field_71439_g.func_71045_bC() == null) {
            return;
        }
        this.isPressed = true;
        AutoWaterDrop.mc.field_71442_b.func_78769_a((EntityPlayer)AutoWaterDrop.mc.field_71439_g, (World)AutoWaterDrop.mc.field_71441_e, AutoWaterDrop.mc.field_71439_g.func_71045_bC());
    }
    
    void pressUp() {
    }
}
