package com.muwbi.devathlon.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Gelox_.
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace( BlockPlaceEvent event ) {
        if ( !event.getPlayer().isOp() ) {
            event.setCancelled( true );
        }
    }
}
