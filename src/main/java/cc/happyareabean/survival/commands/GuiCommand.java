package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand implements CommandExecutor {

    public GuiCommand() {
        Bukkit.getPluginCommand("settings").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /settings...");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be player to use this command!");
            return true;
        }

        return true;
    }
}
