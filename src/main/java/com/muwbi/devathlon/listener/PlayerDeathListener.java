package com.muwbi.devathlon.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Marco_2 on 10.07.2015.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(player.getKiller() instanceof Player)
            event.setDeathMessage(ChatColor.GRAY + "> " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " wurde von " + ChatColor.GOLD + player.getKiller().getName() + ChatColor.YELLOW + " getötet!");
        else
            event.setDeathMessage(ChatColor.GRAY + "> " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " ist gestorben!");
    }
}
