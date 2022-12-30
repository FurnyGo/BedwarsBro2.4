package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.google.common.base.Splitter;
import java.util.Iterator;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandRainbowMessage extends CommandBase {

   public static String command_text = "r";
   public static String[] color_codes = new String[]{"c", "6", "e", "a", "b", "9", "d"};
   public static int MAX_MESSAGE_LENGTH = 100;


   public CommandRainbowMessage(String command) {
      command_text = command.replace("/", "");
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
      if(args.length <= 0) {
         ChatSender.addText("Напиши &e/r &aтекст");
      } else {
         String str = "";
         String[] isGlobal = args;
         int text_mode = args.length;

         String generated_message;
         for(int config_replace_character = 0; config_replace_character < text_mode; ++config_replace_character) {
            generated_message = isGlobal[config_replace_character];
            str = str + generated_message + " ";
         }

         str = str.trim();
         if(str.length() > 0) {
            boolean var8 = Main.getConfigBool(Main.CONFIG_MSG.RAINBOW_MESSAGE_GLOBAL);
            if(str.startsWith("!")) {
               var8 = true;
               str = str.substring(1, str.length());
            }

            text_mode = Main.getConfigInt(Main.CONFIG_MSG.RAINBOW_MESSAGE_MODE);
            String[] var9 = Main.getConfigString(Main.CONFIG_MSG.RAINBOW_MESSAGE_REPLACE_CHARS).split("=");
            if(var9.length == 2) {
               str = str.replace(var9[0], var9[1]);
            }

            generated_message = generateRainbowMessage(str, 1, text_mode);
            if(generated_message == null) {
               ChatSender.addText("&cСообщение слишком большое!");
            } else {
               str = "" + generated_message;
               if(Main.shopManager.findItemInHotbar("ыбор лобби") == -1 && var8) {
                  str = "!" + str;
               }

               Minecraft.func_71410_x().field_71439_g.func_71165_d(str);
               System.out.println(str);
            }
         }
      }
   }

   private static void readConfigParams() {
      String color_codes_str = Main.getConfigString(Main.CONFIG_MSG.RAINBOW_MESSAGE_COLORS);
      color_codes = color_codes_str.split(",");
      if(color_codes_str.contains("+")) {
         try {
            String ex = color_codes_str.split(Pattern.quote("+"))[0];
            String additional = color_codes_str.split(Pattern.quote("+"))[1];
            color_codes = new String[ex.length()];

            for(int i = 0; i < ex.length(); ++i) {
               color_codes[i] = ex.charAt(i) + additional;
            }
         } catch (Exception var4) {
            ChatSender.addText("&cОшибка цветов в конфиге! &7(&e/bwbro&7)");
            return;
         }
      } else if(!color_codes_str.contains(",")) {
         color_codes = color_codes_str.split("");
      }

      if(color_codes_str.length() == 0 || color_codes.length <= 0) {
         ChatSender.addText("&cДобавь цвета в конфиге! &7(&e/bwbro&7)");
      }
   }

   public static String generateRainbowMessage(String s, int step, int text_mode) {
      readConfigParams();
      if(step > MAX_MESSAGE_LENGTH) {
         return null;
      } else {
         if(text_mode == -1) {
            text_mode = Main.getConfigInt(Main.CONFIG_MSG.RAINBOW_MESSAGE_MODE);
         }

         s = s.replace("і", "ы");
         String str = "";
         Iterable chunks = Splitter.fixedLength(step).split(s);
         if(text_mode == 1) {
            chunks = Splitter.on(" ").split(s);
         }

         int code_idx = -1;

         String chunk;
         String this_chunk_code;
         for(Iterator var6 = chunks.iterator(); var6.hasNext(); str = str + this_chunk_code + chunk) {
            String i_chunk = (String)var6.next();
            chunk = i_chunk;
            if(text_mode == 1) {
               str = str + "&0 ";
            }

            code_idx = (code_idx + 1) % color_codes.length;
            this_chunk_code = "";
            String[] var10 = color_codes[code_idx].split("");
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String c = var10[var12];
               this_chunk_code = this_chunk_code + "&" + c;
            }

            if(this_chunk_code.contains("n") && step == 1 && i_chunk.contains(" ")) {
               chunk = i_chunk.replace(" ", "&0 " + this_chunk_code);
               code_idx = (code_idx - 1) % color_codes.length;
            }
         }

         if(color_codes.length > 0) {
            str = str + "&" + color_codes[0].charAt(0);
         }

         str = str.trim();
         if(str.length() > MAX_MESSAGE_LENGTH) {
            return generateRainbowMessage(s, step + 1, text_mode);
         } else {
            return str;
         }
      }
   }

}
