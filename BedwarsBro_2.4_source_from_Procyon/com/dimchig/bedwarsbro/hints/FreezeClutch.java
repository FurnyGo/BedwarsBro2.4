// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.ChatSender;
import java.util.Date;

public class FreezeClutch
{
    private boolean isActive;
    private long time_last_freeze;
    private long time_next_freeze;
    private int total_steps;
    private int FREEZE_STEPS;
    private float FREEZE_TIME;
    private int UNFREEZE_TIME;
    
    public FreezeClutch() {
        this.isActive = false;
        this.time_last_freeze = 0L;
        this.time_next_freeze = 0L;
        this.total_steps = 0;
        this.FREEZE_STEPS = 5;
        this.FREEZE_TIME = 5000.0f;
        this.UNFREEZE_TIME = 10;
        this.isActive = false;
        this.total_steps = 0;
    }
    
    public void startFreeze() {
        this.isActive = true;
        this.total_steps = 0;
        this.time_next_freeze = new Date().getTime();
    }
    
    public void handle() {
        if (!this.isActive) {
            return;
        }
        long t = new Date().getTime();
        if (t > this.time_next_freeze) {
            this.time_last_freeze = t;
            do {
                t = new Date().getTime();
            } while (t - this.time_last_freeze <= this.FREEZE_TIME / this.FREEZE_STEPS);
            ++this.total_steps;
            ChatSender.addText("&bFreeze &8\u25b8 &f\u0418\u0433\u0440\u0430 \u0444\u0440\u0438\u0437\u0438\u0442\u0441\u044f &e" + this.total_steps + "&7/&f" + this.FREEZE_STEPS);
            if (this.total_steps >= this.FREEZE_STEPS || Minecraft.func_71410_x().field_71439_g.field_70122_E) {
                this.isActive = false;
                final MyChatListener chatListener = Main.chatListener;
                MyChatListener.playSound("note.hat");
            }
            else if (this.total_steps < this.FREEZE_STEPS) {
                this.time_next_freeze = t + this.UNFREEZE_TIME;
            }
        }
    }
}
