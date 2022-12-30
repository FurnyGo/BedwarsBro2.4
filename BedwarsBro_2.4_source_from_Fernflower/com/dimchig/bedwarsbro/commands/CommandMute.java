package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandMute extends CommandBase {

   public static String filename = "BedwarsBro_Список_замученных.txt";
   public static String command_text = "/bwmute";
   Main main_instance;


   public CommandMute(Main main) {
      command_text = command_text.replace("/", "");
      this.main_instance = main;
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public String func_71517_b() {
      return command_text;
   }

   public String func_71518_a(ICommandSender sender) {
      return "Help mod";
   }

   public void printMuted() {
      ArrayList arr = Main.fileNicknamesManager.readNames(filename);
      StringBuilder var10000;
      MyChatListener var10001;
      if(arr.size() == 0) {
         var10000 = new StringBuilder();
         var10001 = Main.chatListener;
         ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&cЗамученных нету! Добавь через /").append(command_text).append(" add [ник]").toString());
      } else {
         var10000 = new StringBuilder();
         var10001 = Main.chatListener;
         ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&fСписок замученных:").toString());
         Iterator var2 = arr.iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            String command = "/" + command_text + " remove " + s.trim();
            ChatSender.addClickAndHoverText("&8 • &f" + s.trim() + "    &c[удалить]", "&c" + command, command);
         }

         ChatSender.addText("");
      }
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      ChatSender.addText("");

      try {
         if(args.length == 0) {
            this.printMuted();
         } else if(args[0].equals("list")) {
            this.printMuted();
         } else {
            StringBuilder var10000;
            MyChatListener var10001;
            String ex;
            boolean isSuccess;
            MyChatListener var6;
            if(args[0].equals("add") && args.length == 2) {
               ex = args[1].trim();
               isSuccess = Main.fileNicknamesManager.addName(filename, ex);
               if(isSuccess) {
                  this.printMuted();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&aИгрок \"").append(ex).append("\" замучен").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
               } else {
                  this.printMuted();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&cДруг \"").append(ex).append("\" уже замучен").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_REJECT);
               }
            } else if(args[0].equals("remove") && args.length == 2) {
               ex = args[1].trim();
               isSuccess = Main.fileNicknamesManager.removeName(filename, ex);
               if(isSuccess) {
                  this.printMuted();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&cДруг \"").append(ex).append("\" удален").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
               } else {
                  this.printMuted();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&cДруг \"").append(ex).append("\" уже удален").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_REJECT);
               }
            } else {
               this.printMuted();
               var10000 = new StringBuilder();
               var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&f/").append(command_text).append(" &aadd &f[ник] &7- &fзамутить").toString());
               var10000 = new StringBuilder();
               var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&f/").append(command_text).append(" &cremove &f[ник] &7- &fразмутить").toString());
               var10000 = new StringBuilder();
               var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&f/").append(command_text).append(" &blist &7- &fсписок замученных").toString());
               var10000 = new StringBuilder();
               var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_MUTED).append("&fМожно мутить игроков, у которых ник начинается на что-то, используя звездочку. Наприме &f/").append(command_text).append(" add &cARAB_*&f - будут замученны все игроки типо &cARAB_1234 &fи &cARAB_zxc").toString());
            }
         }
      } catch (Exception var5) {
         ChatSender.addText("&cОшибка!");
         var5.printStackTrace();
         return;
      }

      ChatSender.addText("");
   }

}
