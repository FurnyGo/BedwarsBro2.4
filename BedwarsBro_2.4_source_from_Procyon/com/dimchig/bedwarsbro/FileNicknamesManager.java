// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import java.util.Iterator;
import java.util.ArrayList;

public class FileNicknamesManager
{
    public ArrayList<String> readNames(final String filename) {
        final ArrayList<String> arr = new ArrayList<String>();
        try {
            final String s = FileManager.readFile(filename);
            if (s == null || s.length() == 0) {
                FileManager.writeToFile("", filename, false);
                return arr;
            }
            final String[] split = s.split("\n");
            if (split.length == 0) {
                return arr;
            }
            for (String name : split) {
                name = name.trim();
                if (name.length() != 0) {
                    if (!arr.contains(name)) {
                        arr.add(name);
                    }
                }
            }
            return arr;
        }
        catch (Exception e) {
            return new ArrayList<String>();
        }
    }
    
    public void writeNames(final String filename, final ArrayList<String> names) {
        String s = "";
        for (final String name : names) {
            s = s + name + "\n";
        }
        FileManager.writeToFile(s, filename, false);
    }
    
    public boolean addName(final String filename, final String name) {
        final ArrayList<String> arr = this.readNames(filename);
        if (arr.contains(name)) {
            return false;
        }
        arr.add(name);
        this.writeNames(filename, arr);
        return true;
    }
    
    public boolean removeName(final String filename, final String name) {
        final ArrayList<String> arr = this.readNames(filename);
        if (!arr.contains(name)) {
            return false;
        }
        arr.remove(name);
        this.writeNames(filename, arr);
        return true;
    }
}
