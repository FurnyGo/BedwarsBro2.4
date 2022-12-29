// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import java.util.Iterator;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import com.dimchig.bedwarsbro.ColorCodesManager;
import java.util.regex.Pattern;
import com.dimchig.bedwarsbro.FileManager;
import java.util.ArrayList;
import net.minecraft.command.ICommandSender;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.command.CommandBase;

public class CommandHistoryShop extends CommandBase
{
    public static String command_text;
    Main main_instance;
    
    public CommandHistoryShop(final Main main) {
        CommandHistoryShop.command_text = CommandHistoryShop.command_text.replace("/", "");
        this.main_instance = main;
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandHistoryShop.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Help mod";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        String str = "";
        str += "&8<===============================================>\n";
        final ArrayList<MyItem> items = new ArrayList<MyItem>();
        try {
            final FileManager fileManager = Main.fileManager;
            final String text = FileManager.readFile("resourceBedwarslog.txt");
            final String[] games = text.split(Pattern.quote("========"));
            int games_count = 0;
            if (games != null && games.length != 0) {
                for (final String game : games) {
                    final String[] log = game.split(Pattern.quote(";&;"));
                    if (log != null) {
                        if (log.length != 0) {
                            boolean isGameEmpty = true;
                            for (final String item : log) {
                                final String s = item.replace(Pattern.quote("\n"), "").replace("\r", "").replace("\n", "");
                                if (s.length() > 3) {
                                    if (!s.contains("\ufffd")) {
                                        final String[] split = s.split(";");
                                        if (split.length == 2) {
                                            try {
                                                final String name = ColorCodesManager.removeColorCodes(split[0].trim());
                                                final int count = Integer.parseInt(split[1].trim());
                                                boolean isFound = false;
                                                for (final MyItem i : items) {
                                                    if (i.name.equals(name)) {
                                                        isFound = true;
                                                        final MyItem myItem = i;
                                                        myItem.total_amount += count;
                                                        final MyItem myItem2 = i;
                                                        ++myItem2.buy_amount;
                                                        break;
                                                    }
                                                }
                                                if (!isFound) {
                                                    items.add(new MyItem(name, count, 1));
                                                }
                                                isGameEmpty = false;
                                            }
                                            catch (Exception ex2) {}
                                        }
                                    }
                                }
                            }
                            if (!isGameEmpty) {
                                ++games_count;
                            }
                        }
                    }
                }
            }
            str = str + "\n                      &f&l\u0412\u0441\u0435\u0433\u043e \u0441\u044b\u0433\u0440\u0430\u043d\u043e &a&l" + games_count + " &f&l\u0438\u0433\u0440\n\n";
            str += "                    &f&l\u0422\u043e\u043f &e&l\u043f\u043e\u043f\u0443\u043b\u044f\u0440\u043d\u044b\u0445 &f&l\u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432:\n\n";
            Collections.sort(items, new Comparator<MyItem>() {
                @Override
                public int compare(final MyItem i1, final MyItem i2) {
                    return -Integer.compare(i1.buy_amount, i2.buy_amount);
                }
            });
            int cnt = 0;
            for (final MyItem j : items) {
                ++cnt;
                String total_amount = "" + j.total_amount + " &7\u0448\u0442";
                if (j.total_amount > 64) {
                    final int x = (int)(j.total_amount / 64.0f);
                    final StringBuilder append = new StringBuilder().append(x).append(" &7\u0441\u0442\u0430\u043a");
                    final MyChatListener chatListener = Main.chatListener;
                    total_amount = append.append(MyChatListener.getNumberEnding(x, "", "\u0430", "\u043e\u0432")).toString();
                }
                final String percentage = "" + (int)(j.buy_amount * 100.0f / games_count) + "%";
                String name2 = j.name;
                if (name2.equals("Wool")) {
                    name2 = "\u0428\u0435\u0440\u0441\u0442\u044c";
                }
                else if (name2.equals("Stick")) {
                    name2 = "&c\u041f&6\u0430&e\u043b&a\u043a&b\u0430";
                }
                else if (name2.equals("Arrow")) {
                    name2 = "\u0421\u0442\u0440\u0435\u043b\u044b";
                }
                else if (name2.equals("Stone Sword")) {
                    name2 = "\u041c\u0435\u0447 \u043a\u0430\u043c\u0435\u043d\u043d\u044b\u0439";
                }
                else if (name2.equals("Golden Apple")) {
                    name2 = "\u0417\u043e\u043b\u043e\u0442\u043e\u0435 &e\u044f\u0431\u043b\u043e\u043a\u043e";
                }
                else if (name2.equals("Fire Charge")) {
                    name2 = "&6\u0424\u0430\u0435\u0440\u0431\u043e\u043b";
                }
                else if (name2.equals("TNT")) {
                    name2 = "&c\u0414\u0438\u043d\u0430\u043c\u0438\u0442";
                }
                else if (name2.equals("\u0416\u0435\u043b\u0435\u0437\u043d\u0430\u044f \u0431\u0440\u043e\u043d\u044f")) {
                    name2 = "\u0411\u0440\u043e\u043d\u044f \u0436\u0435\u043b\u0435\u0437\u043d\u0430\u044f";
                }
                else if (name2.equals("Iron Sword")) {
                    name2 = "\u041c\u0435\u0447 \u0436\u0435\u043b\u0435\u0437\u043d\u044b\u0439";
                }
                else if (name2.equals("Wooden Pickaxe")) {
                    name2 = "\u041a\u0438\u0440\u043a\u0430 \u0434\u0435\u0440\u0435\u0432\u044f\u043d\u043d\u0430\u044f";
                }
                else if (name2.equals("Wooden Axe")) {
                    name2 = "\u0422\u043e\u043f\u043e\u0440 \u0434\u0435\u0440\u0435\u0432\u044f\u043d\u043d\u044b\u0439";
                }
                else if (name2.equals("Shears")) {
                    name2 = "\u041d\u043e\u0436\u043d\u0438\u0446\u044b";
                }
                else if (name2.equals("Bow")) {
                    name2 = "&6\u041b\u0443\u043a";
                }
                else if (name2.equals("\u0417\u0435\u043b\u044c\u0435 \u0441\u0438\u043b\u044b")) {
                    name2 = "&4\u0417\u0435\u043b\u044c\u0435 \u0441\u0438\u043b\u044b";
                }
                else if (name2.equals("End Stone")) {
                    name2 = "\u042d\u043d\u0434\u0435\u0440\u043d\u044f\u043a";
                }
                else if (name2.equals("Stone Pickaxe")) {
                    name2 = "\u041a\u0438\u0440\u043a\u0430 \u043a\u0430\u043c\u0435\u043d\u043d\u0430\u044f";
                }
                else if (name2.equals("Ender Pearl")) {
                    name2 = "&9\u042d\u043d\u0434\u0435\u0440 \u043f\u0435\u0440\u043b";
                }
                else if (name2.equals("Water Bucket")) {
                    name2 = "\u0412\u0435\u0434\u0440\u043e \u0432\u043e\u0434\u044b";
                }
                else if (name2.equals("Stone Axe")) {
                    name2 = "\u0422\u043e\u043f\u043e\u0440 \u043a\u0430\u043c\u0435\u043d\u043d\u044b\u0439";
                }
                else if (name2.equals("\u0417\u0435\u043b\u044c\u0435 \u043f\u0440\u044b\u0433\u0443\u0447\u0435\u0441\u0442\u0438")) {
                    name2 = "\u0417\u0435\u043b\u044c\u0435 \u043f\u0440\u044b\u0433\u0443\u0447\u0435\u0441\u0442\u0438";
                }
                else if (name2.equals("Iron Pickaxe")) {
                    name2 = "\u041a\u0438\u0440\u043a\u0430 \u0436\u0435\u043b\u0435\u0437\u043d\u0430\u044f";
                }
                else if (name2.equals("\u0410\u043b\u043c\u0430\u0437\u043d\u0430\u044f \u0431\u0440\u043e\u043d\u044f")) {
                    name2 = "\u0411\u0440\u043e\u043d\u044f \u0430\u043b\u043c\u0430\u0437\u043d\u0430\u044f";
                }
                else if (name2.equals("Diamond Pickaxe")) {
                    name2 = "\u041a\u0438\u0440\u043a\u0430 \u0430\u043b\u043c\u0430\u0437\u043d\u0430\u044f";
                }
                else if (name2.equals("Wooden Planks")) {
                    name2 = "\u0414\u0435\u0440\u0435\u0432\u043e";
                }
                else if (name2.equals("\u0421\u043f\u0430\u0432\u043d\u0435\u0440 \u043c\u043e\u0441\u0442\u0430")) {
                    name2 = "\u0421\u043f\u0430\u0432\u043d\u0435\u0440 \u043c\u043e\u0441\u0442\u0430";
                }
                else if (name2.equals("Stained Clay")) {
                    name2 = "\u0413\u043b\u0438\u043d\u0430";
                }
                else if (name2.equals("Obsidian")) {
                    name2 = "&9\u041e\u0431\u0441\u0438\u0434\u0438\u0430\u043d";
                }
                else if (name2.equals("Iron Axe")) {
                    name2 = "\u0416\u0435\u043b\u0435\u0437\u043d\u044b\u0439 \u0442\u043e\u043f\u043e\u0440";
                }
                else if (name2.equals("Diamond Sword")) {
                    name2 = "\u041c\u0435\u0447 \u0430\u043b\u043c\u0430\u0437\u043d\u044b\u0439";
                }
                else if (name2.equals("Potion")) {
                    name2 = "\u0417\u0435\u043b\u044c\u0435 \u0440\u0435\u0433\u0435\u043d\u0430";
                }
                else if (name2.equals("Diamond Axe")) {
                    name2 = "\u0422\u043e\u043f\u043e\u0440 \u0430\u043b\u043c\u0430\u0437\u043d\u044b\u0439";
                }
                else if (name2.equals("\u041a\u043e\u043b\u044c\u0447\u0443\u0436\u043d\u0430\u044f \u0431\u0440\u043e\u043d\u044f")) {
                    name2 = "\u0411\u0440\u043e\u043d\u044f \u043a\u043e\u043b\u044c\u0447\u0443\u0436\u043d\u0430\u044f";
                }
                else if (name2.equals("Ladder")) {
                    name2 = "\u041b\u0435\u0441\u0442\u043d\u0438\u0446\u044b";
                }
                else if (name2.equals("Glass")) {
                    name2 = "\u0421\u0442\u0435\u043b\u043a\u043e";
                }
                else if (name2.equals("tile.sponge.name")) {
                    name2 = "\u0413\u0443\u0431\u043a\u0430";
                }
                str = str + "&7" + cnt + ". " + ((cnt <= 3) ? "&e&l" : "&f") + (name2.contains("\u0430\u043b\u043c\u0430\u0437") ? "&b" : "") + name2 + " &8(&7\u043a\u0443\u043f\u043b\u0435\u043d\u043e &a" + j.buy_amount + " &7\u0440\u0430\u0437, &b" + total_amount + ", \u0447\u0430\u0441\u0442\u043e\u0442\u0430 &c" + percentage + "&8)\n";
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        str += "&8<===============================================>";
        ChatSender.addText(str);
    }
    
    static {
        CommandHistoryShop.command_text = "/bwbroshop";
    }
    
    private class MyItem
    {
        public String name;
        public int total_amount;
        public int buy_amount;
        public ArrayList<MyItem> game_log;
        
        public MyItem(final String name, final int total_amount, final int buy_amount) {
            this.game_log = new ArrayList<MyItem>();
            this.name = name;
            this.total_amount = total_amount;
            this.buy_amount = buy_amount;
            this.game_log = new ArrayList<MyItem>();
        }
    }
}
