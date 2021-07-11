package cc.happyareabean.survival.listener;

import cc.happyareabean.survival.Survival;
import cc.happyareabean.survival.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class MobListener implements Listener {

    public MobListener(Survival plugin) {
        Bukkit.getConsoleSender().sendMessage(Lang.prefix + "正在加載Listener... [MobListener]");
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPhantomSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
        LivingEntity livingEntity = event.getEntity();
        if(!(livingEntity instanceof Phantom))
            return;

        if(reason == CreatureSpawnEvent.SpawnReason.NATURAL) {
            Phantom phantom = (Phantom) livingEntity;
            phantom.remove();
            return;
        }

        Phantom phantom = (Phantom) livingEntity;
        phantom.remove();
    }

    @EventHandler
    public void onExpore(EntityExplodeEvent e) {
        if (e.getEntity() instanceof WitherSkull) {
            e.setCancelled(true);
        }
    }
}
