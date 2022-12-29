// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import java.io.IOException;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import java.util.List;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public class ChatSender
{
    public static String parseText(final String text) {
        final MyChatListener chatListener = Main.chatListener;
        final String nick = MyChatListener.nickChanger;
        if (nick.length() > 0) {
            return text.replaceAll(Minecraft.func_71410_x().field_71439_g.func_70005_c_(), nick);
        }
        return text;
    }
    
    public static void addText(String text) {
        if (Minecraft.func_71410_x() == null) {
            return;
        }
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        text = parseText(text);
        Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text)));
    }
    
    public static void addText(final boolean b) {
        addText((b ? "&a" : "&c") + b);
    }
    
    public static void addText(final int x) {
        addText("" + x);
    }
    
    public static void addText(final double x) {
        addText("" + x);
    }
    
    public static void addText(final float x) {
        addText("" + x);
    }
    
    public static <T> void addText(final T[] x) {
        addText("" + x);
    }
    
    public static <T> void addText(final List<T> x) {
        addText("" + x);
    }
    
    public static void sendText(final String text) {
        if (Minecraft.func_71410_x() == null) {
            return;
        }
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        Minecraft.func_71410_x().field_71439_g.func_71165_d(text.replace("§", "&"));
    }
    
    public static void addHoverText(String text, String hover_text) {
        text = parseText(text);
        hover_text = parseText(hover_text);
        final IChatComponent mainComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
        final IChatComponent hoverComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hover_text));
        final HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent);
        final ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bedwarsChatModLookAtPlayer");
        mainComponent.func_150256_b().func_150209_a(hover);
        mainComponent.func_150256_b().func_150241_a(click);
        Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
    }
    
    public static void addHoverFileText(String text, String hover_text, final String filepath) throws IOException {
        text = parseText(text);
        hover_text = parseText(hover_text);
        final IChatComponent mainComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
        final IChatComponent hoverComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hover_text));
        final HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent);
        final ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_FILE, filepath);
        mainComponent.func_150256_b().func_150209_a(hover);
        mainComponent.func_150256_b().func_150241_a(click);
        Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
    }
    
    public static void addClickText(String text, final String commandText) {
        text = parseText(text);
        final IChatComponent mainComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
        final ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandText);
        mainComponent.func_150256_b().func_150241_a(click);
        Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
    }
    
    public static void addClickAndHoverText(String text, String hoverText, final String commandText) {
        text = parseText(text);
        hoverText = parseText(hoverText);
        final IChatComponent mainComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
        final IChatComponent hoverComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hoverText));
        final HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent);
        final ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandText);
        mainComponent.func_150256_b().func_150209_a(hover);
        mainComponent.func_150256_b().func_150241_a(click);
        Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
    }
    
    public static void addLinkAndHoverText(String text, String hoverText, final String url) {
        text = parseText(text);
        hoverText = parseText(hoverText);
        final IChatComponent mainComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
        final IChatComponent hoverComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hoverText));
        final HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent);
        final ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
        mainComponent.func_150256_b().func_150209_a(hover);
        mainComponent.func_150256_b().func_150241_a(click);
        Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
    }
    
    public static void addClickSuggestAndHoverText(String text, String hoverText, final String commandText) {
        text = parseText(text);
        hoverText = parseText(hoverText);
        final IChatComponent mainComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
        final IChatComponent hoverComponent = (IChatComponent)new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hoverText));
        final HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent);
        final ClickEvent click = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commandText);
        mainComponent.func_150256_b().func_150209_a(hover);
        mainComponent.func_150256_b().func_150241_a(click);
        Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
    }
}
