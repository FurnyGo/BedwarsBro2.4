// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import java.util.Iterator;
import java.util.ArrayList;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.command.ICommandSender;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.command.CommandBase;

public class CommandMute extends CommandBase
{
    public static String filename;
    public static String command_text;
    Main main_instance;
    
    public CommandMute(final Main main) {
        CommandMute.command_text = CommandMute.command_text.replace("/", "");
        this.main_instance = main;
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandMute.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Help mod";
    }
    
    public void printMuted() {
        final ArrayList<String> arr = Main.fileNicknamesManager.readNames(CommandMute.filename);
        if (arr.size() == 0) {
            final StringBuilder sb = new StringBuilder();
            final MyChatListener chatListener = Main.chatListener;
            ChatSender.addText(sb.append(MyChatListener.PREFIX_MUTED).append("&c\u0417\u0430\u043c\u0443\u0447\u0435\u043d\u043d\u044b\u0445 \u043d\u0435\u0442\u0443! \u0414\u043e\u0431\u0430\u0432\u044c \u0447\u0435\u0440\u0435\u0437 /").append(CommandMute.command_text).append(" add [\u043d\u0438\u043a]").toString());
            return;
        }
        final StringBuilder sb2 = new StringBuilder();
        final MyChatListener chatListener2 = Main.chatListener;
        ChatSender.addText(sb2.append(MyChatListener.PREFIX_MUTED).append("&f\u0421\u043f\u0438\u0441\u043e\u043a \u0437\u0430\u043c\u0443\u0447\u0435\u043d\u043d\u044b\u0445:").toString());
        for (final String s : arr) {
            final String command = "/" + CommandMute.command_text + " remove " + s.trim();
            ChatSender.addClickAndHoverText("&8 \u2022 &f" + s.trim() + "    &c[\u0443\u0434\u0430\u043b\u0438\u0442\u044c]", "&c" + command, command);
        }
        ChatSender.addText("");
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        ChatSender.addText("");
        try {
            if (args.length == 0) {
                this.printMuted();
            }
            else if (args[0].equals("list")) {
                this.printMuted();
            }
            else if (args[0].equals("add") && args.length == 2) {
                final String name = args[1].trim();
                final boolean isSuccess = Main.fileNicknamesManager.addName(CommandMute.filename, name);
                if (isSuccess) {
                    this.printMuted();
                    final StringBuilder sb = new StringBuilder();
                    final MyChatListener chatListener = Main.chatListener;
                    ChatSender.addText(sb.append(MyChatListener.PREFIX_MUTED).append("&a\u0418\u0433\u0440\u043e\u043a \"").append(name).append("\" \u0437\u0430\u043c\u0443\u0447\u0435\u043d").toString());
                    final MyChatListener chatListener2 = Main.chatListener;
                    final MyChatListener chatListener3 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
                }
                else {
                    this.printMuted();
                    final StringBuilder sb2 = new StringBuilder();
                    final MyChatListener chatListener4 = Main.chatListener;
                    ChatSender.addText(sb2.append(MyChatListener.PREFIX_MUTED).append("&c\u0414\u0440\u0443\u0433 \"").append(name).append("\" \u0443\u0436\u0435 \u0437\u0430\u043c\u0443\u0447\u0435\u043d").toString());
                    final MyChatListener chatListener5 = Main.chatListener;
                    final MyChatListener chatListener6 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_REJECT);
                }
            }
            else if (args[0].equals("remove") && args.length == 2) {
                final String name = args[1].trim();
                final boolean isSuccess = Main.fileNicknamesManager.removeName(CommandMute.filename, name);
                if (isSuccess) {
                    this.printMuted();
                    final StringBuilder sb3 = new StringBuilder();
                    final MyChatListener chatListener7 = Main.chatListener;
                    ChatSender.addText(sb3.append(MyChatListener.PREFIX_MUTED).append("&c\u0414\u0440\u0443\u0433 \"").append(name).append("\" \u0443\u0434\u0430\u043b\u0435\u043d").toString());
                    final MyChatListener chatListener8 = Main.chatListener;
                    final MyChatListener chatListener9 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
                }
                else {
                    this.printMuted();
                    final StringBuilder sb4 = new StringBuilder();
                    final MyChatListener chatListener10 = Main.chatListener;
                    ChatSender.addText(sb4.append(MyChatListener.PREFIX_MUTED).append("&c\u0414\u0440\u0443\u0433 \"").append(name).append("\" \u0443\u0436\u0435 \u0443\u0434\u0430\u043b\u0435\u043d").toString());
                    final MyChatListener chatListener11 = Main.chatListener;
                    final MyChatListener chatListener12 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_REJECT);
                }
            }
            else {
                this.printMuted();
                final StringBuilder sb5 = new StringBuilder();
                final MyChatListener chatListener13 = Main.chatListener;
                ChatSender.addText(sb5.append(MyChatListener.PREFIX_MUTED).append("&f/").append(CommandMute.command_text).append(" &aadd &f[\u043d\u0438\u043a] &7- &f\u0437\u0430\u043c\u0443\u0442\u0438\u0442\u044c").toString());
                final StringBuilder sb6 = new StringBuilder();
                final MyChatListener chatListener14 = Main.chatListener;
                ChatSender.addText(sb6.append(MyChatListener.PREFIX_MUTED).append("&f/").append(CommandMute.command_text).append(" &cremove &f[\u043d\u0438\u043a] &7- &f\u0440\u0430\u0437\u043c\u0443\u0442\u0438\u0442\u044c").toString());
                final StringBuilder sb7 = new StringBuilder();
                final MyChatListener chatListener15 = Main.chatListener;
                ChatSender.addText(sb7.append(MyChatListener.PREFIX_MUTED).append("&f/").append(CommandMute.command_text).append(" &blist &7- &f\u0441\u043f\u0438\u0441\u043e\u043a \u0437\u0430\u043c\u0443\u0447\u0435\u043d\u043d\u044b\u0445").toString());
                final StringBuilder sb8 = new StringBuilder();
                final MyChatListener chatListener16 = Main.chatListener;
                ChatSender.addText(sb8.append(MyChatListener.PREFIX_MUTED).append("&f\u041c\u043e\u0436\u043d\u043e \u043c\u0443\u0442\u0438\u0442\u044c \u0438\u0433\u0440\u043e\u043a\u043e\u0432, \u0443 \u043a\u043e\u0442\u043e\u0440\u044b\u0445 \u043d\u0438\u043a \u043d\u0430\u0447\u0438\u043d\u0430\u0435\u0442\u0441\u044f \u043d\u0430 \u0447\u0442\u043e-\u0442\u043e, \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u044f \u0437\u0432\u0435\u0437\u0434\u043e\u0447\u043a\u0443. \u041d\u0430\u043f\u0440\u0438\u043c\u0435 &f/").append(CommandMute.command_text).append(" add &cARAB_*&f - \u0431\u0443\u0434\u0443\u0442 \u0437\u0430\u043c\u0443\u0447\u0435\u043d\u043d\u044b \u0432\u0441\u0435 \u0438\u0433\u0440\u043e\u043a\u0438 \u0442\u0438\u043f\u043e &cARAB_1234 &f\u0438 &cARAB_zxc").toString());
            }
        }
        catch (Exception ex) {
            ChatSender.addText("&c\u041e\u0448\u0438\u0431\u043a\u0430!");
            ex.printStackTrace();
            return;
        }
        ChatSender.addText("");
    }
    
    static {
        CommandMute.filename = "BedwarsBro_\u0421\u043f\u0438\u0441\u043e\u043a_\u0437\u0430\u043c\u0443\u0447\u0435\u043d\u043d\u044b\u0445.txt";
        CommandMute.command_text = "/bwmute";
    }
}
