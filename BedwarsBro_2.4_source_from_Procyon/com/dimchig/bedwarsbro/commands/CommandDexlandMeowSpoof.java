// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandDexlandMeowSpoof extends CommandBase
{
    public static String command_text;
    
    public CommandDexlandMeowSpoof() {
        CommandDexlandMeowSpoof.command_text = CommandDexlandMeowSpoof.command_text.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandDexlandMeowSpoof.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Makes message rainbow setter";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final String[] cmds = { "kill", "death", "bed", "join", "win" };
        String av_cmds = "";
        for (final String c : cmds) {
            av_cmds = av_cmds + "&b" + c + "&7/";
        }
        av_cmds = av_cmds.substring(0, av_cmds.length() - 3);
        if (args.length < 2) {
            ChatSender.addText("&d\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0432\u0441\u0435 5 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439:\n  /" + CommandDexlandMeowSpoof.command_text + " &e\u043d\u0438\u043a &a\u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435");
            ChatSender.addText("&d\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u043e\u0434\u043d\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435:\n&f  /" + CommandDexlandMeowSpoof.command_text + " " + av_cmds + " &e\u043d\u0438\u043a &a\u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435\n     &7(\u043c\u043e\u0436\u043d\u043e \u044e\u0437\u0430\u0442\u044c %player% \u043f\u0440\u0438 &bkill&7)");
            ChatSender.addText("&d\u0417\u0430\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u0441\u043a\u0430\u0437\u0430\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435:\n&f  /" + CommandDexlandMeowSpoof.command_text + " &cfake&bdeath&7/&cfake&bjoin&7/&cfake&bwin &e\u043d\u0438\u043a");
            return;
        }
        int start_idx = 1;
        String type = "all";
        String nick = args[0];
        String local_command = "";
        for (final String c2 : cmds) {
            if (args[0].equals(c2)) {
                if (args.length < 3) {
                    return;
                }
                type = c2;
                nick = args[1];
                start_idx = 2;
            }
            else if (args[0].contains("fake") && args[0].contains(c2)) {
                type = "fake" + c2;
                local_command = c2;
                nick = args[1];
                start_idx = -1;
            }
        }
        if (type.contains("fake")) {
            ChatSender.addText("\u0424\u0435\u0439\u043a\u0443\u044e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 &e" + local_command + "&f...");
            String s = "";
            if (local_command.equals("death")) {
                s = "\u0410\u0445\u0430\u0445, " + nick + " \u0443\u043f\u0430\u043b \u0432 \u0431\u0435\u0437\u0434\u043d\u0443, \u0432\u044b \u0432\u0438\u0434\u0435\u043b\u0438?";
            }
            else if (local_command.equals("join")) {
                s = "\u0412\u0441\u0435\u043c \u043f\u0440\u0438\u0432\u0435\u0442! \u041f\u043e\u0431\u0435\u0434\u0438\u0442 \u0442\u043e\u043b\u044c\u043a\u043e \u043e\u0434\u043d\u0430, \u0441\u0438\u043b\u044c\u043d\u0435\u0439\u0448\u0430\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u0430!";
            }
            else if (local_command.equals("win")) {
                s = "\u0420\u0435\u0431\u044f\u0442, \u0432\u044b \u0437\u043d\u0430\u043b\u0438, \u0447\u0442\u043e \u041f\u0435\u0440\u0435\u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0447\u0435\u0440\u0435\u0437 10 \u0441\u0435\u043a\u0443\u043d\u0434!";
            }
            else {
                ChatSender.addText("&c\u042d\u0442\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u043d\u0435\u043b\u044c\u0437\u044f \u0441\u0444\u0435\u0439\u043a\u043e\u0432\u0430\u0442\u044c!\n&c\u041c\u043e\u0436\u043d\u043e \u0442\u043e\u043b\u044c\u043a\u043e &bdeath&7, &bjoin&7, &bwin");
            }
            if (s.length() == 0) {
                return;
            }
            final MyChatListener chatListener = Main.chatListener;
            MyChatListener.addBedwarsMeowMessageToQuee(s, false);
        }
        else {
            String msg = "";
            for (int i = start_idx; i < args.length; ++i) {
                msg = msg + args[i] + " ";
            }
            msg = msg.trim();
            String[] commands = { "setkillmsg", "setdeathmsg", "setbedmsg", "setjoinmsg", "setwinmsg" };
            for (final String c3 : cmds) {
                if (type.equals(c3)) {
                    commands = new String[] { "set" + c3 + "msg" };
                }
            }
            boolean isOnServer = false;
            final Collection<NetworkPlayerInfo> players = (Collection<NetworkPlayerInfo>)Minecraft.func_71410_x().func_147114_u().func_175106_d();
            final int cnt = 0;
            for (final NetworkPlayerInfo info : players) {
                if (info != null) {
                    if (info.func_178845_a() == null) {
                        continue;
                    }
                    final String name = info.func_178845_a().getName();
                    if (name == null) {
                        continue;
                    }
                    if (name.equals(nick)) {
                        isOnServer = true;
                        break;
                    }
                    continue;
                }
            }
            if (!isOnServer) {
                ChatSender.addText("&c\u0418\u0433\u0440\u043e\u043a &c\"&e" + nick + "&c\" &c \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!");
                return;
            }
            for (final String cmd : commands) {
                final String s2 = nick + " ." + cmd + " " + msg;
                final MyChatListener chatListener2 = Main.chatListener;
                MyChatListener.addBedwarsMeowMessageToQuee(s2, false);
            }
        }
    }
    
    static {
        CommandDexlandMeowSpoof.command_text = "meowspoof";
    }
}
