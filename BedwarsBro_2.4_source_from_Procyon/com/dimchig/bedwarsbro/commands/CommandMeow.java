// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import java.io.IOException;
import com.dimchig.bedwarsbro.hints.BedwarsMeow;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.command.ICommandSender;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.command.CommandBase;

public class CommandMeow extends CommandBase
{
    public static String command_text;
    Main main_instance;
    
    public CommandMeow(final Main main) {
        CommandMeow.command_text = CommandMeow.command_text.replace("/", "");
        this.main_instance = main;
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandMeow.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Meow mod";
    }
    
    void printHelpInfo() {
        final String prefix = MyChatListener.PREFIX_BEDWARS_MEOW;
        ChatSender.addText("");
        ChatSender.addText(prefix + "&f\u041f\u043e\u043c\u043e\u0449\u044c \u043f\u043e \u043c\u043e\u0434\u0443 &f(\u041d\u0430\u0436\u0438\u043c\u0430\u0439 \u043d\u0430 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f)&f:");
        try {
            final String path2file = Minecraft.func_71410_x().field_71412_D.getCanonicalPath() + "\\" + BedwarsMeow.filename;
            try {
                ChatSender.addHoverFileText("&7\u2022 &e\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c/\u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u0441\u0432\u043e\u0438 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f", "&e\u041d\u0430\u0436\u043c\u0438&f, \u0447\u0442\u043e\u0431 \u043e\u0442\u043a\u0440\u044b\u0442\u044c &8" + path2file, path2file);
            }
            catch (IOException e) {
                ChatSender.addClickSuggestAndHoverText("&7\u2022 &e\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c/\u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u0441\u0432\u043e\u0438 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f", "&e\u041d\u0430\u0436\u043c\u0438&f, \u0447\u0442\u043e\u0431 \u0441\u043a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043f\u0443\u0442\u044c", path2file);
            }
        }
        catch (Exception e2) {
            ChatSender.addText("&7\u2022 &c\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043d\u0430\u0439\u0442\u0438 \u0444\u0430\u0439\u043b \u0441 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f\u043c\u0438");
        }
        ChatSender.addClickText("&7\u2022 &b\u041e\u0431\u043d\u043e\u0432\u0438\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0438\u0437 \u0444\u0430\u0439\u043b\u0430", "/" + CommandMeow.command_text + " update");
        ChatSender.addHoverText("&7\u2022 &c\u0412\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0444\u0430\u0439\u043b \u043a \u0437\u0430\u0432\u043e\u0434\u0441\u043a\u0438\u043c", "\u0421\u0431\u0440\u043e\u0441\u0438\u0442 \u0444\u0430\u0439\u043b \u043a\u0430\u043a \u043f\u0440\u0438 \u0443\u0441\u0442\u043e\u043d\u043e\u0432\u043a\u0435 \u043c\u043e\u0434\u0430\n&f\u041d\u0430\u043f\u0438\u0448\u0438 \u0441\u0430\u043c \u0432 \u0447\u0430\u0442 \u044d\u0442\u0443 \u043a\u043e\u043c\u0430\u043d\u0434\u0443:\n\n&c/" + CommandMeow.command_text + " reset");
        ChatSender.addHoverText("&7\u2022 &6\u0423\u0431\u0440\u0430\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0441 \u043c\u0430\u0442\u0430\u043c\u0438", "\u0423\u0431\u0435\u0440\u0435\u0442 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0441 \u043f\u043b\u043e\u0445\u0438\u043c\u0438 \u0441\u043b\u043e\u0432\u0430\u043c\u0438\n&f\u041d\u0430\u043f\u0438\u0448\u0438 \u0441\u0430\u043c \u0432 \u0447\u0430\u0442 \u044d\u0442\u0443 \u043a\u043e\u043c\u0430\u043d\u0434\u0443:\n\n&6/" + CommandMeow.command_text + " remove_bad_words");
        ChatSender.addText("");
        final String starting = "/" + CommandMeow.command_text + " print ";
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &c\u043a\u0438\u043b\u0430", starting + "kill");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &e\u0444\u0438\u043d\u0430\u043b\u044c\u043d\u043e\u0433\u043e \u043a\u0438\u043b\u0430", starting + "final_kill");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &6\u0441\u043c\u0435\u0440\u0442\u0438", starting + "death");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &c\u043f\u0430\u0434\u0435\u043d\u0438\u0438 \u0432 \u0431\u0435\u0437\u0434\u043d\u0443", starting + "death_void");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &a\u043a\u0440\u043e\u0432\u0430\u0442\u0438 &7(\u043e\u0434\u0438\u043d\u043e\u0447\u043d\u044b\u0435)", starting + "bed_single");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &a\u043a\u0440\u043e\u0432\u0430\u0442\u0438 &7(\u043a\u043e\u043c\u0430\u043d\u0434\u043d\u044b\u0435)", starting + "bed_multi");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &a\u043a\u0440\u043e\u0432\u0430\u0442\u0438 &7(\u0442\u0435\u0431\u0435 \u0441\u043b\u043e\u043c\u0430\u043b\u0438)", starting + "bed_own");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0432 &b\u043d\u0430\u0447\u0430\u043b\u0435 \u0438\u0433\u0440\u044b", starting + "game_start");
        ChatSender.addClickText("&7\u2022 &f\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u0440\u0438 &d\u043f\u043e\u0431\u0435\u0434\u0435", starting + "win");
        ChatSender.addText("");
        final boolean is_mod_active = Main.getConfigBool(Main.CONFIG_MSG.BEDWARS_MEOW);
        String s_toggle_mod = "&c\u0412\u044b\u043a\u043b\u044e\u0447\u0435\u043d";
        String s_toggle_mod_hover = "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 &a\u0432\u043a\u043b\u044e\u0447\u0438\u0442\u044c";
        if (is_mod_active) {
            s_toggle_mod = "&a\u0412\u043a\u043b\u044e\u0447\u0435\u043d";
            s_toggle_mod_hover = "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 &c\u0432\u044b\u043a\u043b\u044e\u0447\u0438\u0442\u044c";
        }
        String s_toggle_mod_colors = "&c\u041d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u044e\u0442\u0441\u044f";
        String s_toggle_mod_colors_hover = "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 &a\u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c &f\u0446\u0432\u0435\u0442\u043e\u0432\u0443\u044e \u043f\u0430\u043b\u0438\u0442\u0440\u0443 &e/rs";
        if (Main.getConfigBool(Main.CONFIG_MSG.BEDWARS_MEOW_WITH_COLORS)) {
            s_toggle_mod_colors = "&a\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u044e\u0442\u0441\u044f";
            s_toggle_mod_colors_hover = "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 &c\u043d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c &f\u0446\u0432\u0435\u0442\u043e\u0432\u0443\u044e \u043f\u0430\u043b\u0438\u0442\u0440\u0443 &e/rs";
        }
        ChatSender.addClickAndHoverText("&7\u2022 &f\u041c\u043e\u0434 &8\u25b8 " + s_toggle_mod, s_toggle_mod_hover, "/" + CommandMeow.command_text + " toggle");
        if (is_mod_active) {
            ChatSender.addClickAndHoverText("&7\u2022 &f\u0426\u0432\u0435\u0442\u0430 &8\u25b8 " + s_toggle_mod_colors + " &7(\u0442\u043e\u043b\u044c\u043a\u043e \u0434\u043b\u044f &c\u0434&e\u043e&a\u043d&b\u0430&9\u0442&d\u0435&c\u0440&6\u043e&e\u0432&f, \u043d\u0430\u0441\u0442\u0440\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u043e\u0439 &e/rs&7)", s_toggle_mod_colors_hover, "/" + CommandMeow.command_text + " toggleColors");
        }
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final String prefix = MyChatListener.PREFIX_BEDWARS_MEOW;
        if (args.length == 0) {
            this.printHelpInfo();
            return;
        }
        if (args.length == 2 && args[0].trim().equals("print")) {
            final String c = args[1].trim();
            Main.bedwarsMeow.IS_USE_COLORS = true;
            if (c.equals("kill")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &b\u041a\u0418\u041b\u0410:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.KILL));
            }
            else if (c.equals("final_kill")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &b\u0424\u0418\u041d\u0410\u041b\u042c\u041d\u041e\u0413\u041e \u041a\u0418\u041b\u0410:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.FINAL_KILL));
            }
            else if (c.equals("death")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &b\u0421\u041c\u0415\u0420\u0422\u0418:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.DEATH));
            }
            else if (c.equals("death_void")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &b\u041f\u0410\u0414\u0415\u041d\u0418\u042f \u0412 \u0411\u0415\u0417\u0414\u041d\u0423:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.DEATH_VOID));
            }
            else if (c.equals("bed_single")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u0440\u043e &b\u041a\u0420\u041e\u0412\u0410\u0422\u042c &e(\u043e\u0434\u0438\u043d\u043e\u0447\u043d\u044b\u0439 \u0440\u0435\u0436\u0438\u043c):&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.BED_SINGLE));
            }
            else if (c.equals("bed_multi")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u0440\u043e &b\u041a\u0420\u041e\u0412\u0410\u0422\u042c &e(\u043a\u043e\u043c\u0430\u043d\u0434\u043d\u044b\u0439 \u0440\u0435\u0436\u0438\u043c):&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.BED_MULTI));
            }
            else if (c.equals("bed_own")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u0440\u043e &b\u041a\u0420\u041e\u0412\u0410\u0422\u042c &e(\u0442\u0435\u0431\u0435 \u0441\u043b\u043e\u043c\u0430\u043b\u0438):&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.BED_OWN));
            }
            else if (c.equals("win")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043f\u043e\u0441\u043b\u0435 &b\u0412\u042b\u0419\u0413\u0420\u0410\u0428\u0410:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.WIN));
            }
            else if (c.equals("game_start")) {
                ChatSender.addText(prefix + "\u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u0432 &b\u041d\u0410\u0427\u0410\u041b\u0415 \u0418\u0413\u0420\u042b:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.GAME_START));
            }
            Main.bedwarsMeow.updateBooleans();
        }
        else if (args[0].trim().equals("update")) {
            Main.bedwarsMeow.readFile();
            final BedwarsMeow bedwarsMeow = Main.bedwarsMeow;
            if (BedwarsMeow.meowMessages != null) {
                final BedwarsMeow bedwarsMeow2 = Main.bedwarsMeow;
                final int cnt = BedwarsMeow.meowMessages.size();
                final StringBuilder append = new StringBuilder().append(prefix).append("&b\u041e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u043e &l").append(cnt).append("&b \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438");
                final MyChatListener chatListener = Main.chatListener;
                ChatSender.addText(append.append(MyChatListener.getNumberEnding(cnt, "\u0435", "\u044f", "\u0439")).append("!").toString());
                MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
            }
        }
        else if (args[0].trim().equals("toggle")) {
            Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW.text).set(!Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW.text).getBoolean());
            this.printHelpInfo();
        }
        else if (args[0].trim().equals("toggleColors")) {
            Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW_WITH_COLORS.text).set(!Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW_WITH_COLORS.text).getBoolean());
            this.printHelpInfo();
        }
        else if (args[0].trim().equals("reset")) {
            Main.bedwarsMeow.initMeowMessages();
            Main.bedwarsMeow.readFile();
            this.printHelpInfo();
            final BedwarsMeow bedwarsMeow3 = Main.bedwarsMeow;
            if (BedwarsMeow.meowMessages != null) {
                final BedwarsMeow bedwarsMeow4 = Main.bedwarsMeow;
                final int cnt = BedwarsMeow.meowMessages.size();
                final StringBuilder append2 = new StringBuilder().append(prefix).append("&c\u0424\u0430\u0439\u043b \u0432\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d&7, &b\u0441\u0447\u0438\u0442\u0430\u043d\u043e &l").append(cnt).append("&b \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438");
                final MyChatListener chatListener2 = Main.chatListener;
                ChatSender.addText(append2.append(MyChatListener.getNumberEnding(cnt, "\u0435", "\u044f", "\u0439")).append("!").toString());
                MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
            }
        }
        else {
            if (!args[0].trim().equals("remove_bad_words")) {
                this.printHelpInfo();
                return;
            }
            Main.bedwarsMeow.removeMeessagesWithBadWords();
            this.printHelpInfo();
            final BedwarsMeow bedwarsMeow5 = Main.bedwarsMeow;
            if (BedwarsMeow.meowMessages != null) {
                final BedwarsMeow bedwarsMeow6 = Main.bedwarsMeow;
                final int cnt = BedwarsMeow.meowMessages.size();
                final StringBuilder append3 = new StringBuilder().append(prefix).append("&6\u0424\u0430\u0439\u043b \u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d&7, &b\u0441\u0447\u0438\u0442\u0430\u043d\u043e &l").append(cnt).append("&b \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438");
                final MyChatListener chatListener3 = Main.chatListener;
                ChatSender.addText(append3.append(MyChatListener.getNumberEnding(cnt, "\u0435", "\u044f", "\u0439")).append("!").toString());
                MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
            }
        }
        final Main main_instance = this.main_instance;
        Main.saveConfig();
        final Main main_instance2 = this.main_instance;
        Main.bedwarsMeow.updateBooleans();
    }
    
    static {
        CommandMeow.command_text = "/meow";
    }
}
