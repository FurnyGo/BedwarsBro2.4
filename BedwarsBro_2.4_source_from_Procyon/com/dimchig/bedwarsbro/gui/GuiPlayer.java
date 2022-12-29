// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.nolegit.AimHelper;
import com.dimchig.nolegit.BowAimbot;
import com.dimchig.bedwarsbro.Main;
import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiPlayer extends GuiScreen
{
    static Minecraft mc;
    private String chosen_player_name;
    private static boolean isPartyMod;
    
    public GuiPlayer() {
        this.chosen_player_name = "";
        GuiPlayer.mc = Minecraft.func_71410_x();
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
    }
    
    public void func_73876_c() {
        final ScaledResolution sr = new ScaledResolution(GuiPlayer.mc);
        final int screen_width = sr.func_78326_a();
        final int screen_height = sr.func_78328_b();
        final int cx = screen_width / 2;
        final int cy = screen_height / 2;
        this.field_146292_n.clear();
        int btn_width = 100;
        final int btn_height = 20;
        int x = cx - btn_width / 2;
        int y = cy;
        final ArrayList<String> list;
        final ArrayList<String> arr = list = new ArrayList<String>();
        final StringBuilder sb = new StringBuilder();
        final BowAimbot bowAimbot = Main.bowAimbot;
        list.add(sb.append(BowAimbot.isActive ? "&a" : "&c").append("Bow Aimbot").toString());
        final ArrayList<String> list2 = arr;
        final StringBuilder sb2 = new StringBuilder();
        final BowAimbot bowAimbot2 = Main.bowAimbot;
        list2.add(sb2.append(BowAimbot.isDrawActive ? "&a" : "&c").append("Bow Aimbot Visualization").toString());
        final ArrayList<String> list3 = arr;
        final StringBuilder sb3 = new StringBuilder();
        final AimHelper aimHelper = Main.aimHelper;
        list3.add(sb3.append(AimHelper.isActive ? "&a" : "&c").append("Aim Helper").toString());
        final ArrayList<String> list4 = arr;
        final StringBuilder sb4 = new StringBuilder();
        final GuiPlayerFocus playerFocus = Main.playerFocus;
        list4.add(sb4.append(GuiPlayerFocus.STATE ? "&a" : "&c").append("ESP").toString());
        final ArrayList<String> list5 = arr;
        final StringBuilder sb5 = new StringBuilder();
        final GuiPlayerFocus playerFocus2 = Main.playerFocus;
        list5.add(sb5.append(GuiPlayerFocus.isT_Active ? "&a" : "&c").append("Tracers").toString());
        btn_width = 200;
        x = cx - btn_width / 2;
        y -= (int)((arr.size() / 2.0f + 1.0f) * btn_height);
        for (int i = 0; i < arr.size(); ++i) {
            this.field_146292_n.add(new GuiButton(i, x, y, btn_width, 20, ColorCodesManager.replaceColorCodesInString(arr.get(i))));
            y += btn_height + 5;
        }
    }
    
    public void func_73863_a(final int parWidth, final int parHeight, final float p_73863_3_) {
        this.func_146276_q_();
        super.func_73863_a(parWidth, parHeight, p_73863_3_);
    }
    
    protected void func_146284_a(final GuiButton parButton) {
        for (final GuiButton btn : this.field_146292_n) {
            if (btn == parButton) {
                if (btn.field_146127_k == 0) {
                    Main.bowAimbot.toggle();
                }
                else if (btn.field_146127_k == 1) {
                    Main.bowAimbot.toggleDraw();
                    final BowAimbot bowAimbot = Main.bowAimbot;
                    if (BowAimbot.isDrawActive) {
                        final BowAimbot bowAimbot2 = Main.bowAimbot;
                        BowAimbot.isActive = true;
                    }
                }
                else if (btn.field_146127_k == 2) {
                    Main.aimHelper.toggle();
                }
                else if (btn.field_146127_k == 3) {
                    final GuiPlayerFocus playerFocus = Main.playerFocus;
                    final GuiPlayerFocus playerFocus2 = Main.playerFocus;
                    GuiPlayerFocus.STATE = !GuiPlayerFocus.STATE;
                }
                else if (btn.field_146127_k == 4) {
                    final GuiPlayerFocus playerFocus3 = Main.playerFocus;
                    final GuiPlayerFocus playerFocus4 = Main.playerFocus;
                    GuiPlayerFocus.isT_Active = !GuiPlayerFocus.isT_Active;
                    final GuiPlayerFocus playerFocus5 = Main.playerFocus;
                    if (GuiPlayerFocus.isT_Active) {
                        final GuiPlayerFocus playerFocus6 = Main.playerFocus;
                        GuiPlayerFocus.STATE = true;
                    }
                }
            }
        }
    }
    
    public boolean func_73868_f() {
        return true;
    }
    
    public void func_146281_b() {
    }
    
    static {
        GuiPlayer.isPartyMod = false;
    }
}
