// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.client.Minecraft;

public class AutoSprint
{
    private Minecraft mc;
    private int sprintKeyBind;
    private boolean isAutoSprintActive;
    
    public AutoSprint() {
        this.isAutoSprintActive = false;
        this.mc = Minecraft.func_71410_x();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        this.setSprint(this.isAutoSprintActive = HintsValidator.isAutoSprintActive());
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (this.isAutoSprintActive) {
            this.setSprint(true);
        }
    }
    
    public void setSprint(final boolean state) {
        KeyBinding.func_74510_a(this.sprintKeyBind = Minecraft.func_71410_x().field_71474_y.field_151444_V.func_151463_i(), state);
    }
}
