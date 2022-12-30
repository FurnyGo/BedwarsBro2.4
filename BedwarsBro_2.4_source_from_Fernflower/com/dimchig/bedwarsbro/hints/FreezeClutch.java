package com.dimchig.bedwarsbro.hints;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.Date;
import net.minecraft.client.Minecraft;

public class FreezeClutch {

   private boolean isActive = false;
   private long time_last_freeze = 0L;
   private long time_next_freeze = 0L;
   private int total_steps = 0;
   private int FREEZE_STEPS = 5;
   private float FREEZE_TIME = 5000.0F;
   private int UNFREEZE_TIME = 10;


   public FreezeClutch() {
      this.isActive = false;
      this.total_steps = 0;
   }

   public void startFreeze() {
      this.isActive = true;
      this.total_steps = 0;
      this.time_next_freeze = (new Date()).getTime();
   }

   public void handle() {
      if(this.isActive) {
         long t = (new Date()).getTime();
         if(t > this.time_next_freeze) {
            this.time_last_freeze = t;

            do {
               t = (new Date()).getTime();
            } while((float)(t - this.time_last_freeze) <= this.FREEZE_TIME / (float)this.FREEZE_STEPS);

            ++this.total_steps;
            ChatSender.addText("&bFreeze &8▸ &fИгра фризится &e" + this.total_steps + "&7/&f" + this.FREEZE_STEPS);
            if(this.total_steps < this.FREEZE_STEPS && !Minecraft.func_71410_x().field_71439_g.field_70122_E) {
               if(this.total_steps < this.FREEZE_STEPS) {
                  this.time_next_freeze = t + (long)this.UNFREEZE_TIME;
               }
            } else {
               this.isActive = false;
               MyChatListener var10000 = Main.chatListener;
               MyChatListener.playSound("note.hat");
            }

         }
      }
   }
}
