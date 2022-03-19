package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Cooldown;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SCSCommand implements CommandExecutor {

	public static HashMap<Player, Cooldown> cooldown = new HashMap<>();

	public SCSCommand() {
		Bukkit.getPluginCommand("scs").setExecutor(this);
		Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /scs...");
	}

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be player to use this command!");
			return true;
		}

//        if (player.getWorld().equals("world")) {
//            player.sendMessage(ChatColor.RED + "你不能在主世界使用這個指令!");
//            return true;
//        }

		if (cooldown.get(player) != null) {
			if (!cooldown.get(player).hasExpired()) {
				player.sendMessage(Color.translate("&4&l錯誤 &8» &c請等待 &6" + cooldown.get(player).getMiliSecondsLeft() + " &c秒再次使用指令!"));
				return true;
			}
		}

		String x = "X: " + (int) player.getLocation().getX() + " ";
		String y = "Y: " + (int) player.getLocation().getY() + " ";
		String z = "Z: " + (int) player.getLocation().getZ();

		player.chat(Color.translate("&f" + x + y + z));
//        cooldown.put(player, new Cooldown(3));


		return true;
	}
}
