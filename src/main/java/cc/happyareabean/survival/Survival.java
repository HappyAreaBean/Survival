package cc.happyareabean.survival;

import cc.happyareabean.survival.commands.EnderChestCommand;
import cc.happyareabean.survival.commands.HelpCommand;
import cc.happyareabean.survival.commands.HomeCommand;
import cc.happyareabean.survival.commands.InvseeCommand;
import cc.happyareabean.survival.commands.PingCommand;
import cc.happyareabean.survival.commands.SCSCommand;
import cc.happyareabean.survival.commands.SetSpawnCommand;
import cc.happyareabean.survival.commands.SpawnCommand;
import cc.happyareabean.survival.commands.SurvivalCommand;
import cc.happyareabean.survival.commands.TpaCommand;
import cc.happyareabean.survival.tasks.SCSRunnable;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Survival extends JavaPlugin {

	@Getter
	private static Survival instance;

	@Override
	public void onEnable() {
		System.out.println();
		instance = this;
		this.startupmsg();

		this.genfile("Help.yml");
		this.genfile("config.yml");

		this.register();
	}

	public void register() {

		//加載MySQL
//        long mysqlload = System.currentTimeMillis();
//        this.mysqlsetup();
//        long mysqlfinished = System.currentTimeMillis();
//        Bukkit.getConsoleSender().sendMessage(Color.translate(Lang.prefix + "已成功加載所有MySQL &7(花費" + (mysqlfinished - mysqlload) + "ms)"));
//        Bukkit.getConsoleSender().sendMessage(Lang.prefix);

		//加載Listener
		long eventload = System.currentTimeMillis();
//        new JoinListener(this);
//        new DeathListener(this);
//        new WorldChange(this);
//        new MobListener(this);
//        new CraftListener(this);
		long eventfinished = System.currentTimeMillis();
		log("已成功加載所有Listener &7(花費" + (eventfinished - eventload) + "ms)");
		log();

		//加載指令
		long cmdload = System.currentTimeMillis();
		if (getConfig().getBoolean("modules.spawn")) {
			new SetSpawnCommand();
			new SpawnCommand();
		}

		if (getConfig().getBoolean("modules.help"))
			new HelpCommand();

		if (getConfig().getBoolean("modules.tpa"))
			new TpaCommand();

		if (getConfig().getBoolean("modules.home"))
			new HomeCommand(this);

		new PingCommand();
		new SCSCommand();
		new EnderChestCommand();
		new InvseeCommand();
		new SurvivalCommand();
		long cmdfinished = System.currentTimeMillis();
		log("已成功加載所有指令 &7(花費" + (cmdfinished - cmdload) + "ms)");

		// Load Task
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new SCSRunnable(), 100L, 2L); // SCSCommand Task.

		File playerfolder = new File(this.getDataFolder() + "/Player");
		if (!playerfolder.exists()) {
			playerfolder.mkdir();
			log("Created player data folder.");
		}
	}

	public void startupmsg() {
		log();
		log("伺服器版本: &a" + Bukkit.getBukkitVersion());
		log();
		log(getDescription().getName() + " 已成功啟用!");
		log("作者: &aHappyAreaBean");
		log();
	}

	public void genfile(String file) {
		File files = new File(this.getDataFolder() + "/" + file);
		if (!files.exists()) {
			this.saveResource(file, false);
		} else {
			YamlConfiguration.loadConfiguration(files);
		}
	}

	public void log(String message) {
		Bukkit.getConsoleSender().sendMessage(Color.translate(Lang.prefix + message));
	}

	public void log() {
		Bukkit.getConsoleSender().sendMessage(Color.translate(Lang.prefix));
	}
}
