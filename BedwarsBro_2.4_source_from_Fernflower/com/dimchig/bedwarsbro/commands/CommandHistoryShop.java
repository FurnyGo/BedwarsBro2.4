package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.FileManager;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Pattern;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandHistoryShop extends CommandBase {

   public static String command_text = "/bwbroshop";
   Main main_instance;


   public CommandHistoryShop(Main main) {
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

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      String str = "";
      str = str + "&8<===============================================>\n";
      ArrayList items = new ArrayList();

      try {
         FileManager var10000 = Main.fileManager;
         String ex = FileManager.readFile("resourceBedwarslog.txt");
         String[] games = ex.split(Pattern.quote("========"));
         int games_count = 0;
         String total_amount;
         if(games != null && games.length != 0) {
            String[] cnt = games;
            int var9 = games.length;

            for(int i = 0; i < var9; ++i) {
               total_amount = cnt[i];
               String[] percentage = total_amount.split(Pattern.quote(";&;"));
               if(percentage != null && percentage.length != 0) {
                  boolean name = true;
                  String[] var14 = percentage;
                  int var15 = percentage.length;

                  for(int var16 = 0; var16 < var15; ++var16) {
                     String item = var14[var16];
                     String s = item.replace(Pattern.quote("\n"), "").replace("\r", "").replace("\n", "");
                     if(s.length() > 3 && !s.contains("�")) {
                        String[] split = s.split(";");
                        if(split.length == 2) {
                           try {
                              String name1 = ColorCodesManager.removeColorCodes(split[0].trim());
                              int count = Integer.parseInt(split[1].trim());
                              boolean isFound = false;
                              Iterator var23 = items.iterator();

                              while(var23.hasNext()) {
                                 CommandHistoryShop.MyItem i1 = (CommandHistoryShop.MyItem)var23.next();
                                 if(i1.name.equals(name1)) {
                                    isFound = true;
                                    i1.total_amount += count;
                                    ++i1.buy_amount;
                                    break;
                                 }
                              }

                              if(!isFound) {
                                 items.add(new CommandHistoryShop.MyItem(name1, count, 1));
                              }

                              name = false;
                           } catch (Exception var25) {
                              ;
                           }
                        }
                     }
                  }

                  if(!name) {
                     ++games_count;
                  }
               }
            }
         }

         str = str + "\n                      &f&lВсего сыграно &a&l" + games_count + " &f&lигр\n\n";
         str = str + "                    &f&lТоп &e&lпопулярных &f&lпредметов:\n\n";
         Collections.sort(items, new Comparator() {
            public int compare(CommandHistoryShop.MyItem i1, CommandHistoryShop.MyItem i2) {
               return -Integer.compare(i1.buy_amount, i2.buy_amount);
            }
         });
         int var27 = 0;

         CommandHistoryShop.MyItem var29;
         String var31;
         String var32;
         for(Iterator var28 = items.iterator(); var28.hasNext(); str = str + "&7" + var27 + ". " + (var27 <= 3?"&e&l":"&f") + (var32.contains("алмаз")?"&b":"") + var32 + " &8(&7куплено &a" + var29.buy_amount + " &7раз, &b" + total_amount + ", частота &c" + var31 + "&8)\n") {
            var29 = (CommandHistoryShop.MyItem)var28.next();
            ++var27;
            total_amount = "" + var29.total_amount + " &7шт";
            if(var29.total_amount > 64) {
               int var30 = (int)((float)var29.total_amount / 64.0F);
               StringBuilder var33 = (new StringBuilder()).append(var30).append(" &7стак");
               MyChatListener var10001 = Main.chatListener;
               total_amount = var33.append(MyChatListener.getNumberEnding(var30, "", "а", "ов")).toString();
            }

            var31 = "" + (int)((float)var29.buy_amount * 100.0F / (float)games_count) + "%";
            var32 = var29.name;
            if(var32.equals("Wool")) {
               var32 = "Шерсть";
            } else if(var32.equals("Stick")) {
               var32 = "&cП&6а&eл&aк&bа";
            } else if(var32.equals("Arrow")) {
               var32 = "Стрелы";
            } else if(var32.equals("Stone Sword")) {
               var32 = "Меч каменный";
            } else if(var32.equals("Golden Apple")) {
               var32 = "Золотое &eяблоко";
            } else if(var32.equals("Fire Charge")) {
               var32 = "&6Фаербол";
            } else if(var32.equals("TNT")) {
               var32 = "&cДинамит";
            } else if(var32.equals("Железная броня")) {
               var32 = "Броня железная";
            } else if(var32.equals("Iron Sword")) {
               var32 = "Меч железный";
            } else if(var32.equals("Wooden Pickaxe")) {
               var32 = "Кирка деревянная";
            } else if(var32.equals("Wooden Axe")) {
               var32 = "Топор деревянный";
            } else if(var32.equals("Shears")) {
               var32 = "Ножницы";
            } else if(var32.equals("Bow")) {
               var32 = "&6Лук";
            } else if(var32.equals("Зелье силы")) {
               var32 = "&4Зелье силы";
            } else if(var32.equals("End Stone")) {
               var32 = "Эндерняк";
            } else if(var32.equals("Stone Pickaxe")) {
               var32 = "Кирка каменная";
            } else if(var32.equals("Ender Pearl")) {
               var32 = "&9Эндер перл";
            } else if(var32.equals("Water Bucket")) {
               var32 = "Ведро воды";
            } else if(var32.equals("Stone Axe")) {
               var32 = "Топор каменный";
            } else if(var32.equals("Зелье прыгучести")) {
               var32 = "Зелье прыгучести";
            } else if(var32.equals("Iron Pickaxe")) {
               var32 = "Кирка железная";
            } else if(var32.equals("Алмазная броня")) {
               var32 = "Броня алмазная";
            } else if(var32.equals("Diamond Pickaxe")) {
               var32 = "Кирка алмазная";
            } else if(var32.equals("Wooden Planks")) {
               var32 = "Дерево";
            } else if(var32.equals("Спавнер моста")) {
               var32 = "Спавнер моста";
            } else if(var32.equals("Stained Clay")) {
               var32 = "Глина";
            } else if(var32.equals("Obsidian")) {
               var32 = "&9Обсидиан";
            } else if(var32.equals("Iron Axe")) {
               var32 = "Железный топор";
            } else if(var32.equals("Diamond Sword")) {
               var32 = "Меч алмазный";
            } else if(var32.equals("Potion")) {
               var32 = "Зелье регена";
            } else if(var32.equals("Diamond Axe")) {
               var32 = "Топор алмазный";
            } else if(var32.equals("Кольчужная броня")) {
               var32 = "Броня кольчужная";
            } else if(var32.equals("Ladder")) {
               var32 = "Лестницы";
            } else if(var32.equals("Glass")) {
               var32 = "Стелко";
            } else if(var32.equals("tile.sponge.name")) {
               var32 = "Губка";
            }
         }
      } catch (Exception var26) {
         var26.printStackTrace();
      }

      str = str + "&8<===============================================>";
      ChatSender.addText(str);
   }


   private class MyItem {

      public String name;
      public int total_amount;
      public int buy_amount;
      public ArrayList game_log = new ArrayList();


      public MyItem(String name, int total_amount, int buy_amount) {
         this.name = name;
         this.total_amount = total_amount;
         this.buy_amount = buy_amount;
         this.game_log = new ArrayList();
      }
   }
}
