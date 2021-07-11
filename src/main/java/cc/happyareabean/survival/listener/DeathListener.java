package cc.happyareabean.survival.listener;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.File;

public class DeathListener implements Listener {

    public DeathListener(Survival plugin) {
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載Listener... [DeathListener]");
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        EntityDamageEvent.DamageCause d = e.getEntity().getLastDamageCause().getCause();

        if (e.getEntity().getKiller() != null) {
            if (d.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                Bukkit.broadcastMessage(Color.translate("&c" + player.getName() + " &7被 &a" + killer.getName() + " &7射殺"));
                e.setDeathMessage(null);
            } else {
                Bukkit.broadcastMessage(Color.translate("&c" + player.getName() + " &7被 &a" + killer.getName() + " &7擊殺"));
                e.setDeathMessage(null);
            }
        }
        if (d.equals(EntityDamageEvent.DamageCause.FALL)) {
            Bukkit.broadcastMessage(Color.translate("&c" + player.getName() + " &7從很高的地方掉下來了..."));
            e.setDeathMessage(null);
        }else if (d.equals(EntityDamageEvent.DamageCause.VOID)) {
            Bukkit.broadcastMessage(Color.translate("&c" + player.getName() + " &7被虛空吞噬了"));
            e.setDeathMessage(null);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        final File file = new File(Survival.getInstance().getDataFolder() + "/spawn_spawn.yml");
        final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (player.getBedSpawnLocation() != null) {
            return;
        }

        if (file.exists()) {
            final World w = Bukkit.getWorld(cfg.getString("Spawn.Welt"));
            final double x = cfg.getDouble("Spawn.X");
            final double y = cfg.getDouble("Spawn.Y");
            final double z = cfg.getDouble("Spawn.Z");
            final float yaw = (float)cfg.getDouble("Spawn.Yaw");
            final float pitch = (float)cfg.getDouble("Spawn.Pitch");
            final Location loc = new Location(w, x, y, z, yaw, pitch);
            e.setRespawnLocation(loc);
        }
    }
}
