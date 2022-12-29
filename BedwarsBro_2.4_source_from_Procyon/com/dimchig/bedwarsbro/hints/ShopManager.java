// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.ColorCodesManager;
import org.lwjgl.input.Mouse;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.client.gui.inventory.GuiChest;
import com.dimchig.bedwarsbro.Main;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

public class ShopManager
{
    Minecraft mc;
    private KeyBinding key_rclick;
    private KeyBinding key_lclick;
    private int unpress_counter;
    private boolean mouseFlag;
    private ArrayList<MyShopItem> shopItems;
    private int BUY_TICK_RATE;
    private int BUY_TICK_RATE_COUNTER;
    private static Robot robot;
    private String[] favourite_maps;
    public static boolean isShopOpenedFlag;
    public static long time_last_teams_closen;
    
    public ShopManager() {
        this.mouseFlag = false;
        this.shopItems = new ArrayList<MyShopItem>();
        this.BUY_TICK_RATE = 5;
        this.BUY_TICK_RATE_COUNTER = 0;
        this.favourite_maps = new String[0];
        this.mc = Minecraft.func_71410_x();
        this.key_rclick = this.mc.field_71474_y.field_74313_G;
        this.key_lclick = this.mc.field_71474_y.field_74312_F;
        this.shopItems = new ArrayList<MyShopItem>();
        this.initShopItems();
        this.initRobot();
    }
    
