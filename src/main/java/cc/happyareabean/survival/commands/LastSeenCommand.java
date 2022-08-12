package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class LastSeenCommand implements CommandExecutor {

	public LastSeenCommand() {
		Bukkit.getPluginCommand("lastseen").setExecutor(this);
		Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /lastseen...");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss").withZone(ZoneId.systemDefault());

		if (args.length == 0) {
			sender.sendMessage(Color.translate("&cUsage: /lastseen <Player/UUID>"));
		} else {
			OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(args[0]));
			sender.sendMessage(Color.translate("&e" + player.getName() + "&a last joined: &e" + formatter.format(Instant.ofEpochMilli(player.getLastPlayed()))));
		}

		return true;
	}
}
