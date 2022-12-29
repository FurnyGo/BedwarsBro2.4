// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;

public class RotateBind
{
    static Minecraft mc;
    public boolean isActive;
    public int degrees;
    public float speed;
    public double startAngle;
    public int iteration;
    
    public RotateBind() {
        this.isActive = false;
        this.degrees = 180;
        this.speed = 1.0f;
        this.startAngle = 0.0;
        this.iteration = 0;
        RotateBind.mc = Minecraft.func_71410_x();
    }
    
    public void updateBooleans() {
        this.degrees = Main.getConfigInt(Main.CONFIG_MSG.ROTATE_BIND_DEGREES);
        this.speed = (float)Main.getConfigDouble(Main.CONFIG_MSG.ROTATE_BIND_SPEED) * 10.0f;
    }
    
    public void rotate() {
        if (!this.isActive) {
            return;
        }
        if (this.iteration < this.speed) {
            BridgeAutoAngle.setPlayerPitchAndYaw((Entity)RotateBind.mc.field_71439_g, RotateBind.mc.field_71439_g.field_70177_z + this.degrees / this.speed, RotateBind.mc.field_71439_g.field_70125_A);
            ++this.iteration;
        }
        else {
            BridgeAutoAngle.setPlayerPitchAndYaw((Entity)RotateBind.mc.field_71439_g, (float)(this.startAngle + this.degrees), RotateBind.mc.field_71439_g.field_70125_A);
            this.isActive = false;
        }
    }
    
    public void startRotate() {
        final float yaw = RotateBind.mc.field_71439_g.field_70177_z;
        if (this.speed == 0.0f) {
            BridgeAutoAngle.setPlayerPitchAndYaw((Entity)RotateBind.mc.field_71439_g, yaw + this.degrees, RotateBind.mc.field_71439_g.field_70125_A);
        }
        else {
            this.startAngle = yaw;
            this.iteration = 0;
            this.isActive = true;
        }
    }
}
