// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.hints.BWItemsHandler;
import com.dimchig.bedwarsbro.particles.ParticleController;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.item.Item;
import java.util.List;
import java.util.Iterator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.EntitySelectors;
import net.minecraft.entity.boss.EntityDragon;
import com.dimchig.bedwarsbro.hints.HintsPlayerScanner;
import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import java.awt.Color;
import com.dimchig.bedwarsbro.ColorCodesManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockBed;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import java.util.Date;
import com.dimchig.bedwarsbro.hints.BWBed;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.client.gui.ScaledResolution;
import com.dimchig.bedwarsbro.Main;
import java.util.ArrayList;
import java.text.DecimalFormat;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.Gui;

public class GuiMinimapOLD extends Gui
{
    public static boolean isActive;
    public static boolean isHidePlayersOnShift;
    private static int offset;
    private static int topX;
    private static int topY;
    private static int botX;
    private static int botY;
    public static int map_size;
    private static boolean show_heights;
    private static boolean show_additional_information;
    private ResourceLocation resourceLoc_enemy;
    private static Minecraft mc;
    private TextureManager textureManager;
    private int tick_cnt;
    private final DecimalFormat timeFormatter;
    public static ArrayList<MyBed> bedsFound;
    private static long time_last_bed_scanned;
    private static long TIME_BED_SCAN;
    private static int zoom;
    public static boolean showNicknames;
    private int minimap_scan_z_value;
    private int minimap_scan_z_step;
    private int minimap_scan_range;
    private boolean minimap_scan_is_active;
    public ArrayList<Long> avg_ms;
    
    public GuiMinimapOLD() {
        this.resourceLoc_enemy = new ResourceLocation("bedwarsbro:textures/gui/minimap_icons.png");
        this.tick_cnt = 0;
        this.timeFormatter = new DecimalFormat("0.0");
        this.minimap_scan_z_value = 0;
        this.minimap_scan_z_step = 2;
        this.minimap_scan_range = 75;
        this.minimap_scan_is_active = false;
        this.avg_ms = new ArrayList<Long>();
        GuiMinimapOLD.mc = Minecraft.func_71410_x();
        this.textureManager = GuiMinimapOLD.mc.func_110434_K();
        updateBooleans();
        GuiMinimapOLD.bedsFound = new ArrayList<MyBed>();
        updateSizes();
    }
    
    public static void updateSizes() {
        try {
            GuiMinimapOLD.map_size = Main.getConfigInt(Main.CONFIG_MSG.MINIMAP_SIZE);
            final ScaledResolution sr = new ScaledResolution(GuiMinimapOLD.mc);
            final int screen_width = sr.func_78326_a();
            final int screen_height = sr.func_78328_b();
            final String ox = Main.getConfigString(Main.CONFIG_MSG.MINIMAP_X);
            int offsetX = Integer.parseInt(ox.replace("-", ""));
            if (ox.startsWith("-")) {
                offsetX = screen_width - offsetX - GuiMinimapOLD.map_size;
            }
            final String oy = Main.getConfigString(Main.CONFIG_MSG.MINIMAP_Y);
            int offsetY = Integer.parseInt(oy.replace("-", ""));
            if (oy.startsWith("-")) {
                offsetY = screen_height - offsetY - GuiMinimapOLD.map_size;
            }
            GuiMinimapOLD.show_heights = Main.getConfigBool(Main.CONFIG_MSG.MINIMAP_SHOW_HEIGHT);
            GuiMinimapOLD.show_additional_information = Main.getConfigBool(Main.CONFIG_MSG.MINIMAP_ADDITIONAL_INFORMTAION);
            GuiMinimapOLD.topX = offsetX + GuiMinimapOLD.map_size + 20;
            GuiMinimapOLD.topY = offsetY;
            GuiMinimapOLD.botX = GuiMinimapOLD.topX + GuiMinimapOLD.map_size;
            GuiMinimapOLD.botY = GuiMinimapOLD.topY + GuiMinimapOLD.map_size;
        }
        catch (Exception ex) {
            ChatSender.addText("&aMinimap: &c\u041e\u0448\u0438\u0431\u043a\u0430 \u0432 config!");
            GuiMinimapOLD.map_size = 100;
            GuiMinimapOLD.topX = 0;
            GuiMinimapOLD.topY = 0;
            GuiMinimapOLD.botX = GuiMinimapOLD.topX + GuiMinimapOLD.map_size;
            GuiMinimapOLD.botY = GuiMinimapOLD.topY + GuiMinimapOLD.map_size;
        }
    }
    
    public void handleZoom() {
        GuiMinimapOLD.zoom = 1 - GuiMinimapOLD.zoom;
        updateSizes();
    }
    
    public static void updateBooleans() {
        GuiMinimapOLD.isActive = HintsValidator.isMinimapActive();
        GuiMinimapOLD.isHidePlayersOnShift = Main.getConfigBool(Main.CONFIG_MSG.MINIMAP_HIDE_PLAYERS_ON_SHIFT);
        GuiMinimapOLD.zoom = 0;
        GuiMinimapOLD.showNicknames = false;
        updateSizes();
    }
    
    public void clearGameBeds() {
        if (GuiMinimapOLD.bedsFound == null) {
            GuiMinimapOLD.bedsFound = new ArrayList<MyBed>();
        }
        GuiMinimapOLD.bedsFound.clear();
    }
    
