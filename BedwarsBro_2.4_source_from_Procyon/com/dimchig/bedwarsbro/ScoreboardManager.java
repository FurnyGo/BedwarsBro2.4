// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.client.Minecraft;
import java.util.List;

public class ScoreboardManager
{
    public static List<MyScoreboardLine> lines;
    
    public static String readRawScoreboard() {
        String s = "";
        try {
            final Scoreboard scoreboard = Minecraft.func_71410_x().field_71441_e.func_96441_U();
            for (final ScorePlayerTeam team : scoreboard.func_96525_g()) {
                s += (team.func_96668_e() + team.func_96663_f()).trim();
            }
            return s;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void readScoreboard() {
        try {
            ScoreboardManager.lines = new ArrayList<MyScoreboardLine>();
            final Scoreboard scoreboard = Minecraft.func_71410_x().field_71441_e.func_96441_U();
            for (final ScorePlayerTeam team : scoreboard.func_96525_g()) {
                final String s = ColorCodesManager.removeColorCodes(team.func_96668_e() + team.func_96663_f());
                if (s.length() > 1) {
                    ScoreboardManager.lines.add(new MyScoreboardLine(team, s));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void hardReplaceText(final String from, final String to) {
        hardReplaceText(from, from, to);
    }
    
    public static void hardReplaceText(final String findby, final String from, final String to) {
        readScoreboard();
        final ArrayList<MyScoreboardLine> lines2 = new ArrayList<MyScoreboardLine>();
        lines2.addAll(ScoreboardManager.lines);
        for (final MyScoreboardLine l : lines2) {
            if (l != null) {
                if (l.team == null) {
                    continue;
                }
                final String prefix = l.team.func_96668_e();
                final String suffix = l.team.func_96663_f();
                if (prefix == null) {
                    continue;
                }
                if (suffix == null) {
                    continue;
                }
                final String new_text = (prefix + suffix).replace("&", "§").replace("§r", "").trim();
                l.team.func_96662_c(new_text);
                l.team.func_96666_b("");
                if (new_text.contains(findby)) {
                    l.team.func_96662_c(l.team.func_96663_f().replace(from, ColorCodesManager.replaceColorCodesInString(to)));
                }
                l.value = ColorCodesManager.removeColorCodes(new_text);
            }
        }
    }
    
    public static void replaceText(final String from, final String to) {
        readScoreboard();
        for (final MyScoreboardLine l : ScoreboardManager.lines) {
            if (l.value.contains(from)) {
                l.setText(to);
                break;
            }
        }
    }
    
    public static void replaceTextPartly(final String from, final String to) {
        readScoreboard();
        for (final MyScoreboardLine l : ScoreboardManager.lines) {
            if (l.value.contains(from)) {
                l.setText(l.value.replace(from, to));
                break;
            }
        }
    }
    
    public static void replaceText(final String[] from_array, final String to) {
        for (final String s : from_array) {
            replaceText(s, to);
        }
    }
    
    public static String getText(final String ref) {
        readScoreboard();
        for (final MyScoreboardLine l : ScoreboardManager.lines) {
            if (l != null) {
                if (l.value == null) {
                    continue;
                }
                if (l.value.contains(ref)) {
                    return l.value;
                }
                continue;
            }
        }
        return null;
    }
    
    public static String getRawText(final String ref) {
        readScoreboard();
        for (final MyScoreboardLine l : ScoreboardManager.lines) {
            if (l.value.contains(ref)) {
                return (l.team.func_96668_e() + l.team.func_96663_f()).trim();
            }
        }
        return null;
    }
    
    static {
        ScoreboardManager.lines = new ArrayList<MyScoreboardLine>();
    }
    
    public static class MyScoreboardLine
    {
        public ScorePlayerTeam team;
        String value;
        
        public MyScoreboardLine(final ScorePlayerTeam team, final String value) {
            this.team = team;
            this.value = value;
        }
        
        public void setText(final String text) {
            final String new_val = text.replace("&", "§");
            this.team.func_96666_b(new_val);
            this.team.func_96662_c("");
            this.value = new_val;
        }
    }
}
