package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Gelox_.
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace( BlockPlaceEvent event ) {
        GameState gameState = SearchAndDestroy.getInstance().getGame().getGameState();
        if ( ( gameState == GameState.INGAME || gameState == GameState.INGAME_PLANTED || gameState == GameState.WARMUP ) && !event.getPlayer().isOp() ) {
            event.setCancelled( true );
        }
    }
}
