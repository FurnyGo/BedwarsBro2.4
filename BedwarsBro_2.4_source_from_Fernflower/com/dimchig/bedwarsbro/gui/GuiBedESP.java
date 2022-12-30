package com.dimchig.bedwarsbro.gui;

import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.gui.GuiMinimap;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GuiBedESP {

   static Minecraft mc;
   public static boolean STATE = false;


   public GuiBedESP() {
      mc = Minecraft.func_71410_x();
      this.updateBooleans();
   }

   public void updateBooleans() {
      STATE = HintsValidator.isBedESPActive();
   }

   @SubscribeEvent
   public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
      if(STATE) {
         ArrayList beds = new ArrayList();
         GuiMinimap var10000 = Main.minimap;
         if(GuiMinimap.bedsFound != null) {
            var10000 = Main.minimap;
            if(GuiMinimap.bedsFound.size() != 0) {
               GuiMinimap var10001 = Main.minimap;
               beds.addAll(GuiMinimap.bedsFound);
               float partialTicks = event.partialTicks;
               EntityPlayerSP player = mc.field_71439_g;
               WorldClient world = mc.field_71441_e;
               if(player != null && world != null) {
                  double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * (double)partialTicks;
                  double d1 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * (double)partialTicks;
                  double d2 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * (double)partialTicks;
                  Vec3 pos = new Vec3(d0, d1, d2);
                  GL11.glPushMatrix();
                  GL11.glPushAttrib(8192);
                  GL11.glTranslated(-pos.field_72450_a, -pos.field_72448_b, -pos.field_72449_c);
                  GL11.glDisable(2896);
                  GL11.glDisable(3553);
                  GL11.glDisable(2929);
                  GlStateManager.func_179084_k();
                  GL11.glLineWidth(1.0F);
                  if(beds != null && beds.size() > 0) {
                     Iterator var13 = beds.iterator();

                     while(var13.hasNext()) {
                        GuiMinimap.MyBed b = (GuiMinimap.MyBed)var13.next();
                        double x = (double)b.pos.func_177958_n();
                        double y = (double)b.pos.func_177956_o();
                        double z = (double)b.pos.func_177952_p();
                        double x2 = (double)b.pos_feet.func_177958_n();
                        double y2 = (double)b.pos_feet.func_177956_o();
                        double z2 = (double)b.pos_feet.func_177952_p();
                        GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
                        if(b.isPlayersBed) {
                           GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
                        }

                        Main.playerFocus.drawBox(x, y, z, x2 + 1.0D, y2 + 0.56D, z2 + 1.0D);
                        ArrayList obby = b.obsidianPoses;
                        if(obby.size() > 0 && mc.field_71439_g.func_70011_f(x, y, z) > 10.0D) {
                           GL11.glColor4f(0.0F, 1.0F, 1.0F, 1.0F);
                           Iterator var28 = obby.iterator();

                           while(var28.hasNext()) {
                              BlockPos bp = (BlockPos)var28.next();
                              double px = (double)bp.func_177958_n();
                              double py = (double)bp.func_177956_o();
                              double pz = (double)bp.func_177952_p();
                              double c = 0.02D;
                              Main.playerFocus.drawBox(px + c, py + c, pz + c, px + 1.0D - c, py + 1.0D - c, pz + 1.0D - c);
                           }
                        }
                     }
                  }

                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  GL11.glPopAttrib();
                  GL11.glPopMatrix();
                  return;
               }

               return;
            }
         }

      }
   }

}
