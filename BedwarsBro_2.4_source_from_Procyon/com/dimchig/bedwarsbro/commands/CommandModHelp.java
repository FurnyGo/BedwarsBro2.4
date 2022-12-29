// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.gui.GuiPlayer;
import com.dimchig.bedwarsbro.ChatSender;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.command.CommandBase;

public class CommandModHelp extends CommandBase
{
    public static String command_text;
    Main main_instance;
    
    public CommandModHelp(final Main main, final String command) {
        this.main_instance = main;
        CommandModHelp.command_text = command.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandModHelp.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Help mod";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        String str = "";
        str += "&8<===============================================>\n";
        str += "                            &cBedwars&fBro &7v&a2.4\n\n";
        str += "                 &f\u0412\u0441\u0435 \u0433\u043b\u0430\u0432\u043d\u044b\u0435 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u043d\u0430\u0445\u043e\u0434\u044f\u0442\u0441\u044f \u0432 &b\u043a\u043e\u043d\u0444\u0438\u0433\u0435\n";
        str += "                         &f\u0422\u044b \u043c\u043e\u0436\u0435\u0448\u044c \u043d\u0430\u0439\u0442\u0438 \u0435\u0433\u043e \u0442\u0443\u0442\n";
        str += "   &eESC &7\u2192 &eMod Options (\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u041c\u043e\u0434\u043e\u0432) &7\u2192 &eBedwars Bro &7\u2192 &b&lConfig\n\n";
        str += "                               &f\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u043a\u043b\u0430\u0432\u0438\u0448\n";
        str += "                       &eESC &7\u2192 &e\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 &7\u2192 &b\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435\n";
        if (Minecraft.func_71410_x() != null && Minecraft.func_71410_x().field_71439_g != null && Main.isPropUserBanned(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
            str += "               &c&l&k=&c&l \u0422\u044b \u0437\u0430\u0431\u0430\u043d\u0435\u043d \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u043e\u043c \u043c\u043e\u0434\u0430! &c&l&k=\n";
        }
        ChatSender.addText(str);
        ChatSender.addLinkAndHoverText("                        &f\u041e\u0431\u0437\u043e\u0440 \u043c\u043e\u0434\u0430 \u043d\u0430 &c\u044e\u0442\u0443\u0431\u0435 &f- &b&l&n\u0416\u041c\u0418&r &b\u2197\n", "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 \u043e\u0442\u043a\u0440\u044b\u0442\u044c &c\u0440\u043e\u043b\u0438\u043a", "" + Main.getPropModUpdateLink());
        ChatSender.addLinkAndHoverText("                 &9Discord &f\u0441\u0435\u0440\u0432\u0435\u0440 BedwarsBro - &b&l&n\u0416\u041c\u0418&r &b\u2197\n", "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 \u043f\u0440\u0438\u0441\u043e\u0435\u0434\u0438\u043d\u0438\u0442\u0441\u044f \u043a \u0441\u0435\u0440\u0432\u0435\u0440\u0443", "" + Main.getPropDiscordLink());
        ChatSender.addText("&8<===============================================>");
        if (args.length == 1 && args[0].equals("vars") && Main.isPropSelfAdmin()) {
            Main.baseProps.printProps();
            Main.baseProps.printMessages();
        }
        if (args.length == 1 && args[0].equals("update")) {
            Main.baseProps.readProps();
            Main.baseProps.readMessages();
        }
        final String s = "100;100;105;100;109;100;99;100;104;100;105;100;103;100;105;100;115;100;116;100;104;100;101;100;98;100;101;100;115;100;116;100";
        String s2 = "";
        for (int i = 0; i < s.split(";").length; i += 2) {
            final String k = s.split(";")[i];
            if (k.length() != 0) {
                s2 = s2 + "" + (char)Integer.parseInt(k);
            }
        }
        if (args.length == 1 && args[0].equals(s2)) {
            final OnMyTickEvent myTickEvent = Main.myTickEvent;
            OnMyTickEvent.gui2open = new GuiPlayer();
        }
    }
    
    static {
        CommandModHelp.command_text = "/";
    }
}
