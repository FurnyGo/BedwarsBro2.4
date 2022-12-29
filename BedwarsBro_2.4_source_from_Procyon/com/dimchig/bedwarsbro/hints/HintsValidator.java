// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;

public class HintsValidator
{
    public static boolean isPasswordCorrect() {
        if (Minecraft.func_71410_x() == null || Minecraft.func_71410_x().field_71439_g == null) {
            return false;
        }
        if (Main.isPropUserBanned(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
            return false;
        }
        final String mod_last_version = Main.getPropModLastVersion();
        if (mod_last_version != null && !"2.4".equals(mod_last_version)) {
            MyChatListener.sendUpdateModMessage();
            return false;
        }
        return true;
    }
    
    public static boolean isBedScannerActive() {
        return isPasswordCorrect();
    }
    
    public static boolean isBedESPActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.BED_ESP) && isPasswordCorrect();
    }
    
    public static boolean isBedAutoToolActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.BED_AUTOTOOL) && isPasswordCorrect();
    }
    
    public static boolean isBedScannerChatMessage() {
        return Main.getConfigBool(Main.CONFIG_MSG.BED_SCANNER_SEND_TEAM) && isPasswordCorrect();
    }
    
    public static boolean isRadarActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.RADAR) && isPasswordCorrect();
    }
    
    public static boolean isHintsRadarPlayersActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.RADAR_PLAYERS) && isPasswordCorrect();
    }
    
    public static boolean isRadarIconActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.RADAR_ICON) && isPasswordCorrect();
    }
    
    public static boolean isItemCounterActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.ITEM_COUNTER) && isPasswordCorrect();
    }
    
    public static boolean isBlockCounterActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.ITEM_COUNTER_BLOCKS) && isPasswordCorrect();
    }
    
    public static boolean isFinderActive() {
        return isPasswordCorrect();
    }
    
    public static boolean isResourceFinderActive() {
        return isPasswordCorrect();
    }
    
    public static boolean isWinEmoteActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.WIN_EMOTE) && isPasswordCorrect();
    }
    
    public static boolean isMinimapActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.MINIMAP) && isPasswordCorrect();
    }
    
    public static boolean isParticlesActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.CUSTOM_PARTICLES) && isPasswordCorrect();
    }
    
    public static boolean isPotionEffectsTrackerActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.POTION_TRACKER) && isPasswordCorrect();
    }
    
    public static boolean isPotionEffectsTrackerSoundsActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.POTION_TRACKER_SOUNDS) && isPasswordCorrect();
    }
    
    public static boolean isDangerAlertActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.DANGER_ALERT) && isPasswordCorrect();
    }
    
    public static boolean isAutoSprintActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.AUTO_SPRINT) && isPasswordCorrect();
    }
    
    public static boolean isAutoWaterDropActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.AUTO_WATER_DROP) && isPasswordCorrect();
    }
    
    public static boolean isBedwarsMeowActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.BEDWARS_MEOW) && isPasswordCorrect();
    }
    
    public static boolean isBedwarsMeowColorsActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.BEDWARS_MEOW_WITH_COLORS) && isPasswordCorrect();
    }
    
    public static boolean isBetterShopActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.BETTER_SHOP) && isPasswordCorrect();
    }
    
    public static boolean isParticleTrailActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.PARTICLE_TRAIL);
    }
    
    public static boolean isParticleTrailRainbowActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.PARTICLE_TRAIL_RAINBOW);
    }
    
    public static boolean isInvulnerableTimerActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.INVULNERABLE_TIMER) && isPasswordCorrect();
    }
    
    public static boolean isInvulnerableTimerSoundsActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.INVULNERABLE_TIMER_SOUNDS) && isPasswordCorrect();
    }
    
    public static boolean isNamePlateActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.NAMEPLATE);
    }
    
    public static boolean isNamePlateRainbowActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.NAMEPLATE_RAINBOW);
    }
    
    public static boolean isResourcesHologramActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.RESOURCES_HOLOGRAM);
    }
    
    public static boolean isRadarChatMessage() {
        return Main.getConfigBool(Main.CONFIG_MSG.RADAR_MESSAGES) && isPasswordCorrect();
    }
    
    public static boolean isCrosshairBlocksActive() {
        return Main.getConfigBool(Main.CONFIG_MSG.CROSSHAIR_BLOCKS_COUNT) && isPasswordCorrect();
    }
    
    public static int getRainbowSpeed() {
        return Main.getConfigInt(Main.CONFIG_MSG.RAINBOW_SPEED);
    }
    
    public static String getNamePlateCustomColor() {
        return Main.getConfigString(Main.CONFIG_MSG.NAMEPLATE_RAINBOW_CONSTANT_COLOR);
    }
    
    public static boolean getNamePlateTeamColor() {
        return Main.getConfigBool(Main.CONFIG_MSG.NAMEPLATE_TEAM_COLOR);
    }
}
