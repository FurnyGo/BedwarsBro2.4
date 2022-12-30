package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class LoginHandler {

   Minecraft mc = Minecraft.func_71410_x();


   @SubscribeEvent
   public void onJoinServer(ClientConnectedToServerEvent e) {
      Main.baseProps.readProps();
      Main.baseProps.readMessages();
      Main.updateAllBooleans();
      (new Timer()).schedule(new TimerTask() {
         public void run() {
            StringBuilder var10000 = new StringBuilder();
            MyChatListener var10001 = Main.chatListener;
            ChatSender.addText(var10000.append(MyChatListener.PREFIX_BEDWARSBRO).append("&fВсе настройки мода - &c/bwbro").toString());
            var10000 = new StringBuilder();
            var10001 = Main.chatListener;
            ChatSender.addText(var10000.append(MyChatListener.PREFIX_BEDWARSBRO).append("&fАвтосообщения - &e/meow").toString());
            String author = Main.getPropModAuthor();
            if(author == null || author.length() <= 1) {
               author = "DimChig";
            }

            var10000 = new StringBuilder();
            var10001 = Main.chatListener;
            ChatSender.addText(var10000.append(MyChatListener.PREFIX_BEDWARSBRO).append("&fАвтор мода играет под ником &a").append(author).toString());
            var10000 = new StringBuilder();
            var10001 = Main.chatListener;
            ChatSender.addText(var10000.append(MyChatListener.PREFIX_BEDWARSBRO).append("&fДискорд сервер мода - &9/bwdiscord").toString());
            Main.updateAllBooleans();
         }
      }, 3000L);
   }
}
