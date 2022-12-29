// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.item.ItemStack;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;

public class HintsItemTracker
{
    public void scan() {
        final ItemStack[] items = Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a;
        int cnt_emeralds = 0;
        int cnt_diamonds = 0;
        int cnt_wool = 0;
        for (final ItemStack item : items) {
            if (item != null) {
                if (item.func_77977_a().equals("item.diamond")) {
                    cnt_diamonds += item.field_77994_a;
                }
                if (item.func_77977_a().equals("item.emerald")) {
                    cnt_emeralds += item.field_77994_a;
                }
                if (item.func_77977_a().contains("cloth")) {
                    cnt_wool += item.field_77994_a;
                }
            }
        }
        Main.guiOnScreen.setDiamonds(cnt_diamonds);
        Main.guiOnScreen.setEmeralds(cnt_emeralds);
        Main.guiOnScreen.setBlocks(cnt_wool);
    }
}
