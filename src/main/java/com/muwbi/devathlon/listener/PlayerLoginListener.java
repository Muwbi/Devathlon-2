package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Muwbi
 */
public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin( PlayerLoginEvent event ) {
        if ( GameState.isIngame( SearchAndDestroy.getInstance().getGame().getGameState() ) ) {
            event.disallow( PlayerLoginEvent.Result.KICK_OTHER, "\u00a7cDas Spiel läuft bereits!" );
        }
    }

}
