// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro;

import java.util.Iterator;
import java.util.List;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class FileManager
{
    public static void initFile(final String name) {
        try {
            final File myObj = new File(name);
            myObj.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void clearFile(final String filename) {
        writeToFile(filename, "", false);
    }
    
    public static void writeToFile(final String str, final String name, final boolean append) {
        initFile(name);
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name, append), "UTF-8"));
            out.write((append ? "\n" : "") + str);
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                out.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                out.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public static String readFile(final String filename) {
        initFile(filename);
        try {
            final List<String> list = Files.readAllLines(Paths.get(filename, new String[0]), StandardCharsets.UTF_8);
            final StringBuilder builder = new StringBuilder();
            for (final String s : list) {
                builder.append(s + "\n");
            }
            return builder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
