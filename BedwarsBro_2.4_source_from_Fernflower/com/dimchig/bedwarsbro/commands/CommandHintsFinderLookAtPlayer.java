package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandHintsFinderLookAtPlayer extends CommandBase {

   public static String command_text = "/bedwarsChatModLookAtPlayer";


   public CommandHintsFinderLookAtPlayer() {
      command_text = command_text.replace("/", "");
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public String func_71517_b() {
      return command_text;
   }

   public String func_71518_a(ICommandSender sender) {
      return "Makes message rainbow";
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      if(args.length == 4) {
         try {
            double ex = Double.parseDouble(args[0]);
            double posY = Double.parseDouble(args[1]);
            double posZ = Double.parseDouble(args[2]);
            String name = args[3];
            HintsFinder.findAndlookAtPlayer(ex, posY, posZ, name);
         } catch (Exception var10) {
            ChatSender.addText("&cInvalid arguments!");
         }

      }
   }

}
