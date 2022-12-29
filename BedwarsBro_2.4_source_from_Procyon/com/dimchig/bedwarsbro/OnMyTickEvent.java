// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import com.dimchig.bedwarsbro.hints.LobbyFly;
import com.dimchig.bedwarsbro.hints.WinEmote;
import java.util.Date;
import com.dimchig.bedwarsbro.hints.LobbyBlockPlacer;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import com.dimchig.bedwarsbro.hints.HintsBaseRadar;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import com.dimchig.bedwarsbro.hints.HintsPlayerScanner;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;

public class OnMyTickEvent
{
    private static ArrayList<Integer> myfps;
    public static String prevScoreboard;
    public static boolean isHintsRadarBaseActive;
    public static boolean isHintsRadarPlayersActive;
    public static boolean isDangerAlertActive;
    public static boolean isHintsItemCounterActive;
    public static boolean isHintsBlockCounterActive;
    public static boolean isBetterShopActive;
    public static boolean isWinEmoteActive;
    public static boolean isParticleTrailActive;
    public static int SCANNER_FREQUENCY;
    public static int SCANNER_FREQUENCY_CNT;
    public static boolean FINDER_IS_SEARCH_LOOP;
    public static String FIND_PLAYER_COMMAND_SEARCH;
    public static long FIND_PLAYER_COMMAND_SEARCH_TIME;
    Minecraft mc;
    private KeyBinding keyTab;
    public static GuiScreen gui2open;
    private boolean flag_rclick;
    private boolean flag_lclick;
    private KeyBinding key_lclick;
    private KeyBinding key_rclick;
    ArrayList<HintsPlayerScanner.BWPlayer> scanned_players;
    public int zeroDeathHandlerRejoinVar;
    
    public OnMyTickEvent() {
        this.flag_rclick = false;
        this.flag_lclick = false;
        this.scanned_players = new ArrayList<HintsPlayerScanner.BWPlayer>();
        this.zeroDeathHandlerRejoinVar = 0;
        this.mc = Minecraft.func_71410_x();
        this.keyTab = this.mc.field_71474_y.field_74321_H;
        this.updateHintsBooleans();
    }
    
    public void updateHintsBooleans() {
        OnMyTickEvent.isHintsRadarBaseActive = HintsValidator.isRadarActive();
        OnMyTickEvent.isHintsRadarPlayersActive = HintsValidator.isHintsRadarPlayersActive();
        OnMyTickEvent.isDangerAlertActive = HintsValidator.isDangerAlertActive();
        OnMyTickEvent.isHintsItemCounterActive = HintsValidator.isItemCounterActive();
        OnMyTickEvent.isHintsBlockCounterActive = HintsValidator.isBlockCounterActive();
        OnMyTickEvent.isBetterShopActive = HintsValidator.isBetterShopActive();
        OnMyTickEvent.isWinEmoteActive = HintsValidator.isWinEmoteActive();
        OnMyTickEvent.isParticleTrailActive = HintsValidator.isParticleTrailActive();
        Main.guiOnScreen.setDiamonds(-1);
        Main.guiOnScreen.setEmeralds(-1);
        Main.guiOnScreen.setBlocks(-1);
        this.key_lclick = this.mc.field_71474_y.field_74312_F;
        this.key_rclick = this.mc.field_71474_y.field_74313_G;
    }
    
    public ArrayList<HintsPlayerScanner.BWPlayer> getCurrentScannedPlayers() {
        return this.scanned_players;
    }
    
