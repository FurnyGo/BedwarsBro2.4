package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandEnableESP extends CommandBase {

   public static String command_text = "bwesp";


   public CommandEnableESP() {
      command_text = command_text.replace("/", "");
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public String func_71517_b() {
      return command_text;
   }

   public String func_71518_a(ICommandSender sender) {
      return "Toggle bwesp";
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      GuiPlayerFocus var10000 = Main.playerFocus;
      var10000 = Main.playerFocus;
      GuiPlayerFocus.STATE = !GuiPlayerFocus.STATE;
      var10000 = Main.playerFocus;
      GuiPlayerFocus.isT_Active = false;
      StringBuilder var3 = new StringBuilder();
      GuiPlayerFocus var10001 = Main.playerFocus;
      var3 = var3.append(GuiPlayerFocus.STATE?"&a":"&c").append("ESP ");
      var10001 = Main.playerFocus;
      ChatSender.addText(var3.append(GuiPlayerFocus.STATE?"включен":"выключен").toString());
   }

}
