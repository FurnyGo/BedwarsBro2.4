// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.client.settings.KeyBinding;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import java.awt.Color;
import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraft.entity.item.EntityTNTPrimed;
import java.text.DecimalFormat;
import net.minecraft.client.gui.Gui;

public class TNTJump extends Gui
{
    private final DecimalFormat timeFormatter;
    private static EntityTNTPrimed tntForJump;
    private static int tntForJumpState;
    private float TIME_STRAIGHT_FOR_RUN;
    private float TIME_STRAIGHT_FOR_JUMP;
    private float TIME_DIAGONAL_FOR_RUN;
    private float TIME_DIAGONAL_FOR_JUMP;
    private float TIME_UP_FOR_JUMP;
    private float TIME_FOR_RUN;
    private float TIME_FOR_JUMP;
    private int unpress_keys_counter;
    private double max_height;
    private int last_fuse;
    
    public TNTJump() {
        this.timeFormatter = new DecimalFormat("0.0");
        this.TIME_STRAIGHT_FOR_RUN = 1.1f;
        this.TIME_STRAIGHT_FOR_JUMP = 0.5f;
        this.TIME_DIAGONAL_FOR_RUN = 1.2f;
        this.TIME_DIAGONAL_FOR_JUMP = 0.4f;
        this.TIME_UP_FOR_JUMP = 0.05f;
        this.TIME_FOR_RUN = this.TIME_STRAIGHT_FOR_RUN;
        this.TIME_FOR_JUMP = this.TIME_STRAIGHT_FOR_JUMP;
        this.unpress_keys_counter = -1;
        this.max_height = -1.0;
        this.last_fuse = -1;
    }
    
    public void draw(final Vec3 playerPos, final float partialTicks) {
        final Minecraft mc = Minecraft.func_71410_x();
        final EntityPlayerSP player = mc.field_71439_g;
        if (this.unpress_keys_counter >= 0) {
            --this.unpress_keys_counter;
            if (this.unpress_keys_counter == 0) {
                this.pressUpKey(Minecraft.func_71410_x().field_71474_y.field_74314_A);
                this.pressUpKey(Minecraft.func_71410_x().field_71474_y.field_151444_V);
                this.unpress_keys_counter = -1;
            }
        }
        final List<EntityTNTPrimed> entities = (List<EntityTNTPrimed>)mc.field_71441_e.func_175644_a((Class)EntityTNTPrimed.class, (Predicate)new Predicate<EntityTNTPrimed>() {
            public boolean apply(final EntityTNTPrimed input) {
                return true;
            }
        });
        if (entities == null || entities.size() == 0) {
            return;
        }
        for (final EntityTNTPrimed en : entities) {
            final String text = "" + this.formatTNTTime((en.field_70516_a - partialTicks) / 20.0f);
            final double x = en.field_70165_t;
            final double y = en.field_70163_u + en.field_70131_O + 0.7;
            final double z = en.field_70161_v;
            final float green = Math.min(en.field_70516_a / 50.0f, 1.0f);
            final Color color = new Color(1.0f - green, green, 0.0f);
            Main.draw3DText.drawText(playerPos, new Vec3(x, y, z), player, text, color.getRGB());
            if (en.field_70516_a != this.last_fuse) {
                if ((this.last_fuse == -1 && en.field_70516_a > 70) || (en.field_70516_a > 0 && en.field_70516_a % 20 == 0)) {
                    final float pitch = 1.5f - en.field_70516_a / 20.0f * 0.2f;
                    Minecraft.func_71410_x().field_71441_e.func_72980_b(x, y, z, "note.hat", 1.0f, pitch, false);
                }
                if (en.field_70516_a == 0) {
                    this.last_fuse = -1;
                }
                this.last_fuse = en.field_70516_a;
            }
        }
        if (TNTJump.tntForJump != null) {
            if (TNTJump.tntForJumpState == 0) {
                final double dX = player.field_70165_t - TNTJump.tntForJump.field_70165_t;
                final double dZ = player.field_70161_v - TNTJump.tntForJump.field_70161_v;
                double angle = Math.toDegrees(Math.atan2(dZ, dX));
                double new_angle = 0.0;
                final int[] angles = { -180, -135, -90, -45, 0, 45, 90, 135, 180 };
                double min_dist = 9999.0;
                for (final int a : angles) {
                    final double d = Math.abs(angle - a);
                    if (d < min_dist) {
                        min_dist = d;
                        new_angle = a;
                    }
                }
                angle = Math.toRadians(new_angle);
                double distance = 3.0;
                if (TNTJump.tntForJump.func_70011_f(player.field_70165_t, TNTJump.tntForJump.field_70163_u, player.field_70161_v) <= 1.0) {
                    distance = 0.0;
                    angle = 0.0;
                }
                final double new_x = TNTJump.tntForJump.field_70165_t + distance * Math.cos(angle);
                final double new_z = TNTJump.tntForJump.field_70161_v + distance * Math.sin(angle);
                final float speed = 0.004f;
                double dirX = (player.field_70165_t < new_x) ? 1.0 : -1.0;
                double dirZ = (player.field_70161_v < new_z) ? 1.0 : -1.0;
                double dist = TNTJump.tntForJump.func_70011_f(player.field_70165_t, TNTJump.tntForJump.field_70163_u, player.field_70161_v);
                if (dirX != 0.0 || dirZ != 0.0 || dist != distance) {
                    player.func_70107_b(player.field_70165_t + dirX * speed, player.field_70163_u, player.field_70161_v + dirZ * speed);
                }
                if (Math.abs(player.field_70165_t - new_x) <= speed && Math.abs(player.field_70161_v - new_z) <= speed) {
                    player.func_70107_b(new_x, player.field_70163_u, new_z);
                    dirX = 0.0;
                    dirZ = 0.0;
                }
                this.TIME_FOR_RUN = this.TIME_STRAIGHT_FOR_RUN;
                this.TIME_FOR_JUMP = this.TIME_STRAIGHT_FOR_JUMP;
                if (new_angle % 90.0 != 0.0) {
                    this.TIME_FOR_RUN = this.TIME_DIAGONAL_FOR_RUN;
                    this.TIME_FOR_JUMP = this.TIME_DIAGONAL_FOR_JUMP;
                }
                if (distance == 0.0) {
                    this.TIME_FOR_RUN = -1.0f;
                    this.TIME_FOR_JUMP = this.TIME_UP_FOR_JUMP;
                }
                else {
                    lookAtNearestTNT();
                }
                if (distance == 0.0) {
                    if (TNTJump.tntForJump.field_70516_a / 20.0f <= this.TIME_FOR_JUMP) {
                        this.pressDownKey(Minecraft.func_71410_x().field_71474_y.field_74314_A);
                        TNTJump.tntForJumpState = -1;
                        TNTJump.tntForJump = null;
                        this.unpress_keys_counter = 300;
                        this.max_height = player.field_70163_u;
                    }
                }
                else if (TNTJump.tntForJump.field_70516_a / 20.0f < this.TIME_FOR_RUN) {
                    dist = TNTJump.tntForJump.func_70011_f(player.field_70165_t, TNTJump.tntForJump.field_70163_u, player.field_70161_v);
                    if (Math.abs(dist - distance) > 0.1) {
                        ChatSender.addText(MyChatListener.PREFIX_TNT_JUMP + "&c\u041f\u043b\u043e\u0445\u043e\u0439 \u0442\u0430\u0439\u043c\u0438\u043d\u0433");
                        TNTJump.tntForJumpState = -1;
                        TNTJump.tntForJump = null;
                        return;
                    }
                    this.pressDownKey(Minecraft.func_71410_x().field_71474_y.field_151444_V);
                    this.pressDownKey(Minecraft.func_71410_x().field_71474_y.field_74351_w);
                    TNTJump.tntForJumpState = 1;
                }
            }
            else if (TNTJump.tntForJumpState == 1) {
                if (TNTJump.tntForJump.field_70516_a / 20.0f < this.TIME_FOR_JUMP) {
                    this.pressDownKey(Minecraft.func_71410_x().field_71474_y.field_74314_A);
                    TNTJump.tntForJumpState = -1;
                }
            }
            else if (TNTJump.tntForJumpState == -1) {
                TNTJump.tntForJump = null;
                this.unpress_keys_counter = 80;
            }
        }
    }
    
