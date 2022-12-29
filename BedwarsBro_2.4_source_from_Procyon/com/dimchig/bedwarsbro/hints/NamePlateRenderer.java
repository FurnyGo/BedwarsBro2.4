// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.client.entity.EntityPlayerSP;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraftforge.event.entity.player.PlayerEvent;
import com.dimchig.bedwarsbro.Main;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class NamePlateRenderer
{
    static Minecraft mc;
    static boolean isCrossedNicknamesActive;
    public static ArrayList<String> friends;
    
    public NamePlateRenderer() {
        NamePlateRenderer.mc = Minecraft.func_71410_x();
    }
    
    public void updateBooleans() {
        NamePlateRenderer.isCrossedNicknamesActive = Main.getConfigBool(Main.CONFIG_MSG.BETTER_TAB_CROSSED_NICKNAMES);
    }
    
    @SubscribeEvent
    public void onMyRender(final PlayerEvent.NameFormat event) {
        String color = "&f";
        final CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor(event.entityPlayer);
        if (team_color != CustomScoreboard.TEAM_COLOR.NONE) {
            color = "&" + CustomScoreboard.getCodeByTeamColor(team_color);
        }
        final String prefix = this.getPrefixByName(event.username);
        String display_name = event.displayname;
        display_name = ChatSender.parseText(display_name);
        if (prefix.length() > 0 && display_name.length() > 2) {
            final MyChatListener chatListener = Main.chatListener;
            if (!MyChatListener.IS_IN_GAME) {
                color = "";
            }
        }
        event.displayname = ColorCodesManager.replaceColorCodesInString(prefix + color + display_name);
        this.updateGameTab();
    }
    
    public void updateGameTab() {
        if (NamePlateRenderer.mc.func_147114_u() == null || NamePlateRenderer.mc.func_147114_u().func_175106_d() == null) {
            return;
        }
        final Collection<NetworkPlayerInfo> players = (Collection<NetworkPlayerInfo>)NamePlateRenderer.mc.func_147114_u().func_175106_d();
        int cnt = 0;
        final MyChatListener chatListener = Main.chatListener;
        final boolean isInGame = MyChatListener.IS_IN_GAME;
        for (final NetworkPlayerInfo info : players) {
            if (++cnt > 500) {
                return;
            }
            if (info.func_178845_a() == null) {
                continue;
            }
            if (info.func_178850_i() == null) {
                continue;
            }
            final String player_name = info.func_178845_a().getName();
            final String player_prefix = this.getPrefixByName(player_name);
            String color_code = "&7";
            String donation = "";
            final boolean hasFlag = Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175243_a(info).contains("\u2691") || info.func_178850_i().func_96668_e().contains("\u2691");
            if (hasFlag) {}
            if (isInGame || hasFlag) {
                color_code = "&7";
                if (NamePlateRenderer.isCrossedNicknamesActive) {
                    color_code += "&m";
                }
                if (info.func_178850_i() == null) {
                    continue;
                }
                final String team_name = info.func_178850_i().func_96661_b();
                final MyChatListener chatListener2 = Main.chatListener;
                final CustomScoreboard.TEAM_COLOR c = MyChatListener.getEntityTeamColorByTeam(team_name);
                if (c != CustomScoreboard.TEAM_COLOR.NONE) {
                    color_code = "&" + CustomScoreboard.getCodeByTeamColor(c);
                }
            }
            else {
                donation = info.func_178850_i().func_96668_e().trim();
                if (donation.length() > 4) {
                    donation = donation.replace("§0", "&f");
                    color_code = donation.substring(donation.length() - 2, donation.length());
                    donation += " ";
                }
                else {
                    donation = "";
                }
            }
            if (player_prefix.length() > 0) {
                final MyChatListener chatListener3 = Main.chatListener;
                if (!MyChatListener.IS_IN_GAME) {
                    color_code = "";
                }
            }
            String new_name = player_prefix + donation + color_code + player_name;
            new_name = ChatSender.parseText(new_name);
            if (NamePlateRenderer.friends.contains(player_name)) {
                final StringBuilder sb = new StringBuilder();
                final MyChatListener chatListener4 = Main.chatListener;
                new_name = sb.append(MyChatListener.PREFIX_FRIEND_IN_CHAT).append(new_name).toString();
            }
            final MyChatListener chatListener5 = Main.chatListener;
            MyChatListener.PREFIX_FRIEND_IN_CHAT = "&c&l<&6&l\u0414&e&l\u0440&a&l\u0443&b&l\u0433&d&l> &r";
            info.func_178859_a((IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(new_name)));
        }
    }
    
    public void printSameUsersInGame() {
        if (NamePlateRenderer.mc == null || NamePlateRenderer.mc.field_71439_g == null) {
            return;
        }
        if (Main.getPropAuthorPrefix().equals("none")) {
            return;
        }
        if (NamePlateRenderer.mc.func_147114_u() == null || NamePlateRenderer.mc.func_147114_u().func_175106_d() == null) {
            return;
        }
        final Collection<NetworkPlayerInfo> players = (Collection<NetworkPlayerInfo>)NamePlateRenderer.mc.func_147114_u().func_175106_d();
        if (players == null || players.size() == 0) {
            return;
        }
        final EntityPlayerSP mod_player = NamePlateRenderer.mc.field_71439_g;
        int cnt = 0;
        final ArrayList<String> arr = new ArrayList<String>();
        for (final NetworkPlayerInfo info : players) {
            if (info.func_178845_a() != null) {
                if (info.func_178850_i() == null) {
                    continue;
                }
                final String player_name = info.func_178845_a().getName();
                final String player_prefix = this.getPrefixByName(player_name);
                if (player_prefix.length() == 0) {
                    continue;
                }
                if (mod_player.func_70005_c_().equals(player_name)) {
                    continue;
                }
                final String team_name = info.func_178850_i().func_96661_b();
                final MyChatListener chatListener = Main.chatListener;
                final CustomScoreboard.TEAM_COLOR c = MyChatListener.getEntityTeamColorByTeam(team_name);
                String color_code = "&f";
                if (c != CustomScoreboard.TEAM_COLOR.NONE) {
                    color_code = "&" + CustomScoreboard.getCodeByTeamColor(c);
                }
                ++cnt;
                arr.add(color_code + ColorCodesManager.removeColorCodes(player_name));
            }
        }
        if (cnt <= 0 || arr.size() == 0) {
            return;
        }
        final String text = MyChatListener.PREFIX_BEDWARSBRO + "\u0421 \u0442\u043e\u0431\u043e\u0439 \u0432 \u043a\u0430\u0442\u043a\u0435 \u0438\u0433\u0440\u0430\u0435\u0442 &aDimChig &f\u043f\u043e\u0434 \u043d\u0438\u043a\u043e\u043c \"" + arr.get(0) + "&f\"! \u042d\u0442\u043e \u0430\u0432\u0442\u043e\u0440 \u0438 \u0441\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043c\u043e\u0434\u0430 &r&cBedwars&fBro!";
        ChatSender.addText(text);
    }
    
    public String getPrefixByName(final String player_name) {
        if (!Main.isPropUserAdmin(player_name)) {
            return "";
        }
        final String prefix = Main.getPropAuthorPrefix();
        if (prefix.equals("none")) {
            return "";
        }
        if (prefix == null || prefix.length() == 0) {
            return "&c&l[&6&l\u0421&e&l\u043e&a&l\u0437&b&l\u0434&d&l\u0430&c&l\u0442&6&l\u0435&e&l\u043b&a&l\u044c&c&l]&r ";
        }
        return prefix;
    }
    
    static {
        NamePlateRenderer.isCrossedNicknamesActive = true;
        NamePlateRenderer.friends = new ArrayList<String>();
    }
}
