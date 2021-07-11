package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class HelpCommand implements CommandExecutor {

    private File help = new File(Survival.getInstance().getDataFolder() + "/Help.yml");
    private YamlConfiguration helpcfg = YamlConfiguration.loadConfiguration(help);

    public HelpCommand() {
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /support...");
        Bukkit.getPluginCommand("support").setExecutor(this);
//        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /rules...");
//        Bukkit.getPluginCommand("rules").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("support")) {
            for (String list : helpcfg.getStringList("Support")) {
                sender.sendMessage(Color.translate(list));
            }
            return true;
        }
        else if (label.equalsIgnoreCase("rules")) {
            for (String list : helpcfg.getStringList("Rules")) {
                sender.sendMessage(Color.translate(list));
            }
            return true;
        }
        return true;
    }
}
