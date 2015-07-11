package com.muwbi.devathlon.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Gelox_.
 */
public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak( BlockBreakEvent event ) {
        if ( !event.getPlayer().isOp() ) {
            event.setCancelled( true );
        }
    }
}

