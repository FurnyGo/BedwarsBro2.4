// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import java.util.List;
import net.minecraft.entity.player.InventoryPlayer;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.inventory.Slot;
import net.minecraft.client.gui.inventory.GuiChest;
import org.lwjgl.input.Mouse;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

public class ShopManagerOLD
{
    Minecraft mc;
    private KeyBinding key_rclick;
    private KeyBinding key_lclick;
    private int unpress_counter;
    private boolean mouseFlag;
    private ArrayList<BuyQuee> buyQueue;
    private ArrayList<MyShopItem> shopItems;
    private int BUY_TICK_RATE;
    private int BUY_TICK_RATE_COUNTER;
    public static boolean isShopOpenedFlag;
    
    public ShopManagerOLD() {
        this.mouseFlag = false;
        this.buyQueue = new ArrayList<BuyQuee>();
        this.shopItems = new ArrayList<MyShopItem>();
        this.BUY_TICK_RATE = 5;
        this.BUY_TICK_RATE_COUNTER = 0;
        this.mc = Minecraft.func_71410_x();
        this.key_rclick = this.mc.field_71474_y.field_74313_G;
        this.key_lclick = this.mc.field_71474_y.field_74312_F;
        this.buyQueue = new ArrayList<BuyQuee>();
        this.shopItems = new ArrayList<MyShopItem>();
        this.initShopItems();
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
        final String item_name_stick = "item.stick";
        final String item_name_wool = "tile.cloth";
        final String item_name_pickaxe = "item.pickaxe";
        final String item_name_axe = "item.hatchet";
        final String item_name_shears = "item.shears";
        this.shopItems.add(new MyShopItem(19, null, Blocks.field_150325_L, null, 1, 1, 0, "", "i4", true));
        this.shopItems.add(new MyShopItem(20, Items.field_151052_q, null, null, 1, 1, 0, "", "i10", false));
        this.shopItems.add(new MyShopItem(21, (Item)Items.field_151029_X, null, null, 1, 1, 0, "", "i40", false));
        this.shopItems.add(new MyShopItem(23, (Item)Items.field_151031_f, null, null, 1, 1, 0, "", "g12", false));
        this.shopItems.add(new MyShopItem(24, (Item)Items.field_151068_bn, null, null, 1, 1, 8194, "\u0421\u043a\u043e\u0440\u043a\u0430", "e1", true));
        this.shopItems.add(new MyShopItem(25, null, Blocks.field_150335_W, null, 1, 1, 0, "", "g4", true));
        this.shopItems.add(new MyShopItem(28, null, Blocks.field_150344_f, null, 1, 1, 0, "", "g4", true));
        this.shopItems.add(new MyShopItem(29, Items.field_151040_l, null, null, 1, 1, 0, "", "g7", false));
        this.shopItems.add(new MyShopItem(30, (Item)Items.field_151167_ab, null, null, 1, 1, 0, "", "g12", false));
        this.shopItems.add(new MyShopItem(31, (Item)Items.field_151097_aZ, null, null, 1, 1, 0, "", "i16", false));
        this.shopItems.add(new MyShopItem(32, Items.field_151032_g, null, null, 1, 1, 0, "", "g2", true));
        this.shopItems.add(new MyShopItem(33, (Item)Items.field_151068_bn, null, null, 1, 1, 8197, "", "e1", true));
        this.shopItems.add(new MyShopItem(34, Items.field_151131_as, null, null, 1, 1, 0, "", "g4", false));
    }
    
