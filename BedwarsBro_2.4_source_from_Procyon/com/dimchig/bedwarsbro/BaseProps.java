// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import java.util.Random;
import com.dimchig.bedwarsbro.serializer.MySerializer;
import java.util.Iterator;
import java.util.ArrayList;

public class BaseProps
{
    private String mod_last_version;
    private String mod_update_link;
    private String discord_link;
    private String mod_autor;
    private String mod_autor_prefix;
    private ArrayList<String> banned_users;
    private ArrayList<String> admin_users;
    public ArrayList<MyMessage> my_messages;
    
    public BaseProps() {
        this.my_messages = new ArrayList<MyMessage>();
        this.mod_last_version = null;
        this.mod_update_link = null;
        this.discord_link = null;
        this.mod_autor = null;
        this.mod_autor_prefix = "&c&l[&6&l\u0421&e&l\u043e&a&l\u0437&b&l\u0434&d&l\u0430&c&l\u0442&6&l\u0435&e&l\u043b&a&l\u044c&c&l]&r";
        this.banned_users = new ArrayList<String>();
        this.admin_users = new ArrayList<String>();
    }
    
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
    
    public ArrayList<String> getModBannedUsers() {
        return this.banned_users;
    }
    
    public boolean isUserBanned(final String player_name) {
        for (final String n : this.banned_users) {
            if (n.equals(player_name)) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<String> getModAdminUsers() {
        return this.admin_users;
    }
    
    public boolean isUserAdmin(final String player_name) {
        for (final String n : this.admin_users) {
            if (n.equals(player_name)) {
                return true;
            }
        }
        return false;
    }
    
    public void printProps() {
        ChatSender.addText("&5=====&dBASE PROPS&5=====");
        ChatSender.addText(" &5mod_last_version &5\u25b8 &d" + this.mod_last_version);
        ChatSender.addText(" &5yt &5\u25b8 &d" + this.mod_update_link);
        ChatSender.addText(" &5discord_link &5\u25b8 &d" + this.discord_link);
        ChatSender.addText(" &5autor &5\u25b8 &d" + this.mod_autor);
        ChatSender.addText(" &5autor_prefix &5\u25b8 &d" + this.mod_autor_prefix);
        ChatSender.addText(" &5admin users:");
        for (final String a : this.admin_users) {
            ChatSender.addText(" &5\u2022 &d" + a);
        }
        ChatSender.addText(" &5banned users:");
        for (final String b : this.banned_users) {
            ChatSender.addText(" &5\u2022 &d" + b);
        }
        ChatSender.addText("&5======================");
    }
    
    public void printMessages() {
        ChatSender.addText("&2=====&aMESSAGES&2=====");
        for (final MyMessage m : this.my_messages) {
            ChatSender.addText(" &a" + m.trigger);
            for (final String a : m.messages) {
                ChatSender.addText("  &2\u2022 " + a);
            }
        }
        ChatSender.addText("&2==================");
    }
    
    public void readMessages() {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final MySerializer mySerializer = Main.mySerializer;
                    final String data_text = MySerializer.readMessages();
                    if (data_text == null) {
                        return;
                    }
                    final String separator_thirdly = ";=BRO2=;";
                    if (data_text.length() < 10) {
                        return;
                    }
                    final String[] split = data_text.split(MySerializer.separator);
                    if (split.length == 0) {
                        return;
                    }
                    if (BaseProps.this.my_messages == null) {
                        BaseProps.this.my_messages = new ArrayList<MyMessage>();
                    }
                    BaseProps.this.my_messages.clear();
                    for (final String line : split) {
                        if (line.length() >= 3) {
                            final String[] split2 = line.split(MySerializer.separator_secondary);
                            final String trigger = split2[0].trim();
                            final ArrayList<String> messages = new ArrayList<String>();
                            for (String s2 : split2[1].trim().split(separator_thirdly)) {
                                s2 = s2.trim();
                                if (s2.length() >= 1) {
                                    messages.add(s2);
                                }
                            }
                            final MyMessage msg = new MyMessage(trigger, messages);
                            BaseProps.this.my_messages.add(msg);
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }
    
    public void readProps() {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final MySerializer mySerializer = Main.mySerializer;
                    final String data_text = MySerializer.readProps();
                    if (data_text == null) {
                        return;
                    }
                    final String[] split = data_text.split(MySerializer.separator);
                    if (split.length < 7) {
                        return;
                    }
                    BaseProps.this.mod_last_version = split[0].trim();
                    BaseProps.this.mod_update_link = split[1].replace("xyzqwerty", ".").trim();
                    BaseProps.this.discord_link = split[2].replace("xyzqwerty", ".").trim();
                    BaseProps.this.mod_autor = split[3].trim();
                    BaseProps.this.mod_autor_prefix = split[4].replace("x.y.z", "&").trim();
                    BaseProps.this.admin_users.clear();
                    final String trim = split[5].trim();
                    final MySerializer mySerializer2 = Main.mySerializer;
                    final String[] split2;
                    final String[] mod_admin_users = split2 = trim.split(MySerializer.separator_secondary);
                    for (final String l : split2) {
                        if (l.length() > 1) {
                            BaseProps.this.admin_users.add(l.trim());
                        }
                    }
                    BaseProps.this.banned_users.clear();
                    final String trim2 = split[6].trim();
                    final MySerializer mySerializer3 = Main.mySerializer;
                    final String[] split3;
                    final String[] mod_banned_users = split3 = trim2.split(MySerializer.separator_secondary);
                    for (final String i : split3) {
                        if (i.length() > 1) {
                            BaseProps.this.banned_users.add(i.trim());
                        }
                    }
                    Main.updateAllBooleans();
                }
                catch (Exception e) {}
            }
        });
        t1.start();
    }
    
    public class MyMessage
    {
        public String trigger;
        public ArrayList<String> messages;
        
        public MyMessage(final String trigger, final ArrayList<String> messages) {
            this.trigger = trigger.trim();
            this.messages = messages;
        }
        
        public String getRndMessage() {
            if (this.messages.size() == 0) {
                return null;
            }
            if (this.messages.size() == 1) {
                return this.messages.get(0);
            }
            return this.messages.get(new Random().nextInt(this.messages.size()));
        }
    }
}
