package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.hints.BWBed;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class HintsBedScanner {

   public static long time_last_message_sent_for_team = 0L;


   public static void scanBed() {
      EntityPlayerSP mod_player = Minecraft.func_71410_x().field_71439_g;
      MovingObjectPosition ray = mod_player.func_174822_a(100.0D, 1.0F);
      if(ray != null) {
         int blockHitX = (int)ray.field_72307_f.field_72450_a;
         int blockHitY = (int)ray.field_72307_f.field_72448_b;
         int blockHitZ = (int)ray.field_72307_f.field_72449_c;
         String prefix = MyChatListener.PREFIX_HINT_BED_SCANNER;
         ArrayList beds = findBeds(blockHitX, blockHitY, blockHitZ);
         if(beds != null && beds.size() != 0) {
            EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
            BWBed bed = null;
            int min_dist = 999999;
            Iterator bed_analisys = beds.iterator();

            while(bed_analisys.hasNext()) {
               BWBed s = (BWBed)bed_analisys.next();
               int dist = (int)Math.sqrt(Math.pow((double)(s.part1_posX - blockHitX), 2.0D) + Math.pow((double)(s.part1_posZ - blockHitZ), 2.0D));
               if(dist < min_dist) {
                  min_dist = dist;
                  bed = s;
               }
            }

            if(bed == null) {
               ChatSender.addText(prefix + "&fКровать не найдена!");
            } else {
               String bed_analisys1 = bed.getAnalysis();
               if(Main.getConfigBool(Main.CONFIG_MSG.BED_SCANNER_ANIMATION)) {
                  try {
                     bed.showLayers(Minecraft.func_71410_x().field_71441_e);
                  } catch (Exception var13) {
                     var13.printStackTrace();
                  }
               }

               ChatSender.addText(prefix + bed_analisys1);
               if(HintsValidator.isBedScannerChatMessage() && (new Date()).getTime() - time_last_message_sent_for_team > (long)Main.ANTIMUT_DELAY) {
                  time_last_message_sent_for_team = (new Date()).getTime();
                  String s1 = (prefix.replace(" ▸", ":") + bed_analisys1).replace("§", "&").replace("&r", "") + "&e";
                  s1 = s1.replace("▸", "->");
                  if(!HintsValidator.isBedwarsMeowColorsActive()) {
                     s1 = ColorCodesManager.removeColorCodes(s1);
                  }

                  Minecraft.func_71410_x().field_71439_g.func_71165_d(s1);
               }

            }
         }
      }
   }

   public static ArrayList findBeds(int rayPosX, int rayPosY, int rayPosZ) {
      try {
         ArrayList ex = new ArrayList();
         EntityPlayerSP mod_player = Minecraft.func_71410_x().field_71439_g;
         byte range = 30;
         boolean bed_level = false;
         int cnt = 0;

         int cnt_prevent_loop;
         for(int beds = -range; beds < range; ++beds) {
            for(cnt_prevent_loop = -range; cnt_prevent_loop < range; ++cnt_prevent_loop) {
               for(int world = -range; world < range; ++world) {
                  int bed1 = rayPosX + cnt_prevent_loop;
                  int bed = rayPosY + beds;
                  int bz = rayPosZ + world;
                  ++cnt;
                  Block bed2 = Minecraft.func_71410_x().field_71441_e.func_180495_p(new BlockPos(bed1, bed, bz)).func_177230_c();
                  if(bed2 != null && bed2 != null && bed2.func_149739_a().substring(5).equals("bed")) {
                     bed_level = true;
                     ex.add(new BWBed(bed1, bed, bz, 0, 0, 0));
                  }
               }
            }

            if(bed_level) {
               break;
            }
         }

         ArrayList var16 = new ArrayList();
         cnt_prevent_loop = 0;

         label76:
         while(ex.size() > 0) {
            ++cnt_prevent_loop;
            if(cnt_prevent_loop > 1000) {
               break;
            }

            Iterator var17 = ex.iterator();

            while(var17.hasNext()) {
               BWBed var19 = (BWBed)var17.next();
               boolean var21 = false;
               Iterator var23 = ex.iterator();

               while(true) {
                  if(var23.hasNext()) {
                     BWBed var24 = (BWBed)var23.next();
                     if(var19.part1_posX == var24.part1_posX && var19.part1_posY == var24.part1_posY && var19.part1_posZ == var24.part1_posZ || !BWBed.isBlockConnectsToBlock(var19.part1_posX, var19.part1_posY, var19.part1_posZ, var24.part1_posX, var24.part1_posY, var24.part1_posZ)) {
                        continue;
                     }

                     var16.add(new BWBed(var19.part1_posX, var19.part1_posY, var19.part1_posZ, var24.part1_posX, var24.part1_posY, var24.part1_posZ));
                     ex.remove(var19);
                     ex.remove(var24);
                     var21 = true;
                  }

                  if(var21) {
                     continue label76;
                  }
                  break;
               }
            }
         }

         WorldClient var18 = Minecraft.func_71410_x().field_71441_e;
         Iterator var20 = var16.iterator();

         while(var20.hasNext()) {
            BWBed var22 = (BWBed)var20.next();
            var22.scanDefence(var18);
         }

         return var16;
      } catch (Exception var15) {
         var15.printStackTrace();
         return null;
      }
   }

}
