package com.dimchig.bedwarsbro;

import com.dimchig.bedwarsbro.FileManager;
import java.util.ArrayList;
import java.util.Iterator;

public class FileNicknamesManager {

   public ArrayList readNames(String filename) {
      ArrayList arr = new ArrayList();

      try {
         String e = FileManager.readFile(filename);
         if(e != null && e.length() != 0) {
            String[] split = e.split("\n");
            if(split.length == 0) {
               return arr;
            } else {
               String[] var5 = split;
               int var6 = split.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String name = var5[var7];
                  name = name.trim();
                  if(name.length() != 0 && !arr.contains(name)) {
                     arr.add(name);
                  }
               }

               return arr;
            }
         } else {
            FileManager.writeToFile("", filename, false);
            return arr;
         }
      } catch (Exception var9) {
         return new ArrayList();
      }
   }

   public void writeNames(String filename, ArrayList names) {
      String s = "";

      String name;
      for(Iterator var4 = names.iterator(); var4.hasNext(); s = s + name + "\n") {
         name = (String)var4.next();
      }

      FileManager.writeToFile(s, filename, false);
   }

   public boolean addName(String filename, String name) {
      ArrayList arr = this.readNames(filename);
      if(arr.contains(name)) {
         return false;
      } else {
         arr.add(name);
         this.writeNames(filename, arr);
         return true;
      }
   }

   public boolean removeName(String filename, String name) {
      ArrayList arr = this.readNames(filename);
      if(!arr.contains(name)) {
         return false;
      } else {
         arr.remove(name);
         this.writeNames(filename, arr);
         return true;
      }
   }
}
