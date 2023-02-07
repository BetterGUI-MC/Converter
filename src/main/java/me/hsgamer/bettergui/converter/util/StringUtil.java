package me.hsgamer.bettergui.converter.util;

import org.bukkit.ChatColor;

public class StringUtil {
    private StringUtil() {
        // EMPTY
    }

    public static String reverseColor(String string) {
        return string.replace(String.valueOf(ChatColor.COLOR_CHAR), "&");
    }
}
