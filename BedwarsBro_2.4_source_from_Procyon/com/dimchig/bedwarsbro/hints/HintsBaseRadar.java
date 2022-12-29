// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.item.EnumDyeColor;
import com.dimchig.bedwarsbro.ColorCodesManager;
import java.util.Date;
import java.util.Iterator;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.entity.player.EntityPlayer;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.Main;
import java.util.List;
import com.dimchig.bedwarsbro.CustomScoreboard;
import java.util.ArrayList;

public class HintsBaseRadar
{
    public static int RADAR_RANGE_1;
    public static int RADAR_RANGE_2;
    public static int basePosX;
    public static int basePosY;
    public static int basePosZ;
    public static ArrayList<RadarAlert> alerts;
    public static CustomScoreboard.TEAM_COLOR mod_team_color;
    public static int RADAR_TIME_TRESHOLD;
    public static String prefix;
    public static boolean GAME_isBedBroken;
    public static List<CustomScoreboard.BedwarsTeam> game_bw_teams;
    public static long time_last_message_sent_for_team;
    
    public void setBase(final int x, final int y, final int z) {
        HintsBaseRadar.basePosX = x;
        HintsBaseRadar.basePosY = y;
        HintsBaseRadar.basePosZ = z;
        HintsBaseRadar.alerts = new ArrayList<RadarAlert>();
        HintsBaseRadar.game_bw_teams = new ArrayList<CustomScoreboard.BedwarsTeam>();
        HintsBaseRadar.mod_team_color = CustomScoreboard.TEAM_COLOR.NONE;
        HintsBaseRadar.GAME_isBedBroken = false;
        recognizeTeamColor();
    }
    
    public void updateBooleans() {
        HintsBaseRadar.RADAR_RANGE_1 = Main.getConfigInt(Main.CONFIG_MSG.RADAR_RANGE_FIRST);
        HintsBaseRadar.RADAR_RANGE_2 = Main.getConfigInt(Main.CONFIG_MSG.RADAR_RANGE_SECOND);
    }
    
    public static void recognizeTeamColor() {
        final List<CustomScoreboard.BedwarsTeam> teams = HintsBaseRadar.game_bw_teams = CustomScoreboard.readBedwarsGame();
        HintsBaseRadar.mod_team_color = MyChatListener.getEntityTeamColor((EntityPlayer)Minecraft.func_71410_x().field_71439_g);
        if (HintsBaseRadar.mod_team_color == CustomScoreboard.TEAM_COLOR.NONE) {
            ChatSender.addText(HintsBaseRadar.prefix + "&c\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0446\u0432\u0435\u0442 \u0442\u0432\u043e\u0435\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b!");
        }
    }
    
    public static void checkBedState() {
        if (HintsBaseRadar.GAME_isBedBroken) {
            return;
        }
        final List<CustomScoreboard.BedwarsTeam> teams = CustomScoreboard.readBedwarsGame();
        for (final CustomScoreboard.BedwarsTeam team : teams) {
            if (team.color == HintsBaseRadar.mod_team_color && team.state != CustomScoreboard.TEAM_STATE.BED_ALIVE) {
                HintsBaseRadar.GAME_isBedBroken = true;
                ChatSender.addText(HintsBaseRadar.prefix + "&c\u041a\u0440\u043e\u0432\u0430\u0442\u044c \u0441\u043b\u043e\u043c\u0430\u043d\u0430!" + (Main.getConfigBool(Main.CONFIG_MSG.RADAR_PLAYERS) ? " \u0420\u0430\u0434\u0430\u0440 \u043f\u0435\u0440\u0435\u043a\u043b\u044e\u0447\u0435\u043d \u0432 \u0440\u0435\u0436\u0438\u043c &l\u0438\u0433\u0440\u043e\u043a\u043e\u0432&c!" : ""));
            }
        }
    }
    
