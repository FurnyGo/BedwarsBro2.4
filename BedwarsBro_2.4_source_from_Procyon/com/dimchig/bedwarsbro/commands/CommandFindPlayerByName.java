// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import java.util.Date;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.Iterator;
import java.util.List;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandFindPlayerByName extends CommandBase
{
    public static String command_text;
    
    public CommandFindPlayerByName() {
        CommandFindPlayerByName.command_text = CommandFindPlayerByName.command_text.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandFindPlayerByName.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Find player by name";
    }
    
    public void work(final String name) {
        final List<EntityPlayer> players = (List<EntityPlayer>)Minecraft.func_71410_x().field_71441_e.field_73010_i;
        for (final EntityPlayer p : players) {
            if (p.func_70005_c_().equalsIgnoreCase(name)) {
                final OnMyTickEvent myTickEvent = Main.myTickEvent;
                OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH = "";
                final MyChatListener chatListener = Main.chatListener;
                final MyChatListener chatListener2 = Main.chatListener;
                MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
                ChatSender.addText("&f\u041d\u0430\u0439\u0434\u0435\u043d &e" + p.func_70005_c_());
                final EntityPlayerSP mod_palayer = Minecraft.func_71410_x().field_71439_g;
                HintsFinder.lookAtPlayer(mod_palayer.field_70165_t, mod_palayer.field_70163_u, mod_palayer.field_70161_v, p.field_70165_t, p.field_70163_u, p.field_70161_v);
                Minecraft.func_71410_x().field_71439_g.func_70016_h(0.0, 0.0, 0.0);
            }
        }
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1) {
            return;
        }
        try {
            final String name = args[0].trim();
            final OnMyTickEvent myTickEvent = Main.myTickEvent;
            if (!OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH.equals(name)) {
                ChatSender.addText("&f\u041f\u043e\u0438\u0441\u043a &e" + name + "&f...");
                final OnMyTickEvent myTickEvent2 = Main.myTickEvent;
                OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH = name;
                final OnMyTickEvent myTickEvent3 = Main.myTickEvent;
                OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH_TIME = new Date().getTime();
            }
            this.work(name);
        }
        catch (Exception ex) {
            ChatSender.addText("&cInvalid arguments!");
        }
    }
    
    static {
        CommandFindPlayerByName.command_text = "/findplayer";
    }
}
