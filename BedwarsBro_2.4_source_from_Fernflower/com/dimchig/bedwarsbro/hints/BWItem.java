package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.hints.BWItemsHandler;

public class BWItem {

   public String local_name;
   public String display_name;
   public BWItemsHandler.BWItemType type;
   public BWItemsHandler.BWItemToolLevel toolLevel;
   public BWItemsHandler.BWItemColor color;
   public BWItemsHandler.BWItemArmourLevel armourLevel;


   public BWItem(String local_name, String display_name, BWItemsHandler.BWItemType type, BWItemsHandler.BWItemToolLevel toolLevel, BWItemsHandler.BWItemArmourLevel armourLevel) {
      this.local_name = local_name;
      this.display_name = display_name;
      this.type = type;
      this.toolLevel = toolLevel;
      this.armourLevel = armourLevel;
      this.color = BWItemsHandler.BWItemColor.NONE;
   }
}
