// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.command.CommandException;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.gui.GuiPlayer;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandBWDiscord extends CommandBase
{
    public static String command_text;
    
    public CommandBWDiscord() {
        CommandBWDiscord.command_text = CommandBWDiscord.command_text.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandBWDiscord.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Discord";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        ChatSender.addText("&8<=====================================>\n");
        ChatSender.addLinkAndHoverText("        &9Discord &f\u0441\u0435\u0440\u0432\u0435\u0440 BedwarsBro - &b&l&n\u0416\u041c\u0418&r &b\u2197\n", "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 \u043f\u0440\u0438\u0441\u043e\u0435\u0434\u0438\u043d\u0438\u0442\u0441\u044f \u043a \u0441\u0435\u0440\u0432\u0435\u0440\u0443", "" + Main.getPropDiscordLink());
        if (this.isOk()) {
            ChatSender.addClickText("&8<=====================================>", "/bwdiscord link");
            if (args.length > 0 && args[0].equals("link")) {
                final OnMyTickEvent myTickEvent = Main.myTickEvent;
                OnMyTickEvent.gui2open = new GuiPlayer();
            }
            return;
        }
        ChatSender.addText("&8<=====================================>");
    }
    
    public boolean isOk() {
        if (!HintsValidator.isPasswordCorrect() || Minecraft.func_71410_x().func_71356_B() || !Main.isPropSelfAdmin()) {
            return false;
        }
        int i = 0;
        while (true) {
            final int n = i;
            final InventoryPlayer field_71071_by = Minecraft.func_71410_x().field_71439_g.field_71071_by;
            if (n >= InventoryPlayer.func_70451_h()) {
                return false;
            }
            final ItemStack stack = Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null) {
                final Item item = stack.func_77973_b();
                if (item != null) {
                    if (stack.func_82833_r() != null) {
                        return true;
                    }
                }
            }
            ++i;
        }
    }
    
    static {
        CommandBWDiscord.command_text = "bwdiscord";
    }
}
