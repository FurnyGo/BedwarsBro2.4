package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.hints.BWItem;
import java.util.ArrayList;
import java.util.Iterator;

public class BWItemsHandler {

   public static ArrayList bwitems = new ArrayList();


   public static BWItem findItem(String local_name, String display_name) {
      if(bwitems.size() == 0) {
         initBWItems();
      }

      Iterator var2 = bwitems.iterator();

      while(true) {
         BWItem item;
         do {
            do {
               if(!var2.hasNext()) {
                  return null;
               }

               item = (BWItem)var2.next();
            } while(!local_name.contains(item.local_name));
         } while(!display_name.contains(item.display_name));

         if(item.type != BWItemsHandler.BWItemType.BLOCK_WOOL && item.type != BWItemsHandler.BWItemType.BLOCK_CLAY) {
            if(item.type == BWItemsHandler.BWItemType.POTION_REGENERATION) {
               if(!display_name.contains("егенерац") && !display_name.contains("egeneratio")) {
                  continue;
               }

               return item;
            }

            if(item.type == BWItemsHandler.BWItemType.POTION_SPEED) {
               if(!display_name.contains("тремительнос") && !display_name.contains("wiftnes")) {
                  continue;
               }

               return item;
            }

            if(item.type != BWItemsHandler.BWItemType.POTION_HEAL) {
               return item;
            }

            if(!display_name.contains("ечебно") && !display_name.contains("ealin")) {
               continue;
            }

            return item;
         }

         BWItemsHandler.BWItemColor color = BWItemsHandler.BWItemColor.WHITE;
         if(local_name.contains(".red")) {
            color = BWItemsHandler.BWItemColor.RED;
         } else if(local_name.contains(".yellow")) {
            color = BWItemsHandler.BWItemColor.YELLOW;
         } else if(local_name.contains(".lime")) {
            color = BWItemsHandler.BWItemColor.GREEN;
         } else if(local_name.contains(".lightBlue")) {
            color = BWItemsHandler.BWItemColor.AQUA;
         } else if(local_name.contains(".blue")) {
            color = BWItemsHandler.BWItemColor.BLUE;
         } else if(local_name.contains(".pink")) {
            color = BWItemsHandler.BWItemColor.PINK;
         } else if(local_name.contains(".gray")) {
            color = BWItemsHandler.BWItemColor.GRAY;
         }

         BWItem new_item = new BWItem(local_name, display_name, item.type, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE);
         new_item.color = color;
         return new_item;
      }
   }

