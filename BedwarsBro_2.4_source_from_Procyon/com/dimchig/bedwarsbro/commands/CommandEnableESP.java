// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandEnableESP extends CommandBase
{
    public static String command_text;
    
    public CommandEnableESP() {
        CommandEnableESP.command_text = CommandEnableESP.command_text.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandEnableESP.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Toggle bwesp";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final GuiPlayerFocus playerFocus = Main.playerFocus;
        final GuiPlayerFocus playerFocus2 = Main.playerFocus;
        GuiPlayerFocus.STATE = !GuiPlayerFocus.STATE;
        final GuiPlayerFocus playerFocus3 = Main.playerFocus;
        GuiPlayerFocus.isT_Active = false;
        final StringBuilder sb = new StringBuilder();
        final GuiPlayerFocus playerFocus4 = Main.playerFocus;
        final StringBuilder append = sb.append(GuiPlayerFocus.STATE ? "&a" : "&c").append("ESP ");
        final GuiPlayerFocus playerFocus5 = Main.playerFocus;
        ChatSender.addText(append.append(GuiPlayerFocus.STATE ? "\u0432\u043a\u043b\u044e\u0447\u0435\u043d" : "\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d").toString());
    }
    
    static {
        CommandEnableESP.command_text = "bwesp";
    }
}
