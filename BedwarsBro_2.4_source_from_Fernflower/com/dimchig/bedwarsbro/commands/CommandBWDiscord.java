package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.gui.GuiPlayer;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommandBWDiscord extends CommandBase {

   public static String command_text = "bwdiscord";


   public CommandBWDiscord() {
      command_text = command_text.replace("/", "");
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public String func_71517_b() {
      return command_text;
   }

   public String func_71518_a(ICommandSender sender) {
      return "Discord";
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      ChatSender.addText("&8<=====================================>\n");
      ChatSender.addLinkAndHoverText("        &9Discord &fсервер BedwarsBro - &b&l&nЖМИ&r &b↗\n", "&fНажми, чтоб присоединится к серверу", "" + Main.getPropDiscordLink());
      if(this.isOk()) {
         ChatSender.addClickText("&8<=====================================>", "/bwdiscord link");
         if(args.length > 0 && args[0].equals("link")) {
            OnMyTickEvent var10000 = Main.myTickEvent;
            OnMyTickEvent.gui2open = new GuiPlayer();
         }

      } else {
         ChatSender.addText("&8<=====================================>");
      }
   }

   public boolean isOk() {
      if(HintsValidator.isPasswordCorrect() && !Minecraft.func_71410_x().func_71356_B() && Main.isPropSelfAdmin()) {
         int i = 0;

         while(true) {
            InventoryPlayer var10001 = Minecraft.func_71410_x().field_71439_g.field_71071_by;
            if(i >= InventoryPlayer.func_70451_h()) {
               return false;
            }

            ItemStack stack = Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a[i];
            if(stack != null) {
               Item item = stack.func_77973_b();
               if(item != null && stack.func_82833_r() != null) {
                  return true;
               }
            }

            ++i;
         }
      } else {
         return false;
      }
   }

}
