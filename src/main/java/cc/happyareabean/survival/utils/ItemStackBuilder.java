package cc.happyareabean.survival.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemStackBuilder {

    public static ItemStack create(Material material, String name, String... lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta stackmeta = stack.getItemMeta();
        stackmeta.setDisplayName(Color.translate(name));

        ArrayList<String> lorelist = new ArrayList<>();
        for (String lores : lore) {
            lorelist.add(Color.translate(lores));
            stackmeta.setLore(lorelist);
        }
        stack.setItemMeta(stackmeta);

        return stack;
    }
}
