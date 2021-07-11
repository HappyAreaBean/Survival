package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashMap;

public class TpaCommand implements CommandExecutor {

    HashMap<Player, Player> tpa = new HashMap<Player, Player>();
    HashMap<Player, Player> tpacancel = new HashMap<Player, Player>();

    public TpaCommand() {
        Bukkit.getPluginCommand("tpa").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /tpa...");
        Bukkit.getPluginCommand("tpaccept").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /tpaccept...");
        Bukkit.getPluginCommand("tpdeny").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /tpdeny...");
        Bukkit.getPluginCommand("tpcancel").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /tpcancel...");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (!player.hasPermission("survival.tpa")) {
                player.sendMessage(Color.translate("&4&l錯誤 &8» &c你沒有權限使用! &e請到商店購買1個傳送卷軸!"));
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(Color.translate("&4&l錯誤 &8» &c你必須指定一位玩家!"));
                player.sendMessage(Color.translate("&c用法: /tpa <玩家>"));
                return true;
            }
            if (args.length == 1) {
                Player target = (Player) Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Color.translate("&4&l錯誤 &8» &c未知的玩家!"));
                    return true;
                }
                if (target != null) {
                    if (target == player) {
                        player.sendMessage(Color.translate("&4&l錯誤 &8» &c你無法傳送一個傳送請求給自己!"));
                        return true;
                    }
                    if (!tpa.containsKey(target)) {
                        tpa.put(target, player);
                        tpacancel.put(player, target);
                        player.sendMessage("");
                        player.sendMessage(Color.translate("&e你傳送了一個傳送請求給 &b" + target.getName()));
                        player.sendMessage(Color.translate("&c此傳送請求將會在 &61分鐘 &c後到期"));
                        player.sendMessage("");
                        TextComponent messagecancel = new TextComponent(ChatColor.YELLOW + "點擊取消傳送請求 -" + ChatColor.RED + ChatColor.BOLD + " [取消]");
                        messagecancel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpcancel"));
                        messagecancel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.AQUA + "點擊取消傳送傳送請求").create()));
                        player.spigot().sendMessage(messagecancel);
                        player.sendMessage("");
                        target.sendMessage("");
                        target.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " 傳送了一個傳送請求給你!");
                        TextComponent message1 = new TextComponent(ChatColor.YELLOW + "點擊接受傳送 -" + ChatColor.GREEN + ChatColor.BOLD + " [接受]");
                        TextComponent message2 = new TextComponent(ChatColor.YELLOW + "點擊拒絕傳送 -" + ChatColor.RED + ChatColor.BOLD + " [拒絕]");
                        message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                        message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.AQUA + "點擊接受傳送").create()));
                        message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
                        message2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.AQUA + "點擊拒絕傳送").create()));
                        target.spigot().sendMessage(message1);
                        target.spigot().sendMessage(message2);
                        target.sendMessage(Color.translate("&c此傳送請求將會在 &61分鐘 &c後到期"));
                        target.sendMessage("");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!tpa.containsKey(target)) {
                                    cancel();
                                    return;
                                }
                                target.sendMessage(Color.translate("&7來自 &6" + player.getName() + " &7的傳送請求已經過期!"));
                                tpa.get(target).sendMessage(Color.translate("&e你對 &b" + target.getName() + " &e傳送的請求已過期!"));
                                tpa.remove(target);
                                tpacancel.remove(target);
                            }
                        }.runTaskLater(Survival.getInstance(), 20 * 60);

                        return true;
                    } else {
                        player.sendMessage(Color.translate("&4&l錯誤 &8» &c你已經傳送了傳送請求給 " + target.getName() + " 了!"));
                        return true;
                    }
                }
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("tpaccept")) {
            if (tpa.containsKey(player)) {
                tpa.get(player).sendMessage(Color.translate(ChatColor.GREEN + player.getName() + " &e已接受了你的請求"));
                tpa.get(player).sendMessage(ChatColor.YELLOW + "已將你傳送到了 " + ChatColor.GREEN + player.getName());
                player.sendMessage(ChatColor.GREEN + tpa.get(player).getName() + ChatColor.YELLOW + " 已傳送到你的位置!");
                tpa.get(player).teleport(player);
                tpa.remove(player);
                return true;
            }
            player.sendMessage(Color.translate("&c你目前沒有待處理的傳送請求!"));
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("tpdeny")) {
            if(tpa.get(player) != null) {
                tpa.get(player).sendMessage(ChatColor.YELLOW + "你的傳送請求已被拒絕!");
                player.sendMessage(ChatColor.YELLOW + "你已拒絕 " + tpa.get(player).getName() + " 的傳送請求");
                tpa.remove(player);
                return true;
            }
            player.sendMessage(Color.translate("&c你目前沒有待處理的傳送請求!"));
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("tpcancel")) {
            if(tpacancel.containsKey(player)) {
                player.sendMessage(ChatColor.YELLOW + "你已取消傳送請求");
                tpacancel.remove(tpa.get(player));
                tpa.remove(tpacancel.get(player));
                return true;
            }
            player.sendMessage(Color.translate("&c你目前沒有待處理的傳送請求!"));
            return true;
        }
        return true;
    }
}
