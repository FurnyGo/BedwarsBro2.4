// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import java.util.Collection;
import java.util.ArrayList;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;
import java.util.List;
import com.dimchig.bedwarsbro.ColorCodesManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod;
import com.dimchig.bedwarsbro.Main;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig
{
    @Mod.Instance
    private static Main asInstance;
    
    public ConfigGui(final GuiScreen parentScreen) {
        super(parentScreen, (List)getConfigElements(), "bedwarsbro", false, false, ColorCodesManager.replaceColorCodesInString("&e\u041a\u043e\u043d\u0444\u0438\u0433 &7\u0434\u043b\u044f &cBedwars&fBro &7| &b&l\u041d\u0430\u0432\u043e\u0434\u0438 \u043c\u044b\u0448\u043a\u043e\u0439 \u043d\u0430 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u044f! &7| &2&ltrue &7= &a\u0412\u043a\u043b\u044e\u0447\u0438\u0442\u044c&7, &4&lfalse &7= &c\u0412\u044b\u043a\u043b\u044e\u0447\u0438\u0442\u044c"));
    }
    
    private static List<IConfigElement> getConfigElements() {
        return new ArrayList<IConfigElement>(new ConfigElement(ConfigGui.asInstance.getClientConfig()).getChildElements());
    }
}
