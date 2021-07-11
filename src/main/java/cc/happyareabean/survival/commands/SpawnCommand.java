package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class SpawnCommand implements CommandExecutor {

    public SpawnCommand() {
        Bukkit.getPluginCommand("spawn").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /spawn...");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be player to use this command!");
            return true;
        }

        if (args.length == 0) {
            final File file = new File(Survival.getInstance().getDataFolder() + "/spawn_spawn.yml");
            final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            final World w = Bukkit.getWorld(cfg.getString("Spawn.Welt"));
            final double x = cfg.getDouble("Spawn.X");
            final double y = cfg.getDouble("Spawn.Y");
            final double z = cfg.getDouble("Spawn.Z");
            final float yaw = (float) cfg.getDouble("Spawn.Yaw");
            final float pitch = (float) cfg.getDouble("Spawn.Pitch");
            final Location loc = new Location(w, x, y, z, yaw, pitch);
            player.teleport(loc);
            player.sendMessage(Color.translate("&a你已傳送到重生點! &7"));

            return true;
        }
        if (!(args.length == 0)) {
            if (args[0].equalsIgnoreCase("world")) {
                final File file = new File(Survival.getInstance().getDataFolder() + "/spawn_world.yml");
                final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                final World w = Bukkit.getWorld(cfg.getString("Spawn.Welt"));
                final double x = cfg.getDouble("Spawn.X");
                final double y = cfg.getDouble("Spawn.Y");
                final double z = cfg.getDouble("Spawn.Z");
                final float yaw = (float) cfg.getDouble("Spawn.Yaw");
                final float pitch = (float) cfg.getDouble("Spawn.Pitch");
                final Location loc = new Location(w, x, y, z, yaw, pitch);
                player.teleport(loc);
                player.sendMessage(Color.translate("&a你已傳送到主世界!"));
                player.sendMessage(Color.translate("&c&l請注意! &e每個世界的背包並不共通! 請先選擇好世界遊玩!"));

                return true;
            }
        }

        return true;
    }
}
