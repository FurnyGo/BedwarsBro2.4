// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityLargeFireball;
import com.dimchig.bedwarsbro.hints.LobbyBlockPlacer;
import com.dimchig.bedwarsbro.hints.LightningLocator;
import com.dimchig.bedwarsbro.hints.LobbyFly;
import com.dimchig.bedwarsbro.hints.NamePlateRenderer;
import com.dimchig.bedwarsbro.commands.CommandFriends;
import com.dimchig.bedwarsbro.gui.GuiPlayerFocus;
import com.dimchig.bedwarsbro.hints.BridgeAutoAngle;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import com.dimchig.bedwarsbro.hints.HintsBedScanner;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiControls;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import com.dimchig.bedwarsbro.gui.GuiMinimap;
import java.util.ArrayList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

public class KeybindHandler
{
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
    public static String filename;
    boolean flagPlaceBlockUnderPlayer;
    boolean flagTowerDefence;
    private ArrayList<GuiMinimap.Pos> mybeds;
    
    public KeybindHandler(final Main asInstance) {
        this.flagPlaceBlockUnderPlayer = false;
        this.flagTowerDefence = false;
        this.mybeds = new ArrayList<GuiMinimap.Pos>();
        KeybindHandler.mc = Minecraft.func_71410_x();
        this.readKeys();
    }
    
