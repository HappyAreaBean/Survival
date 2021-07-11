package cc.happyareabean.survival.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FillEmptySlots {
    public FillEmptySlots(Inventory inv) {
		// Glass
		ItemStack Glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta GlassMeta = Glass.getItemMeta();
		
		GlassMeta.setDisplayName("§f");
		
		Glass.setItemMeta(GlassMeta);
		
        for (int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                inv.setItem(i, Glass);
            }
        }
    }
}
