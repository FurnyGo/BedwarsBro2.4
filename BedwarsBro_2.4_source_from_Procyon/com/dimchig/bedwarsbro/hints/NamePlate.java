// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.client.gui.FontRenderer;
import java.awt.Color;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class NamePlate extends Gui
{
    static Minecraft mc;
    private static float color_idx;
    
    public NamePlate() {
        NamePlate.mc = Minecraft.func_71410_x();
    }
    
    public void draw(final Vec3 pos, boolean isRainbow, final boolean isTeamColor, final int rainbowSpeed, String constantColor) {
        if (NamePlate.mc == null || NamePlate.mc.field_71439_g == null) {
            return;
        }
        final int view_idx = NamePlate.mc.field_71474_y.field_74320_O;
        if (view_idx == 0) {
            return;
        }
        final double name_plate_scale = 0.03;
        final Vec3 text_pos = pos.func_178787_e(new Vec3(0.0, NamePlate.mc.field_71439_g.func_70047_e() + 0.6, 0.0));
        GL11.glPushMatrix();
        GlStateManager.func_179084_k();
        GL11.glTranslated(-pos.field_72450_a + text_pos.field_72450_a, -pos.field_72448_b + text_pos.field_72448_b, -pos.field_72449_c + text_pos.field_72449_c);
        GL11.glRotatef(-NamePlate.mc.field_71439_g.field_70177_z, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(NamePlate.mc.field_71439_g.field_70125_A, 1.0f, 0.0f, 0.0f);
        GL11.glScaled((view_idx == 1) ? (-name_plate_scale) : name_plate_scale, -name_plate_scale, name_plate_scale);
        final FontRenderer fontRenderer = NamePlate.mc.field_71466_p;
        if (isTeamColor) {
            final MyChatListener chatListener = Main.chatListener;
            final CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor((EntityPlayer)NamePlate.mc.field_71439_g);
            if (team_color != CustomScoreboard.TEAM_COLOR.NONE) {
                isRainbow = false;
                if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
                    constantColor = "#ff0000";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
                    constantColor = "#ffff00";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
                    constantColor = "#00ff00";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
                    constantColor = "#00ffff";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
                    constantColor = "#0055ff";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
                    constantColor = "#ff00ff";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
                    constantColor = "#ffffff";
                }
                else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
                    constantColor = "#b5b5b5";
                }
            }
        }
        String player_name = NamePlate.mc.field_71439_g.func_70005_c_();
        String text;
        player_name = (text = ChatSender.parseText(player_name));
        int text_width = fontRenderer.func_78256_a(player_name);
        if (!isRainbow && constantColor.length() != 7) {
            text = ChatSender.parseText(NamePlate.mc.field_71439_g.func_145748_c_().func_150254_d());
            text_width = fontRenderer.func_78256_a(NamePlate.mc.field_71439_g.func_145748_c_().func_150260_c());
        }
        GL11.glTranslated(0.0, (double)(-fontRenderer.field_78288_b / 2), 0.0);
        if (constantColor.length() == 7 && constantColor.startsWith("#")) {
            int c = -999;
            try {
                c = this.getColor(constantColor.substring(1) + "ff");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            if (c == -999) {
                final String str = "\u041e\u0448\u0438\u0431\u043a\u0430 \u0446\u0432\u0435\u0442\u0430 \u0432 \u043a\u043e\u043d\u0444\u0438\u0433\u0435!";
                fontRenderer.func_78276_b(str, -fontRenderer.func_78256_a(str) / 2, 0, new Color(1.0f, 0.0f, 0.0f, 1.0f).getRGB());
            }
            else {
                fontRenderer.func_78276_b(text, -text_width / 2, 0, c);
            }
        }
        else if (!isRainbow) {
            fontRenderer.func_78276_b(text, -text_width / 2, 0, new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB());
        }
        else {
            int start_x = -text_width / 2;
            final int gradient_hargness = 10;
            for (int i = 0; i < player_name.length(); ++i) {
                final String t = "" + player_name.charAt(i);
                final int t_width = fontRenderer.func_78256_a(t);
                fontRenderer.func_78276_b(t, start_x, 0, Main.rainbowColorSynchronizer.getColor(i * gradient_hargness - text.length() / 2).getRGB());
                start_x += t_width;
            }
        }
        final double d = 0.10000000149011612;
        GL11.glTranslated(0.0, 0.0, d);
        func_73734_a(-text_width / 2 - 1, -1, text_width / 2 + 1, 8, new Color(0.0f, 0.0f, 0.0f, 0.2f).getRGB());
        GL11.glPopMatrix();
    }
    
    private int getColor(final String hexColor) {
        final Color colorNoAlpha = Color.decode("0x" + hexColor.substring(0, 6));
        final int alpha = Integer.parseInt(hexColor.substring(6, 8), 16);
        return new Color(colorNoAlpha.getRed(), colorNoAlpha.getGreen(), colorNoAlpha.getBlue(), alpha).getRGB();
    }
}
