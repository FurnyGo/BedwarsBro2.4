package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.CustomScoreboard;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.ScoreboardManager;
import com.dimchig.bedwarsbro.hints.HintsBaseRadar;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import com.dimchig.bedwarsbro.hints.HintsPlayerScanner;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import com.dimchig.bedwarsbro.hints.LobbyBlockPlacer;
import com.dimchig.bedwarsbro.hints.LobbyFly;
import com.dimchig.bedwarsbro.hints.WinEmote;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class OnMyTickEvent {

   private static ArrayList myfps = new ArrayList();
   public static String prevScoreboard = "";
   public static boolean isHintsRadarBaseActive = false;
   public static boolean isHintsRadarPlayersActive = false;
   public static boolean isDangerAlertActive = false;
   public static boolean isHintsItemCounterActive = false;
   public static boolean isHintsBlockCounterActive = false;
   public static boolean isBetterShopActive = false;
   public static boolean isWinEmoteActive = false;
   public static boolean isParticleTrailActive = false;
   public static int SCANNER_FREQUENCY = 10;
   public static int SCANNER_FREQUENCY_CNT = 0;
   public static boolean FINDER_IS_SEARCH_LOOP = false;
   public static String FIND_PLAYER_COMMAND_SEARCH = "";
   public static long FIND_PLAYER_COMMAND_SEARCH_TIME = 0L;
   Minecraft mc = Minecraft.func_71410_x();
   private KeyBinding keyTab;
   public static GuiScreen gui2open = null;
   private boolean flag_rclick = false;
   private boolean flag_lclick = false;
   private KeyBinding key_lclick;
   private KeyBinding key_rclick;
   ArrayList scanned_players = new ArrayList();
   public int zeroDeathHandlerRejoinVar = 0;


   public OnMyTickEvent() {
      this.keyTab = this.mc.field_71474_y.field_74321_H;
      this.updateHintsBooleans();
   }

   public void updateHintsBooleans() {
      isHintsRadarBaseActive = HintsValidator.isRadarActive();
      isHintsRadarPlayersActive = HintsValidator.isHintsRadarPlayersActive();
      isDangerAlertActive = HintsValidator.isDangerAlertActive();
      isHintsItemCounterActive = HintsValidator.isItemCounterActive();
      isHintsBlockCounterActive = HintsValidator.isBlockCounterActive();
      isBetterShopActive = HintsValidator.isBetterShopActive();
      isWinEmoteActive = HintsValidator.isWinEmoteActive();
      isParticleTrailActive = HintsValidator.isParticleTrailActive();
      Main.guiOnScreen.setDiamonds(-1);
      Main.guiOnScreen.setEmeralds(-1);
      Main.guiOnScreen.setBlocks(-1);
      this.key_lclick = this.mc.field_71474_y.field_74312_F;
      this.key_rclick = this.mc.field_71474_y.field_74313_G;
   }

   public ArrayList getCurrentScannedPlayers() {
      return this.scanned_players;
   }

   @SubscribeEvent
   public void playerTick(ClientTickEvent event) {
      if(this.mc != null) {
         if(this.mc.field_71439_g != null) {
            Minecraft var10001 = this.mc;
            myfps.add(Integer.valueOf(Minecraft.func_175610_ah()));
            if(myfps.size() > 40) {
               myfps.remove(0);
            }

            double s = 0.0D;

            int x;
            for(Iterator var4 = myfps.iterator(); var4.hasNext(); s += (double)x) {
               x = ((Integer)var4.next()).intValue();
            }

            s /= 40.0D;
            ScoreboardManager var10000 = Main.scoreboardManager;
            String var6 = ScoreboardManager.readRawScoreboard();
            if(var6 != null && var6.length() >= 0) {
               String t = ColorCodesManager.removeColorCodes(var6);
               if(!t.equals(prevScoreboard) || this.mc.field_71441_e.field_73012_v.nextInt(20) == 0) {
                  prevScoreboard = t;
                  CustomScoreboard.updateScoreboard();
               }
            }

            if(gui2open != null) {
               Minecraft.func_71410_x().func_147108_a(gui2open);
               gui2open = null;
            }

            Main.shopManager.scan(isBetterShopActive);
            if(MyChatListener.IS_IN_GAME) {
               ++SCANNER_FREQUENCY_CNT;
               if(SCANNER_FREQUENCY_CNT > SCANNER_FREQUENCY) {
                  SCANNER_FREQUENCY_CNT = 0;
                  this.scanned_players = HintsPlayerScanner.scanPlayers();
                  HintsBaseRadar var8 = Main.hintsBaseRadar;
                  HintsBaseRadar.scan(this.scanned_players, isHintsRadarBaseActive, isHintsRadarPlayersActive);
                  if(isDangerAlertActive) {
                     Main.dangerAlert.scan(this.scanned_players, this.mc.field_71439_g);
                  }

                  Main.generatorTimers.onTick();
                  Main.takeMaxSlotBlocks.handle();
               }

               Main.zeroDeathHandler.scan();
               Main.bedAutoTool.handleTools();
               if(isHintsItemCounterActive || isHintsBlockCounterActive) {
                  Main.itemTracker.scan();
               }

               if(FINDER_IS_SEARCH_LOOP) {
                  HintsFinder.findAll(false);
               }
            } else {
               Main.guiOnScreen.setDiamonds(-1);
               Main.guiOnScreen.setEmeralds(-1);
               Main.guiOnScreen.setBlocks(-1);
            }

            MyChatListener var9;
            if(LobbyBlockPlacer.state) {
               var9 = Main.chatListener;
               if((!MyChatListener.IS_IN_GAME || Main.shopManager.findItemInHotbar("Наблюдение за") != -1) && Main.shopManager.findItemInHotbar("Выбор коман") == -1) {
                  LobbyBlockPlacer.place();
               }
            }

            if(this.zeroDeathHandlerRejoinVar > 0) {
               --this.zeroDeathHandlerRejoinVar;
               ChatSender.sendText("/rejoin");
               var9 = Main.chatListener;
               MyChatListener.recoverGameBed();
            }

            if(FIND_PLAYER_COMMAND_SEARCH.length() > 0) {
               long var7 = (new Date()).getTime();
               if(var7 - FIND_PLAYER_COMMAND_SEARCH_TIME > 30000L) {
                  ChatSender.addText("&cНе удалось найти &e" + FIND_PLAYER_COMMAND_SEARCH);
                  var9 = Main.chatListener;
                  var9 = Main.chatListener;
                  MyChatListener.playSound(MyChatListener.SOUND_REJECT);
                  FIND_PLAYER_COMMAND_SEARCH = "";
               } else {
                  Main.commandFindPlayerByName.work(FIND_PLAYER_COMMAND_SEARCH);
               }
            }

            if(isWinEmoteActive) {
               WinEmote.handleEmote();
            }

            Main.freezeClutch.handle();
            if(isParticleTrailActive) {
               Main.particleTrail.drawPlayerTrail();
            }

            MyChatListener.handleBedwarsMeowMessagesQuee();
            if(this.keyTab.func_151470_d()) {
               Main.namePlateRenderer.updateGameTab();
            }

            LobbyFly var10;
            if(this.key_rclick.func_151470_d()) {
               if(this.flag_rclick) {
                  this.flag_rclick = false;
                  var10 = Main.lobbyFly;
                  if(LobbyFly.speed == 1.0F) {
                     var10 = Main.lobbyFly;
                     LobbyFly.speed = 0.5F;
                  } else {
                     var10 = Main.lobbyFly;
                     if(LobbyFly.speed == 0.5F) {
                        var10 = Main.lobbyFly;
                        LobbyFly.speed = 0.25F;
                     } else {
                        var10 = Main.lobbyFly;
                        if(LobbyFly.speed == 0.25F) {
                           var10 = Main.lobbyFly;
                           LobbyFly.speed = 0.25F;
                        } else {
                           var10 = Main.lobbyFly;
                           --LobbyFly.speed;
                        }
                     }
                  }
               }
            } else if(!this.flag_rclick) {
               this.flag_rclick = true;
            }

            if(this.key_lclick.func_151470_d()) {
               if(this.flag_lclick) {
                  this.flag_lclick = false;
                  Main.particlesAlwaysSharpness.onMyLeftClick();
                  var10 = Main.lobbyFly;
                  ++LobbyFly.speed;
               }
            } else if(!this.flag_lclick) {
               this.flag_lclick = true;
            }

         }
      }
   }

}
