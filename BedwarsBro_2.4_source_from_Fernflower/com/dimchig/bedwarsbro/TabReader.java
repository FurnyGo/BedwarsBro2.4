package com.dimchig.bedwarsbro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class TabReader {

   public static List getTabNames() {
      ArrayList arr = new ArrayList();

      try {
         Collection ex = Minecraft.func_71410_x().func_147114_u().func_175106_d();
         int cnt = 0;

         for(Iterator var3 = ex.iterator(); var3.hasNext(); ++cnt) {
            NetworkPlayerInfo info = (NetworkPlayerInfo)var3.next();
            String name = Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175243_a(info).trim();
            arr.add(name);
            if(cnt > 10000) {
               break;
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return arr;
   }
}
