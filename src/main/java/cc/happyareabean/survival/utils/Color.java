package cc.happyareabean.survival.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Color {
   public static String translate(String value) {
      return ChatColor.translateAlternateColorCodes('&', value);
   }

    public static String translate(ArrayList<String> help) {
       for (String list : help) {
           return ChatColor.translateAlternateColorCodes('&', list);
       }
       return null;
    }
}