    void initRobot() {
        try {
            ShopManager.robot = new Robot();
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
    }
    
    public void updateBooleans() {
        final String[] split = Main.getConfigString(Main.CONFIG_MSG.MAP_AUTO_SELECTER).split(",");
        final ArrayList<String> arr = new ArrayList<String>();
        final ArrayList<String> all_maps = new ArrayList<String>();
        for (final String s : split) {
            final String map = s.trim();
            if (map.length() >= 3) {
                all_maps.add(map);
            }
        }
        this.favourite_maps = new String[all_maps.size()];
        for (int i = 0; i < this.favourite_maps.length; ++i) {
            this.favourite_maps[i] = all_maps.get(i);
        }
    }
    
    void initShopItems() {
        this.shopItems = new ArrayList<MyShopItem>();
        final String category_quickbuy = "\u0411\u044b\u0441\u0442\u0440\u044b\u0435 \u043f\u043e\u043a\u0443\u043f\u043a\u0438";
        final String category_blocks = "\u0411\u043b\u043e\u043a\u0438";
        final String category_swords = "\u041c\u0435\u0447\u0438";
        final String category_armour = "\u0411\u0440\u043e\u043d\u044f";
        final String category_tools = "\u0418\u043d\u0441\u0442\u0440\u0443\u043c\u0435\u043d\u0442\u044b";
        final String category_bows = "\u041b\u0443\u043a\u0438";
        final String category_potions = "\u0417\u0435\u043b\u044c\u044f";
        final String category_other = "\u0420\u0430\u0437\u043d\u043e\u0435";
        final String category_trackers = "\u0422\u0440\u0435\u043a\u0435\u0440\u044b";
    }
    
    public void scan(final boolean isBetterShopActive) {
        final EntityPlayerSP player = this.mc.field_71439_g;
        if (this.mc.field_71439_g.field_71071_by == null || this.mc.field_71439_g.field_71071_by.field_70462_a == null) {
            return;
        }
        final int idx1 = this.findItemInGui("\u0411\u044b\u0441\u0442\u0440\u044b\u0435 \u043f\u043e\u043a\u0443\u043f\u043a\u0438", true);
        final int idx2 = this.findItemInHotbar("\u0411\u044b\u0441\u0442\u0440\u044b\u0439 \u0441\u0442\u0430\u0440\u0442");
        if (this.findItemInGui("item.skull", true) != -1 && this.findItemInHotbar("\u041d\u0430\u0431\u043b\u044e\u0434\u0435\u043d\u0438\u0435 \u0437\u0430") != -1) {
            if (this.mc.field_71462_r == null) {
                return;
            }
            if (!(this.mc.field_71462_r instanceof GuiChest)) {
                return;
            }
            final GuiChest chest = (GuiChest)this.mc.field_71462_r;
            if (chest == null) {
                return;
            }
            final List<Slot> chest_slots = (List<Slot>)chest.field_147002_h.field_75151_b;
            if (chest_slots == null || chest_slots.size() == 0) {
                return;
            }
            for (final Slot slot : chest_slots) {
                if (slot != null && slot.func_75211_c() != null) {
                    if (slot.func_75211_c().func_82833_r() == null) {
                        continue;
                    }
                    final List<String> descriptions = (List<String>)slot.func_75211_c().func_82840_a((EntityPlayer)player, false);
                    if (descriptions == null) {
                        continue;
                    }
                    if (descriptions.size() < 2) {
                        continue;
                    }
                    final String name = descriptions.get(0);
                    if (name.length() < 5) {
                        continue;
                    }
                    final CustomScoreboard customScoreboard = Main.customScoreboard;
                    final CustomScoreboard.TEAM_COLOR team_color = CustomScoreboard.getTeamColorByCode("&" + name.charAt(3));
                    final ItemStack is = slot.func_75211_c();
                    is.func_150996_a(Item.func_150898_a(Blocks.field_150325_L));
                    int meta = 0;
                    if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
                        meta = 14;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
                        meta = 4;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
                        meta = 5;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
                        meta = 3;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
                        meta = 11;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
                        meta = 6;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
                        meta = 0;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
                        meta = 8;
                    }
                    is.func_77964_b(meta);
                }
            }
        }
        if (this.findItemInGui("tile.stainedGlass", false) != -1 && this.findItemInGui("item.bed", false) != -1 && this.findItemInGui("item.arrow", false) != -1) {
            if (this.mc.field_71462_r == null) {
                return;
            }
            if (!(this.mc.field_71462_r instanceof GuiChest)) {
                return;
            }
            final GuiChest chest = (GuiChest)this.mc.field_71462_r;
            if (chest == null) {
                return;
            }
            final List<Slot> chest_slots = (List<Slot>)chest.field_147002_h.field_75151_b;
            if (chest_slots == null || chest_slots.size() == 0) {
                return;
            }
            if (this.favourite_maps == null || this.favourite_maps.length == 0) {
                return;
            }
            for (final Slot slot : chest_slots) {
                if (slot != null && slot.func_75211_c() != null && slot.func_75211_c().func_82833_r() != null) {
                    if (slot.field_75222_d > 34) {
                        continue;
                    }
                    final String slot_name = slot.func_75211_c().func_82833_r();
                    if (slot_name.contains("\u041d\u0435\u0442 \u0434\u043e\u0441\u0442\u0443\u043f\u043d\u043e\u0439")) {
                        slot.func_75215_d((ItemStack)null);
                    }
                    else {
                        if (slot.field_75222_d > 34) {
                            continue;
                        }
                        boolean isFavMap = false;
                        for (final String fav_map : this.favourite_maps) {
                            if (slot_name.contains(fav_map)) {
                                isFavMap = true;
                                break;
                            }
                        }
                        if (isFavMap) {
                            continue;
                        }
                        slot.func_75215_d(new ItemStack((Block)Blocks.field_150399_cn, 1, 14));
                        slot.func_75211_c().func_151001_c(slot_name);
                    }
                }
            }
            final ArrayList<String> fastMaps = new ArrayList<String>();
            for (int k = 0; k < this.favourite_maps.length; ++k) {
                final boolean isSlotFound = false;
                for (final Slot slot2 : chest_slots) {
                    if (slot2 != null && slot2.func_75211_c() != null && slot2.func_75211_c().func_82833_r() != null) {
                        if (slot2.field_75222_d > 34) {
                            continue;
                        }
                        if (slot2.func_75211_c().func_82833_r().contains(this.favourite_maps[k])) {
                            fastMaps.add(this.favourite_maps[k]);
                            break;
                        }
                        continue;
                    }
                }
            }
            final boolean isMousePressed = Mouse.isButtonDown(0);
            for (int i = 0; i < 9; ++i) {
                if (i >= this.favourite_maps.length) {
                    break;
                }
                final boolean isMapFound = fastMaps.contains(this.favourite_maps[i]);
                final int slot_number = i + 37;
                for (final Slot slot3 : chest_slots) {
                    if (slot3.field_75222_d != slot_number) {
                        continue;
                    }
                    slot3.func_75215_d(new ItemStack((Block)Blocks.field_150399_cn, 1, isMapFound ? 3 : 8));
                    slot3.func_75211_c().func_151001_c(ColorCodesManager.replaceColorCodesInString((isMapFound ? "&b" : "&7") + this.favourite_maps[i]));
                }
                final Slot slot4 = chest.getSlotUnderMouse();
                if (isMousePressed && slot4 != null && slot4.field_75222_d == slot_number) {
                    if (isMapFound) {
                        this.useItemInGui(this.favourite_maps[i]);
                        final StringBuilder sb = new StringBuilder();
                        final MyChatListener chatListener = Main.chatListener;
                        ChatSender.addText(sb.append(MyChatListener.PREFIX_BEDWARSBRO).append("\u0417\u0430\u043f\u0443\u0441\u043a\u0430\u044e &a").append(this.favourite_maps[i]).append("&f...").toString());
                    }
                    else {
                        final int slot_idx = this.findItemInGui("item.arrow", false);
                        if (slot_idx == -1) {
                            return;
                        }
                        this.clickItemInGui(slot_idx);
                    }
                }
            }
        }
        if (!isBetterShopActive) {
            return;
        }
        if (idx1 == -1 && idx2 == -1) {
            return;
        }
        if (this.mc.field_71462_r == null) {
            return;
        }
        if (!(this.mc.field_71462_r instanceof GuiChest)) {
            return;
        }
        final GuiChest chest = (GuiChest)this.mc.field_71462_r;
        if (chest == null) {
            return;
        }
        if (idx2 != -1) {
            final List<Slot> chest_slots = (List<Slot>)chest.field_147002_h.field_75151_b;
            if (chest_slots == null || chest_slots.size() == 0) {
                return;
            }
            for (final Slot slot : chest_slots) {
                if (slot != null && slot.func_75211_c() != null) {
                    if (slot.func_75211_c().func_82833_r() == null) {
                        continue;
                    }
                    if (slot.field_75222_d > 34) {
                        continue;
                    }
                    if (!slot.func_75211_c().func_77973_b().func_77658_a().contains("tile.")) {
                        continue;
                    }
                    final String display_name = slot.func_75211_c().func_82833_r();
                    if (display_name == null) {
                        continue;
                    }
                    if (!display_name.contains("[")) {
                        continue;
                    }
                    try {
                        final int cnt = Integer.parseInt("" + display_name.split(" ")[1].charAt(1));
                        final int max_cnt = Integer.parseInt("" + display_name.split(" ")[1].charAt(3));
                        final ItemStack itemStack = slot.func_75211_c();
                        if ((itemStack.field_77994_a = cnt) == max_cnt || max_cnt == 0) {
                            continue;
                        }
                        final ItemStack is2 = slot.func_75211_c();
                        is2.func_151001_c(slot.func_75211_c().func_82833_r());
                        slot.func_75211_c().func_150996_a(Item.func_150898_a((Block)Blocks.field_150399_cn));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        final ArrayList<Item> items2hide = new ArrayList<Item>();
        if (this.findItemInHotbar("stick") > 0) {
            items2hide.add(Items.field_151055_y);
        }
        final List<Slot> chest_slots2 = (List<Slot>)chest.field_147002_h.field_75151_b;
        if (this.findItemInGui("\u0411\u044b\u0441\u0442\u0440\u044b\u0435 \u043f\u043e\u043a\u0443\u043f\u043a\u0438", true) == -1) {
            return;
        }
        if (chest_slots2 == null || chest_slots2.size() == 0) {
            return;
        }
        for (final Slot slot5 : chest_slots2) {
            if (slot5 != null && slot5.func_75211_c() != null) {
                if (slot5.func_75211_c().func_77973_b() == null) {
                    continue;
                }
                if (slot5.field_75222_d >= 45) {
                    continue;
                }
                final List<String> descriptions2 = (List<String>)slot5.func_75211_c().func_82840_a((EntityPlayer)player, false);
                if (descriptions2 == null) {
                    return;
                }
                for (final String s : descriptions2) {
                    if (s.contains("\u041d\u0435\u0434\u043e\u0441\u0442\u0430\u0442\u043e\u0447\u043d\u043e \u0440\u0435\u0441\u0443\u0440\u0441\u043e\u0432") || s.contains("\u0423\u0436\u0435 \u043a\u0443\u043f\u043b\u0435\u043d\u043e")) {
                        slot5.func_75215_d((ItemStack)null);
                        break;
                    }
                }
                if (slot5.func_75211_c() == null) {
                    continue;
                }
                if (slot5.func_75211_c().func_77973_b() == null) {
                    continue;
                }
                for (final Item it : items2hide) {
                    if (slot5.func_75211_c().func_77973_b() == it) {
                        slot5.func_75215_d((ItemStack)null);
                        break;
                    }
                }
            }
        }
    }
    
    public int findItemInHotbar(final String item2find) {
        int i = 0;
        while (true) {
            final int n = i;
            final InventoryPlayer field_71071_by = this.mc.field_71439_g.field_71071_by;
            if (n >= InventoryPlayer.func_70451_h()) {
                return -1;
            }
            final ItemStack stack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null) {
                final Item item = stack.func_77973_b();
                if (item != null) {
                    if (stack.func_82833_r() != null) {
                        if (stack.func_82833_r().contains(item2find) || item.func_77658_a().contains(item2find)) {
                            return i;
                        }
                    }
                }
            }
            ++i;
        }
    }
    
    public int findItemInGui(final String item2find, final boolean justGui) {
        if (this.mc.field_71462_r == null) {
            return -1;
        }
        if (!(this.mc.field_71462_r instanceof GuiChest)) {
            return -1;
        }
        final GuiChest chest = (GuiChest)this.mc.field_71462_r;
        if (chest == null) {
            return -1;
        }
        final List<Slot> chest_slots = (List<Slot>)chest.field_147002_h.field_75151_b;
        if (chest_slots == null || chest_slots.size() == 0) {
            return -1;
        }
        for (final Slot slot : chest_slots) {
            if (slot != null && slot.func_75211_c() != null) {
                if (slot.func_75211_c().func_82833_r() == null) {
                    continue;
                }
                if (justGui && slot.field_75222_d >= 45) {
                    continue;
                }
                if (!slot.func_75211_c().func_77973_b().func_77658_a().contains(item2find)) {
                    if (!slot.func_75211_c().func_82833_r().contains(item2find)) {
                        continue;
                    }
                }
                try {
                    return slot.field_75222_d;
                }
                catch (Exception ex) {
                    break;
                }
            }
        }
        return -1;
    }
    
    public void clickItemInGui(final int slotIdx) {
        this.clickItemInGui(slotIdx, 0);
    }
    
    public void clickItemInGui(final int slotIdx, final int mode) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71070_bA.field_75152_c, slotIdx, 0, mode, (EntityPlayer)this.mc.field_71439_g);
    }
    
    public boolean useItemInHotbar(final String display_name) {
        final int idx = this.findItemInHotbar(display_name);
        return idx != -1 && this.clickItemInHotbar(display_name);
    }
    
    public boolean clickItemInHotbar(final String item2find) {
        if (this.mc.field_71439_g.field_71071_by.func_70448_g() == null || this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r() == null || !this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r().contains(item2find)) {
            this.mc.field_71439_g.field_71071_by.func_70453_c(1);
        }
        if (this.mc.field_71439_g.field_71071_by.func_70448_g() != null && this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r() != null && this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r().contains(item2find)) {
            if (ShopManager.robot == null) {
                this.initRobot();
            }
            try {
                ShopManager.robot.mousePress(4096);
                ShopManager.robot.mouseRelease(4096);
            }
            catch (Exception ex) {}
            return true;
        }
        return false;
    }
    
    public void useItemInGui(final String display_name) {
        this.useItemInGui(display_name, 0);
    }
    
    public void useItemInGui(final String display_name, final int click_mode) {
        final int slot_idx = this.findItemInGui(display_name, true);
        if (slot_idx == -1) {
            return;
        }
        this.clickItemInGui(slot_idx, click_mode);
    }
    
    void printGuiContent() {
        if (this.mc.field_71462_r == null) {
            return;
        }
        if (!(this.mc.field_71462_r instanceof GuiChest)) {
            return;
        }
        final GuiChest chest = (GuiChest)this.mc.field_71462_r;
        if (chest == null) {
            return;
        }
        final List<Slot> chest_slots = (List<Slot>)chest.field_147002_h.field_75151_b;
        if (chest_slots == null || chest_slots.size() == 0) {
            return;
        }
        for (final Slot slot : chest_slots) {
            if (slot != null && slot.func_75211_c() != null) {
                if (slot.func_75211_c().func_82833_r() == null) {
                    continue;
                }
                ChatSender.addText(slot.field_75222_d + ") " + slot.func_75211_c().func_82833_r() + " | " + slot.func_75211_c().func_77973_b().func_77658_a());
            }
        }
        ChatSender.addText("");
    }
    
    void printHotbarContent() {
        if (this.mc.field_71439_g.field_71071_by == null || this.mc.field_71439_g.field_71071_by.field_70462_a == null) {
            return;
        }
        int i = 0;
        while (true) {
            final int n = i;
            final InventoryPlayer field_71071_by = this.mc.field_71439_g.field_71071_by;
            if (n >= InventoryPlayer.func_70451_h()) {
                break;
            }
            final ItemStack stack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null) {
                final Item item = stack.func_77973_b();
                if (item != null) {
                    if (stack.func_82833_r() != null) {
                        ChatSender.addText(stack.func_77977_a());
                    }
                }
            }
            ++i;
        }
        ChatSender.addText("");
    }
    
