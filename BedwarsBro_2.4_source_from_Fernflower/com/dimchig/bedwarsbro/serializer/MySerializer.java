package com.dimchig.bedwarsbro.serializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class MySerializer {

   public static String url_origin = "https://t.me/bedwarsbro_minecraft/4?embed=1";
   public static String url_messages = "https://t.me/bedwarsbro_minecraft/3?embed=1";
   public static String separator = ";==BWBRO==;";
   public static String separator_secondary = ";=BRO=;";


   public static String readProps() {
      return extractMyData(readContentByUrl(url_origin));
   }

   public static String readMessages() {
      return extractMyData(readContentByUrl(url_messages));
   }

   public static String extractMyData(String content) {
      String tag_start = "BWBROTAGSTART";
      String tag_end = "BWBROTAGEND";

      try {
         if(content != null && content.length() >= 10 && content.contains(tag_start) && content.contains(tag_end)) {
            String e = content.split(tag_end)[0].trim();
            if(!e.contains(tag_start)) {
               return null;
            } else {
               String[] splt = e.split(tag_start);
               if(splt.length == 0) {
                  return null;
               } else {
                  e = URLDecoder.decode(splt[splt.length - 1], "UTF-8");
                  return e;
               }
            }
         } else {
            return null;
         }
      } catch (Exception var5) {
         return null;
      }
   }

   public static String readContentByUrl(String url) {
      try {
         String content = getText(url);
         return content;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static String getText(String url) throws Exception {
      URL website = new URL(url);
      URLConnection connection = website.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
      StringBuilder response = new StringBuilder();

      String inputLine;
      while((inputLine = in.readLine()) != null) {
         response.append(inputLine);
      }

      in.close();
      return response.toString();
   }

}
