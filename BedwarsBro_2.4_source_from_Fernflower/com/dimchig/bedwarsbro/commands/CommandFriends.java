package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandFriends extends CommandBase {

   public static String filename = "BedwarsBro_Список_друзей.txt";
   public static String command_text = "/bwf";
   Main main_instance;


   public CommandFriends(Main main) {
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
      return "";
   }

   public void printFriends() {
      ArrayList arr = Main.fileNicknamesManager.readNames(filename);
      StringBuilder var10000;
      MyChatListener var10001;
      if(arr.size() == 0) {
         var10000 = new StringBuilder();
         var10001 = Main.chatListener;
         ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&cДрузей нету! Добавь через /").append(command_text).append(" add [ник]").toString());
      } else {
         var10000 = new StringBuilder();
         var10001 = Main.chatListener;
         ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&fСписок друзей &7(удалить друга - &c/").append(command_text).append(" remove [ник]&7)&f:").toString());
         Iterator command = arr.iterator();

         while(command.hasNext()) {
            String s = (String)command.next();
            String command1 = "/party " + s.trim();
            "/" + command_text + " remove " + s.trim();
            ChatSender.addClickAndHoverText("&8 • &f" + s.trim() + "    &e[кинуть пати]", "&e" + command1, command1);
         }

         String command2 = "/" + command_text + " party_all";
         var10000 = new StringBuilder();
         var10001 = Main.chatListener;
         ChatSender.addClickAndHoverText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&e[Кинуть пати ВСЕМ друзьям]").toString(), "&e" + command2, command2);
         ChatSender.addText("");
      }
   }

   public void sendPartyToAll() {
      ArrayList arr = Main.fileNicknamesManager.readNames(filename);

      for(int i = 0; i < arr.size(); ++i) {
         final String name = ((String)arr.get(i)).trim();
         (new Timer()).schedule(new TimerTask() {
            public void run() {
               StringBuilder var10000 = new StringBuilder();
               MyChatListener var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&fКидаю пати &e").append(name).append("...").toString());
               ChatSender.sendText("/party " + name);
            }
         }, (long)(i * 500));
      }

      StringBuilder var10000 = new StringBuilder();
      MyChatListener var10001 = Main.chatListener;
      var10000 = var10000.append(MyChatListener.PREFIX_FRIENDS).append("&aКидаю пати ").append(arr.size()).append(" игрок");
      var10001 = Main.chatListener;
      ChatSender.addText(var10000.append(MyChatListener.getNumberEnding(arr.size(), "у", "ам", "ам")).append("...").toString());
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      ChatSender.addText("");

      try {
         if(args.length == 0) {
            this.printFriends();
         } else if(args[0].equals("list")) {
            this.printFriends();
         } else if(args[0].equals("party_all")) {
            this.sendPartyToAll();
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
                  this.printFriends();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&aДруг \"").append(ex).append("\" добавлен").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
               } else {
                  this.printFriends();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&cДруг \"").append(ex).append("\" уже добавлен").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_REJECT);
               }
            } else if(args[0].equals("remove") && args.length == 2) {
               ex = args[1].trim();
               isSuccess = Main.fileNicknamesManager.removeName(filename, ex);
               if(isSuccess) {
                  this.printFriends();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&cДруг \"").append(ex).append("\" удален").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
               } else {
                  this.printFriends();
                  var10000 = new StringBuilder();
                  var10001 = Main.chatListener;
                  ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&cДруг \"").append(ex).append("\" не найден").toString());
                  var6 = Main.chatListener;
                  var6 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_REJECT);
               }
            } else {
               this.printFriends();
               var10000 = new StringBuilder();
               var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&f/").append(command_text).append(" &aadd &f[ник] &7- &fдобавить друга").toString());
               var10000 = new StringBuilder();
               var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&f/").append(command_text).append(" &cremove &f[ник] &7- &fудалить друга").toString());
               var10000 = new StringBuilder();
               var10001 = Main.chatListener;
               ChatSender.addText(var10000.append(MyChatListener.PREFIX_FRIENDS).append("&f/").append(command_text).append(" &blist &7- &fсписок друзей").toString());
            }
         }
      } catch (Exception var5) {
         ChatSender.addText("&cОшибка!");
         var5.printStackTrace();
      }

      ChatSender.addText("");
   }

}
