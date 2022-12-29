// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumDyeColor;

public class LobbyBlockPlacer
{
    public static boolean state;
    public static int block_idx;
    public static EnumDyeColor[] colors;
    
    public static void place() {
        if (!LobbyBlockPlacer.state) {
            return;
        }
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc == null || mc.field_71439_g == null) {
            return;
        }
        final EntityPlayerSP player = mc.field_71439_g;
        final Random rnd = new Random();
        final BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u - 1.0, player.field_70161_v);
        if (Minecraft.func_71410_x().field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
            return;
        }
        LobbyBlockPlacer.block_idx = (LobbyBlockPlacer.block_idx + 1) % LobbyBlockPlacer.colors.length;
        final WorldClient field_71441_e = Minecraft.func_71410_x().field_71441_e;
        final BlockPos blockPos = pos;
        final IBlockState func_176223_P = Blocks.field_150325_L.func_176223_P();
        final BlockStainedGlass field_150399_cn = Blocks.field_150399_cn;
        field_71441_e.func_175656_a(blockPos, func_176223_P.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)LobbyBlockPlacer.colors[LobbyBlockPlacer.block_idx]));
    }
    
    static {
        LobbyBlockPlacer.block_idx = 0;
        LobbyBlockPlacer.colors = new EnumDyeColor[] { EnumDyeColor.RED, EnumDyeColor.ORANGE, EnumDyeColor.YELLOW, EnumDyeColor.LIME, EnumDyeColor.LIGHT_BLUE, EnumDyeColor.BLUE, EnumDyeColor.PURPLE, EnumDyeColor.MAGENTA, EnumDyeColor.PINK };
    }
}
