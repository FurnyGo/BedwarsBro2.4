package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class NamePlate extends Gui {

   static Minecraft mc;
   private static float color_idx;


   public NamePlate() {
      mc = Minecraft.func_71410_x();
   }

   public void draw(Vec3 pos, boolean isRainbow, boolean isTeamColor, int rainbowSpeed, String constantColor) {
      if(mc != null && mc.field_71439_g != null) {
         int view_idx = mc.field_71474_y.field_74320_O;
         if(view_idx != 0) {
            double name_plate_scale = 0.03D;
            Vec3 text_pos = pos.func_178787_e(new Vec3(0.0D, (double)mc.field_71439_g.func_70047_e() + 0.6D, 0.0D));
            GL11.glPushMatrix();
            GlStateManager.func_179084_k();
            GL11.glTranslated(-pos.field_72450_a + text_pos.field_72450_a, -pos.field_72448_b + text_pos.field_72448_b, -pos.field_72449_c + text_pos.field_72449_c);
            GL11.glRotatef(-mc.field_71439_g.field_70177_z, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(mc.field_71439_g.field_70125_A, 1.0F, 0.0F, 0.0F);
            GL11.glScaled(view_idx == 1?-name_plate_scale:name_plate_scale, -name_plate_scale, name_plate_scale);
            FontRenderer fontRenderer = mc.field_71466_p;
            if(isTeamColor) {
               MyChatListener var10000 = Main.chatListener;
               CustomScoreboard.TEAM_COLOR player_name = MyChatListener.getEntityTeamColor(mc.field_71439_g);
               if(player_name != CustomScoreboard.TEAM_COLOR.NONE) {
                  isRainbow = false;
                  if(player_name == CustomScoreboard.TEAM_COLOR.RED) {
                     constantColor = "#ff0000";
                  } else if(player_name == CustomScoreboard.TEAM_COLOR.YELLOW) {
                     constantColor = "#ffff00";
                  } else if(player_name == CustomScoreboard.TEAM_COLOR.GREEN) {
                     constantColor = "#00ff00";
                  } else if(player_name == CustomScoreboard.TEAM_COLOR.AQUA) {
                     constantColor = "#00ffff";
                  } else if(player_name == CustomScoreboard.TEAM_COLOR.BLUE) {
                     constantColor = "#0055ff";
                  } else if(player_name == CustomScoreboard.TEAM_COLOR.PINK) {
                     constantColor = "#ff00ff";
                  } else if(player_name == CustomScoreboard.TEAM_COLOR.WHITE) {
                     constantColor = "#ffffff";
                  } else if(player_name == CustomScoreboard.TEAM_COLOR.GRAY) {
                     constantColor = "#b5b5b5";
                  }
               }
            }

            String var20 = mc.field_71439_g.func_70005_c_();
            var20 = ChatSender.parseText(var20);
            String text = var20;
            int text_width = fontRenderer.func_78256_a(var20);
            if(!isRainbow && constantColor.length() != 7) {
               text = ChatSender.parseText(mc.field_71439_g.func_145748_c_().func_150254_d());
               text_width = fontRenderer.func_78256_a(mc.field_71439_g.func_145748_c_().func_150260_c());
            }

            GL11.glTranslated(0.0D, (double)(-fontRenderer.field_78288_b / 2), 0.0D);
            int d;
            if(constantColor.length() == 7 && constantColor.startsWith("#")) {
               d = -999;

               try {
                  d = this.getColor(constantColor.substring(1) + "ff");
               } catch (Exception var19) {
                  var19.printStackTrace();
               }

               if(d == -999) {
                  String var21 = "Ошибка цвета в конфиге!";
                  fontRenderer.func_78276_b(var21, -fontRenderer.func_78256_a(var21) / 2, 0, (new Color(1.0F, 0.0F, 0.0F, 1.0F)).getRGB());
               } else {
                  fontRenderer.func_78276_b(text, -text_width / 2, 0, d);
               }
            } else if(!isRainbow) {
               fontRenderer.func_78276_b(text, -text_width / 2, 0, (new Color(1.0F, 1.0F, 1.0F, 1.0F)).getRGB());
            } else {
               d = -text_width / 2;
               byte gradient_hargness = 10;

               for(int i = 0; i < var20.length(); ++i) {
                  String t = "" + var20.charAt(i);
                  int t_width = fontRenderer.func_78256_a(t);
                  fontRenderer.func_78276_b(t, d, 0, Main.rainbowColorSynchronizer.getColor(i * gradient_hargness - text.length() / 2).getRGB());
                  d += t_width;
               }
            }

            double var22 = 0.10000000149011612D;
            GL11.glTranslated(0.0D, 0.0D, var22);
            func_73734_a(-text_width / 2 - 1, -1, text_width / 2 + 1, 8, (new Color(0.0F, 0.0F, 0.0F, 0.2F)).getRGB());
            GL11.glPopMatrix();
         }
      }
   }

   private int getColor(String hexColor) {
      Color colorNoAlpha = Color.decode("0x" + hexColor.substring(0, 6));
      int alpha = Integer.parseInt(hexColor.substring(6, 8), 16);
      return (new Color(colorNoAlpha.getRed(), colorNoAlpha.getGreen(), colorNoAlpha.getBlue(), alpha)).getRGB();
   }
}
