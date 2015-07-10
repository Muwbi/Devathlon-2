package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Created by Gelox_ on 10.07.2015.
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit( PlayerQuitEvent event ) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if ( Team.hasTeam( uuid ) ) {
            Team.clearTeam( uuid );
        }

        GameState gameState = SearchAndDestroy.getInstance().getGame().getGameState();
        if ( GameState.isIngame( gameState ) ) {
            //TODO: Win-Message
        }
        event.setQuitMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + event.getPlayer().getName() + " hat das Spiel verlassen!" );
    }
}
