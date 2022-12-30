package com.dimchig.bedwarsbro.hints;

import java.util.Random;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;

public class LobbyBlockPlacer {

   public static boolean state;
   public static int block_idx = 0;
   public static EnumDyeColor[] colors = new EnumDyeColor[]{EnumDyeColor.RED, EnumDyeColor.ORANGE, EnumDyeColor.YELLOW, EnumDyeColor.LIME, EnumDyeColor.LIGHT_BLUE, EnumDyeColor.BLUE, EnumDyeColor.PURPLE, EnumDyeColor.MAGENTA, EnumDyeColor.PINK};


   public static void place() {
      if(state) {
         Minecraft mc = Minecraft.func_71410_x();
         if(mc != null && mc.field_71439_g != null) {
            EntityPlayerSP player = mc.field_71439_g;
            new Random();
            BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u - 1.0D, player.field_70161_v);
            if(Minecraft.func_71410_x().field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a) {
               block_idx = (block_idx + 1) % colors.length;
               WorldClient var10000 = Minecraft.func_71410_x().field_71441_e;
               IBlockState var10002 = Blocks.field_150325_L.func_176223_P();
               BlockStainedGlass var10003 = Blocks.field_150399_cn;
               var10000.func_175656_a(pos, var10002.func_177226_a(BlockStainedGlass.field_176547_a, colors[block_idx]));
            }
         }
      }
   }

}
