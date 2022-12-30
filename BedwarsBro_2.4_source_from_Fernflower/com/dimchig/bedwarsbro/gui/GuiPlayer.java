package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import com.dimchig.nolegit.AimHelper;
import com.dimchig.nolegit.BowAimbot;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiPlayer extends GuiScreen {

   static Minecraft mc;
   private String chosen_player_name = "";
   private static boolean isPartyMod = false;


   public GuiPlayer() {
      mc = Minecraft.func_71410_x();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
   }

   public void func_73876_c() {
      ScaledResolution sr = new ScaledResolution(mc);
      int screen_width = sr.func_78326_a();
      int screen_height = sr.func_78328_b();
      int cx = screen_width / 2;
      int cy = screen_height / 2;
      this.field_146292_n.clear();
      byte btn_width = 100;
      byte btn_height = 20;
      int var10000 = cx - btn_width / 2;
      ArrayList arr = new ArrayList();
      StringBuilder var10001 = new StringBuilder();
      BowAimbot var10002 = Main.bowAimbot;
      arr.add(var10001.append(BowAimbot.isActive?"&a":"&c").append("Bow Aimbot").toString());
      var10001 = new StringBuilder();
      var10002 = Main.bowAimbot;
      arr.add(var10001.append(BowAimbot.isDrawActive?"&a":"&c").append("Bow Aimbot Visualization").toString());
      var10001 = new StringBuilder();
      AimHelper var13 = Main.aimHelper;
      arr.add(var10001.append(AimHelper.isActive?"&a":"&c").append("Aim Helper").toString());
      var10001 = new StringBuilder();
      GuiPlayerFocus var14 = Main.playerFocus;
      arr.add(var10001.append(GuiPlayerFocus.STATE?"&a":"&c").append("ESP").toString());
      var10001 = new StringBuilder();
      var14 = Main.playerFocus;
      arr.add(var10001.append(GuiPlayerFocus.isT_Active?"&a":"&c").append("Tracers").toString());
      short var12 = 200;
      int x = cx - var12 / 2;
      int y = cy - (int)(((float)arr.size() / 2.0F + 1.0F) * (float)btn_height);

      for(int i = 0; i < arr.size(); ++i) {
         this.field_146292_n.add(new GuiButton(i, x, y, var12, 20, ColorCodesManager.replaceColorCodesInString((String)arr.get(i))));
         y += btn_height + 5;
      }

   }

   public void func_73863_a(int parWidth, int parHeight, float p_73863_3_) {
      this.func_146276_q_();
      super.func_73863_a(parWidth, parHeight, p_73863_3_);
   }

   protected void func_146284_a(GuiButton parButton) {
      Iterator var2 = this.field_146292_n.iterator();

      GuiButton btn;
      do {
         if(!var2.hasNext()) {
            return;
         }

         btn = (GuiButton)var2.next();
      } while(btn != parButton);

      if(btn.field_146127_k == 0) {
         Main.bowAimbot.toggle();
      } else if(btn.field_146127_k == 1) {
         Main.bowAimbot.toggleDraw();
         BowAimbot var10000 = Main.bowAimbot;
         if(BowAimbot.isDrawActive) {
            var10000 = Main.bowAimbot;
            BowAimbot.isActive = true;
         }
      } else if(btn.field_146127_k == 2) {
         Main.aimHelper.toggle();
      } else {
         GuiPlayerFocus var4;
         if(btn.field_146127_k == 3) {
            var4 = Main.playerFocus;
            var4 = Main.playerFocus;
            GuiPlayerFocus.STATE = !GuiPlayerFocus.STATE;
         } else if(btn.field_146127_k == 4) {
            var4 = Main.playerFocus;
            var4 = Main.playerFocus;
            GuiPlayerFocus.isT_Active = !GuiPlayerFocus.isT_Active;
            var4 = Main.playerFocus;
            if(GuiPlayerFocus.isT_Active) {
               var4 = Main.playerFocus;
               GuiPlayerFocus.STATE = true;
            }
         }
      }

   }

   public boolean func_73868_f() {
      return true;
   }

   public void func_146281_b() {}

}
