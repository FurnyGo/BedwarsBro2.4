// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Iterator;
import java.util.ArrayList;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.command.ICommandSender;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.command.CommandBase;

public class CommandFriends extends CommandBase
{
    public static String filename;
    public static String command_text;
    Main main_instance;
    
    public CommandFriends(final Main main) {
        CommandFriends.command_text = CommandFriends.command_text.replace("/", "");
        this.main_instance = main;
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandFriends.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "";
    }
    
    public void printFriends() {
        final ArrayList<String> arr = Main.fileNicknamesManager.readNames(CommandFriends.filename);
        if (arr.size() == 0) {
            final StringBuilder sb = new StringBuilder();
            final MyChatListener chatListener = Main.chatListener;
            ChatSender.addText(sb.append(MyChatListener.PREFIX_FRIENDS).append("&c\u0414\u0440\u0443\u0437\u0435\u0439 \u043d\u0435\u0442\u0443! \u0414\u043e\u0431\u0430\u0432\u044c \u0447\u0435\u0440\u0435\u0437 /").append(CommandFriends.command_text).append(" add [\u043d\u0438\u043a]").toString());
            return;
        }
        final StringBuilder sb2 = new StringBuilder();
        final MyChatListener chatListener2 = Main.chatListener;
        ChatSender.addText(sb2.append(MyChatListener.PREFIX_FRIENDS).append("&f\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 &7(\u0443\u0434\u0430\u043b\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430 - &c/").append(CommandFriends.command_text).append(" remove [\u043d\u0438\u043a]&7)&f:").toString());
        for (final String s : arr) {
            final String command = "/party " + s.trim();
            final String remove_friend = "/" + CommandFriends.command_text + " remove " + s.trim();
            ChatSender.addClickAndHoverText("&8 \u2022 &f" + s.trim() + "    &e[\u043a\u0438\u043d\u0443\u0442\u044c \u043f\u0430\u0442\u0438]", "&e" + command, command);
        }
        final String command2 = "/" + CommandFriends.command_text + " party_all";
        final StringBuilder sb3 = new StringBuilder();
        final MyChatListener chatListener3 = Main.chatListener;
        ChatSender.addClickAndHoverText(sb3.append(MyChatListener.PREFIX_FRIENDS).append("&e[\u041a\u0438\u043d\u0443\u0442\u044c \u043f\u0430\u0442\u0438 \u0412\u0421\u0415\u041c \u0434\u0440\u0443\u0437\u044c\u044f\u043c]").toString(), "&e" + command2, command2);
        ChatSender.addText("");
    }
    
