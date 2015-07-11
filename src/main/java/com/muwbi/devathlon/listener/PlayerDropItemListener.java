package com.muwbi.devathlon.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Muwbi
 */
public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItem( PlayerDropItemEvent event ) {
        if ( !event.getPlayer().isOp() ) {
            event.setCancelled( true );
        }
    }

}
