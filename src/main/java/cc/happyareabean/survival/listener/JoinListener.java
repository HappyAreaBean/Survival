package cc.happyareabean.survival.listener;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import cc.happyareabean.survival.utils.PlayerData;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class JoinListener implements Listener {

    public JoinListener(Survival plugin) {
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載Listener... [JoinListener]");
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        final File file = new File(Survival.getInstance().getDataFolder() + "/spawn_spawn.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (file.exists()) {
            if (!player.hasPlayedBefore()) {
                Bukkit.broadcastMessage(Color.translate("&8[&a+&8] &d讓我們熱烈歡迎 " + dopapi(player, "%luckperms_prefix%") + player.getName() + " &d第一次加入伺服器"));
                final World w = Bukkit.getWorld(cfg.getString("Spawn.Welt"));
                final double x = cfg.getDouble("Spawn.X");
                final double y = cfg.getDouble("Spawn.Y");
                final double z = cfg.getDouble("Spawn.Z");
                final float yaw = (float)cfg.getDouble("Spawn.Yaw");
                final float pitch = (float)cfg.getDouble("Spawn.Pitch");
                final Location loc = new Location(w, x, y, z, yaw, pitch);
                Bukkit.getScheduler().runTaskLater(Survival.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        player.teleport(loc);
                    }
                }, 20);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        new PlayerData().create(player);
    }

    public String dopapi(Player player, String vaule) {
        return Color.translate(PlaceholderAPI.setPlaceholders(player, vaule));
    }

    @EventHandler
    public void onJump(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (player.getWorld() == Bukkit.getWorld("spawn")) {
            if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.FARMLAND) {
                e.setCancelled(true);
            }
        }
    }
}
