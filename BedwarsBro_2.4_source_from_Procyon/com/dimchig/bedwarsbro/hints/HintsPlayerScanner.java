// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;

public class HintsPlayerScanner
{
    public static ArrayList<BWPlayer> scanPlayers() {
        try {
            final ArrayList<BWPlayer> players = new ArrayList<BWPlayer>();
            final Entity mod_player = (Entity)Minecraft.func_71410_x().field_71439_g;
            final ArrayList<EntityPlayer> entities = (ArrayList<EntityPlayer>)Minecraft.func_71410_x().field_71441_e.field_73010_i;
            for (final EntityPlayer en : entities) {
                try {
                    final double posX = en.field_70165_t;
                    final double posY = en.field_70163_u;
                    final double posZ = en.field_70161_v;
                    final double dist = Math.sqrt(Math.pow(mod_player.field_70165_t - posX, 2.0) + Math.pow(mod_player.field_70161_v - posZ, 2.0));
                    final BWPlayer player = new BWPlayer(en, en.func_70005_c_(), null, 0, BWItemsHandler.BWItemArmourLevel.NONE, posX, posY, posZ, dist);
                    player.team_color = MyChatListener.getEntityTeamColor(en);
                    setPlayerHoldingItem(en, player);
                    player.armourLevel = getPlayerArmourLevel(en);
                    players.add(player);
                }
                catch (Exception ex) {}
            }
            return players;
        }
        catch (Exception ex2) {
            return new ArrayList<BWPlayer>();
        }
    }
    
    public static void setPlayerHoldingItem(final EntityPlayer en, final BWPlayer player) {
        if (en.func_70035_c() != null) {
            final ItemStack item = en.field_71071_by.func_70448_g();
            if (item != null) {
                final String displayer_name = item.func_82833_r();
                final String local_name = item.func_77977_a().substring(5);
                player.item_in_hand = BWItemsHandler.findItem(local_name, displayer_name);
                player.item_in_hand_amount = item.field_77994_a;
            }
        }
    }
    
    public static BWItemsHandler.BWItemArmourLevel getPlayerArmourLevel(final EntityPlayer en) {
        if (en.func_70035_c().length == 0) {
            return BWItemsHandler.BWItemArmourLevel.NONE;
        }
        final ItemStack itemStack = en.func_70035_c()[1];
        if (itemStack == null) {
            return BWItemsHandler.BWItemArmourLevel.NONE;
        }
        final String displayer_name = itemStack.func_82833_r();
        final String local_name = itemStack.func_77977_a().substring(5);
        final BWItem item_armour = BWItemsHandler.findItem(local_name, displayer_name);
        return item_armour.armourLevel;
    }
    
    public static class BWPlayer
    {
        public EntityPlayer en;
        public String name;
        public BWItem item_in_hand;
        public int item_in_hand_amount;
        public BWItemsHandler.BWItemArmourLevel armourLevel;
        public double posX;
        public double posY;
        public double posZ;
        public double distToPlayer;
        public CustomScoreboard.TEAM_COLOR team_color;
        
        public BWPlayer(final EntityPlayer en, final String name, final BWItem item_in_hand, final int item_in_hand_amount, final BWItemsHandler.BWItemArmourLevel armourLevel, final double posX, final double posY, final double posZ, final double distToPlayer) {
            this.en = en;
            this.name = name;
            this.item_in_hand = item_in_hand;
            this.item_in_hand_amount = item_in_hand_amount;
            this.armourLevel = armourLevel;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.distToPlayer = distToPlayer;
            this.team_color = CustomScoreboard.TEAM_COLOR.NONE;
        }
    }
}
