package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SurvivalCommand implements CommandExecutor {

	public SurvivalCommand() {
		Bukkit.getPluginCommand("survival").setExecutor(this);
		Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /survival...");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		sender.sendMessage(Color.translate("&8&m-------------------------------"));
		sender.sendMessage(Color.translate("&f這個伺服器目前正在運行 &a&lSurvival"));
		sender.sendMessage(Color.translate("&fAPI版本: &a&l" + Survival.getInstance().getDescription().getAPIVersion()));
		sender.sendMessage(Color.translate("&f版本: &a&l" + Survival.getInstance().getDescription().getVersion()));
		sender.sendMessage(Color.translate("&f作者: &a&l" + String.join(", ", Survival.getInstance().getDescription().getAuthors())));
		sender.sendMessage(Color.translate("&8&m-------------------------------"));
		return true;
	}
}
