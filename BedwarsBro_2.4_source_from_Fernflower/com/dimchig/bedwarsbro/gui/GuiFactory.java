package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.gui.ConfigGui;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.IModGuiFactory.RuntimeOptionCategoryElement;
import net.minecraftforge.fml.client.IModGuiFactory.RuntimeOptionGuiHandler;

public class GuiFactory implements IModGuiFactory {

   public void initialize(Minecraft minecraftInstance) {}

   public boolean hasConfigGui() {
      return true;
   }

   public GuiScreen createConfigGui(GuiScreen parentScreen) {
      return new ConfigGui(parentScreen);
   }

   public Class mainConfigGuiClass() {
      return ConfigGui.class;
   }

   public Set runtimeGuiCategories() {
      return null;
   }

   public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
      return null;
   }
}
