// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.TimerTask;
import java.util.Timer;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.Minecraft;

public class LoginHandler
{
    Minecraft mc;
    
    public LoginHandler() {
        this.mc = Minecraft.func_71410_x();
    }
    
    @SubscribeEvent
    public void onJoinServer(final FMLNetworkEvent.ClientConnectedToServerEvent e) {
        Main.baseProps.readProps();
        Main.baseProps.readMessages();
        Main.updateAllBooleans();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                final StringBuilder sb = new StringBuilder();
                final MyChatListener chatListener = Main.chatListener;
                ChatSender.addText(sb.append(MyChatListener.PREFIX_BEDWARSBRO).append("&f\u0412\u0441\u0435 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u043c\u043e\u0434\u0430 - &c/bwbro").toString());
                final StringBuilder sb2 = new StringBuilder();
                final MyChatListener chatListener2 = Main.chatListener;
                ChatSender.addText(sb2.append(MyChatListener.PREFIX_BEDWARSBRO).append("&f\u0410\u0432\u0442\u043e\u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f - &e/meow").toString());
                String author = Main.getPropModAuthor();
                if (author == null || author.length() <= 1) {
                    author = "DimChig";
                }
                final StringBuilder sb3 = new StringBuilder();
                final MyChatListener chatListener3 = Main.chatListener;
                ChatSender.addText(sb3.append(MyChatListener.PREFIX_BEDWARSBRO).append("&f\u0410\u0432\u0442\u043e\u0440 \u043c\u043e\u0434\u0430 \u0438\u0433\u0440\u0430\u0435\u0442 \u043f\u043e\u0434 \u043d\u0438\u043a\u043e\u043c &a").append(author).toString());
                final StringBuilder sb4 = new StringBuilder();
                final MyChatListener chatListener4 = Main.chatListener;
                ChatSender.addText(sb4.append(MyChatListener.PREFIX_BEDWARSBRO).append("&f\u0414\u0438\u0441\u043a\u043e\u0440\u0434 \u0441\u0435\u0440\u0432\u0435\u0440 \u043c\u043e\u0434\u0430 - &9/bwdiscord").toString());
                Main.updateAllBooleans();
            }
        }, 3000L);
    }
}
