package cc.happyareabean.survival.listener;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldChange implements Listener {

    public WorldChange(Survival plugin) {
        if (Bukkit.getPluginManager().isPluginEnabled("Multiverse-Core")) {
            Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載Listener... [WorldChangeListener]");
            Bukkit.getPluginManager().registerEvents(this, plugin);
        } else {
            Bukkit.getConsoleSender().sendMessage(Lang.prefix + "因為 Multiverse-Core 並沒有安裝在此伺服器, 所以並不會加載 [WorldChangeListener]");
        }
    }

    @EventHandler
    public void onChange(PlayerChangedWorldEvent e) {
        final Player player = e.getPlayer();

        new BukkitRunnable() {
            public void run() {
                player.sendTitle("", dopapi(player, "&a&l%multiverse_world_alias%"), 1, 50, 10);
            }
        }.runTaskLater(Survival.getInstance(), 20);
    }

    public String dopapi(Player player, String vaule) {
        return Color.translate(PlaceholderAPI.setPlaceholders(player, vaule));
    }
}
