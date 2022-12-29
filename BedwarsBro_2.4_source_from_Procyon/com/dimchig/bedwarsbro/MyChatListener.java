// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.commands.CommandFriends;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import java.util.Comparator;
import com.dimchig.bedwarsbro.hints.WinEmote;
import java.text.DecimalFormat;
import com.dimchig.bedwarsbro.hints.HintsBaseRadar;
import com.dimchig.bedwarsbro.particles.ParticleController;
import com.dimchig.bedwarsbro.hints.LobbyBlockPlacer;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import com.dimchig.bedwarsbro.hints.BedwarsMeow;
import com.dimchig.bedwarsbro.commands.CommandMute;
import net.minecraft.entity.player.EntityPlayer;
import com.dimchig.bedwarsbro.hints.HintsBedScanner;
import com.dimchig.bedwarsbro.hints.LightningLocator;
import com.dimchig.bedwarsbro.gui.GuiMinimap;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import java.util.Date;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import java.util.TimerTask;
import java.util.Timer;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import java.util.regex.Pattern;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import com.dimchig.bedwarsbro.hints.BWBed;

public class MyChatListener
{
    public static boolean IS_MOD_ACTIVE;
    public static boolean IS_IN_GAME;
    public static long GAME_start_time;
    public static int GAME_total_death;
    public static int GAME_total_kills;
    public static int GAME_total_beds;
    public static String nickChanger;
    public static BWBed GAME_BED;
    public static GameRecovery GAME_RECOVERY;
    public static boolean removeNextMessage;
    public static int TIME_MEOW_MESSAGES_CHECK_FREQUENCY;
    public static List<MsgMeowQuee> meowMessagesQuee;
    public static MyStatistic myStatistic;
    public static String DIMCHIG_NAME;
    public static String DIMCHIG_NAME_GOLD;
    public static String SOUND_BED_BROKEN;
    public static String SOUND_TEAM_DESTROYED;
    public static String SOUND_GAME_END;
    public static String SOUND_REJECT;
    public static String SOUND_UPGRADE_BOUGHT;
    public static String SOUND_TRAP_ACTIVATED;
    public static String SOUND_PLAYER_STATS;
    public static String SOUND_TEAM_CHOSEN;
    public static String SOUND_PARTY_CREATED;
    public static String SOUND_PARTY_CHAT;
    public static String SOUND_RADAR_FAR;
    public static String SOUND_RADAR_CLOSE;
    public static String SOUND_EMOTE;
    public static String PREFIX_BEDWARSBRO;
    public static String PREFIX_PARTY;
    public static String PREFIX_BED;
    public static String PREFIX_TEAM;
    public static String PREFIX_ANTICHEAT;
    public static String PREFIX_UPGRADES;
    public static String PREFIX_HINT;
    public static String PREFIX_HINT_BED_SCANNER;
    public static String PREFIX_HINT_RADAR;
    public static String PREFIX_HINT_FINDER;
    public static String PREFIX_HINT_RESOURCES_FINDER;
    public static String PREFIX_ANTIFLOOD;
    public static String PREFIX_DANGER_ALERT;
    public static String PREFIX_TNT_JUMP;
    public static String PREFIX_WATER_DROP;
    public static String PREFIX_BEDWARS_MEOW;
    public static String PREFIX_DIMCHIG_JOINED;
    public static String PREFIX_ZERO_DEATH;
    public static String PREFIX_FRIENDS;
    public static String PREFIX_MUTED;
    public static String PREFIX_FRIEND_IN_CHAT;
    public static int GAME_MAP_PLAYERS_MAX_SIZE;
    public static ArrayList<ChatMessage> chatMessages;
    private static long time_last_meow_message;
    private static MsgMeowQuee quee_last_meow_message;
    
    public MyChatListener() {
        MyChatListener.IS_MOD_ACTIVE = true;
        MyChatListener.IS_IN_GAME = false;
        MyChatListener.removeNextMessage = false;
        initChatMessages();
        for (final ChatMessage chatMessage : MyChatListener.chatMessages) {
            for (final ChatMessage chatMessage2 : MyChatListener.chatMessages) {
                if (chatMessage.type == chatMessage2.type && !chatMessage.message.equals(chatMessage2.message)) {
                    ChatSender.addText("DUBLICATE CHAT MESSAGE - " + chatMessage.type);
                }
            }
        }
        MyChatListener.meowMessagesQuee = new ArrayList<MsgMeowQuee>();
    }
    
