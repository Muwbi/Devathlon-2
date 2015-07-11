package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Gelox_
 */
public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove( PlayerMoveEvent event ) {
        if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.WARMUP ) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if ( from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ() ) {
                event.getPlayer().teleport( from );
            }
        } else if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.INGAME ) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if ( from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ() ) {
                SearchAndDestroy.getInstance().getGame().setPlanting( false );
            }
        }
    }
}
