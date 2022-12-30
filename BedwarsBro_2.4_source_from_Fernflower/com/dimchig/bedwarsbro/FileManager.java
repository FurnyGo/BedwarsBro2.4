package com.dimchig.bedwarsbro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class FileManager {

   public static void initFile(String name) {
      try {
         File e = new File(name);
         e.createNewFile();
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public static void clearFile(String filename) {
      writeToFile(filename, "", false);
   }

   public static void writeToFile(String str, String name, boolean append) {
      initFile(name);
      BufferedWriter out = null;

      try {
         out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name, append), "UTF-8"));
         out.write((append?"\n":"") + str);
      } catch (IOException var13) {
         var13.printStackTrace();
      } finally {
         try {
            out.close();
         } catch (IOException var12) {
            var12.printStackTrace();
         }

      }

   }

   public static String readFile(String filename) {
      initFile(filename);

      try {
         List e = Files.readAllLines(Paths.get(filename, new String[0]), StandardCharsets.UTF_8);
         StringBuilder builder = new StringBuilder();
         Iterator var3 = e.iterator();

         while(var3.hasNext()) {
            String s = (String)var3.next();
            builder.append(s + "\n");
         }

         return builder.toString();
      } catch (IOException var5) {
         var5.printStackTrace();
         return "";
      }
   }
}
