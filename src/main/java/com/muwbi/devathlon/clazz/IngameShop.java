package com.muwbi.devathlon.clazz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Muwbi
 */
public class IngameShop implements Listener {

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        if ( event.getItem().getType() == Material.EMERALD && ( event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ) ) {

        }
    }


}
