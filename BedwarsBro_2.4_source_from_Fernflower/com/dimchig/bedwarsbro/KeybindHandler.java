package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.FileManager;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.commands.CommandFriends;
import com.dimchig.bedwarsbro.gui.GuiMinimap;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import com.dimchig.bedwarsbro.hints.BridgeAutoAngle;
import com.dimchig.bedwarsbro.hints.HintsBedScanner;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import com.dimchig.bedwarsbro.hints.LightningLocator;
import com.dimchig.bedwarsbro.hints.LobbyBlockPlacer;
import com.dimchig.bedwarsbro.hints.LobbyFly;
import com.dimchig.bedwarsbro.hints.NamePlateRenderer;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent.Post;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;

public class KeybindHandler {

   static Minecraft mc;
   KeyBinding keyHintsBedScanner;
   KeyBinding keyHintsFinder;
   KeyBinding keyBridgeautoAngle;
   KeyBinding keyPlayerFocus;
   KeyBinding keyCommandLeave;
   KeyBinding keyCommandRejoin;
   KeyBinding keyCommandLeaveRejoin;
   KeyBinding keyCommandPartyWarp;
   KeyBinding keyRotateBind;
   KeyBinding keyZoomMinimap;
   KeyBinding keyPlaceBlockUnderPlayer;
   KeyBinding keyLobbyFly;
   KeyBinding keyLookAtMyBase;
   KeyBinding keyFreezeCluth;
   KeyBinding keyShowLastLightning;
   KeyBinding keySpawnFakeFireball;
   KeyBinding keyMCAttack;
   KeyBinding keyMCUseItem;
   KeyBinding keyTab;
   public static String filename = "BedwarsBro_Keybindings_2.4.txt";
   boolean flagPlaceBlockUnderPlayer = false;
   boolean flagTowerDefence = false;
   private ArrayList mybeds = new ArrayList();


   public KeybindHandler(Main asInstance) {
      mc = Minecraft.func_71410_x();
      this.readKeys();
   }

