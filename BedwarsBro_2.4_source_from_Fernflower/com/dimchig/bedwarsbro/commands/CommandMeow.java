package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.BedwarsMeow;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandMeow extends CommandBase {

   public static String command_text = "/meow";
   Main main_instance;


   public CommandMeow(Main main) {
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
      return "Meow mod";
   }

   void printHelpInfo() {
      String prefix = MyChatListener.PREFIX_BEDWARS_MEOW;
      ChatSender.addText("");
      ChatSender.addText(prefix + "&fПомощь по моду &f(Нажимай на сообщения)&f:");

      String starting;
      try {
         starting = Minecraft.func_71410_x().field_71412_D.getCanonicalPath() + "\\" + BedwarsMeow.filename;

         try {
            ChatSender.addHoverFileText("&7• &eДобавить/Изменить свои сообщения", "&eНажми&f, чтоб открыть &8" + starting, starting);
         } catch (IOException var8) {
            ChatSender.addClickSuggestAndHoverText("&7• &eДобавить/Изменить свои сообщения", "&eНажми&f, чтоб скопировать путь", starting);
         }
      } catch (Exception var9) {
         ChatSender.addText("&7• &cНе удалось найти файл с сообщениями");
      }

      ChatSender.addClickText("&7• &bОбновить сообщения из файла", "/" + command_text + " update");
      ChatSender.addHoverText("&7• &cВосстановить файл к заводским", "Сбросит файл как при устоновке мода\n&fНапиши сам в чат эту команду:\n\n&c/" + command_text + " reset");
      ChatSender.addHoverText("&7• &6Убрать сообщения с матами", "Уберет сообщения с плохими словами\n&fНапиши сам в чат эту команду:\n\n&6/" + command_text + " remove_bad_words");
      ChatSender.addText("");
      starting = "/" + command_text + " print ";
      ChatSender.addClickText("&7• &fСообщения после &cкила", starting + "kill");
      ChatSender.addClickText("&7• &fСообщения после &eфинального кила", starting + "final_kill");
      ChatSender.addClickText("&7• &fСообщения после &6смерти", starting + "death");
      ChatSender.addClickText("&7• &fСообщения после &cпадении в бездну", starting + "death_void");
      ChatSender.addClickText("&7• &fСообщения после &aкровати &7(одиночные)", starting + "bed_single");
      ChatSender.addClickText("&7• &fСообщения после &aкровати &7(командные)", starting + "bed_multi");
      ChatSender.addClickText("&7• &fСообщения после &aкровати &7(тебе сломали)", starting + "bed_own");
      ChatSender.addClickText("&7• &fСообщения в &bначале игры", starting + "game_start");
      ChatSender.addClickText("&7• &fСообщения при &dпобеде", starting + "win");
      ChatSender.addText("");
      boolean is_mod_active = Main.getConfigBool(Main.CONFIG_MSG.BEDWARS_MEOW);
      String s_toggle_mod = "&cВыключен";
      String s_toggle_mod_hover = "&fНажми, чтоб &aвключить";
      if(is_mod_active) {
         s_toggle_mod = "&aВключен";
         s_toggle_mod_hover = "&fНажми, чтоб &cвыключить";
      }

      String s_toggle_mod_colors = "&cНе используются";
      String s_toggle_mod_colors_hover = "&fНажми, чтоб &aиспользовать &fцветовую палитру &e/rs";
      if(Main.getConfigBool(Main.CONFIG_MSG.BEDWARS_MEOW_WITH_COLORS)) {
         s_toggle_mod_colors = "&aИспользуются";
         s_toggle_mod_colors_hover = "&fНажми, чтоб &cне использовать &fцветовую палитру &e/rs";
      }

      ChatSender.addClickAndHoverText("&7• &fМод &8▸ " + s_toggle_mod, s_toggle_mod_hover, "/" + command_text + " toggle");
      if(is_mod_active) {
         ChatSender.addClickAndHoverText("&7• &fЦвета &8▸ " + s_toggle_mod_colors + " &7(только для &cд&eо&aн&bа&9т&dе&cр&6о&eв&f, настрой командой &e/rs&7)", s_toggle_mod_colors_hover, "/" + command_text + " toggleColors");
      }

   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      String prefix = MyChatListener.PREFIX_BEDWARS_MEOW;
      if(args.length == 0) {
         this.printHelpInfo();
      } else {
         if(args.length == 2 && args[0].trim().equals("print")) {
            String cnt1 = args[1].trim();
            Main.bedwarsMeow.IS_USE_COLORS = true;
            if(cnt1.equals("kill")) {
               ChatSender.addText(prefix + "Сообщения после &bКИЛА:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.KILL));
            } else if(cnt1.equals("final_kill")) {
               ChatSender.addText(prefix + "Сообщения после &bФИНАЛЬНОГО КИЛА:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.FINAL_KILL));
            } else if(cnt1.equals("death")) {
               ChatSender.addText(prefix + "Сообщения после &bСМЕРТИ:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.DEATH));
            } else if(cnt1.equals("death_void")) {
               ChatSender.addText(prefix + "Сообщения после &bПАДЕНИЯ В БЕЗДНУ:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.DEATH_VOID));
            } else if(cnt1.equals("bed_single")) {
               ChatSender.addText(prefix + "Сообщения про &bКРОВАТЬ &e(одиночный режим):&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.BED_SINGLE));
            } else if(cnt1.equals("bed_multi")) {
               ChatSender.addText(prefix + "Сообщения про &bКРОВАТЬ &e(командный режим):&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.BED_MULTI));
            } else if(cnt1.equals("bed_own")) {
               ChatSender.addText(prefix + "Сообщения про &bКРОВАТЬ &e(тебе сломали):&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.BED_OWN));
            } else if(cnt1.equals("win")) {
               ChatSender.addText(prefix + "Сообщения после &bВЫЙГРАША:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.WIN));
            } else if(cnt1.equals("game_start")) {
               ChatSender.addText(prefix + "Сообщения в &bНАЧАЛЕ ИГРЫ:&f\n" + Main.bedwarsMeow.getMeows(BedwarsMeow.MsgCase.GAME_START));
            }

            Main.bedwarsMeow.updateBooleans();
         } else {
            BedwarsMeow var10000;
            MyChatListener var10001;
            int cnt;
            StringBuilder var6;
            if(args[0].trim().equals("update")) {
               Main.bedwarsMeow.readFile();
               var10000 = Main.bedwarsMeow;
               if(BedwarsMeow.meowMessages != null) {
                  var10000 = Main.bedwarsMeow;
                  cnt = BedwarsMeow.meowMessages.size();
                  var6 = (new StringBuilder()).append(prefix).append("&bОбновлено &l").append(cnt).append("&b сообщени");
                  var10001 = Main.chatListener;
                  ChatSender.addText(var6.append(MyChatListener.getNumberEnding(cnt, "е", "я", "й")).append("!").toString());
                  MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
               }
            } else if(args[0].trim().equals("toggle")) {
               Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW.text).set(!Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW.text).getBoolean());
               this.printHelpInfo();
            } else if(args[0].trim().equals("toggleColors")) {
               Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW_WITH_COLORS.text).set(!Main.clientConfig.get(Main.CONFIG_MSG.BEDWARS_MEOW_WITH_COLORS.text).getBoolean());
               this.printHelpInfo();
            } else if(args[0].trim().equals("reset")) {
               Main.bedwarsMeow.initMeowMessages();
               Main.bedwarsMeow.readFile();
               this.printHelpInfo();
               var10000 = Main.bedwarsMeow;
               if(BedwarsMeow.meowMessages != null) {
                  var10000 = Main.bedwarsMeow;
                  cnt = BedwarsMeow.meowMessages.size();
                  var6 = (new StringBuilder()).append(prefix).append("&cФайл восстановлен&7, &bсчитано &l").append(cnt).append("&b сообщени");
                  var10001 = Main.chatListener;
                  ChatSender.addText(var6.append(MyChatListener.getNumberEnding(cnt, "е", "я", "й")).append("!").toString());
                  MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
               }
            } else {
               if(!args[0].trim().equals("remove_bad_words")) {
                  this.printHelpInfo();
                  return;
               }

               Main.bedwarsMeow.removeMeessagesWithBadWords();
               this.printHelpInfo();
               var10000 = Main.bedwarsMeow;
               if(BedwarsMeow.meowMessages != null) {
                  var10000 = Main.bedwarsMeow;
                  cnt = BedwarsMeow.meowMessages.size();
                  var6 = (new StringBuilder()).append(prefix).append("&6Файл обновлен&7, &bсчитано &l").append(cnt).append("&b сообщени");
                  var10001 = Main.chatListener;
                  ChatSender.addText(var6.append(MyChatListener.getNumberEnding(cnt, "е", "я", "й")).append("!").toString());
                  MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
               }
            }
         }

         Main var7 = this.main_instance;
         Main.saveConfig();
         var7 = this.main_instance;
         Main.bedwarsMeow.updateBooleans();
      }
   }

}
