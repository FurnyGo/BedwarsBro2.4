// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.entity.Entity;
import net.minecraft.util.IChatComponent;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import com.dimchig.bedwarsbro.commands.CommandHintsFinderLookAtPlayer;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.client.Minecraft;

public class HintsFinder
{
    static Minecraft mc;
    
    public HintsFinder() {
        HintsFinder.mc = Minecraft.func_71410_x();
    }
    
    public static void findAll(final boolean withMessageOnFail) {
        final ArrayList<HintsPlayerScanner.BWPlayer> players = HintsPlayerScanner.scanPlayers();
        if (players == null) {
            ChatSender.addText(MyChatListener.PREFIX_HINT_FINDER + "&c\u041e\u0448\u0438\u0431\u043a\u0430");
            return;
        }
        if (players.size() <= 1) {
            OnMyTickEvent.FINDER_IS_SEARCH_LOOP = true;
            return;
        }
        final String mod_player_name = ColorCodesManager.removeColorCodes(HintsFinder.mc.field_71439_g.func_70005_c_());
        final List<CustomScoreboard.BedwarsTeam> teams = CustomScoreboard.readBedwarsGame();
        CustomScoreboard.TEAM_COLOR mod_team_color = CustomScoreboard.TEAM_COLOR.NONE;
        final double mod_pos_x = Minecraft.func_71410_x().field_71439_g.field_70165_t;
        final double mod_pos_y = Minecraft.func_71410_x().field_71439_g.field_70163_u;
        final double mod_pos_z = Minecraft.func_71410_x().field_71439_g.field_70161_v;
    Label_0193:
        for (final CustomScoreboard.BedwarsTeam team : teams) {
            for (final CustomScoreboard.BedwarsPlayer p : team.players) {
                if (p.name.equals(mod_player_name)) {
                    mod_team_color = team.color;
                    break Label_0193;
                }
            }
        }
        String str = "";
        int cnt_found = 0;
        HintsPlayerScanner.BWPlayer closest_player = null;
        boolean isFirst = true;
        int min_distance = 9999;
        for (final HintsPlayerScanner.BWPlayer player : players) {
            if (player.name.equals(mod_player_name)) {
                continue;
            }
            boolean isTeamFound = false;
            for (final CustomScoreboard.BedwarsTeam team2 : teams) {
                for (final CustomScoreboard.BedwarsPlayer p2 : team2.players) {
                    if (p2.name.equals(player.name)) {
                        player.team_color = team2.color;
                        isTeamFound = true;
                        break;
                    }
                }
                if (isTeamFound) {
                    break;
                }
            }
            if (player.team_color == CustomScoreboard.TEAM_COLOR.NONE) {
                continue;
            }
            if (player.team_color == mod_team_color) {
                continue;
            }
            final int dist = (int)Math.sqrt(Math.pow(mod_pos_x - player.posX, 2.0) + Math.pow(mod_pos_z - player.posZ, 2.0));
            if (dist < min_distance) {
                min_distance = dist;
                closest_player = player;
            }
            if (isFirst) {
                isFirst = false;
            }
            String stars = "";
            final String player_color_code = "&" + CustomScoreboard.getCodeByTeamColor(player.team_color);
            str = MyChatListener.PREFIX_HINT_FINDER + player_color_code + "" + player.name;
            String hoverText = "&7(&f" + (int)player.posX + "&7, &f" + (int)player.posY + "&7, &f" + (int)player.posY + "&7, &c" + dist + "&7)";
            if (player.armourLevel == BWItemsHandler.BWItemArmourLevel.LEATHER) {
                hoverText += " &7\u0411\u0435\u0437 \u0431\u0440\u043e\u043d\u0438";
                stars += "\u25cb";
            }
            if (player.armourLevel == BWItemsHandler.BWItemArmourLevel.CHAIN) {
                hoverText += " &7\u041a\u043e\u043b\u044c\u0447\u0443\u0433\u0430";
            }
            if (player.armourLevel == BWItemsHandler.BWItemArmourLevel.IRON) {
                hoverText += " &f\u0416\u0435\u043b\u0435\u0437\u043d\u0438\u043a";
            }
            if (player.armourLevel == BWItemsHandler.BWItemArmourLevel.DIAMOND) {
                hoverText += " &b\u0410\u043b\u043c\u0430\u0437\u043d\u0438\u043a";
                stars += "&b&l*";
            }
            if (player.item_in_hand != null) {
                if (player.item_in_hand.type == BWItemsHandler.BWItemType.BOW) {
                    hoverText += "&8, &c\u041b\u0443\u0447\u043d\u0438\u043a";
                    stars += "&c&l*";
                }
                if (player.item_in_hand.type == BWItemsHandler.BWItemType.SWORD) {
                    if (player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.WOOD) {
                        hoverText += "&8, &7\u0414\u0435\u0440\u0435\u0432\u044f\u043d\u043d\u044b\u0439 \u043c\u0435\u0447";
                    }
                    if (player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.STONE) {
                        hoverText += "&8, &7\u041a\u0430\u043c\u0435\u043d\u043d\u044b\u0439 \u043c\u0435\u0447";
                    }
                    if (player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.IRON) {
                        hoverText += "&8, &f\u0416\u0435\u043b\u0435\u0437\u043d\u044b\u0439 \u043c\u0435\u0447";
                    }
                    if (player.item_in_hand.toolLevel == BWItemsHandler.BWItemToolLevel.DIAMOND) {
                        hoverText += "&8, &b\u0410\u043b\u043c\u0430\u0437\u043d\u044b\u0439 \u043c\u0435\u0447";
                        stars += "&6&l*";
                    }
                }
                if (player.item_in_hand.type == BWItemsHandler.BWItemType.POTION_STRENGTH) {
                    hoverText += "&8, &c\u0421\u0438\u043b\u043a\u0430";
                    stars += "&4&l*";
                }
                if (player.item_in_hand.type == BWItemsHandler.BWItemType.PEARL) {
                    hoverText += "&8, &a\u041f\u0435\u0440\u043b";
                    stars += "&9&l*";
                }
            }
            str += stars;
            final EntityPlayerSP mod_player = HintsFinder.mc.field_71439_g;
            str = str + " " + player_color_code + getArrowDirection(player.posX, player.posZ);
            final String commandText = "/" + CommandHintsFinderLookAtPlayer.command_text + " " + player.posX + " " + player.posY + " " + player.posZ + " " + player.name;
            final IChatComponent mainComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(str));
            final IChatComponent hoverComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hoverText));
            final HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent);
            final ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandText);
            mainComponent.func_150256_b().func_150209_a(hover);
            mainComponent.func_150256_b().func_150241_a(click);
            Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
            ++cnt_found;
        }
        if (cnt_found > 0) {
            OnMyTickEvent.FINDER_IS_SEARCH_LOOP = false;
            if (!withMessageOnFail) {
                MyChatListener.playSound(MyChatListener.SOUND_PARTY_CHAT);
            }
        }
        else {
            OnMyTickEvent.FINDER_IS_SEARCH_LOOP = true;
        }
    }
    
    public static void findAndlookAtPlayer(final double posX, final double posY, final double posZ, final String p_name) {
        final Entity player = (Entity)Minecraft.func_71410_x().field_71439_g;
        final ArrayList<HintsPlayerScanner.BWPlayer> players = HintsPlayerScanner.scanPlayers();
        if (players != null && players.size() > 1) {
            for (final HintsPlayerScanner.BWPlayer p : players) {
                if (p.name.equals(p_name)) {
                    lookAtPlayer(player.field_70165_t, player.field_70163_u, player.field_70161_v, p.posX, p.posY, p.posZ);
                    return;
                }
            }
        }
        lookAtPlayer(player.field_70165_t, player.field_70163_u, player.field_70161_v, posX, posY, posZ);
    }
    
    public static void lookAtPlayer(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double dX = x1 - x2;
        final double dY = y1 - y2;
        final double dZ = z1 - z2;
        final float yaw = (float)Math.atan2(dZ, dX);
        final float pitch = (float)(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + 3.141592653589793);
        final float t_yaw = myMap(yaw, -3.1415927f, 3.1415927f, -180.0f, 180.0f);
        final float t_pitch = myMap(pitch, 3.1415927f, 6.2831855f, 90.0f, -90.0f);
        rotateTo((Entity)Minecraft.func_71410_x().field_71439_g, t_yaw + 90.0f, t_pitch);
    }
    
    public static String getArrowDirection(final double x, final double z) {
        float angle_diff = (float)Math.toDegrees(Math.atan2(z - HintsFinder.mc.field_71439_g.field_70161_v, x - HintsFinder.mc.field_71439_g.field_70165_t));
        angle_diff += (180.0f - HintsFinder.mc.field_71439_g.field_70177_z) % 360.0f;
        angle_diff = (angle_diff + 90.0f + 720.0f) % 360.0f;
        final int[] angles = { 0, 45, 90, 135, 180, 225, 270, 315, 360 };
        final String[] angle_strings = { "\u2191", "\u2197", "\u2192", "\u2198", "\u2193", "\u2199", "\u2190", "\u2196", "\u2191" };
        double min_diff = 1000.0;
        String angle_str = "-";
        for (int i = 0; i < angles.length; ++i) {
            final double diff = Math.abs(angles[i] - angle_diff);
            if (diff < min_diff) {
                min_diff = diff;
                angle_str = angle_strings[i];
            }
        }
        return angle_str;
    }
    
    public static float myMap(final float value, final float leftMin, final float leftMax, final float rightMin, final float rightMax) {
        final float leftSpan = leftMax - leftMin;
        final float rightSpan = rightMax - rightMin;
        final float valueScaled = (value - leftMin) / leftSpan;
        return rightMin + valueScaled * rightSpan;
    }
    
    public static void rotateTo(final Entity player, final float target_angle_yaw, final float target_angle_pitch) {
        final float prev_rot_yaw = player.field_70177_z;
        final float prev_rot_pitch = player.field_70125_A;
        final float angle_yaw = target_angle_yaw - prev_rot_yaw;
        final float angle_pitch = target_angle_pitch - prev_rot_pitch;
        rotateAngles(player, angle_yaw, angle_pitch);
        final double delta_yaw = player.field_70177_z - prev_rot_yaw;
        final double delta_pitch = player.field_70125_A - prev_rot_pitch;
        if (Math.abs(target_angle_pitch) > 90.0f) {
            return;
        }
        if (target_angle_yaw != player.field_70177_z || target_angle_pitch != player.field_70125_A) {
            rotateTo(player, target_angle_yaw, target_angle_pitch);
        }
    }
    
    public static void rotateAngles(final Entity player, final float angle_yaw, final float angle_pitch) {
        player.func_70082_c(angle_yaw / 0.15f, angle_pitch / -0.15f);
    }
}
