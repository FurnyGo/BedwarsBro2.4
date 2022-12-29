// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;

public class LobbyFly
{
    static Minecraft mc;
    public static boolean isActive;
    public static LightningLocator.MyLightning last_lightning;
    public static float speed;
    
    public LobbyFly() {
        LobbyFly.mc = Minecraft.func_71410_x();
        LobbyFly.isActive = false;
        LobbyFly.speed = 1.0f;
    }
    
    @SubscribeEvent
    public void playerTick(final TickEvent.ClientTickEvent event) {
        if (LobbyFly.isActive) {
            if (LobbyFly.speed > 1.0f) {
                LobbyFly.speed = (float)Math.floor(LobbyFly.speed);
            }
            final double yaw = LobbyFly.mc.field_71439_g.field_70177_z;
            double pitch = LobbyFly.mc.field_71439_g.field_70125_A - 15.0;
            if (LobbyFly.mc.field_71439_g.field_70125_A == -90.0f) {
                pitch = LobbyFly.mc.field_71439_g.field_70125_A;
            }
            final double motionX = -MathHelper.func_76126_a((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
            final double motionY = -MathHelper.func_76126_a((float)(pitch * 0.01745329238474369));
            final double motionZ = MathHelper.func_76134_b((float)(yaw * 0.01745329238474369)) * MathHelper.func_76134_b((float)(pitch * 0.01745329238474369));
            LobbyFly.mc.field_71439_g.func_70016_h(motionX * LobbyFly.speed, motionY * LobbyFly.speed, motionZ * LobbyFly.speed);
        }
        else {
            LobbyFly.speed = 1.0f;
        }
    }
    
    static {
        LobbyFly.isActive = false;
        LobbyFly.last_lightning = null;
        LobbyFly.speed = 1.0f;
    }
}
