package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import com.dimchig.bedwarsbro.particles.ParticleController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandHintsFinderLookAt extends CommandBase {

   public static String command_text = "/bedwarsChatModLookAt";


   public CommandHintsFinderLookAt() {
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
            int isEmerald = Integer.parseInt(args[3]);
            EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
            HintsFinder.lookAtPlayer(player.field_70165_t, player.field_70163_u + (double)player.func_70047_e(), player.field_70161_v, ex, posY, posZ);
            if(isEmerald == 1) {
               ParticleController.spawnGenEmeraldParticles(ex, posY, posZ, 1);
            } else {
               ParticleController.spawnGenDiamondParticles(ex, posY, posZ, 1);
            }
         } catch (Exception var11) {
            ChatSender.addText("&cInvalid arguments!");
         }

      }
   }

}