    public void scan() {
        final EntityPlayerSP player = this.mc.field_71439_g;
        if (this.mc.field_71439_g.field_71071_by == null || this.mc.field_71439_g.field_71071_by.field_70462_a == null) {
            return;
        }
        final String item_name = "\u0411\u044b\u0441\u0442\u0440\u044b\u0435 \u043f\u043e\u043a\u0443\u043f\u043a\u0438";
        final int idx = this.findItemInGui(item_name);
        if (idx == -1) {
            if (this.buyQueue.size() > 0) {
                this.buyQueue.clear();
            }
            if (ShopManagerOLD.isShopOpenedFlag) {
                ShopManagerOLD.isShopOpenedFlag = false;
            }
            return;
        }
        this.handleBuyQueue();
        boolean isMousePressed = false;
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            isMousePressed = true;
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
        if (!ShopManagerOLD.isShopOpenedFlag) {
            ShopManagerOLD.isShopOpenedFlag = true;
            this.mouseFlag = false;
        }
        if (chest.field_147002_h.field_75151_b.get(9).func_75211_c().func_77960_j() == 5) {
            int cnt_iron = 0;
            int cnt_gold = 0;
            int cnt_emeralds = 0;
            for (final ItemStack itemStack : player.field_71071_by.field_70462_a) {
                if (itemStack != null) {
                    if (itemStack.func_77973_b() != null) {
                        if (itemStack.func_77973_b() == Items.field_151042_j) {
                            cnt_iron += itemStack.field_77994_a;
                        }
                        else if (itemStack.func_77973_b() == Items.field_151043_k) {
                            cnt_gold += itemStack.field_77994_a;
                        }
                        else if (itemStack.func_77973_b() == Items.field_151166_bC) {
                            cnt_emeralds += itemStack.field_77994_a;
                        }
                    }
                }
            }
            final Item[] single_items = { Items.field_151055_y, Items.field_151131_as };
            final ArrayList<Item> items2hide = new ArrayList<Item>();
            if (this.mc.field_71439_g.func_70035_c() != null && this.mc.field_71439_g.func_70035_c().length == 4 && this.mc.field_71439_g.func_70035_c()[1] != null) {
                final String armour_name = this.mc.field_71439_g.func_70035_c()[1].func_77977_a().substring(5);
                if (armour_name.contains("Chain")) {
                    items2hide.add((Item)Items.field_151029_X);
                }
                else if (armour_name.contains("Iron")) {
                    items2hide.add((Item)Items.field_151167_ab);
                    items2hide.add((Item)Items.field_151029_X);
                }
                else if (armour_name.contains("Diamond")) {
                    items2hide.add((Item)Items.field_151167_ab);
                    items2hide.add((Item)Items.field_151029_X);
                    items2hide.add((Item)Items.field_151175_af);
                }
            }
            if (this.findItemInHotbar("pickaxe") > 0 && this.findItemInHotbar("hatchet") > 0 && this.findItemInHotbar("shears") > 0) {
                items2hide.add(Items.field_151038_n);
            }
            if (this.findItemInHotbar("shears") > 0) {
                items2hide.add((Item)Items.field_151097_aZ);
            }
            for (MyShopItem shopItem : this.shopItems) {
                if (shopItem.slot_idx == 19) {
                    int wool_id = 0;
                    final CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor((EntityPlayer)this.mc.field_71439_g);
                    if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
                        wool_id = 14;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
                        wool_id = 4;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
                        wool_id = 5;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
                        wool_id = 3;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
                        wool_id = 11;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
                        wool_id = 6;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
                        wool_id = 0;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
                        wool_id = 7;
                    }
                    shopItem = new MyShopItem(19, null, Blocks.field_150325_L, null, 1, 1, wool_id, "", "i4", true);
                }
                int cnt = 0;
                if (shopItem.price_iron > 0 && cnt_iron >= shopItem.price_iron) {
                    cnt = cnt_iron / shopItem.price_iron;
                }
                else if (shopItem.price_gold > 0 && cnt_gold >= shopItem.price_gold) {
                    cnt = cnt_gold / shopItem.price_gold;
                }
                else if (shopItem.price_emeralds > 0 && cnt_emeralds >= shopItem.price_emeralds) {
                    cnt = cnt_emeralds / shopItem.price_emeralds;
                }
                if (!shopItem.isCountable && cnt > 1) {
                    cnt = 1;
                }
                shopItem.cnt_can_buy = cnt;
                shopItem.itemStack.field_77994_a = ((cnt > 1) ? cnt : 1);
                final Slot slot = chest.field_147002_h.field_75151_b.get(shopItem.slot_idx);
                if (cnt == 0) {
                    slot.func_75215_d((ItemStack)null);
                }
                else {
                    slot.func_75215_d(shopItem.itemStack);
                }
                for (final Item single_item : single_items) {
                    if (shopItem.itemStack.func_77973_b() == single_item && this.findItemInHotbar(shopItem.itemStack.func_77973_b().func_77658_a()) != -1) {
                        slot.func_75215_d((ItemStack)null);
                        break;
                    }
                }
                for (final Item i : items2hide) {
                    if (shopItem.itemStack.func_77973_b() == i) {
                        slot.func_75215_d((ItemStack)null);
                        break;
                    }
                }
            }
            if (isMousePressed) {
                if (this.mouseFlag) {
                    this.mouseFlag = false;
                    if (chest.getSlotUnderMouse() == null) {
                        return;
                    }
                    try {
                        if (chest.getSlotUnderMouse() == null) {
                            return;
                        }
                        final int slot_idx = chest.getSlotUnderMouse().field_75222_d;
                        for (final MyShopItem shopItem2 : this.shopItems) {
                            if (slot_idx == shopItem2.slot_idx) {
                                if (shopItem2.cnt_can_buy > 0) {
                                    for (final BuyQuee b : shopItem2.buyQueue) {
                                        int click_mode = b.click_mode;
                                        if (shopItem2.isCountable && Mouse.isButtonDown(1)) {
                                            click_mode = 1;
                                        }
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else if (!this.mouseFlag) {
                this.mouseFlag = true;
            }
        }
    }
    
    void handleBuyQueue() {
        if (this.buyQueue == null) {
            this.buyQueue = new ArrayList<BuyQuee>();
        }
        if (this.buyQueue.size() <= 0) {
            this.BUY_TICK_RATE_COUNTER = -1;
            return;
        }
        if (this.BUY_TICK_RATE_COUNTER >= 0 && this.BUY_TICK_RATE_COUNTER <= this.BUY_TICK_RATE) {
            ++this.BUY_TICK_RATE_COUNTER;
            return;
        }
        this.BUY_TICK_RATE_COUNTER = 0;
        final BuyQuee b = this.buyQueue.get(0);
        this.buyQueue.remove(0);
        try {
            final int slot_id = Integer.parseInt(b.item_name);
            this.clickItemInGui(slot_id, b.click_mode);
        }
        catch (Exception ex) {
            this.useItemInGui(b.item_name, b.click_mode);
        }
    }
    
    void click_r(final boolean state) {
        this.click_key(this.key_rclick, state);
    }
    
    void click_l(final boolean state) {
        this.click_key(this.key_lclick, state);
    }
    
    void click_key(final KeyBinding key, final boolean state) {
        KeyBinding.func_74510_a(key.func_151463_i(), state);
        if (state) {
            this.unpress_counter = 5;
        }
    }
    
    int findItemInHotbar(final String item2find) {
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
    
    boolean clickItemInHotbar(final String item2find) {
        if (this.mc.field_71439_g.field_71071_by.func_70448_g() == null || this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r() == null || !this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r().contains(item2find)) {
            this.mc.field_71439_g.field_71071_by.func_70453_c(1);
        }
        if (this.mc.field_71439_g.field_71071_by.func_70448_g() != null && this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r() != null && this.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r().contains(item2find)) {
            this.click_r(true);
            return true;
        }
        return false;
    }
    
    int findItemInGui(final String item2find) {
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
                if (slot.field_75222_d >= 72) {
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
    
    void clickItemInGui(final int slotIdx) {
        this.clickItemInGui(slotIdx, 0);
    }
    
    void clickItemInGui(final int slotIdx, final int mode) {
        this.mc.field_71442_b.func_78753_a(this.mc.field_71439_g.field_71070_bA.field_75152_c, slotIdx, 0, mode, (EntityPlayer)this.mc.field_71439_g);
    }
    
    boolean useItemInHotbar(final String display_name) {
        final int idx = this.findItemInHotbar(display_name);
        return idx != -1 && this.clickItemInHotbar(display_name);
    }
    
    void useItemInGui(final String display_name) {
        this.useItemInGui(display_name, 0);
    }
    
    void useItemInGui(final String display_name, final int click_mode) {
        final int slot_idx = this.findItemInGui(display_name);
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
                        ChatSender.addText(stack.func_82833_r());
                    }
                }
            }
            ++i;
        }
        ChatSender.addText("");
    }
    
    static {
        ShopManagerOLD.isShopOpenedFlag = false;
    }
    
    public class BuyQuee
    {
        public String item_name;
        public int click_mode;
        
        public BuyQuee(final String item_name, final int click_mode) {
            this.item_name = item_name;
            this.click_mode = click_mode;
        }
    }
    
    public class MyShopItem
    {
        public int slot_idx;
        public ItemStack itemStack;
        public ArrayList<BuyQuee> buyQueue;
        public int price_iron;
        public int price_gold;
        public int price_emeralds;
        public boolean isCountable;
        public int cnt_can_buy;
        
        public MyShopItem(final int slot_idx, final Item item, final Block block, final Enchantment enchantment, final int enchantment_level, final int stackSize, final int metadata, final String display_name, final String price, final boolean isCountable) {
            this.slot_idx = slot_idx;
            this.buyQueue = new ArrayList<BuyQuee>();
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
        
        public void addBuyQuee(final String item_name) {
            this.addBuyQuee(item_name, 0);
        }
        
        public void addBuyQuee(final String item_name, final int click_mode) {
            this.buyQueue.add(new BuyQuee(item_name, click_mode));
        }
    }
}