   void readKeys() {
      String readFile = FileManager.readFile(filename);
      if(readFile == null || readFile.length() < 3) {
         this.initKeys();
      }

      readFile = FileManager.readFile(filename);
      if(readFile != null && readFile.length() >= 3) {
         String category = ColorCodesManager.replaceColorCodesInString(" &c&lBedwars&f&lBro ");

         try {
            String[] ex = readFile.split(";");
            int key1 = Integer.parseInt(ex[0]);
            int key2 = Integer.parseInt(ex[1]);
            int key3 = Integer.parseInt(ex[2]);
            int key4 = Integer.parseInt(ex[3]);
            int key5 = Integer.parseInt(ex[4]);
            int key6 = Integer.parseInt(ex[5]);
            int key7 = Integer.parseInt(ex[6]);
            int key8 = Integer.parseInt(ex[7]);
            int key9 = Integer.parseInt(ex[8]);
            int key10 = Integer.parseInt(ex[9]);
            int key11 = Integer.parseInt(ex[10]);
            int key12 = Integer.parseInt(ex[11]);
            int key13 = Integer.parseInt(ex[12]);
            int key14 = Integer.parseInt(ex[13]);
            int key15 = Integer.parseInt(ex[14]);
            char k = '\ue000';
            this.keyHintsBedScanner = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 1) + "&fСканер &cкровати"), key1, category);
            this.keyHintsFinder = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 2) + "&fНайти &bигроков"), key2, category);
            this.keyCommandLeave = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 3) + "&fКоманда &c/leave"), key3, category);
            this.keyCommandRejoin = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 4) + "&fКоманда &a/rejoin"), key4, category);
            this.keyCommandLeaveRejoin = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 5) + "&fКоманда &c/leave &7+ &a/rejoin"), key5, category);
            this.keyCommandPartyWarp = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 6) + "&fКоманда &e/party warp"), key6, category);
            this.keyRotateBind = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 7) + "&fБинд на &cрозворот"), key7, category);
            this.keyZoomMinimap = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 8) + "&bZoom &fминикрты"), key8, category);
            this.keyBridgeautoAngle = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 9) + "&fУстановить &6угол для GodBridge"), key9, category);
            this.keyPlaceBlockUnderPlayer = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 10) + "&fПрыжки по воздуху в &eлобби &7(Не чит)"), key10, category);
            this.keyLobbyFly = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 11) + "&aFly &fв лобби"), key11, category);
            this.keyLookAtMyBase = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 12) + "&fПосмотреть на &cсвою базу"), key12, category);
            this.keyFreezeCluth = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 13) + "&bЗафризить &fигру для клатча"), key13, category);
            this.keyShowLastLightning = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 14) + "&bПоказать последнюю &bмолнию"), key14, category);
            this.keySpawnFakeFireball = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 15) + "&fЗаспавнить &6фейк-фаербол &7(видишь только ты)"), key15, category);
            ClientRegistry.registerKeyBinding(this.keyHintsBedScanner);
            ClientRegistry.registerKeyBinding(this.keyHintsFinder);
            ClientRegistry.registerKeyBinding(this.keyBridgeautoAngle);
            ClientRegistry.registerKeyBinding(this.keyCommandLeave);
            ClientRegistry.registerKeyBinding(this.keyCommandRejoin);
            ClientRegistry.registerKeyBinding(this.keyCommandLeaveRejoin);
            ClientRegistry.registerKeyBinding(this.keyCommandPartyWarp);
            ClientRegistry.registerKeyBinding(this.keyRotateBind);
            ClientRegistry.registerKeyBinding(this.keyZoomMinimap);
            ClientRegistry.registerKeyBinding(this.keyPlaceBlockUnderPlayer);
            ClientRegistry.registerKeyBinding(this.keyLobbyFly);
            ClientRegistry.registerKeyBinding(this.keyLookAtMyBase);
            ClientRegistry.registerKeyBinding(this.keyFreezeCluth);
            ClientRegistry.registerKeyBinding(this.keyShowLastLightning);
            ClientRegistry.registerKeyBinding(this.keySpawnFakeFireball);
            this.keyPlayerFocus = mc.field_71474_y.field_152398_ap;
            this.keyMCAttack = mc.field_71474_y.field_74312_F;
            this.keyMCUseItem = mc.field_71474_y.field_74313_G;
            this.keyTab = mc.field_71474_y.field_74321_H;
            System.out.println("SUCCESFULLY REGISTERED");
         } catch (Exception var20) {
            var20.printStackTrace();
         }

      }
   }

   void initKeys() {
      String s = "";
      s = s + "19;";
      s = s + "82;";
      s = s + "71;";
      s = s + "72;";
      s = s + "36;";
      s = s + "73;";
      s = s + "41;";
      s = s + "0;";
      s = s + "48;";
      s = s + "33;";
      s = s + "38;";
      s = s + "37;";
      s = s + "0;";
      s = s + "0;";
      s = s + "0;";
      FileManager.writeToFile(s, filename, false);
   }

   void saveKeybindings() {
      try {
         String s = "";
         s = s + this.keyHintsBedScanner.func_151463_i() + ";";
         s = s + this.keyHintsFinder.func_151463_i() + ";";
         s = s + this.keyCommandLeave.func_151463_i() + ";";
         s = s + this.keyCommandRejoin.func_151463_i() + ";";
         s = s + this.keyCommandLeaveRejoin.func_151463_i() + ";";
         s = s + this.keyCommandPartyWarp.func_151463_i() + ";";
         s = s + this.keyRotateBind.func_151463_i() + ";";
         s = s + this.keyZoomMinimap.func_151463_i() + ";";
         s = s + this.keyBridgeautoAngle.func_151463_i() + ";";
         s = s + this.keyPlaceBlockUnderPlayer.func_151463_i() + ";";
         s = s + this.keyLobbyFly.func_151463_i() + ";";
         s = s + this.keyLookAtMyBase.func_151463_i() + ";";
         s = s + this.keyFreezeCluth.func_151463_i() + ";";
         s = s + this.keyShowLastLightning.func_151463_i() + ";";
         s = s + this.keySpawnFakeFireball.func_151463_i() + ";";
         FileManager.writeToFile(s, filename, false);
      } catch (Exception var2) {
         ;
      }

   }

   @SubscribeEvent
   public void changeKeybind(Post event) {
      if(event.gui instanceof GuiControls) {
         this.saveKeybindings();
      }

   }

   @SubscribeEvent
   public void onMouseInput(MouseInputEvent e) throws Exception {
      this.handleKeys();
   }

   @SubscribeEvent
   public void onKeyInput(KeyInputEvent e) throws Exception {
      this.handleKeys();
   }

   public void handleKeys() {
      if(Minecraft.func_71410_x().field_71439_g != null) {
         if(this.keyHintsBedScanner == null) {
            StringBuilder var7 = new StringBuilder();
            MyChatListener var3 = Main.chatListener;
            ChatSender.addText(var7.append(MyChatListener.PREFIX_BEDWARSBRO).append("&cПроизошла ошибка с файлом... Попытка настройки...").toString());
            this.initKeys();
            this.readKeys();
            this.saveKeybindings();
         } else {
            if(this.keyHintsBedScanner.func_151468_f() && HintsValidator.isBedScannerActive()) {
               HintsBedScanner.scanBed();
            }

            if(this.keyRotateBind.func_151468_f() && !Main.rotateBind.isActive) {
               Main.rotateBind.startRotate();
            }

            if(this.keyHintsFinder.func_151468_f() && HintsValidator.isFinderActive()) {
               ChatSender.addText(MyChatListener.PREFIX_HINT_FINDER + "&fПоиск игроков...");
               HintsFinder.findAll(true);
            }

            if(this.keyBridgeautoAngle.func_151468_f() && HintsValidator.isPasswordCorrect()) {
               BridgeAutoAngle.aim();
            }

            GuiPlayerFocus var10000;
            if(this.keyPlayerFocus.func_151468_f() && this.keyPlayerFocus.func_151463_i() == 17 && HintsValidator.isPasswordCorrect()) {
               var10000 = Main.playerFocus;
               var10000 = Main.playerFocus;
               GuiPlayerFocus.STATE = !GuiPlayerFocus.STATE;
               this.keyPlayerFocus.func_151462_b(0);
               Minecraft.func_71410_x().field_71474_y.field_74351_w.func_151462_b(17);
               Minecraft.func_71410_x().field_71474_y.func_74303_b();
            }

            if(this.keyCommandLeaveRejoin.func_151468_f() && HintsValidator.isPasswordCorrect()) {
               ChatSender.sendText("/leave");
               Main.myTickEvent.zeroDeathHandlerRejoinVar = 10;
            }

            if(this.keyCommandLeave.func_151468_f() && HintsValidator.isPasswordCorrect()) {
               ChatSender.sendText("/leave");
            }

            if(this.keyCommandRejoin.func_151468_f() && HintsValidator.isPasswordCorrect()) {
               ChatSender.sendText("/rejoin");
            }

            if(this.keyCommandPartyWarp.func_151468_f() && HintsValidator.isPasswordCorrect()) {
               ChatSender.sendText("/party warp");
            }

            if(this.keyZoomMinimap.func_151468_f()) {
               Main.minimap.handleZoom();
            }

            GuiMinimap var1;
            if(this.keyTab.func_151468_f()) {
               var1 = Main.minimap;
               GuiMinimap.showNicknames = true;
               NamePlateRenderer var2 = Main.namePlateRenderer;
               CommandFriends var10001 = Main.commandFriends;
               NamePlateRenderer.friends = Main.fileNicknamesManager.readNames(CommandFriends.filename);
            } else if(!this.keyTab.func_151470_d()) {
               var1 = Main.minimap;
               GuiMinimap.showNicknames = false;
            }

            LobbyFly var4;
            if(this.keyLobbyFly.func_151468_f()) {
               var4 = Main.lobbyFly;
               LobbyFly.isActive = true;
            } else if(!this.keyLobbyFly.func_151470_d()) {
               var4 = Main.lobbyFly;
               LobbyFly.isActive = false;
            }

            if(this.keyLookAtMyBase.func_151468_f()) {
               var10000 = Main.playerFocus;
               GuiPlayerFocus.isLookAtBaseActive = true;
               MyChatListener var5 = Main.chatListener;
               if(MyChatListener.GAME_BED == null) {
                  ChatSender.addText("&cНет кровати 0_o");
               }
            } else if(!this.keyLobbyFly.func_151470_d()) {
               var10000 = Main.playerFocus;
               GuiPlayerFocus.isLookAtBaseActive = false;
            }

            if(this.keyFreezeCluth.func_151468_f() && HintsValidator.isPasswordCorrect()) {
               Main.freezeClutch.startFreeze();
            }

            LightningLocator var6;
            if(this.keyShowLastLightning.func_151468_f()) {
               var6 = Main.lightningLocator;
               LightningLocator.isActive = true;
            } else if(!this.keyShowLastLightning.func_151470_d()) {
               var6 = Main.lightningLocator;
               LightningLocator.isActive = false;
            }

            if(this.keyPlaceBlockUnderPlayer.func_151468_f()) {
               if(!this.flagPlaceBlockUnderPlayer) {
                  this.flagPlaceBlockUnderPlayer = true;
                  LobbyBlockPlacer.state = !LobbyBlockPlacer.state;
               }
            } else if(this.flagPlaceBlockUnderPlayer) {
               this.flagPlaceBlockUnderPlayer = false;
            }

            if(this.keySpawnFakeFireball.func_151470_d()) {
               this.spawnFakeFireball();
            }

         }
      }
   }

   private void spawnFakeFireball() {
      double posX = mc.field_71439_g.field_70165_t;
      double posY = mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e();
      double posZ = mc.field_71439_g.field_70161_v;
      double d1 = 1000.0D;
      Vec3 vec3 = mc.field_71439_g.func_70676_i(1.0F);
      double d3 = (double)mc.field_71439_g.field_70131_O;
      EntityLargeFireball entitylargefireball = new EntityLargeFireball(mc.field_71441_e, mc.field_71439_g, vec3.field_72450_a * d1, vec3.field_72448_b * d1, vec3.field_72449_c * d1);
      entitylargefireball.field_92057_e = 0;
      entitylargefireball.func_96094_a("fake_fireball");
      entitylargefireball.field_70165_t = mc.field_71439_g.field_70165_t;
      entitylargefireball.field_70163_u = mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e();
      entitylargefireball.field_70161_v = mc.field_71439_g.field_70161_v;
      mc.field_71441_e.func_72838_d(entitylargefireball);
   }

}