    static {
        ShopManager.isShopOpenedFlag = false;
        ShopManager.time_last_teams_closen = 0L;
    }
    
    public class MyShopItem
    {
        public int slot_idx;
        public ItemStack itemStack;
        public int[] categories;
        public int price_iron;
        public int price_gold;
        public int price_emeralds;
        public boolean isCountable;
        public int cnt_can_buy;
        
        public MyShopItem(final int[] categories, final int slot_idx, final Item item, final Block block, final Enchantment enchantment, final int enchantment_level, final int stackSize, final int metadata, final String display_name, final String price, final boolean isCountable) {
            this.slot_idx = slot_idx;
            this.categories = categories;
            this.cnt_can_buy = 0;
            if (item != null) {
                this.itemStack = new ItemStack(item, stackSize, metadata);
            }
            else if (block != null) {
                this.itemStack = new ItemStack(block, stackSize, metadata);
            }
            if (enchantment != null) {
                this.itemStack.func_77966_a(enchantment, enchantment_level);
            }
            if (display_name.length() > 0) {
                this.itemStack.func_151001_c(display_name);
            }
            this.price_iron = 0;
            this.price_gold = 0;
            this.price_emeralds = 0;
            if (price.startsWith("i")) {
                this.price_iron = Integer.parseInt(price.substring(1));
            }
            if (price.startsWith("g")) {
                this.price_gold = Integer.parseInt(price.substring(1));
            }
            if (price.startsWith("e")) {
                this.price_emeralds = Integer.parseInt(price.substring(1));
            }
            this.isCountable = isCountable;
        }
    }
}
