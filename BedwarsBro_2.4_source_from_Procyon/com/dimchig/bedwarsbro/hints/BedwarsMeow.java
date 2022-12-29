// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.commands.CommandRainbowMessage;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.CustomScoreboard;
import java.util.Collection;
import java.util.List;
import java.util.Collections;
import java.util.Random;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import java.util.regex.Pattern;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.FileManager;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.ArrayList;

public class BedwarsMeow
{
    public static String filename;
    public static ArrayList<MeowMsg> meowMessages;
    String string_for_bed_single;
    String string_for_bed_multi;
    String category_kill_messages;
    String category_final_kill_messages;
    String category_death_messages;
    String category_death_void_messages;
    String category_bed_messages;
    String category_bed_own_messages;
    String category_wins_messages;
    String category_game_start_messages;
    String prefix;
    ArrayList<MeowMsg> meowMessagesQueue;
    public boolean IS_USE_COLORS;
    public boolean IS_ACTIVE;
    public static boolean IS_AUTO_RANDOM_ACTIVE;
    
    public String upperCaseFirstLetter(final String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
    
    public BedwarsMeow() {
        this.string_for_bed_single = "(1 \u0438\u0433\u0440\u043e\u043a)";
        this.string_for_bed_multi = "(2 \u0438 \u0431\u043e\u043b\u044c\u0448\u0435 \u0438\u0433\u0440\u043e\u043a\u043e\u0432)";
        this.category_kill_messages = "\u041f\u041e\u0421\u041b\u0415 \u0423\u0411\u0418\u0419\u0421\u0422\u0412\u0410 (\u0437\u0430\u043c\u0435\u043d\u0438\u0442\u0441\u044f \u0441\u043b\u043e\u0432\u043e \"\u0438\u0433\u0440\u043e\u043a\")";
        this.category_final_kill_messages = "\u041f\u041e\u0421\u041b\u0415 \u0424\u0418\u041d\u0410\u041b\u042c\u041d\u041e\u0413\u041e \u0423\u0411\u0418\u0419\u0421\u0422\u0412\u0410 (\u0437\u0430\u043c\u0435\u043d\u0438\u0442\u0441\u044f \u0441\u043b\u043e\u0432\u043e \"\u0438\u0433\u0440\u043e\u043a\")";
        this.category_death_messages = "\u041f\u0420\u0418 \u0421\u041c\u0415\u0420\u0422\u0418 (\u0437\u0430\u043c\u0435\u043d\u0438\u0442\u0441\u044f \u0441\u043b\u043e\u0432\u043e \"\u0438\u0433\u0440\u043e\u043a\" [\u0442\u043e\u0442, \u043a\u0442\u043e \u0442\u0435\u0431\u044f \u0443\u0431\u0438\u043b])";
        this.category_death_void_messages = "\u041f\u0420\u0418 \u041f\u0410\u0414\u0415\u041d\u0418\u0418 \u0412 \u0411\u0415\u0417\u0414\u041d\u0423 (\u0442\u0443\u0442 \u043d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u0437\u0430\u043c\u0435\u043d\u044f\u0435\u0442\u0441\u044f, \u0431\u0435\u0437 \u043d\u0438\u043a\u043e\u0432)";
        this.category_bed_messages = "\u0421\u041b\u041e\u041c\u0410\u041d\u0410 \u041a\u0420\u041e\u0412\u0410\u0422\u042c (\u0437\u0430\u043c\u0435\u043d\u044f\u0442\u0441\u044f \u0441\u043b\u043e\u0432\u0430 \"\u043a\u043e\u043c\u0430\u043d\u0434\u0430\", \"\u043a\u043e\u043c\u0430\u043d\u0434\u044b\") \u041c\u043e\u0436\u0435\u0448\u044c \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c \"" + this.string_for_bed_single + "\", \u0438 \"" + this.string_for_bed_multi + "\" \u0447\u0442\u043e\u0431 \u043e\u0442\u0434\u0435\u043b\u0438\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0434\u043b\u044f \u043e\u0434\u0438\u043d\u043e\u0447\u043d\u043e\u0439 \u0438\u0433\u0440\u044b";
        this.category_bed_own_messages = "\u0421\u041b\u041e\u041c\u0410\u041d\u0410 \u0422\u0412\u041e\u042f \u041a\u0420\u041e\u0412\u0410\u0422\u042c (\u0437\u0430\u043c\u0435\u043d\u0438\u0442\u0441\u044f \u0441\u043b\u043e\u0432\u043e \"\u0438\u0433\u0440\u043e\u043a\", \u0442\u043e\u0442 \u043a\u0442\u043e \u0441\u043b\u043e\u043c\u0430\u043b \u0442\u0432\u043e\u044e \u043a\u0440\u043e\u0432\u0430\u0442\u044c)";
        this.category_wins_messages = "\u041f\u0420\u0418 \u041f\u041e\u0411\u0415\u0414\u0415";
        this.category_game_start_messages = "\u041f\u0420\u0418 \u0412\u0425\u041e\u0414\u0415 \u0412 \u0418\u0413\u0420\u0423 (\u041a\u0430\u043a \u0442\u043e\u043b\u044c\u043a\u043e \u043d\u0430\u0447\u0430\u043b\u0430\u0441\u044c \u043a\u0430\u0442\u043a\u0430)";
        this.prefix = MyChatListener.PREFIX_BEDWARS_MEOW;
        this.IS_USE_COLORS = false;
        this.IS_ACTIVE = false;
        BedwarsMeow.meowMessages = new ArrayList<MeowMsg>();
        final String readFile = FileManager.readFile(BedwarsMeow.filename);
        if (readFile == null || readFile.length() < 10) {
            this.initMeowMessages();
        }
        this.readFile();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        this.IS_ACTIVE = HintsValidator.isBedwarsMeowActive();
        this.IS_USE_COLORS = HintsValidator.isBedwarsMeowColorsActive();
    }
    
    public void readFile() {
        BedwarsMeow.meowMessages = new ArrayList<MeowMsg>();
        this.meowMessagesQueue = new ArrayList<MeowMsg>();
        String readFile = FileManager.readFile(BedwarsMeow.filename);
        if (readFile == null || readFile.length() < 10) {
            this.initMeowMessages();
            readFile = FileManager.readFile(BedwarsMeow.filename);
        }
        if (readFile == null) {
            ChatSender.addText(this.prefix + "&c\u0424\u0430\u0439\u043b \u0441 \u0442\u0435\u043a\u0441\u0442\u043e\u043c \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d! \u041f\u0440\u043e\u0432\u0435\u0440\u044c &e%appdata%\\Roaming\\.minecraft\\" + BedwarsMeow.filename);
            return;
        }
        try {
            final String[] splitters_text = { this.category_kill_messages, this.category_final_kill_messages, this.category_death_messages, this.category_death_void_messages, this.category_bed_messages, this.category_bed_own_messages, this.category_wins_messages, this.category_game_start_messages, "randomtexttoletmycodeparselastline" };
            final MsgCase[] splitters_cases = { MsgCase.KILL, MsgCase.FINAL_KILL, MsgCase.DEATH, MsgCase.DEATH_VOID, MsgCase.BED_SINGLE, MsgCase.BED_OWN, MsgCase.WIN, MsgCase.GAME_START };
            for (int i = 0; i < splitters_cases.length - 1; ++i) {
                if (!readFile.contains(splitters_text[i])) {
                    final StringBuilder append = new StringBuilder().append("\n");
                    final MyChatListener chatListener = Main.chatListener;
                    ChatSender.addText(append.append(MyChatListener.PREFIX_BEDWARS_MEOW).append("&c&l\u041e\u0448\u0438\u0431\u043a\u0430 \u0444\u0430\u0439\u043b\u0430, \u043a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u0438 \u043e\u0442\u0441\u0443\u0442\u0441\u0442\u0432\u0443\u044e\u0442! \u0412\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u0438 \u0444\u0430\u0439\u043b \u043a &l\u0437\u0430\u0432\u043e\u0434\u0441\u043a\u0438\u043c \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430\u043c &7(&e/meow&7)&c!\n").toString());
                    return;
                }
            }
            for (int i = 0; i < splitters_cases.length; ++i) {
                final String[] split;
                final String[] lines = split = readFile.split(Pattern.quote(splitters_text[i]))[1].trim().split(Pattern.quote(splitters_text[i + 1]))[0].trim().split("\n");
                for (final String line : split) {
                    Label_0590: {
                        if (!line.contains("===")) {
                            final String l = line.replace("\n", "").trim();
                            if (l.length() > 0) {
                                final MeowMsg m = new MeowMsg(splitters_cases[i], l);
                                if (m.msgcase == MsgCase.BED_SINGLE) {
                                    if (!m.text.contains(this.string_for_bed_single) && !m.text.contains(this.string_for_bed_multi)) {
                                        BedwarsMeow.meowMessages.add(new MeowMsg(MsgCase.BED_SINGLE, l));
                                        BedwarsMeow.meowMessages.add(new MeowMsg(MsgCase.BED_MULTI, l));
                                        break Label_0590;
                                    }
                                    if (m.text.contains(this.string_for_bed_single)) {
                                        m.msgcase = MsgCase.BED_SINGLE;
                                        m.text = m.text.replace(this.string_for_bed_single, "").trim();
                                    }
                                    if (m.text.contains(this.string_for_bed_multi)) {
                                        m.msgcase = MsgCase.BED_MULTI;
                                        m.text = m.text.replace(this.string_for_bed_multi, "").trim();
                                    }
                                }
                                BedwarsMeow.meowMessages.add(m);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ChatSender.addText(this.prefix + "&c\u0424\u0430\u0439\u043b \u0441 \u0442\u0435\u043a\u0441\u0442\u043e\u043c \u0441\u043e\u0434\u0435\u0440\u0436\u0438\u0442 \u043e\u0448\u0438\u0431\u043a\u0438! \u041f\u0440\u043e\u0432\u0435\u0440\u044c &e%appdata%\\Roaming\\.minecraft\\" + BedwarsMeow.filename);
        }
    }
    
    public void printMeows() {
        ChatSender.addText(this.prefix + "&7\u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430...\n");
        ChatSender.addText("&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &c\u043a\u0438\u043b\u0430&f:");
        final String start_prefix = " &8\u2022 &f";
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.KILL) {
                ChatSender.addText(start_prefix + m.text);
            }
        }
        ChatSender.addText("\n&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &e\u0444\u0438\u043d\u0430\u043b\u044c\u043d\u043e\u0433\u043e \u043a\u0438\u043b\u0430&f:");
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.FINAL_KILL) {
                ChatSender.addText(start_prefix + m.text);
            }
        }
        ChatSender.addText("\n&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &6\u0441\u043c\u0435\u0440\u0442\u0438&f:");
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.DEATH) {
                ChatSender.addText(start_prefix + m.text);
            }
        }
        ChatSender.addText("\n&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &6\u043f\u0430\u0434\u0435\u043d\u0438\u0438 \u0432 \u0431\u0435\u0437\u0434\u043d\u0443&f:");
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.DEATH_VOID) {
                ChatSender.addText(start_prefix + m.text);
            }
        }
        ChatSender.addText("\n&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &a\u043a\u0440\u043e\u0432\u0430\u0442\u0438&f:");
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.BED_SINGLE) {
                ChatSender.addText(start_prefix + m.text + " &a" + this.string_for_bed_single);
            }
            if (m.msgcase == MsgCase.BED_MULTI) {
                ChatSender.addText(start_prefix + m.text + " &c" + this.string_for_bed_multi);
            }
        }
        ChatSender.addText("\n&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &c\u0441\u043b\u043e\u043c\u0430\u043d\u043d\u043e\u0439 \u0442\u0432\u043e\u0435\u0439 \u043a\u0440\u043e\u0432\u0430\u0442\u0438&f:");
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.BED_OWN) {
                ChatSender.addText(start_prefix + m.text);
            }
        }
        ChatSender.addText("\n&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &b\u0432\u044b\u0438\u0433\u0440\u0430\u0448\u0430&f:");
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.WIN) {
                ChatSender.addText(start_prefix + m.text);
            }
        }
        ChatSender.addText("\n&f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u0440\u0438 &9\u0432\u0445\u043e\u0434\u0435 \u0432 \u0438\u0433\u0440\u0443&f:");
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == MsgCase.GAME_START) {
                ChatSender.addText(start_prefix + m.text);
            }
        }
        ChatSender.addText(this.prefix + "&e\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0441\u0447\u0438\u0442\u0430\u043d\u044b!");
    }
    
    public String getMeows(final MsgCase msgcase) {
        String s = "";
        String variable = "";
        String player_name = Minecraft.func_71410_x().field_71439_g.func_70005_c_();
        if (Main.isPropUserAdmin(player_name)) {
            player_name = "Vanya1337";
        }
        if (msgcase == MsgCase.KILL || msgcase == MsgCase.FINAL_KILL || msgcase == MsgCase.BED_OWN) {
            variable = "&e" + player_name;
        }
        else if (msgcase == MsgCase.DEATH) {
            variable = "&e" + player_name;
        }
        else if (msgcase == MsgCase.BED_SINGLE || msgcase == MsgCase.BED_MULTI) {
            final String[] colors = { "c", "e", "a", "b", "d", "9" };
            final Random rnd = new Random();
            variable = "&" + colors[rnd.nextInt(colors.length)];
        }
        else if (msgcase == MsgCase.WIN || msgcase == MsgCase.GAME_START || msgcase == MsgCase.DEATH_VOID) {
            variable = "";
        }
        for (final MeowMsg m : BedwarsMeow.meowMessages) {
            if (m.msgcase == msgcase) {
                s = s + " &8\u2022 &f" + m.getText(variable) + "\n";
            }
        }
        return s;
    }
    
    public void removeMeessagesWithBadWords() {
        this.initMeowMessages(true);
        this.readFile();
    }
    
    public void initMeowMessages() {
        this.initMeowMessages(false);
    }
    
    public void initMeowMessages(final boolean isRemoveBadWords) {
        final String[] bad_words = { "\u0441\u043e\u0441\u0438", "\u0441\u043e\u0441\u0430", "\u0441\u043e\u0441\u043d\u0443", "\u0435\u0431\u0430\u043b", "\u0442\u0440\u0430\u0445", "\u0445\u0443\u0435", "\u0435\u0431\u0430\u0442", "\u0435\u0431\u0430\u043d", "\u0441\u0443\u0447\u043a", "\u0441\u0443\u043a", "\u0445\u0443\u0439", "\u043f\u0438\u0437\u0434", "\u0431\u043b\u044f", "\u043f\u0438\u0434\u043e\u0440", "\u0435\u0431\u043b\u043e", "\u0443\u0435\u0431\u043e" };
        final String[] killMessages = { "\u0418\u0433\u0440\u043e\u043a, \u0442\u0435\u0431\u0435 \u043d\u0443\u0436\u043d\u043e \u0431\u043e\u043b\u044c\u0448\u0435 \u0442\u0440\u0435\u043d\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f!", "\u0414\u0440\u0443\u0436\u043e\u043a, \u0442\u0435\u0431\u0435 \u043d\u0430\u0434\u043e \u0442\u0440\u0435\u043d\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f", "\u041c\u0430\u043b\u044b\u0448, \u0442\u0435\u0431\u0435 \u043d\u0430\u0434\u043e \u0442\u0440\u0435\u043d\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f", "\u0418\u0433\u0440\u043e\u043a, \u0442\u044b \u043d\u0435 \u043d\u044f\u0448\u043a\u0430 <3", "\u0418\u0433\u0440\u043e\u043a, \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u0435\u0435 \u043f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0435!", "\u0418\u0433\u0440\u043e\u043a, \u043f\u043e\u043f\u0440\u043e\u0431\u0443\u0439 \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u043f\u0432\u043f \u043d\u0430\u0436\u0438\u043c\u0430\u0442\u044c \u043d\u0430 \u043c\u044b\u0448\u043a\u0443", "\u0418\u0433\u0440\u043e\u043a, \u0441\u0443\u0442\u044c \u0438\u0433\u0440\u044b \u043d\u0435 \u0443\u043c\u0438\u0440\u0430\u0442\u044c, \u0430 \u0443\u0431\u0438\u0432\u0430\u0442\u044c", "\u0421\u043f\u0430\u0441\u0438\u0431\u043e, + \u043a\u0438\u043b\u043b \u0432 \u0441\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0443", "\u041c\u0430\u043b\u044b\u0448, \u0431\u043e\u043b\u044c\u0448\u0435 \u0442\u0430\u043a \u043d\u0435 \u0434\u0435\u043b\u0430\u0439)" };
        final String[] finalKillMessages = { "0", "L", "\u041d\u0435 \u0440\u0430\u0441\u0441\u0442\u0440\u0430\u0438\u0432\u0430\u0439\u0441\u044f, \u0438\u0433\u0440\u043e\u043a, \u0432 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0438\u0439 \u0440\u0430\u0437 \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c\u0441\u044f!", "\u041d\u0435 \u0440\u0430\u0441\u0441\u0442\u0440\u0430\u0438\u0432\u0430\u0439\u0441\u044f, \u0438\u0433\u0440\u043e\u043a, \u0442\u0435\u0431\u0435 \u043f\u043e\u0432\u0435\u0437\u0435\u0442 \u0432 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0438\u0439 \u0440\u0430\u0437!", "\u041d\u0435 \u0433\u0440\u0443\u0441\u0442\u0438, \u0438\u0433\u0440\u043e\u043a, \u043f\u043e\u0442\u0440\u0435\u043d\u0438\u0440\u0443\u0439\u0441\u044f \u043d\u0435\u043c\u043d\u043e\u0433\u043e \u0438 \u0432\u0441\u0435 \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c\u0441\u044f!", "\u041d\u0435 \u0433\u0440\u0443\u0441\u0442\u0438, \u0438\u0433\u0440\u043e\u043a, \u0432 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0438\u0439 \u0440\u0430\u0437 \u0432\u0441\u0435 \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c\u0441\u044f!", "\u0418\u0433\u0440\u043e\u043a, \u043f\u043e\u0442\u0440\u0435\u043d\u0438\u0440\u0443\u0439\u0441\u044f \u0438 \u043f\u0440\u0438\u0445\u043e\u0434\u0438 \u0432 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0438\u0439 \u0440\u0430\u0437!", "\u0418\u0433\u0440\u043e\u043a, \u0443 \u0442\u0435\u0431\u044f \u0441\u043a\u043e\u0440\u043e \u0432\u0441\u0435 \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c\u0441\u044f, \u0433\u043b\u0430\u0432\u043d\u043e\u0435 \u0432\u0435\u0440\u0438\u0442\u044c \u0432 \u0441\u0435\u0431\u044f!", "\u042d\u0442\u043e \u043d\u043e\u0440\u043c\u0430\u043b\u044c\u043d\u043e, \u0438\u0433\u0440\u043e\u043a, \u043d\u0443\u0436\u043d\u043e \u0443\u043c\u0435\u0442\u044c \u043f\u0440\u0438\u043d\u0438\u043c\u0430\u0442\u044c \u043f\u043e\u0440\u0430\u0436\u0435\u043d\u0438\u0435", "\u042d\u0442\u043e\u0442 \u043f\u0440\u0438\u0446\u0435\u043b \u043f\u0440\u043e\u0441\u0442\u043e \u0438\u043c\u0431\u0430", "\u0412 \u043b\u043e\u0431\u0431\u0438, \u043c\u0430\u043b\u044b\u0448)", "\u0418\u0433\u0440\u043e\u043a, \u0442\u043e\u043b\u044c\u043a\u043e \u043d\u0435 \u043f\u043b\u0430\u0447\u044c", "\u041e\u0439-\u043e\u0439-\u043e\u0439, \u043d\u0435 \u043f\u043e\u0432\u0435\u0437\u043b\u043e \u0442\u0435\u0431\u0435, \u0438\u0433\u0440\u043e\u043a", "\u0427\u0442\u043e, \u0438\u0433\u0440\u043e\u043a, \u0431\u0435\u0437 \u043a\u0440\u043e\u0432\u0430\u0442\u043a\u0438 \u0441\u043b\u043e\u0436\u043d\u043e \u043f\u043e\u043b\u0443\u0447\u0438\u043b\u043e\u0441\u044c?", "\u0418\u0433\u0440\u043e\u043a, \u043d\u0430\u0434\u043e \u0431\u044b\u043b\u043e \u043a\u0440\u043e\u0432\u0430\u0442\u044c \u0434\u0435\u0444\u0430\u0442\u044c", "\u042f \u0433\u0435\u043d\u0438\u0439 \u044d\u0442\u043e\u0439 \u0438\u0433\u0440\u044b", "\u042f \u0441\u0435\u0439\u0447\u0430\u0441 \u0432 \u043e\u0447\u0435\u043d\u044c \u0436\u043e\u0441\u0442\u043a\u043e\u0439 \u0444\u043e\u0440\u043c\u0435!", "\u0412\u043e\u0442 \u043f\u043e\u0447\u0435\u043c\u0443 \u044f \u043b\u0443\u0447\u0448\u0438\u0439 \u0432 \u043c\u0438\u0440\u0435!", "\u0418\u0433\u0440\u043e\u043a, \u043f\u0440\u043e\u0441\u0442\u043e \u044f \u0442\u0440\u0435\u043d\u0438\u0440\u0443\u044e\u0441\u044c, \u043f\u043e\u043a\u0430 \u0432\u0441\u0435 \u043e\u0442\u0434\u044b\u0445\u0430\u044e\u0442", "\u042f \u043f\u0440\u043e\u0441\u0442\u043e \u0431\u043e\u0433 \u0432 \u044d\u0442\u043e\u0439 \u0438\u0433\u0440\u0435!", "\u041f\u043e\u0439\u043c\u0430\u043b \u043d\u0430 \u0430\u0448\u0438\u0431\u043a\u0435!", "\u0410 \u0447\u0442\u043e, \u0438\u0433\u0440\u043e\u043a, \u0431\u0435\u0437 \u043a\u0440\u043e\u0432\u0430\u0442\u043a\u0438 \u0441\u043b\u043e\u0436\u043d\u043e \u043f\u043e\u043b\u0443\u0447\u0438\u043b\u043e\u0441\u044c?" };
        final String s = " " + this.string_for_bed_single;
        final String m = " " + this.string_for_bed_multi;
        final String[] bedMessages = { "\u0424\u0443\u0443... \u041a\u0430\u043a\u0430\u044f \u043d\u0435 \u0432\u043a\u0443\u0441\u043d\u0430\u044f \u043a\u0440\u043e\u0432\u0430\u0442\u043a\u0430 \u0443 \u043a\u043e\u043c\u0430\u043d\u0434\u044b", "\u041f\u043e\u0445\u043e\u0434\u0443 \u043a\u043e\u043c\u0430\u043d\u0434\u0430 \u0441\u0447\u0430\u0441 \u043f\u0440\u043e\u0438\u0433\u0440\u0430\u0435\u0442" + s, "\u041f\u043e\u0445\u043e\u0434\u0443 \u043a\u043e\u043c\u0430\u043d\u0434\u0430 \u0441\u0447\u0430\u0441 \u043f\u0440\u043e\u0438\u0433\u0440\u0430\u044e\u0442" + m, "\u041d\u0435 \u043f\u043e\u0432\u0435\u0437\u043b\u043e \u0442\u0435\u0431\u0435, \u043a\u043e\u043c\u0430\u043d\u0434\u0430, \u043f\u043e\u043f\u0430\u0441\u0442\u044c \u0441\u043e \u043c\u043d\u043e\u0439 \u0432 \u043e\u0434\u043d\u0443 \u043a\u0430\u0442\u043a\u0443" + s, "\u041d\u0435 \u043f\u043e\u0432\u0435\u0437\u043b\u043e \u0432\u0430\u043c, \u043a\u043e\u043c\u0430\u043d\u0434\u0430, \u043f\u043e\u043f\u0430\u0441\u0442\u044c \u0441\u043e \u043c\u043d\u043e\u0439 \u0432 \u043e\u0434\u043d\u0443 \u043a\u0430\u0442\u043a\u0443" + m, "\u041a\u043e\u043c\u0430\u043d\u0434\u0430 \u0441\u043a\u043e\u0440\u043e \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u0442\u0441\u044f \u0432 \u043b\u043e\u0431\u0431\u0438)" + s, "\u041a\u043e\u043c\u0430\u043d\u0434\u0430 \u0441\u043a\u043e\u0440\u043e \u043e\u0442\u043f\u0440\u0430\u0432\u044f\u0442\u0441\u044f \u0432 \u043b\u043e\u0431\u0431\u0438)" + m, "\u041e\u0439, \u043a\u043e\u043c\u0430\u043d\u0434\u0430, \u043f\u0440\u043e\u0441\u0442\u0438" + s, "\u041e\u0439, \u043a\u043e\u043c\u0430\u043d\u0434\u0430, \u043f\u0440\u043e\u0441\u0442\u0438\u0442\u0435" + m, "\u041a\u043e\u043c\u0430\u043d\u0434\u0430 \u0442\u0435\u043f\u0435\u0440\u044c \u0431\u0435\u0437 \u043a\u0440\u043e\u0432\u0430\u0442\u043a\u0438(" + s, "\u041a\u043e\u043c\u0430\u043d\u0434\u0430 \u0442\u0435\u043f\u0435\u0440\u044c \u0431\u0435\u0437 \u043a\u0440\u043e\u0432\u0430\u0442\u043a\u0438(" + m, "\u041a\u043e\u043c\u0430\u043d\u0434\u0430, \u0442\u043e\u043b\u044c\u043a\u043e \u043d\u0435 \u043f\u043b\u0430\u0447\u044c" + s, "\u041a\u043e\u043c\u0430\u043d\u0434\u0430, \u0442\u043e\u043b\u044c\u043a\u043e \u043d\u0435 \u043f\u043b\u0430\u0447\u044c\u0442\u0435" + m };
        final String[] bedOwnMessages = { "\u0418\u0433\u0440\u043e\u043a, \u043a\u0430\u043a \u0442\u044b \u043f\u043e\u0441\u043c\u0435\u043b \u0441\u043b\u043e\u043c\u0430\u0442\u044c \u043c\u043e\u044e \u043a\u0440\u043e\u0432\u0430\u0442\u044c?!", "\u0418\u0433\u0440\u043e\u043a, \u0441\u043f\u0430\u0441\u0438\u0431\u043e, \u0442\u0435\u043f\u0435\u0440\u044c \u044f \u043d\u0435 \u0443\u0441\u043d\u0443(", "\u041a\u0430\u043a \u043c\u043d\u0435 \u0442\u0435\u043f\u0435\u0440\u044c \u0432\u043e\u0437\u0440\u043e\u0434\u0438\u0442\u044c\u0441\u044f, \u0438\u0433\u0440\u043e\u043a?", "\u0414\u0430 \u044f \u0438 \u0431\u0435\u0437 \u043a\u0440\u043e\u0432\u0430\u0442\u0438 \u0441\u043f\u0440\u0430\u0432\u043b\u044e\u0441\u044c, \u0438\u0433\u0440\u043e\u043a", "\u041d\u0415\u0415\u0415\u0422! \u041c\u043e\u044f \u043a\u0440\u043e\u0432\u0430\u0442\u043a\u0430(" };
        final String[] deathMessages = { "\u0418\u0433\u0440\u043e\u043a, \u0442\u0435\u0431\u0435 \u043f\u0440\u043e\u0441\u0442\u043e \u043f\u043e\u0432\u0435\u0437\u043b\u043e)", "\u0418\u0433\u0440\u043e\u043a, \u043d\u0430 \u043b\u0430\u043a\u0438\u0447\u0430\u0445 \u0447\u0438\u0441\u0442\u043e", "\u0418\u0433\u0440\u043e\u043a, \u0442\u043e\u043b\u044c\u043a\u043e \u0441 \u043f\u0430\u043b\u043a\u043e\u0439 \u0438 \u0443\u043c\u0435\u0435\u0448\u044c", "\u0422\u0435\u0431\u0435 \u043f\u0440\u043e\u0441\u0442\u043e \u043f\u043e\u0432\u0435\u0437\u043b\u043e, \u0438\u0433\u0440\u043e\u043a", "\u0418\u0433\u0440\u043e\u043a, \u0441 \u0447\u0438\u0442\u0430\u043c\u0438 \u043d\u043e\u0440\u043c?", "\u0423 \u043c\u0435\u043d\u044f \u0437\u0430\u043b\u0430\u0433\u0430\u043b\u043e)", "\u0413\u043e\u0442\u043e\u0432\u044c \u043f\u043e\u043f\u043a\u0443, \u0438\u0433\u0440\u043e\u043a", "\u041d\u0443, \u0438\u0433\u0440\u043e\u043a, \u0442\u044b \u0434\u043e\u0438\u0433\u0440\u0430\u043b\u0441\u044f" };
        final String[] deathVoidMessages = new String[0];
        final String[] winMessages = { "\u0423\u0440\u0430, \u043f\u043e\u0431\u0435\u0434\u0430!", "GG", "\u041d\u0435 \u043f\u043e\u0447\u0443\u0432\u0441\u0442\u0432\u043e\u0432\u0430\u043b", "\u041a\u0430\u043a \u0436\u0435 \u0431\u044b\u043b\u043e \u043b\u0435\u0433\u043a\u043e", "\u041a\u0430\u043a\u043e\u0439 \u0436\u0435 \u044d\u0442\u043e \u0441\u0435\u0440\u0432\u0435\u0440 \u0431\u043e\u0442\u0438\u043a\u043e\u0432..." };
        final String[] gameStartMessages = { "\u0420\u0435\u0431\u044f\u0442\u043a\u0438, \u0432\u0441\u0435\u043c \u0443\u0434\u0430\u0447\u043d\u043e\u0439 \u0438\u0433\u0440\u044b!", "\u0420\u0435\u0431\u044f\u0442, \u0434\u0430\u0432\u0430\u0439\u0442\u0435 \u0438\u0433\u0440\u0430\u0442\u044c \u0431\u0435\u0437 \u0447\u0438\u0442\u043e\u0432", "\u041c\u0430\u043b\u044b\u0448\u0438, \u043d\u0435 \u043e\u0431\u0437\u044b\u0432\u0430\u0439\u0442\u0435\u0441\u044c \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u0438\u0433\u0440\u044b! \u0418\u0433\u0440\u0430\u0439\u0442\u0435 \u0434\u0440\u0443\u0436\u043d\u043e \u0438 \u043c\u0438\u0440\u043d\u043e!", "\u0422\u0430\u043a, \u0431\u043e\u0442\u0438\u043a\u0438, \u043b\u0438\u0432\u043d\u0438\u0442\u0435 \u0438\u0437 \u043a\u0430\u0442\u043a\u0438!", "\u041a\u0442\u043e \u0442\u043e\u0436\u0435 \u0438\u0433\u0440\u0430\u0435\u0442 \u0441 \u0441\u0430\u043c\u044b\u043c \u0438\u043c\u0431\u043e\u0432\u044b\u043c \u043c\u043e\u0434\u043e\u043c \u0434\u043b\u044f \u0431\u0435\u0434\u0432\u0430\u0440\u0441\u0430?", "\u041a\u0442\u043e \u0442\u043e\u0436\u0435 \u0438\u0433\u0440\u0430\u0435\u0442 \u0441 \u0441\u0430\u043c\u044b\u043c \u043a\u0440\u0443\u0442\u044b\u043c \u043c\u043e\u0434\u043e\u043c \u0434\u043b\u044f \u0431\u0435\u0434\u0432\u0430\u0440\u0441\u0430?", "\u041a\u0442\u043e \u0442\u043e\u0436\u0435 \u0438\u0433\u0440\u0430\u0435\u0442 \u0441 \u0441\u0430\u043c\u044b\u043c \u043b\u0443\u0447\u0448\u0438\u043c \u043c\u043e\u0434\u043e\u043c \u0434\u043b\u044f \u0431\u0435\u0434\u0432\u0430\u0440\u0441\u0430?" };
        String str = "\u0412 \u044d\u0442\u043e\u043c \u0444\u0430\u0439\u043b\u0435 \u0442\u044b \u043c\u043e\u0436\u0435\u0448\u044c \u0440\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0438 \u0434\u043e\u0431\u0430\u0432\u043b\u044f\u0442\u044c \u0441\u0432\u043e\u0438 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f\n\u041f\u043e\u0441\u043b\u0435 \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u0439 \u0441\u043e\u0445\u0440\u0430\u043d\u0438 \u0444\u0430\u0439\u043b \u0438 \u0432 \u043c\u0430\u0439\u043d\u043a\u0440\u0430\u0444\u0442\u0435 \u043d\u0430\u043f\u0438\u0448\u0438 /meow, \u0438 \u0442\u0430\u043c \u041e\u0411\u041d\u041e\u0412\u0418\u0422\u042c \u0421\u041e\u041e\u0411\u0429\u0415\u041d\u0418\u042f\n\u0415\u0441\u043b\u0438 \u0432\u043e\u0437\u043d\u0438\u043a\u043b\u0430 \u043e\u0448\u0438\u0431\u043a\u0430, \u0438\u043b\u0438 \u0447\u0442\u043e-\u0442\u043e \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442, \u0443\u0434\u0430\u043b\u0438 \u044d\u0442\u043e\u0442 \u0444\u0430\u0439\u043b \u0438 \u043d\u0430\u0436\u043c\u0438 \u041e\u0411\u041d\u041e\u0412\u0418\u0422\u042c \u0421\u041e\u041e\u0411\u0429\u0415\u041d\u0418\u042f\n\u041d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439 \"===\" \u0438 \u043d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439 \u043f\u0435\u0440\u0435\u043d\u043e\u0441 \u0441\u0442\u0440\u043e\u043a\u0438 \u0432\u043d\u0443\u0442\u0440\u0438 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439!\n\n";
        final String[] splitters_text = { this.category_kill_messages, this.category_final_kill_messages, this.category_death_messages, this.category_death_void_messages, this.category_bed_messages, this.category_bed_own_messages, this.category_wins_messages, this.category_game_start_messages };
        final String[][] splitters_messages = { killMessages, finalKillMessages, deathMessages, deathVoidMessages, bedMessages, bedOwnMessages, winMessages, gameStartMessages };
        if (isRemoveBadWords) {
            for (final String[] messages : splitters_messages) {
                for (int i = 0; i < messages.length; ++i) {
                    boolean isOk = true;
                    for (final String bad_word : bad_words) {
                        if (messages[i].toLowerCase().contains(bad_word)) {
                            isOk = false;
                            break;
                        }
                    }
                    if (!isOk) {
                        final StringBuilder sb = new StringBuilder();
                        final MyChatListener chatListener = Main.chatListener;
                        ChatSender.addText(sb.append(MyChatListener.PREFIX_BEDWARS_MEOW).append("&f\u0423\u0434\u0430\u043b\u0435\u043d\u043e &c").append(messages[i]).toString());
                        messages[i] = "";
                    }
                }
            }
        }
        for (int j = 0; j < splitters_text.length; ++j) {
            str = str + "===" + splitters_text[j] + "===\n";
            for (final String s2 : splitters_messages[j]) {
                if (s2.length() != 0) {
                    str = str + s2 + "\n";
                }
            }
            str += "\n\n";
        }
        FileManager.initFile(BedwarsMeow.filename);
        FileManager.writeToFile(str, BedwarsMeow.filename, false);
    }
    
    public String getNextMessage(final MsgCase msgcase, final String variable) {
        if (!this.IS_ACTIVE) {
            return null;
        }
        MeowMsg msg = null;
        if (this.meowMessagesQueue == null) {
            this.meowMessagesQueue = new ArrayList<MeowMsg>();
        }
        for (final MeowMsg m : this.meowMessagesQueue) {
            if (m.msgcase == msgcase) {
                msg = m;
                this.meowMessagesQueue.remove(m);
                break;
            }
        }
        boolean areMessagesLeft = false;
        for (final MeowMsg i : this.meowMessagesQueue) {
            if (i.msgcase == msgcase) {
                areMessagesLeft = true;
                break;
            }
        }
        if (!areMessagesLeft) {
            final ArrayList<MeowMsg> arr = new ArrayList<MeowMsg>();
            for (final MeowMsg j : BedwarsMeow.meowMessages) {
                if (j.msgcase == msgcase) {
                    arr.add(j);
                }
            }
            if (arr.size() == 0) {
                return null;
            }
            Collections.shuffle(arr);
            if (msg == null) {
                msg = arr.get(0);
                arr.remove(0);
            }
            else if (arr.size() > 1) {
                while (arr.get(0).text.equals(msg.text)) {
                    Collections.shuffle(arr);
                }
            }
            this.meowMessagesQueue.addAll(arr);
        }
        return msg.getText(variable);
    }
    
    static {
        BedwarsMeow.filename = "BedwarsBro_MeowMod_2.4.txt";
        BedwarsMeow.IS_AUTO_RANDOM_ACTIVE = false;
    }
    
    public enum MsgCase
    {
        KILL, 
        FINAL_KILL, 
        DEATH, 
        DEATH_VOID, 
        BED_SINGLE, 
        BED_MULTI, 
        BED_OWN, 
        WIN, 
        GAME_START;
    }
    
    public class MeowMsg
    {
        public MsgCase msgcase;
        public String text;
        
        public MeowMsg(final MsgCase msgcase, final String text) {
            this.msgcase = msgcase;
            this.text = text;
        }
        
        public String getText(final String variable) {
            if (Main.isPropUserAdmin(variable)) {
                return null;
            }
            String new_text = "&f" + this.text;
            if (BedwarsMeow.IS_AUTO_RANDOM_ACTIVE) {
                Main.commandRainbowMessageSetter.setRandomPalitra();
            }
            if (this.msgcase == MsgCase.KILL || this.msgcase == MsgCase.FINAL_KILL || this.msgcase == MsgCase.DEATH || this.msgcase == MsgCase.BED_OWN) {
                final String killer = "\u0438\u0433\u0440\u043e\u043a";
                new_text = new_text.replace(killer, variable + "&f").replace(BedwarsMeow.this.upperCaseFirstLetter(killer), variable + "&f").trim();
            }
            else if (this.msgcase == MsgCase.BED_SINGLE || this.msgcase == MsgCase.BED_MULTI) {
                final String team_normal = "\u043a\u043e\u043c\u0430\u043d\u0434\u0430";
                final String team_edited = "\u043a\u043e\u043c\u0430\u043d\u0434\u044b";
                final CustomScoreboard.TEAM_COLOR team_color = CustomScoreboard.getTeamColorByCode(variable);
                String replace_normal = "\u0431\u043e\u0442\u0438\u043a\u0438";
                String replace_edited = "\u0431\u043e\u0442\u0438\u043a\u043e\u0432";
                String replace_normal_single = "\u0431\u043e\u0442\u0438\u043a";
                String replace_edited_single = "\u0431\u043e\u0442\u0438\u043a\u0430";
                final String color_code = "&" + CustomScoreboard.getCodeByTeamColor(team_color);
                if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
                    replace_normal = "\u043a\u0440\u0430\u0441\u043d\u044b\u0435";
                    replace_edited = "\u043a\u0440\u0430\u0441\u043d\u044b\u0445";
                    replace_normal_single = "\u043a\u0440\u0430\u0441\u043d\u044b\u0439";
                    replace_edited_single = "\u043a\u0440\u0430\u0441\u043d\u043e\u0433\u043e";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
                    replace_normal = "\u0436\u0435\u043b\u0442\u044b\u0435";
                    replace_edited = "\u0436\u0435\u043b\u0442\u044b\u0445";
                    replace_normal_single = "\u0436\u0435\u043b\u0442\u044b\u0439";
                    replace_edited_single = "\u0436\u0435\u043b\u0442\u043e\u0433\u043e";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
                    replace_normal = "\u0437\u0435\u043b\u0435\u043d\u044b\u0435";
                    replace_edited = "\u0437\u0435\u043b\u0435\u043d\u044b\u0445";
                    replace_normal_single = "\u0437\u0435\u043b\u0435\u043d\u044b\u0439";
                    replace_edited_single = "\u0437\u0435\u043b\u0435\u043d\u043e\u0433\u043e";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
                    replace_normal = "\u0433\u043e\u043b\u0443\u0431\u044b\u0435";
                    replace_edited = "\u0433\u043e\u043b\u0443\u0431\u044b\u0445";
                    replace_normal_single = "\u0433\u043e\u043b\u0443\u0431\u043e\u0439";
                    replace_edited_single = "\u0433\u043e\u043b\u0443\u0431\u043e\u0433\u043e";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
                    replace_normal = "\u0441\u0438\u043d\u0438\u0435";
                    replace_edited = "\u0441\u0438\u043d\u0438\u0445";
                    replace_normal_single = "\u0441\u0438\u043d\u0438\u0439";
                    replace_edited_single = "\u0441\u0438\u043d\u0435\u0433\u043e";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
                    replace_normal = "\u0440\u043e\u0437\u043e\u0432\u044b\u0435";
                    replace_edited = "\u0440\u043e\u0437\u043e\u0432\u044b\u0445";
                    replace_normal_single = "\u0440\u043e\u0437\u043e\u0432\u044b\u0439";
                    replace_edited_single = "\u0440\u043e\u0437\u043e\u0432\u043e\u0433\u043e";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
                    replace_normal = "\u0441\u0435\u0440\u044b\u0435";
                    replace_edited = "\u0441\u0435\u0440\u044b\u0445";
                    replace_normal_single = "\u0441\u0435\u0440\u044b\u0439";
                    replace_edited_single = "\u0441\u0435\u0440\u043e\u0433\u043e";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
                    replace_normal = "\u0431\u0435\u043b\u044b\u0435";
                    replace_edited = "\u0431\u0435\u043b\u044b\u0445";
                    replace_normal_single = "\u0431\u0435\u043b\u044b\u0439";
                    replace_edited_single = "\u0431\u0435\u043b\u043e\u0433\u043e";
                }
                if (this.msgcase == MsgCase.BED_SINGLE) {
                    replace_normal = replace_normal_single;
                    replace_edited = replace_edited_single;
                }
                new_text = new_text.replace(team_normal, color_code + replace_normal + "&f").replace(BedwarsMeow.this.upperCaseFirstLetter(team_normal), color_code + BedwarsMeow.this.upperCaseFirstLetter(replace_normal) + "&f").replace(team_edited, color_code + replace_edited + "&f").replace(BedwarsMeow.this.upperCaseFirstLetter(team_edited), color_code + BedwarsMeow.this.upperCaseFirstLetter(replace_edited) + "&f");
            }
            else {
                if (this.msgcase != MsgCase.WIN && this.msgcase != MsgCase.GAME_START && this.msgcase != MsgCase.DEATH_VOID) {
                    return null;
                }
                new_text = this.text;
            }
            new_text = "&f" + new_text;
            if (BedwarsMeow.this.IS_USE_COLORS) {
                final String rainbow_text = CommandRainbowMessage.generateRainbowMessage(ColorCodesManager.removeColorCodes(new_text), 1, -1);
                if (rainbow_text != null) {
                    new_text = rainbow_text;
                }
            }
            else {
                new_text = ColorCodesManager.removeColorCodes(new_text);
            }
            if (new_text.length() >= 100) {
                new_text = new_text.substring(0, 100);
            }
            return new_text;
        }
    }
}