    public static void initChatMessages() {
        (MyChatListener.chatMessages = new ArrayList<ChatMessage>()).add(new ChatMessage(CHAT_MESSAGE.CHAT_LEFT_GAME, "§r§6§lBedWars §r§8%skip% §r%player% §r§f\u043f\u043e\u043a\u0438\u043d\u0443\u043b \u0438\u0433\u0440\u0443§r", new String[] { "skip", "player", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LEFT_PREGAME, "§r§6§lBedWars §r§8%skip% §r%player% §r§f\u043f\u043e\u043a\u0438\u043d\u0443\u043b \u0438\u0433\u0440\u0443 §r§c%cnt_players_current%/%cnt_players_total%§r", new String[] { "skip", "player", "cnt_players_current", "cnt_players_total" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_STARTED, "§r§e\u0417\u0430\u0449\u0438\u0442\u0438 \u0441\u0432\u043e\u044e \u043a\u0440\u043e\u0432\u0430\u0442\u044c \u0438 \u0441\u043b\u043e\u043c\u0430\u0439 \u0447\u0443\u0436\u0438\u0435 \u043a\u0440\u043e\u0432\u0430\u0442\u0438.§r", new String[] { "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_JOINED_PREGAME, "§r§6§lBedWars §r§8%skip% §r%player% §r§f\u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u043b\u0441\u044f \u043a \u0438\u0433\u0440\u0435 §r§a%cnt_players_current%/%cnt_players_total%§r", new String[] { "skip", "player", "cnt_players_current", "cnt_players_total" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_JOINED_MIDGAME, "§r§6§lBedWars §r§8%skip% §r%player% §r§f\u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u043b\u0441\u044f \u043a \u0438\u0433\u0440\u0435§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_SUICIDE, "§r§6§lBedWars §r§8%skip% §r%player% §r§f\u043f\u043e\u0433\u0438\u0431.§r", new String[] { "skip", "player", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_SUICIDE_VOID, "§r§6§lBedWars §r§8%skip% §r%player% §r§f\u0443\u043f\u0430\u043b \u0432 \u0431\u0435\u0437\u0434\u043d\u0443.§r", new String[] { "skip", "player", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_KILLED_BY_VOID, "§r§6§lBedWars §r§8%skip% §r%victim% §r§f\u0431\u044b\u043b \u0441\u043a\u0438\u043d\u0443\u0442 \u0432 \u0431\u0435\u0437\u0434\u043d\u0443 \u0438\u0433\u0440\u043e\u043a\u043e\u043c §r%killer%§r§f.§r", new String[] { "skip", "victim", "killer" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_KILLED_BY_PLAYER, "§r§6§lBedWars §r§8%skip% §r%victim% §r§f\u0431\u044b\u043b \u0443\u0431\u0438\u0442 \u0438\u0433\u0440\u043e\u043a\u043e\u043c §r%killer%§r§f.§r", new String[] { "skip", "victim", "killer" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_TEAM_DESTROYED, "§r§6§lBedWars §r§8%skip% §r§f\u041a\u043e\u043c\u0430\u043d\u0434\u0430 §r%team% §r§f\u0443\u043d\u0438\u0447\u0442\u043e\u0436\u0435\u043d\u0430!§r", new String[] { "skip", "team", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_TEAM_BED_BROKEN, "§r§6§lBedWars §r§8%skip% §r%player% §r§f\u0440\u0430\u0437\u0440\u0443\u0448\u0438\u043b \u043a\u0440\u043e\u0432\u0430\u0442\u044c \u043a\u043e\u043c\u0430\u043d\u0434\u044b §r%team%§r§f!§r", new String[] { "skip", "player", "team" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_TEAM_COLOR_CHOSEN, "§r§6§lBedWars §r§8%skip% §r§f\u0412\u044b \u0432\u044b\u0431\u0440\u0430\u043b\u0438 \u043a\u043e\u043c\u0430\u043d\u0434\u0443 §r%team%§r§f!§r", new String[] { "skip", "team", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_TEAM_ALREADY_CHOSEN, "§r§6§lBedWars §r§8%skip% §r§c\u0412\u044b \u0443\u0436\u0435 \u0432\u044b\u0431\u0440\u0430\u043b\u0438 \u0442\u0435\u043a\u0443\u0449\u0443\u044e \u043a\u043e\u043c\u0430\u043d\u0434\u0443.§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_TEAM_IS_FULL, "§r§6§lBedWars §r§8%skip% §r§c\u0414\u0430\u043d\u043d\u0430\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u0430 \u0437\u0430\u043f\u043e\u043b\u043d\u0435\u043d\u0430.§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_SHOP_ITEM_BOUGHT, "§r§7\u0412\u044b \u043a\u0443\u043f\u0438\u043b\u0438 §r§a%item%§r §ax%amount%§r", new String[] { "item", "amount" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_SHOP_NOT_ENOUGH_RESOURCES, "§r§6§lBedWars §r§8%skip% §r§c\u0423 \u0412\u0430\u0441 \u043d\u0435\u0434\u043e\u0441\u0442\u0430\u0442\u043e\u0447\u043d\u043e \u0440\u0435\u0441\u0443\u0440\u0441\u043e\u0432!§r", new String[] { "skip", "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_UPGRADE_BOUGHT, "§r§6§lBedWars §r§8%skip% §r§f\u0418\u0433\u0440\u043e\u043a §r§f%player% §r§f\u043f\u0440\u043e\u043a\u0430\u0447\u0430\u043b \u0443\u043b\u0443\u0447\u0448\u0435\u043d\u0438\u0435 §r§b%upgrade% §r§f\u0434\u043e \u0443\u0440\u043e\u0432\u043d\u044f §r§b%level%§r§f!", new String[] { "skip", "player", "upgrade", "level" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GENERATOR_DIAMOND_LEVELED_UP, "§r§6§lBedWars §r§8%skip% §r§f\u0413\u0435\u043d\u0435\u0440\u0430\u0442\u043e\u0440\u044b §r§b\u0430\u043b\u043c\u0430\u0437\u043e\u0432§r§f \u043f\u0440\u043e\u043a\u0430\u0447\u0430\u043d\u044b \u0434\u043e \u0443\u0440\u043e\u0432\u043d\u044f §r§a%level%§r§f", new String[] { "skip", "level", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GENERATOR_EMERALD_LEVELED_UP, "§r§6§lBedWars §r§8%skip% §r§f\u0413\u0435\u043d\u0435\u0440\u0430\u0442\u043e\u0440\u044b §r§a\u0438\u0437\u0443\u043c\u0440\u0443\u0434\u043e\u0432§r§f \u043f\u0440\u043e\u043a\u0430\u0447\u0430\u043d\u044b \u0434\u043e \u0443\u0440\u043e\u0432\u043d\u044f §r§a%level%§r§f.§r", new String[] { "skip", "level", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_TRAP_ACTIVATED, "§r§6§lBedWars §r§8%skip% §r§c\u041b\u043e\u0432\u0443\u0448\u043a\u0430 \u0441\u0440\u0430\u0431\u043e\u0442\u0430\u043b\u0430, \u043d\u0430 \u0432\u0430\u0448\u0435\u043c \u043e\u0441\u0442\u0440\u043e\u0432\u0435 \u0432\u0440\u0430\u0433!§r", new String[] { "skip", "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_SERVER_RESTART, "§r§c\u041f\u0435\u0440\u0435\u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0447\u0435\u0440\u0435\u0437 §r§c§l%seconds%§r§c \u0441\u0435\u043a\u0443\u043d\u0434!§r", new String[] { "seconds", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_TELEPORTATION_TO_HUB, "§r§6§lBedWars §r§8%skip% §r§a\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u044f \u0432 \u043b\u043e\u0431\u0431\u0438....§r", new String[] { "skip", "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_CONNECTING_TO_LOBBY, "§a\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0435 \u043a %lobby%...§r", new String[] { "lobby", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_HUB_CHAT_PLAYER_MESSAGE, "§r§r§r%player% §r§8 §r§8%skip% §r§7§r%message%$bwbrotagend$", new String[] { "player", "skip", "message" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_YOU_WERE_KICKED, "§r§f\u0422\u044b \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0435\u043d \u0432 \u043b\u043e\u0431\u0431\u0438 §r§8\u25b8 §r§c\u0418\u0437\u0432\u0438\u043d\u0438\u0442\u0435, \u043d\u043e \u0432\u0430\u0441 \u043a\u0438\u043a\u043d\u0443\u043b\u0438", new String[] { "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_STARTS_IN_SECONDS, "§r§6§lBedWars §r§8%skip% §r§e\u0414\u043e \u0441\u0442\u0430\u0440\u0442\u0430 \u043e\u0441\u0442\u0430\u043b\u043e\u0441\u044c §r§c%seconds%§r§e \u0441\u0435\u043a\u0443\u043d\u0434...§r", new String[] { "skip", "seconds", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_CHAT_LOCAL, "§r§r%teamcolor%[\u2691] %player% §8%skip% §7§r%message%§r", new String[] { "teamcolor", "player", "skip", "message" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_CHAT_GLOBAL, "§r§r%teamcolor%[\u0412\u0441\u0435\u043c] %player% §8%skip% §7§r%message%§r", new String[] { "teamcolor", "player", "skip", "message" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_CHAT_SPECTATOR, "§r§r§7\u041d\u0430\u0431\u043b\u044e\u0434\u0430\u0442\u0435\u043b\u044c §8| §f%player% §8%skip% §7§r%message%§r", new String[] { "player", "skip", "message" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_CHAT_PREGAME, "§r§r§f%player% §8\u2192 §7§r§7§7%message%§r", new String[] { "player", "message" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_PREGAME_FASTSTART_REJECT, "§c\u041d\u0435\u0434\u043e\u0441\u0442\u0430\u0442\u043e\u0447\u043d\u043e \u043f\u0440\u0430\u0432 \u0434\u043b\u044f \u0437\u0430\u043f\u0443\u0441\u043a\u0430 \u0438\u0433\u0440\u044b.§r", new String[] { "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_DONATER_GREETING, "§r§c§l\u203a §r§f\u0418\u0433\u0440\u043e\u043a §r%player% §r§f%greeting%§r", new String[] { "player", "greeting" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_PLAYER_BANNED, "§r§6\u0410\u043d\u0442\u0438\u0427\u0438\u0442§r§8 %skip% §r\u0418\u0433\u0440\u043e\u043a §r§c%player% §r§f\u0431\u044b\u043b \u0432\u0440\u0435\u043c\u0435\u043d\u043d\u043e §r§c\u0437\u0430\u0431\u0430\u043d\u0435\u043d §r§f\u043f\u043e \u043f\u043e\u0434\u043e\u0437\u0440\u0435\u043d\u0438\u044e \u0432 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0438 \u0447\u0438\u0442\u043e\u0432.§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_PREGAME_NOT_ENOUGH_PLAYERS_TO_START, "§r§6§lBedWars §r§8%skip% §r§c\u041d\u0435\u0434\u043e\u0441\u0442\u0430\u0442\u043e\u0447\u043d\u043e \u0438\u0433\u0440\u043e\u043a\u043e\u0432 \u0434\u043b\u044f \u0441\u0442\u0430\u0440\u0442\u0430.§r", new String[] { "skip" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_BEDWARS_END_TEAM_WON, "§r§f                 \u041f\u043e\u0431\u0435\u0434\u0438\u043b\u0430 \u043a\u043e\u043c\u0430\u043d\u0434\u0430 - §r%team%§r", new String[] { "team" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_BEDWARS_END_TOP_KILLER, "§r%skip%                      §r%place% §r§7- §r§7%player% §r§7(%kills_cnt%)§r", new String[] { "skip", "place", "player", "kills_cnt" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_BEDWARS_END_TOP_KILLER_UNKNOWN, "§r%skip%                      §r%place% §r§7- §r§cN/A§r", new String[] { "skip", "place" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_INVITE, "§r§6Party §r§8%skip% §r§f\u0412\u044b \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u043b\u0438 \u043f\u0440\u0438\u0433\u043b\u0430\u0448\u0435\u043d\u0438\u0435 \u0438\u0433\u0440\u043e\u043a\u0443 §r§e%player%§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_INVITE_REJECTED, "§r§6Party §r§8%skip% §r§c\u0412\u0430\u0448\u0435 \u043f\u0440\u0438\u0433\u043b\u0430\u0448\u0435\u043d\u0438\u0435 \u0432 \u043f\u0430\u0442\u0438 \u0438\u0433\u0440\u043e\u043a\u0430 §r§c§l%player% §r§c\u0438\u0441\u0442\u0435\u043a\u043b\u043e§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_WARP, "§r§6Party §r§8%skip% §r§c\u0412\u044b \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u043b\u0438 \u0437\u0430\u043f\u0440\u043e\u0441 \u043d\u0430 \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0435\u043d\u0438\u0435 \u0438\u0433\u0440\u043e\u043a\u043e\u0432 \u0438\u0437 \u043f\u0430\u0442\u0438 \u043a \u0432\u0430\u043c§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_PLAYER_OFFLINE, "§r§6Party §r§8%skip% §r§c\u0418\u0433\u0440\u043e\u043a §r§c§l%player%§r§c \u043a\u0438\u043a\u043d\u0443\u0442 \u0438\u0437 \u043f\u0430\u0442\u0438 \u043f\u043e\u0441\u043a\u043e\u043b\u044c\u043a\u0443 \u043e\u043d \u043e\u0444\u0444\u043b\u0430\u0439\u043d %reason%§r", new String[] { "skip", "player", "reason" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_DISBANDED, "§r§6Party §r§8%skip% §r§c\u041f\u0430\u0442\u0438 \u0440\u0430\u0441\u0444\u043e\u0440\u043c\u0438\u0440\u043e\u0432\u0430\u043d\u043e.§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_STATS_PLAYER, "§r§e§l\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 §r§8%skip% §r§f%player%§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_STATS_CATEGORY, "§r§e%skip% §r§f%category%: §r§e%cnt% §r§a(\u041c\u0435\u0441\u0442\u043e \u0432 \u0442\u043e\u043f\u0435 %place%)§r", new String[] { "skip", "category", "cnt", "place" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_ALREADY_IN_PARTY, "§r§6Party §r§8%skip% §r§c\u0418\u0433\u0440\u043e\u043a \u0443\u0436\u0435 \u0441\u043e\u0441\u0442\u043e\u0438\u0442 \u0432 \u043f\u0430\u0442\u0438!§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_OFFLINE, "§r§6Party §r§8%skip% §r§f\u0418\u0433\u0440\u043e\u043a \u0441 \u0442\u0430\u043a\u0438\u043c \u043d\u0438\u043a\u043e\u043c \u043d\u0435 \u0432 \u0441\u0435\u0442\u0438§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_YOU_ARE_NOT_IN_PARTY, "§r§6Party §r§8%skip% §r§c\u0412\u044b \u043d\u0435 \u0441\u043e\u0441\u0442\u043e\u0438\u0442\u0435 \u0432 \u043f\u0430\u0442\u0438§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_PLAYER_KICKED, "§r§6Party §r§8%skip% §r§f\u0418\u0433\u0440\u043e\u043a §r§e%player%§r§f \u0431\u044b\u043b \u043a\u0438\u043a\u043d\u0443\u0442 \u0438\u0437 \u043f\u0430\u0442\u0438§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_PLAYER_LEFT, "§r§6Party §r§8%skip% §r§f\u0418\u0433\u0440\u043e\u043a §r§e%player%§r§f \u0432\u044b\u0448\u0435\u043b \u0438\u0437 \u043f\u0430\u0442\u0438§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_CHAT_ENTER_MESSAGE, "§r§6Party §r§8%skip% §r§c\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0441\u0432\u043e\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_YOU_ACCEPTED_REQUEST, "§r§6Party §r§8%skip% §r§f\u0412\u044b \u043f\u0440\u0438\u043d\u044f\u043b\u0438 \u0437\u0430\u043f\u0440\u043e\u0441!§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_NEW_LEADER, "§r§6Party §r§8%skip% §r§f\u0418\u0433\u0440\u043e\u043a §r§e%player% §r§f\u0441\u0442\u0430\u043b \u043d\u043e\u0432\u044b\u043c \u0441\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u0435\u043c \u043f\u0430\u0442\u0438§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_COMMANDS_DONT_WORK_IN_LOBBY, "§r§6Party §r§8%skip% §r§f\u041a\u043e\u043c\u0430\u043d\u0434\u044b \u043f\u0430\u0442\u0438 \u0440\u0430\u0431\u043e\u0442\u0430\u044e\u0442 \u0442\u043e\u043b\u044c\u043a\u043e \u0432 \u0431\u0435\u0434\u0440\u0432\u0430\u0440\u0441 \u043b\u043e\u0431\u0431\u0438/\u0438\u0433\u0440\u0435.§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_CHAT_MESSAGE, "§r§6Party §r§8%skip% §r§e%player% §r§f\u043f\u0438\u0448\u0435\u0442:§r§7 %message%§r", new String[] { "skip", "player", "message" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_NO_PERMISSION, "§r§6Party §r§8%skip% §r§c\u041d\u0430 \u044d\u0442\u043e \u0435\u0441\u0442\u044c \u043f\u0440\u0430\u0432\u0430 \u0442\u043e\u043b\u044c\u043a\u043e \u0443 \u0441\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044f \u043f\u0430\u0442\u0438§r", new String[] { "skip" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_DISBAND_REQUEST, "§r§6Party §r§8%skip% §r§c\u0421\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043f\u0430\u0442\u0438 %player% \u0437\u0430\u043f\u0440\u043e\u0441\u0438\u043b \u0440\u0430\u0441\u0444\u043e\u0440\u043c\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435!§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_NOT_ENOUGH_SPACE, "§r§6Party §r§8%skip% §r§c\u041d\u0430 \u044d\u0442\u043e\u0439 \u0430\u0440\u0435\u043d\u0435 \u043d\u0435 \u0445\u0432\u0430\u0442\u0430\u0435\u0442 \u0441\u043b\u043e\u0442\u043e\u0432 \u0434\u043b\u044f \u0432\u0441\u0435\u0433\u043e \u043f\u0430\u0442\u0438, \u0432\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u0434\u0440\u0443\u0433\u0443\u044e \u0430\u0440\u0435\u043d\u0443§r", new String[] { "skip" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_REQUEST_ALREADY_SENT, "§r§6Party §r§8%skip% §r§c\u042d\u0442\u043e\u043c\u0443 \u0438\u0433\u0440\u043e\u043a\u0443 \u0443\u0436\u0435 \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u043b\u0438 \u0437\u0430\u043f\u0440\u043e\u0441, \u043f\u043e\u0434\u043e\u0436\u0434\u0438\u0442\u0435§r", new String[] { "skip" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_OWNER_LEFT, "§r§6Party §r§8%skip% §r§c\u0421\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043f\u0430\u0442\u0438 %player% \u043f\u043e\u043a\u0438\u043d\u0443\u043b \u0435\u0433\u043e, \u043f\u0430\u0442\u0438 \u0441\u0435\u0439\u0447\u0430\u0441 \u0431\u0443\u0434\u0435\u0442 \u0440\u0430\u0441\u0444\u043e\u043c\u0438\u0440\u043e\u0432\u0430\u043d\u043e!§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_YOU_WERE_WARPED, "§r§6Party §r§8%skip% §r§c\u0412\u0430\u0441 \u043f\u0435\u0440\u0435\u043c\u0435\u0441\u0442\u0438\u043b \u043a \u0441\u0435\u0431\u0435 \u0441\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043f\u0430\u0442\u0438§r", new String[] { "skip" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_INFO, "\u25b8 \u0418\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044f \u043e \u043f\u0430\u0442\u0438", new String[] { "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_LOBBY_PARTY_JOIN_REQUEST, "§e--------------------------------------------%skip1%§e%player_name% §f\u043f\u0440\u0438\u0433\u043b\u0430\u0441\u0438\u043b \u0432\u0430\u0441 \u0432 \u043f\u0430\u0442\u0438%skip2%§e\u041d\u0430\u0436\u043c\u0438\u0442\u0435 \u0441\u044e\u0434\u0430 §f\u0447\u0442\u043e\u0431\u044b \u043f\u0440\u0438\u0441\u043e\u0435\u0434\u0438\u043d\u0438\u0442\u044c\u0441\u044f! \u0423 \u0432\u0430\u0441 \u0435\u0441\u0442\u044c 60 \u0441\u0435\u043a\u0443\u043d\u0434.%skip3%$bwbrotagend$", new String[] { "skip1", "player_name", "skip2", "skip3" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_YOU_ARE_TELEPORTED_TO_LOBBY_DUE_TO_FULL_ARENA, "§r§c§l| §r§f\u0422\u044b \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0435\u043d \u0432 \u043b\u043e\u0431\u0431\u0438 §r§8%skip% §r§c\u0410\u0440\u0435\u043d\u0430 \u0437\u0430\u043f\u043e\u043b\u043d\u0435\u043d\u0430.§r", new String[] { "skip", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAY_YOU_ALREADY_ON_SERVER, "§c\u0412\u044b \u0443\u0436\u0435 \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435!§r", new String[] { "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_PARTY_ON_CREATE, "\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 §r§e/party \u043d\u0438\u043a §r§f\u0447\u0442\u043e\u0431\u044b \u043f\u0440\u0438\u0433\u043b\u0430\u0441\u0438\u0442\u044c \u043d\u043e\u0432\u044b\u0445 \u0438\u0433\u0440\u043e\u043a\u043e\u0432!", new String[] { "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_CANT_USE_COMMANDS, "§r§c\u041d\u0435\u043b\u044c\u0437\u044f \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c \u0442\u0430\u043a\u0443\u044e \u043a\u043e\u043c\u0430\u043d\u0434\u0443 \u0432 \u0438\u0433\u0440\u0435, \u0434\u043b\u044f \u0432\u044b\u0445\u043e\u0434\u0430 \u043f\u0438\u0448\u0438\u0442\u0435 /leave§r", new String[] { "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_WAIT_SECONDS, "§r§c§l| §r§f\u041f\u043e\u0436\u0430\u0439\u043b\u0443\u0441\u0442\u0430 \u043f\u043e\u0434\u043e\u0436\u0434\u0438\u0442\u0435 §r§c%seconds% \u0441\u0435\u043a\u0443\u043d\u0434 §r§f\u0441\u0435\u043a.§r", new String[] { "seconds", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_YOU_CANT_USE_COLORS, "\u0423 \u0442\u0435\u0431\u044f \u043d\u0435\u0442 \u043f\u0440\u0430\u0432 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c \u0446\u0432\u0435\u0442\u0430 \u0432 \u0447\u0430\u0442\u0435", new String[] { "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_YOU_CANT_USE_COLOR, "\u0412\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c \u0446\u0432\u0435\u0442", new String[] { "", "" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_GAME_ANTI_CHEAT_KICK, "§r§6\u0410\u043d\u0442\u0438\u0427\u0438\u0442§r§8 %skip% §r\u0418\u0433\u0440\u043e\u043a §r§c%player% §r§f\u0431\u044b\u043b §r§c\u043a\u0438\u043a\u043d\u0443\u0442 §r§f\u043f\u043e \u043f\u043e\u0434\u043e\u0437\u0440\u0435\u043d\u0438\u044e \u0432 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0438 \u0447\u0438\u0442\u043e\u0432.§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_HUB_ANTIFLOOD, "§r§6\u0410\u043d\u0442\u0438\u0424\u043b\u0443\u0434§r§8 %skip% §r\u0418\u0433\u0440\u043e\u043a §r§c%player% §r§f\u0431\u044b\u043b §r§c\u0437\u0430\u043c\u0443\u0447\u0435\u043d §r§f\u0441 \u043f\u0440\u0438\u0447\u0438\u043d\u043e\u0439: '§r§c\u041f\u043e\u0432\u0442\u043e\u0440\u0435\u043d\u0438\u0435 \u043e\u0434\u043d\u043e\u0442\u0438\u043f\u043d\u044b\u0445 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0439§r§f'§r", new String[] { "skip", "player" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.LOGIN_WITH_PASSWORD, "§r§c§l| §r§f\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0438\u0440\u0443\u0439\u0442\u0435\u0441\u044c %skip% §r§e/login [\u043f\u0430\u0440\u043e\u043b\u044c]§r", new String[] { "skip" }));
        MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.LOGIN_WITH_PASSWORD_CHEATMINE, "§r§c§l%skip% §r§f\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0438\u0440\u0443\u0439\u0442\u0435\u0441\u044c §r§7§l\u203a §r§c§l/login §r§c[§r§c§l\u043f\u0430\u0440\u043e\u043b\u044c§r§c]", new String[] { "skip" }));
        final String[] array;
        final String[] chatMessagesGameStarted = array = new String[] { "§r§f                           §r§c§l\u041a\u0420\u041e\u0412\u0410\u0422\u041d\u042b\u0415 \u0412\u041e\u0419\u041d\u042b§r", "§r§c§l  §r§f                          §r", "§r§e          §r§e\u041f\u043e\u043a\u0443\u043f\u0430\u0439 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u0438 \u0443\u043b\u0443\u0447\u0448\u0435\u043d\u0438\u044f \u0434\u043b\u044f \u0441\u0432\u043e\u0435\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u0437\u0430§r", "§r§e        §r§e\u0416\u0435\u043b\u0435\u0437\u043e, \u0417\u043e\u043b\u043e\u0442\u043e, \u0410\u043b\u043c\u0430\u0437\u044b \u0438 \u0418\u0437\u0443\u043c\u0440\u0443\u0434\u044b \u0447\u0442\u043e\u0431\u044b \u0441\u0442\u0430\u0442\u044c \u043d\u0435\u0441\u043e\u043a\u0440\u0443\u0448\u0438\u043c\u044b\u043c\u0438!§r", "§r§e                 §r§e\u041f\u043e\u0431\u0435\u0434\u0438\u0442 \u0442\u043e\u043b\u044c\u043a\u043e \u043e\u0434\u043d\u0430, \u0441\u0438\u043b\u044c\u043d\u0435\u0439\u0448\u0430\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u0430!§r", "§r§e  §r§f                §r§a   \u0412\u044b \u0438\u0433\u0440\u0430\u0435\u0442\u0435 \u043d\u0430 §r§amc.MineBlaze.ru§r", "§r§e  §r§f                §r§a   \u0412\u044b \u0438\u0433\u0440\u0430\u0435\u0442\u0435 \u043d\u0430 §r§amc.DexLand.ru§r" };
        for (final String s : array) {
            MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_BEDWARS_GAME_STARTED_TIPS, s, new String[] { "" }, true));
        }
        final String[] array2;
        final String[] chatMessagesAds = array2 = new String[] { "\u0421\u0430\u0439\u0442 \u0434\u043b\u044f \u0441\u043e\u0432\u0435\u0440\u0448\u0435\u043d\u0438\u044f \u043f\u043e\u043a\u0443\u043f\u043e\u043a:", "\u041f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u0438 [GOLD]", "\u0414\u043e\u0441\u0442\u0443\u043f\u043d\u043e: /sit", "\u0425\u043e\u0442\u0438\u0442\u0435 \u043a\u0443\u043f\u0438\u0442\u044c \u0434\u043e\u043d\u0430\u0442, \u043d\u043e \u0441\u043e\u043c\u043d\u0435\u0432\u0430\u0435\u0442\u0435\u0441\u044c?", "\u0427\u0442\u043e\u0431\u044b \u043f\u043e\u0441\u043c\u043e\u0442\u0440\u0435\u0442\u044c \u0441\u0432\u043e\u044e \u0438\u043b\u0438 \u0447\u0443\u0436\u0443\u044e \u0441\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0443", "\u0425\u043e\u0442\u0438\u0442\u0435 \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u0441\u0432\u043e\u0439 \u043d\u0438\u043a?", "\u0425\u043e\u0442\u0438\u0442\u0435 \u0431\u044b\u0442\u044c \u043f\u0435\u0440\u0432\u044b\u043c \u0432\u043a\u0443\u0440\u0441\u0435 \u0432\u0441\u0435\u0445 \u043d\u043e\u0432\u043e\u0441\u0442\u0435\u0439 \u0441\u0435\u0440\u0432\u0435\u0440\u0430?", "\u0425\u043e\u0442\u0438\u0442\u0435 \u0432\u044b\u0438\u0433\u0440\u0430\u0442\u044c \u0410\u0439\u0444\u043e\u043d 11 PRO?", "\u041f\u043e\u043c\u043e\u0433\u0438\u0442\u0435 \u043d\u0430\u043c \u0443\u043b\u0443\u0447\u0448\u0438\u0442\u044c \u0441\u0435\u0440\u0432\u0435\u0440", "\u0425\u043e\u0442\u0438\u0442\u0435 \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0432\u0441\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0438 \u043e\u043f\u043a\u0443?", "\u041f\u043e\u043a\u0443\u043f\u043a\u0430 \u043a\u0435\u0439\u0441\u0430 \u043f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0438\u0442\u0441\u044f \u043d\u0430 \u0441\u0430\u0439\u0442\u0435", "\u041d\u0435 \u0437\u043d\u0430\u0435\u0442\u0435 \u043a\u0430\u043a\u0438\u0435 \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e\u0441\u0442\u0438 \u0435\u0441\u0442\u044c \u0443 \u0434\u043e\u043d\u0430\u0442\u0430?", "\u0412\u041a\u043e\u043d\u0442\u0430\u043a\u0442\u0435 - ", "\u0414\u043b\u044f \u0447\u0435\u0433\u043e \u044d\u0442\u043e \u043d\u0443\u0436\u043d\u043e (\u041d\u0430\u0432\u0435\u0434\u0438 \u043c\u044b\u0448\u043a\u043e\u0439)", "\u0412\u044b \u0441\u043b\u0443\u0447\u0430\u0439\u043d\u043e \u0432\u044b\u0448\u043b\u0438 \u0438\u0437 \u0438\u0433\u0440\u044b \u0438 \u0445\u043e\u0442\u0438\u0442\u0435 \u0432\u0435\u0440\u043d\u0443\u0442\u044c\u0441\u044f?", "\u0427\u0442\u043e\u0431\u044b \u043f\u043e\u0441\u043c\u043e\u0442\u0440\u0435\u0442\u044c \u0442\u043e\u043f\u044b \u043f\u043e \u0432\u0441\u0435\u043c \u043a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f\u043c", "\u0423\u0441\u043f\u0435\u0439\u0442\u0435 \u043a\u0443\u043f\u0438\u0442\u044c \u0434\u043e\u043d\u0430\u0442 \u043f\u043e \u0434\u0435\u0448\u0435\u0432\u044b\u043c \u0446\u0435\u043d\u0430\u043c", "\u0425\u043e\u0447\u0435\u0448\u044c \u043a\u0443\u043f\u0438\u0442\u044c \u0434\u043e\u043d\u0430\u0442, \u043d\u043e \u0441\u043e\u043c\u043d\u0435\u0432\u0430\u0435\u0448\u044c\u0441\u044f?", "\u041d\u0435 \u0437\u043d\u0430\u0435\u0448\u044c \u043a\u0430\u043a\u0438\u0435 \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e\u0441\u0442\u0438 \u0435\u0441\u0442\u044c \u0443 \u0434\u043e\u043d\u0430\u0442\u0430?", "\u041d\u043e \u0442\u044b \u043d\u0435 \u0433\u043e\u0442\u043e\u0432 \u0442\u0440\u0430\u0442\u0438\u0442\u044c \u043c\u043d\u043e\u0433\u043e \u0434\u0435\u043d\u0435\u0433?", "\u0425\u043e\u0442\u0435\u043b \u0431\u044b \u0441\u043d\u0438\u043c\u0430\u0442\u044c \u0430\u043d\u0442\u0438\u0433\u0440\u0438\u0444\u0435\u0440 \u0448\u043e\u0443?", "\u0414\u0440\u0443\u0437\u044c\u044f \u0445\u0432\u0430\u0441\u0442\u0430\u044e\u0442\u0441\u044f \u0434\u043e\u043d\u0430\u0442\u043e\u043c? \u0410 \u0442\u044b \u043d\u0438\u043a\u0442\u043e?", "\u0425\u043e\u0447\u0435\u0448\u044c \u043a\u0443\u043f\u0438\u0442\u044c \u0434\u043e\u043d\u0430\u0442, \u043d\u043e \u043d\u0435 \u0434\u043e\u0432\u0435\u0440\u044f\u0435\u0448\u044c?", "\u0421\u0430\u0439\u0442 \u0434\u043b\u044f \u043f\u043e\u043a\u0443\u043f\u043a\u0438 \u0434\u043e\u043d\u0430\u0442\u0430", "\u0414\u043e\u043d\u0430\u0442 \u043f\u043e\u043a\u0443\u043f\u0430\u0442\u044c \u0442\u043e\u043b\u044c\u043a\u043e \u043d\u0430 \u0441\u0430\u0439\u0442\u0435", "\u0422\u043e\u043b\u044c\u043a\u043e \u0441\u0435\u0433\u043e\u0434\u043d\u044f \u0443 \u0442\u0435\u0431\u044f \u0435\u0441\u0442\u044c \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e\u0441\u0442\u044c \u043a\u0443\u043f\u0438\u0442\u044c \u0434\u043e\u043d\u0430\u0442", "\u0425\u043e\u0447\u0435\u0448\u044c \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c 85% \u043a\u043e\u043c\u0430\u043d\u0434 \u041e\u0427\u0415\u041d\u042c \u0434\u0435\u0448\u0435\u0432\u043e", "\u0425\u043e\u0447\u0435\u0448\u044c \u0431\u044b\u0442\u044c \u0432 \u043a\u0443\u0440\u0441\u0435 \u043d\u043e\u0432\u043e\u0441\u0442\u0435\u0439 \u0441\u0435\u0440\u0432\u0435\u0440\u0430?", "\u041a\u0443\u043f\u0438\u0442\u044c \u0434\u043e\u043d\u0430\u0442 \u043d\u0430 \u043d\u0430\u0448\u0435\u043c \u0441\u0430\u0439\u0442\u0435 \u043c\u043e\u0436\u043d\u043e \u0447\u0435\u0440\u0435\u0437:", "\u041d\u0430 \u043d\u0430\u0448\u0435\u043c \u0441\u0430\u0439\u0442\u0435 \u0435\u0441\u0442\u044c \u043e\u043f\u043b\u0430\u0442\u0430 \u0441 \u043c\u043e\u0431\u0438\u043b\u044c\u043d\u043e\u0433\u043e \u0442\u0435\u043b\u0435\u0444\u043e\u043d\u0430", "\u0422\u0435\u0431\u0435 \u043d\u0443\u0436\u0435\u043d \u0434\u043e\u0441\u0442\u0443\u043f \u0432 \u043b\u044e\u0431\u043e\u0439 \u043f\u0440\u0438\u0432\u0430\u0442?", "\u0425\u043e\u0447\u0435\u0448\u044c \u043f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0432\u0441\u0435\u043c \u043a\u0442\u043e \u0442\u0443\u0442 \u0433\u043b\u0430\u0432\u043d\u044b\u0439?", "\u041a\u0443\u043f\u0438\u0442\u044c \u0434\u043e\u043d\u0430\u0442 \u043d\u0430 \u043d\u0430\u0448\u0435\u043c \u0441\u0430\u0439\u0442\u0435 \u043c\u043e\u0436\u043d\u043e \u0447\u0435\u0440\u0435\u0437:", "\u041f\u043e\u043c\u043e\u0433\u0438\u0442\u0435 \u043d\u0430\u043c \u0443\u043b\u0443\u0447\u0448\u0438\u0442\u044c \u0441\u0435\u0440\u0432\u0435\u0440", "\u0423 \u043d\u0430\u0441 \u043f\u043e\u044f\u0432\u0438\u043b\u0441\u044f \u043d\u043e\u0432\u044b\u0439 \u0434\u043e\u043d\u0430\u0442", "\u0412\u0441\u0435 \u0434\u043e\u043d\u0430\u0442-\u043f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u0438 \u0438 \u043a\u0435\u0439\u0441\u044b", "\u0412\u0430\u0436\u043d\u043e \u0434\u043b\u044f \u0432\u0430\u0448\u0435\u0439 \u0431\u0435\u0437\u043e\u043f\u0430\u0441\u043d\u043e\u0441\u0442\u0438" };
        for (final String s2 : array2) {
            MyChatListener.chatMessages.add(new ChatMessage(CHAT_MESSAGE.CHAT_ADS, s2, new String[] { "" }));
        }
        final ArrayList<ChatMessage> temp = new ArrayList<ChatMessage>();
        for (final ChatMessage m : MyChatListener.chatMessages) {
            temp.add(m);
        }
        MyChatListener.chatMessages = new ArrayList<ChatMessage>();
        for (final ChatMessage m : temp) {
            if (m.type != CHAT_MESSAGE.NONE) {
                MyChatListener.chatMessages.add(m);
            }
        }
    }
    
    public static ChatMessage findChatMessage(final String str) {
        final ChatMessage instance = null;
        for (final ChatMessage chatMessage : MyChatListener.chatMessages) {
            if (chatMessage.elements.length == 0) {
                String message_text = str;
                if (chatMessage.type == CHAT_MESSAGE.CHAT_ADS) {
                    message_text = ColorCodesManager.removeColorCodes(message_text);
                }
                if (chatMessage.isMustBeEqual) {
                    if (message_text.equals(chatMessage.message)) {
                        return chatMessage;
                    }
                    continue;
                }
                else {
                    if (message_text.contains(chatMessage.message)) {
                        return chatMessage;
                    }
                    continue;
                }
            }
            else {
                final ArrayList<String> parts = new ArrayList<String>();
                String s = chatMessage.message;
                try {
                    for (int i = 0; i < chatMessage.elements.length; ++i) {
                        final String part = s.split(Pattern.quote(chatMessage.elements[i]))[0];
                        parts.add(part);
                        s = s.split(chatMessage.elements[i])[1];
                        if (i == chatMessage.elements.length - 1) {
                            parts.add(s);
                        }
                    }
                }
                catch (Exception ex) {
                    ChatSender.addText("ERROR with &a" + chatMessage.type);
                }
                boolean isFound = true;
                for (final String part2 : parts) {
                    if (part2.equals("$bwbrotagend$")) {
                        continue;
                    }
                    if (!str.contains(part2)) {
                        isFound = false;
                        break;
                    }
                }
                if (!isFound) {
                    continue;
                }
                if (parts.size() < 2) {
                    continue;
                }
                try {
                    String line = str;
                    for (int j = 0; j < parts.size() - 1; ++j) {
                        line = line.replaceFirst(Pattern.quote(parts.get(j)), "");
                        final String val = line.split(Pattern.quote(parts.get(j + 1)))[0].trim();
                        line = line.replaceFirst(Pattern.quote(val), "");
                        chatMessage.element_values[j] = val;
                    }
                    return chatMessage;
                }
                catch (Exception ex2) {
                    if (chatMessage.type == CHAT_MESSAGE.CHAT_GAME_CHAT_GLOBAL) {
                        chatMessage.element_values[chatMessage.element_values.length - 1] = "";
                        return chatMessage;
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    @SubscribeEvent
    public void onJoinServer(final FMLNetworkEvent.ClientConnectedToServerEvent e) {
        Main.updateAllBooleans();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Minecraft.func_71410_x() == null || Minecraft.func_71410_x().field_71456_v == null || Minecraft.func_71410_x().field_71456_v.func_146158_b() == null) {
                    return;
                }
                Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146231_a();
            }
        }, 200L);
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent e) {
        if (Minecraft.func_71410_x() == null) {
            return;
        }
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        final String str = e.message.func_150254_d();
        final ChatMessage instance = findChatMessage(str);
        if (instance == null) {
            final String k = ColorCodesManager.removeColorCodes(str).toLowerCase();
            if (k.contains("\u0432\u0440\u0435\u0434\u043e\u043d\u043e\u0441\u043d") || k.contains("\u0440\u0435\u043a\u043e\u043c\u0435\u043d\u0434\u0443\u0435\u043c \u0443\u0434\u0430\u043b\u0438\u0442\u044c") || k.contains("\u043d\u0435 \u0443\u0441\u0442\u0430\u043d\u0430\u0432\u043b\u0438\u0432\u0430\u0442\u044c") || k.contains("\u0432\u044b \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442\u0435 \u044d\u0442\u043e\u0442 \u043c\u043e\u0434") || (k.contains("\u0443\u043f\u0440\u0430\u0432\u043b\u044f") && k.contains("\u0441\u043f\u043e\u043b\u044c\u0437\u0443") && k.contains("\u043c\u043e\u0434"))) {
                setMessageText(e, "&8<===============================================>\n&c \u041c\u043e\u0434 &c&lBedwars&f&lBro &b\u0431\u043b\u043e\u043a\u0438\u0440\u0443\u0435\u0442 \u0432\u0441\u044e \u0440\u0435\u043a\u043b\u0430\u043c\u0443 &c\u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435. \u041f\u043e\u044d\u0442\u043e\u043c\u0443 \u0430\u0434\u043c\u0438\u043d\u044b \u0445\u0443\u0435\u0441\u043e\u0441\u044b &l\u0437\u0430\u043f\u0440\u0435\u0442\u0438\u043b\u0438 &c\u0433\u043e\u0432\u043e\u0440\u0438\u0442\u044c \u0441\u043b\u043e\u0432\u0430 &eBedwarsBro&c, \u0438 &eDimChig&c. \u0410\u0434\u043c\u0438\u043d\u044b \u043f\u0440\u0438\u0434\u0443\u043c\u0430\u043b\u0438 \u0432\u0441\u044f\u043a\u0443\u044e \u0444\u0438\u0433\u043d\u044e \u043f\u0440\u043e &a\u0448\u043f\u0438\u043e\u043d\u0430\u0436&c, &a\u043a\u0440\u0430\u0436\u0443 \u043f\u0430\u0440\u043e\u043b\u0435\u0439 &c\u0438 \u0442\u0434. \u041d\u043e \u043c\u044b \u0442\u043e \u0441 \u0432\u0430\u043c\u0438 \u0437\u043d\u0430\u0435\u043c, \u0447\u0442\u043e \u043e\u043d\u0438 \u043f\u0440\u043e\u0441\u0442\u043e &b\u0445\u043e\u0442\u044f\u0442 \u0441\u0440\u0443\u0431\u0438\u0442\u044c \u0431\u0430\u0431\u043b\u043e &c\u0441 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0435\u0439!\n&8<===============================================>");
                playSound(MyChatListener.SOUND_PARTY_CHAT);
            }
            if (e.message.func_150260_c().length() == 0) {
                e.message = null;
            }
            return;
        }
        handleReceivedMessage(e, instance);
    }
    
    public static void sendDelayedGameStats() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (MyChatListener.GAME_start_time > 0L) {
                    String seconds = "" + (int)((new Date().getTime() - MyChatListener.GAME_start_time) / 1000L % 60L);
                    if (seconds.length() == 1) {
                        seconds = "0" + seconds;
                    }
                    final int minutes = (int)((new Date().getTime() - MyChatListener.GAME_start_time) / 1000L / 60L);
                    ChatSender.addText("");
                    ChatSender.addText(" &f\u0412\u0440\u0435\u043c\u044f &8\u25b8 &c" + minutes + ":" + seconds);
                    ChatSender.addText(" &f\u041a\u0438\u043b\u043e\u0432 &8\u25b8 &6" + MyChatListener.GAME_total_kills);
                    ChatSender.addText(" &f\u0421\u043c\u0435\u0440\u0442\u0435\u0439 &8\u25b8 &e" + MyChatListener.GAME_total_death);
                    String kdr_string = "100%";
                    if (MyChatListener.GAME_total_death > 0) {
                        final float kdr = (int)(MyChatListener.GAME_total_kills / (float)MyChatListener.GAME_total_death * 100.0f) / 100.0f;
                        kdr_string = "" + kdr;
                    }
                    ChatSender.addText(" &fK/D &8\u25b8 &a" + kdr_string);
                    ChatSender.addText(" &f\u041a\u0440\u043e\u0432\u0430\u0442\u0435\u0439 &8\u25b8 &b" + MyChatListener.GAME_total_beds);
                    ChatSender.addText("");
                    MyChatListener.anullateGameStats();
                }
            }
        }, 500L);
    }
    
    static void anullateGameStats() {
        MyChatListener.GAME_start_time = -1L;
        MyChatListener.GAME_total_kills = -1;
        MyChatListener.GAME_total_death = -1;
        MyChatListener.GAME_total_beds = -1;
    }
    
    static void initGameStats() {
        MyChatListener.GAME_start_time = new Date().getTime();
        MyChatListener.GAME_total_kills = 0;
        MyChatListener.GAME_total_death = 0;
        MyChatListener.GAME_total_beds = 0;
        Main.generatorTimers.setStartTimesOnGameStart();
    }
    
    public static void updateScoreboard() {
        CustomScoreboard.updateScoreboard();
    }
    
    public static String replaceColorCodes(final String text) {
        return ColorCodesManager.replaceColorCodesInString(text);
    }
    
    public static void setMessageText(final ClientChatReceivedEvent e, String text) {
        if (!MyChatListener.IS_MOD_ACTIVE) {
            return;
        }
        text = ChatSender.parseText(text);
        e.message = (IChatComponent)new ChatComponentText(replaceColorCodes(text));
    }
    
    public static void deleteMessage(final ClientChatReceivedEvent e) {
        e.message = null;
    }
    
    public static String formatChatPlayerName(final IChatComponent message, final String player_name, final String player_color) {
        return formatChatPlayerName(message, player_name, player_color, false);
    }
    
    public static String formatChatPlayerName(final IChatComponent message, String player_name, final String player_color, final boolean removeColor) {
        player_name = player_name.trim();
        if (player_name.contains(" ")) {
            player_name = player_name.split(" ")[1].trim();
        }
        while (player_name.charAt(0) == '&') {
            player_name = player_name.substring(2);
        }
        final String hoverText = getHoverMessage(message);
        if (hoverText.length() > 0 && hoverText.split(" ").length == 2 && hoverText.contains("\u041d\u0438\u043a:")) {
            final String hoverName = hoverText.split(" ")[1].trim();
            if (!hoverName.equals(player_name)) {
                player_name = hoverName + "*";
            }
        }
        if (removeColor) {
            player_name = ColorCodesManager.removeColorCodes(player_name);
        }
        if (player_name.endsWith("YT")) {
            player_name = "&c[Y&fT] " + player_color + player_name.replace("~", "");
        }
        return player_name;
    }
    
    public static String getTeamBoldName(final String team_name) {
        return team_name.substring(0, 2) + "&l" + team_name.substring(2);
    }
    
    public static String getTeamColor(final String team_name) {
        return team_name.substring(0, 2);
    }
    
    public static String getGeneratorDiamondUpgradeTime(final String upgrade) {
        final int[] levels = { 30, 23, 12 };
        int current_level = -1;
        if (upgrade.contains("II")) {
            current_level = 2;
        }
        else if (upgrade.contains("I")) {
            current_level = 1;
        }
        if (current_level == -1) {
            return "?";
        }
        return "" + levels[current_level];
    }
    
    public static String getGeneratorEmeraldUpgradeTime(final String upgrade) {
        final int[] levels = { 65, 50, 35 };
        int current_level = -1;
        if (upgrade.contains("II")) {
            current_level = 2;
        }
        else if (upgrade.contains("I")) {
            current_level = 1;
        }
        if (current_level == -1) {
            return "?";
        }
        return "" + levels[current_level];
    }
    
    public static String upperCaseFirstLetter(final String text) {
        if (text.length() <= 1) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
    
    public static String getTopKillerPlace(final String text) {
        String place = "§cN/A";
        if (text.contains("1")) {
            place = "&e#1";
        }
        else if (text.contains("2")) {
            place = "&f#2";
        }
        else if (text.contains("3")) {
            place = "&6#3";
        }
        return place;
    }
    
    public static String getStatsCategoryName(final String text) {
        String category = "§cN/A";
        if (text.contains("\u0423\u0431\u0438\u0439\u0441\u0442\u0432")) {
            category = "&a\u041a\u0438\u043b\u043e\u0432";
        }
        else if (text.contains("\u0421\u043c\u0435\u0440\u0442\u0435\u0439")) {
            category = "&c\u0421\u043c\u0435\u0440\u0442\u0435\u0439";
        }
        else if (text.contains("\u0418\u0433\u0440")) {
            category = "&b\u041a\u0430\u0442\u043e\u043a";
        }
        else if (text.contains("\u041f\u043e\u0431\u0435\u0434")) {
            category = "&e\u041f\u043e\u0431\u0435\u0434";
        }
        else if (text.contains("\u0421\u043b\u043e\u043c\u0430\u043d\u043e \u043a\u0440\u043e\u0432\u0430\u0442\u0435\u0439")) {
            category = "&d\u041a\u0440\u043e\u0432\u0430\u0442\u0435\u0439";
        }
        return category;
    }
    
    public static String highLightExtras(String text) {
        text = highLightDiscord(text);
        if (text.length() == 0) {
            return text;
        }
        assert Minecraft.func_71410_x().field_71439_g == null;
        final String player_name = Minecraft.func_71410_x().field_71439_g.func_70005_c_();
        if (ColorCodesManager.removeColorCodes(text).contains(player_name)) {
            playSound(MyChatListener.SOUND_PLAYER_STATS);
        }
        return text;
    }
    
    public static String getDiscordFromString(final String text) {
        if (text.contains("#")) {
            final String[] split = text.split(Pattern.quote("#"));
            if (split.length < 2) {
                return null;
            }
            try {
                final String nickname = split[0].split(" ")[split[0].split(" ").length - 1].trim();
                final String hash = split[1].split(" ")[0].substring(0, 4).trim();
                if (hash.length() != 4) {
                    return text;
                }
                final int hash_value = Integer.parseInt(hash);
                final String discord = nickname + "#" + hash;
                return ColorCodesManager.removeColorCodes(discord);
            }
            catch (Exception ex) {
                return null;
            }
        }
        return null;
    }
    
    public static String highLightDiscord(String text) {
        final String color = "&9";
        text = text.replace(" \u0434\u0441 ", color + " \u0434\u0441 &f").replace(" \u0414\u0441 ", color + " \u0414\u0441 &f").replace(" \u0414\u0421 ", color + " \u0414\u0421 &f");
        final String discord = getDiscordFromString(text);
        if (discord != null) {
            text = text.replace(discord, color + "" + discord + "&f");
        }
        return text;
    }
    
    public static void playSound(final String name) {
        playSound(name, 1.0f);
    }
    
    public static void playSound(final String name, final float volume) {
        assert Minecraft.func_71410_x().field_71439_g == null;
        Minecraft.func_71410_x().field_71439_g.func_85030_a(name, volume, 1.0f);
    }
    
    public static boolean isItFinalKill(final String player_name) {
        final String name = ColorCodesManager.removeColorCodes(player_name).trim();
        final List<CustomScoreboard.BedwarsTeam> teams = CustomScoreboard.readBedwarsGame();
        for (final CustomScoreboard.BedwarsTeam t : teams) {
            for (final CustomScoreboard.BedwarsPlayer p : t.players) {
                if (p.name.equals(name)) {
                    return t.state != CustomScoreboard.TEAM_STATE.BED_ALIVE;
                }
            }
        }
        return false;
    }
    
    public static void saveGameRecovery() {
        if (MyChatListener.GAME_BED == null) {
            return;
        }
        final BWBed game_BED = MyChatListener.GAME_BED;
        final GuiMinimap minimap = Main.minimap;
        final ArrayList<GuiMinimap.MyBed> bedsFound = GuiMinimap.bedsFound;
        final LightningLocator lightningLocator = Main.lightningLocator;
        MyChatListener.GAME_RECOVERY = new GameRecovery(game_BED, bedsFound, LightningLocator.last_lightning);
    }
    
    public static void clearGame() {
        saveGameRecovery();
        MyChatListener.GAME_BED = null;
        MyChatListener.IS_IN_GAME = false;
        Main.minimap.clearGameBeds();
        final LightningLocator lightningLocator = Main.lightningLocator;
        LightningLocator.last_lightning = null;
    }
    
    public static void readPlayerBase() {
        final int delay = 2000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Minecraft.func_71410_x() == null || Minecraft.func_71410_x().field_71439_g == null) {
                    return;
                }
                Minecraft.func_71410_x().field_71439_g.refreshDisplayName();
                Main.namePlateRenderer.printSameUsersInGame();
                final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
                final ArrayList<BWBed> beds = HintsBedScanner.findBeds(player.func_180425_c().func_177958_n(), player.func_180425_c().func_177956_o(), player.func_180425_c().func_177952_p());
                if (beds.size() == 0) {
                    ChatSender.addText(MyChatListener.PREFIX_HINT + "&c\u0411\u0430\u0437\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430!");
                }
                else {
                    MyChatListener.GAME_BED = null;
                    int min_dist = 999999;
                    for (final BWBed b : beds) {
                        final int dist = (int)Math.sqrt(Math.pow(b.part1_posX - player.field_70165_t, 2.0) + Math.pow(b.part1_posZ - player.field_70161_v, 2.0));
                        if (dist < min_dist) {
                            min_dist = dist;
                            MyChatListener.GAME_BED = b;
                        }
                    }
                    if (MyChatListener.GAME_BED == null) {
                        ChatSender.addText(MyChatListener.PREFIX_HINT + "&c\u0411\u0430\u0437\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430, \u043e\u0448\u0438\u0431\u043a\u0430!");
                        return;
                    }
                    MyChatListener.saveGameRecovery();
                    Main.hintsBaseRadar.setBase(MyChatListener.GAME_BED.part1_posX, MyChatListener.GAME_BED.part1_posY, MyChatListener.GAME_BED.part1_posZ);
                }
            }
        }, delay);
    }
    
    public static String handleTeamDestruction(final String player_name) {
        try {
            final String player_team_color = player_name.substring(0, 2);
            final CustomScoreboard.TEAM_COLOR team_color = CustomScoreboard.getTeamColorByCode(player_team_color);
            final List<CustomScoreboard.BedwarsTeam> teams = CustomScoreboard.readBedwarsGame();
            for (final CustomScoreboard.BedwarsTeam t : teams) {
                if (t.color == team_color && (t.state == CustomScoreboard.TEAM_STATE.DESTROYED || (t.state == CustomScoreboard.TEAM_STATE.BED_BROKEN && t.players.size() == 1))) {
                    playSound(MyChatListener.SOUND_TEAM_DESTROYED);
                    return "\n" + MyChatListener.PREFIX_TEAM + player_team_color + "\u0423\u043d\u0438\u0447\u0442\u043e\u0436\u0435\u043d\u044b &l" + CustomScoreboard.getTeamNameByTeamColor(team_color) + "";
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public static boolean isUserMuted(final String player_name) {
        final FileNicknamesManager fileNicknamesManager = Main.fileNicknamesManager;
        final CommandMute commandMute = Main.commandMute;
        final ArrayList<String> arr = fileNicknamesManager.readNames(CommandMute.filename);
        for (String name : arr) {
            if (name.equals("*")) {
                return true;
            }
            if (name.length() <= 1) {
                continue;
            }
            if (name.equals(player_name)) {
                return true;
            }
            if (!name.contains("*")) {
                continue;
            }
            name = name.replace("*", "").trim();
            if (player_name.toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    public static String getStrikeMessageVictim(final String text) {
        return text.substring(0, 2) + "&m" + text.substring(2);
    }
    
    public static String getNumberEnding(final int cnt, final String case1, final String case2, final String case3) {
        String e = case3;
        final char last_num = Integer.toString(cnt).charAt(Integer.toString(cnt).length() - 1);
        switch (Integer.parseInt(Character.toString(last_num))) {
            case 1: {
                e = case1;
                break;
            }
            case 2:
            case 3:
            case 4: {
                e = case2;
                break;
            }
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: {
                e = case3;
                break;
            }
        }
        if (cnt >= 11 && cnt <= 14) {
            e = case3;
        }
        return e;
    }
    
    public static void addBedwarsMeowMessageToQuee(final String s, final boolean isInGameOnly) {
        if (MyChatListener.meowMessagesQuee == null) {
            MyChatListener.meowMessagesQuee = new ArrayList<MsgMeowQuee>();
        }
        MyChatListener.meowMessagesQuee.add(new MsgMeowQuee(s, new Date().getTime(), isInGameOnly));
    }
    
    static void handleBedwarsMeowMessagesQuee() {
        if (MyChatListener.meowMessagesQuee.size() <= 0) {
            return;
        }
        final long t = new Date().getTime();
        if (MyChatListener.time_last_meow_message != -1L && t - MyChatListener.time_last_meow_message < MyChatListener.TIME_MEOW_MESSAGES_CHECK_FREQUENCY) {
            return;
        }
        MyChatListener.time_last_meow_message = t;
        final MsgMeowQuee m = MyChatListener.meowMessagesQuee.get(0);
        if (MyChatListener.quee_last_meow_message == null) {
            MyChatListener.quee_last_meow_message = m;
            MyChatListener.quee_last_meow_message.time = t;
        }
        if (t - MyChatListener.quee_last_meow_message.time > 5000L) {
            ChatSender.addText(MyChatListener.PREFIX_BEDWARS_MEOW + "&c\u0412 \u0421\u041e\u041e\u0411\u0429\u0415\u041d\u0418\u0418 \u0417\u0410\u041f\u0420\u0415\u0429\u0415\u041d\u041d\u042b\u0415 \u0421\u041b\u041e\u0412\u0410:\n &7- &f" + MyChatListener.quee_last_meow_message.text);
            MyChatListener.meowMessagesQuee.remove(MyChatListener.quee_last_meow_message);
            MyChatListener.quee_last_meow_message = null;
            return;
        }
        if (m.isInGameOnly && !MyChatListener.IS_IN_GAME) {
            MyChatListener.meowMessagesQuee.remove(MyChatListener.quee_last_meow_message);
            MyChatListener.quee_last_meow_message = null;
            return;
        }
        ChatSender.sendText("!" + m.text);
    }
    
    static void checkBedwarsMeowMessagesQueelastMessage(final String text) {
        if (MyChatListener.meowMessagesQuee.size() <= 0 || MyChatListener.quee_last_meow_message == null) {
            return;
        }
        if (ColorCodesManager.removeColorCodes(text).contains(ColorCodesManager.removeColorCodes(MyChatListener.quee_last_meow_message.text))) {
            MyChatListener.meowMessagesQuee.remove(MyChatListener.quee_last_meow_message);
            MyChatListener.quee_last_meow_message = null;
        }
    }
    
    static void handleBedwarsMeowKill(final ClientChatReceivedEvent e, final String p_killer, final String p_victim) {
        handleBedwarsMeowKillType(e, p_killer, p_victim, BedwarsMeow.MsgCase.KILL);
    }
    
    static void handleBedwarsMeowDeathVoid(final String p_player) {
        final String player = ColorCodesManager.removeColorCodes(p_player).trim();
        if (!Minecraft.func_71410_x().field_71439_g.func_70005_c_().equals(player)) {
            return;
        }
        final String s = Main.bedwarsMeow.getNextMessage(BedwarsMeow.MsgCase.DEATH_VOID, "");
        if (s == null) {
            return;
        }
        addBedwarsMeowMessageToQuee(s, true);
    }
    
    static void handleBedwarsMeowFinalKill(final ClientChatReceivedEvent e, final String p_killer, final String p_victim) {
        handleBedwarsMeowKillType(e, p_killer, p_victim, BedwarsMeow.MsgCase.FINAL_KILL);
    }
    
    static void handleBedwarsMeowKillType(final ClientChatReceivedEvent e, final String p_killer, final String p_victim, final BedwarsMeow.MsgCase msgcase) {
        final String killer = ColorCodesManager.removeColorCodes(p_killer).trim();
        final String victim = ColorCodesManager.removeColorCodes(p_victim).trim();
        final String mod_player_name = Minecraft.func_71410_x().field_71439_g.func_70005_c_();
        if (msgcase == BedwarsMeow.MsgCase.KILL && mod_player_name.equals(victim)) {
            ++MyChatListener.GAME_total_death;
            final String s = Main.bedwarsMeow.getNextMessage(BedwarsMeow.MsgCase.DEATH, p_killer);
            if (s == null) {
                return;
            }
            addBedwarsMeowMessageToQuee(s, true);
        }
        if (!mod_player_name.equals(killer)) {
            return;
        }
        ++MyChatListener.GAME_total_kills;
        final String s = Main.bedwarsMeow.getNextMessage(msgcase, p_victim);
        if (s == null) {
            return;
        }
        addBedwarsMeowMessageToQuee(s, true);
    }
    
    static void handleBedwarsMeowBed(final CustomScoreboard.TEAM_COLOR destroyed_bed_color, final String p_player) {
        final String player = ColorCodesManager.removeColorCodes(p_player).trim();
        if (destroyed_bed_color == getEntityTeamColor((EntityPlayer)Minecraft.func_71410_x().field_71439_g)) {
            final String s = Main.bedwarsMeow.getNextMessage(BedwarsMeow.MsgCase.BED_OWN, p_player);
            if (s == null) {
                return;
            }
            addBedwarsMeowMessageToQuee(s, true);
        }
        else {
            if (!Minecraft.func_71410_x().field_71439_g.func_70005_c_().equals(player)) {
                return;
            }
            ++MyChatListener.GAME_total_beds;
            boolean isSingleMode = false;
            final List<CustomScoreboard.BedwarsTeam> teams = CustomScoreboard.readBedwarsGame();
            for (final CustomScoreboard.BedwarsTeam t : teams) {
                if (t.color == destroyed_bed_color) {
                    if (t.players.size() <= 1) {
                        isSingleMode = true;
                        break;
                    }
                    break;
                }
            }
            BedwarsMeow.MsgCase msgcase = BedwarsMeow.MsgCase.BED_MULTI;
            if (isSingleMode) {
                msgcase = BedwarsMeow.MsgCase.BED_SINGLE;
            }
            final String s2 = Main.bedwarsMeow.getNextMessage(msgcase, CustomScoreboard.getCodeByTeamColor(destroyed_bed_color));
            if (s2 == null) {
                return;
            }
            addBedwarsMeowMessageToQuee(s2, true);
        }
    }
    
    static boolean handleAuthorMessages(final String author_name, String message_text) {
        final boolean isInLobby = Main.shopManager.findItemInHotbar("\u043b\u043e\u0431\u0431\u0438") != -1;
        Main.baseProps.readMessages();
        message_text = ColorCodesManager.removeColorCodes(message_text);
        if (message_text.startsWith("!")) {
            message_text = message_text.substring(1);
        }
        if (message_text.startsWith("!") && message_text.length() > 1) {
            message_text = message_text.substring(1);
        }
        if (message_text.toLowerCase().contains("\u044f \u0430\u0432\u0442\u043e\u0440 \u043c\u043e\u0434\u0430")) {
            final StringBuilder sb = new StringBuilder();
            final MyChatListener chatListener = Main.chatListener;
            ChatSender.addText(sb.append(MyChatListener.PREFIX_BEDWARSBRO).append("&b\u041d\u0430 \u0434\u0430\u043d\u043d\u044b\u0439 \u043c\u043e\u043c\u0435\u043d\u0442 \u0441\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043c\u043e\u0434\u0430 \u0438\u0433\u0440\u0430\u0435\u0442 \u043f\u043e\u0434 \u043d\u0438\u043a\u043e\u043c \"&e").append(author_name).append("&b\"").toString());
            playSound(MyChatListener.SOUND_PARTY_CHAT);
            return true;
        }
        if (message_text.toLowerCase().contains("\u043a\u0442\u043e \u0441 \u043c\u043e\u0434\u043e\u043c?")) {
            MyChatListener.removeNextMessage = true;
            ChatSender.sendText((isInLobby ? "" : "!") + "\u042f \u0438\u0433\u0440\u0430\u044e \u0441 \u043c\u043e\u0434\u043e\u043c!");
            return true;
        }
        if (message_text.toLowerCase().contains("\u0441\u0435\u0439\u0447\u0430\u0441 \u0434\u043e\u043b\u0436\u043d\u043e \u0431\u044b\u0442\u044c \u0432\u0438\u0434\u043d\u043e")) {
            Main.baseProps.readProps();
            return true;
        }
        final ArrayList<BaseProps.MyMessage> messages = Main.baseProps.my_messages;
        if (messages.size() == 0) {
            return false;
        }
        for (final BaseProps.MyMessage m : messages) {
            final String trigger = m.trigger.replace("%name%", Minecraft.func_71410_x().field_71439_g.func_70005_c_());
            if (trigger.toLowerCase().equals(message_text.toLowerCase())) {
                final String text2send = m.getRndMessage();
                if (text2send == null) {
                    return false;
                }
                MyChatListener.removeNextMessage = true;
                final String starter = (isInLobby || text2send.startsWith("/")) ? "" : "!";
                ChatSender.sendText(starter + text2send);
                return true;
            }
        }
        return false;
    }
    
    static void handleBedwarsMeowWin(final String p_team_name) {
        final CustomScoreboard.TEAM_COLOR team_color = CustomScoreboard.getTeamColorByName(p_team_name);
        final CustomScoreboard.TEAM_COLOR mod_team_color = getEntityTeamColor((EntityPlayer)Minecraft.func_71410_x().field_71439_g);
        if (mod_team_color == team_color) {
            return;
        }
        final String s = Main.bedwarsMeow.getNextMessage(BedwarsMeow.MsgCase.WIN, "");
        if (s == null) {
            return;
        }
        addBedwarsMeowMessageToQuee(s, true);
    }
    
    static void handleBedwarsMeowGameGreeting() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                final String s = Main.bedwarsMeow.getNextMessage(BedwarsMeow.MsgCase.GAME_START, "");
                if (s == null) {
                    return;
                }
                MyChatListener.addBedwarsMeowMessageToQuee(s, true);
            }
        }, 500L);
    }
    
    public static void handleReceivedMessage(final ClientChatReceivedEvent e, final ChatMessage chatMessage) {
        String str = e.message.func_150254_d();
        final ChatMessage msg = chatMessage;
        final String[] vals = msg.element_values;
        checkBedwarsMeowMessagesQueelastMessage(e.message.func_150260_c());
        if (MyChatListener.removeNextMessage) {
            MyChatListener.removeNextMessage = false;
            deleteMessage(e);
            return;
        }
        updateScoreboard();
        switch (msg.type) {
            case CHAT_LEFT_GAME: {
                final String victim = vals[1];
                if (isItFinalKill(victim)) {
                    str = getStrikeMessageVictim(victim) + "&r &7\u043b\u0438\u0432\u043d\u0443\u043b";
                    str += handleTeamDestruction(victim);
                }
                else {
                    str = victim + " &r&7\u043b\u0438\u0432\u043d\u0443\u043b";
                }
                setMessageText(e, str);
                break;
            }
            case CHAT_GAME_STARTED: {
                str = "§r§f                            &c&lB&6&le&e&ld&a&lW&b&la&9&lr&d&ls\n";
                FileManager.writeToFile("========", "resourceBedwarslog.txt", true);
                if (HintsValidator.isBedScannerActive()) {
                    readPlayerBase();
                }
                MyChatListener.IS_IN_GAME = true;
                initGameStats();
                Main.updateAllBooleans();
                setMessageText(e, str);
                handleBedwarsMeowGameGreeting();
                LobbyBlockPlacer.state = false;
                break;
            }
            case CHAT_BEDWARS_GAME_STARTED_TIPS: {
                if (MyChatListener.IS_MOD_ACTIVE) {
                    deleteMessage(e);
                    break;
                }
                break;
            }
            case CHAT_JOINED_MIDGAME: {
                str = "§r" + vals[1] + " §r§7\u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u043b\u0441\u044f";
                setMessageText(e, str);
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_JOINED_PREGAME: {
                str = "&7" + formatChatPlayerName(e.message, vals[1], "&7") + " &r&7\u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0438\u043b\u0441\u044f &a" + vals[2] + "&7/&e" + vals[3];
                setMessageText(e, str);
                clearGame();
                break;
            }
            case CHAT_LEFT_PREGAME: {
                str = "&7" + formatChatPlayerName(e.message, vals[1], "&7") + " &r&7\u043b\u0438\u0432\u043d\u0443\u043b &c" + vals[2] + "&7/&e" + vals[3];
                setMessageText(e, str);
                clearGame();
                break;
            }
            case CHAT_SUICIDE: {
                final String victim = vals[1];
                if (isItFinalKill(victim)) {
                    str = "&r&f\u2694 " + getStrikeMessageVictim(victim);
                    str += handleTeamDestruction(victim);
                }
                else {
                    str = "&r&f\u2694 " + victim;
                    if (ColorCodesManager.removeColorCodes(victim).trim().equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                        ++MyChatListener.GAME_total_death;
                    }
                }
                setMessageText(e, str);
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_SUICIDE_VOID: {
                final String victim = vals[1];
                handleBedwarsMeowDeathVoid(victim);
                if (isItFinalKill(victim)) {
                    str = "&r&f\u2694 " + getStrikeMessageVictim(victim);
                    str += handleTeamDestruction(victim);
                }
                else {
                    str = "&r&f\u2694 " + victim;
                    if (ColorCodesManager.removeColorCodes(victim).trim().equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                        ++MyChatListener.GAME_total_death;
                    }
                }
                setMessageText(e, str);
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_KILLED_BY_VOID: {
                final String victim = vals[1];
                if (isItFinalKill(victim)) {
                    str = "&r" + vals[2] + " &f\u2694 &r" + getStrikeMessageVictim(victim);
                    str += handleTeamDestruction(victim);
                    setMessageText(e, str);
                    handleBedwarsMeowFinalKill(e, vals[2], victim);
                }
                else {
                    str = "&r" + vals[2] + " &f\u2694 &r" + victim;
                    setMessageText(e, str);
                    handleBedwarsMeowKill(e, vals[2], victim);
                }
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_KILLED_BY_PLAYER: {
                final String victim = vals[1];
                if (isItFinalKill(victim)) {
                    str = "&r" + vals[2] + " &f\u2694 &r" + getStrikeMessageVictim(victim);
                    str += handleTeamDestruction(victim);
                    setMessageText(e, str);
                    handleBedwarsMeowFinalKill(e, vals[2], victim);
                    final Entity mod_player = (Entity)Minecraft.func_71410_x().field_71439_g;
                    if (ColorCodesManager.removeColorCodes(vals[2]).equals(mod_player.func_70005_c_())) {
                        final MovingObjectPosition ray = mod_player.func_174822_a(3.0, 1.0f);
                        if (ray == null) {
                            break;
                        }
                        final int posX = (int)ray.field_72307_f.field_72450_a;
                        final int posY = (int)ray.field_72307_f.field_72448_b;
                        final int posZ = (int)ray.field_72307_f.field_72449_c;
                        final CustomScoreboard.TEAM_COLOR team_color = CustomScoreboard.getTeamColorByCode(victim.substring(0, 2));
                        final ParticleController particleController = Main.particleController;
                        ParticleController.spawnFinalKillParticles(posX, posY, posZ, team_color);
                    }
                }
                else {
                    str = "&r" + vals[2] + " &f\u2694 &r" + victim;
                    setMessageText(e, str);
                    handleBedwarsMeowKill(e, vals[2], victim);
                }
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_TEAM_DESTROYED: {
                if (MyChatListener.IS_MOD_ACTIVE) {
                    deleteMessage(e);
                }
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_TEAM_BED_BROKEN: {
                if (MyChatListener.IS_MOD_ACTIVE) {
                    str = MyChatListener.PREFIX_BED + getTeamColor(vals[2]) + "\u041c\u0438\u043d\u0443\u0441 " + getTeamBoldName(vals[2]);
                    if (vals[1].length() > 2) {
                        str = str + "" + vals[1].substring(0, 2) + "*";
                    }
                    deleteMessage(e);
                    ChatSender.addHoverText(str, "&7\u0421\u043b\u043e\u043c\u0430\u043b &f" + vals[1]);
                }
                playSound(MyChatListener.SOUND_BED_BROKEN);
                HintsBaseRadar.checkBedState();
                handleBedwarsMeowBed(CustomScoreboard.getTeamColorByCode(getTeamColor(vals[2])), vals[1]);
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_TEAM_COLOR_CHOSEN: {
                str = "&r" + getTeamColor(vals[1]) + "\u0412\u044b\u0431\u0440\u0430\u043d\u0430 \u0442\u0438\u043c\u0430 " + getTeamBoldName(vals[1]);
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_TEAM_CHOSEN);
                break;
            }
            case CHAT_TEAM_ALREADY_CHOSEN: {
                str = "&c\u0422\u0438\u043c\u0430 \u0443\u0436\u0435 \u0432\u044b\u0431\u0440\u0430\u043d\u0430!";
                setMessageText(e, str);
                break;
            }
            case CHAT_TEAM_IS_FULL: {
                str = "&c\u0422\u0438\u043c\u0430 \u0437\u0430\u043f\u043e\u043b\u043d\u0435\u043d\u0430!";
                setMessageText(e, str);
                clearGame();
                break;
            }
            case CHAT_SHOP_ITEM_BOUGHT: {
                str = "&r&7+ &e" + vals[1] + " &a" + vals[0];
                FileManager.writeToFile(vals[0] + ";" + vals[1] + ";&;", "resourceBedwarslog.txt", true);
                if (Main.getConfigBool(Main.CONFIG_MSG.REMOVE_BUY_MESSAGE)) {
                    deleteMessage(e);
                }
                else {
                    setMessageText(e, str);
                }
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_SHOP_NOT_ENOUGH_RESOURCES: {
                str = "&r&c\u041d\u0435\u0442 \u0440\u0435\u0441\u043e\u0432!";
                if (Main.getConfigBool(Main.CONFIG_MSG.REMOVE_BUY_MESSAGE)) {
                    deleteMessage(e);
                }
                else {
                    setMessageText(e, str);
                }
                MyChatListener.IS_IN_GAME = true;
                if (MyChatListener.GAME_BED == null) {
                    recoverGameBed();
                    break;
                }
                break;
            }
            case CHAT_UPGRADE_BOUGHT: {
                if (MyChatListener.IS_MOD_ACTIVE) {
                    str = "&b&l" + vals[2] + " &a" + vals[3] + " &7\u043f\u0440\u043e\u043a\u0430\u0447\u0430\u043d\u044b";
                    deleteMessage(e);
                    ChatSender.addHoverText(str, "&7\u041f\u0440\u043e\u043a\u0430\u0447\u0430\u043b &b" + vals[1]);
                }
                playSound(MyChatListener.SOUND_UPGRADE_BOUGHT, 0.5f);
                break;
            }
            case CHAT_GENERATOR_DIAMOND_LEVELED_UP: {
                final String new_time = getGeneratorDiamondUpgradeTime(vals[1]);
                str = "&b\u0410\u043b\u043c\u0430\u0437\u044b &7\u0432\u043a\u0430\u0447\u0430\u043d\u044b \u0434\u043e &a" + vals[1] + " &7- \u043a\u0430\u0436\u0434\u044b\u0435 &e" + new_time + " &7\u0441\u0435\u043a";
                try {
                    Main.generatorTimers.setMaxTimeDiamonds(Integer.parseInt(new_time));
                }
                catch (Exception ex) {}
                setMessageText(e, str);
                break;
            }
            case CHAT_GENERATOR_EMERALD_LEVELED_UP: {
                final String new_time = getGeneratorEmeraldUpgradeTime(vals[1]);
                str = "&a\u0418\u0437\u0443\u043c\u0440\u0443\u0434\u044b &7\u0432\u043a\u0430\u0447\u0430\u043d\u044b \u0434\u043e &a" + vals[1] + " &7- \u043a\u0430\u0436\u0434\u044b\u0435 &e" + new_time + " &7\u0441\u0435\u043a";
                try {
                    Main.generatorTimers.setMaxTimeEmeralds(Integer.parseInt(new_time));
                }
                catch (Exception ex2) {}
                setMessageText(e, str);
                break;
            }
            case CHAT_TRAP_ACTIVATED: {
                str = "&c&k===&r &c&l\u0412\u0420\u0410\u0413 \u041d\u0410 \u0411\u0410\u0417\u0415 &c&k===";
                str = str + "\n" + str + "\n" + str;
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_TRAP_ACTIVATED);
                break;
            }
            case CHAT_SERVER_RESTART: {
                str = "&c\u0412\u044b\u0445\u043e\u0434 \u0432 \u043b\u043e\u0431\u0431\u0438 \u0447\u0435\u0440\u0435\u0437 &c&l" + vals[0] + " &c\u0441\u0435\u043a";
                setMessageText(e, str);
                break;
            }
            case CHAT_TELEPORTATION_TO_HUB: {
                str = "&8\u0422\u041f \u0432 \u043b\u043e\u0431\u0431\u0438...";
                clearGame();
                setMessageText(e, str);
                break;
            }
            case CHAT_CONNECTING_TO_LOBBY: {
                MyChatListener.IS_IN_GAME = false;
                str = "&8\u041f\u043e\u0434\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0435 \u043a " + vals[0];
                clearGame();
                setMessageText(e, str);
                break;
            }
            case CHAT_HUB_CHAT_PLAYER_MESSAGE: {
                clearGame();
                final String player_name = formatChatPlayerName(e.message, vals[0], "&7");
                String text = vals[2];
                if (text.startsWith("§7") && text.length() > 2) {
                    text = text.substring(2).trim();
                }
                final String message_hover_text = getHoverMessage(e.message);
                if (Main.isPropUserAdmin(player_name) && !Main.isPropSelfAdmin() && handleAuthorMessages(player_name, vals[2])) {
                    deleteMessage(e);
                    break;
                }
                final String discord_string = getDiscordFromString(text);
                String sender_name = player_name;
                String real_name = player_name;
                if (message_hover_text.length() > 0) {
                    final String[] split = message_hover_text.split(" ");
                    if (split.length >= 2) {
                        String donation = ColorCodesManager.removeColorCodes(split[0].trim());
                        String donation_color = "&d";
                        if (donation.contains("\u0418\u0433\u0440\u043e\u043a")) {
                            donation = "";
                            donation_color = "&7";
                        }
                        else if (donation.contains("GOLD")) {
                            donation_color = "&e";
                        }
                        else if (donation.contains("DIAMOND")) {
                            donation_color = "&b";
                        }
                        else if (donation.contains("EMERALD")) {
                            donation_color = "&a";
                        }
                        else if (donation.contains("MAGMA")) {
                            donation_color = "&6";
                        }
                        else if (donation.contains("LEGEND")) {
                            donation_color = "&c";
                        }
                        String battle_stats = "";
                        final String hover_data = ColorCodesManager.removeColorCodes(message_hover_text);
                        if (hover_data.contains("\u0423\u0431\u0438\u0439\u0441\u0442\u0432 \u25b8") && hover_data.contains("K/D \u25b8 ") && Main.getConfigBool(Main.CONFIG_MSG.ENABLE_BETTER_CHAT_STATISTIC_PREFIX)) {
                            try {
                                final int kills = Integer.parseInt(hover_data.split("\u0423\u0431\u0438\u0439\u0441\u0442\u0432 \u25b8")[1].trim().split(" ")[0].trim().replace(",", ""));
                                final int games_cnt = Integer.parseInt(hover_data.split("\u0418\u0433\u0440 \u25b8")[1].trim().split(" ")[0].trim().replace(",", ""));
                                final int wins_cnt = Integer.parseInt(hover_data.split("\u041f\u043e\u0431\u0435\u0434 \u25b8")[1].trim().split(" ")[0].trim().replace(",", ""));
                                String kills_s = kills / 1000 + "\u043a";
                                kills_s = new DecimalFormat("0.0").format(kills / 1000.0f) + "\u043a";
                                if (kills > 100) {
                                    String kills_color = "&c";
                                    if (kills < 500) {
                                        kills_color = "&c";
                                    }
                                    else if (kills < 1000) {
                                        kills_color = "&6";
                                    }
                                    else if (kills < 2000) {
                                        kills_color = "&e";
                                    }
                                    else if (kills < 5000) {
                                        kills_color = "&a";
                                    }
                                    else if (kills < 10000) {
                                        kills_color = "&a&l";
                                    }
                                    else if (kills < 20000) {
                                        kills_color = "&a&l&n";
                                    }
                                    else if (kills >= 40000) {
                                        kills_color = "&b&l&n";
                                    }
                                    String kdr_color = "&c";
                                    final double kdr = Double.parseDouble(hover_data.split("K/D \u25b8 ")[1].trim().split(" ")[0].split("\n")[0].trim());
                                    if (kdr < 1.0) {
                                        kdr_color = "&c";
                                    }
                                    else if (kdr < 1.5) {
                                        kdr_color = "&6";
                                    }
                                    else if (kdr < 2.0) {
                                        kdr_color = "&e";
                                    }
                                    else if (kdr < 3.0) {
                                        kdr_color = "&a";
                                    }
                                    else if (kdr < 4.0) {
                                        kdr_color = "&a&l";
                                    }
                                    else if (kdr < 5.0) {
                                        kdr_color = "&a&l&n";
                                    }
                                    else if (kdr >= 5.0) {
                                        kdr_color = "&b&l&n";
                                    }
                                    String wr_color = "&c";
                                    int wr = -1;
                                    if (games_cnt > 0) {
                                        wr = (int)(wins_cnt * 100.0f / games_cnt);
                                        if (wr < 10) {
                                            wr_color = "&c";
                                        }
                                        else if (wr < 20) {
                                            wr_color = "&6";
                                        }
                                        else if (wr < 30) {
                                            wr_color = "&e";
                                        }
                                        else if (wr < 40) {
                                            wr_color = "&a";
                                        }
                                        else if (wr < 50) {
                                            wr_color = "&a&l";
                                        }
                                        else if (wr < 60) {
                                            wr_color = "&a&l&n";
                                        }
                                        else if (wr >= 70) {
                                            wr_color = "&b&l&n";
                                        }
                                    }
                                    battle_stats = "&7[" + kills_color + kills_s + "&8, " + kdr_color + kdr + ((wr >= 0) ? ("&8, " + wr_color + wr + "%") : "") + "&7]";
                                }
                            }
                            catch (Exception ex3) {}
                        }
                        real_name = ColorCodesManager.removeColorCodes(split[1].trim());
                        if (isUserMuted(real_name) && !isPlayerMyFriend(real_name)) {
                            deleteMessage(e);
                        }
                        sender_name = ((battle_stats.length() > 0) ? (battle_stats + " ") : "") + donation_color + donation + ((donation.length() > 0) ? " &l" : "") + real_name;
                        if (isPlayerMyFriend(real_name)) {
                            sender_name = MyChatListener.PREFIX_FRIEND_IN_CHAT + sender_name;
                            playSound("note.hat");
                        }
                        String mod_prefix = Main.namePlateRenderer.getPrefixByName(real_name);
                        if (mod_prefix == null) {
                            mod_prefix = "";
                        }
                        sender_name = mod_prefix + sender_name.trim();
                    }
                    if (MyChatListener.IS_MOD_ACTIVE) {
                        str = sender_name + "&7 \u2192 &f" + highLightExtras(text);
                        if (discord_string != null) {
                            deleteMessage(e);
                            ChatSender.addClickSuggestAndHoverText(str, "&f\u041d\u0430\u0436\u043c\u0438, \u0447\u0442\u043e\u0431 \u0441\u043a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u0442\u044c \"&e" + discord_string + "&f\"", discord_string);
                        }
                        else {
                            deleteMessage(e);
                            ChatSender.addClickSuggestAndHoverText(str, message_hover_text, ColorCodesManager.removeColorCodes(player_name));
                        }
                    }
                    else if (isPlayerMyFriend(real_name)) {
                        final String line = MyChatListener.PREFIX_FRIEND_IN_CHAT + e.message.func_150254_d();
                        e.message = (IChatComponent)new ChatComponentText(replaceColorCodes(line));
                    }
                    break;
                }
                break;
            }
            case CHAT_YOU_WERE_KICKED: {
                clearGame();
                str = "&c\u0422\u0435\u0431\u044f \u043a\u0438\u043a\u043d\u0443\u043b\u0438";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_ADS: {
                deleteMessage(e);
                break;
            }
            case CHAT_GAME_STARTS_IN_SECONDS: {
                clearGame();
                str = "&e\u041a\u0430\u0442\u043a\u0430 \u043d\u0430\u0447\u043d\u0435\u0442\u0441\u044f \u0447\u0435\u0440\u0435\u0437 &c&l" + vals[1] + "";
                setMessageText(e, str);
                break;
            }
            case CHAT_GAME_CHAT_LOCAL: {
                if (MyChatListener.IS_MOD_ACTIVE) {
                    final String n = formatChatPlayerName(e.message, vals[1], vals[0], true);
                    String mod_prefix2 = Main.namePlateRenderer.getPrefixByName(n);
                    if (mod_prefix2 == null) {
                        mod_prefix2 = "";
                    }
                    str = "&r" + vals[0] + "<\u0422\u0438\u043c\u0435> " + mod_prefix2 + vals[0] + n + " &8\u2192 &f" + vals[3];
                    deleteMessage(e);
                    String name2copy = ColorCodesManager.removeColorCodes(vals[1]);
                    if (name2copy.split(" ").length == 2) {
                        name2copy = name2copy.split(" ")[1].trim().replaceFirst("~", "");
                    }
                    ChatSender.addClickSuggestAndHoverText(str, vals[1], name2copy);
                    break;
                }
                break;
            }
            case CHAT_GAME_CHAT_GLOBAL: {
                String n = formatChatPlayerName(e.message, vals[1], vals[0], true);
                if (n.contains(" ")) {
                    n = n.split(" ")[1].trim();
                }
                final String player_name = n.replace("*", "").trim();
                if (Main.isPropUserAdmin(player_name) && !Main.isPropSelfAdmin() && handleAuthorMessages(player_name, vals[3])) {
                    deleteMessage(e);
                    break;
                }
                if (MyChatListener.IS_MOD_ACTIVE) {
                    String mod_prefix2 = Main.namePlateRenderer.getPrefixByName(n);
                    if (mod_prefix2 == null) {
                        mod_prefix2 = "";
                    }
                    final String real_name = ColorCodesManager.removeColorCodes(formatChatPlayerName(e.message, vals[1], vals[0], true));
                    str = "&r" + mod_prefix2 + "" + vals[0] + "" + real_name + " &8\u2192 &f" + vals[3];
                    deleteMessage(e);
                    String name2copy = ColorCodesManager.removeColorCodes(vals[1]);
                    if (name2copy.split(" ").length == 2) {
                        name2copy = name2copy.split(" ")[1].trim().replaceFirst("~", "");
                    }
                    if (isPlayerMyFriend(real_name)) {
                        str = MyChatListener.PREFIX_FRIEND_IN_CHAT + str;
                        playSound("note.hat");
                    }
                    ChatSender.addClickSuggestAndHoverText(str, vals[1], name2copy);
                    break;
                }
                break;
            }
            case CHAT_GAME_CHAT_SPECTATOR: {
                String n = formatChatPlayerName(e.message, vals[0], "&7", true);
                if (n.contains(" ")) {
                    n = n.split(" ")[1].trim();
                }
                if (Main.isPropUserAdmin(n) && !Main.isPropSelfAdmin() && handleAuthorMessages(n, vals[2])) {
                    deleteMessage(e);
                    break;
                }
                String mod_prefix2 = Main.namePlateRenderer.getPrefixByName(n);
                if (mod_prefix2 == null) {
                    mod_prefix2 = "";
                }
                str = "&r&7[\u0421\u043f\u0435\u043a\u0442\u0430\u0442\u043e\u0440] " + mod_prefix2 + n + " &8\u2192 &f" + vals[2];
                setMessageText(e, str);
                break;
            }
            case CHAT_GAME_CHAT_PREGAME: {
                String n = formatChatPlayerName(e.message, vals[0], "&7", true);
                if (n.contains(" ")) {
                    n = n.split(" ")[1].trim();
                }
                if (Main.isPropUserAdmin(n) && !Main.isPropSelfAdmin() && handleAuthorMessages(n, vals[1])) {
                    deleteMessage(e);
                    break;
                }
                str = "&7" + n + " &8\u2192 &f" + vals[1];
                setMessageText(e, str);
                clearGame();
                break;
            }
            case CHAT_PREGAME_FASTSTART_REJECT: {
                clearGame();
                str = "&f\u041a\u0443\u043f\u0438 &c&l\u0434&6&l\u043e&e&l\u043d&a&l\u0430&b&l\u0442 &f\u0441\u043d\u0430\u0447\u0430\u043b\u0430";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_DONATER_GREETING: {
                clearGame();
                String name = vals[0];
                if (name.contains(" ")) {
                    name = name.split(" ")[0].trim();
                }
                if (Main.isPropUserAdmin(vals[0])) {
                    playSound(MyChatListener.SOUND_TEAM_DESTROYED);
                    str = "§r§c§l\u203a §r§f\u0421\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043c\u043e\u0434\u0430 &c&lBedwars&f&lBro &f\"&e" + vals[0] + "&f\" \u0437\u0430\u0448\u0435\u043b \u0432 \u043b\u043e\u0431\u0431\u0438!";
                }
                else {
                    str = "§r§c§l\u203a §r§f" + vals[0] + " &7" + vals[1];
                }
                setMessageText(e, str);
                break;
            }
            case CHAT_PLAYER_BANNED: {
                str = MyChatListener.PREFIX_ANTICHEAT + "&f\u0414\u0430\u0443\u043d &c&l" + ColorCodesManager.removeColorCodes(vals[1]) + " &f\u0447\u0438\u0442\u0435\u0440 \u0438 \u0431\u044b\u043b &c\u0437\u0430\u0431\u0430\u043d\u0435\u043d&f!";
                setMessageText(e, str);
                break;
            }
            case CHAT_GAME_ANTI_CHEAT_KICK: {
                final String p_name = ColorCodesManager.removeColorCodes(vals[1]);
                str = MyChatListener.PREFIX_ANTICHEAT + "&f\u0414\u0430\u0443\u043d &c&l" + p_name + " &f\u0441\u043f\u0430\u043b\u0438\u043b\u0441\u044f \u0441 \u0447\u0438\u0442\u0430\u043c\u0438 \u0438 \u0431\u044b\u043b &c\u043a\u0438\u043a\u043d\u0443\u0442&f!";
                setMessageText(e, str);
                break;
            }
            case CHAT_HUB_ANTIFLOOD: {
                str = MyChatListener.PREFIX_ANTIFLOOD + "&c\u0417\u0430\u043c\u0443\u0447\u0435\u043d &l" + vals[1] + " &c\u0437\u0430 \u0441\u043f\u0430\u043c!";
                setMessageText(e, str);
                break;
            }
            case LOGIN_WITH_PASSWORD:
            case LOGIN_WITH_PASSWORD_CHEATMINE: {
                final String pwd2auth = Main.getConfigString(Main.CONFIG_MSG.AUTO_LOGIN_PWD);
                if (pwd2auth != null && pwd2auth.length() > 0) {
                    setMessageText(e, e.message.func_150254_d() + "\n" + MyChatListener.PREFIX_BEDWARSBRO + "&a\u0410\u0432\u0442\u043e\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u044f &f\u0447\u0435\u0440\u0435\u0437 \u043f\u0430\u0440\u043e\u043b\u044c \u0432 \u043a\u043e\u043d\u0444\u0438\u0433\u0435...");
                    ChatSender.sendText("/l " + pwd2auth);
                    break;
                }
                break;
            }
            case CHAT_PREGAME_NOT_ENOUGH_PLAYERS_TO_START: {
                str = "&c\u041f\u043e\u0434\u043e\u0436\u0434\u0438 \u0435\u0449\u0435 \u0438\u0433\u0440\u043e\u043a\u043e\u0432";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_BEDWARS_END_TEAM_WON: {
                str = "§r§f                     " + getTeamBoldName(vals[0]) + " &f\u043f\u043e\u0431\u0435\u0434\u0438\u043b\u0438!";
                if (HintsValidator.isWinEmoteActive()) {
                    WinEmote.changeWorldBlocks(CustomScoreboard.getTeamColorByCode(vals[0].substring(0, 2)));
                }
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_GAME_END);
                handleBedwarsMeowWin(vals[0]);
                sendDelayedGameStats();
                break;
            }
            case CHAT_BEDWARS_END_TOP_KILLER: {
                final String place = getTopKillerPlace(vals[1]);
                String player = vals[2];
                if (player.equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                    player = "&e&l" + ColorCodesManager.removeColorCodes(player);
                }
                try {
                    final int kills_cnt = Integer.parseInt(vals[3]);
                    final String ending = getNumberEnding(kills_cnt, "", "\u0430", "\u043e\u0432");
                    str = "§r§f                  §r" + place + " &8\u25b8 §7" + player + " &7- &c" + vals[3] + " &f\u043a\u0438\u043b" + ending + "";
                    setMessageText(e, str);
                }
                catch (Exception ex4) {}
                break;
            }
            case CHAT_BEDWARS_END_TOP_KILLER_UNKNOWN: {
                final String place = getTopKillerPlace(vals[1]);
                str = "§r§f                  §r" + place + " &8\u25b8 §cN/A";
                setMessageText(e, str);
                break;
            }
            case CHAT_YOU_ARE_TELEPORTED_TO_LOBBY_DUE_TO_FULL_ARENA: {
                str = "§r\u0410\u0440\u0435\u043d\u0430 \u0437\u0430\u043f\u043e\u043b\u043d\u0435\u043d\u0430! \u0412\u044b\u0431\u0435\u0440\u0438 \u0434\u0440\u0443\u0433\u0443\u044e";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAY_YOU_ALREADY_ON_SERVER: {
                str = MyChatListener.PREFIX_PARTY + "§c\u0422\u0435\u0431\u044f \u0432\u0430\u0440\u043f\u043d\u0443\u043b\u0438, \u043d\u043e \u0442\u044b \u0443\u0436\u0435 \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435!";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_INVITE: {
                str = MyChatListener.PREFIX_PARTY + "&f\u0418\u043d\u0432\u0430\u0439\u0442 \u043a\u0438\u043d\u0443\u0442 \u0438\u0433\u0440\u043e\u043a\u0443 &e" + vals[1];
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_INVITE_REJECTED: {
                str = MyChatListener.PREFIX_PARTY + "&f\u0418\u043d\u0432\u0430\u0439\u0442 \u043d\u0430 \u043f\u0430\u0442\u0438 &c\u0438\u0441\u0442\u0435\u043a &f\u0438\u0433\u0440\u043e\u043a\u0443 &e" + vals[1];
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT, 0.7f);
                break;
            }
            case CHAT_LOBBY_PARTY_WARP: {
                str = MyChatListener.PREFIX_PARTY + "&f\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u044e \u0442\u0438\u043c\u043c\u0435\u0439\u0442\u043e\u0432 \u043a \u0441\u0435\u0431\u0435...";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_PLAYER_OFFLINE: {
                str = MyChatListener.PREFIX_PARTY + "&r&c\u0418\u0437 \u043f\u0430\u0442\u0438 \u0432\u044b\u0433\u043d\u0430\u043d &c&l" + vals[1] + " &c\u0437\u0430 \u0410\u0424\u041a";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_DISBANDED: {
                str = MyChatListener.PREFIX_PARTY + "&c\u041f\u0430\u0442\u0438 \u0440\u0430\u0437\u0444\u043e\u0440\u043c\u0438\u0440\u043e\u0432\u0430\u043d\u043e";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_ALREADY_IN_PARTY: {
                str = MyChatListener.PREFIX_PARTY + "&c\u042d\u0442\u043e\u0442 \u0438\u0433\u0440\u043e\u043a \u0443\u0436\u0435 \u0432 \u043f\u0430\u0442\u0438";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_OFFLINE: {
                str = MyChatListener.PREFIX_PARTY + "&c\u042d\u0442\u043e\u0442 \u0438\u0433\u0440\u043e\u043a \u043d\u0435 \u0432 \u0441\u0435\u0442\u0438";
                setMessageText(e, str);
                break;
            }
            case CHAT_PARTY_ON_CREATE: {
                str = MyChatListener.PREFIX_PARTY + "&a&l\u041f\u0430\u0442\u0438 \u0441\u043e\u0437\u0434\u0430\u043d\u043e!";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_PARTY_CREATED);
                break;
            }
            case CHAT_LOBBY_PARTY_YOU_ARE_NOT_IN_PARTY: {
                str = MyChatListener.PREFIX_PARTY + "&c\u0422\u044b \u043d\u0435 \u0432 \u043f\u0430\u0442\u0438";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_PLAYER_KICKED: {
                str = MyChatListener.PREFIX_PARTY + "&f\u041a\u0438\u043a\u043d\u0443\u0442 &c&l" + vals[1];
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_PARTY_PLAYER_LEFT: {
                str = MyChatListener.PREFIX_PARTY + "&f\u041b\u0438\u0432\u043d\u0443\u043b &c&l" + vals[1];
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_PARTY_CHAT_ENTER_MESSAGE: {
                str = MyChatListener.PREFIX_PARTY + "&f\u0412\u0432\u0435\u0434\u0438 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435: &a/pc \u0412\u0441\u0435\u043c \u043f\u0440\u0438\u0432\u0435\u0442!";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_CHAT_MESSAGE: {
                final String player_name = formatChatPlayerName(e.message, vals[1], "&7");
                final String this_text = vals[2].trim();
                str = MyChatListener.PREFIX_PARTY + "&e[Chat] &7" + player_name + " &8\u2192 &f" + this_text;
                setMessageText(e, str);
                if (!ColorCodesManager.removeColorCodes(player_name).equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                    playSound(MyChatListener.SOUND_PARTY_CHAT);
                    break;
                }
                break;
            }
            case CHAT_LOBBY_PARTY_NO_PERMISSION: {
                str = MyChatListener.PREFIX_PARTY + "&c\u0422\u044b \u043d\u0435 \u043b\u0438\u0434\u0435\u0440 \u043f\u0430\u0442\u0438!";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_PARTY_DISBAND_REQUEST: {
                str = MyChatListener.PREFIX_PARTY + "&c&l" + vals[1] + " &c\u0443\u0434\u0430\u043b\u044f\u0435\u0442 \u043f\u0430\u0442\u0438";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_PARTY_NOT_ENOUGH_SPACE: {
                str = MyChatListener.PREFIX_PARTY + "&c\u041d\u0435\u0442\u0443 \u043c\u0435\u0441\u0442\u0430 \u043d\u0430 \u044d\u0442\u043e\u0439 \u0430\u0440\u0435\u043d\u0435";
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_REQUEST_ALREADY_SENT: {
                str = MyChatListener.PREFIX_PARTY + "&c\u041f\u043e\u0434\u043e\u0436\u0434\u0438, \u0443 \u043d\u0435\u0433\u043e \u0443\u0436\u0435 \u0435\u0441\u0442\u044c \u0437\u0430\u043f\u0440\u043e\u0441";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_PARTY_YOU_WERE_WARPED: {
                str = MyChatListener.PREFIX_PARTY + "&a\u0422\u0435\u0431\u044f \u0432\u0430\u0440\u043f\u043d\u0443\u043b \u043a \u0441\u0435\u0431\u0435 \u0441\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043f\u0430\u0442\u0438!";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_PARTY_CHAT);
                break;
            }
            case CHAT_LOBBY_PARTY_INFO: {
                str = MyChatListener.PREFIX_PARTY + "&a\u041f\u0430\u0442\u0438 \u0438\u043d\u0444\u043e";
                final String this_text = e.message.func_150254_d();
                final String[] split2 = this_text.replace("§r", "").split("\u21aa");
                if (split2 != null && split2.length == 3 && split2[0].contains("\u0418\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044f \u043e \u043f\u0430\u0442\u0438")) {
                    try {
                        final String creator_line = ColorCodesManager.removeColorCodes(split2[1]);
                        final String creator_name = creator_line.split("\u0421\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c:")[1].trim();
                        final String[] members_line = split2[2].split("\u0423\u0447\u0430\u0441\u0442\u043d\u0438\u043a\u0438:§e")[1].trim().split(",");
                        final ArrayList<String> members = new ArrayList<String>();
                        for (final String m : members_line) {
                            String name = m.trim().split(" ")[0].trim();
                            if (!name.equals(creator_name)) {
                                final boolean isOnline = !m.contains("§c\u25cf");
                                if (!isOnline) {
                                    name = "&c" + name;
                                }
                                members.add(name.trim());
                            }
                        }
                        members.sort(new Comparator<String>() {
                            @Override
                            public int compare(final String s1, final String s2) {
                                if (s1.startsWith("&") && s2.startsWith("&")) {
                                    return 0;
                                }
                                if (s1.startsWith("&")) {
                                    return 1;
                                }
                                if (s2.startsWith("&")) {
                                    return -1;
                                }
                                return s1.compareTo(s2);
                            }
                        });
                        String response = MyChatListener.PREFIX_PARTY + "&e" + creator_name + "&6&l*";
                        String clickSuggest = creator_name.trim() + " ";
                        if (creator_name.equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                            clickSuggest = "";
                        }
                        for (final String member : members) {
                            response = response + "&8, &e" + member;
                            final String s = ColorCodesManager.removeColorCodes(member).trim();
                            if (s.equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                                continue;
                            }
                            clickSuggest = clickSuggest + s + " ";
                        }
                        ChatSender.addClickSuggestAndHoverText(response, "\u041d\u0430\u0436\u043c\u0438 \u0447\u0442\u043e\u0431 \u0441\u043a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043d\u0438\u043a\u0438", clickSuggest.trim());
                        deleteMessage(e);
                    }
                    catch (Exception ex5) {}
                    break;
                }
                break;
            }
            case CHAT_LOBBY_PARTY_JOIN_REQUEST: {
                final String p_name = vals[1];
                if (Main.isPropUserAdmin(p_name)) {
                    ChatSender.sendText("/party accept");
                }
                playSound(MyChatListener.SOUND_PARTY_CHAT);
                break;
            }
            case CHAT_LOBBY_PARTY_OWNER_LEFT: {
                str = MyChatListener.PREFIX_PARTY + "&c\u0421\u043e\u0437\u0434\u0430\u0442\u0435\u043b\u044c \u043f\u0430\u0442\u0438 &l" + vals[1] + " &c\u043b\u0438\u0432\u043d\u0443\u043b, \u043f\u0430\u0442\u0438 \u0431\u0443\u0434\u0435\u0442 \u0443\u0434\u0430\u043b\u0435\u043d\u043e!";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_PARTY_REQUEST: {
                playSound(MyChatListener.SOUND_PLAYER_STATS);
                break;
            }
            case CHAT_LOBBY_PARTY_YOU_ACCEPTED_REQUEST: {
                str = MyChatListener.PREFIX_PARTY + "&a\u0422\u044b \u043f\u0440\u0438\u043d\u044f\u043b \u0437\u0430\u043f\u0440\u043e\u0441!";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_PARTY_CREATED);
                break;
            }
            case CHAT_LOBBY_PARTY_NEW_LEADER: {
                assert Minecraft.func_71410_x().field_71439_g == null;
                if (vals[1].equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
                    str = MyChatListener.PREFIX_PARTY + "&a\u0422\u0435\u043f\u0435\u0440\u044c \u0442\u044b \u043b\u0438\u0434\u0435\u0440 \u043f\u0430\u0442\u0438!";
                }
                else {
                    str = MyChatListener.PREFIX_PARTY + "&f\u041d\u043e\u0432\u044b\u0439 \u043b\u0438\u0434\u0435\u0440 \u043f\u0430\u0442\u0438: &a&l" + vals[1];
                }
                playSound(MyChatListener.SOUND_PARTY_CREATED);
                setMessageText(e, str);
                break;
            }
            case CHAT_LOBBY_PARTY_COMMANDS_DONT_WORK_IN_LOBBY: {
                str = MyChatListener.PREFIX_PARTY + "&c\u041a\u043e\u043c\u0430\u043d\u0434\u044b \u043f\u0430\u0442\u0438 \u0442\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u044e\u0442";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_REJECT);
                break;
            }
            case CHAT_LOBBY_STATS_PLAYER: {
                final String player = vals[1];
                str = "\n&r&f\u0421\u0442\u0430\u0442\u0430 &e" + player + "&f:";
                setMessageText(e, str);
                playSound(MyChatListener.SOUND_PLAYER_STATS);
                MyChatListener.myStatistic = new MyStatistic(player);
                break;
            }
            case CHAT_LOBBY_STATS_CATEGORY: {
                final String category = getStatsCategoryName(vals[1]);
                String placeColor = "&0";
                try {
                    final int x = Integer.parseInt(vals[3].replace(",", "").trim());
                    placeColor = "&8";
                    if (x < 5000) {
                        placeColor = "&7";
                    }
                    if (x < 2000) {
                        placeColor = "&f";
                    }
                    if (x < 1000) {
                        placeColor = "&a";
                    }
                    if (x < 100) {
                        placeColor = "&e&l";
                    }
                    if (x == 0) {
                        placeColor = "&c";
                    }
                    if (x == -1) {
                        placeColor = "&0";
                    }
                }
                catch (Exception ex6) {}
                str = " &8- &r" + category + "&8 \u25b8 &f" + vals[2] + "&8. \u041c\u0435\u0441\u0442\u043e - " + placeColor + vals[3];
                int cnt = -1;
                int place_cnt = -1;
                try {
                    cnt = Integer.parseInt(vals[2].replace(",", "").trim());
                    place_cnt = Integer.parseInt(vals[3].replace(",", "").trim());
                }
                catch (Exception ex7) {}
                if (cnt != -1) {
                    if (category.contains("\u041a\u0438\u043b\u043e\u0432")) {
                        MyChatListener.myStatistic.category_kills_cnt = cnt;
                        MyChatListener.myStatistic.category_kills_place = place_cnt;
                    }
                    else if (category.contains("\u0421\u043c\u0435\u0440\u0442\u0435\u0439")) {
                        MyChatListener.myStatistic.category_deathes_cnt = cnt;
                        MyChatListener.myStatistic.category_deathes_place = place_cnt;
                    }
                    else if (category.contains("\u041a\u0430\u0442\u043e\u043a")) {
                        MyChatListener.myStatistic.category_games_cnt = cnt;
                        MyChatListener.myStatistic.category_games_place = place_cnt;
                    }
                    else if (category.contains("\u041f\u043e\u0431\u0435\u0434")) {
                        MyChatListener.myStatistic.category_wins_cnt = cnt;
                        MyChatListener.myStatistic.category_wins_place = place_cnt;
                    }
                    else if (category.contains("\u041a\u0440\u043e\u0432\u0430\u0442\u0435\u0439")) {
                        MyChatListener.myStatistic.category_beds_cnt = cnt;
                        MyChatListener.myStatistic.category_beds_place = place_cnt;
                        str += "\n";
                        int player_mark = 0;
                        if (MyChatListener.myStatistic.category_games_cnt > 0) {
                            final float percentage = (float)(MyChatListener.myStatistic.category_wins_cnt / (double)MyChatListener.myStatistic.category_games_cnt * 100.0);
                            String color = "&0";
                            if (percentage < 10.0f) {
                                ++player_mark;
                                color = "&c";
                            }
                            else if (percentage < 20.0f) {
                                player_mark += 2;
                                color = "&6";
                            }
                            else if (percentage < 30.0f) {
                                player_mark += 3;
                                color = "&e";
                            }
                            else if (percentage <= 40.0f) {
                                player_mark += 4;
                                color = "&a";
                            }
                            else {
                                player_mark += 5;
                                color = "&2";
                            }
                            str = str + "&8 - &f\u0412\u0438\u043d\u0440\u0435\u0439\u0442 &8\u25b8 " + color + (int)percentage + "%\n";
                        }
                        if (MyChatListener.myStatistic.category_deathes_cnt > 0) {
                            final float kdr2 = Math.round(MyChatListener.myStatistic.category_kills_cnt / (double)MyChatListener.myStatistic.category_deathes_cnt * 100.0) / 100.0f;
                            String color = "&0";
                            if (kdr2 < 0.7) {
                                ++player_mark;
                                color = "&c";
                            }
                            else if (kdr2 < 1.0f) {
                                player_mark += 2;
                                color = "&6";
                            }
                            else if (kdr2 < 1.5) {
                                player_mark += 3;
                                color = "&e";
                            }
                            else if (kdr2 <= 2.0f) {
                                player_mark += 4;
                                color = "&a";
                            }
                            else {
                                player_mark += 5;
                                color = "&2";
                            }
                            str = str + "&8 - &fK/D &8\u25b8 " + color + kdr2 + "\n";
                        }
                        if (MyChatListener.myStatistic.category_games_cnt > 0) {
                            final float bpg = Math.round(MyChatListener.myStatistic.category_beds_cnt / (double)MyChatListener.myStatistic.category_games_cnt * 10.0) / 10.0f;
                            String color = "&0";
                            if (bpg < 0.7) {
                                ++player_mark;
                                color = "&c";
                            }
                            else if (bpg < 1.0f) {
                                player_mark += 2;
                                color = "&6";
                            }
                            else if (bpg < 1.5) {
                                player_mark += 3;
                                color = "&e";
                            }
                            else if (bpg <= 2.0f) {
                                player_mark += 4;
                                color = "&a";
                            }
                            else {
                                player_mark += 5;
                                color = "&2";
                            }
                            str = str + "&8 - &fBeds/Game &8\u25b8 " + color + bpg + " \u0448\u0442\n";
                        }
                        final int treshold = 200;
                        if (MyChatListener.myStatistic.category_kills_cnt < treshold && MyChatListener.myStatistic.category_deathes_cnt < treshold && MyChatListener.myStatistic.category_games_cnt < treshold && MyChatListener.myStatistic.category_wins_cnt < treshold && MyChatListener.myStatistic.category_beds_cnt < treshold) {
                            str += "&8 - &c\u041d\u0443\u043b\u0435\u0432\u044b\u0439 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\n";
                        }
                        final float mark = (float)(Math.round((float)(player_mark / 3 * 10)) / 10);
                        String color2 = "&f";
                        if (mark < 2.0f) {
                            color2 = "&c";
                        }
                        else if (mark < 3.0f) {
                            color2 = "&6";
                        }
                        else if (mark < 4.0f) {
                            color2 = "&e";
                        }
                        else if (mark < 4.5) {
                            color2 = "&a";
                        }
                        else {
                            color2 = "&2";
                        }
                        str = str + "&8 - &f\u041e\u0446\u0435\u043d\u043a\u0430 &8\u25b8 " + color2 + mark + "\n";
                        MyChatListener.myStatistic = null;
                    }
                }
                setMessageText(e, str);
                break;
            }
            case CHAT_GAME_CANT_USE_COMMANDS: {
                str = "&c\u041d\u0435\u043b\u044c\u0437\u044f \u044e\u0437\u0430\u0442\u044c \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u0438\u0433\u0440\u044b!";
                setMessageText(e, str);
                break;
            }
            case CHAT_GAME_WAIT_SECONDS: {
                str = "&c\u041f\u043e\u0434\u043e\u0436\u0434\u0438 " + vals[0] + " \u0441\u0435\u043a!";
                setMessageText(e, str);
                break;
            }
            case CHAT_GAME_YOU_CANT_USE_COLORS: {
                str = "&c\u041a\u0443\u043f\u0438 \u0434\u043e\u043d\u0430\u0442 \u0447\u0442\u043e\u0431 \u044e\u0437\u0430\u0442\u044c \u0446\u0432\u0435\u0442\u0430 \u0432 \u0447\u0430\u0442\u0435!";
                setMessageText(e, str);
                MyChatListener.removeNextMessage = true;
                break;
            }
            case CHAT_GAME_YOU_CANT_USE_COLOR: {
                str = "&c\u0422\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0448\u044c \u044e\u0437\u0430\u0442\u044c \u0442\u0430\u043a\u0438\u0435 \u0446\u0432\u0435\u0442\u0430 \u0432 \u0447\u0430\u0442\u0435!";
                setMessageText(e, str);
                MyChatListener.removeNextMessage = true;
                break;
            }
        }
    }
    
    public static CustomScoreboard.TEAM_COLOR getEntityTeamColor(final EntityPlayer en) {
        final CustomScoreboard.TEAM_COLOR mod_team_color = CustomScoreboard.TEAM_COLOR.NONE;
        if (en.func_96124_cp() == null) {
            return CustomScoreboard.TEAM_COLOR.NONE;
        }
        final String team_name = en.func_96124_cp().func_96661_b();
        if (team_name == null) {
            return CustomScoreboard.TEAM_COLOR.NONE;
        }
        return getEntityTeamColorByTeam(team_name);
    }
    
    public static boolean isPlayerMyFriend(final String name) {
        if (!name.equals(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
            final FileNicknamesManager fileNicknamesManager = Main.fileNicknamesManager;
            final CommandFriends commandFriends = Main.commandFriends;
            if (fileNicknamesManager.readNames(CommandFriends.filename).contains(name)) {
                return true;
            }
        }
        return false;
    }
    
    public static CustomScoreboard.TEAM_COLOR getEntityTeamColorByTeam(final String team_name) {
        if (team_name.contains("red")) {
            return CustomScoreboard.TEAM_COLOR.RED;
        }
        if (team_name.contains("yellow")) {
            return CustomScoreboard.TEAM_COLOR.YELLOW;
        }
        if (team_name.contains("green")) {
            return CustomScoreboard.TEAM_COLOR.GREEN;
        }
        if (team_name.contains("aqua")) {
            return CustomScoreboard.TEAM_COLOR.AQUA;
        }
        if (team_name.contains("blue")) {
            return CustomScoreboard.TEAM_COLOR.BLUE;
        }
        if (team_name.contains("light_purple")) {
            return CustomScoreboard.TEAM_COLOR.PINK;
        }
        if (team_name.contains("white")) {
            return CustomScoreboard.TEAM_COLOR.WHITE;
        }
        if (team_name.contains("gray")) {
            return CustomScoreboard.TEAM_COLOR.GRAY;
        }
        return CustomScoreboard.TEAM_COLOR.NONE;
    }
    
    public static String getHoverMessage(final IChatComponent message) {
        for (final IChatComponent component : message.func_150253_a()) {
            if (component.func_150256_b() != null && component.func_150256_b().func_150210_i() != null) {
                if (component.func_150256_b().func_150210_i().func_150702_b() == null) {
                    continue;
                }
                return component.func_150256_b().func_150210_i().func_150702_b().func_150260_c() + "\n";
            }
        }
        return "";
    }
    
    public static void sendUpdateModMessage() {
        final String link = Main.getPropModUpdateLink();
        final String last_version = Main.getPropModLastVersion();
        if (link == null || last_version == null) {
            return;
        }
        String str = "";
        str += "&8<===============================================>\n";
        str += "                         &cBedwars&fBro &7v2.4\n\n";
        str = str + "                 &f\u041f\u043e\u044f\u0432\u0438\u043b\u0430\u0441\u044c &b&l\u043d\u043e\u0432\u0430\u044f \u0432\u0435\u0440\u0441\u0438\u044f \u043c\u043e\u0434\u0430 &7v&a" + last_version + "\n";
        str += "                     &f\u0411\u044b\u043b\u043e \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u043e &e&l\u0431\u043e\u043b\u044c\u0448\u0435 \u043e\u043f\u0446\u0438\u0439&f!\n";
        str += "            &f\u0422\u0435\u0431\u0435 \u043d\u0443\u0436\u043d\u043e &a&l\u043e\u0431\u043d\u043e\u0432\u0438\u0442\u044c \u043c\u043e\u0434&f, \u0447\u0442\u043e\u0431 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u0438\u043c \u0434\u0430\u043b\u044c\u0448\u0435\n\n";
        str += "               &d&l\u041d\u0430\u0436\u043c\u0438 \u043d\u0430 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 &b&l(\u0441\u0441\u044b\u043b\u043a\u0430 \u0432 \u043e\u043f\u0438\u0441\u0430\u043d\u0438\u0438)\n\n";
        str += "                     &7\u0421\u043a\u0430\u0447\u0430\u0439 \u043c\u043e\u0434, \u0438 \u043f\u043e\u043b\u043e\u0436\u0438 \u0435\u0433\u043e \u0432 \u043f\u0430\u043f\u043a\u0443\n";
        str += "             &7%appdata%&8/&7Roaming&8/&7.minecraft&8/&7mods\n";
        str += "&8<===============================================>";
        ChatSender.addLinkAndHoverText(str, "&e\u041d\u0430\u0436\u043c\u0438&f, \u0447\u0442\u043e\u0431 \u0441\u043a\u0430\u0447\u0430\u0442\u044c \u043d\u043e\u0432\u0443\u044e \u0432\u0435\u0440\u0441\u0438\u044e \u043c\u043e\u0434\u0430", link);
    }
    
    public static void recoverGameBed() {
        if (MyChatListener.GAME_BED == null && MyChatListener.GAME_RECOVERY != null && MyChatListener.GAME_RECOVERY.game_bed != null) {
            MyChatListener.GAME_BED = MyChatListener.GAME_RECOVERY.game_bed;
            final GuiMinimap minimap = Main.minimap;
            GuiMinimap.bedsFound = MyChatListener.GAME_RECOVERY.minimap_beds;
            final LightningLocator lightningLocator = Main.lightningLocator;
            LightningLocator.last_lightning = MyChatListener.GAME_RECOVERY.last_lightning;
        }
    }
    
    static {
        MyChatListener.GAME_start_time = -1L;
        MyChatListener.GAME_total_death = -1;
        MyChatListener.GAME_total_kills = -1;
        MyChatListener.GAME_total_beds = -1;
        MyChatListener.nickChanger = "";
        MyChatListener.removeNextMessage = false;
        MyChatListener.TIME_MEOW_MESSAGES_CHECK_FREQUENCY = 3000;
        MyChatListener.myStatistic = null;
        MyChatListener.DIMCHIG_NAME = "&c&lD&6&li&e&lm&a&lC&b&lh&9&li&d&lg";
        MyChatListener.DIMCHIG_NAME_GOLD = "&e&lDim&6&lChig";
        MyChatListener.SOUND_BED_BROKEN = "random.wood_click";
        MyChatListener.SOUND_TEAM_DESTROYED = "fireworks.blast";
        MyChatListener.SOUND_GAME_END = "fireworks.twinkle_far";
        MyChatListener.SOUND_REJECT = "note.bassattack";
        MyChatListener.SOUND_UPGRADE_BOUGHT = "random.anvil_land";
        MyChatListener.SOUND_TRAP_ACTIVATED = "note.pling";
        MyChatListener.SOUND_PLAYER_STATS = "random.orb";
        MyChatListener.SOUND_TEAM_CHOSEN = "random.click";
        MyChatListener.SOUND_PARTY_CREATED = "fireworks.twinkle_far";
        MyChatListener.SOUND_PARTY_CHAT = "random.orb";
        MyChatListener.SOUND_RADAR_FAR = "note.harp";
        MyChatListener.SOUND_RADAR_CLOSE = "note.pling";
        MyChatListener.SOUND_EMOTE = "random.pop";
        MyChatListener.PREFIX_BEDWARSBRO = "&r&cBedwars&fBro &8\u25b8 &r";
        MyChatListener.PREFIX_PARTY = "&r&6&lParty &8\u25b8 &r";
        MyChatListener.PREFIX_BED = "&r&c&lBed&8 \u25b8 &r";
        MyChatListener.PREFIX_TEAM = "&r&6&lTeam&8 \u25b8 &r";
        MyChatListener.PREFIX_ANTICHEAT = "&r&6&lAntiCheat&8 \u25b8 §r";
        MyChatListener.PREFIX_UPGRADES = "&r&b&lUpgrades&8 \u25b8 §r";
        MyChatListener.PREFIX_HINT = "&r&a&lHints&8 \u25b8 §r";
        MyChatListener.PREFIX_HINT_BED_SCANNER = "&r&c\u0417\u0430\u0449\u0438\u0442\u0430 &c\u043a\u0440\u043e\u0432\u0430\u0442\u0438&8 \u25b8 §r";
        MyChatListener.PREFIX_HINT_RADAR = "&r&a&lRadar&8 \u25b8 §r";
        MyChatListener.PREFIX_HINT_FINDER = "&r&b&l\u041f\u043e\u0438\u0441\u043a&8 \u25b8 §r";
        MyChatListener.PREFIX_HINT_RESOURCES_FINDER = "&r&e&l\u0420\u0435\u0441\u0443\u0440\u0441\u044b&8 \u25b8 §r";
        MyChatListener.PREFIX_ANTIFLOOD = "§6&l\u0410\u043d\u0442\u0438\u0424\u043b\u0443\u0434 &8\u25b8 &r";
        MyChatListener.PREFIX_DANGER_ALERT = "§c&l\u041e\u041f\u0410\u0421\u041d\u041e\u0421\u0422\u042c &8\u25b8 &r";
        MyChatListener.PREFIX_TNT_JUMP = "§4&lTNT &8\u25b8 &r";
        MyChatListener.PREFIX_WATER_DROP = "§b&lWater Drop &8\u25b8 &r";
        MyChatListener.PREFIX_BEDWARS_MEOW = "§e&lMeow &8\u25b8 &r";
        MyChatListener.PREFIX_DIMCHIG_JOINED = "§r§c§l\u203a &c[&c&lYou&f&lTube&f] &r";
        MyChatListener.PREFIX_ZERO_DEATH = "&r&a&lZero&c&lDeath&8 \u25b8 §r";
        MyChatListener.PREFIX_FRIENDS = "&r&6\u0414\u0440\u0443\u0437\u044c\u044f&8 \u25b8 §r";
        MyChatListener.PREFIX_MUTED = "&r&cMuted&8 \u25b8 §r";
        MyChatListener.PREFIX_FRIEND_IN_CHAT = "&f&l<\u0414\u0440\u0443\u0433> &r";
        MyChatListener.GAME_MAP_PLAYERS_MAX_SIZE = 0;
        MyChatListener.time_last_meow_message = -1L;
        MyChatListener.quee_last_meow_message = null;
    }
    
    public static class GameRecovery
    {
        public BWBed game_bed;
        public ArrayList<GuiMinimap.MyBed> minimap_beds;
        public LightningLocator.MyLightning last_lightning;
        
        public GameRecovery(final BWBed game_bed, final ArrayList<GuiMinimap.MyBed> minimap_beds, final LightningLocator.MyLightning last_lightning) {
            this.game_bed = game_bed;
            this.minimap_beds = minimap_beds;
            this.last_lightning = last_lightning;
        }
    }
    
    public static class ChatMessage
    {
        public CHAT_MESSAGE type;
        public String message;
        public String[] elements;
        public String[] element_values;
        public boolean isMustBeEqual;
        
        public ChatMessage(final CHAT_MESSAGE type, final String message, final String[] elements) {
            this.init(type, message, elements, false);
        }
        
        public ChatMessage(final CHAT_MESSAGE type, final String message, final String[] elements, final boolean isMustBeEqual) {
            this.init(type, message, elements, isMustBeEqual);
        }
        
        private void init(final CHAT_MESSAGE type, final String message, final String[] elements, final boolean isMustBeEqual) {
            this.type = type;
            this.message = message.trim();
            final ArrayList<String> arr = new ArrayList<String>();
            for (int i = 0; i < elements.length; ++i) {
                if (elements[i].length() > 0) {
                    arr.add("%" + elements[i].trim() + "%");
                }
            }
            this.elements = arr.toArray(new String[arr.size()]);
            this.element_values = new String[this.elements.length];
            this.isMustBeEqual = isMustBeEqual;
        }
    }
    
    public static class MsgMeowQuee
    {
        public String text;
        public long time;
        public boolean isInGameOnly;
        
        public MsgMeowQuee(final String text, final long time, final boolean isInGameOnly) {
            this.text = text;
            this.time = time;
            this.isInGameOnly = isInGameOnly;
        }
    }
    
    public static class MyStatistic
    {
        public String player;
        public int category_kills_cnt;
        public int category_kills_place;
        public int category_deathes_cnt;
        public int category_deathes_place;
        public int category_games_cnt;
        public int category_games_place;
        public int category_wins_cnt;
        public int category_wins_place;
        public int category_beds_cnt;
        public int category_beds_place;
        public float percentage_kill_to_death;
        public float percentage_games_to_wins;
        public int best_ranking;
        
        public MyStatistic(final String player) {
            this.player = player;
            this.category_kills_cnt = 0;
            this.category_kills_place = 0;
            this.category_deathes_cnt = 0;
            this.category_deathes_place = 0;
            this.category_games_cnt = 0;
            this.category_games_place = 0;
            this.category_wins_cnt = 0;
            this.category_wins_place = 0;
            this.category_beds_cnt = 0;
            this.category_beds_place = 0;
            this.percentage_kill_to_death = -1.0f;
            this.percentage_games_to_wins = -1.0f;
            this.best_ranking = -1;
        }
    }
    
    enum CHAT_MESSAGE
    {
        NONE, 
        CHAT_LEFT_GAME, 
        CHAT_GAME_STARTED, 
        CHAT_BEDWARS_GAME_STARTED_TIPS, 
        CHAT_JOINED_MIDGAME, 
        CHAT_JOINED_PREGAME, 
        CHAT_LEFT_PREGAME, 
        CHAT_SUICIDE, 
        CHAT_SUICIDE_VOID, 
        CHAT_KILLED_BY_VOID, 
        CHAT_KILLED_BY_PLAYER, 
        CHAT_TEAM_DESTROYED, 
        CHAT_TEAM_BED_BROKEN, 
        CHAT_TEAM_COLOR_CHOSEN, 
        CHAT_TEAM_ALREADY_CHOSEN, 
        CHAT_TEAM_IS_FULL, 
        CHAT_SHOP_ITEM_BOUGHT, 
        CHAT_SHOP_NOT_ENOUGH_RESOURCES, 
        CHAT_UPGRADE_BOUGHT, 
        CHAT_GENERATOR_DIAMOND_LEVELED_UP, 
        CHAT_GENERATOR_EMERALD_LEVELED_UP, 
        CHAT_TRAP_ACTIVATED, 
        CHAT_SERVER_RESTART, 
        CHAT_TELEPORTATION_TO_HUB, 
        CHAT_CONNECTING_TO_LOBBY, 
        CHAT_HUB_CHAT_PLAYER_MESSAGE, 
        CHAT_YOU_WERE_KICKED, 
        CHAT_ADS, 
        CHAT_GAME_STARTS_IN_SECONDS, 
        CHAT_GAME_CHAT_LOCAL, 
        CHAT_GAME_CHAT_GLOBAL, 
        CHAT_GAME_CHAT_SPECTATOR, 
        CHAT_GAME_CHAT_PREGAME, 
        CHAT_PREGAME_FASTSTART_REJECT, 
        CHAT_LOBBY_DONATER_GREETING, 
        CHAT_PLAYER_BANNED, 
        CHAT_PREGAME_NOT_ENOUGH_PLAYERS_TO_START, 
        CHAT_BEDWARS_END_TEAM_WON, 
        CHAT_BEDWARS_END_TOP_KILLER, 
        CHAT_BEDWARS_END_TOP_KILLER_UNKNOWN, 
        CHAT_LOBBY_PARTY_INVITE, 
        CHAT_LOBBY_PARTY_INVITE_REJECTED, 
        CHAT_LOBBY_PARTY_WARP, 
        CHAT_LOBBY_PARTY_PLAYER_OFFLINE, 
        CHAT_LOBBY_PARTY_DISBANDED, 
        CHAT_LOBBY_PARTY_ALREADY_IN_PARTY, 
        CHAT_LOBBY_PARTY_OFFLINE, 
        CHAT_LOBBY_PARTY_YOU_ARE_NOT_IN_PARTY, 
        CHAT_LOBBY_PARTY_PLAYER_KICKED, 
        CHAT_LOBBY_PARTY_PLAYER_LEFT, 
        CHAT_LOBBY_PARTY_CHAT_ENTER_MESSAGE, 
        CHAT_LOBBY_PARTY_CHAT_MESSAGE, 
        CHAT_LOBBY_PARTY_NO_PERMISSION, 
        CHAT_LOBBY_PARTY_DISBAND_REQUEST, 
        CHAT_LOBBY_PARTY_NOT_ENOUGH_SPACE, 
        CHAT_LOBBY_PARTY_REQUEST_ALREADY_SENT, 
        CHAT_LOBBY_PARTY_YOU_WERE_WARPED, 
        CHAT_LOBBY_PARTY_OWNER_LEFT, 
        CHAT_LOBBY_PARTY_REQUEST, 
        CHAT_LOBBY_PARTY_YOU_ACCEPTED_REQUEST, 
        CHAT_LOBBY_PARTY_NEW_LEADER, 
        CHAT_LOBBY_PARTY_COMMANDS_DONT_WORK_IN_LOBBY, 
        CHAT_LOBBY_PARTY_INFO, 
        CHAT_LOBBY_PARTY_JOIN_REQUEST, 
        CHAT_LOBBY_STATS_PLAYER, 
        CHAT_LOBBY_STATS_CATEGORY, 
        CHAT_PARTY_ON_CREATE, 
        CHAT_GAME_CANT_USE_COMMANDS, 
        CHAT_GAME_WAIT_SECONDS, 
        CHAT_GAME_YOU_CANT_USE_COLORS, 
        CHAT_GAME_YOU_CANT_USE_COLOR, 
        CHAT_GAME_ANTI_CHEAT_KICK, 
        CHAT_HUB_ANTIFLOOD, 
        CHAT_YOU_ARE_TELEPORTED_TO_LOBBY_DUE_TO_FULL_ARENA, 
        CHAY_YOU_ALREADY_ON_SERVER, 
        LOGIN_WITH_PASSWORD, 
        LOGIN_WITH_PASSWORD_CHEATMINE;
    }
}
