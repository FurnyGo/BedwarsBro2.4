// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

public class SwearDictionary
{
    public static String[] words;
    
    public static boolean isTextContainsBedWord(String s) {
        s = s.toLowerCase();
        for (final String word : SwearDictionary.words) {
            if (s.contains(word)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        SwearDictionary.words = new String[] { "6\u043b\u044f", "6\u043b\u044f\u0434\u044c", "6\u043b\u044f\u0442\u044c", "b3\u044ae\u0431", "cock", "cunt", "e6a\u043b\u044c", "ebal", "eblan", "e\u0431a\u043b", "e\u0431a\u0442\u044c", "e\u0431y\u0447", "e\u0431\u0430\u0442\u044c", "e\u0431\u0451\u0442", "e\u0431\u043b\u0430\u043d\u0442\u0438\u0439", "fuck", "fucker", "fucking", "xy\u0451\u0432", "xy\u0439", "xy\u044f", "x\u0443\u0435", "x\u0443\u0439", "x\u0443\u044e", "zaeb", "zaebal", "zaebali", "zaebat", "\u0430\u0440\u0445\u0438\u043f\u0438\u0437\u0434\u0440\u0438\u0442", "\u0430\u0445\u0443\u0435\u043b", "\u0430\u0445\u0443\u0435\u0442\u044c", "\u0431\u0437\u0434\u0435\u043d\u0438\u0435", "\u0431\u0437\u0434\u0435\u0442\u044c", "\u0431\u0437\u0434\u0435\u0445", "\u0431\u0437\u0434\u0435\u0446\u044b", "\u0431\u0437\u0434\u0438\u0442", "\u0431\u0437\u0434\u0438\u0446\u044b", "\u0431\u0437\u0434\u043b\u043e", "\u0431\u0437\u0434\u043d\u0443\u0442\u044c", "\u0431\u0437\u0434\u0443\u043d", "\u0431\u0437\u0434\u0443\u043d\u044c\u044f", "\u0431\u0437\u0434\u044e\u0445\u0430", "\u0431\u0437\u0434\u044e\u0448\u043a\u0430", "\u0431\u0437\u0434\u044e\u0448\u043a\u043e", "\u0431\u043b\u044f", "\u0431\u043b\u044f\u0431\u0443", "\u0431\u043b\u044f\u0431\u0443\u0434\u0443", "\u0431\u043b\u044f\u0434", "\u0431\u043b\u044f\u0434\u0438", "\u0431\u043b\u044f\u0434\u0438\u043d\u0430", "\u0431\u043b\u044f\u0434\u0438\u0449\u0435", "\u0431\u043b\u044f\u0434\u043a\u0438", "\u0431\u043b\u044f\u0434\u043e\u0432\u0430\u0442\u044c", "\u0431\u043b\u044f\u0434\u0441\u0442\u0432\u043e", "\u0431\u043b\u044f\u0434\u0443\u043d", "\u0431\u043b\u044f\u0434\u0443\u043d\u044b", "\u0431\u043b\u044f\u0434\u0443\u043d\u044c\u044f", "\u0431\u043b\u044f\u0434\u044c", "\u0431\u043b\u044f\u0434\u044e\u0433\u0430", "\u0431\u043b\u044f\u0442\u044c", "\u0432\u0430\u0444\u0435\u043b", "\u0432\u0430\u0444\u043b\u0451\u0440", "\u0432\u0437\u044a\u0435\u0431\u043a\u0430", "\u0432\u0437\u044c\u0435\u0431\u043a\u0430", "\u0432\u0437\u044c\u0435\u0431\u044b\u0432\u0430\u0442\u044c", "\u0432\u044a\u0435\u0431", "\u0432\u044a\u0435\u0431\u0430\u043b\u0441\u044f", "\u0432\u044a\u0435\u0431\u0435\u043d\u043d", "\u0432\u044a\u0435\u0431\u0443\u0441\u044c", "\u0432\u044a\u0435\u0431\u044b\u0432\u0430\u0442\u044c", "\u0432\u044b\u0431\u043b\u044f\u0434\u043e\u043a", "\u0432\u044b\u0431\u043b\u044f\u0434\u044b\u0448", "\u0432\u044b\u0435\u0431", "\u0432\u044b\u0435\u0431\u0430\u0442\u044c", "\u0432\u044b\u0435\u0431\u0435\u043d", "\u0432\u044b\u0435\u0431\u043d\u0443\u043b\u0441\u044f", "\u0432\u044b\u0435\u0431\u043e\u043d", "\u0432\u044b\u0435\u0431\u044b\u0432\u0430\u0442\u044c\u0441\u044f", "\u0432\u044b\u043f\u0435\u0440\u0434\u0435\u0442\u044c", "\u0432\u044b\u0441\u0440\u0430\u0442\u044c\u0441\u044f", "\u0432\u044b\u0441\u0441\u0430\u0442\u044c\u0441\u044f", "\u0432\u044c\u0435\u0431\u0435\u043d", "\u0433\u0430\u0432\u043d\u043e", "\u0433\u0430\u0432\u043d\u044e\u043a", "\u0433\u0430\u0432\u043d\u044e\u0447\u043a\u0430", "\u0433\u0430\u043c\u043d\u043e", "\u0433\u0430\u043d\u0434\u043e\u043d", "\u0433\u043d\u0438\u0434", "\u0433\u043d\u0438\u0434\u0430", "\u0433\u043d\u0438\u0434\u044b", "\u0433\u043e\u0432\u0435\u043d\u043a\u0430", "\u0433\u043e\u0432\u0435\u0448\u043a\u0430", "\u0433\u043e\u0432\u043d\u0430\u0437\u0438\u044f", "\u0433\u043e\u0432\u043d\u0435\u0446\u043e", "\u0433\u043e\u0432\u043d\u0438\u0449\u0435", "\u0433\u043e\u0432\u043d\u043e", "\u0433\u043e\u0432\u043d\u043e\u0435\u0434", "\u0433\u043e\u0432\u043d\u043e\u043b\u0438\u043d\u043a", "\u0433\u043e\u0432\u043d\u043e\u0447\u0438\u0441\u0442", "\u0433\u043e\u0432\u043d\u044e\u043a", "\u0433\u043e\u0432\u043d\u044e\u0445\u0430", "\u0433\u043e\u0432\u043d\u044f\u0434\u0438\u043d\u0430", "\u0433\u043e\u0432\u043d\u044f\u043a", "\u0433\u043e\u0432\u043d\u044f\u043d\u044b\u0439", "\u0433\u043e\u0432\u043d\u044f\u0442\u044c", "\u0433\u043e\u043d\u0434\u043e\u043d", "\u0434\u043e\u0435\u0431\u044b\u0432\u0430\u0442\u044c\u0441\u044f", "\u0434\u043e\u043b\u0431\u043e\u0435\u0431", "\u0434\u043e\u043b\u0431\u043e\u0451\u0431", "\u0434\u043e\u043b\u0431\u043e\u044f\u0449\u0435\u0440", "\u0434\u0440\u0438\u0441\u0442", "\u0434\u0440\u0438\u0441\u0442\u0430\u043d\u0443\u0442\u044c", "\u0434\u0440\u0438\u0441\u0442\u0430\u0442\u044c", "\u0434\u0440\u0438\u0441\u0442\u0443\u043d", "\u0434\u0440\u0438\u0441\u0442\u0443\u0445\u0430", "\u0434\u0440\u043e\u0447\u0435\u043b\u043b\u043e", "\u0434\u0440\u043e\u0447\u0435\u043d\u0430", "\u0434\u0440\u043e\u0447\u0438\u043b\u0430", "\u0434\u0440\u043e\u0447\u0438\u043b\u043a\u0430", "\u0434\u0440\u043e\u0447\u0438\u0441\u0442\u044b\u0439", "\u0434\u0440\u043e\u0447\u0438\u0442\u044c", "\u0434\u0440\u043e\u0447\u043a\u0430", "\u0434\u0440\u043e\u0447\u0443\u043d", "\u04356\u0430\u043b", "\u04356\u0443\u0442", "\u0435\u0431 \u0442\u0432\u043e\u044e \u043c\u0430\u0442\u044c", "\u0451\u0431 \u0442\u0432\u043e\u044e \u043c\u0430\u0442\u044c", "\u0451\u0431a\u043d", "\u0435\u0431\u0443", "\u0451\u0431\u0443", "\u0435\u0431y\u0447", "\u0435\u0431\u0430\u043b", "\u0435\u0431\u0430\u043b\u043e", "\u0435\u0431\u0430\u043b\u044c\u043d\u0438\u043a", "\u0435\u0431\u0430\u043d", "\u0435\u0431\u0430\u043d\u0430\u043c\u0430\u0442\u044c", "\u0435\u0431\u0430\u043d\u0430\u0442", "\u0435\u0431\u0430\u043d\u0430\u044f", "\u0451\u0431\u0430\u043d\u0430\u044f", "\u0435\u0431\u0430\u043d\u0438\u0447\u0435\u0441\u043a\u0438\u0439", "\u0435\u0431\u0430\u043d\u043d\u044b\u0439", "\u0435\u0431\u0430\u043d\u043d\u044b\u0439\u0432\u0440\u043e\u0442", "\u0435\u0431\u0430\u043d\u043e\u0435", "\u0435\u0431\u0430\u043d\u0443\u0442\u044c", "\u0435\u0431\u0430\u043d\u0443\u0442\u044c\u0441\u044f", "\u0451\u0431\u0430\u043d\u0443\u044e", "\u0435\u0431\u0430\u043d\u044b\u0439", "\u0435\u0431\u0430\u043d\u044c\u043a\u043e", "\u0435\u0431\u0430\u0440\u044c", "\u0435\u0431\u0430\u0442", "\u0451\u0431\u0430\u0442", "\u0435\u0431\u0430\u0442\u043e\u0440\u0438\u044f", "\u0435\u0431\u0430\u0442\u044c", "\u0435\u0431\u0430\u0442\u044c-\u043a\u043e\u043f\u0430\u0442\u044c", "\u0435\u0431\u0430\u0442\u044c\u0441\u044f", "\u0435\u0431\u0430\u0448\u0438\u0442\u044c", "\u0435\u0431\u0451\u043d\u0430", "\u0435\u0431\u0435\u0442", "\u0435\u0431\u0451\u0442", "\u0435\u0431\u0435\u0446", "\u0435\u0431\u0438\u043a", "\u0435\u0431\u0438\u043d", "\u0435\u0431\u0438\u0441\u044c", "\u0435\u0431\u0438\u0447\u0435\u0441\u043a\u0430\u044f", "\u0435\u0431\u043a\u0438", "\u0435\u0431\u043b\u0430", "\u0435\u0431\u043b\u0430\u043d", "\u0435\u0431\u043b\u0438\u0432\u044b\u0439", "\u0435\u0431\u043b\u0438\u0449\u0435", "\u0435\u0431\u043b\u043e", "\u0435\u0431\u043b\u044b\u0441\u0442", "\u0435\u0431\u043b\u044f", "\u0451\u0431\u043d", "\u0435\u0431\u043d\u0443\u0442\u044c", "\u0435\u0431\u043d\u0443\u0442\u044c\u0441\u044f", "\u0435\u0431\u043d\u044f", "\u0435\u0431\u043e\u0448\u0438\u0442\u044c", "\u0435\u0431\u0441\u043a\u0430\u044f", "\u0435\u0431\u0441\u043a\u0438\u0439", "\u0435\u0431\u0442\u0432\u043e\u044e\u043c\u0430\u0442\u044c", "\u0435\u0431\u0443\u043d", "\u0435\u0431\u0443\u0442", "\u0435\u0431\u0443\u0447", "\u0435\u0431\u0443\u0447\u0435", "\u0435\u0431\u0443\u0447\u0435\u0435", "\u0435\u0431\u0443\u0447\u0438\u0439", "\u0435\u0431\u0443\u0447\u0438\u043c", "\u0435\u0431\u0443\u0449", "\u0435\u0431\u044b\u0440\u044c", "\u0435\u043b\u0434\u0430", "\u0435\u043b\u0434\u0430\u043a", "\u0435\u043b\u0434\u0430\u0447\u0438\u0442\u044c", "\u0436\u043e\u043f\u0430", "\u0436\u043e\u043f\u0443", "\u0437\u0430\u0433\u043e\u0432\u043d\u044f\u0442\u044c", "\u0437\u0430\u0434\u0440\u0430\u0447\u0438\u0432\u0430\u0442\u044c", "\u0437\u0430\u0434\u0440\u0438\u0441\u0442\u0430\u0442\u044c", "\u0437\u0430\u0434\u0440\u043e\u0442\u0430", "\u0437\u0430\u04356", "\u0437\u0430\u04516", "\u0437\u0430\u0435\u0431", "\u0437\u0430\u0451\u0431", "\u0437\u0430\u0435\u0431\u0430", "\u0437\u0430\u0435\u0431\u0430\u043b", "\u0437\u0430\u0435\u0431\u0430\u043d\u0435\u0446", "\u0437\u0430\u0435\u0431\u0430\u0441\u0442\u0430\u044f", "\u0437\u0430\u0435\u0431\u0430\u0441\u0442\u044b\u0439", "\u0437\u0430\u0435\u0431\u0430\u0442\u044c", "\u0437\u0430\u0435\u0431\u0430\u0442\u044c\u0441\u044f", "\u0437\u0430\u0435\u0431\u0430\u0448\u0438\u0442\u044c", "\u0437\u0430\u0435\u0431\u0438\u0441\u0442\u043e\u0435", "\u0437\u0430\u0451\u0431\u0438\u0441\u0442\u043e\u0435", "\u0437\u0430\u0435\u0431\u0438\u0441\u0442\u044b\u0435", "\u0437\u0430\u0451\u0431\u0438\u0441\u0442\u044b\u0435", "\u0437\u0430\u0435\u0431\u0438\u0441\u0442\u044b\u0439", "\u0437\u0430\u0451\u0431\u0438\u0441\u0442\u044b\u0439", "\u0437\u0430\u0435\u0431\u0438\u0441\u044c", "\u0437\u0430\u0435\u0431\u043e\u0448\u0438\u0442\u044c", "\u0437\u0430\u0435\u0431\u044b\u0432\u0430\u0442\u044c\u0441\u044f", "\u0437\u0430\u043b\u0443\u043f", "\u0437\u0430\u043b\u0443\u043f\u0430", "\u0437\u0430\u043b\u0443\u043f\u0430\u0442\u044c\u0441\u044f", "\u0437\u0430\u043b\u0443\u043f\u0438\u0442\u044c", "\u0437\u0430\u043b\u0443\u043f\u0438\u0442\u044c\u0441\u044f", "\u0437\u0430\u043c\u0443\u0434\u043e\u0445\u0430\u0442\u044c\u0441\u044f", "\u0437\u0430\u043f\u0438\u0437\u0434\u044f\u0447\u0438\u0442\u044c", "\u0437\u0430\u0441\u0435\u0440\u0430\u0442\u044c", "\u0437\u0430\u0441\u0435\u0440\u0443\u043d", "\u0437\u0430\u0441\u0435\u0440\u044f", "\u0437\u0430\u0441\u0438\u0440\u0430\u0442\u044c", "\u0437\u0430\u0441\u0440\u0443\u043d", "\u0437\u0430\u0445\u0443\u044f\u0447\u0438\u0442\u044c", "\u0437\u0430\u044f\u0431\u0435\u0441\u0442\u0430\u044f", "\u0437\u043b\u043e\u0435\u0431", "\u0437\u043b\u043e\u0435\u0431\u0443\u0447\u0430\u044f", "\u0437\u043b\u043e\u0435\u0431\u0443\u0447\u0435\u0435", "\u0437\u043b\u043e\u0435\u0431\u0443\u0447\u0438\u0439", "\u0438\u0431\u0430\u043d\u0430\u043c\u0430\u0442", "\u0438\u0431\u043e\u043d\u0435\u0445", "\u0438\u0437\u0433\u043e\u0432\u043d\u044f\u0442\u044c", "\u0438\u0437\u0433\u043e\u0432\u043d\u044f\u0442\u044c\u0441\u044f", "\u0438\u0437\u044a\u0435\u0431\u043d\u0443\u0442\u044c\u0441\u044f", "\u0438\u043f\u0430\u0442\u044c", "\u0438\u043f\u0430\u0442\u044c\u0441\u044f", "\u0438\u043f\u0430\u0446\u0446\u043e", "\u041a\u0430\u043a\u0434\u0432\u0430\u043f\u0430\u043b\u044c\u0446\u0430\u043e\u0431\u043e\u0441\u0441\u0430\u0442\u044c", "\u043a\u043e\u043d\u0447\u0430", "\u043a\u0443\u0440\u0432\u0430", "\u043a\u0443\u0440\u0432\u044f\u0442\u043d\u0438\u043a", "\u043b\u043e\u0445", "\u043b\u043e\u0448\u0430\u0440a", "\u043b\u043e\u0448\u0430\u0440\u0430", "\u043b\u043e\u0448\u0430\u0440\u044b", "\u043b\u043e\u0448\u043e\u043a", "\u043b\u044f\u0440\u0432\u0430", "\u043c\u0430\u043b\u0430\u0444\u044c\u044f", "\u043c\u0430\u043d\u0434\u0430", "\u043c\u0430\u043d\u0434\u0430\u0432\u043e\u0448\u0435\u043a", "\u043c\u0430\u043d\u0434\u0430\u0432\u043e\u0448\u043a\u0430", "\u043c\u0430\u043d\u0434\u0430\u0432\u043e\u0448\u043a\u0438", "\u043c\u0430\u043d\u0434\u0435\u0439", "\u043c\u0430\u043d\u0434\u0435\u043d\u044c", "\u043c\u0430\u043d\u0434\u0435\u0442\u044c", "\u043c\u0430\u043d\u0434\u0438\u0449\u0430", "\u043c\u0430\u043d\u0434\u043e\u0439", "\u043c\u0430\u043d\u0434\u0443", "\u043c\u0430\u043d\u0434\u044e\u043a", "\u043c\u0438\u043d\u0435\u0442", "\u043c\u0438\u043d\u0435\u0442\u0447\u0438\u043a", "\u043c\u0438\u043d\u0435\u0442\u0447\u0438\u0446\u0430", "\u043c\u043b\u044f\u0442\u044c", "\u043c\u043e\u043a\u0440\u043e\u0449\u0435\u043b\u043a\u0430", "\u043c\u043e\u043a\u0440\u043e\u0449\u0451\u043b\u043a\u0430", "\u043c\u0440\u0430\u0437\u044c", "\u043c\u0443\u0434ak", "\u043c\u0443\u0434a\u043a", "\u043c\u0443\u0434\u0430\u0433", "\u043c\u0443\u0434\u0430\u043a", "\u043c\u0443\u0434\u0435", "\u043c\u0443\u0434\u0435\u043b\u044c", "\u043c\u0443\u0434\u0435\u0442\u044c", "\u043c\u0443\u0434\u0438", "\u043c\u0443\u0434\u0438\u043b", "\u043c\u0443\u0434\u0438\u043b\u0430", "\u043c\u0443\u0434\u0438\u0441\u0442\u044b\u0439", "\u043c\u0443\u0434\u043d\u044f", "\u043c\u0443\u0434\u043e\u0435\u0431", "\u043c\u0443\u0434\u043e\u0437\u0432\u043e\u043d", "\u043c\u0443\u0434\u043e\u043a\u043b\u044e\u0439", "\u043d\u0430 \u0445\u0435\u0440", "\u043d\u0430 \u0445\u0443\u0439", "\u043d\u0430\u0431\u0437\u0434\u0435\u043b", "\u043d\u0430\u0431\u0437\u0434\u0435\u0442\u044c", "\u043d\u0430\u0433\u043e\u0432\u043d\u044f\u0442\u044c", "\u043d\u0430\u0434\u0440\u0438\u0441\u0442\u0430\u0442\u044c", "\u043d\u0430\u0434\u0440\u043e\u0447\u0438\u0442\u044c", "\u043d\u0430\u0435\u0431\u0430\u0442\u044c", "\u043d\u0430\u0435\u0431\u0435\u0442", "\u043d\u0430\u0435\u0431\u043d\u0443\u0442\u044c", "\u043d\u0430\u0435\u0431\u043d\u0443\u0442\u044c\u0441\u044f", "\u043d\u0430\u0435\u0431\u044b\u0432\u0430\u0442\u044c", "\u043d\u0430\u043f\u0438\u0437\u0434\u0435\u043b", "\u043d\u0430\u043f\u0438\u0437\u0434\u0435\u043b\u0438", "\u043d\u0430\u043f\u0438\u0437\u0434\u0435\u043b\u043e", "\u043d\u0430\u043f\u0438\u0437\u0434\u0438\u043b\u0438", "\u043d\u0430\u0441\u0440\u0430\u0442\u044c", "\u043d\u0430\u0441\u0442\u043e\u043f\u0438\u0437\u0434\u0438\u0442\u044c", "\u043d\u0430\u0445\u0435\u0440", "\u043d\u0430\u0445\u0440\u0435\u043d", "\u043d\u0430\u0445\u0443\u0439", "\u043d\u0430\u0445\u0443\u0439\u043d\u0438\u043a", "\u043d\u0435 \u0435\u0431\u0435\u0442", "\u043d\u0435 \u0435\u0431\u0451\u0442", "\u043d\u0435\u0432\u0440\u043e\u0442\u0435\u0431\u0443\u0447\u0438\u0439", "\u043d\u0435\u0432\u044a\u0435\u0431\u0435\u043d\u043d\u043e", "\u043d\u0435\u0445\u0438\u0440\u0430", "\u043d\u0435\u0445\u0440\u0435\u043d", "\u041d\u0435\u0445\u0443\u0439", "\u043d\u0435\u0445\u0443\u0439\u0441\u0442\u0432\u0435\u043d\u043d\u043e", "\u043d\u0438\u0438\u0431\u0430\u0446\u043e", "\u043d\u0438\u0438\u043f\u0430\u0446\u0446\u0430", "\u043d\u0438\u0438\u043f\u0430\u0446\u0446\u043e", "\u043d\u0438\u0438\u043f\u0435\u0442", "\u043d\u0438\u043a\u0443\u044f", "\u043d\u0438\u0445\u0435\u0440\u0430", "\u043d\u0438\u0445\u0443\u044f", "\u043e\u0431\u0434\u0440\u0438\u0441\u0442\u0430\u0442\u044c\u0441\u044f", "\u043e\u0431\u043e\u0441\u0440\u0430\u043d\u0435\u0446", "\u043e\u0431\u043e\u0441\u0440\u0430\u0442\u044c", "\u043e\u0431\u043e\u0441\u0446\u0430\u0442\u044c", "\u043e\u0431\u043e\u0441\u0446\u0430\u0442\u044c\u0441\u044f", "\u043e\u0431\u0441\u0438\u0440\u0430\u0442\u044c", "\u043e\u0431\u044a\u0435\u0431\u043e\u0441", "\u043e\u0431\u044c\u0435\u0431\u0430\u0442\u044c ", "\u043e\u0431\u044c\u0435\u0431\u043e\u0441", "\u043e\u0434\u043d\u043e\u0445\u0443\u0439\u0441\u0442\u0432\u0435\u043d\u043d\u043e", "\u043e\u043f\u0435\u0437\u0434\u0430\u043b", "\u043e\u043f\u0438\u0437\u0434\u0435", "\u043e\u043f\u0438\u0437\u0434\u0435\u043d\u0438\u0432\u0430\u044e\u0449\u0435", "\u043e\u0441\u0442\u043e\u0435\u0431\u0435\u043d\u0438\u0442\u044c", "\u043e\u0441\u0442\u043e\u043f\u0438\u0437\u0434\u0435\u0442\u044c", "\u043e\u0442\u043c\u0443\u0434\u043e\u0445\u0430\u0442\u044c", "\u043e\u0442\u043f\u0438\u0437\u0434\u0438\u0442\u044c", "\u043e\u0442\u043f\u0438\u0437\u0434\u044f\u0447\u0438\u0442\u044c", "\u043e\u0442\u043f\u043e\u0440\u043e\u0442\u044c", "\u043e\u0442\u044a\u0435\u0431\u0438\u0441\u044c", "\u043e\u0445\u0443\u0435\u0432\u0430\u0442\u0435\u043b\u044c\u0441\u043a\u0438\u0439", "\u043e\u0445\u0443\u0435\u0432\u0430\u0442\u044c", "\u043e\u0445\u0443\u0435\u0432\u0430\u044e\u0449\u0438\u0439", "\u043e\u0445\u0443\u0435\u043b", "\u043e\u0445\u0443\u0435\u043d\u043d\u043e", "\u043e\u0445\u0443\u0435\u043d\u044c\u0447\u0438\u043a", "\u043e\u0445\u0443\u0435\u0442\u044c", "\u043e\u0445\u0443\u0438\u0442\u0435\u043b\u044c\u043d\u043e", "\u043e\u0445\u0443\u0438\u0442\u0435\u043b\u044c\u043d\u044b\u0439", "\u043e\u0445\u0443\u044f\u043d\u044c\u0447\u0438\u043a", "\u043e\u0445\u0443\u044f\u0447\u0438\u0432\u0430\u0442\u044c", "\u043e\u0445\u0443\u044f\u0447\u0438\u0442\u044c", "\u043e\u0447\u043a\u0443\u043d", "\u043f\u0430\u0434\u043b\u0430", "\u043f\u0430\u0434\u043e\u043d\u043a\u0438", "\u043f\u0430\u0434\u043e\u043d\u043e\u043a", "\u043f\u0430\u0441\u043a\u0443\u0434\u0430", "\u043f\u0435\u0434\u0435\u0440\u0430\u0441", "\u043f\u0435\u0434\u0438\u043a", "\u043f\u0435\u0434\u0440\u0438\u043a", "\u043f\u0435\u0434\u0440\u0438\u043b\u0430", "\u043f\u0435\u0434\u0440\u0438\u043b\u043b\u043e", "\u043f\u0435\u0434\u0440\u0438\u043b\u043e", "\u043f\u0435\u0434\u0440\u0438\u043b\u044b", "\u043f\u0435\u0437\u0434\u0435\u043d\u044c", "\u043f\u0435\u0437\u0434\u0438\u0442", "\u043f\u0435\u0437\u0434\u0438\u0448\u044c", "\u043f\u0435\u0437\u0434\u043e", "\u043f\u0435\u0437\u0434\u044f\u0442", "\u043f\u0435\u0440\u0434\u0430\u043d\u0443\u0442\u044c", "\u043f\u0435\u0440\u0434\u0435\u0436", "\u043f\u0435\u0440\u0434\u0435\u043d\u0438\u0435", "\u043f\u0435\u0440\u0434\u0435\u0442\u044c", "\u043f\u0435\u0440\u0434\u0438\u043b\u044c\u043d\u0438\u043a", "\u043f\u0435\u0440\u0434\u043d\u0443\u0442\u044c", "\u043f\u0451\u0440\u0434\u043d\u0443\u0442\u044c", "\u043f\u0435\u0440\u0434\u0443\u043d", "\u043f\u0435\u0440\u0434\u0443\u043d\u0435\u0446", "\u043f\u0435\u0440\u0434\u0443\u043d\u0438\u043d\u0430", "\u043f\u0435\u0440\u0434\u0443\u043d\u044c\u044f", "\u043f\u0435\u0440\u0434\u0443\u0445\u0430", "\u043f\u0435\u0440\u0434\u044c", "\u043f\u0435\u0440\u0435\u0451\u0431\u043e\u043a", "\u043f\u0435\u0440\u043d\u0443\u0442\u044c", "\u043f\u0451\u0440\u043d\u0443\u0442\u044c", "\u043f\u04383\u0434", "\u043f\u04383\u0434\u0435", "\u043f\u04383\u0434\u0443", "\u043f\u0438z\u0434\u0435\u0446", "\u043f\u0438\u0434\u0430\u0440", "\u043f\u0438\u0434\u0430\u0440a\u0441", "\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u0430", "\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u044b", "\u043f\u0438\u0434\u0430\u0440\u044b", "\u043f\u0438\u0434\u043e\u0440", "\u043f\u0438\u0434\u043e\u0440\u0430\u0441\u044b", "\u043f\u0438\u0434\u043e\u0440\u043a\u0430", "\u043f\u0438\u0434\u043e\u0440\u043e\u043a", "\u043f\u0438\u0434\u043e\u0440\u044b", "\u043f\u0438\u0434\u0440\u0430\u0441", "\u043f\u0438\u0437\u0434\u0430", "\u043f\u0438\u0437\u0434\u0430\u043d\u0443\u0442\u044c", "\u043f\u0438\u0437\u0434\u0430\u043d\u0443\u0442\u044c\u0441\u044f", "\u043f\u0438\u0437\u0434\u0430\u0440\u0432\u0430\u043d\u044c\u0447\u0438\u043a", "\u043f\u0438\u0437\u0434\u0430\u0442\u043e", "\u043f\u0438\u0437\u0434\u0430\u0442\u043e\u0435", "\u043f\u0438\u0437\u0434\u0430\u0442\u044b\u0439", "\u043f\u0438\u0437\u0434\u0435\u043d\u043a\u0430", "\u043f\u0438\u0437\u0434\u0435\u043d\u044b\u0448", "\u043f\u0438\u0437\u0434\u0451\u043d\u044b\u0448", "\u043f\u0438\u0437\u0434\u0435\u0442\u044c", "\u043f\u0438\u0437\u0434\u0435\u0446", "\u043f\u0438\u0437\u0434\u0438\u0442", "\u043f\u0438\u0437\u0434\u0438\u0442\u044c", "\u043f\u0438\u0437\u0434\u0438\u0442\u044c\u0441\u044f", "\u043f\u0438\u0437\u0434\u0438\u0448\u044c", "\u043f\u0438\u0437\u0434\u0438\u0449\u0430", "\u043f\u0438\u0437\u0434\u0438\u0449\u0435", "\u043f\u0438\u0437\u0434\u043e\u0431\u043e\u043b", "\u043f\u0438\u0437\u0434\u043e\u0431\u043e\u043b\u044b", "\u043f\u0438\u0437\u0434\u043e\u0431\u0440\u0430\u0442\u0438\u044f", "\u043f\u0438\u0437\u0434\u043e\u0432\u0430\u0442\u0430\u044f", "\u043f\u0438\u0437\u0434\u043e\u0432\u0430\u0442\u044b\u0439", "\u043f\u0438\u0437\u0434\u043e\u043b\u0438\u0437", "\u043f\u0438\u0437\u0434\u043e\u043d\u0443\u0442\u044b\u0435", "\u043f\u0438\u0437\u0434\u043e\u0440\u0432\u0430\u043d\u0435\u0446", "\u043f\u0438\u0437\u0434\u043e\u0440\u0432\u0430\u043d\u043a\u0430", "\u043f\u0438\u0437\u0434\u043e\u0441\u0442\u0440\u0430\u0434\u0430\u0442\u0435\u043b\u044c", "\u043f\u0438\u0437\u0434\u0443", "\u043f\u0438\u0437\u0434\u0443\u0439", "\u043f\u0438\u0437\u0434\u0443\u043d", "\u043f\u0438\u0437\u0434\u0443\u043d\u044c\u044f", "\u043f\u0438\u0437\u0434\u044b", "\u043f\u0438\u0437\u0434\u044e\u0433\u0430", "\u043f\u0438\u0437\u0434\u044e\u043a", "\u043f\u0438\u0437\u0434\u044e\u043b\u0438\u043d\u0430", "\u043f\u0438\u0437\u0434\u044e\u043b\u044f", "\u043f\u0438\u0437\u0434\u044f\u0442", "\u043f\u0438\u0437\u0434\u044f\u0447\u0438\u0442\u044c", "\u043f\u0438\u0441\u0431\u0448\u043a\u0438", "\u043f\u0438\u0441\u044c\u043a\u0430", "\u043f\u0438\u0441\u044c\u043a\u043e\u0441\u0442\u0440\u0430\u0434\u0430\u0442\u0435\u043b\u044c", "\u043f\u0438\u0441\u044e\u043d", "\u043f\u0438\u0441\u044e\u0448\u043a\u0430", "\u043f\u043e \u0445\u0443\u0439", "\u043f\u043e \u0445\u0443\u044e", "\u043f\u043e\u0434\u0433\u043e\u0432\u043d\u044f\u0442\u044c", "\u043f\u043e\u0434\u043e\u043d\u043a\u0438", "\u043f\u043e\u0434\u043e\u043d\u043e\u043a", "\u043f\u043e\u0434\u044a\u0435\u0431\u043d\u0443\u0442\u044c", "\u043f\u043e\u0434\u044a\u0435\u0431\u043d\u0443\u0442\u044c\u0441\u044f", "\u043f\u043e\u0435\u0431\u0430\u0442\u044c", "\u043f\u043e\u0435\u0431\u0435\u043d\u044c", "\u043f\u043e\u0451\u0431\u044b\u0432\u0430\u0430\u0435\u0442", "\u043f\u043e\u0441\u043a\u0443\u0434\u0430", "\u043f\u043e\u0441\u0440\u0430\u0442\u044c", "\u043f\u043e\u0442\u0430\u0441\u043a\u0443\u0445\u0430", "\u043f\u043e\u0442\u0430\u0441\u043a\u0443\u0448\u043a\u0430", "\u043f\u043e\u0445\u0435\u0440", "\u043f\u043e\u0445\u0435\u0440\u0438\u043b", "\u043f\u043e\u0445\u0435\u0440\u0438\u043b\u0430", "\u043f\u043e\u0445\u0435\u0440\u0438\u043b\u0438", "\u043f\u043e\u0445\u0435\u0440\u0443", "\u043f\u043e\u0445\u0440\u0435\u043d", "\u043f\u043e\u0445\u0440\u0435\u043d\u0443", "\u043f\u043e\u0445\u0443\u0439", "\u043f\u043e\u0445\u0443\u0438\u0441\u0442", "\u043f\u043e\u0445\u0443\u0438\u0441\u0442\u043a\u0430", "\u043f\u043e\u0445\u0443\u044e", "\u043f\u0440\u0438\u0434\u0443\u0440\u043e\u043a", "\u043f\u0440\u0438\u0435\u0431\u0430\u0442\u044c\u0441\u044f", "\u043f\u0440\u0438\u043f\u0438\u0437\u0434\u0435\u043d\u044c", "\u043f\u0440\u0438\u043f\u0438\u0437\u0434\u043d\u0443\u0442\u044b\u0439", "\u043f\u0440\u0438\u043f\u0438\u0437\u0434\u044e\u043b\u0438\u043d\u0430", "\u043f\u0440\u043e\u0431\u0437\u0434\u0435\u043b\u0441\u044f", "\u043f\u0440\u043e\u0431\u043b\u044f\u0434\u044c", "\u043f\u0440\u043e\u0435\u0431", "\u043f\u0440\u043e\u0435\u0431\u0430\u043d\u043a\u0430", "\u043f\u0440\u043e\u0435\u0431\u0430\u0442\u044c", "\u043f\u0440\u043e\u043c\u0430\u043d\u0434\u0435\u0442\u044c", "\u043f\u0440\u043e\u043c\u0443\u0434\u0435\u0442\u044c", "\u043f\u0440\u043e\u043f\u0438\u0437\u0434\u0435\u043b\u0441\u044f", "\u043f\u0440\u043e\u043f\u0438\u0437\u0434\u0435\u0442\u044c", "\u043f\u0440\u043e\u043f\u0438\u0437\u0434\u044f\u0447\u0438\u0442\u044c", "\u0440\u0430\u0437\u0434\u043e\u043b\u0431\u0430\u0439", "\u0440\u0430\u0437\u0445\u0443\u044f\u0447\u0438\u0442\u044c", "\u0440\u0430\u0437\u044a\u0435\u0431", "\u0440\u0430\u0437\u044a\u0435\u0431\u0430", "\u0440\u0430\u0437\u044a\u0435\u0431\u0430\u0439", "\u0440\u0430\u0437\u044a\u0435\u0431\u0430\u0442\u044c", "\u0440\u0430\u0441\u043f\u0438\u0437\u0434\u0430\u0439", "\u0440\u0430\u0441\u043f\u0438\u0437\u0434\u0435\u0442\u044c\u0441\u044f", "\u0440\u0430\u0441\u043f\u0438\u0437\u0434\u044f\u0439", "\u0440\u0430\u0441\u043f\u0438\u0437\u0434\u044f\u0439\u0441\u0442\u0432\u043e", "\u0440\u0430\u0441\u043f\u0440\u043e\u0435\u0442\u044c", "\u0441\u0432\u043e\u043b\u043e\u0442\u0430", "\u0441\u0432\u043e\u043b\u043e\u0447\u044c", "\u0441\u0433\u043e\u0432\u043d\u044f\u0442\u044c", "\u0441\u0435\u043a\u0435\u043b\u044c", "\u0441\u0435\u0440\u0443\u043d", "\u0441\u0435\u0440\u044c\u043a\u0430", "\u0441\u0435\u0441\u0442\u0440\u043e\u0435\u0431", "\u0441\u0438\u043a\u0435\u043b\u044c", "\u0441\u0438\u043b\u0430", "\u0441\u0438\u0440\u0430\u0442\u044c", "\u0441\u0438\u0440\u044b\u0432\u0430\u0442\u044c", "\u0441\u043e\u0441\u0438", "\u0441\u043f\u0438\u0437\u0434\u0435\u043b", "\u0441\u043f\u0438\u0437\u0434\u0435\u0442\u044c", "\u0441\u043f\u0438\u0437\u0434\u0438\u043b", "\u0441\u043f\u0438\u0437\u0434\u0438\u043b\u0430", "\u0441\u043f\u0438\u0437\u0434\u0438\u043b\u0438", "\u0441\u043f\u0438\u0437\u0434\u0438\u0442", "\u0441\u043f\u0438\u0437\u0434\u0438\u0442\u044c", "\u0441\u0440\u0430\u043a\u0430", "\u0441\u0440\u0430\u043a\u0443", "\u0441\u0440\u0430\u043d\u044b\u0439", "\u0441\u0440\u0430\u043d\u044c\u0435", "\u0441\u0440\u0430\u0442\u044c", "\u0441\u0440\u0443\u043d", "\u0441\u0441\u0430\u043a\u0430", "\u0441\u0441\u044b\u0448\u044c", "\u0441\u0442\u0435\u0440\u0432\u0430", "\u0441\u0442\u0440\u0430\u0445\u043e\u043f\u0438\u0437\u0434\u0438\u0449\u0435", "\u0441\u0443\u043a\u0430", "\u0441\u0443\u043a\u0438", "\u0441\u0443\u0445\u043e\u0434\u0440\u043e\u0447\u043a\u0430", "\u0441\u0443\u0447\u0430\u0440\u0430", "\u0441\u0443\u0447\u0438\u0439", "\u0441\u0443\u0447\u043a\u0430", "\u0441\u0443\u0447\u043a\u043e", "\u0441\u0443\u0447\u043e\u043d\u043e\u043a", "\u0441\u0443\u0447\u044c\u0435", "\u0441\u0446\u0430\u043d\u0438\u0435", "\u0441\u0446\u0430\u0442\u044c", "\u0441\u0446\u0443\u043a\u0430", "\u0441\u0446\u0443\u043a\u0438", "\u0441\u0446\u0443\u043a\u043e\u043d\u0430\u0445", "\u0441\u0446\u0443\u043b\u044c", "\u0441\u0446\u044b\u0445\u0430", "\u0441\u0446\u044b\u0448\u044c", "\u0441\u044a\u0435\u0431\u0430\u0442\u044c\u0441\u044f", "\u0441\u044b\u043a\u0443\u043d", "\u0442\u0440\u0430\u0445\u0430\u04356", "\u0442\u0440\u0430\u0445\u0430\u0435\u0431", "\u0442\u0440\u0430\u0445\u0430\u0451\u0431", "\u0442\u0440\u0430\u0445\u0430\u0442\u0435\u043b\u044c", "\u0443\u0431\u043b\u044e\u0434\u043e\u043a", "\u0443\u0435\u0431\u0430\u0442\u044c", "\u0443\u0451\u0431\u0438\u0449\u0430", "\u0443\u0435\u0431\u0438\u0449\u0435", "\u0443\u0451\u0431\u0438\u0449\u0435", "\u0443\u0435\u0431\u0438\u0449\u043d\u043e\u0435", "\u0443\u0451\u0431\u0438\u0449\u043d\u043e\u0435", "\u0443\u0435\u0431\u043a", "\u0443\u0435\u0431\u043a\u0438", "\u0443\u0451\u0431\u043a\u0438", "\u0443\u0435\u0431\u043e\u043a", "\u0443\u0451\u0431\u043e\u043a", "\u0443\u0440\u044e\u043a", "\u0443\u0441\u0440\u0430\u0442\u044c\u0441\u044f", "\u0443\u0448\u043b\u0435\u043f\u043e\u043a", "\u0445_\u0443_\u044f_\u0440_\u0430", "\u0445y\u0451", "\u0445y\u0439", "\u0445y\u0439\u043d\u044f", "\u0445\u0430\u043c\u043b\u043e", "\u0445\u0435\u0440", "\u0445\u0435\u0440\u043d\u044f", "\u0445\u0435\u0440\u043e\u0432\u0430\u0442\u043e", "\u0445\u0435\u0440\u043e\u0432\u0438\u043d\u0430", "\u0445\u0435\u0440\u043e\u0432\u044b\u0439", "\u0445\u0438\u0442\u0440\u043e\u0432\u044b\u0435\u0431\u0430\u043d\u043d\u044b\u0439", "\u0445\u0438\u0442\u0440\u043e\u0436\u043e\u043f\u044b\u0439", "\u0445\u0443e\u043c", "\u0445\u0443\u0435", "\u0445\u0443\u0451", "\u0445\u0443\u0435\u0432\u0430\u0442\u043e", "\u0445\u0443\u0451\u0432\u0435\u043d\u044c\u043a\u0438\u0439", "\u0445\u0443\u0435\u0432\u0438\u043d\u0430", "\u0445\u0443\u0435\u0432\u043e", "\u0445\u0443\u0435\u0432\u044b\u0439", "\u0445\u0443\u0451\u0432\u044b\u0439", "\u0445\u0443\u0435\u043a", "\u0445\u0443\u0451\u043a", "\u0445\u0443\u0435\u043b", "\u0445\u0443\u0435\u043c", "\u0445\u0443\u0435\u043d\u0447", "\u0445\u0443\u0435\u043d\u044b\u0448", "\u0445\u0443\u0435\u043d\u044c\u043a\u0438\u0439", "\u0445\u0443\u0435\u043f\u043b\u0435\u0442", "\u0445\u0443\u0435\u043f\u043b\u0451\u0442", "\u0445\u0443\u0435\u043f\u0440\u043e\u043c\u044b\u0448\u043b\u0435\u043d\u043d\u0438\u043a", "\u0445\u0443\u0435\u0440\u0438\u043a", "\u0445\u0443\u0435\u0440\u044b\u043b\u043e", "\u0445\u0443\u0435\u0441\u043e\u0441", "\u0445\u0443\u0435\u0441\u043e\u0441\u043a\u0430", "\u0445\u0443\u0435\u0442\u0430", "\u0445\u0443\u0435\u0442\u0435\u043d\u044c", "\u0445\u0443\u0435\u044e", "\u0445\u0443\u0438", "\u0445\u0443\u0439", "\u0445\u0443\u0439\u043a\u043e\u043c", "\u0445\u0443\u0439\u043b\u043e", "\u0445\u0443\u0439\u043d\u044f", "\u0445\u0443\u0439\u0440\u0438\u043a", "\u0445\u0443\u0438\u0449\u0435", "\u0445\u0443\u043b\u044f", "\u0445\u0443\u044e", "\u0445\u0443\u044e\u043b", "\u0445\u0443\u044f", "\u0445\u0443\u044f\u043a", "\u0445\u0443\u044f\u043a\u0430\u0442\u044c", "\u0445\u0443\u044f\u043a\u043d\u0443\u0442\u044c", "\u0445\u0443\u044f\u0440\u0430", "\u0445\u0443\u044f\u0441\u0435", "\u0445\u0443\u044f\u0447\u0438\u0442\u044c", "\u0446\u0435\u043b\u043a\u0430", "\u0447\u043c\u043e", "\u0447\u043c\u043e\u0448\u043d\u0438\u043a", "\u0447\u043c\u044b\u0440\u044c", "\u0448\u0430\u043b\u0430\u0432\u0430", "\u0448\u0430\u043b\u0430\u0432\u043e\u0439", "\u0448\u0430\u0440\u0430\u0451\u0431\u0438\u0442\u044c\u0441\u044f", "\u0448\u043b\u044e\u0445\u0430", "\u0448\u043b\u044e\u0445\u043e\u0439", "\u0448\u043b\u044e\u0448\u043a\u0430", "fuck", "fack", "suck", "shit", "piss", "ass", "ashole", "bitch", "bastard", "cunt" };
    }
}
