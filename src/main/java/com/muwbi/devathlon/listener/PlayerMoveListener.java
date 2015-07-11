package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Marco_2 on 11.07.2015.
 */
public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove( PlayerMoveEvent event ) {
        if(SearchAndDestroy.getInstance().isPlanting()) {
            SearchAndDestroy.getInstance().setPlanting(false);
        }
    }
}
