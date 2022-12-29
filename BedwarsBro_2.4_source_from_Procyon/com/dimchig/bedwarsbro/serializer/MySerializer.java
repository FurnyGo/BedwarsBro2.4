// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.serializer;

import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLDecoder;

public class MySerializer
{
    public static String url_origin;
    public static String url_messages;
    public static String separator;
    public static String separator_secondary;
    
    public static String readProps() {
        return extractMyData(readContentByUrl(MySerializer.url_origin));
    }
    
    public static String readMessages() {
        return extractMyData(readContentByUrl(MySerializer.url_messages));
    }
    
    public static String extractMyData(final String content) {
        final String tag_start = "BWBROTAGSTART";
        final String tag_end = "BWBROTAGEND";
        try {
            if (content == null || content.length() < 10 || !content.contains(tag_start) || !content.contains(tag_end)) {
                return null;
            }
            String data_text = content.split(tag_end)[0].trim();
            if (!data_text.contains(tag_start)) {
                return null;
            }
            final String[] splt = data_text.split(tag_start);
            if (splt.length == 0) {
                return null;
            }
            data_text = URLDecoder.decode(splt[splt.length - 1], "UTF-8");
            return data_text;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String readContentByUrl(final String url) {
        try {
            final String content = getText(url);
            return content;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getText(final String url) throws Exception {
        final URL website = new URL(url);
        final URLConnection connection = website.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    
    static {
        MySerializer.url_origin = "https://t.me/bedwarsbro_minecraft/4?embed=1";
        MySerializer.url_messages = "https://t.me/bedwarsbro_minecraft/3?embed=1";
        MySerializer.separator = ";==BWBRO==;";
        MySerializer.separator_secondary = ";=BRO=;";
    }
}
