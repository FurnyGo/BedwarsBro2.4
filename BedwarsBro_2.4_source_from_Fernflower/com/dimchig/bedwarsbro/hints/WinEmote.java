package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.particles.ParticleController;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;

public class WinEmote {

   public static int emoteStage = -1;
   public static int all_positions_idx = -1;
   public static int maxEmoteBlocksPerTick = 10000;
   public static int targetRange = 120;
   public static int currentRange = -1;
   public static BlockPos startingPos = null;
   public static long startingTime = 0L;
   public static ArrayList all_positions = new ArrayList();
   public static CustomScoreboard.TEAM_COLOR emoteStage_team_color;


   public static void handleEmote() {
      if(!MyChatListener.IS_IN_GAME) {
         emoteStage = -1;
         all_positions.clear();
      } else {
         if(emoteStage > 0 && startingPos != null) {
            if((new Date()).getTime() - startingTime > 15000L) {
               emoteStage = -1;
               return;
            }

            if(all_positions_idx >= all_positions.size()) {
               if(currentRange == targetRange) {
                  emoteStage = -1;
                  return;
               }

               fillRange(currentRange);
               ++currentRange;
            }

            ArrayList states = getStates(emoteStage_team_color);
            Random rnd = new Random();
            WorldClient world = Minecraft.func_71410_x().field_71441_e;
            BlockPos pos = null;
            int cnt = 0;

            while(true) {
               ++cnt;
               ++all_positions_idx;
               if(cnt > maxEmoteBlocksPerTick || all_positions_idx >= all_positions.size()) {
                  break;
               }

               pos = (BlockPos)all_positions.get(all_positions_idx);
               if(world.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
                  ++cnt;
                  world.func_175656_a(pos, (IBlockState)states.get(rnd.nextInt(states.size())));
               }
            }
         }

      }
   }

   public static void fillRange(int range) {
      new BlockPos(0, 0, 0);

      for(int xi = -range; xi <= range; ++xi) {
         for(int zi = -range; zi <= range; ++zi) {
            int dist = Math.abs(xi);
            if(Math.abs(xi) < Math.abs(zi)) {
               dist = Math.abs(zi);
            }

            if(dist == range) {
               for(int yi = 0; yi <= 120; ++yi) {
                  BlockPos pos = new BlockPos(startingPos.func_177958_n() + xi, yi, startingPos.func_177952_p() + zi);
                  all_positions.add(pos);
               }
            }
         }
      }

   }

   public static void changeWorldBlocks(CustomScoreboard.TEAM_COLOR team_color) {
      EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
      emoteStage = 1;
      emoteStage_team_color = team_color;
      all_positions = new ArrayList();
      all_positions_idx = -1;
      currentRange = 0;
      startingTime = (new Date()).getTime();
      startingPos = new BlockPos(player);

      for(int i = 0; i < 10; ++i) {
         ParticleController.spawnFinalKillParticles(player.field_70165_t, player.field_70163_u + (double)(player.func_70047_e() / 2.0F), player.field_70161_v, team_color);
      }

   }

   public static ArrayList getStates(CustomScoreboard.TEAM_COLOR team_color) {
      ArrayList states = new ArrayList();
      IBlockState var10001;
      BlockStainedGlass var10002;
      if(team_color == CustomScoreboard.TEAM_COLOR.RED) {
         states.add(Blocks.field_150451_bX.func_176223_P());
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.RED));
         var10001 = Blocks.field_150406_ce.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.RED));
      } else if(team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
         states.add(Blocks.field_150340_R.func_176223_P());
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.YELLOW));
      } else if(team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
         states.add(Blocks.field_150475_bE.func_176223_P());
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.LIME));
      } else if(team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
         states.add(Blocks.field_150484_ah.func_176223_P());
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.LIGHT_BLUE));
      } else if(team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
         states.add(Blocks.field_150368_y.func_176223_P());
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.BLUE));
      } else if(team_color == CustomScoreboard.TEAM_COLOR.PINK) {
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.PINK));
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.MAGENTA));
      } else if(team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
         states.add(Blocks.field_150348_b.func_176223_P());
         states.add(Blocks.field_150347_e.func_176223_P());
         states.add(Blocks.field_150417_aV.func_176223_P());
      } else if(team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
         states.add(Blocks.field_150339_S.func_176223_P());
         states.add(Blocks.field_150371_ca.func_176223_P());
         var10001 = Blocks.field_150325_L.func_176223_P();
         var10002 = Blocks.field_150399_cn;
         states.add(var10001.func_177226_a(BlockStainedGlass.field_176547_a, EnumDyeColor.WHITE));
      }

      return states;
   }

}
