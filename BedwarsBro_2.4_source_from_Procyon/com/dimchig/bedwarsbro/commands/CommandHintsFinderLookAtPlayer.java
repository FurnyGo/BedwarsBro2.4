// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandHintsFinderLookAtPlayer extends CommandBase
{
    public static String command_text;
    
    public CommandHintsFinderLookAtPlayer() {
        CommandHintsFinderLookAtPlayer.command_text = CommandHintsFinderLookAtPlayer.command_text.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandHintsFinderLookAtPlayer.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Makes message rainbow";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 4) {
            return;
        }
        try {
            final double posX = Double.parseDouble(args[0]);
            final double posY = Double.parseDouble(args[1]);
            final double posZ = Double.parseDouble(args[2]);
            final String name = args[3];
            HintsFinder.findAndlookAtPlayer(posX, posY, posZ, name);
        }
        catch (Exception ex) {
            ChatSender.addText("&cInvalid arguments!");
        }
    }
    
    static {
        CommandHintsFinderLookAtPlayer.command_text = "/bedwarsChatModLookAtPlayer";
    }
}
