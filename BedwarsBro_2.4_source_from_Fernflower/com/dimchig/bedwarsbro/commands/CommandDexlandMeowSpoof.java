package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandDexlandMeowSpoof extends CommandBase {

   public static String command_text = "meowspoof";


   public CommandDexlandMeowSpoof() {
      command_text = command_text.replace("/", "");
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public String func_71517_b() {
      return command_text;
   }

   public String func_71518_a(ICommandSender sender) {
      return "Makes message rainbow setter";
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      String[] cmds = new String[]{"kill", "death", "bed", "join", "win"};
      String av_cmds = "";
      String[] start_idx = cmds;
      int type = cmds.length;

      String local_command;
      for(int nick = 0; nick < type; ++nick) {
         local_command = start_idx[nick];
         av_cmds = av_cmds + "&b" + local_command + "&7/";
      }

      av_cmds = av_cmds.substring(0, av_cmds.length() - 3);
      if(args.length < 2) {
         ChatSender.addText("&dУстановить все 5 сообщений:\n  /" + command_text + " &eник &aсообщение");
         ChatSender.addText("&dУстановить одно сообщение:\n&f  /" + command_text + " " + av_cmds + " &eник &aсообщение\n     &7(можно юзать %player% при &bkill&7)");
         ChatSender.addText("&dЗаставить сказать сообщение:\n&f  /" + command_text + " &cfake&bdeath&7/&cfake&bjoin&7/&cfake&bwin &eник");
      } else {
         byte var19 = 1;
         String var20 = "all";
         String var21 = args[0];
         local_command = "";
         String[] msg = cmds;
         int commands = cmds.length;

         for(int isOnServer = 0; isOnServer < commands; ++isOnServer) {
            String players = msg[isOnServer];
            if(args[0].equals(players)) {
               if(args.length < 3) {
                  return;
               }

               var20 = players;
               var21 = args[1];
               var19 = 2;
            } else if(args[0].contains("fake") && args[0].contains(players)) {
               var20 = "fake" + players;
               local_command = players;
               var21 = args[1];
               var19 = -1;
            }
         }

         MyChatListener var10000;
         String var22;
         if(var20.contains("fake")) {
            ChatSender.addText("Фейкую сообщение &e" + local_command + "&f...");
            var22 = "";
            if(local_command.equals("death")) {
               var22 = "Ахах, " + var21 + " упал в бездну, вы видели?";
            } else if(local_command.equals("join")) {
               var22 = "Всем привет! Победит только одна, сильнейшая команда!";
            } else if(local_command.equals("win")) {
               var22 = "Ребят, вы знали, что Перезагрузка сервера через 10 секунд!";
            } else {
               ChatSender.addText("&cЭто сообщение нельзя сфейковать!\n&cМожно только &bdeath&7, &bjoin&7, &bwin");
            }

            if(var22.length() != 0) {
               var10000 = Main.chatListener;
               MyChatListener.addBedwarsMeowMessageToQuee(var22, false);
            }
         } else {
            var22 = "";

            for(commands = var19; commands < args.length; ++commands) {
               var22 = var22 + args[commands] + " ";
            }

            var22 = var22.trim();
            String[] var23 = new String[]{"setkillmsg", "setdeathmsg", "setbedmsg", "setjoinmsg", "setwinmsg"};
            String[] var24 = cmds;
            int var26 = cmds.length;

            for(int cnt = 0; cnt < var26; ++cnt) {
               String c = var24[cnt];
               if(var20.equals(c)) {
                  var23 = new String[]{"set" + c + "msg"};
               }
            }

            boolean var25 = false;
            Collection var27 = Minecraft.func_71410_x().func_147114_u().func_175106_d();
            boolean var28 = false;
            Iterator var29 = var27.iterator();

            while(var29.hasNext()) {
               NetworkPlayerInfo info = (NetworkPlayerInfo)var29.next();
               if(info != null && info.func_178845_a() != null) {
                  String name = info.func_178845_a().getName();
                  if(name != null && name.equals(var21)) {
                     var25 = true;
                     break;
                  }
               }
            }

            if(!var25) {
               ChatSender.addText("&cИгрок &c\"&e" + var21 + "&c\" &c не найден!");
            } else {
               String[] var30 = var23;
               int var31 = var23.length;

               for(int var32 = 0; var32 < var31; ++var32) {
                  String cmd = var30[var32];
                  String s = var21 + " ." + cmd + " " + var22;
                  var10000 = Main.chatListener;
                  MyChatListener.addBedwarsMeowMessageToQuee(s, false);
               }

            }
         }
      }
   }

}
