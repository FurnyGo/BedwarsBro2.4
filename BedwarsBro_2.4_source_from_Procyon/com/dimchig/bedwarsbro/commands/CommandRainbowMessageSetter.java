// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.client.Minecraft;
import java.util.regex.Pattern;
import com.dimchig.bedwarsbro.hints.BedwarsMeow;
import com.dimchig.bedwarsbro.ChatSender;
import java.util.Iterator;
import com.dimchig.bedwarsbro.Main;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandRainbowMessageSetter extends CommandBase
{
    public static String command_text;
    String randomFilterString;
    
    public CommandRainbowMessageSetter() {
        this.randomFilterString = "lno";
        CommandRainbowMessageSetter.command_text = CommandRainbowMessageSetter.command_text.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandRainbowMessageSetter.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Makes message rainbow setter";
    }
    
    public String setRandomPalitra() {
        final String[] available_colors = { "2", "3", "5", "6", "9", "a", "b", "c", "d", "e", "f" };
        final Random rnd = new Random();
        final int min_size = 2;
        final int max_size = 5;
        final int count = Math.min(min_size + rnd.nextInt(max_size - min_size + 1), max_size);
        final ArrayList<String> generated_colors = new ArrayList<String>();
        for (int i = 0; i < count; ++i) {
            String rnd_color = "0";
            do {
                rnd_color = available_colors[rnd.nextInt(available_colors.length)];
            } while (generated_colors.contains(rnd_color));
            generated_colors.add(rnd_color);
        }
        String output_str = "";
        for (final String s : generated_colors) {
            output_str += s;
        }
        output_str += "+l";
        for (final String s2 : this.randomFilterString.split("")) {
            if (rnd.nextBoolean() && !output_str.contains(s2)) {
                output_str += s2;
            }
        }
        Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS.text).set(output_str);
        return output_str;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final String current_palitra = Main.getConfigString(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS);
        final String tutorial_colorcodes = "&f\u0426\u0432\u0435\u0442\u0430:\n &11 &22 &33 &44 &55 &66 &77 &88 &99 &aa &bb &cc &dd &ee &ff \n&f\u0424\u043e\u0440\u043c\u0430\u0442\u0438\u0440\u0432\u043e\u043a\u0430:\n &fl &7- &f&l\u0436\u0438\u0440\u043d\u044b\u0439&7\n &fn&7 - &f&n\u043f\u043e\u0434\u0447\u0435\u0440\u043a\u043d\u0443\u0442\u044b\u0439&7\n &fo &7- &f&o\u043a\u0443\u0440\u0441\u0438\u0432\n &fm &7- &f&m\u0437\u0430\u0447\u0435\u0440\u043a\u043d\u0443\u0442\u044b\u0439&7\n&e&l&n&o\u041c\u043e\u0436\u043d\u043e \u0441\u043e\u0432\u043c\u0435\u0449\u0430\u0442\u044c&7 (e+lno)";
        if (args.length <= 0 || args[0].equals("help")) {
            ChatSender.addText("&a\u0422\u0435\u043a\u0443\u0449\u0430\u044f\u044f \u043f\u0430\u043b\u0438\u0442\u0440\u0430: &f" + current_palitra);
            ChatSender.addText("&e/rs &b\u043f\u0430\u043b\u0438\u0442\u0440\u0430 &7- &f\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u043f\u0430\u043b\u0438\u0442\u0440\u0443 &7(&e/rs &bade+ln&7)");
            ChatSender.addText("&e/rs rainbow &7- &c\u0440&6\u0430&e\u0434&a\u0443&b\u0433&d\u0430");
            ChatSender.addText("&e/rs add &a\u043a\u043e\u0434 &7- &f\u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0447\u0442\u043e-\u0442\u043e");
            ChatSender.addText("&e/rs remove &c\u043a\u043e\u0434 &7- &f\u0443\u0431\u0440\u0430\u0442\u044c \u0441\u0438\u043c\u0432\u043e\u043b\u044b \u0438\u0437 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u044e\u0449\u0435\u0433\u043e");
            ChatSender.addText("&e/rs mirror &7- &f\u043e\u0442\u0437\u0435\u0440\u043a\u0430\u043b\u0438\u0442\u044c \u043f\u0430\u043b\u0438\u0442\u0440\u0443");
            ChatSender.addText("&e/rs team &7- &f\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0446\u0432\u0435\u0442 \u043f\u043e\u0434 \u043a\u043e\u043c\u0430\u043d\u0434\u0443");
            ChatSender.addHoverText("&e/rs mode &7[&b0&7/&b1&7] &7- &f\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0440\u0435\u0436\u0438\u043c&c*", "&e0 &7- &f\u0430\u0434\u0430\u043f\u0442\u0438\u0432\u043d\u0430\u044f \u0434\u043b\u0438\u043d\u0430\n&e1 &7- &f\u0446\u0432\u0435\u0442\u0430 \u043c\u0435\u043d\u044f\u044e\u0442\u0441\u044f \u0447\u0435\u0440\u0435\u0437 \u043a\u0430\u0436\u0434\u043e\u0435 \u0441\u043b\u043e\u0432\u043e");
            ChatSender.addText("&e/rs global &7[&bon&7/&boff&7]&7- &f\u043f\u0438\u0441\u0430\u0442\u044c \u0432\u0441\u0435\u0433\u0434\u0430 \u0432 \u0433\u043b\u043e\u0431\u0430\u043b\u044c\u043d\u044b\u0439 \u0447\u0430\u0442 &7(\u0432 \u043b\u043e\u0431\u0431\u0438 \u043d\u0435 \u0431\u0443\u0434\u0435\u0442 \"!\"");
            ChatSender.addText("&e/rs random &7- &f\u0440\u0430\u043d\u0434\u043e\u043c\u043d\u0430\u044f \u043f\u0430\u043b\u0438\u0442\u0440\u0430");
            ChatSender.addText("&e/rs randomstyle &7[lnom&7] &7- &f\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0434\u043e\u0441\u0442\u0443\u043f\u043d\u044b\u0435 \u0440\u0430\u043d\u0434\u043e\u043c\u043d\u044b\u0435 \u0444\u043e\u0440\u043c\u0430\u0442\u0438\u0440\u043e\u0432\u043a\u0438");
            ChatSender.addText("&e/rs autorandom &7[&bon&7/&boff&7]&7- &f\u043f\u043e\u0441\u043b\u0435 \u043a\u0430\u0436\u0434\u043e\u0433\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0441\u0442\u0430\u0432\u0438\u0442\u044c \u0440\u0430\u043d\u0434\u043e\u043c\u043d\u0443\u044e \u043f\u0430\u043b\u0438\u0442\u0440\u0443");
            ChatSender.addHoverText("&a\u041d\u0430\u0432\u0435\u0434\u0438 &a&n\u0441\u044e\u0434\u0430&a, \u0447\u0442\u043e\u0431 \u0443\u0432\u0438\u0434\u0435\u0442\u044c \u0432\u0441\u0435 \u0446\u0432\u0435\u0442-\u043a\u043e\u0434\u044b", tutorial_colorcodes);
            return;
        }
        final String[] available_colors_to_set = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "+" };
        String output_str = "f";
        final String arg0 = args[0];
        final BedwarsMeow bedwarsMeow = Main.bedwarsMeow;
        BedwarsMeow.IS_AUTO_RANDOM_ACTIVE = false;
        Label_1334: {
            if (arg0.equals("random")) {
                output_str = this.setRandomPalitra();
            }
            else {
                if (arg0.equals("randomstyle") && args.length == 2) {
                    this.randomFilterString = args[1];
                    ChatSender.addText("\u0421\u0442\u0438\u043b\u0438 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u044b!");
                    return;
                }
                if (arg0.equals("rainbow")) {
                    output_str = "c6eabd+l";
                }
                else if (arg0.equals("add") && args.length == 2) {
                    output_str = current_palitra;
                    for (int i = 0; i < args[1].length(); ++i) {
                        final String s = "" + args[1].charAt(i);
                        if (s.equals("l") || s.equals("n") || s.equals("o") || s.equals("m")) {
                            if (!output_str.contains("+")) {
                                output_str += "+";
                            }
                            output_str += s;
                        }
                        else if (output_str.contains("+")) {
                            output_str = output_str.split(Pattern.quote("+"))[0] + s + "+" + output_str.split(Pattern.quote("+"))[1];
                        }
                        else {
                            output_str += s;
                        }
                    }
                }
                else if (arg0.equals("remove") && args.length == 2) {
                    output_str = current_palitra.replace(args[1], "");
                    if (output_str.endsWith("+")) {
                        output_str = output_str.substring(0, output_str.length() - 1);
                    }
                }
                else if (arg0.equals("mirror") && args.length == 1) {
                    final String[] split = current_palitra.split(Pattern.quote("+"));
                    final String colors = split[0];
                    final String reverse = new StringBuilder(colors).reverse().toString();
                    output_str = colors + reverse;
                    if (split.length > 1) {
                        output_str = output_str + "+" + split[1];
                    }
                }
                else {
                    if (arg0.equals("global") && args.length == 2) {
                        output_str = current_palitra;
                        boolean b = false;
                        if (args[1].equals("on")) {
                            b = true;
                        }
                        Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_GLOBAL.text).set(b);
                        ChatSender.addText("\u041f\u0438\u0441\u0430\u0442\u044c \u0432 \u0433\u043b\u043e\u0431\u0430\u043b\u044c\u043d\u044b\u0439 \u0447\u0430\u0442 - " + (b ? "&a\u0432\u043a\u043b\u044e\u0447\u0435\u043d\u043e" : "&c\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d\u043e"));
                        return;
                    }
                    if (arg0.equals("autorandom") && args.length == 2) {
                        output_str = current_palitra;
                        boolean b = false;
                        if (args[1].equals("on")) {
                            b = true;
                        }
                        final BedwarsMeow bedwarsMeow2 = Main.bedwarsMeow;
                        BedwarsMeow.IS_AUTO_RANDOM_ACTIVE = b;
                        ChatSender.addText("\u0420\u0430\u043d\u0434\u043e\u043c\u043d\u0430\u044f \u043f\u0430\u043b\u0438\u0442\u0440\u0430 - " + (b ? "&a\u0432\u043a\u043b\u044e\u0447\u0435\u043d\u0430" : "&c\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d\u0430"));
                        return;
                    }
                    if (arg0.equals("mode")) {
                        output_str = current_palitra;
                        try {
                            final int x = Integer.parseInt(args[1]);
                            if (x != 0 && x != 1) {
                                ChatSender.addText("&c\u0421\u0442\u0430\u0432\u044c \u0442\u043e\u043b\u044c\u043a\u043e 1 \u0438\u043b\u0438 0!");
                                return;
                            }
                            Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_MODE.text).set(x);
                            break Label_1334;
                        }
                        catch (Exception ex) {
                            ChatSender.addText("&c\u0421\u0442\u0430\u0432\u044c \u0442\u043e\u043b\u044c\u043a\u043e 1 \u0438\u043b\u0438 0!");
                            return;
                        }
                    }
                    if (arg0.equals("team")) {
                        try {
                            final MyChatListener chatListener = Main.chatListener;
                            final CustomScoreboard.TEAM_COLOR tc = MyChatListener.getEntityTeamColorByTeam(Minecraft.func_71410_x().field_71439_g.func_96124_cp().func_96661_b());
                            if (tc == CustomScoreboard.TEAM_COLOR.RED) {
                                output_str = "c";
                            }
                            else if (tc == CustomScoreboard.TEAM_COLOR.YELLOW) {
                                output_str = "e6";
                            }
                            else if (tc == CustomScoreboard.TEAM_COLOR.GREEN) {
                                output_str = "a";
                            }
                            else if (tc == CustomScoreboard.TEAM_COLOR.AQUA) {
                                output_str = "b";
                            }
                            else if (tc == CustomScoreboard.TEAM_COLOR.BLUE) {
                                output_str = "b";
                            }
                            else if (tc == CustomScoreboard.TEAM_COLOR.PINK) {
                                output_str = "d";
                            }
                            else if (tc == CustomScoreboard.TEAM_COLOR.GRAY) {
                                output_str = "f7";
                            }
                            else {
                                if (tc != CustomScoreboard.TEAM_COLOR.WHITE) {
                                    ChatSender.addText("&c\u041a\u043e\u043c\u0430\u043d\u0434\u0430 \u043d\u0435 \u0440\u0430\u0437\u043f\u043e\u0437\u043d\u0430\u043d\u0430!");
                                    return;
                                }
                                output_str = "f";
                            }
                            output_str += "+l";
                            break Label_1334;
                        }
                        catch (Exception e) {
                            ChatSender.addText("&c\u041a\u043e\u043c\u0430\u043d\u0434\u0430 \u043d\u0435 \u0440\u0430\u0437\u043f\u043e\u0437\u043d\u0430\u043d\u0430!");
                            return;
                        }
                    }
                    if (arg0.length() > 0) {
                        final String new_str = arg0;
                        for (int j = 0; j < new_str.length(); ++j) {
                            boolean contains = false;
                            for (int k = 0; k < available_colors_to_set.length; ++k) {
                                if (available_colors_to_set[k].equals("" + new_str.charAt(j))) {
                                    contains = true;
                                    break;
                                }
                            }
                            if (!contains) {
                                ChatSender.addText("&c\u041a\u043e\u0434 \"&e" + new_str.charAt(j) + "&c\" \u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442!\n" + tutorial_colorcodes);
                                return;
                            }
                        }
                        output_str = arg0;
                    }
                }
            }
        }
        ChatSender.addText("&7\u0421\u0433\u0435\u043d\u0435\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u043d\u0430\u044f \u0441\u0442\u0440\u043e\u043a\u0430: &f" + output_str + "&7, \u043f\u0440\u0438\u043c\u0435\u0440\u044b:");
        Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS.text).set(output_str);
        final CommandRainbowMessage commandRainbowMessage = Main.commandRainbowMessage;
        String msg = CommandRainbowMessage.generateRainbowMessage("\u0411\u0440\u043e, \u0442\u0435\u0431\u0435 \u043d\u0430\u0434\u043e \u0431\u043e\u043b\u044c\u0448\u0435 \u0442\u0440\u0435\u043d\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f!", 1, -1);
        if (msg != null) {
            ChatSender.addText(msg);
        }
        final CommandRainbowMessage commandRainbowMessage2 = Main.commandRainbowMessage;
        msg = CommandRainbowMessage.generateRainbowMessage("\u0423\u043d\u0438\u0437\u0438\u043b \u043b\u043e\u0445\u0430", 1, -1);
        if (msg != null) {
            ChatSender.addText(msg);
        }
        Main.saveConfig();
    }
    
    static {
        CommandRainbowMessageSetter.command_text = "rs";
    }
}
