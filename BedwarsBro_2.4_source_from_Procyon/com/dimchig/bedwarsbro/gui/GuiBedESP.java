// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.Vec3;
import java.util.Collection;
import com.dimchig.bedwarsbro.Main;
import java.util.ArrayList;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.client.Minecraft;

public class GuiBedESP
{
    static Minecraft mc;
    public static boolean STATE;
    
    public GuiBedESP() {
        GuiBedESP.mc = Minecraft.func_71410_x();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        GuiBedESP.STATE = HintsValidator.isBedESPActive();
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        if (!GuiBedESP.STATE) {
            return;
        }
        final ArrayList<GuiMinimap.MyBed> beds = new ArrayList<GuiMinimap.MyBed>();
        final GuiMinimap minimap = Main.minimap;
        if (GuiMinimap.bedsFound != null) {
            final GuiMinimap minimap2 = Main.minimap;
            if (GuiMinimap.bedsFound.size() != 0) {
                final ArrayList<GuiMinimap.MyBed> list = beds;
                final GuiMinimap minimap3 = Main.minimap;
                list.addAll(GuiMinimap.bedsFound);
                final float partialTicks = event.partialTicks;
                final EntityPlayerSP player = GuiBedESP.mc.field_71439_g;
                final World world = (World)GuiBedESP.mc.field_71441_e;
                if (player == null || world == null) {
                    return;
                }
                final double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
                final double d2 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
                final double d3 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
                final Vec3 pos = new Vec3(d0, d2, d3);
                GL11.glPushMatrix();
                GL11.glPushAttrib(8192);
                GL11.glTranslated(-pos.field_72450_a, -pos.field_72448_b, -pos.field_72449_c);
                GL11.glDisable(2896);
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GlStateManager.func_179084_k();
                GL11.glLineWidth(1.0f);
                if (beds != null && beds.size() > 0) {
                    for (final GuiMinimap.MyBed b : beds) {
                        final double x = b.pos.func_177958_n();
                        final double y = b.pos.func_177956_o();
                        final double z = b.pos.func_177952_p();
                        final double x2 = b.pos_feet.func_177958_n();
                        final double y2 = b.pos_feet.func_177956_o();
                        final double z2 = b.pos_feet.func_177952_p();
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                        if (b.isPlayersBed) {
                            GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                        }
                        Main.playerFocus.drawBox(x, y, z, x2 + 1.0, y2 + 0.56, z2 + 1.0);
                        final ArrayList<BlockPos> obby = b.obsidianPoses;
                        if (obby.size() > 0 && GuiBedESP.mc.field_71439_g.func_70011_f(x, y, z) > 10.0) {
                            GL11.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
                            for (final BlockPos bp : obby) {
                                final double px = bp.func_177958_n();
                                final double py = bp.func_177956_o();
                                final double pz = bp.func_177952_p();
                                final double c = 0.02;
                                Main.playerFocus.drawBox(px + c, py + c, pz + c, px + 1.0 - c, py + 1.0 - c, pz + 1.0 - c);
                            }
                        }
                    }
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
    }
    
    static {
        GuiBedESP.STATE = false;
    }
}
