package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetSpawnCommand implements CommandExecutor {

    public SetSpawnCommand() {
        Bukkit.getPluginCommand("setspawn").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /setspawn...");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be player to use this command!");
            return true;
        }

        if (!player.hasPermission("saga.setspawn")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
            return true;
        }

        if (args.length == 0) {
            final File file = new File(Survival.getInstance().getDataFolder() + "/spawn_" + player.getWorld().getName() + ".yml");
            final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set("Spawn.Welt", (Object) player.getLocation().getWorld().getName());
            cfg.set("Spawn.X", (Object) player.getLocation().getX());
            cfg.set("Spawn.Y", (Object) player.getLocation().getY());
            cfg.set("Spawn.Z", (Object) player.getLocation().getZ());
            cfg.set("Spawn.Yaw", (Object) player.getLocation().getYaw());
            cfg.set("Spawn.Pitch", (Object) player.getLocation().getPitch());
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(Color.translate("&a已成功儲存" + player.getWorld().getName() + "重生點!"));
        } else if (args[0].equalsIgnoreCase("join")) {
            final File file = new File(Survival.getInstance().getDataFolder() + "/spawn_world.yml");
            final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set("Spawn.Welt", (Object) player.getLocation().getWorld().getName());
            cfg.set("Spawn.X", (Object) player.getLocation().getX());
            cfg.set("Spawn.Y", (Object) player.getLocation().getY());
            cfg.set("Spawn.Z", (Object) player.getLocation().getZ());
            cfg.set("Spawn.Yaw", (Object) player.getLocation().getYaw());
            cfg.set("Spawn.Pitch", (Object) player.getLocation().getPitch());
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(Color.translate("&a已成功儲存首次加入傳送的重生點!"));
        }

        return true;
    }
}
