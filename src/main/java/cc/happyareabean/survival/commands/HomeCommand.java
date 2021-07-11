package cc.happyareabean.survival.commands;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Color;
import cc.happyareabean.survival.utils.Lang;
import cc.happyareabean.survival.utils.AliasReplace;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeCommand implements CommandExecutor, Listener {

    static ArrayList<String> arrayList = new ArrayList<>();
    Survival plugin;

    public HomeCommand(Survival plugin) {
        Bukkit.getPluginCommand("home").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /home...");
        Bukkit.getPluginCommand("hometp").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /hometp...");
        Bukkit.getPluginCommand("homelist").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /homelist...");
        Bukkit.getPluginCommand("sethome").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /sethome...");
        Bukkit.getPluginCommand("delhome").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載指令 /delhome...");
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("hometp")) {
            if (!player.getOpenInventory().getTitle().equalsIgnoreCase("家傳送點")) {
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(Color.translate("&a&l家 &8» &a家傳送點列表:" + " &7(使用 &e/home &7開啟GUI介面)"));

                arrayList.clear();

                File homefolder = (new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home"));

                if (homefolder.listFiles().length == 0) {
                    player.sendMessage("你目前並沒有任何家的設置!");
                } else if (homefolder.exists()) {
                    File folder = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home");
                    File[] listOfFiles = folder.listFiles();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        arrayList.add(listOfFiles[i].getName());
                    }

                    String list = String.join(", ", arrayList);
                    String replace = list.replace(".yml", "");
                    player.sendMessage(replace);

                    return true;
                }
                return true;
            }
            File warp = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home/" + args[0] + ".yml");
            YamlConfiguration warpcfg = YamlConfiguration.loadConfiguration(warp);

            if (warp.exists()) {
                player.sendMessage(Color.translate("&a&l家 &8» &a已把你傳送到 &e" + args[0]));

                final World w = Bukkit.getWorld(warpcfg.getString("location.World"));
                final double x = warpcfg.getDouble("location.X");
                final double y = warpcfg.getDouble("location.Y");
                final double z = warpcfg.getDouble("location.Z");
                final float yaw = (float) warpcfg.getDouble("location.Yaw");
                final float pitch = (float) warpcfg.getDouble("location.Pitch");
                final Location loc = new Location(w, x, y, z, yaw, pitch);
                player.teleport(loc);

                return true;
            } else if (!warp.exists()) {
                player.sendMessage(Color.translate("&4&l錯誤 &8» &c你指定的家傳送點並不存在! 請使用 &e/sethome " + args[0] + " &c創建!"));
            }
        }
        if (label.equalsIgnoreCase("homelist") || label.equalsIgnoreCase("home")) {
            ArrayList<String> homelist = new ArrayList<>();
            Integer size = 9;

            homelist.clear();

            File homefolder = (new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home"));

            if(homefolder.listFiles().length > 9) size = 18;
            if(homefolder.listFiles().length > 18) size = 27;
            if(homefolder.listFiles().length > 27) size = 36;
            if(homefolder.listFiles().length > 36) size = 45;
            if(homefolder.listFiles().length > 45) size = 54;

            Inventory homelistinv = Bukkit.createInventory(null, size, "家傳送點");

            if (homefolder.listFiles().length == 0) {


                ItemStack garss = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta garssmeta = garss.getItemMeta();
                garssmeta.setDisplayName(Color.translate("&c你沒有設定任何的傳送點"));
                ArrayList<String> lores = new ArrayList<>();
                lores.add(Color.translate("&7使用 &e/sethome <名字> &7設定一個吧!"));
                garssmeta.setLore(lores);
                garss.setItemMeta(garssmeta);

                homelistinv.setItem(4, garss);
                player.openInventory(homelistinv);
            } else if (homefolder.exists() && homefolder.listFiles().length > 0) {

                File folder = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home");
                File[] listOfFiles = folder.listFiles();

                for (int i = 0; i < listOfFiles.length; i++) {
                    homelist.add(listOfFiles[i].getName());

                    String list = String.join(", ", homelist);
                    String replace = list.replace(".yml", "");
                }

                for ( String m : homelist) {
                    String re = m.replace(".yml", "");
                    File cfgfile = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home/" + re + ".yml");
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgfile);

                    ItemStack paper = new ItemStack(Material.valueOf(cfg.getString("icon")));
                    ItemMeta papermeta = paper.getItemMeta();
                    papermeta.setDisplayName(Color.translate("&e&l" + re));
                    ArrayList<String> paperlore = new ArrayList<>();
                    paperlore.add("");
                    paperlore.add(Color.translate("&e世界: &f" + new AliasReplace().getAlias(cfg.getString("location.World"))));
                    paperlore.add(Color.translate("&eX: &f" + cfg.getInt("location.X")));
                    paperlore.add(Color.translate("&eY: &f" + cfg.getInt("location.Y")));
                    paperlore.add(Color.translate("&eZ: &f" + cfg.getInt("location.Z")));
                    paperlore.add("");
                    paperlore.add(Color.translate("&e» &a&l點擊&e傳送 &c&l中鍵&e刪除"));
                    paperlore.add(Color.translate("&e» &d&lShift+左鍵&e重置圖示 &b&lShift+右鍵&e使用你手上的物品更換物品圖示"));
                    papermeta.setLore(paperlore);
                    paper.setItemMeta(papermeta);
                    homelistinv.addItem(paper);
                    player.openInventory(homelistinv);
                }

                return true;
            }
            return true;
        }
        if (label.equalsIgnoreCase("sethome")) {
            if (args.length == 0) {
                player.sendMessage(Color.translate("&4&l錯誤 &8» &c請輸入家名字!"));
                return true;
            } else {
                if (!args[0].matches("^[-a-zA-Z0-9._]+")) {
                    player.sendMessage(Color.translate("&4&l錯誤 &8» &c你只可以使用 a-z A-Z 0-9 ._- 作為你的家名字"));
                    return true;
                }
                File folder = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home");
                File[] listOfFiles = folder.listFiles();

                File warp = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home/" + args[0] + ".yml");
                YamlConfiguration warpcfg = YamlConfiguration.loadConfiguration(warp);

                // Dynamic team sizes with permissions
//                Bukkit.broadcastMessage(getMax(player) + "");

                if (listOfFiles.length == getMax(player) && getMax(player) != -1) {
                    player.sendMessage(Color.translate("&4&l錯誤 &8» &c你已經到達了可以設置家的最大數量! (" + listOfFiles.length + ")"));
                    return true;
                }

                warpcfg.set("location.World", player.getWorld().getName());
                warpcfg.set("location.X", player.getLocation().getX());
                warpcfg.set("location.Y", player.getLocation().getY());
                warpcfg.set("location.Z", player.getLocation().getZ());
                warpcfg.set("location.Yaw", player.getLocation().getYaw());
                warpcfg.set("location.Pitch", player.getLocation().getPitch());
                warpcfg.set("icon", "PAPER");


                try {
                    player.sendMessage(Color.translate("&a&l完成 &8» &a家已設置: &e" + args[0]));
                    warpcfg.save(warp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (label.equalsIgnoreCase("delhome")) {
            if (args.length == 0) {
                player.sendMessage(Color.translate("&4&l錯誤 &8» &c請輸入家名字!"));
                return true;
            }
            File warp = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home/" + args[0] + ".yml");
            YamlConfiguration warpcfg = YamlConfiguration.loadConfiguration(warp);

            if (warp.exists()) {
                player.sendMessage(Color.translate("&a&l完成 &8» &a家已刪除: &e" + args[0]));
                warp.delete();
            } else {
                player.sendMessage(Color.translate("&4&l錯誤 &8» &c這個家傳送點並不存在!"));
                arrayList.clear();

                File homefolder = (new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home"));

                if (homefolder.listFiles().length == 0) {
                    player.sendMessage(Color.translate("&4&l錯誤 &8» &c你沒有設定任何的傳送點 &c使用 &e/sethome <名字> &c設定一個吧!"));
                } else if (homefolder.listFiles().length > 0) {
                    File folder = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home");
                    File[] listOfFiles = folder.listFiles();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        arrayList.add(listOfFiles[i].getName());
                    }

                    String list = String.join(", ", arrayList);
                    String replace = list.replace(".yml", "");
                    player.sendMessage(Color.translate("&a家傳送點列表: &f" + replace));

                    return true;
                }
            }
        }
        return true;
    }

    public int getMax(Permissible permissible) {
        if (permissible.isOp())
            return -1;

        final AtomicInteger max = new AtomicInteger();

        permissible.getEffectivePermissions().stream().map(PermissionAttachmentInfo::getPermission).map(String::toLowerCase).filter(value -> value.startsWith("survival.home.max.")).map(value -> value.replace("survival.home.max.", "")).forEach(value -> {
            if (value.equalsIgnoreCase("*")) {
                max.set(-1);
                return;
            }

            if (max.get() == -1)
                return;

            try {
                int amount = Integer.parseInt(value);

                if (amount > max.get())
                    max.set(amount);
            } catch (NumberFormatException ignored) {
            }
        });

        return max.get();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        ArrayList<String> click = new ArrayList<>();

        File folder = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home");
        File[] listOfFiles = folder.listFiles();

        if (item != null) {
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasDisplayName()) {
                    if (e.getView().getTitle().equalsIgnoreCase("家傳送點")) {
                        e.setCancelled(true);
                    }

                    if (e.getView().getTitle().equalsIgnoreCase("家傳送點")) {
                        e.setCancelled(true);
                        for (int i = 0; i < listOfFiles.length; i++) {
                            click.add(listOfFiles[i].getName());
                        }
                        for (String t : click) {
                            String replace = t.replace(".yml", "");

                            if (e.getClick() == ClickType.LEFT) {
                                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate("&e&l" + replace))) {
                                    player.chat("/hometp " + replace);
                                }
                            } else if (e.getClick() == ClickType.MIDDLE) {
                                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate("&e&l" + replace))) {
                                    player.chat("/delhome " + replace);
                                    player.performCommand("homelist");
                                }
                            } else if (e.getClick() == ClickType.SHIFT_RIGHT) {
                                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate("&e&l" + replace))) {
                                    if (player.getInventory().getItemInMainHand().getType().isAir()) {
                                        e.setCancelled(true);
                                        player.closeInventory();
                                        player.sendMessage(Color.translate("&4&l錯誤 &8» &c你手上沒有拿著任何物品!"));
                                        Bukkit.getScheduler().runTaskLaterAsynchronously(Survival.getInstance(), player::updateInventory, 1L);
                                        return;
                                    }
                                    File homefile = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home/" + replace + ".yml");
                                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(homefile);
                                    try {
                                        cfg.set("icon", player.getInventory().getItemInMainHand().getType().toString());
                                        cfg.save(homefile);
                                        player.sendMessage(Color.translate("&a&l完成 &8» &a已成功更換家 &e" + replace + " &a的圖示"));
                                        player.performCommand("homelist");
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            } else if (e.getClick() == ClickType.SHIFT_LEFT) {
                                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Color.translate("&e&l" + replace))) {
                                    File homefile = new File(Survival.getInstance().getDataFolder() + "/Player/" + player.getUniqueId().toString() + "/home/" + replace + ".yml");
                                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(homefile);
                                    try {
                                        cfg.set("icon", "PAPER");
                                        cfg.save(homefile);
                                        player.sendMessage(Color.translate("&a&l完成 &8» &a已成功重置家 &e" + replace + " &a的圖示"));
                                        player.performCommand("homelist");
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
