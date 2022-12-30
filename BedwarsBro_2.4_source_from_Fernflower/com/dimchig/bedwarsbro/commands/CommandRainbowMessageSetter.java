package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.commands.CommandRainbowMessage;
import com.dimchig.bedwarsbro.hints.BedwarsMeow;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandRainbowMessageSetter extends CommandBase {

   public static String command_text = "rs";
   String randomFilterString = "lno";


   public CommandRainbowMessageSetter() {
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

   public String setRandomPalitra() {
      String[] available_colors = new String[]{"2", "3", "5", "6", "9", "a", "b", "c", "d", "e", "f"};
      Random rnd = new Random();
      byte min_size = 2;
      byte max_size = 5;
      int count = Math.min(min_size + rnd.nextInt(max_size - min_size + 1), max_size);
      ArrayList generated_colors = new ArrayList();

      for(int output_str = 0; output_str < count; ++output_str) {
         String rnd_color = "0";

         do {
            rnd_color = available_colors[rnd.nextInt(available_colors.length)];
         } while(generated_colors.contains(rnd_color));

         generated_colors.add(rnd_color);
      }

      String var12 = "";

      String s;
      for(Iterator var13 = generated_colors.iterator(); var13.hasNext(); var12 = var12 + s) {
         s = (String)var13.next();
      }

      var12 = var12 + "+l";
      String[] var14 = this.randomFilterString.split("");
      int var15 = var14.length;

      for(int var10 = 0; var10 < var15; ++var10) {
         String s1 = var14[var10];
         if(rnd.nextBoolean() && !var12.contains(s1)) {
            var12 = var12 + s1;
         }
      }

      Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS.text).set(var12);
      return var12;
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      String current_palitra = Main.getConfigString(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS);
      String tutorial_colorcodes = "&fЦвета:\n &11 &22 &33 &44 &55 &66 &77 &88 &99 &aa &bb &cc &dd &ee &ff \n&fФорматирвока:\n &fl &7- &f&lжирный&7\n &fn&7 - &f&nподчеркнутый&7\n &fo &7- &f&oкурсив\n &fm &7- &f&mзачеркнутый&7\n&e&l&n&oМожно совмещать&7 (e+lno)";
      if(args.length > 0 && !args[0].equals("help")) {
         String[] available_colors_to_set = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "+"};
         String output_str = "f";
         String arg0 = args[0];
         BedwarsMeow var10000 = Main.bedwarsMeow;
         BedwarsMeow.IS_AUTO_RANDOM_ACTIVE = false;
         String var15;
         if(arg0.equals("random")) {
            output_str = this.setRandomPalitra();
         } else {
            if(arg0.equals("randomstyle") && args.length == 2) {
               this.randomFilterString = args[1];
               ChatSender.addText("Стили установлены!");
               return;
            }

            if(arg0.equals("rainbow")) {
               output_str = "c6eabd+l";
            } else {
               int msg;
               String var17;
               if(arg0.equals("add") && args.length == 2) {
                  output_str = current_palitra;

                  for(msg = 0; msg < args[1].length(); ++msg) {
                     var17 = "" + args[1].charAt(msg);
                     if(!var17.equals("l") && !var17.equals("n") && !var17.equals("o") && !var17.equals("m")) {
                        if(output_str.contains("+")) {
                           output_str = output_str.split(Pattern.quote("+"))[0] + var17 + "+" + output_str.split(Pattern.quote("+"))[1];
                        } else {
                           output_str = output_str + var17;
                        }
                     } else {
                        if(!output_str.contains("+")) {
                           output_str = output_str + "+";
                        }

                        output_str = output_str + var17;
                     }
                  }
               } else if(arg0.equals("remove") && args.length == 2) {
                  output_str = current_palitra.replace(args[1], "");
                  if(output_str.endsWith("+")) {
                     output_str = output_str.substring(0, output_str.length() - 1);
                  }
               } else if(arg0.equals("mirror") && args.length == 1) {
                  String[] var18 = current_palitra.split(Pattern.quote("+"));
                  var17 = var18[0];
                  String var19 = (new StringBuilder(var17)).reverse().toString();
                  output_str = var17 + var19;
                  if(var18.length > 1) {
                     output_str = output_str + "+" + var18[1];
                  }
               } else {
                  boolean var16;
                  if(arg0.equals("global") && args.length == 2) {
                     var16 = false;
                     if(args[1].equals("on")) {
                        var16 = true;
                     }

                     Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_GLOBAL.text).set(var16);
                     ChatSender.addText("Писать в глобальный чат - " + (var16?"&aвключено":"&cвыключено"));
                     return;
                  }

                  if(arg0.equals("autorandom") && args.length == 2) {
                     var16 = false;
                     if(args[1].equals("on")) {
                        var16 = true;
                     }

                     var10000 = Main.bedwarsMeow;
                     BedwarsMeow.IS_AUTO_RANDOM_ACTIVE = var16;
                     ChatSender.addText("Рандомная палитра - " + (var16?"&aвключена":"&cвыключена"));
                     return;
                  }

                  if(arg0.equals("mode")) {
                     output_str = current_palitra;

                     try {
                        msg = Integer.parseInt(args[1]);
                        if(msg != 0 && msg != 1) {
                           ChatSender.addText("&cСтавь только 1 или 0!");
                           return;
                        }

                        Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_MODE.text).set(msg);
                     } catch (Exception var13) {
                        ChatSender.addText("&cСтавь только 1 или 0!");
                        return;
                     }
                  } else if(arg0.equals("team")) {
                     try {
                        MyChatListener var20 = Main.chatListener;
                        CustomScoreboard.TEAM_COLOR var14 = MyChatListener.getEntityTeamColorByTeam(Minecraft.func_71410_x().field_71439_g.func_96124_cp().func_96661_b());
                        if(var14 == CustomScoreboard.TEAM_COLOR.RED) {
                           output_str = "c";
                        } else if(var14 == CustomScoreboard.TEAM_COLOR.YELLOW) {
                           output_str = "e6";
                        } else if(var14 == CustomScoreboard.TEAM_COLOR.GREEN) {
                           output_str = "a";
                        } else if(var14 == CustomScoreboard.TEAM_COLOR.AQUA) {
                           output_str = "b";
                        } else if(var14 == CustomScoreboard.TEAM_COLOR.BLUE) {
                           output_str = "b";
                        } else if(var14 == CustomScoreboard.TEAM_COLOR.PINK) {
                           output_str = "d";
                        } else if(var14 == CustomScoreboard.TEAM_COLOR.GRAY) {
                           output_str = "f7";
                        } else {
                           if(var14 != CustomScoreboard.TEAM_COLOR.WHITE) {
                              ChatSender.addText("&cКоманда не разпознана!");
                              return;
                           }

                           output_str = "f";
                        }

                        output_str = output_str + "+l";
                     } catch (Exception var12) {
                        ChatSender.addText("&cКоманда не разпознана!");
                        return;
                     }
                  } else if(arg0.length() > 0) {
                     var15 = arg0;

                     for(int i = 0; i < var15.length(); ++i) {
                        boolean contains = false;

                        for(int j = 0; j < available_colors_to_set.length; ++j) {
                           if(available_colors_to_set[j].equals("" + var15.charAt(i))) {
                              contains = true;
                              break;
                           }
                        }

                        if(!contains) {
                           ChatSender.addText("&cКод \"&e" + var15.charAt(i) + "&c\" не существует!\n" + tutorial_colorcodes);
                           return;
                        }
                     }

                     output_str = arg0;
                  }
               }
            }
         }

         ChatSender.addText("&7Сгенерированная строка: &f" + output_str + "&7, примеры:");
         Main.clientConfig.get(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS.text).set(output_str);
         CommandRainbowMessage var21 = Main.commandRainbowMessage;
         var15 = CommandRainbowMessage.generateRainbowMessage("Бро, тебе надо больше тренироваться!", 1, -1);
         if(var15 != null) {
            ChatSender.addText(var15);
         }

         var21 = Main.commandRainbowMessage;
         var15 = CommandRainbowMessage.generateRainbowMessage("Унизил лоха", 1, -1);
         if(var15 != null) {
            ChatSender.addText(var15);
         }

         Main.saveConfig();
      } else {
         ChatSender.addText("&aТекущаяя палитра: &f" + current_palitra);
         ChatSender.addText("&e/rs &bпалитра &7- &fустановить палитру &7(&e/rs &bade+ln&7)");
         ChatSender.addText("&e/rs rainbow &7- &cр&6а&eд&aу&bг&dа");
         ChatSender.addText("&e/rs add &aкод &7- &fдобавить что-то");
         ChatSender.addText("&e/rs remove &cкод &7- &fубрать символы из существующего");
         ChatSender.addText("&e/rs mirror &7- &fотзеркалить палитру");
         ChatSender.addText("&e/rs team &7- &fустановить цвет под команду");
         ChatSender.addHoverText("&e/rs mode &7[&b0&7/&b1&7] &7- &fустановить режим&c*", "&e0 &7- &fадаптивная длина\n&e1 &7- &fцвета меняются через каждое слово");
         ChatSender.addText("&e/rs global &7[&bon&7/&boff&7]&7- &fписать всегда в глобальный чат &7(в лобби не будет \"!\"");
         ChatSender.addText("&e/rs random &7- &fрандомная палитра");
         ChatSender.addText("&e/rs randomstyle &7[lnom&7] &7- &fУстановить доступные рандомные форматировки");
         ChatSender.addText("&e/rs autorandom &7[&bon&7/&boff&7]&7- &fпосле каждого сообщения ставить рандомную палитру");
         ChatSender.addHoverText("&aНаведи &a&nсюда&a, чтоб увидеть все цвет-коды", tutorial_colorcodes);
      }
   }

}