    public void sendPartyToAll() {
        final ArrayList<String> arr = Main.fileNicknamesManager.readNames(CommandFriends.filename);
        for (int i = 0; i < arr.size(); ++i) {
            final String name = arr.get(i).trim();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    final StringBuilder sb = new StringBuilder();
                    final MyChatListener chatListener = Main.chatListener;
                    ChatSender.addText(sb.append(MyChatListener.PREFIX_FRIENDS).append("&f\u041a\u0438\u0434\u0430\u044e \u043f\u0430\u0442\u0438 &e").append(name).append("...").toString());
                    ChatSender.sendText("/party " + name);
                }
            }, i * 500);
        }
        final StringBuilder sb = new StringBuilder();
        final MyChatListener chatListener = Main.chatListener;
        final StringBuilder append = sb.append(MyChatListener.PREFIX_FRIENDS).append("&a\u041a\u0438\u0434\u0430\u044e \u043f\u0430\u0442\u0438 ").append(arr.size()).append(" \u0438\u0433\u0440\u043e\u043a");
        final MyChatListener chatListener2 = Main.chatListener;
        ChatSender.addText(append.append(MyChatListener.getNumberEnding(arr.size(), "\u0443", "\u0430\u043c", "\u0430\u043c")).append("...").toString());
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        ChatSender.addText("");
        try {
            if (args.length == 0) {
                this.printFriends();
            }
            else if (args[0].equals("list")) {
                this.printFriends();
            }
            else if (args[0].equals("party_all")) {
                this.sendPartyToAll();
            }
            else if (args[0].equals("add") && args.length == 2) {
                final String name = args[1].trim();
                final boolean isSuccess = Main.fileNicknamesManager.addName(CommandFriends.filename, name);
                if (isSuccess) {
                    this.printFriends();
                    final StringBuilder sb = new StringBuilder();
                    final MyChatListener chatListener = Main.chatListener;
                    ChatSender.addText(sb.append(MyChatListener.PREFIX_FRIENDS).append("&a\u0414\u0440\u0443\u0433 \"").append(name).append("\" \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d").toString());
                    final MyChatListener chatListener2 = Main.chatListener;
                    final MyChatListener chatListener3 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
                }
                else {
                    this.printFriends();
                    final StringBuilder sb2 = new StringBuilder();
                    final MyChatListener chatListener4 = Main.chatListener;
                    ChatSender.addText(sb2.append(MyChatListener.PREFIX_FRIENDS).append("&c\u0414\u0440\u0443\u0433 \"").append(name).append("\" \u0443\u0436\u0435 \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d").toString());
                    final MyChatListener chatListener5 = Main.chatListener;
                    final MyChatListener chatListener6 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_REJECT);
                }
            }
            else if (args[0].equals("remove") && args.length == 2) {
                final String name = args[1].trim();
                final boolean isSuccess = Main.fileNicknamesManager.removeName(CommandFriends.filename, name);
                if (isSuccess) {
                    this.printFriends();
                    final StringBuilder sb3 = new StringBuilder();
                    final MyChatListener chatListener7 = Main.chatListener;
                    ChatSender.addText(sb3.append(MyChatListener.PREFIX_FRIENDS).append("&c\u0414\u0440\u0443\u0433 \"").append(name).append("\" \u0443\u0434\u0430\u043b\u0435\u043d").toString());
                    final MyChatListener chatListener8 = Main.chatListener;
                    final MyChatListener chatListener9 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
                }
                else {
                    this.printFriends();
                    final StringBuilder sb4 = new StringBuilder();
                    final MyChatListener chatListener10 = Main.chatListener;
                    ChatSender.addText(sb4.append(MyChatListener.PREFIX_FRIENDS).append("&c\u0414\u0440\u0443\u0433 \"").append(name).append("\" \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d").toString());
                    final MyChatListener chatListener11 = Main.chatListener;
                    final MyChatListener chatListener12 = Main.chatListener;
                    MyChatListener.playSound(MyChatListener.SOUND_REJECT);
                }
            }
            else {
                this.printFriends();
                final StringBuilder sb5 = new StringBuilder();
                final MyChatListener chatListener13 = Main.chatListener;
                ChatSender.addText(sb5.append(MyChatListener.PREFIX_FRIENDS).append("&f/").append(CommandFriends.command_text).append(" &aadd &f[\u043d\u0438\u043a] &7- &f\u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430").toString());
                final StringBuilder sb6 = new StringBuilder();
                final MyChatListener chatListener14 = Main.chatListener;
                ChatSender.addText(sb6.append(MyChatListener.PREFIX_FRIENDS).append("&f/").append(CommandFriends.command_text).append(" &cremove &f[\u043d\u0438\u043a] &7- &f\u0443\u0434\u0430\u043b\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430").toString());
                final StringBuilder sb7 = new StringBuilder();
                final MyChatListener chatListener15 = Main.chatListener;
                ChatSender.addText(sb7.append(MyChatListener.PREFIX_FRIENDS).append("&f/").append(CommandFriends.command_text).append(" &blist &7- &f\u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439").toString());
            }
        }
        catch (Exception ex) {
            ChatSender.addText("&c\u041e\u0448\u0438\u0431\u043a\u0430!");
            ex.printStackTrace();
        }
        ChatSender.addText("");
    }
    
    static {
        CommandFriends.filename = "BedwarsBro_\u0421\u043f\u0438\u0441\u043e\u043a_\u0434\u0440\u0443\u0437\u0435\u0439.txt";
        CommandFriends.command_text = "/bwf";
    }
}
