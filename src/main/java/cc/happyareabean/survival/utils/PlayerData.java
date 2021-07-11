package cc.happyareabean.survival.utils;

import cc.happyareabean.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerData {

    public void create(Player player) {

        //Player data folder
        File folder = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString());
        File homefolder = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home");
        if (!folder.exists()) {
            folder.mkdir();
            Bukkit.getConsoleSender().sendMessage(Lang.prefix + "Created " + player.getUniqueId() + " Data Folder");
        }
        if (!homefolder.exists()) {
            homefolder.mkdir();
        }

//        //Player settings file
//        File cfile = new File(SagaSurvival.getInstance().getDataFolder(), "Player" + File.separator + player.getUniqueId().toString() + File.separator + "settings.yml");
//        FileConfiguration data = YamlConfiguration.loadConfiguration(cfile);
//        data.set("scoreboard", true);
//        data.set("adminscoreboard", false);
//        data.set("public-chat", true);
//        if (!cfile.exists()) {
//            try {
//                data.save(cfile);
//                Bukkit.getConsoleSender().sendMessage(Lang.prefix + "Created " + player.getName() + " (" + player.getUniqueId() + ") settings file.");
//            } catch (IOException e) {
//                e.printStackTrace();
//                Bukkit.getConsoleSender().sendMessage(Lang.prefix + ChatColor.RED + "Error saving " + player.getName() + " (" + player.getUniqueId() + ") settings file!");
//            }
//        }
    }

    public static String getString(Player player, String vaule) {
        File cfile = new File(Survival.getInstance().getDataFolder(), "Player" + File.separator + player.getUniqueId().toString() + File.separator + "settings.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(cfile);
        return data.getString(vaule);
    }

    public static Integer getInteger(Player player, String vaule) {
        File cfile = new File(Survival.getInstance().getDataFolder(), "Player" + File.separator + player.getUniqueId().toString() + File.separator + "settings.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(cfile);
        return data.getInt(vaule);
    }

    public static Double getDouble(Player player, String vaule) {
        File cfile = new File(Survival.getInstance().getDataFolder(), "Player" + File.separator + player.getUniqueId().toString() + File.separator + "settings.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(cfile);
        return data.getDouble(vaule);
    }

    public static Boolean getBoolean(Player player, String vaule) {
        File cfile = new File(Survival.getInstance().getDataFolder(), "Player" + File.separator + player.getUniqueId().toString() + File.separator + "settings.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(cfile);
        return data.getBoolean(vaule);
    }

    public static void set(Player player, String path, Boolean vaule) {
        File cfile = new File(Survival.getInstance().getDataFolder(), "Player" + File.separator + player.getUniqueId().toString() + File.separator + "settings.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(cfile);
        data.set(path, vaule);
        save(data, cfile);
    }

    public static void save(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch(Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(Lang.prefix + ChatColor.RED + "Error saving " + file.getName() + "!");
        }
    }
}
