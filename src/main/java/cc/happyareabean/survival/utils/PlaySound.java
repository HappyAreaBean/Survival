package cc.happyareabean.survival.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlaySound {

    public static void play(Player player) {
        if (Bukkit.getVersion().contains("1.12.2") || Bukkit.getVersion().contains("1.11.2")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        } else {
            player.playSound(player.getLocation(), Sound.valueOf("NOTE_PLING"), 1, 1);
        }
    }
    public static void playanvil(Player player) {
        if (Bukkit.getVersion().contains("1.12.2") || Bukkit.getVersion().contains("1.11.2")) {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
        } else {
            player.playSound(player.getLocation(), Sound.valueOf("ANVIL_PLACE"), 1, 1);
        }
    }
}
