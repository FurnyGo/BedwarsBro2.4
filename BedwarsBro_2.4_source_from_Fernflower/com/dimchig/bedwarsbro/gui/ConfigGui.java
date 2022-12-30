package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.Main;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.common.Mod.Instance;

public class ConfigGui extends GuiConfig {

   @Instance
   private static Main asInstance;


   public ConfigGui(GuiScreen parentScreen) {
      super(parentScreen, getConfigElements(), "bedwarsbro", false, false, ColorCodesManager.replaceColorCodesInString("&eКонфиг &7для &cBedwars&fBro &7| &b&lНаводи мышкой на названия! &7| &2&ltrue &7= &aВключить&7, &4&lfalse &7= &cВыключить"));
   }

   private static List getConfigElements() {
      return new ArrayList((new ConfigElement(asInstance.getClientConfig())).getChildElements());
   }
}
