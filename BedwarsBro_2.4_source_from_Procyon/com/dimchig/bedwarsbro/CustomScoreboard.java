// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.Collection;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public class CustomScoreboard
{
    private static ScoreboardManager sm;
    private static boolean isEnglishScoreboard;
    private static String[] russianTranslations;
    private static String[] englishTranslations;
    public static String[] team_names;
    public static String[] team_names_english;
    public static String[] team_names_single_player;
    
    public CustomScoreboard() {
        CustomScoreboard.sm = new ScoreboardManager();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        CustomScoreboard.isEnglishScoreboard = Main.getConfigBool(Main.CONFIG_MSG.SCOREBOARD_ENGLISH);
    }
    
    public static void updateScoreboard() {
        final String subscribe = "&6\u041f\u043e\u0434\u043f\u0438\u0448\u0438\u0441\u044c \u043d\u0430 &e&l\u043a\u0430\u043d\u0430\u043b&6!";
        final String mod_name = "&7\u041c\u043e\u0434 &8\u25b8 &cBedwars&fBro";
        final String[] servers = { "MineBlaze", "DexLand", "MasedWorld" };
        if (Minecraft.func_71410_x() == null || Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        String mod_player_name = Minecraft.func_71410_x().field_71439_g.func_70005_c_();
        mod_player_name = ChatSender.parseText(mod_player_name);
        final boolean isAdmin = Main.isPropSelfAdmin();
        final ScoreboardManager sm = CustomScoreboard.sm;
        ScoreboardManager.readScoreboard();
        final ScoreboardManager sm2 = CustomScoreboard.sm;
        Label_1233: {
            if (ScoreboardManager.getText("\u0440\u043e\u0432\u0430\u0442\u0435\u0439") != null) {
                final ScoreboardManager sm3 = CustomScoreboard.sm;
                if (ScoreboardManager.getText("\u043c\u0435\u0440\u0442") != null) {
                    if (isAdmin) {
                        final ScoreboardManager sm4 = CustomScoreboard.sm;
                        ScoreboardManager.replaceText(servers, subscribe);
                        final ScoreboardManager sm5 = CustomScoreboard.sm;
                        if (ScoreboardManager.getText("\u0414\u043e\u043d\u0430\u0442") != null) {
                            final ScoreboardManager sm6 = CustomScoreboard.sm;
                            final ScoreboardManager sm7 = CustomScoreboard.sm;
                            ScoreboardManager.replaceText(ScoreboardManager.getText("\u0414\u043e\u043d\u0430\u0442"), " \u041f\u0440\u0435\u0444\u0438\u043a\u0441 &7\u25b8 " + Main.getPropAuthorPrefix());
                        }
                    }
                    final ScoreboardManager sm8 = CustomScoreboard.sm;
                    if (ScoreboardManager.getText("\u041d\u0438\u043a") != null) {
                        final ScoreboardManager sm9 = CustomScoreboard.sm;
                        final ScoreboardManager sm10 = CustomScoreboard.sm;
                        ScoreboardManager.replaceText(ScoreboardManager.getText("\u041d\u0438\u043a"), " \u041d\u0438\u043a &7\u25b8 &a&l" + mod_player_name);
                    }
                    final ScoreboardManager sm11 = CustomScoreboard.sm;
                    ScoreboardManager.replaceText("\u0421\u043a\u0440\u044b\u0442\u044c: /board", " " + mod_name);
                    try {
                        final ScoreboardManager sm12 = CustomScoreboard.sm;
                        String text = ScoreboardManager.getText("\u0423\u0431\u0438\u0439\u0441\u0442\u0432:");
                        if (text != null && text.contains(" ")) {
                            final ScoreboardManager sm13 = CustomScoreboard.sm;
                            ScoreboardManager.replaceText(text, " \u041a\u0438\u043b\u043e\u0432 &7\u25b8 &c" + text.split(" ")[1].trim());
                        }
                        final ScoreboardManager sm14 = CustomScoreboard.sm;
                        text = ScoreboardManager.getText("\u0421\u043c\u0435\u0440\u0442\u0435\u0439:");
                        if (text != null && text.contains(": ")) {
                            final ScoreboardManager sm15 = CustomScoreboard.sm;
                            ScoreboardManager.replaceText(text, " \u0421\u043c\u0435\u0440\u0442\u0435\u0439 &7\u25b8 &e" + text.split(": ")[1].trim());
                        }
                        final ScoreboardManager sm16 = CustomScoreboard.sm;
                        text = ScoreboardManager.getText("K/D:");
                        if (text != null && text.contains(": ")) {
                            final ScoreboardManager sm17 = CustomScoreboard.sm;
                            ScoreboardManager.replaceText(text, " K/D &7\u25b8 &a" + text.split(": ")[1].trim());
                        }
                        final ScoreboardManager sm18 = CustomScoreboard.sm;
                        text = ScoreboardManager.getText("\u0418\u0433\u0440:");
                        int games_cnt = -1;
                        try {
                            final ScoreboardManager sm19 = CustomScoreboard.sm;
                            games_cnt = Integer.parseInt(ScoreboardManager.getText("\u0418\u0433\u0440:").split(": ")[1].trim());
                        }
                        catch (Exception ex) {}
                        if (text != null && text.contains(": ")) {
                            final ScoreboardManager sm20 = CustomScoreboard.sm;
                            ScoreboardManager.replaceText(text, " \u041a\u0430\u0442\u043e\u043a &7\u25b8 &b" + text.split(": ")[1].trim());
                        }
                        final ScoreboardManager sm21 = CustomScoreboard.sm;
                        text = ScoreboardManager.getText("\u041f\u043e\u0431\u0435\u0434:");
                        double win_rate = -1.0;
                        String new_text = " \u041f\u043e\u0431\u0435\u0434 &7\u25b8 &9" + text.split(": ")[1].trim();
                        int wins_cnt = -1;
                        try {
                            final ScoreboardManager sm22 = CustomScoreboard.sm;
                            wins_cnt = Integer.parseInt(ScoreboardManager.getText("\u041f\u043e\u0431\u0435\u0434:").split(": ")[1].trim());
                        }
                        catch (Exception ex2) {}
                        if (games_cnt != -1 && wins_cnt != -1) {
                            win_rate = wins_cnt / (float)games_cnt;
                        }
                        if (win_rate > 0.0) {
                            new_text = new_text + "&7 | &fWR &7\u25b8 &9" + (int)(win_rate * 100.0) + "%";
                        }
                        if (text != null && text.contains(": ")) {
                            final ScoreboardManager sm23 = CustomScoreboard.sm;
                            ScoreboardManager.replaceText(text, new_text);
                        }
                        final ScoreboardManager sm24 = CustomScoreboard.sm;
                        text = ScoreboardManager.getText("\u0421\u043b\u043e\u043c. \u043a\u0440\u043e\u0432\u0430\u0442\u0435\u0439:");
                        new_text = " \u041a\u0440\u043e\u0432\u0430\u0442\u0435\u0439 &7\u25b8 &d" + text.split(": ")[1].trim();
                        int beds_cnt = -1;
                        try {
                            final ScoreboardManager sm25 = CustomScoreboard.sm;
                            beds_cnt = Integer.parseInt(ScoreboardManager.getText("\u0421\u043b\u043e\u043c. \u043a\u0440\u043e\u0432\u0430\u0442\u0435\u0439:").split(": ")[1].trim());
                        }
                        catch (Exception ex3) {}
                        if (beds_cnt <= 0 || games_cnt > 0) {}
                        if (text != null && text.contains(": ")) {
                            final ScoreboardManager sm26 = CustomScoreboard.sm;
                            ScoreboardManager.replaceText(text, new_text);
                        }
                    }
                    catch (Exception ex4) {}
                    break Label_1233;
                }
            }
            final ScoreboardManager sm27 = CustomScoreboard.sm;
            if (ScoreboardManager.getText("\u0421\u0442\u0430\u0440\u0442 \u0447\u0435\u0440\u0435\u0437: ") == null) {
                final ScoreboardManager sm28 = CustomScoreboard.sm;
                if (ScoreboardManager.getText("\u041a\u0430\u0440\u0442\u0430: ") == null) {
                    final ScoreboardManager sm29 = CustomScoreboard.sm;
                    if (ScoreboardManager.getText(ColorCodesManager.removeColorCodes(CustomScoreboard.russianTranslations[1])) == null) {
                        final ScoreboardManager sm30 = CustomScoreboard.sm;
                        if (ScoreboardManager.getText(CustomScoreboard.russianTranslations[1]) == null) {
                            final ScoreboardManager sm31 = CustomScoreboard.sm;
                            if (ScoreboardManager.getText(CustomScoreboard.englishTranslations[1]) == null) {
                                break Label_1233;
                            }
                        }
                    }
                    for (int i = 0; i < CustomScoreboard.team_names.length; ++i) {
                        final String team_name = CustomScoreboard.team_names[i];
                        final String team_name_new = CustomScoreboard.isEnglishScoreboard ? CustomScoreboard.team_names_english[i] : team_name;
                        final TEAM_COLOR team_color = getTeamColorByName(team_name);
                        final String colorcode = "&" + getCodeByTeamColor(team_color);
                        final ScoreboardManager sm32 = CustomScoreboard.sm;
                        String source = ScoreboardManager.getText(team_name);
                        if (source != null) {
                            source = source.trim();
                            String replace = team_name;
                            if (source.contains(":")) {
                                replace += ":";
                            }
                            else {
                                replace = replace + colorcode + " \u25b8";
                            }
                            String extraFormatting = "";
                            if (source.contains("\u2717")) {
                                extraFormatting = "&m";
                            }
                            final ScoreboardManager sm33 = CustomScoreboard.sm;
                            ScoreboardManager.hardReplaceText(replace, ColorCodesManager.replaceColorCodesInString(colorcode + extraFormatting + team_name_new + colorcode + " \u25b8"));
                        }
                    }
                    if (CustomScoreboard.isEnglishScoreboard) {
                        for (int j = 0; j < CustomScoreboard.russianTranslations.length; ++j) {
                            final ScoreboardManager sm34 = CustomScoreboard.sm;
                            ScoreboardManager.hardReplaceText(CustomScoreboard.russianTranslations[j], CustomScoreboard.englishTranslations[j]);
                        }
                    }
                    final ScoreboardManager sm35 = CustomScoreboard.sm;
                    final String text_your_team = ScoreboardManager.getText("(\u0412\u044b)");
                    if (text_your_team != null) {
                        final ScoreboardManager sm36 = CustomScoreboard.sm;
                        ScoreboardManager.hardReplaceText("(\u0412\u044b)", "&8\u2190");
                    }
                    if (isAdmin) {
                        final ScoreboardManager sm37 = CustomScoreboard.sm;
                        ScoreboardManager.replaceText(servers, subscribe);
                        break Label_1233;
                    }
                    final ScoreboardManager sm38 = CustomScoreboard.sm;
                    ScoreboardManager.replaceText(servers, mod_name);
                    break Label_1233;
                }
            }
            if (isAdmin) {
                final ScoreboardManager sm39 = CustomScoreboard.sm;
                ScoreboardManager.replaceText(servers, subscribe);
            }
        }
        final String tab_header_text = "\n" + mod_name + " &7v&a" + "2.4" + " &7(&e/bwbro&7)\n";
        String tab_footer_text = "";
        if (Minecraft.func_71410_x().func_147104_D() != null) {
            tab_footer_text = "\n&7\u0422\u044b \u0438\u0433\u0440\u0430\u0435\u0448\u044c \u043d\u0430 &e" + Minecraft.func_71410_x().func_147104_D().field_78845_b + "\n";
        }
        Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175244_b((IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(tab_header_text)));
        Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175248_a((IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(tab_footer_text)));
    }
    
    public static TEAM_COLOR getTeamColorByCode(String color) {
        if (color.contains("&") || color.contains("§")) {
            color = color.substring(1);
        }
        if (color.equals("c")) {
            return TEAM_COLOR.RED;
        }
        if (color.equals("e")) {
            return TEAM_COLOR.YELLOW;
        }
        if (color.equals("a")) {
            return TEAM_COLOR.GREEN;
        }
        if (color.equals("b")) {
            return TEAM_COLOR.AQUA;
        }
        if (color.equals("9")) {
            return TEAM_COLOR.BLUE;
        }
        if (color.equals("d")) {
            return TEAM_COLOR.PINK;
        }
        if (color.equals("7")) {
            return TEAM_COLOR.GRAY;
        }
        if (color.equals("f")) {
            return TEAM_COLOR.WHITE;
        }
        return TEAM_COLOR.NONE;
    }
    
    public static String getTeamNameByTeamColor(final TEAM_COLOR color) {
        if (color == TEAM_COLOR.RED) {
            return CustomScoreboard.team_names[0];
        }
        if (color == TEAM_COLOR.YELLOW) {
            return CustomScoreboard.team_names[1];
        }
        if (color == TEAM_COLOR.GREEN) {
            return CustomScoreboard.team_names[2];
        }
        if (color == TEAM_COLOR.AQUA) {
            return CustomScoreboard.team_names[3];
        }
        if (color == TEAM_COLOR.BLUE) {
            return CustomScoreboard.team_names[4];
        }
        if (color == TEAM_COLOR.PINK) {
            return CustomScoreboard.team_names[5];
        }
        if (color == TEAM_COLOR.GRAY) {
            return CustomScoreboard.team_names[6];
        }
        if (color == TEAM_COLOR.WHITE) {
            return CustomScoreboard.team_names[7];
        }
        return "-";
    }
    
    public static String getTeamNameSinglePlayerByTeamColor(final TEAM_COLOR color) {
        if (color == TEAM_COLOR.RED) {
            return CustomScoreboard.team_names_single_player[0];
        }
        if (color == TEAM_COLOR.YELLOW) {
            return CustomScoreboard.team_names_single_player[1];
        }
        if (color == TEAM_COLOR.GREEN) {
            return CustomScoreboard.team_names_single_player[2];
        }
        if (color == TEAM_COLOR.AQUA) {
            return CustomScoreboard.team_names_single_player[3];
        }
        if (color == TEAM_COLOR.BLUE) {
            return CustomScoreboard.team_names_single_player[4];
        }
        if (color == TEAM_COLOR.PINK) {
            return CustomScoreboard.team_names_single_player[5];
        }
        if (color == TEAM_COLOR.GRAY) {
            return CustomScoreboard.team_names_single_player[6];
        }
        if (color == TEAM_COLOR.WHITE) {
            return CustomScoreboard.team_names_single_player[7];
        }
        return "-";
    }
    
    public static TEAM_COLOR getTeamColorByName(final String name) {
        if (name.equals(CustomScoreboard.team_names[0])) {
            return TEAM_COLOR.RED;
        }
        if (name.equals(CustomScoreboard.team_names[1])) {
            return TEAM_COLOR.YELLOW;
        }
        if (name.equals(CustomScoreboard.team_names[2])) {
            return TEAM_COLOR.GREEN;
        }
        if (name.equals(CustomScoreboard.team_names[3])) {
            return TEAM_COLOR.AQUA;
        }
        if (name.equals(CustomScoreboard.team_names[4])) {
            return TEAM_COLOR.BLUE;
        }
        if (name.equals(CustomScoreboard.team_names[5])) {
            return TEAM_COLOR.PINK;
        }
        if (name.equals(CustomScoreboard.team_names[6])) {
            return TEAM_COLOR.GRAY;
        }
        if (name.equals(CustomScoreboard.team_names[7])) {
            return TEAM_COLOR.WHITE;
        }
        return TEAM_COLOR.NONE;
    }
    
    public static String getCodeByTeamColor(final TEAM_COLOR color) {
        if (color == TEAM_COLOR.RED) {
            return "c";
        }
        if (color == TEAM_COLOR.YELLOW) {
            return "e";
        }
        if (color == TEAM_COLOR.GREEN) {
            return "a";
        }
        if (color == TEAM_COLOR.AQUA) {
            return "b";
        }
        if (color == TEAM_COLOR.BLUE) {
            return "9";
        }
        if (color == TEAM_COLOR.PINK) {
            return "d";
        }
        if (color == TEAM_COLOR.GRAY) {
            return "7";
        }
        if (color == TEAM_COLOR.WHITE) {
            return "f";
        }
        return "r";
    }
    
    public static List<BedwarsTeam> readBedwarsGame() {
        final ArrayList<BedwarsTeam> teams = new ArrayList<BedwarsTeam>();
        final ScoreboardManager sm = CustomScoreboard.sm;
        ScoreboardManager.readScoreboard();
        for (int i = 0; i < CustomScoreboard.team_names.length; ++i) {
            final String team_russian = CustomScoreboard.team_names[i];
            final String team_english = CustomScoreboard.team_names_english[i];
            final ScoreboardManager sm2 = CustomScoreboard.sm;
            String str = ScoreboardManager.getText(team_russian);
            if (str == null) {
                final ScoreboardManager sm3 = CustomScoreboard.sm;
                str = ScoreboardManager.getText(team_english);
            }
            if (str != null) {
                final BedwarsTeam team = new BedwarsTeam(getTeamColorByName(team_russian));
                try {
                    String icon = "";
                    if (str.contains(":")) {
                        icon = str.split(":")[1].trim().split(" ")[0].trim();
                    }
                    else if (str.contains("\u25b8")) {
                        icon = str.split("\u25b8")[1].trim().split(" ")[0].trim();
                    }
                    if (icon.codePointAt(0) == 10004) {
                        team.state = TEAM_STATE.BED_ALIVE;
                    }
                    else if (icon.codePointAt(0) == 10007) {
                        team.state = TEAM_STATE.DESTROYED;
                    }
                    else {
                        try {
                            Integer.parseInt(icon);
                            team.state = TEAM_STATE.BED_BROKEN;
                        }
                        catch (NumberFormatException nfe) {
                            team.state = TEAM_STATE.NONE;
                        }
                    }
                    if (team.state != TEAM_STATE.NONE) {
                        teams.add(team);
                    }
                }
                catch (Exception ex) {}
            }
        }
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc == null || mc.field_71439_g == null) {
            return teams;
        }
        if (mc.func_147114_u() == null || mc.func_147114_u().func_175106_d() == null) {
            return teams;
        }
        final Collection<NetworkPlayerInfo> players = (Collection<NetworkPlayerInfo>)mc.func_147114_u().func_175106_d();
        if (players == null || players.size() == 0) {
            return teams;
        }
        final EntityPlayerSP mod_player = mc.field_71439_g;
        for (final NetworkPlayerInfo info : players) {
            if (info.func_178845_a() != null) {
                if (info.func_178850_i() == null) {
                    continue;
                }
                final String player_name = info.func_178845_a().getName();
                final String team_name = info.func_178850_i().func_96661_b();
                final MyChatListener chatListener = Main.chatListener;
                final TEAM_COLOR c = MyChatListener.getEntityTeamColorByTeam(team_name);
                BedwarsTeam team2 = null;
                for (final BedwarsTeam t : teams) {
                    if (t.color == c) {
                        team2 = t;
                        break;
                    }
                }
                if (team2 == null) {
                    continue;
                }
                team2.players.add(new BedwarsPlayer(player_name, team2));
            }
        }
        return teams;
    }
    
    public static void printData() {
        try {
            final List<BedwarsTeam> teams = readBedwarsGame();
            Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("teams: " + teams.size()));
            for (final BedwarsTeam t : teams) {
                String stateColor = "&a";
                if (t.state == TEAM_STATE.BED_BROKEN) {
                    stateColor = "&e";
                }
                if (t.state == TEAM_STATE.DESTROYED) {
                    stateColor = "&c";
                }
                String str = stateColor + t.state + " &" + getCodeByTeamColor(t.color) + t.color + ":\n   &f- ";
                for (final BedwarsPlayer p : t.players) {
                    str = str + p.name + ", ";
                }
                Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(str)));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    static {
        CustomScoreboard.isEnglishScoreboard = false;
        CustomScoreboard.russianTranslations = new String[] { "\u0423\u0431\u0438\u0439\u0441\u0442\u0432", "\u0424\u0438\u043d\u0430\u043b\u044c\u043d\u044b\u0445§f \u0443\u0431\u0438\u0439\u0441\u0442\u0432", "\u0421\u043b\u043e\u043c\u0430\u043d\u043e \u043a§f\u0440\u043e\u0432\u0430\u0442\u0435\u0439" };
        CustomScoreboard.englishTranslations = new String[] { "Kills", "Finals", "Beds" };
        CustomScoreboard.team_names = new String[] { "\u041a\u0440\u0430\u0441\u043d\u044b\u0435", "\u0416\u0435\u043b\u0442\u044b\u0435", "\u0417\u0435\u043b\u0435\u043d\u044b\u0435", "\u0413\u043e\u043b\u0443\u0431\u044b\u0435", "\u0421\u0438\u043d\u0438\u0435", "\u0420\u043e\u0437\u043e\u0432\u044b\u0435", "\u0421\u0435\u0440\u044b\u0435", "\u0411\u0435\u043b\u044b\u0435" };
        CustomScoreboard.team_names_english = new String[] { "Red", "Yellow", "Green", "Aqua", "Blue", "Pink", "Gray", "White" };
        CustomScoreboard.team_names_single_player = new String[] { "\u041a\u0440\u0430\u0441\u043d\u044b\u0439", "\u0416\u0435\u043b\u0442\u044b\u0439", "\u0417\u0435\u043b\u0435\u043d\u044b\u0439", "\u0413\u043e\u043b\u0443\u0431\u044b\u0439", "\u0421\u0438\u043d\u0438\u0439", "\u0420\u043e\u0437\u043e\u0432\u044b\u0439", "\u0421\u0435\u0440\u044b\u0439", "\u0411\u0435\u043b\u044b\u0439" };
    }
    
    public enum TEAM_COLOR
    {
        RED, 
        YELLOW, 
        GREEN, 
        AQUA, 
        BLUE, 
        PINK, 
        GRAY, 
        WHITE, 
        NONE;
    }
    
    public enum TEAM_STATE
    {
        BED_ALIVE, 
        BED_BROKEN, 
        DESTROYED, 
        NONE;
    }
    
    public static class BedwarsPlayer
    {
        public String name;
        public BedwarsTeam team;
        
        public BedwarsPlayer(final String name, final BedwarsTeam team) {
            this.name = name;
            this.team = team;
        }
    }
    
    public static class BedwarsTeam
    {
        public TEAM_COLOR color;
        public List<BedwarsPlayer> players;
        public TEAM_STATE state;
        
        public BedwarsTeam(final TEAM_COLOR color) {
            this.color = color;
            this.players = new ArrayList<BedwarsPlayer>();
            this.state = TEAM_STATE.NONE;
        }
    }
}
