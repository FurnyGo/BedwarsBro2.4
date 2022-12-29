// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.Main;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.Minecraft;

public class LightningLocator
{
    static Minecraft mc;
    public static boolean isActive;
    public static MyLightning last_lightning;
    
    public LightningLocator() {
        LightningLocator.mc = Minecraft.func_71410_x();
        LightningLocator.last_lightning = null;
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        if (LightningLocator.mc.field_71441_e == null) {
            return;
        }
        final List<Entity> weather_effects = (List<Entity>)LightningLocator.mc.field_71441_e.field_73007_j;
        if (weather_effects != null && weather_effects.size() > 0) {
            EntityLightningBolt last_lig = null;
            double min_ticks = 9999.0;
            for (final Entity en : weather_effects) {
                if (!(en instanceof EntityLightningBolt)) {
                    continue;
                }
                final EntityLightningBolt lig = (EntityLightningBolt)en;
                final int ticks = lig.field_70173_aa;
                if (ticks >= min_ticks) {
                    continue;
                }
                min_ticks = ticks;
                last_lig = lig;
            }
            if (last_lig != null) {
                LightningLocator.last_lightning = new MyLightning(last_lig, new Date().getTime());
            }
        }
        if (LightningLocator.isActive) {
            this.drawLastLightning(event.partialTicks);
        }
    }
    
    public void drawLastLightning(final float partialTicks) {
        if (LightningLocator.last_lightning == null) {
            return;
        }
        double size = 1.0;
        double height = 200.0;
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        final double x = LightningLocator.mc.field_71439_g.field_70169_q + (LightningLocator.mc.field_71439_g.field_70165_t - LightningLocator.mc.field_71439_g.field_70169_q) * partialTicks;
        final double y = LightningLocator.mc.field_71439_g.field_70167_r + (LightningLocator.mc.field_71439_g.field_70163_u - LightningLocator.mc.field_71439_g.field_70167_r) * partialTicks;
        final double z = LightningLocator.mc.field_71439_g.field_70166_s + (LightningLocator.mc.field_71439_g.field_70161_v - LightningLocator.mc.field_71439_g.field_70166_s) * partialTicks;
        final Vec3 playerPos = new Vec3(x, y, z);
        GL11.glTranslated(-playerPos.field_72450_a, -playerPos.field_72448_b, -playerPos.field_72449_c);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2929);
        GlStateManager.func_179084_k();
        GL11.glLineWidth(1.0f);
        final double posX = LightningLocator.last_lightning.lightning.field_70165_t + 0.5;
        final double posY = LightningLocator.last_lightning.lightning.field_70163_u - 2.0;
        final double posZ = LightningLocator.last_lightning.lightning.field_70161_v + 0.5;
        height = 255.0 - posY;
        Color color = new Color(0.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 1.0f);
        Main.playerFocus.drawBoxAroundBlock(posX, posY, posZ, -size, 0.0, -size, size, height, size);
        color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 1.0f);
        size += 0.01;
        Main.playerFocus.drawBox(posX - size, posY, posZ - size, posX + size - 1.0, posY + height - 1.0, posZ + size - 1.0);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    static {
        LightningLocator.isActive = false;
        LightningLocator.last_lightning = null;
    }
    
    public class MyLightning
    {
        public EntityLightningBolt lightning;
        public long time_created;
        
        public MyLightning(final EntityLightningBolt lightning, final long time_created) {
            this.lightning = lightning;
            this.time_created = time_created;
        }
    }
}
