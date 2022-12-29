// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.block.properties.IProperty;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.particles.ParticleController;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.client.Minecraft;
import java.util.Random;
import java.util.Date;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.CustomScoreboard;
import java.util.ArrayList;
import net.minecraft.util.BlockPos;

public class WinEmote
{
    public static int emoteStage;
    public static int all_positions_idx;
    public static int maxEmoteBlocksPerTick;
    public static int targetRange;
    public static int currentRange;
    public static BlockPos startingPos;
    public static long startingTime;
    public static ArrayList<BlockPos> all_positions;
    public static CustomScoreboard.TEAM_COLOR emoteStage_team_color;
    
    public static void handleEmote() {
        if (!MyChatListener.IS_IN_GAME) {
            WinEmote.emoteStage = -1;
            WinEmote.all_positions.clear();
            return;
        }
        if (WinEmote.emoteStage > 0 && WinEmote.startingPos != null) {
            if (new Date().getTime() - WinEmote.startingTime > 15000L) {
                WinEmote.emoteStage = -1;
                return;
            }
            if (WinEmote.all_positions_idx >= WinEmote.all_positions.size()) {
                if (WinEmote.currentRange == WinEmote.targetRange) {
                    WinEmote.emoteStage = -1;
                    return;
                }
                fillRange(WinEmote.currentRange);
                ++WinEmote.currentRange;
            }
            final ArrayList<IBlockState> states = getStates(WinEmote.emoteStage_team_color);
            final Random rnd = new Random();
            final World world = (World)Minecraft.func_71410_x().field_71441_e;
            BlockPos pos = null;
            int cnt = 0;
            while (true) {
                ++cnt;
                ++WinEmote.all_positions_idx;
                if (cnt > WinEmote.maxEmoteBlocksPerTick) {
                    break;
                }
                if (WinEmote.all_positions_idx >= WinEmote.all_positions.size()) {
                    break;
                }
                pos = WinEmote.all_positions.get(WinEmote.all_positions_idx);
                if (world.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a) {
                    continue;
                }
                ++cnt;
                world.func_175656_a(pos, (IBlockState)states.get(rnd.nextInt(states.size())));
            }
        }
    }
    
    public static void fillRange(final int range) {
        BlockPos pos = new BlockPos(0, 0, 0);
        for (int xi = -range; xi <= range; ++xi) {
            for (int zi = -range; zi <= range; ++zi) {
                int dist = Math.abs(xi);
                if (Math.abs(xi) < Math.abs(zi)) {
                    dist = Math.abs(zi);
                }
                if (dist == range) {
                    for (int yi = 0; yi <= 120; ++yi) {
                        pos = new BlockPos(WinEmote.startingPos.func_177958_n() + xi, yi, WinEmote.startingPos.func_177952_p() + zi);
                        WinEmote.all_positions.add(pos);
                    }
                }
            }
        }
    }
    
    public static void changeWorldBlocks(final CustomScoreboard.TEAM_COLOR team_color) {
        final Entity player = (Entity)Minecraft.func_71410_x().field_71439_g;
        WinEmote.emoteStage = 1;
        WinEmote.emoteStage_team_color = team_color;
        WinEmote.all_positions = new ArrayList<BlockPos>();
        WinEmote.all_positions_idx = -1;
        WinEmote.currentRange = 0;
        WinEmote.startingTime = new Date().getTime();
        WinEmote.startingPos = new BlockPos(player);
        for (int i = 0; i < 10; ++i) {
            ParticleController.spawnFinalKillParticles(player.field_70165_t, player.field_70163_u + player.func_70047_e() / 2.0f, player.field_70161_v, team_color);
        }
    }
    
    public static ArrayList<IBlockState> getStates(final CustomScoreboard.TEAM_COLOR team_color) {
        final ArrayList<IBlockState> states = new ArrayList<IBlockState>();
        if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
            states.add(Blocks.field_150451_bX.func_176223_P());
            final ArrayList<IBlockState> list = states;
            final IBlockState func_176223_P = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn = Blocks.field_150399_cn;
            list.add(func_176223_P.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.RED));
            final ArrayList<IBlockState> list2 = states;
            final IBlockState func_176223_P2 = Blocks.field_150406_ce.func_176223_P();
            final BlockStainedGlass field_150399_cn2 = Blocks.field_150399_cn;
            list2.add(func_176223_P2.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.RED));
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
            states.add(Blocks.field_150340_R.func_176223_P());
            final ArrayList<IBlockState> list3 = states;
            final IBlockState func_176223_P3 = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn3 = Blocks.field_150399_cn;
            list3.add(func_176223_P3.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.YELLOW));
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
            states.add(Blocks.field_150475_bE.func_176223_P());
            final ArrayList<IBlockState> list4 = states;
            final IBlockState func_176223_P4 = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn4 = Blocks.field_150399_cn;
            list4.add(func_176223_P4.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.LIME));
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
            states.add(Blocks.field_150484_ah.func_176223_P());
            final ArrayList<IBlockState> list5 = states;
            final IBlockState func_176223_P5 = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn5 = Blocks.field_150399_cn;
            list5.add(func_176223_P5.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.LIGHT_BLUE));
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
            states.add(Blocks.field_150368_y.func_176223_P());
            final ArrayList<IBlockState> list6 = states;
            final IBlockState func_176223_P6 = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn6 = Blocks.field_150399_cn;
            list6.add(func_176223_P6.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.BLUE));
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
            final ArrayList<IBlockState> list7 = states;
            final IBlockState func_176223_P7 = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn7 = Blocks.field_150399_cn;
            list7.add(func_176223_P7.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.PINK));
            final ArrayList<IBlockState> list8 = states;
            final IBlockState func_176223_P8 = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn8 = Blocks.field_150399_cn;
            list8.add(func_176223_P8.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.MAGENTA));
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
            states.add(Blocks.field_150348_b.func_176223_P());
            states.add(Blocks.field_150347_e.func_176223_P());
            states.add(Blocks.field_150417_aV.func_176223_P());
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
            states.add(Blocks.field_150339_S.func_176223_P());
            states.add(Blocks.field_150371_ca.func_176223_P());
            final ArrayList<IBlockState> list9 = states;
            final IBlockState func_176223_P9 = Blocks.field_150325_L.func_176223_P();
            final BlockStainedGlass field_150399_cn9 = Blocks.field_150399_cn;
            list9.add(func_176223_P9.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)EnumDyeColor.WHITE));
        }
        return states;
    }
    
    static {
        WinEmote.emoteStage = -1;
        WinEmote.all_positions_idx = -1;
        WinEmote.maxEmoteBlocksPerTick = 10000;
        WinEmote.targetRange = 120;
        WinEmote.currentRange = -1;
        WinEmote.startingPos = null;
        WinEmote.startingTime = 0L;
        WinEmote.all_positions = new ArrayList<BlockPos>();
    }
}