    public static void scan(final ArrayList<HintsPlayerScanner.BWPlayer> players, final boolean isRadarBaseActive, final boolean isPlayerRadarActive) {
        if (HintsBaseRadar.basePosX == -99999 || HintsBaseRadar.alerts == null) {
            return;
        }
        boolean isBedAlert = true;
        if (HintsBaseRadar.GAME_isBedBroken) {
            isBedAlert = false;
        }
        if (isBedAlert && !isRadarBaseActive) {
            return;
        }
        if (!isBedAlert && !isPlayerRadarActive) {
            return;
        }
        if (HintsBaseRadar.mod_team_color == CustomScoreboard.TEAM_COLOR.NONE) {
            if (Minecraft.func_71410_x().field_71441_e.func_82737_E() % 50L == 0L) {
                recognizeTeamColor();
            }
            return;
        }
        if (new Date().getTime() - MyChatListener.GAME_start_time < 3000L) {
            return;
        }
        if (isBedAlert && Minecraft.func_71410_x().field_71441_e.field_73012_v.nextInt(5) == 0) {
            checkBedState();
        }
        for (final HintsPlayerScanner.BWPlayer player : players) {
            int player_cnt = 1;
            for (final RadarAlert alert : HintsBaseRadar.alerts) {
                if (alert.player.team_color == player.team_color && player.distToPlayer <= alert.player.distToPlayer && !player.name.equals(alert.player.name)) {
                    ++player_cnt;
                }
            }
            int dist = 99999;
            if (isBedAlert) {
                dist = (int)Math.sqrt(Math.pow(player.posX - HintsBaseRadar.basePosX, 2.0) + Math.pow(player.posZ - HintsBaseRadar.basePosZ, 2.0));
            }
            else {
                dist = (int)Math.sqrt(Math.pow(player.posX - Minecraft.func_71410_x().field_71439_g.field_70165_t, 2.0) + Math.pow(player.posZ - Minecraft.func_71410_x().field_71439_g.field_70161_v, 2.0));
            }
            if (Minecraft.func_71410_x().field_71439_g == null) {
                return;
            }
            final double posY = isBedAlert ? (player.posY - HintsBaseRadar.basePosY) : (player.posY - Minecraft.func_71410_x().field_71439_g.field_70163_u);
            if (dist < HintsBaseRadar.RADAR_RANGE_1) {
                alertPlayerRange(player, posY, 1, player_cnt, isBedAlert);
            }
            else {
                if (dist >= HintsBaseRadar.RADAR_RANGE_2) {
                    continue;
                }
                alertPlayerRange(player, posY, 2, player_cnt, isBedAlert);
            }
        }
    }
    
    public static void alertPlayerRange(final HintsPlayerScanner.BWPlayer player, final double posY, final int range_id, final int count, final boolean isBedAlert) {
        if (Main.shopManager.findItemInHotbar("\u041d\u0430\u0431\u043b\u044e\u0434\u0435\u043d\u0438\u0435 \u0437\u0430") != -1) {
            return;
        }
        if (player.team_color == CustomScoreboard.TEAM_COLOR.NONE || player.team_color == HintsBaseRadar.mod_team_color) {
            return;
        }
        if (HintsBaseRadar.basePosY - player.posY >= 5.0) {
            return;
        }
        for (int i = 0; i < HintsBaseRadar.alerts.size() && i >= 0 && i < HintsBaseRadar.alerts.size(); ++i) {
            final RadarAlert alert = HintsBaseRadar.alerts.get(i);
            if (alert.getTimePassed() > HintsBaseRadar.RADAR_TIME_TRESHOLD) {
                HintsBaseRadar.alerts.remove(alert);
                i = 0;
            }
            else if (alert.player.name.equals(player.name)) {
                if (alert.range_id <= range_id) {
                    return;
                }
                HintsBaseRadar.alerts.remove(alert);
                --i;
            }
        }
        final RadarAlert radarAlert = new RadarAlert(player, posY, range_id);
        HintsBaseRadar.alerts.add(radarAlert);
        String str = "";
        final String team_color = CustomScoreboard.getCodeByTeamColor(player.team_color);
        final String team_name_1_person = CustomScoreboard.getTeamNameSinglePlayerByTeamColor(player.team_color);
        final double height_diff = posY;
        if (range_id == 1) {
            if (height_diff > 5.0) {
                str += (isBedAlert ? "&c\u0421\u0432\u0435\u0440\u0445\u0443 \u0431\u0430\u0437\u044b" : "&c\u0421\u0432\u0435\u0440\u0445\u0443");
            }
            else {
                str += (isBedAlert ? "&c\u041d\u0430\u0441 \u043b\u043e\u043c\u0430\u0435\u0442" : "&c\u0420\u044f\u0434\u043e\u043c");
            }
            MyChatListener.playSound(MyChatListener.SOUND_RADAR_CLOSE);
            Main.guiRadarIcon.show(isBedAlert);
        }
        else if (range_id == 2) {
            if (height_diff > 7.0) {
                str += (isBedAlert ? "&e\u041d\u0430\u0441 \u0441\u0432\u0435\u0440\u0445\u0443 \u0440\u0430\u0448\u0438\u0442" : "&e\u0421\u0432\u0435\u0440\u0445\u0443");
            }
            else {
                str += (isBedAlert ? "&e\u041d\u0430\u0441 \u0440\u0430\u0448\u0438\u0442" : "&e\u0420\u044f\u0434\u043e\u043c");
            }
            MyChatListener.playSound(MyChatListener.SOUND_RADAR_FAR);
        }
        str = str + "&" + team_color;
        if (count > 1) {
            str = str + " " + count + "-\u0439";
        }
        str = str + " " + team_name_1_person;
        if (HintsValidator.isRadarChatMessage() && isBedAlert && new Date().getTime() - HintsBaseRadar.time_last_message_sent_for_team > Main.ANTIMUT_DELAY) {
            HintsBaseRadar.time_last_message_sent_for_team = new Date().getTime();
            String s = str;
            final String str_final = str;
            if (!HintsValidator.isBedwarsMeowColorsActive()) {
                s = ColorCodesManager.removeColorCodes(s);
            }
            Minecraft.func_71410_x().field_71439_g.func_71165_d(s);
        }
        ChatSender.addText(HintsBaseRadar.prefix + str);
    }
    