    void pressDownKey(final KeyBinding key) {
        KeyBinding.func_74510_a(key.func_151463_i(), true);
    }
    
    void pressUpKey(final KeyBinding key) {
        KeyBinding.func_74510_a(key.func_151463_i(), false);
    }
    
    public static void lookAtNearestTNT() {
        final List<EntityTNTPrimed> entities = (List<EntityTNTPrimed>)Minecraft.func_71410_x().field_71441_e.func_175644_a((Class)EntityTNTPrimed.class, (Predicate)new Predicate<EntityTNTPrimed>() {
            public boolean apply(final EntityTNTPrimed input) {
                return true;
            }
        });
        final EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        if (entities == null || entities.size() == 0) {
            return;
        }
        EntityTNTPrimed tnt = null;
        double min_dist = 999999.0;
        for (final EntityTNTPrimed en : entities) {
            final double x = en.field_70165_t;
            final double y = en.field_70163_u;
            final double z = en.field_70161_v;
            final double dist = en.func_70011_f(player.field_70165_t, player.field_70163_u, player.field_70161_v);
            if (dist < min_dist) {
                min_dist = dist;
                tnt = en;
            }
        }
        if (tnt == null) {
            return;
        }
        HintsFinder.lookAtPlayer(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v, tnt.field_70165_t, tnt.field_70163_u, tnt.field_70161_v);
        TNTJump.tntForJump = tnt;
        TNTJump.tntForJumpState = 0;
    }
    
    String formatTNTTime(float time) {
        final String str = "";
        if (time < 0.0f) {
            time = 0.0f;
        }
        return this.timeFormatter.format(time);
    }
    
    static {
        TNTJump.tntForJump = null;
        TNTJump.tntForJumpState = -1;
    }
}
