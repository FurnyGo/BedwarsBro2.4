package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class AutoSprint {

   private Minecraft mc = Minecraft.func_71410_x();
   private int sprintKeyBind;
   private boolean isAutoSprintActive = false;


   public AutoSprint() {
      this.updateBooleans();
   }

   public void updateBooleans() {
      this.isAutoSprintActive = HintsValidator.isAutoSprintActive();
      this.setSprint(this.isAutoSprintActive);
   }

   @SubscribeEvent
   public void onKeyInput(KeyInputEvent event) {
      if(this.isAutoSprintActive) {
         this.setSprint(true);
      }

   }

   public void setSprint(boolean state) {
      this.sprintKeyBind = Minecraft.func_71410_x().field_71474_y.field_151444_V.func_151463_i();
      KeyBinding.func_74510_a(this.sprintKeyBind, state);
   }
}
