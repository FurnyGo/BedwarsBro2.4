// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import java.util.Iterator;
import java.util.Collection;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class TabReader
{
    public static List<String> getTabNames() {
        final ArrayList<String> arr = new ArrayList<String>();
        try {
            final Collection<NetworkPlayerInfo> players = (Collection<NetworkPlayerInfo>)Minecraft.func_71410_x().func_147114_u().func_175106_d();
            int cnt = 0;
            for (final NetworkPlayerInfo info : players) {
                final String name = Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175243_a(info).trim();
                arr.add(name);
                if (cnt > 10000) {
                    break;
                }
                ++cnt;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return arr;
    }
}
