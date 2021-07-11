package cc.happyareabean.survival.tasks;

import cc.happyareabean.survival.commands.SCSCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SCSRunnable implements Runnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (SCSCommand.cooldown.get(player) != null) {
                final long time = SCSCommand.cooldown.get(player).getRemaining();
                if (time < 0) {
                    SCSCommand.cooldown.replace(player, null);
                }
            }
        }
    }
}
