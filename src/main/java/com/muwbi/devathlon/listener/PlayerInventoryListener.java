package com.muwbi.devathlon.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Created by Muwbi
 */
public class PlayerInventoryListener implements Listener {

    @EventHandler( priority = EventPriority.MONITOR )
    public void onInventoryClick( InventoryClickEvent event ) {
        event.setCancelled( true );
    }

    @EventHandler( priority = EventPriority.MONITOR )
    public void onInventoryDrag( InventoryDragEvent event ) {
        event.setCancelled( true );
    }

}
