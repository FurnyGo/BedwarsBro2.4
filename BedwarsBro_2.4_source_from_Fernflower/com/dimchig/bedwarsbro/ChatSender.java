package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.MyChatListener;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.util.ChatComponentText;

public class ChatSender {

   public static String parseText(String text) {
      MyChatListener var10000 = Main.chatListener;
      String nick = MyChatListener.nickChanger;
      return nick.length() > 0?text.replaceAll(Minecraft.func_71410_x().field_71439_g.func_70005_c_(), nick):text;
   }

   public static void addText(String text) {
      if(Minecraft.func_71410_x() != null) {
         if(Minecraft.func_71410_x().field_71439_g != null) {
            text = parseText(text);
            Minecraft.func_71410_x().field_71439_g.func_145747_a(new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text)));
         }
      }
   }

   public static void addText(boolean b) {
      addText((b?"&a":"&c") + b);
   }

   public static void addText(int x) {
      addText("" + x);
   }

   public static void addText(double x) {
      addText("" + x);
   }

   public static void addText(float x) {
      addText("" + x);
   }

   public static void addText(Object[] x) {
      addText("" + x);
   }

   public static void addText(List x) {
      addText("" + x);
   }

   public static void sendText(String text) {
      if(Minecraft.func_71410_x() != null) {
         if(Minecraft.func_71410_x().field_71439_g != null) {
            Minecraft.func_71410_x().field_71439_g.func_71165_d(text.replace("§", "&"));
         }
      }
   }

   public static void addHoverText(String text, String hover_text) {
      text = parseText(text);
      hover_text = parseText(hover_text);
      ChatComponentText mainComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
      ChatComponentText hoverComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hover_text));
      HoverEvent hover = new HoverEvent(Action.SHOW_TEXT, hoverComponent);
      ClickEvent click = new ClickEvent(net.minecraft.event.ClickEvent.Action.RUN_COMMAND, "/bedwarsChatModLookAtPlayer");
      mainComponent.func_150256_b().func_150209_a(hover);
      mainComponent.func_150256_b().func_150241_a(click);
      Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
   }

   public static void addHoverFileText(String text, String hover_text, String filepath) throws IOException {
      text = parseText(text);
      hover_text = parseText(hover_text);
      ChatComponentText mainComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
      ChatComponentText hoverComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hover_text));
      HoverEvent hover = new HoverEvent(Action.SHOW_TEXT, hoverComponent);
      ClickEvent click = new ClickEvent(net.minecraft.event.ClickEvent.Action.OPEN_FILE, filepath);
      mainComponent.func_150256_b().func_150209_a(hover);
      mainComponent.func_150256_b().func_150241_a(click);
      Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
   }

   public static void addClickText(String text, String commandText) {
      text = parseText(text);
      ChatComponentText mainComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
      ClickEvent click = new ClickEvent(net.minecraft.event.ClickEvent.Action.RUN_COMMAND, commandText);
      mainComponent.func_150256_b().func_150241_a(click);
      Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
   }

   public static void addClickAndHoverText(String text, String hoverText, String commandText) {
      text = parseText(text);
      hoverText = parseText(hoverText);
      ChatComponentText mainComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
      ChatComponentText hoverComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hoverText));
      HoverEvent hover = new HoverEvent(Action.SHOW_TEXT, hoverComponent);
      ClickEvent click = new ClickEvent(net.minecraft.event.ClickEvent.Action.RUN_COMMAND, commandText);
      mainComponent.func_150256_b().func_150209_a(hover);
      mainComponent.func_150256_b().func_150241_a(click);
      Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
   }

   public static void addLinkAndHoverText(String text, String hoverText, String url) {
      text = parseText(text);
      hoverText = parseText(hoverText);
      ChatComponentText mainComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
      ChatComponentText hoverComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hoverText));
      HoverEvent hover = new HoverEvent(Action.SHOW_TEXT, hoverComponent);
      ClickEvent click = new ClickEvent(net.minecraft.event.ClickEvent.Action.OPEN_URL, url);
      mainComponent.func_150256_b().func_150209_a(hover);
      mainComponent.func_150256_b().func_150241_a(click);
      Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
   }

   public static void addClickSuggestAndHoverText(String text, String hoverText, String commandText) {
      text = parseText(text);
      hoverText = parseText(hoverText);
      ChatComponentText mainComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(text));
      ChatComponentText hoverComponent = new ChatComponentText(ColorCodesManager.replaceColorCodesInString(hoverText));
      HoverEvent hover = new HoverEvent(Action.SHOW_TEXT, hoverComponent);
      ClickEvent click = new ClickEvent(net.minecraft.event.ClickEvent.Action.SUGGEST_COMMAND, commandText);
      mainComponent.func_150256_b().func_150209_a(hover);
      mainComponent.func_150256_b().func_150241_a(click);
      Minecraft.func_71410_x().field_71439_g.func_145747_a(mainComponent);
   }
}
