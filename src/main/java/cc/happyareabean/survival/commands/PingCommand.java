package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

	public PingCommand() {
		Bukkit.getPluginCommand("ping").setExecutor(this);
		Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /ping...");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be player to use this command!");
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(Color.translate("&a&l綠豆SMP › &f你的延遲為 &a" + player.getPing() + "ms"));
		} else {
			if (Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage(Color.translate("&a&l綠豆SMP › &c你所輸入的玩家並不存在!"));
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			player.sendMessage(Color.translate("&a&l綠豆SMP › &a" + target.getName() + " &f的延遲為 &a" + target.getPing() + "ms"));
		}

		return true;
	}
}
