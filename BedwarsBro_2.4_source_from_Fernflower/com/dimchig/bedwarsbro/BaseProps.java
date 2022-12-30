package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.Main;
import com.dimchig.bedwarsbro.serializer.MySerializer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BaseProps {

   private String mod_last_version = null;
   private String mod_update_link = null;
   private String discord_link = null;
   private String mod_autor = null;
   private String mod_autor_prefix = "&c&l[&6&lС&e&lо&a&lз&b&lд&d&lа&c&lт&6&lе&e&lл&a&lь&c&l]&r";
   private ArrayList banned_users = new ArrayList();
   private ArrayList admin_users = new ArrayList();
   public ArrayList my_messages = new ArrayList();


   public String getModLastVersion() {
      return this.mod_last_version;
   }

   public String getModUpdateLink() {
      return this.mod_update_link;
   }

   public String getDiscordLink() {
      return this.discord_link;
   }

   public String getModAuthor() {
      return this.mod_autor;
   }

   public String getModAuthorPrefix() {
      return this.mod_autor_prefix;
   }

   public ArrayList getModBannedUsers() {
      return this.banned_users;
   }

   public boolean isUserBanned(String player_name) {
      Iterator var2 = this.banned_users.iterator();

      String n;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         n = (String)var2.next();
      } while(!n.equals(player_name));

      return true;
   }

   public ArrayList getModAdminUsers() {
      return this.admin_users;
   }

   public boolean isUserAdmin(String player_name) {
      Iterator var2 = this.admin_users.iterator();

      String n;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         n = (String)var2.next();
      } while(!n.equals(player_name));

      return true;
   }

   public void printProps() {
      ChatSender.addText("&5=====&dBASE PROPS&5=====");
      ChatSender.addText(" &5mod_last_version &5▸ &d" + this.mod_last_version);
      ChatSender.addText(" &5yt &5▸ &d" + this.mod_update_link);
      ChatSender.addText(" &5discord_link &5▸ &d" + this.discord_link);
      ChatSender.addText(" &5autor &5▸ &d" + this.mod_autor);
      ChatSender.addText(" &5autor_prefix &5▸ &d" + this.mod_autor_prefix);
      ChatSender.addText(" &5admin users:");
      Iterator var1 = this.admin_users.iterator();

      String b;
      while(var1.hasNext()) {
         b = (String)var1.next();
         ChatSender.addText(" &5• &d" + b);
      }

      ChatSender.addText(" &5banned users:");
      var1 = this.banned_users.iterator();

      while(var1.hasNext()) {
         b = (String)var1.next();
         ChatSender.addText(" &5• &d" + b);
      }

      ChatSender.addText("&5======================");
   }

   public void printMessages() {
      ChatSender.addText("&2=====&aMESSAGES&2=====");
      Iterator var1 = this.my_messages.iterator();

      while(var1.hasNext()) {
         BaseProps.MyMessage m = (BaseProps.MyMessage)var1.next();
         ChatSender.addText(" &a" + m.trigger);
         Iterator var3 = m.messages.iterator();

         while(var3.hasNext()) {
            String a = (String)var3.next();
            ChatSender.addText("  &2• " + a);
         }
      }

      ChatSender.addText("&2==================");
   }

   public void readMessages() {
      Thread t1 = new Thread(new Runnable() {
         public void run() {
            try {
               MySerializer var10000 = Main.mySerializer;
               String e = MySerializer.readMessages();
               if(e == null) {
                  return;
               }

               String separator_thirdly = ";=BRO2=;";
               if(e.length() < 10) {
                  return;
               }

               String[] split = e.split(MySerializer.separator);
               if(split.length == 0) {
                  return;
               }

               if(BaseProps.this.my_messages == null) {
                  BaseProps.this.my_messages = new ArrayList();
               }

               BaseProps.this.my_messages.clear();
               String[] var4 = split;
               int var5 = split.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  String line = var4[var6];
                  if(line.length() >= 3) {
                     String[] split2 = line.split(MySerializer.separator_secondary);
                     String trigger = split2[0].trim();
                     ArrayList messages = new ArrayList();
                     String[] msg = split2[1].trim().split(separator_thirdly);
                     int var12 = msg.length;

                     for(int var13 = 0; var13 < var12; ++var13) {
                        String s2 = msg[var13];
                        s2 = s2.trim();
                        if(s2.length() >= 1) {
                           messages.add(s2);
                        }
                     }

                     BaseProps.MyMessage var16 = BaseProps.this.new MyMessage(trigger, messages);
                     BaseProps.this.my_messages.add(var16);
                  }
               }
            } catch (Exception var15) {
               var15.printStackTrace();
            }

         }
      });
      t1.start();
   }

   public void readProps() {
      Thread t1 = new Thread(new Runnable() {
         public void run() {
            try {
               MySerializer var10000 = Main.mySerializer;
               String e = MySerializer.readProps();
               if(e != null) {
                  String[] split = e.split(MySerializer.separator);
                  if(split.length >= 7) {
                     BaseProps.this.mod_last_version = split[0].trim();
                     BaseProps.this.mod_update_link = split[1].replace("xyzqwerty", ".").trim();
                     BaseProps.this.discord_link = split[2].replace("xyzqwerty", ".").trim();
                     BaseProps.this.mod_autor = split[3].trim();
                     BaseProps.this.mod_autor_prefix = split[4].replace("x.y.z", "&").trim();
                     BaseProps.this.admin_users.clear();
                     String var12 = split[5].trim();
                     MySerializer var10001 = Main.mySerializer;
                     String[] mod_admin_users = var12.split(MySerializer.separator_secondary);
                     String[] mod_banned_users = mod_admin_users;
                     int var5 = mod_admin_users.length;

                     int var6;
                     for(var6 = 0; var6 < var5; ++var6) {
                        String l = mod_banned_users[var6];
                        if(l.length() > 1) {
                           BaseProps.this.admin_users.add(l.trim());
                        }
                     }

                     BaseProps.this.banned_users.clear();
                     var12 = split[6].trim();
                     var10001 = Main.mySerializer;
                     mod_banned_users = var12.split(MySerializer.separator_secondary);
                     String[] var10 = mod_banned_users;
                     var6 = mod_banned_users.length;

                     for(int var11 = 0; var11 < var6; ++var11) {
                        String l1 = var10[var11];
                        if(l1.length() > 1) {
                           BaseProps.this.banned_users.add(l1.trim());
                        }
                     }

                     Main.updateAllBooleans();
                  }
               }
            } catch (Exception var9) {
               ;
            }
         }
      });
      t1.start();
   }

   public class MyMessage {

      public String trigger;
      public ArrayList messages;


      public MyMessage(String trigger, ArrayList messages) {
         this.trigger = trigger.trim();
         this.messages = messages;
      }

      public String getRndMessage() {
         return this.messages.size() == 0?null:(this.messages.size() == 1?(String)this.messages.get(0):(String)this.messages.get((new Random()).nextInt(this.messages.size())));
      }
   }
}
