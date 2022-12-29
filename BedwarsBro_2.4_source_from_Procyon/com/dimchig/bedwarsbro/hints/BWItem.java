// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

public class BWItem
{
    public String local_name;
    public String display_name;
    public BWItemsHandler.BWItemType type;
    public BWItemsHandler.BWItemToolLevel toolLevel;
    public BWItemsHandler.BWItemColor color;
    public BWItemsHandler.BWItemArmourLevel armourLevel;
    
    public BWItem(final String local_name, final String display_name, final BWItemsHandler.BWItemType type, final BWItemsHandler.BWItemToolLevel toolLevel, final BWItemsHandler.BWItemArmourLevel armourLevel) {
        this.local_name = local_name;
        this.display_name = display_name;
        this.type = type;
        this.toolLevel = toolLevel;
        this.armourLevel = armourLevel;
        this.color = BWItemsHandler.BWItemColor.NONE;
    }
}
