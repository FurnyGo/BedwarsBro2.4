// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.Minecraft;
import java.util.HashMap;
import net.minecraft.item.EnumDyeColor;
import java.util.Collection;
import net.minecraft.block.Block;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Timer;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import java.util.ArrayList;

public class BWBed
{
    public int part1_posX;
    public int part1_posY;
    public int part1_posZ;
    public int part2_posX;
    public int part2_posY;
    public int part2_posZ;
    public ArrayList<DefenceLayer> defence;
    public BWItemsHandler.BWItemColor color;
    
    public BWBed(final int part1_posX, final int part1_posY, final int part1_posZ, final int part2_posX, final int part2_posY, final int part2_posZ) {
        this.part1_posX = part1_posX;
        this.part1_posY = part1_posY;
        this.part1_posZ = part1_posZ;
        this.part2_posX = part2_posX;
        this.part2_posY = part2_posY;
        this.part2_posZ = part2_posZ;
    }
    
    public void showLayers(final World world) throws Exception {
        if (this.defence.size() == 0) {
            return;
        }
        class 1Pop
        {
            public BlockPos pos = pos;
            public IBlockState state = world.func_180495_p(pos);
            
            public 1Pop(final IBlockState state) {
            }
        }
        final ArrayList<1Pop> pops = new ArrayList<1Pop>();
        for (final DefenceLayer layer : this.defence) {
            for (final BlockPos pos : layer.arr) {
                pops.add(new 1Pop(world.func_180495_p(pos)));
                this.setBlock(world, pos, Blocks.field_150350_a);
            }
        }
        final int delay = Main.getConfigInt(Main.CONFIG_MSG.BED_SCANNER_ANIMATION_DELAY);
        for (final DefenceLayer layer2 : this.defence) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    for (final BlockPos pos : layer2.arr) {
                        for (final 1Pop p : pops) {
                            if (p.pos.func_177958_n() == pos.func_177958_n() && p.pos.func_177956_o() == pos.func_177956_o() && p.pos.func_177952_p() == pos.func_177952_p()) {
                                world.func_175654_a(pos, world.func_180495_p(pos).func_177230_c(), 0, 1);
                                if (!world.func_180495_p(pos).func_177230_c().func_149732_F().contains("air")) {
                                    continue;
                                }
                                BWBed.this.setBlock(world, pos, p.state);
                            }
                        }
                    }
                }
            }, delay * (layer2.index + 1));
        }
    }
    
    public void setBlock(final World world, final BlockPos pos, final Block block) {
        this.setBlock(world, pos, block.func_176223_P());
    }
    
    public void setBlock(final World world, final BlockPos pos, final IBlockState state) {
        world.func_175656_a(pos, state);
    }
    
    public void scanDefence(final World world) {
        this.defence = new ArrayList<DefenceLayer>();
        ArrayList<BlockPos> arr_conected = new ArrayList<BlockPos>();
        arr_conected.add(new BlockPos(this.part1_posX, this.part1_posY, this.part1_posZ));
        arr_conected.add(new BlockPos(this.part2_posX, this.part2_posY, this.part2_posZ));
        final ArrayList<BlockPos> arr_ignore = new ArrayList<BlockPos>();
        arr_ignore.addAll(arr_conected);
        final EnumDyeColor[] dyeColors = { EnumDyeColor.RED, EnumDyeColor.YELLOW, EnumDyeColor.LIME, EnumDyeColor.LIGHT_BLUE };
        final int min_y = this.part1_posY;
        for (int layer = 0; layer <= 5; ++layer) {
            final ArrayList<BlockPos> arr = this.scanNearestBlocks(arr_conected, arr_ignore, min_y);
            arr_ignore.addAll(arr);
            arr_conected = arr;
            final HashMap<BWItemsHandler.BWItemType, Integer> map = new HashMap<BWItemsHandler.BWItemType, Integer>();
            int cnt_not_air = 0;
            for (final BlockPos p : arr) {
                final Block block = Minecraft.func_71410_x().field_71441_e.func_180495_p(p).func_177230_c();
                final String name = block.func_149739_a().substring(5);
                if (!block.func_149739_a().contains("air")) {
                    ++cnt_not_air;
                    final BWItem item = BWItemsHandler.findItem(name, "");
                    if (item == null) {
                        continue;
                    }
                    if (item.type == null) {
                        continue;
                    }
                    if (map != null && map.containsKey(item.type)) {
                        map.put(item.type, map.get(item.type) + 1);
                    }
                    else {
                        map.put(item.type, 1);
                    }
                }
            }
            BWItemsHandler.BWItemType max_item = null;
            int max_val = -2;
            for (final Map.Entry<BWItemsHandler.BWItemType, Integer> entry : map.entrySet()) {
                int v = -1;
                if (entry.getKey() == BWItemsHandler.BWItemType.BLOCK_WOOL) {
                    v = 0;
                }
                else if (entry.getKey() == BWItemsHandler.BWItemType.BLOCK_WOOD) {
                    v = 1;
                }
                else if (entry.getKey() == BWItemsHandler.BWItemType.BLOCK_CLAY) {
                    v = 2;
                }
                else if (entry.getKey() == BWItemsHandler.BWItemType.BLOCK_LADDER) {
                    v = -1;
                }
                else if (entry.getKey() == BWItemsHandler.BWItemType.BLOCK_ENDSTONE) {
                    v = 3;
                }
                else if (entry.getKey() == BWItemsHandler.BWItemType.BLOCK_GLASS) {
                    v = 0;
                }
                else if (entry.getKey() == BWItemsHandler.BWItemType.BLOCK_OBSIDIAN) {
                    v = 4;
                }
                if (v > max_val) {
                    max_val = v;
                    max_item = entry.getKey();
                }
            }
            final float air_treshold = 0.3f;
            if (map.size() == 0) {
                break;
            }
            if (max_item == null) {
                break;
            }
            final BWItemsHandler.BWItemType item_type = max_item;
            this.defence.add(new DefenceLayer(arr, layer, item_type, map.get(max_item) / (float)arr.size(), arr.size() - cnt_not_air));
        }
    }
    
    public ArrayList<BlockPos> scanNearestBlocks(final ArrayList<BlockPos> connected_positions, final ArrayList<BlockPos> ignore_positions, final int min_y) {
        final ArrayList<BlockPos> arr = new ArrayList<BlockPos>();
        for (final BlockPos p : connected_positions) {
            for (int range = 1, yi = -range; yi <= range; ++yi) {
                for (int xi = -range; xi <= range; ++xi) {
                    for (int zi = -range; zi <= range; ++zi) {
                        final int bx = p.func_177958_n() + xi;
                        final int by = p.func_177956_o() + yi;
                        final int bz = p.func_177952_p() + zi;
                        if (by >= min_y) {
                            boolean isIgnored = false;
                            for (final BlockPos p_ignore : ignore_positions) {
                                if (bx == p_ignore.func_177958_n() && by == p_ignore.func_177956_o() && bz == p_ignore.func_177952_p()) {
                                    isIgnored = true;
                                    break;
                                }
                            }
                            for (final BlockPos p_ignore : arr) {
                                if (bx == p_ignore.func_177958_n() && by == p_ignore.func_177956_o() && bz == p_ignore.func_177952_p()) {
                                    isIgnored = true;
                                    break;
                                }
                            }
                            if (!isIgnored) {
                                if (isBlockConnectsToBlock(bx, by, bz, p.func_177958_n(), p.func_177956_o(), p.func_177952_p())) {
                                    arr.add(new BlockPos(bx, by, bz));
                                }
                            }
                        }
                    }
                }
            }
        }
        return arr;
    }
    
    public static boolean isBlockConnectsToBlock(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        final int distX = Math.abs(x1 - x2);
        final int distY = Math.abs(y1 - y2);
        final int distZ = Math.abs(z1 - z2);
        return (distX == 1 && distY == 0 && distZ == 0) || (distX == 0 && distY == 1 && distZ == 0) || (distX == 0 && distY == 0 && distZ == 1);
    }
    
    public String getAnalysis() {
        int defence_layer_count = 0;
        final ArrayList<String> defence_blocks = new ArrayList<String>();
        String defence_blocks_string = "";
        final HashSet<String> defence_requirements = new HashSet<String>();
        String defence_requirements_string = "";
        String extras = "";
        for (final DefenceLayer layer : this.defence) {
            if (layer.type != null && (layer.percentage == 1.0f || (layer.index + 1 < this.defence.size() && layer.percentage > 0.0f && layer.type != BWItemsHandler.BWItemType.BLOCK_OBSIDIAN))) {
                ++defence_layer_count;
                String item_name = "&c-";
                if (layer.type == BWItemsHandler.BWItemType.BLOCK_WOOL) {
                    item_name = "&7\u0428\u0435\u0440\u0441\u0442\u044c";
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_WOOD) {
                    item_name = "&6\u0414\u0435\u0440\u0435\u0432\u043e";
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_CLAY) {
                    item_name = "&f\u0411\u0435\u0442\u043e\u043d";
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_LADDER) {
                    item_name = "&7\u041b\u0435\u0441\u0442\u043d\u0438\u0446\u0430";
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_ENDSTONE) {
                    item_name = "&e\u042d\u043d\u0434\u0435\u0440\u043d\u044f\u043a";
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_GLASS) {
                    item_name = "&7\u0421\u0442\u0435\u043a\u043b\u043e";
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_OBSIDIAN) {
                    item_name = "&b&l\u041e\u0411\u0421\u0410";
                }
                defence_blocks.add(item_name);
                if (layer.type == BWItemsHandler.BWItemType.BLOCK_ENDSTONE || layer.type == BWItemsHandler.BWItemType.BLOCK_CLAY) {
                    if (defence_requirements.contains("&b&l\u0410\u043b\u043c\u0430\u0437\u043d\u0430\u044f \u041a\u0438\u0440\u043a\u0430")) {
                        continue;
                    }
                    defence_requirements.add("&6\u041a\u0438\u0440\u043a\u0430");
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_OBSIDIAN) {
                    defence_requirements.add("&b&l\u0410\u043b\u043c\u0430\u0437\u043d\u0430\u044f \u041a\u0438\u0440\u043a\u0430");
                    defence_requirements.remove("&6\u041a\u0438\u0440\u043a\u0430");
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_WOOD) {
                    defence_requirements.add("&6\u0422\u043e\u043f\u043e\u0440");
                }
                else if (layer.type == BWItemsHandler.BWItemType.BLOCK_WOOL) {}
            }
            else {
                if (layer.index != 0 || layer.type != BWItemsHandler.BWItemType.BLOCK_OBSIDIAN || layer.percentage >= 1.0f) {
                    continue;
                }
                defence_layer_count = 1;
                extras = "\u041d\u0435 \u043f\u043e\u043b\u043d\u0430\u044f \u0437\u0430\u0449\u0438\u0442\u0430 \u041e\u0411\u0421\u041e\u0419! &8" + (int)(layer.percentage * 100.0f) + "%";
            }
        }
        defence_blocks_string = "&7";
        String prev_block = "none";
        int inline_count = 0;
        Collections.reverse(defence_blocks);
        defence_blocks.add("none");
        if (defence_blocks.size() > 0) {
            for (final String s : defence_blocks) {
                if (prev_block.equals("none")) {
                    prev_block = s;
                    inline_count = 1;
                }
                else if (prev_block.equals(s)) {
                    ++inline_count;
                }
                else {
                    defence_blocks_string += prev_block;
                    if (inline_count > 1) {
                        defence_blocks_string = defence_blocks_string + " &7x&c" + inline_count;
                    }
                    defence_blocks_string += " &8\u25b8 ";
                    inline_count = 1;
                    prev_block = s;
                }
            }
        }
        if (defence_blocks_string.length() > 5) {
            defence_blocks_string = defence_blocks_string.substring(0, defence_blocks_string.length() - 5).trim();
        }
        defence_requirements_string = "";
        for (final String s : defence_requirements) {
            defence_requirements_string = defence_requirements_string + s + "&7, ";
        }
        if (defence_requirements_string.length() > 2) {
            defence_requirements_string = defence_requirements_string.substring(0, defence_requirements_string.length() - 2).trim();
        }
        String str = "";
        if (defence_layer_count == 0) {
            str = "&f\u041d\u0435\u0442 \u0437\u0430\u0449\u0438\u0442\u044b";
        }
        else if (defence_blocks.size() > 1) {
            str = str + "" + defence_blocks_string + "";
        }
        if (extras.length() > 0) {
            str = str + " &b" + extras;
        }
        return str;
    }
    
    public class DefenceLayer
    {
        public int index;
        public BWItemsHandler.BWItemType type;
        public float percentage;
        public ArrayList<BlockPos> arr;
        public int empty_space_cnt;
        
        public DefenceLayer(final ArrayList<BlockPos> arr, final int index, final BWItemsHandler.BWItemType type, final float percentage, final int empty_space_cnt) {
            this.index = index;
            this.type = type;
            this.percentage = percentage;
            this.arr = arr;
            this.empty_space_cnt = empty_space_cnt;
        }
    }
}
