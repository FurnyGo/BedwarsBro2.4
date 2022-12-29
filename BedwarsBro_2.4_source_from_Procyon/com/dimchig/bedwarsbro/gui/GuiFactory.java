// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory
{
    public void initialize(final Minecraft minecraftInstance) {
    }
    
    public boolean hasConfigGui() {
        return true;
    }
    
    public GuiScreen createConfigGui(final GuiScreen parentScreen) {
        return (GuiScreen)new ConfigGui(parentScreen);
    }
    
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return (Class<? extends GuiScreen>)ConfigGui.class;
    }
    
    public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
    
    public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(final IModGuiFactory.RuntimeOptionCategoryElement element) {
        return null;
    }
}
