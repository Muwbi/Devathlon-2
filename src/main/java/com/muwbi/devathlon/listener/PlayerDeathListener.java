package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.event.PointChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Muwbi
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath( PlayerDeathEvent event ) {
        Player dead = event.getEntity();
        Player culprit = dead.getKiller();

        event.setDeathMessage( ChatColor.GRAY + "> " + ChatColor.GOLD + dead.getName() + ChatColor.YELLOW + " wurde von " + ChatColor.GOLD + culprit.getName() + ChatColor.YELLOW + " getötet!" );

        Bukkit.getPluginManager().callEvent( new PointChangeEvent( culprit, 3 ) );
    }

}
