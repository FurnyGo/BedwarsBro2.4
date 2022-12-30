package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandFindPlayerByName extends CommandBase {

   public static String command_text = "/findplayer";


   public CommandFindPlayerByName() {
      command_text = command_text.replace("/", "");
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public String func_71517_b() {
      return command_text;
   }

   public String func_71518_a(ICommandSender sender) {
      return "Find player by name";
   }

   public void work(String name) {
      List players = Minecraft.func_71410_x().field_71441_e.field_73010_i;
      Iterator var3 = players.iterator();

      while(var3.hasNext()) {
         EntityPlayer p = (EntityPlayer)var3.next();
         if(p.func_70005_c_().equalsIgnoreCase(name)) {
            OnMyTickEvent var10000 = Main.myTickEvent;
            OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH = "";
            MyChatListener var6 = Main.chatListener;
            var6 = Main.chatListener;
            MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
            ChatSender.addText("&fНайден &e" + p.func_70005_c_());
            EntityPlayerSP mod_palayer = Minecraft.func_71410_x().field_71439_g;
            HintsFinder.lookAtPlayer(mod_palayer.field_70165_t, mod_palayer.field_70163_u, mod_palayer.field_70161_v, p.field_70165_t, p.field_70163_u, p.field_70161_v);
            Minecraft.func_71410_x().field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      if(args.length == 1) {
         try {
            String ex = args[0].trim();
            OnMyTickEvent var10000 = Main.myTickEvent;
            if(!OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH.equals(ex)) {
               ChatSender.addText("&fПоиск &e" + ex + "&f...");
               var10000 = Main.myTickEvent;
               OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH = ex;
               var10000 = Main.myTickEvent;
               OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH_TIME = (new Date()).getTime();
            }

            this.work(ex);
         } catch (Exception var4) {
            ChatSender.addText("&cInvalid arguments!");
         }

      }
   }

}
