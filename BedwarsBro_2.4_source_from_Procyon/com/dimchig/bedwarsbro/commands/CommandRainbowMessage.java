// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import java.util.Iterator;
import com.google.common.base.Splitter;
import java.util.regex.Pattern;
import net.minecraft.command.CommandException;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandRainbowMessage extends CommandBase
{
    public static String command_text;
    public static String[] color_codes;
    public static int MAX_MESSAGE_LENGTH;
    
    public CommandRainbowMessage(final String command) {
        CommandRainbowMessage.command_text = command.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandRainbowMessage.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Makes message rainbow";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0) {
            ChatSender.addText("\u041d\u0430\u043f\u0438\u0448\u0438 &e/r &a\u0442\u0435\u043a\u0441\u0442");
            return;
        }
        String str = "";
        for (final String s : args) {
            str = str + s + " ";
        }
        str = str.trim();
        if (str.length() <= 0) {
            return;
        }
        boolean isGlobal = Main.getConfigBool(Main.CONFIG_MSG.RAINBOW_MESSAGE_GLOBAL);
        if (str.startsWith("!")) {
            isGlobal = true;
            str = str.substring(1, str.length());
        }
        final int text_mode = Main.getConfigInt(Main.CONFIG_MSG.RAINBOW_MESSAGE_MODE);
        final String[] config_replace_character = Main.getConfigString(Main.CONFIG_MSG.RAINBOW_MESSAGE_REPLACE_CHARS).split("=");
        if (config_replace_character.length == 2) {
            str = str.replace(config_replace_character[0], config_replace_character[1]);
        }
        final String generated_message = generateRainbowMessage(str, 1, text_mode);
        if (generated_message == null) {
            ChatSender.addText("&c\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u0431\u043e\u043b\u044c\u0448\u043e\u0435!");
            return;
        }
        str = "" + generated_message;
        if (Main.shopManager.findItemInHotbar("\u044b\u0431\u043e\u0440 \u043b\u043e\u0431\u0431\u0438") == -1 && isGlobal) {
            str = "!" + str;
        }
        Minecraft.func_71410_x().field_71439_g.func_71165_d(str);
        System.out.println(str);
    }
    
    private static void readConfigParams() {
        final String color_codes_str = Main.getConfigString(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS);
        CommandRainbowMessage.color_codes = color_codes_str.split(",");
        Label_0130: {
            if (color_codes_str.contains("+")) {
                try {
                    final String colors = color_codes_str.split(Pattern.quote("+"))[0];
                    final String additional = color_codes_str.split(Pattern.quote("+"))[1];
                    CommandRainbowMessage.color_codes = new String[colors.length()];
                    for (int i = 0; i < colors.length(); ++i) {
                        CommandRainbowMessage.color_codes[i] = colors.charAt(i) + additional;
                    }
                    break Label_0130;
                }
                catch (Exception ex) {
                    ChatSender.addText("&c\u041e\u0448\u0438\u0431\u043a\u0430 \u0446\u0432\u0435\u0442\u043e\u0432 \u0432 \u043a\u043e\u043d\u0444\u0438\u0433\u0435! &7(&e/bwbro&7)");
                    return;
                }
            }
            if (!color_codes_str.contains(",")) {
                CommandRainbowMessage.color_codes = color_codes_str.split("");
            }
        }
        if (color_codes_str.length() == 0 || CommandRainbowMessage.color_codes.length <= 0) {
            ChatSender.addText("&c\u0414\u043e\u0431\u0430\u0432\u044c \u0446\u0432\u0435\u0442\u0430 \u0432 \u043a\u043e\u043d\u0444\u0438\u0433\u0435! &7(&e/bwbro&7)");
        }
    }
    
    public static String generateRainbowMessage(String s, final int step, int text_mode) {
        readConfigParams();
        if (step > CommandRainbowMessage.MAX_MESSAGE_LENGTH) {
            return null;
        }
        if (text_mode == -1) {
            text_mode = Main.getConfigInt(Main.CONFIG_MSG.RAINBOW_MESSAGE_MODE);
        }
        s = s.replace("\u0456", "\u044b");
        String str = "";
        Iterable<String> chunks = (Iterable<String>)Splitter.fixedLength(step).split((CharSequence)s);
        if (text_mode == 1) {
            chunks = (Iterable<String>)Splitter.on(" ").split((CharSequence)s);
        }
        int code_idx = -1;
        for (String chunk : chunks) {
            final String i_chunk = chunk;
            if (text_mode == 1) {
                str += "&0 ";
            }
            code_idx = (code_idx + 1) % CommandRainbowMessage.color_codes.length;
            String this_chunk_code = "";
            for (final String c : CommandRainbowMessage.color_codes[code_idx].split("")) {
                this_chunk_code = this_chunk_code + "&" + c;
            }
            if (this_chunk_code.contains("n") && step == 1 && chunk.contains(" ")) {
                chunk = chunk.replace(" ", "&0 " + this_chunk_code);
                code_idx = (code_idx - 1) % CommandRainbowMessage.color_codes.length;
            }
            str = str + this_chunk_code + chunk;
        }
        if (CommandRainbowMessage.color_codes.length > 0) {
            str = str + "&" + CommandRainbowMessage.color_codes[0].charAt(0);
        }
        str = str.trim();
        if (str.length() > CommandRainbowMessage.MAX_MESSAGE_LENGTH) {
            return generateRainbowMessage(s, step + 1, text_mode);
        }
        return str;
    }
    
    static {
        CommandRainbowMessage.command_text = "r";
        CommandRainbowMessage.color_codes = new String[] { "c", "6", "e", "a", "b", "9", "d" };
        CommandRainbowMessage.MAX_MESSAGE_LENGTH = 100;
    }
}
