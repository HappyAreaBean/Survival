package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {

    public InvseeCommand() {
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /invsee...");
        Bukkit.getPluginCommand("invsee").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.translate("&c這個指令只能讓玩家使用!"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("survival.see.inv")) {
            player.sendMessage(Color.translate("&c你沒有權限使用此指令!"));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Color.translate("&c缺少參數!"));
            player.sendMessage(Color.translate("&c/invsee <player>"));
        } else if (args.length == 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target.getPlayer() != null) {
                player.openInventory(target.getPlayer().getInventory());
            } else {
                player.sendMessage(Color.translate("&c你所指定的 " + args[0] + " 並不存在!"));
            }
        }


        return true;
    }
}
