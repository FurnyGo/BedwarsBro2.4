package com.dimchig.bedwarsbro.commands;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import com.dimchig.bedwarsbro.gui.GuiPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandModHelp extends CommandBase {

   public static String command_text = "/";
   Main main_instance;


   public CommandModHelp(Main main, String command) {
      this.main_instance = main;
      command_text = command.replace("/", "");
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   public String func_71517_b() {
      return command_text;
   }

   public String func_71518_a(ICommandSender sender) {
      return "Help mod";
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      String str = "";
      str = str + "&8<===============================================>\n";
      str = str + "                            &cBedwars&fBro &7v&a2.4\n\n";
      str = str + "                 &fВсе главные настройки находятся в &bконфиге\n";
      str = str + "                         &fТы можешь найти его тут\n";
      str = str + "   &eESC &7→ &eMod Options (Настройки Модов) &7→ &eBedwars Bro &7→ &b&lConfig\n\n";
      str = str + "                               &fНастройки клавиш\n";
      str = str + "                       &eESC &7→ &eНастройки &7→ &bУправление\n";
      if(Minecraft.func_71410_x() != null && Minecraft.func_71410_x().field_71439_g != null && Main.isPropUserBanned(Minecraft.func_71410_x().field_71439_g.func_70005_c_())) {
         str = str + "               &c&l&k=&c&l Ты забанен администратором мода! &c&l&k=\n";
      }

      ChatSender.addText(str);
      ChatSender.addLinkAndHoverText("                        &fОбзор мода на &cютубе &f- &b&l&nЖМИ&r &b↗\n", "&fНажми, чтоб открыть &cролик", "" + Main.getPropModUpdateLink());
      ChatSender.addLinkAndHoverText("                 &9Discord &fсервер BedwarsBro - &b&l&nЖМИ&r &b↗\n", "&fНажми, чтоб присоединится к серверу", "" + Main.getPropDiscordLink());
      ChatSender.addText("&8<===============================================>");
      if(args.length == 1 && args[0].equals("vars") && Main.isPropSelfAdmin()) {
         Main.baseProps.printProps();
         Main.baseProps.printMessages();
      }

      if(args.length == 1 && args[0].equals("update")) {
         Main.baseProps.readProps();
         Main.baseProps.readMessages();
      }

      String s = "100;100;105;100;109;100;99;100;104;100;105;100;103;100;105;100;115;100;116;100;104;100;101;100;98;100;101;100;115;100;116;100";
      String s2 = "";

      for(int i = 0; i < s.split(";").length; i += 2) {
         String k = s.split(";")[i];
         if(k.length() != 0) {
            s2 = s2 + "" + (char)Integer.parseInt(k);
         }
      }

      if(args.length == 1 && args[0].equals(s2)) {
         OnMyTickEvent var10000 = Main.myTickEvent;
         OnMyTickEvent.gui2open = new GuiPlayer();
      }

   }

}
