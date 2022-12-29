// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.player.EntityPlayer;
import com.dimchig.bedwarsbro.ColorCodesManager;
import java.util.Date;
import net.minecraft.world.World;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.client.Minecraft;

public class HintsBedScanner
{
    public static long time_last_message_sent_for_team;
    
    public static void scanBed() {
        final EntityPlayer mod_player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        final MovingObjectPosition ray = mod_player.func_174822_a(100.0, 1.0f);
        if (ray == null) {
            return;
        }
        final int blockHitX = (int)ray.field_72307_f.field_72450_a;
        final int blockHitY = (int)ray.field_72307_f.field_72448_b;
        final int blockHitZ = (int)ray.field_72307_f.field_72449_c;
        final String prefix = MyChatListener.PREFIX_HINT_BED_SCANNER;
        final ArrayList<BWBed> beds = findBeds(blockHitX, blockHitY, blockHitZ);
        if (beds == null || beds.size() == 0) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        BWBed bed = null;
        int min_dist = 999999;
        for (final BWBed b : beds) {
            final int dist = (int)Math.sqrt(Math.pow(b.part1_posX - blockHitX, 2.0) + Math.pow(b.part1_posZ - blockHitZ, 2.0));
            if (dist < min_dist) {
                min_dist = dist;
                bed = b;
            }
        }
        if (bed == null) {
            ChatSender.addText(prefix + "&f\u041a\u0440\u043e\u0432\u0430\u0442\u044c \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430!");
            return;
        }
        final String bed_analisys = bed.getAnalysis();
        if (Main.getConfigBool(Main.CONFIG_MSG.BED_SCANNER_ANIMATION)) {
            try {
                bed.showLayers((World)Minecraft.func_71410_x().field_71441_e);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ChatSender.addText(prefix + bed_analisys);
        if (HintsValidator.isBedScannerChatMessage() && new Date().getTime() - HintsBedScanner.time_last_message_sent_for_team > Main.ANTIMUT_DELAY) {
            HintsBedScanner.time_last_message_sent_for_team = new Date().getTime();
            String s = (prefix.replace(" \u25b8", ":") + bed_analisys).replace("§", "&").replace("&r", "") + "&e";
            s = s.replace("\u25b8", "->");
            if (!HintsValidator.isBedwarsMeowColorsActive()) {
                s = ColorCodesManager.removeColorCodes(s);
            }
            Minecraft.func_71410_x().field_71439_g.func_71165_d(s);
        }
    }
    
    public static ArrayList<BWBed> findBeds(final int rayPosX, final int rayPosY, final int rayPosZ) {
        try {
            final ArrayList<BWBed> beds_parts = new ArrayList<BWBed>();
            final Entity mod_player = (Entity)Minecraft.func_71410_x().field_71439_g;
            final int range = 30;
            boolean bed_level = false;
            int cnt = 0;
            for (int yi = -range; yi < range; ++yi) {
                for (int xi = -range; xi < range; ++xi) {
                    for (int zi = -range; zi < range; ++zi) {
                        final int bx = rayPosX + xi;
                        final int by = rayPosY + yi;
                        final int bz = rayPosZ + zi;
                        ++cnt;
                        final Block block = Minecraft.func_71410_x().field_71441_e.func_180495_p(new BlockPos(bx, by, bz)).func_177230_c();
                        if (block != null && block != null && block.func_149739_a().substring(5).equals("bed")) {
                            bed_level = true;
                            beds_parts.add(new BWBed(bx, by, bz, 0, 0, 0));
                        }
                    }
                }
                if (bed_level) {
                    break;
                }
            }
            final ArrayList<BWBed> beds = new ArrayList<BWBed>();
            int cnt_prevent_loop = 0;
            while (beds_parts.size() > 0 && ++cnt_prevent_loop <= 1000) {
                for (final BWBed bed1 : beds_parts) {
                    boolean isBreak = false;
                    for (final BWBed bed2 : beds_parts) {
                        if ((bed1.part1_posX != bed2.part1_posX || bed1.part1_posY != bed2.part1_posY || bed1.part1_posZ != bed2.part1_posZ) && BWBed.isBlockConnectsToBlock(bed1.part1_posX, bed1.part1_posY, bed1.part1_posZ, bed2.part1_posX, bed2.part1_posY, bed2.part1_posZ)) {
                            beds.add(new BWBed(bed1.part1_posX, bed1.part1_posY, bed1.part1_posZ, bed2.part1_posX, bed2.part1_posY, bed2.part1_posZ));
                            beds_parts.remove(bed1);
                            beds_parts.remove(bed2);
                            isBreak = true;
                            break;
                        }
                    }
                    if (isBreak) {
                        break;
                    }
                }
            }
            final World world = (World)Minecraft.func_71410_x().field_71441_e;
            for (final BWBed bed3 : beds) {
                bed3.scanDefence(world);
            }
            return beds;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    static {
        HintsBedScanner.time_last_message_sent_for_team = 0L;
    }
}
