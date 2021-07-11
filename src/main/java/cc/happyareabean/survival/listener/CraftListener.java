package cc.happyareabean.survival.listener;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class CraftListener implements Listener {

    public CraftListener(Survival plugin) {
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載Listener... [CraftListener]");
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
//        if (e.getRecipe() != null && e.getRecipe().getResult() != null) {
//            Material itemType = e.getRecipe().getResult().getType();
//            if (itemType == Material.HOPPER) {
//                e.getInventory().setResult(new ItemStack(Material.AIR));
//            }
//        }
    }
}