    @SubscribeEvent
    public void playerTick(final TickEvent.ClientTickEvent event) {
        if (this.mc == null) {
            return;
        }
        if (this.mc.field_71439_g == null) {
            return;
        }
        final ArrayList<Integer> myfps = OnMyTickEvent.myfps;
        final Minecraft mc = this.mc;
        myfps.add(Minecraft.func_175610_ah());
        if (OnMyTickEvent.myfps.size() > 40) {
            OnMyTickEvent.myfps.remove(0);
        }
        double avg_fps = 0.0;
        for (final int x : OnMyTickEvent.myfps) {
            avg_fps += x;
        }
        avg_fps /= 40.0;
        final ScoreboardManager scoreboardManager = Main.scoreboardManager;
        final String s = ScoreboardManager.readRawScoreboard();
        if (s != null && s.length() >= 0) {
            final String current_scoreboard = ColorCodesManager.removeColorCodes(s);
            if (!current_scoreboard.equals(OnMyTickEvent.prevScoreboard) || this.mc.field_71441_e.field_73012_v.nextInt(20) == 0) {
                OnMyTickEvent.prevScoreboard = current_scoreboard;
                CustomScoreboard.updateScoreboard();
            }
        }
        if (OnMyTickEvent.gui2open != null) {
            Minecraft.func_71410_x().func_147108_a(OnMyTickEvent.gui2open);
            OnMyTickEvent.gui2open = null;
        }
        Main.shopManager.scan(OnMyTickEvent.isBetterShopActive);
        if (MyChatListener.IS_IN_GAME) {
            ++OnMyTickEvent.SCANNER_FREQUENCY_CNT;
            if (OnMyTickEvent.SCANNER_FREQUENCY_CNT > OnMyTickEvent.SCANNER_FREQUENCY) {
                OnMyTickEvent.SCANNER_FREQUENCY_CNT = 0;
                this.scanned_players = HintsPlayerScanner.scanPlayers();
                final HintsBaseRadar hintsBaseRadar = Main.hintsBaseRadar;
                HintsBaseRadar.scan(this.scanned_players, OnMyTickEvent.isHintsRadarBaseActive, OnMyTickEvent.isHintsRadarPlayersActive);
                if (OnMyTickEvent.isDangerAlertActive) {
                    Main.dangerAlert.scan(this.scanned_players, this.mc.field_71439_g);
                }
                Main.generatorTimers.onTick();
                Main.takeMaxSlotBlocks.handle();
            }
            Main.zeroDeathHandler.scan();
            Main.bedAutoTool.handleTools();
            if (OnMyTickEvent.isHintsItemCounterActive || OnMyTickEvent.isHintsBlockCounterActive) {
                Main.itemTracker.scan();
            }
            if (OnMyTickEvent.FINDER_IS_SEARCH_LOOP) {
                HintsFinder.findAll(false);
            }
        }
        else {
            Main.guiOnScreen.setDiamonds(-1);
            Main.guiOnScreen.setEmeralds(-1);
            Main.guiOnScreen.setBlocks(-1);
        }
        if (LobbyBlockPlacer.state) {
            final MyChatListener chatListener = Main.chatListener;
            if ((!MyChatListener.IS_IN_GAME || Main.shopManager.findItemInHotbar("\u041d\u0430\u0431\u043b\u044e\u0434\u0435\u043d\u0438\u0435 \u0437\u0430") != -1) && Main.shopManager.findItemInHotbar("\u0412\u044b\u0431\u043e\u0440 \u043a\u043e\u043c\u0430\u043d") == -1) {
                LobbyBlockPlacer.place();
            }
        }
        if (this.zeroDeathHandlerRejoinVar > 0) {
            --this.zeroDeathHandlerRejoinVar;
            ChatSender.sendText("/rejoin");
            final MyChatListener chatListener2 = Main.chatListener;
            MyChatListener.recoverGameBed();
        }
        if (OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH.length() > 0) {
            final long t = new Date().getTime();
            if (t - OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH_TIME > 30000L) {
                ChatSender.addText("&c\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043d\u0430\u0439\u0442\u0438 &e" + OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH);
                final MyChatListener chatListener3 = Main.chatListener;
                final MyChatListener chatListener4 = Main.chatListener;
                MyChatListener.playSound(MyChatListener.SOUND_REJECT);
                OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH = "";
            }
            else {
                Main.commandFindPlayerByName.work(OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH);
            }
        }
        if (OnMyTickEvent.isWinEmoteActive) {
            WinEmote.handleEmote();
        }
        Main.freezeClutch.handle();
        if (OnMyTickEvent.isParticleTrailActive) {
            Main.particleTrail.drawPlayerTrail();
        }
        MyChatListener.handleBedwarsMeowMessagesQuee();
        if (this.keyTab.func_151470_d()) {
            Main.namePlateRenderer.updateGameTab();
        }
        if (this.key_rclick.func_151470_d()) {
            if (this.flag_rclick) {
                this.flag_rclick = false;
                final LobbyFly lobbyFly = Main.lobbyFly;
                if (LobbyFly.speed == 1.0f) {
                    final LobbyFly lobbyFly2 = Main.lobbyFly;
                    LobbyFly.speed = 0.5f;
                }
                else {
                    final LobbyFly lobbyFly3 = Main.lobbyFly;
                    if (LobbyFly.speed == 0.5f) {
                        final LobbyFly lobbyFly4 = Main.lobbyFly;
                        LobbyFly.speed = 0.25f;
                    }
                    else {
                        final LobbyFly lobbyFly5 = Main.lobbyFly;
                        if (LobbyFly.speed == 0.25f) {
                            final LobbyFly lobbyFly6 = Main.lobbyFly;
                            LobbyFly.speed = 0.25f;
                        }
                        else {
                            final LobbyFly lobbyFly7 = Main.lobbyFly;
                            --LobbyFly.speed;
                        }
                    }
                }
            }
        }
        else if (!this.flag_rclick) {
            this.flag_rclick = true;
        }
        if (this.key_lclick.func_151470_d()) {
            if (this.flag_lclick) {
                this.flag_lclick = false;
                Main.particlesAlwaysSharpness.onMyLeftClick();
                final LobbyFly lobbyFly8 = Main.lobbyFly;
                ++LobbyFly.speed;
            }
        }
        else if (!this.flag_lclick) {
            this.flag_lclick = true;
        }
    }
    
    static {
        OnMyTickEvent.myfps = new ArrayList<Integer>();
        OnMyTickEvent.prevScoreboard = "";
        OnMyTickEvent.isHintsRadarBaseActive = false;
        OnMyTickEvent.isHintsRadarPlayersActive = false;
        OnMyTickEvent.isDangerAlertActive = false;
        OnMyTickEvent.isHintsItemCounterActive = false;
        OnMyTickEvent.isHintsBlockCounterActive = false;
        OnMyTickEvent.isBetterShopActive = false;
        OnMyTickEvent.isWinEmoteActive = false;
        OnMyTickEvent.isParticleTrailActive = false;
        OnMyTickEvent.SCANNER_FREQUENCY = 10;
        OnMyTickEvent.SCANNER_FREQUENCY_CNT = 0;
        OnMyTickEvent.FINDER_IS_SEARCH_LOOP = false;
        OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH = "";
        OnMyTickEvent.FIND_PLAYER_COMMAND_SEARCH_TIME = 0L;
        OnMyTickEvent.gui2open = null;
    }
}