   public static void initBWItems() {
      bwitems = new ArrayList();
      bwitems.add(new BWItem("appleGold", "", BWItemsHandler.BWItemType.FOOD, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("arrow", "", BWItemsHandler.BWItemType.ARROW, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("bootsCloth", "", BWItemsHandler.BWItemType.ARMOUR_BOOTS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.LEATHER));
      bwitems.add(new BWItem("bootsChain", "", BWItemsHandler.BWItemType.ARMOUR_BOOTS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.CHAIN));
      bwitems.add(new BWItem("bootsIron", "", BWItemsHandler.BWItemType.ARMOUR_BOOTS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.IRON));
      bwitems.add(new BWItem("bootsDiamond", "", BWItemsHandler.BWItemType.ARMOUR_BOOTS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.DIAMOND));
      bwitems.add(new BWItem("bow", "", BWItemsHandler.BWItemType.BOW, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("bucketWater", "", BWItemsHandler.BWItemType.BUCKET, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("chestplateCloth", "", BWItemsHandler.BWItemType.ARMOUR_CHESTPLATE, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.LEATHER));
      bwitems.add(new BWItem("helmetCloth", "", BWItemsHandler.BWItemType.ARMOUR_HELMET, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.LEATHER));
      bwitems.add(new BWItem("clayHardenedStained", "", BWItemsHandler.BWItemType.BLOCK_CLAY, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("cloth", "", BWItemsHandler.BWItemType.BLOCK_WOOL, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("compass", "§aТрекер команды", BWItemsHandler.BWItemType.COMPASS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("diamond", "", BWItemsHandler.BWItemType.DIAMOND, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("egg", "§aСпавнер моста", BWItemsHandler.BWItemType.BRIDGE_EGG, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("emerald", "", BWItemsHandler.BWItemType.EMERALD, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("enderPearl", "", BWItemsHandler.BWItemType.PEARL, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("fireball", "", BWItemsHandler.BWItemType.FIREBALL, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("glass", "", BWItemsHandler.BWItemType.BLOCK_GLASS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("glassBottle", "", BWItemsHandler.BWItemType.POTION_EMPTY, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("hatchetWood", "", BWItemsHandler.BWItemType.AXE, BWItemsHandler.BWItemToolLevel.WOOD, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("hatchetStone", "", BWItemsHandler.BWItemType.AXE, BWItemsHandler.BWItemToolLevel.STONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("hatchetIron", "", BWItemsHandler.BWItemType.AXE, BWItemsHandler.BWItemToolLevel.IRON, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("hatchetDiamond", "", BWItemsHandler.BWItemType.AXE, BWItemsHandler.BWItemToolLevel.DIAMOND, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("ingotGold", "", BWItemsHandler.BWItemType.GOLD_INGOT, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("ingotIron", "", BWItemsHandler.BWItemType.IRON_INGOT, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("ladder", "", BWItemsHandler.BWItemType.BLOCK_LADDER, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("leggingsChain", "", BWItemsHandler.BWItemType.ARMOUR_LEGGINGS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.CHAIN));
      bwitems.add(new BWItem("leggingsCloth", "", BWItemsHandler.BWItemType.ARMOUR_LEGGINGS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.LEATHER));
      bwitems.add(new BWItem("leggingsDiamond", "", BWItemsHandler.BWItemType.ARMOUR_LEGGINGS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.DIAMOND));
      bwitems.add(new BWItem("leggingsIron", "", BWItemsHandler.BWItemType.ARMOUR_LEGGINGS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.IRON));
      bwitems.add(new BWItem("obsidian", "", BWItemsHandler.BWItemType.BLOCK_OBSIDIAN, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("pickaxeWood", "", BWItemsHandler.BWItemType.PICKAXE, BWItemsHandler.BWItemToolLevel.WOOD, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("pickaxeStone", "", BWItemsHandler.BWItemType.PICKAXE, BWItemsHandler.BWItemToolLevel.STONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("pickaxeIron", "", BWItemsHandler.BWItemType.PICKAXE, BWItemsHandler.BWItemToolLevel.IRON, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("pickaxeDiamond", "", BWItemsHandler.BWItemType.PICKAXE, BWItemsHandler.BWItemToolLevel.DIAMOND, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("potion", "§fЗелье силы", BWItemsHandler.BWItemType.POTION_STRENGTH, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("potion", "§fЗелье прыгучести", BWItemsHandler.BWItemType.POTION_JUMP, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("potion", "", BWItemsHandler.BWItemType.POTION_SPEED, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("potion", "", BWItemsHandler.BWItemType.POTION_REGENERATION, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("potion", "", BWItemsHandler.BWItemType.POTION_HEAL, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("shears", "", BWItemsHandler.BWItemType.SHEARS, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("sponge", "", BWItemsHandler.BWItemType.SPONGE, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("stick", "", BWItemsHandler.BWItemType.STICK, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("swordWood", "", BWItemsHandler.BWItemType.SWORD, BWItemsHandler.BWItemToolLevel.WOOD, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("swordStone", "", BWItemsHandler.BWItemType.SWORD, BWItemsHandler.BWItemToolLevel.STONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("swordIron", "", BWItemsHandler.BWItemType.SWORD, BWItemsHandler.BWItemToolLevel.IRON, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("swordDiamond", "", BWItemsHandler.BWItemType.SWORD, BWItemsHandler.BWItemToolLevel.DIAMOND, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("tnt", "", BWItemsHandler.BWItemType.TNT, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("whiteStone", "", BWItemsHandler.BWItemType.BLOCK_ENDSTONE, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("wood", "", BWItemsHandler.BWItemType.BLOCK_WOOD, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("compass", "Наблюдение за игроками", BWItemsHandler.BWItemType.IGNORE, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("enderPearl", "Начать новую игру", BWItemsHandler.BWItemType.IGNORE, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
      bwitems.add(new BWItem("bed", "Назад в лобби", BWItemsHandler.BWItemType.IGNORE, BWItemsHandler.BWItemToolLevel.NONE, BWItemsHandler.BWItemArmourLevel.NONE));
   }


   public static enum BWItemType {

      NONE("NONE", 0),
      BLOCK_WOOL("BLOCK_WOOL", 1),
      BLOCK_CLAY("BLOCK_CLAY", 2),
      BLOCK_WOOD("BLOCK_WOOD", 3),
      BLOCK_ENDSTONE("BLOCK_ENDSTONE", 4),
      BLOCK_GLASS("BLOCK_GLASS", 5),
      BLOCK_LADDER("BLOCK_LADDER", 6),
      BLOCK_OBSIDIAN("BLOCK_OBSIDIAN", 7),
      ARMOUR_BOOTS("ARMOUR_BOOTS", 8),
      ARMOUR_LEGGINGS("ARMOUR_LEGGINGS", 9),
      ARMOUR_CHESTPLATE("ARMOUR_CHESTPLATE", 10),
      ARMOUR_HELMET("ARMOUR_HELMET", 11),
      SWORD("SWORD", 12),
      PICKAXE("PICKAXE", 13),
      AXE("AXE", 14),
      SHEARS("SHEARS", 15),
      STICK("STICK", 16),
      FOOD("FOOD", 17),
      BOW("BOW", 18),
      ARROW("ARROW", 19),
      FIREBALL("FIREBALL", 20),
      EMERALD("EMERALD", 21),
      DIAMOND("DIAMOND", 22),
      IRON_INGOT("IRON_INGOT", 23),
      GOLD_INGOT("GOLD_INGOT", 24),
      BUCKET("BUCKET", 25),
      COMPASS("COMPASS", 26),
      BRIDGE_EGG("BRIDGE_EGG", 27),
      PEARL("PEARL", 28),
      SPONGE("SPONGE", 29),
      TNT("TNT", 30),
      POTION_SPEED("POTION_SPEED", 31),
      POTION_HEAL("POTION_HEAL", 32),
      POTION_REGENERATION("POTION_REGENERATION", 33),
      POTION_STRENGTH("POTION_STRENGTH", 34),
      POTION_JUMP("POTION_JUMP", 35),
      POTION_EMPTY("POTION_EMPTY", 36),
      IGNORE("IGNORE", 37);
      // $FF: synthetic field
      private static final BWItemsHandler.BWItemType[] $VALUES = new BWItemsHandler.BWItemType[]{NONE, BLOCK_WOOL, BLOCK_CLAY, BLOCK_WOOD, BLOCK_ENDSTONE, BLOCK_GLASS, BLOCK_LADDER, BLOCK_OBSIDIAN, ARMOUR_BOOTS, ARMOUR_LEGGINGS, ARMOUR_CHESTPLATE, ARMOUR_HELMET, SWORD, PICKAXE, AXE, SHEARS, STICK, FOOD, BOW, ARROW, FIREBALL, EMERALD, DIAMOND, IRON_INGOT, GOLD_INGOT, BUCKET, COMPASS, BRIDGE_EGG, PEARL, SPONGE, TNT, POTION_SPEED, POTION_HEAL, POTION_REGENERATION, POTION_STRENGTH, POTION_JUMP, POTION_EMPTY, IGNORE};


      private BWItemType(String var1, int var2) {}

   }

   public static enum BWItemColor {

      NONE("NONE", 0),
      RED("RED", 1),
      YELLOW("YELLOW", 2),
      GREEN("GREEN", 3),
      AQUA("AQUA", 4),
      BLUE("BLUE", 5),
      PINK("PINK", 6),
      GRAY("GRAY", 7),
      WHITE("WHITE", 8);
      // $FF: synthetic field
      private static final BWItemsHandler.BWItemColor[] $VALUES = new BWItemsHandler.BWItemColor[]{NONE, RED, YELLOW, GREEN, AQUA, BLUE, PINK, GRAY, WHITE};


      private BWItemColor(String var1, int var2) {}

   }

   public static enum BWItemToolLevel {

      NONE("NONE", 0),
      WOOD("WOOD", 1),
      STONE("STONE", 2),
      IRON("IRON", 3),
      DIAMOND("DIAMOND", 4);
      // $FF: synthetic field
      private static final BWItemsHandler.BWItemToolLevel[] $VALUES = new BWItemsHandler.BWItemToolLevel[]{NONE, WOOD, STONE, IRON, DIAMOND};


      private BWItemToolLevel(String var1, int var2) {}

   }

   public static enum BWItemPotionsID {

      NONE("NONE", 0, -1),
      STRENGTH("STRENGTH", 1, 5),
      JUMP("JUMP", 2, 8),
      SPEED("SPEED", 3, 1),
      REGEN("REGEN", 4, 10),
      INVIS("INVIS", 5, 14);
      public int id;
      // $FF: synthetic field
      private static final BWItemsHandler.BWItemPotionsID[] $VALUES = new BWItemsHandler.BWItemPotionsID[]{NONE, STRENGTH, JUMP, SPEED, REGEN, INVIS};


      private BWItemPotionsID(String var1, int var2, int id) {
         this.id = id;
      }

   }

   public static enum BWItemArmourLevel {

      NONE("NONE", 0),
      LEATHER("LEATHER", 1),
      CHAIN("CHAIN", 2),
      IRON("IRON", 3),
      DIAMOND("DIAMOND", 4);
      // $FF: synthetic field
      private static final BWItemsHandler.BWItemArmourLevel[] $VALUES = new BWItemsHandler.BWItemArmourLevel[]{NONE, LEATHER, CHAIN, IRON, DIAMOND};


      private BWItemArmourLevel(String var1, int var2) {}

   }
}