    void readKeys() {
        String readFile = FileManager.readFile(KeybindHandler.filename);
        if (readFile == null || readFile.length() < 3) {
            this.initKeys();
        }
        readFile = FileManager.readFile(KeybindHandler.filename);
        if (readFile == null || readFile.length() < 3) {
            return;
        }
        final String category = ColorCodesManager.replaceColorCodesInString(" &c&lBedwars&f&lBro ");
        try {
            final String[] keys = readFile.split(";");
            final int key1 = Integer.parseInt(keys[0]);
            final int key2 = Integer.parseInt(keys[1]);
            final int key3 = Integer.parseInt(keys[2]);
            final int key4 = Integer.parseInt(keys[3]);
            final int key5 = Integer.parseInt(keys[4]);
            final int key6 = Integer.parseInt(keys[5]);
            final int key7 = Integer.parseInt(keys[6]);
            final int key8 = Integer.parseInt(keys[7]);
            final int key9 = Integer.parseInt(keys[8]);
            final int key10 = Integer.parseInt(keys[9]);
            final int key11 = Integer.parseInt(keys[10]);
            final int key12 = Integer.parseInt(keys[11]);
            final int key13 = Integer.parseInt(keys[12]);
            final int key14 = Integer.parseInt(keys[13]);
            final int key15 = Integer.parseInt(keys[14]);
            final int k = 57344;
            this.keyHintsBedScanner = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 1) + "&f\u0421\u043a\u0430\u043d\u0435\u0440 &c\u043a\u0440\u043e\u0432\u0430\u0442\u0438"), key1, category);
            this.keyHintsFinder = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 2) + "&f\u041d\u0430\u0439\u0442\u0438 &b\u0438\u0433\u0440\u043e\u043a\u043e\u0432"), key2, category);
            this.keyCommandLeave = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 3) + "&f\u041a\u043e\u043c\u0430\u043d\u0434\u0430 &c/leave"), key3, category);
            this.keyCommandRejoin = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 4) + "&f\u041a\u043e\u043c\u0430\u043d\u0434\u0430 &a/rejoin"), key4, category);
            this.keyCommandLeaveRejoin = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 5) + "&f\u041a\u043e\u043c\u0430\u043d\u0434\u0430 &c/leave &7+ &a/rejoin"), key5, category);
            this.keyCommandPartyWarp = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 6) + "&f\u041a\u043e\u043c\u0430\u043d\u0434\u0430 &e/party warp"), key6, category);
            this.keyRotateBind = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 7) + "&f\u0411\u0438\u043d\u0434 \u043d\u0430 &c\u0440\u043e\u0437\u0432\u043e\u0440\u043e\u0442"), key7, category);
            this.keyZoomMinimap = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 8) + "&bZoom &f\u043c\u0438\u043d\u0438\u043a\u0440\u0442\u044b"), key8, category);
            this.keyBridgeautoAngle = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 9) + "&f\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c &6\u0443\u0433\u043e\u043b \u0434\u043b\u044f GodBridge"), key9, category);
            this.keyPlaceBlockUnderPlayer = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 10) + "&f\u041f\u0440\u044b\u0436\u043a\u0438 \u043f\u043e \u0432\u043e\u0437\u0434\u0443\u0445\u0443 \u0432 &e\u043b\u043e\u0431\u0431\u0438 &7(\u041d\u0435 \u0447\u0438\u0442)"), key10, category);
            this.keyLobbyFly = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 11) + "&aFly &f\u0432 \u043b\u043e\u0431\u0431\u0438"), key11, category);
            this.keyLookAtMyBase = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 12) + "&f\u041f\u043e\u0441\u043c\u043e\u0442\u0440\u0435\u0442\u044c \u043d\u0430 &c\u0441\u0432\u043e\u044e \u0431\u0430\u0437\u0443"), key12, category);
            this.keyFreezeCluth = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 13) + "&b\u0417\u0430\u0444\u0440\u0438\u0437\u0438\u0442\u044c &f\u0438\u0433\u0440\u0443 \u0434\u043b\u044f \u043a\u043b\u0430\u0442\u0447\u0430"), key13, category);
            this.keyShowLastLightning = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 14) + "&b\u041f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u044e\u044e &b\u043c\u043e\u043b\u043d\u0438\u044e"), key14, category);
            this.keySpawnFakeFireball = new KeyBinding(ColorCodesManager.replaceColorCodesInString((char)(k + 15) + "&f\u0417\u0430\u0441\u043f\u0430\u0432\u043d\u0438\u0442\u044c &6\u0444\u0435\u0439\u043a-\u0444\u0430\u0435\u0440\u0431\u043e\u043b &7(\u0432\u0438\u0434\u0438\u0448\u044c \u0442\u043e\u043b\u044c\u043a\u043e \u0442\u044b)"), key15, category);
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
            this.keyPlayerFocus = KeybindHandler.mc.field_71474_y.field_152398_ap;
            this.keyMCAttack = KeybindHandler.mc.field_71474_y.field_74312_F;
            this.keyMCUseItem = KeybindHandler.mc.field_71474_y.field_74313_G;
            this.keyTab = KeybindHandler.mc.field_71474_y.field_74321_H;
            System.out.println("SUCCESFULLY REGISTERED");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    void initKeys() {
        String s = "";
        s += "19;";
        s += "82;";
        s += "71;";
        s += "72;";
        s += "36;";
        s += "73;";
        s += "41;";
        s += "0;";
        s += "48;";
        s += "33;";
        s += "38;";
        s += "37;";
        s += "0;";
        s += "0;";
        s += "0;";
        FileManager.writeToFile(s, KeybindHandler.filename, false);
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
            FileManager.writeToFile(s, KeybindHandler.filename, false);
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void changeKeybind(final GuiScreenEvent.KeyboardInputEvent.Post event) {
        if (event.gui instanceof GuiControls) {
            this.saveKeybindings();
        }
    }
    
    @SubscribeEvent
    public void onMouseInput(final InputEvent.MouseInputEvent e) throws Exception {
        this.handleKeys();
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent e) throws Exception {
        this.handleKeys();
    }
    
    public void handleKeys() {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        if (this.keyHintsBedScanner == null) {
            final StringBuilder sb = new StringBuilder();
            final MyChatListener chatListener = Main.chatListener;
            ChatSender.addText(sb.append(MyChatListener.PREFIX_BEDWARSBRO).append("&c\u041f\u0440\u043e\u0438\u0437\u043e\u0448\u043b\u0430 \u043e\u0448\u0438\u0431\u043a\u0430 \u0441 \u0444\u0430\u0439\u043b\u043e\u043c... \u041f\u043e\u043f\u044b\u0442\u043a\u0430 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438...").toString());
            this.initKeys();
            this.readKeys();
            this.saveKeybindings();
            return;
        }
        if (this.keyHintsBedScanner.func_151468_f() && HintsValidator.isBedScannerActive()) {
            HintsBedScanner.scanBed();
        }
        if (this.keyRotateBind.func_151468_f() && !Main.rotateBind.isActive) {
            Main.rotateBind.startRotate();
        }
        if (this.keyHintsFinder.func_151468_f() && HintsValidator.isFinderActive()) {
            ChatSender.addText(MyChatListener.PREFIX_HINT_FINDER + "&f\u041f\u043e\u0438\u0441\u043a \u0438\u0433\u0440\u043e\u043a\u043e\u0432...");
            HintsFinder.findAll(true);
        }
        if (this.keyBridgeautoAngle.func_151468_f() && HintsValidator.isPasswordCorrect()) {
            BridgeAutoAngle.aim();
        }
        if (this.keyPlayerFocus.func_151468_f() && this.keyPlayerFocus.func_151463_i() == 17 && HintsValidator.isPasswordCorrect()) {
            final GuiPlayerFocus playerFocus = Main.playerFocus;
            final GuiPlayerFocus playerFocus2 = Main.playerFocus;
            GuiPlayerFocus.STATE = !GuiPlayerFocus.STATE;
            this.keyPlayerFocus.func_151462_b(0);
            Minecraft.func_71410_x().field_71474_y.field_74351_w.func_151462_b(17);
            Minecraft.func_71410_x().field_71474_y.func_74303_b();
        }
        if (this.keyCommandLeaveRejoin.func_151468_f() && HintsValidator.isPasswordCorrect()) {
            ChatSender.sendText("/leave");
            Main.myTickEvent.zeroDeathHandlerRejoinVar = 10;
        }
        if (this.keyCommandLeave.func_151468_f() && HintsValidator.isPasswordCorrect()) {
            ChatSender.sendText("/leave");
        }
        if (this.keyCommandRejoin.func_151468_f() && HintsValidator.isPasswordCorrect()) {
            ChatSender.sendText("/rejoin");
        }
        if (this.keyCommandPartyWarp.func_151468_f() && HintsValidator.isPasswordCorrect()) {
            ChatSender.sendText("/party warp");
        }
        if (this.keyZoomMinimap.func_151468_f()) {
            Main.minimap.handleZoom();
        }
        if (this.keyTab.func_151468_f()) {
            final GuiMinimap minimap = Main.minimap;
            GuiMinimap.showNicknames = true;
            final NamePlateRenderer namePlateRenderer = Main.namePlateRenderer;
            final FileNicknamesManager fileNicknamesManager = Main.fileNicknamesManager;
            final CommandFriends commandFriends = Main.commandFriends;
            NamePlateRenderer.friends = fileNicknamesManager.readNames(CommandFriends.filename);
        }
        else if (!this.keyTab.func_151470_d()) {
            final GuiMinimap minimap2 = Main.minimap;
            GuiMinimap.showNicknames = false;
        }
        if (this.keyLobbyFly.func_151468_f()) {
            final LobbyFly lobbyFly = Main.lobbyFly;
            LobbyFly.isActive = true;
        }
        else if (!this.keyLobbyFly.func_151470_d()) {
            final LobbyFly lobbyFly2 = Main.lobbyFly;
            LobbyFly.isActive = false;
        }
        if (this.keyLookAtMyBase.func_151468_f()) {
            final GuiPlayerFocus playerFocus3 = Main.playerFocus;
            GuiPlayerFocus.isLookAtBaseActive = true;
            final MyChatListener chatListener2 = Main.chatListener;
            if (MyChatListener.GAME_BED == null) {
                ChatSender.addText("&c\u041d\u0435\u0442 \u043a\u0440\u043e\u0432\u0430\u0442\u0438 0_o");
            }
        }
        else if (!this.keyLobbyFly.func_151470_d()) {
            final GuiPlayerFocus playerFocus4 = Main.playerFocus;
            GuiPlayerFocus.isLookAtBaseActive = false;
        }
        if (this.keyFreezeCluth.func_151468_f() && HintsValidator.isPasswordCorrect()) {
            Main.freezeClutch.startFreeze();
        }
        if (this.keyShowLastLightning.func_151468_f()) {
            final LightningLocator lightningLocator = Main.lightningLocator;
            LightningLocator.isActive = true;
        }
        else if (!this.keyShowLastLightning.func_151470_d()) {
            final LightningLocator lightningLocator2 = Main.lightningLocator;
            LightningLocator.isActive = false;
        }
        if (this.keyPlaceBlockUnderPlayer.func_151468_f()) {
            if (!this.flagPlaceBlockUnderPlayer) {
                this.flagPlaceBlockUnderPlayer = true;
                LobbyBlockPlacer.state = !LobbyBlockPlacer.state;
            }
        }
        else if (this.flagPlaceBlockUnderPlayer) {
            this.flagPlaceBlockUnderPlayer = false;
        }
        if (this.keySpawnFakeFireball.func_151470_d()) {
            this.spawnFakeFireball();
        }
    }
    
    private void spawnFakeFireball() {
        final double posX = KeybindHandler.mc.field_71439_g.field_70165_t;
        final double posY = KeybindHandler.mc.field_71439_g.field_70163_u + KeybindHandler.mc.field_71439_g.func_70047_e();
        final double posZ = KeybindHandler.mc.field_71439_g.field_70161_v;
        final double d1 = 1000.0;
        final Vec3 vec3 = KeybindHandler.mc.field_71439_g.func_70676_i(1.0f);
        final double d2 = posX;
        final double d3 = KeybindHandler.mc.field_71439_g.field_70131_O;
        final double d4 = posZ;
        final EntityLargeFireball entitylargefireball = new EntityLargeFireball((World)KeybindHandler.mc.field_71441_e, (EntityLivingBase)KeybindHandler.mc.field_71439_g, vec3.field_72450_a * d1, vec3.field_72448_b * d1, vec3.field_72449_c * d1);
        entitylargefireball.field_92057_e = 0;
        entitylargefireball.func_96094_a("fake_fireball");
        entitylargefireball.field_70165_t = KeybindHandler.mc.field_71439_g.field_70165_t;
        entitylargefireball.field_70163_u = KeybindHandler.mc.field_71439_g.field_70163_u + KeybindHandler.mc.field_71439_g.func_70047_e();
        entitylargefireball.field_70161_v = KeybindHandler.mc.field_71439_g.field_70161_v;
        KeybindHandler.mc.field_71441_e.func_72838_d((Entity)entitylargefireball);
    }
    
    static {
        KeybindHandler.filename = "BedwarsBro_Keybindings_2.4.txt";
    }
}