    public void myScan() {
        if (!this.minimap_scan_is_active) {
            return;
        }
        if (GuiMinimapOLD.bedsFound == null) {
            GuiMinimapOLD.bedsFound = new ArrayList<MyBed>();
        }
        final MyChatListener chatListener = Main.chatListener;
        if (!MyChatListener.IS_IN_GAME) {
            return;
        }
        final MyChatListener chatListener2 = Main.chatListener;
        final BWBed bed = MyChatListener.GAME_BED;
        if (bed == null) {
            return;
        }
        final int range = this.minimap_scan_range;
        if (this.minimap_scan_z_value >= range) {
            this.minimap_scan_is_active = false;
            this.minimap_scan_z_value = -range;
        }
        final int min_z = this.minimap_scan_z_value;
        this.minimap_scan_z_value = Math.min(min_z + this.minimap_scan_z_step, range);
        final int max_z = this.minimap_scan_z_value;
        if (bed.part1_posY == 71 || bed.part1_posY == 69) {
            this.scanArea(-range, range, min_z, max_z, 71);
            this.scanArea(-range, range, min_z, max_z, 69);
        }
        else {
            this.scanArea(-range, range, min_z, max_z, bed.part1_posY);
        }
    }
    
    public void scanArea(final int min_x, final int max_x, final int min_z, final int max_z, final int y_level) {
        final int px = (int)Math.floor(GuiMinimapOLD.mc.field_71439_g.field_70165_t);
        final int pz = (int)Math.floor(GuiMinimapOLD.mc.field_71439_g.field_70161_v);
        final int py = y_level;
        final long t = new Date().getTime();
        BlockPos pos = new BlockPos(px + min_x, py, pz + min_z);
        final World world = (World)GuiMinimapOLD.mc.field_71441_e;
        final MyChatListener chatListener = Main.chatListener;
        final BWBed player_bed = MyChatListener.GAME_BED;
        for (int zi = min_z; zi < max_z; ++zi) {
            for (int xi = min_x; xi <= max_x; ++xi) {
                pos = pos.func_177972_a(EnumFacing.EAST);
                final IBlockState state = world.func_180495_p(pos);
                if (state != null) {
                    final Block b = state.func_177230_c();
                    if (b == Blocks.field_150324_C && state.func_177229_b((IProperty)BlockBed.field_176472_a) == BlockBed.EnumPartType.HEAD) {
                        double x = pos.func_177958_n();
                        final double y = pos.func_177956_o();
                        double z = pos.func_177952_p();
                        double x2 = x;
                        final double y2 = y;
                        double z2 = z;
                        final EnumFacing facing = (EnumFacing)world.func_180495_p(new BlockPos(x, y, z)).func_177229_b((IProperty)BlockBed.field_176387_N);
                        switch (facing) {
                            case EAST: {
                                --x;
                                ++x2;
                                ++z2;
                                break;
                            }
                            case NORTH: {
                                z2 += 2.0;
                                ++x2;
                                break;
                            }
                            case SOUTH: {
                                --z;
                                ++z2;
                                ++x2;
                                break;
                            }
                            case WEST: {
                                ++z2;
                                x2 += 2.0;
                                break;
                            }
                        }
                        --x2;
                        --z2;
                        final MyBed bed = new MyBed(new BlockPos(x, y, z), new BlockPos(x2, y, z2), t, false);
                        if (player_bed != null && ((player_bed.part1_posX == pos.func_177958_n() && player_bed.part1_posZ == pos.func_177952_p()) || (player_bed.part2_posX == pos.func_177958_n() && player_bed.part2_posZ == pos.func_177952_p()))) {
                            bed.isPlayersBed = true;
                        }
                        final BlockPos[] array;
                        final BlockPos[] arr = array = new BlockPos[] { new BlockPos(x - 1.0, y, z), new BlockPos(x + 1.0, y, z), new BlockPos(x, y, z - 1.0), new BlockPos(x, y, z + 1.0), new BlockPos(x2 - 1.0, y2, z2), new BlockPos(x2 + 1.0, y2, z2), new BlockPos(x2, y2, z2 - 1.0), new BlockPos(x2, y2, z2 + 1.0), new BlockPos(x, y + 1.0, z), new BlockPos(x2, y2 + 1.0, z2) };
                        for (final BlockPos pp : array) {
                            GL11.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
                            final int block_x = pp.func_177958_n();
                            final int block_y = pp.func_177956_o();
                            final int block_z = pp.func_177952_p();
                            if (block_x != x || block_y != y || block_z != z) {
                                if (block_x != x2 || block_y != y2 || block_z != z2) {
                                    final IBlockState block_state = world.func_180495_p(pp);
                                    if (block_state != null) {
                                        final Block block = block_state.func_177230_c();
                                        if (block != null) {
                                            if (block == Blocks.field_150343_Z) {
                                                bed.obsidianPoses.add(pp);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        GuiMinimapOLD.bedsFound.add(bed);
                    }
                }
            }
            pos = pos.func_177972_a(EnumFacing.SOUTH);
            pos = pos.func_177967_a(EnumFacing.EAST, -max_x - max_x - 1);
        }
    }
    
    public void draw(final Minecraft mc) {
        if (!GuiMinimapOLD.isActive) {
            return;
        }
        if (this.textureManager == null) {
            this.textureManager = mc.func_110434_K();
        }
        final int color_bg = this.getColor("00000011");
        final int color_bg2 = this.getColor("000000aa");
        final int color_dot = this.getColor("ff0000ff");
        final int color_player = this.getColor("00ff00ff");
        final int dot_size = 2;
        final EntityPlayerSP player = mc.field_71439_g;
        final Pos playerPos = new Pos(player.field_70165_t, player.field_70163_u, player.field_70161_v);
        final float player_angle = player.field_70177_z;
        final double player_angle_radians = Math.toRadians(180.0f - player_angle);
        final double player_angle_cos = Math.cos(player_angle_radians);
        final double player_angle_sin = Math.sin(player_angle_radians);
        int scaling = 60;
        if (GuiMinimapOLD.zoom == 1) {
            scaling /= 2;
        }
        final float enemy_angle = 45.0f;
        final long time_start = new Date().getTime();
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(0.2f, 0.2f, 0.2f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        GlStateManager.func_179090_x();
        GlStateManager.func_179131_c(0.5f, 0.5f, 0.5f, 1.0f);
        for (int chunk_size = 11, i = 0; i < chunk_size; ++i) {
            for (int j = 0; j < chunk_size; ++j) {
                this.drawChunk(worldrenderer, new BlockPos(player.field_70165_t + 16 * (i - (chunk_size + 1) / 2), player.field_70163_u, player.field_70161_v + 16 * (j - (chunk_size + 1) / 2)), playerPos, scaling, player_angle_cos, player_angle_sin);
            }
        }
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
        this.avg_ms.add(new Date().getTime() - time_start);
        if (this.avg_ms.size() > 1000) {
            this.avg_ms.remove(0);
        }
        long temp_cnt = 0L;
        for (final long x : this.avg_ms) {
            temp_cnt += x * 1000L;
        }
        final float avg_ms_value = temp_cnt * 1.0f / this.avg_ms.size();
        final String temp_text = "&b" + (int)(avg_ms_value - avg_ms_value % 10.0f) + " mms";
        mc.field_71466_p.func_78276_b(ColorCodesManager.replaceColorCodesInString(temp_text), GuiMinimapOLD.topX + GuiMinimapOLD.map_size / 2 - mc.field_71466_p.func_78256_a(temp_text) / 2, GuiMinimapOLD.botY - 10, new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB());
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        if (GuiMinimapOLD.zoom == 1) {
            mc.field_71466_p.func_78276_b("x2", GuiMinimapOLD.botX - mc.field_71466_p.func_78256_a("x2"), GuiMinimapOLD.botY - 9, new Color(1.0f, 1.0f, 1.0f, 0.2f).getRGB());
        }
        final List<EntityPlayer> entities = (List<EntityPlayer>)mc.field_71441_e.field_73010_i;
        final MyChatListener chatListener = Main.chatListener;
        final BWBed game_bed = MyChatListener.GAME_BED;
        for (final EntityPlayer en : entities) {
            if (en != null && en.func_70005_c_() != null) {
                if (en.func_145748_c_() == null) {
                    continue;
                }
                if (en.func_70005_c_().equals(player.func_70005_c_())) {
                    continue;
                }
                if (GuiMinimapOLD.isHidePlayersOnShift && en.func_70093_af()) {
                    continue;
                }
                final Pos blockPos = new Pos(en.field_70165_t, en.field_70163_u, en.field_70161_v);
                final double motionY = en.field_70167_r - en.field_70163_u;
                if (game_bed != null && en.field_70163_u < game_bed.part1_posY - 5 && motionY > 1.0) {
                    continue;
                }
                int texture_offset_x = 2;
                int texture_offset_y = 2;
                final CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor(en);
                if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
                    texture_offset_x = 0;
                    texture_offset_y = 0;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
                    texture_offset_x = 1;
                    texture_offset_y = 0;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
                    texture_offset_x = 2;
                    texture_offset_y = 0;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
                    texture_offset_x = 0;
                    texture_offset_y = 1;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
                    texture_offset_x = 1;
                    texture_offset_y = 1;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
                    texture_offset_x = 2;
                    texture_offset_y = 1;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
                    texture_offset_x = 0;
                    texture_offset_y = 2;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
                    texture_offset_x = 1;
                    texture_offset_y = 2;
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.NONE) {
                    texture_offset_x = 2;
                    texture_offset_y = 2;
                }
                HintsPlayerScanner.BWPlayer bwplayer = null;
                for (final HintsPlayerScanner.BWPlayer p : Main.myTickEvent.getCurrentScannedPlayers()) {
                    if (p.en == en) {
                        bwplayer = p;
                        break;
                    }
                }
                mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                this.drawPoint(blockPos, playerPos, bwplayer, scaling, player_angle, en.field_70177_z, en.field_70125_A, team_color, texture_offset_x, texture_offset_y, false);
            }
        }
        final List<EntityDragon> dragons = (List<EntityDragon>)mc.field_71441_e.func_175644_a((Class)EntityDragon.class, EntitySelectors.field_94557_a);
        if (dragons != null && dragons.size() > 0) {
            for (final EntityDragon dragon : dragons) {
                final Pos blockPos2 = new Pos(dragon.field_70165_t, dragon.field_70163_u, dragon.field_70161_v);
                mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                this.drawPoint(blockPos2, playerPos, null, scaling, player_angle, 180.0f + dragon.field_70177_z, dragon.field_70125_A, CustomScoreboard.TEAM_COLOR.NONE, 2, 1, false);
                GlStateManager.func_179094_E();
                final float text_size = 0.5f;
                GlStateManager.func_179152_a(text_size, text_size, text_size);
                this.drawTextOnMap(blockPos2, playerPos, scaling, text_size, player_angle, "Dragon", this.getColor("ff00ffff"), 0.0, -9.0);
                final String health_s = "" + (int)(dragon.func_110143_aJ() / 2.0f) + "%";
                this.drawTextOnMap(blockPos2, playerPos, scaling, text_size, player_angle, health_s, this.getColor("ff0000ff"), 0.0, 5.0);
                GlStateManager.func_179121_F();
            }
        }
        final List<EntityFireball> fireballs = (List<EntityFireball>)mc.field_71441_e.func_175644_a((Class)EntityFireball.class, EntitySelectors.field_94557_a);
        if (fireballs != null && fireballs.size() > 0) {
            for (final EntityFireball fireball : fireballs) {
                if (fireball.field_70163_u <= 300.0) {
                    if (fireball.field_70163_u < -10.0) {
                        continue;
                    }
                    final Pos blockPos3 = new Pos(fireball.field_70165_t, fireball.field_70163_u, fireball.field_70161_v);
                    mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                    this.drawTexture(blockPos3, playerPos, scaling, player_angle, 0.0f, 205, 244, 12, 12, 0.4f, 0.0f, 0.0f);
                }
            }
        }
        final List<EntityEnderPearl> enderpearls = (List<EntityEnderPearl>)mc.field_71441_e.func_175644_a((Class)EntityEnderPearl.class, EntitySelectors.field_94557_a);
        if (enderpearls != null && enderpearls.size() > 0) {
            for (final EntityEnderPearl pearl : enderpearls) {
                if (pearl.field_70163_u <= 300.0) {
                    if (pearl.field_70163_u < -10.0) {
                        continue;
                    }
                    final Pos blockPos4 = new Pos(pearl.field_70165_t, pearl.field_70163_u, pearl.field_70161_v);
                    mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                    this.drawTexture(blockPos4, playerPos, scaling, player_angle, 0.0f, 192, 243, 13, 13, 0.4f, 0.0f, 0.0f);
                }
            }
        }
        final List<EntityArrow> arrows = (List<EntityArrow>)mc.field_71441_e.func_175644_a((Class)EntityArrow.class, EntitySelectors.field_94557_a);
        if (arrows != null && arrows.size() > 0) {
            for (final EntityArrow arrow : arrows) {
                if (arrow.field_70163_u <= 300.0 && arrow.field_70163_u >= -10.0) {
                    if (arrow.field_70137_T == arrow.field_70163_u) {
                        continue;
                    }
                    final Pos blockPos5 = new Pos(arrow.field_70165_t, arrow.field_70163_u, arrow.field_70161_v);
                    final double dX = arrow.field_70142_S - arrow.field_70165_t;
                    final double dZ = arrow.field_70136_U - arrow.field_70161_v;
                    final float t_yaw = (float)Math.toDegrees(Math.atan2(dZ, dX)) + 90.0f;
                    mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                    this.drawTexture(blockPos5, playerPos, scaling, player_angle, t_yaw - player_angle - 45.0f, 179, 243, 13, 13, 0.3f, 0.0f, 0.0f);
                }
            }
        }
        final List<EntityTNTPrimed> tnts = (List<EntityTNTPrimed>)mc.field_71441_e.func_175644_a((Class)EntityTNTPrimed.class, EntitySelectors.field_94557_a);
        if (tnts != null && tnts.size() > 0) {
            for (final EntityTNTPrimed tnt : tnts) {
                if (tnt.field_70163_u <= 300.0) {
                    if (tnt.field_70163_u < -10.0) {
                        continue;
                    }
                    final Pos blockPos6 = new Pos(tnt.field_70165_t, tnt.field_70163_u, tnt.field_70161_v);
                    mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                    this.drawTexture(blockPos6, playerPos, scaling, player_angle, 0.0f, 139, 240, 16, 16, 0.3f, 0.0f, 0.0f);
                    float tnt_time = tnt.field_70516_a / 20.0f;
                    if (tnt_time < 0.0f) {
                        tnt_time = 0.0f;
                    }
                    final String str = this.timeFormatter.format(tnt_time);
                    GlStateManager.func_179094_E();
                    final float text_size2 = 0.4f;
                    GlStateManager.func_179152_a(text_size2, text_size2, text_size2);
                    final float green = Math.min(tnt.field_70516_a / 50.0f, 1.0f);
                    final Color color = new Color(1.0f - green, green, 0.0f);
                    this.drawTextOnMap(blockPos6, playerPos, scaling, text_size2, player_angle, str, color.getRGB(), 0.0, -8.0);
                    GlStateManager.func_179121_F();
                }
            }
        }
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
        this.drawPoint(playerPos, playerPos, null, scaling, player_angle, player_angle, mc.field_71439_g.field_70125_A, CustomScoreboard.TEAM_COLOR.NONE, 0, 0, true);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
        final List<Entity> items = (List<Entity>)mc.field_71441_e.field_72996_f;
        final int cnt_emerald = 0;
        final int cnt_diamond = 0;
        final EntityItem item_max_emerads = null;
        final EntityItem item_max_diamonds = null;
        final ArrayList<PosItem> itemsPos = new ArrayList<PosItem>();
        for (final Entity en2 : items) {
            if (en2 instanceof EntityItem) {
                final EntityItem itemEntity = (EntityItem)en2;
                final Item item = itemEntity.func_92059_d().func_77973_b();
                if (item == null) {
                    continue;
                }
                if (MyChatListener.GAME_BED != null && en2.field_70163_u < MyChatListener.GAME_BED.part1_posY - 30) {
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
                if (item_type == -1) {
                    continue;
                }
                final int cnt = itemEntity.func_92059_d().field_77994_a;
                boolean isFound = false;
                for (final PosItem p2 : itemsPos) {
                    if (p2.type != item_type) {
                        continue;
                    }
                    final double dist = Math.sqrt(Math.pow(p2.x - en2.field_70165_t, 2.0) + Math.pow(p2.z - en2.field_70161_v, 2.0));
                    if (dist < 3.0) {
                        final PosItem posItem = p2;
                        posItem.cnt += cnt;
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    continue;
                }
                itemsPos.add(new PosItem(en2.field_70165_t, en2.field_70163_u, en2.field_70161_v, item_type, cnt));
            }
        }
        for (final PosItem p3 : itemsPos) {
            if (p3.type == 2 && p3.cnt < 6) {
                continue;
            }
            if (p3.type == 3 && p3.cnt < 32) {
                continue;
            }
            this.drawItemResouce(new Pos(p3.x, p3.y, p3.z), playerPos, scaling, player_angle, p3.type, p3.cnt);
        }
        GlStateManager.func_179121_F();
        final long t = new Date().getTime();
        if (t - GuiMinimapOLD.time_last_bed_scanned > GuiMinimapOLD.TIME_BED_SCAN) {
            this.minimap_scan_is_active = true;
            GuiMinimapOLD.time_last_bed_scanned = t;
        }
        this.myScan();
        if (GuiMinimapOLD.bedsFound.size() > 0) {
            final Iterator<MyBed> k = GuiMinimapOLD.bedsFound.iterator();
            while (k.hasNext()) {
                final MyBed b = k.next();
                if (t - b.t > GuiMinimapOLD.TIME_BED_SCAN * 2L) {
                    k.remove();
                }
                mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                this.drawBed(b.pos, playerPos, scaling, player_angle, dot_size, b.isPlayersBed);
                final String text = "OBBY";
                final String additional = "" + (int)(b.obsidianPoses.size() / 8.0f * 100.0f) + "%";
                if (b.obsidianPoses.size() == 0) {
                    continue;
                }
                GlStateManager.func_179094_E();
                final float text_size3 = 0.5f;
                GlStateManager.func_179152_a(text_size3, text_size3, text_size3);
                this.drawTextOnMap(new Pos(b.pos.func_177958_n(), b.pos.func_177956_o(), b.pos.func_177952_p()), playerPos, scaling, text_size3, player_angle, text, this.getColor("bb2affff"), 0.0, 3.0);
                if (b.obsidianPoses.size() < 8) {
                    this.drawTextOnMap(new Pos(b.pos.func_177958_n(), b.pos.func_177956_o(), b.pos.func_177952_p()), playerPos, scaling, text_size3, player_angle, additional, this.getColor("d787fcff"), 13.0, 3.0);
                }
                GlStateManager.func_179121_F();
            }
        }
    }
    
    void drawItemResouce(final Pos pos, final Pos playerPos, final int scaling, final float player_angle, final int item_idx, final int item_count) {
        if (item_count <= 0) {
            return;
        }
        int color = this.getColor("ffffffff");
        final double offsetX = 0.0;
        double offsetZ = 0.0;
        if (item_idx == 0) {
            color = this.getColor("00ff00ff");
        }
        else if (item_idx == 1) {
            color = this.getColor("00ffffff");
        }
        else if (item_idx == 2) {
            color = this.getColor("ffea00ff");
            offsetZ = -4.0;
        }
        else if (item_idx == 3) {
            color = this.getColor("e0e0e0ff");
            offsetZ = 2.0;
        }
        String text = "" + item_count;
        if (item_count > 64) {
            text = "" + this.timeFormatter.format(item_count / 64.0f) + "s";
        }
        this.drawTextOnMap(pos, playerPos, scaling, 0.5f, player_angle, text, color, offsetX, offsetZ);
    }
    
    void drawTextOnMap(final Pos pos, final Pos playerPos, final int scaling, final float matrix_scale, final float player_angle, final String text, final int color, final double offsetX, final double offsetZ) {
        final double deltaX = pos.x - playerPos.x;
        final double deltaY = pos.y - playerPos.y;
        final double deltaZ = pos.z - playerPos.z;
        final float multiplier = 1.0f / matrix_scale;
        final int cx = (int)((GuiMinimapOLD.topX + GuiMinimapOLD.botX) / 2 * multiplier);
        final int cy = (int)((GuiMinimapOLD.topY + GuiMinimapOLD.botY) / 2 * multiplier);
        final float scaling_coef = GuiMinimapOLD.map_size / (float)(scaling * 2) * multiplier;
        float screenDeltaX = (float)(deltaX * scaling_coef);
        float screenDeltaZ = (float)(deltaZ * scaling_coef);
        final double x1 = screenDeltaX;
        final double z1 = screenDeltaZ;
        final double angle = Math.toRadians(180.0f - player_angle);
        screenDeltaX = (float)(x1 * Math.cos(angle) - z1 * Math.sin(angle)) + cx;
        screenDeltaZ = (float)(x1 * Math.sin(angle) + z1 * Math.cos(angle)) + cy;
        screenDeltaX += (float)(offsetX * scaling_coef);
        screenDeltaZ += (float)(offsetZ * scaling_coef);
        if (Math.abs(screenDeltaX - cx) > GuiMinimapOLD.map_size / 2 * multiplier) {
            return;
        }
        if (Math.abs(screenDeltaZ - cy) > GuiMinimapOLD.map_size / 2 * multiplier) {
            return;
        }
        this.func_73732_a(GuiMinimapOLD.mc.field_71466_p, text, (int)screenDeltaX, (int)screenDeltaZ, color);
    }
    
    public void drawChunk(final WorldRenderer worldrenderer, final BlockPos chunkPos, final Pos playerPos, final int scaling, final double player_angle_cos, final double player_angle_sin) {
        final Chunk chunk = GuiMinimapOLD.mc.field_71441_e.func_175726_f(chunkPos);
        final int[] heights = chunk.func_177445_q();
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                final int idx = x * 16 + z;
                final int h = heights[idx];
                final Pos pos = new Pos(z + chunk.field_76635_g * 16, h, x + chunk.field_76647_h * 16);
                if (h > 0) {
                    this.drawBlock(worldrenderer, pos, playerPos, scaling, player_angle_cos, player_angle_sin);
                }
            }
        }
    }
    
    private void drawBlock(final WorldRenderer worldrenderer, final Pos pos, final Pos playerPos, final int scaling, final double cos, final double sin) {
        final double deltaX = pos.x + 0.5 - playerPos.x;
        final double deltaZ = pos.z + 0.5 - playerPos.z;
        final int multiplier = 5;
        final int cx = (GuiMinimapOLD.topX + GuiMinimapOLD.botX) / 2 * multiplier;
        final int cy = (GuiMinimapOLD.topY + GuiMinimapOLD.botY) / 2 * multiplier;
        final float scaling_coef = GuiMinimapOLD.map_size / (float)(scaling * 2) * multiplier;
        float screenDeltaX = (float)(deltaX * scaling_coef);
        float screenDeltaZ = (float)(deltaZ * scaling_coef);
        final float dot_size = scaling_coef + 4.0f;
        final double x1 = screenDeltaX;
        final double z1 = screenDeltaZ;
        screenDeltaX = (float)(x1 * cos - z1 * sin) + cx;
        screenDeltaZ = (float)(x1 * sin + z1 * cos) + cy;
        if (Math.abs(screenDeltaX - cx) > GuiMinimapOLD.map_size / 2 * multiplier) {
            return;
        }
        if (Math.abs(screenDeltaZ - cy) > GuiMinimapOLD.map_size / 2 * multiplier) {
            return;
        }
        final double topX1 = screenDeltaX - dot_size / 2.0f;
        final double topY1 = screenDeltaZ - dot_size / 2.0f;
        final double topX2 = screenDeltaX - dot_size / 2.0f;
        final double topY2 = screenDeltaZ + dot_size / 2.0f;
        final double topX3 = screenDeltaX + dot_size / 2.0f;
        final double topY3 = screenDeltaZ + dot_size / 2.0f;
        final double topX4 = screenDeltaX + dot_size / 2.0f;
        final double topY4 = screenDeltaZ - dot_size / 2.0f;
        worldrenderer.func_181662_b(topX1, topY1, 0.0).func_181675_d();
        worldrenderer.func_181662_b(topX2, topY2, 0.0).func_181675_d();
        worldrenderer.func_181662_b(topX3, topY3, 0.0).func_181675_d();
        worldrenderer.func_181662_b(topX4, topY4, 0.0).func_181675_d();
    }
    
    private double max(final double a, final double b) {
        if (a > b) {
            return a;
        }
        return b;
    }
    
    private double min(final double a, final double b) {
        if (a < b) {
            return a;
        }
        return b;
    }
    
    private void drawPoint(final Pos pos, final Pos playerPos, final HintsPlayerScanner.BWPlayer bwplayer, final int scaling, final float player_angle, float enemy_angle, final float enemy_pitch, final CustomScoreboard.TEAM_COLOR team_color, final int texture_offset_x, final int texture_offset_y, final boolean isMainPlayer) {
        if (isMainPlayer) {
            final int cx = GuiMinimapOLD.topX + GuiMinimapOLD.map_size / 2;
            final int cy = GuiMinimapOLD.topY + GuiMinimapOLD.map_size / 2;
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)cx, (float)cy, 0.0f);
            GlStateManager.func_179152_a(0.3f, 0.3f, 0.3f);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_73729_b(-7, -7, 243, 243, 13, 13);
            GlStateManager.func_179121_F();
            return;
        }
        final double deltaX = pos.x - playerPos.x;
        final double deltaY = pos.y - playerPos.y;
        final double deltaZ = pos.z - playerPos.z;
        final int cx2 = (GuiMinimapOLD.topX + GuiMinimapOLD.botX) / 2;
        final int cy2 = (GuiMinimapOLD.topY + GuiMinimapOLD.botY) / 2;
        final float scaling_coef = GuiMinimapOLD.map_size / (float)(scaling * 2);
        float screenDeltaX = (float)(deltaX * scaling_coef);
        float screenDeltaZ = (float)(deltaZ * scaling_coef);
        final double x1 = screenDeltaX;
        final double z1 = screenDeltaZ;
        final double angle = Math.toRadians(180.0f - player_angle);
        final double x2 = x1 * Math.cos(angle) - z1 * Math.sin(angle);
        final double y2 = x1 * Math.sin(angle) + z1 * Math.cos(angle);
        screenDeltaX = (float)x2;
        screenDeltaZ = (float)y2;
        final float padding = 1.5f;
        if (Math.abs(screenDeltaX) > GuiMinimapOLD.map_size / 2 + padding || Math.abs(screenDeltaZ) > GuiMinimapOLD.map_size / 2 + padding) {
            return;
        }
        enemy_angle -= player_angle;
        try {
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(cx2 + screenDeltaX, cy2 + screenDeltaZ, 0.0f);
            GlStateManager.func_179114_b(enemy_angle, 0.0f, 0.0f, 1.0f);
            GlStateManager.func_179152_a(0.07f, 0.07f, 0.07f);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_73729_b(-40, -40, 80 * texture_offset_x, 80 * texture_offset_y, 80, 80);
            GlStateManager.func_179121_F();
            if (team_color != CustomScoreboard.TEAM_COLOR.NONE) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
                final ParticleController particleController = Main.particleController;
                Color color = ParticleController.getParticleColorForTeam(team_color);
                if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
                    color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
                }
                if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
                    color = new Color(0.0f, 0.5f, 1.0f, 1.0f);
                }
                if (GuiMinimapOLD.showNicknames && bwplayer != null) {
                    this.drawTextOnMap(pos, playerPos, scaling, 0.5f, player_angle, "" + bwplayer.name, color.getRGB(), 0.0, 3.0);
                }
                else if (GuiMinimapOLD.show_heights) {
                    final MyChatListener chatListener = Main.chatListener;
                    final BWBed game_bed = MyChatListener.GAME_BED;
                    int bed_pos_y = (int)pos.y;
                    if (game_bed != null) {
                        bed_pos_y = game_bed.part1_posY;
                    }
                    final int height_difference = (int)(pos.y - playerPos.y);
                    final int player_height_above_bed = (int)(playerPos.y - bed_pos_y);
                    final double speedY = GuiMinimapOLD.mc.field_71439_g.field_70163_u - GuiMinimapOLD.mc.field_71439_g.field_70167_r;
                    if ((player_height_above_bed > 5 || height_difference >= 5) && speedY > -1.0) {
                        this.drawTextOnMap(pos, playerPos, scaling, 0.5f, player_angle, "" + height_difference, color.getRGB(), 0.0, 3.0);
                    }
                }
                GlStateManager.func_179121_F();
            }
            if (GuiMinimapOLD.show_additional_information && team_color != CustomScoreboard.TEAM_COLOR.NONE && bwplayer != null && bwplayer.item_in_hand != null) {
                final BWItemsHandler.BWItemType item_type = bwplayer.item_in_hand.type;
                final float offsetY = -5.0f;
                GuiMinimapOLD.mc.field_71446_o.func_110577_a(this.resourceLoc_enemy);
                String text_item_count = "";
                int text_color = 0;
                float offsetY2 = 0.0f;
                if (item_type == BWItemsHandler.BWItemType.BLOCK_WOOL && enemy_pitch > 75.0f) {
                    final Tessellator tessellator = Tessellator.func_178181_a();
                    final WorldRenderer worldrenderer = tessellator.func_178180_c();
                    GlStateManager.func_179094_E();
                    GlStateManager.func_179109_b(cx2 + screenDeltaX, cy2 + screenDeltaZ, 0.0f);
                    GlStateManager.func_179152_a(0.2f, 0.2f, 0.2f);
                    worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
                    GlStateManager.func_179090_x();
                    final Color color2 = Main.playerFocus.getColorByTeam(team_color);
                    GlStateManager.func_179131_c(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, 1.0f);
                    final float width = 30.0f;
                    final float height = 4.0f;
                    final float px1 = -width / 2.0f;
                    final float py1 = -height / 2.0f - 20.0f;
                    final float px2 = px1 + width;
                    final float py2 = py1 + height;
                    worldrenderer.func_181662_b((double)px1, (double)py1, 0.0).func_181675_d();
                    worldrenderer.func_181662_b((double)px1, (double)py2, 0.0).func_181675_d();
                    worldrenderer.func_181662_b((double)px2, (double)py2, 0.0).func_181675_d();
                    worldrenderer.func_181662_b((double)px2, (double)py1, 0.0).func_181675_d();
                    tessellator.func_78381_a();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179121_F();
                }
                else if (item_type == BWItemsHandler.BWItemType.BOW) {
                    this.drawTexture(pos, playerPos, scaling, player_angle, 0.0f, 164, 240, 15, 16, 0.3f, 0.0f, offsetY);
                }
                else if (item_type == BWItemsHandler.BWItemType.PEARL) {
                    this.drawTexture(pos, playerPos, scaling, player_angle, 0.0f, 192, 243, 13, 13, 0.3f, 0.0f, offsetY);
                    text_item_count = "" + bwplayer.item_in_hand_amount;
                    text_color = this.getColor("2ccdb1ff");
                    offsetY2 = offsetY - 2.0f;
                }
                else if (item_type == BWItemsHandler.BWItemType.FIREBALL) {
                    this.drawTexture(pos, playerPos, scaling, player_angle, 0.0f, 205, 244, 12, 12, 0.35f, 0.0f, offsetY);
                    text_item_count = "" + bwplayer.item_in_hand_amount;
                    text_color = this.getColor("eb8517ff");
                    offsetY2 = offsetY - 2.0f;
                }
                else if (item_type == BWItemsHandler.BWItemType.POTION_STRENGTH) {
                    this.drawTexture(pos, playerPos, scaling, player_angle, 0.0f, 155, 243, 9, 13, 0.5f, 0.0f, offsetY - 2.0f);
                }
                else if (item_type == BWItemsHandler.BWItemType.DIAMOND) {
                    this.drawTexture(pos, playerPos, scaling, player_angle, 0.0f, 116, 243, 12, 13, 0.4f, 0.0f, offsetY - 1.0f);
                    text_item_count = "" + bwplayer.item_in_hand_amount;
                    text_color = this.getColor("8cf4e2ff");
                    offsetY2 = offsetY - 2.0f;
                }
                else if (item_type == BWItemsHandler.BWItemType.EMERALD) {
                    this.drawTexture(pos, playerPos, scaling, player_angle, 0.0f, 128, 242, 11, 14, 0.46f, 0.0f, offsetY - 1.0f);
                    text_item_count = "" + bwplayer.item_in_hand_amount;
                    text_color = this.getColor("2bdf64ff");
                    offsetY2 = offsetY - 2.0f;
                }
                if (text_item_count.length() > 0) {
                    GlStateManager.func_179094_E();
                    final float text_size = 0.5f;
                    GlStateManager.func_179152_a(text_size, text_size, text_size);
                    this.drawTextOnMap(pos, playerPos, scaling, text_size, player_angle, "" + bwplayer.item_in_hand_amount, text_color, 6.0, offsetY2);
                    GlStateManager.func_179121_F();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void drawTexture(final Pos pos, final Pos playerPos, final int scaling, final float player_angle, final float rotation, final int tex_x, final int tex_y, final int tex_size_x, final int tex_size_y, final float tex_scale, final float offsetX, final float offsetY) {
        final double deltaX = pos.x - playerPos.x;
        final double deltaY = pos.y - playerPos.y;
        final double deltaZ = pos.z - playerPos.z;
        final int cx = (GuiMinimapOLD.topX + GuiMinimapOLD.botX) / 2;
        final int cy = (GuiMinimapOLD.topY + GuiMinimapOLD.botY) / 2;
        final float scaling_coef = GuiMinimapOLD.map_size / (float)(scaling * 2);
        float screenDeltaX = (float)(deltaX * scaling_coef);
        float screenDeltaZ = (float)(deltaZ * scaling_coef);
        final double x1 = screenDeltaX;
        final double z1 = screenDeltaZ;
        final double angle = Math.toRadians(180.0f - player_angle);
        final double x2 = x1 * Math.cos(angle) - z1 * Math.sin(angle);
        final double y2 = x1 * Math.sin(angle) + z1 * Math.cos(angle);
        screenDeltaX = (float)x2;
        screenDeltaZ = (float)y2;
        if (Math.abs(screenDeltaX) > GuiMinimapOLD.map_size / 2 || Math.abs(screenDeltaZ) > GuiMinimapOLD.map_size / 2) {
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(cx + screenDeltaX + offsetX, cy + screenDeltaZ + offsetY, 0.0f);
        GlStateManager.func_179114_b(rotation, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179152_a(tex_scale, tex_scale, tex_scale);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_175174_a(-tex_size_x / 2.0f, -tex_size_y / 2.0f, tex_x, tex_y, tex_size_x, tex_size_y);
        GlStateManager.func_179121_F();
    }
    
    private void drawBed(final BlockPos pos, final Pos playerPos, final int scaling, final float player_angle, int dot_size, final boolean isPlayersBed) {
        dot_size = 6;
        final double deltaX = pos.func_177958_n() - playerPos.x + 0.5;
        final double deltaY = pos.func_177956_o() - playerPos.y;
        final double deltaZ = pos.func_177952_p() - playerPos.z + 0.5;
        final int cx = (GuiMinimapOLD.topX + GuiMinimapOLD.botX) / 2;
        final int cy = (GuiMinimapOLD.topY + GuiMinimapOLD.botY) / 2;
        final float scaling_coef = GuiMinimapOLD.map_size / (float)(scaling * 2);
        float screenDeltaX = (float)(deltaX * scaling_coef);
        float screenDeltaZ = (float)(deltaZ * scaling_coef);
        final double x1 = screenDeltaX;
        final double z1 = screenDeltaZ;
        final double angle = Math.toRadians(180.0f - player_angle);
        final double x2 = x1 * Math.cos(angle) - z1 * Math.sin(angle);
        final double y2 = x1 * Math.sin(angle) + z1 * Math.cos(angle);
        screenDeltaX = (float)x2;
        screenDeltaZ = (float)y2;
        if (Math.abs(screenDeltaX) > GuiMinimapOLD.map_size / 2 || Math.abs(screenDeltaZ) > GuiMinimapOLD.map_size / 2) {
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(cx + screenDeltaX, cy + screenDeltaZ, 0.0f);
        GlStateManager.func_179114_b(0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179152_a(0.25f, 0.25f, 0.25f);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_73729_b(-7, -7, 230 + (isPlayersBed ? -13 : 0), 243, 13, 13);
        GlStateManager.func_179121_F();
    }
    
    private int getColor(final String hexColor) {
        final Color colorNoAlpha = Color.decode("0x" + hexColor.substring(0, 6));
        final int alpha = Integer.parseInt(hexColor.substring(6, 8), 16);
        return new Color(colorNoAlpha.getRed(), colorNoAlpha.getGreen(), colorNoAlpha.getBlue(), alpha).getRGB();
    }
    
    static {
        GuiMinimapOLD.isActive = false;
        GuiMinimapOLD.isHidePlayersOnShift = false;
        GuiMinimapOLD.bedsFound = new ArrayList<MyBed>();
        GuiMinimapOLD.time_last_bed_scanned = 0L;
        GuiMinimapOLD.TIME_BED_SCAN = 2000L;
        GuiMinimapOLD.zoom = 0;
        GuiMinimapOLD.showNicknames = false;
    }
    
    public static class MyBed
    {
        public BlockPos pos;
        public BlockPos pos_feet;
        public long t;
        public boolean isPlayersBed;
        public ArrayList<BlockPos> obsidianPoses;
        
        public MyBed(final BlockPos pos, final BlockPos pos_feet, final long t, final boolean isPlayersBed) {
            this.pos = pos;
            this.pos_feet = pos_feet;
            this.t = t;
            this.isPlayersBed = isPlayersBed;
            this.obsidianPoses = new ArrayList<BlockPos>();
        }
    }
    
    public static class Pos
    {
        public double x;
        public double y;
        public double z;
        
        public Pos(final double x, final double y, final double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    
    public static class PosItem
    {
        double x;
        double y;
        double z;
        int type;
        int cnt;
        
        public PosItem(final double x, final double y, final double z, final int type, final int cnt) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.type = type;
            this.cnt = cnt;
        }
    }
}