    public static void visualizeZone() {
        drawZone(8, EnumDyeColor.RED);
        drawZone(20, EnumDyeColor.YELLOW);
        drawZone(35, EnumDyeColor.GREEN);
    }
    
    public static void drawZone(final int range, final EnumDyeColor block_color) {
        for (int yi = 2; yi <= 2; ++yi) {
            for (int xi = -range * 2; xi < range * 2; ++xi) {
                for (int zi = -range * 2; zi < range * 2; ++zi) {
                    final int bx = HintsBaseRadar.basePosX + xi;
                    final int by = HintsBaseRadar.basePosY + yi;
                    final int bz = HintsBaseRadar.basePosZ + zi;
                    final int dist = (int)Math.sqrt(Math.pow(bx - HintsBaseRadar.basePosX, 2.0) + Math.pow(bz - HintsBaseRadar.basePosZ, 2.0));
                    if (dist == range) {
                        final Block block = Minecraft.func_71410_x().field_71441_e.func_180495_p(new BlockPos(bx, by, bz)).func_177230_c();
                        if (block.func_149732_F().contains("air")) {
                            final WorldClient field_71441_e = Minecraft.func_71410_x().field_71441_e;
                            final BlockPos blockPos = new BlockPos(bx, by, bz);
                            final IBlockState func_176223_P = Blocks.field_150399_cn.func_176223_P();
                            final BlockStainedGlass field_150399_cn = Blocks.field_150399_cn;
                            field_71441_e.func_175656_a(blockPos, func_176223_P.func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)block_color));
                        }
                    }
                }
            }
        }
    }
    
    static {
        HintsBaseRadar.RADAR_RANGE_1 = 8;
        HintsBaseRadar.RADAR_RANGE_2 = 20;
        HintsBaseRadar.basePosX = -9999;
        HintsBaseRadar.basePosY = -9999;
        HintsBaseRadar.basePosZ = -9999;
        HintsBaseRadar.mod_team_color = CustomScoreboard.TEAM_COLOR.NONE;
        HintsBaseRadar.RADAR_TIME_TRESHOLD = 10000;
        HintsBaseRadar.prefix = MyChatListener.PREFIX_HINT_RADAR;
        HintsBaseRadar.time_last_message_sent_for_team = 0L;
    }
    
    public static class RadarAlert
    {
        public HintsPlayerScanner.BWPlayer player;
        public int range_id;
        public double posY;
        public long time;
        
        public RadarAlert(final HintsPlayerScanner.BWPlayer player, final double posY, final int range_id) {
            this.player = player;
            this.range_id = range_id;
            this.posY = posY;
            this.time = new Date().getTime();
        }
        
        public long getTimePassed() {
            return new Date().getTime() - this.time;
        }
    }
}
