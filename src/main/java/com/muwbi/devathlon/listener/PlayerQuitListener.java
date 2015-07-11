package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Created by Gelox_
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit( PlayerQuitEvent event ) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Team team = Team.getTeam( uuid );


        GameState gameState = SearchAndDestroy.getInstance().getGame().getGameState();
        if ( GameState.isIngame( gameState ) ) {
            //TODO: Win-Message
        }

        event.setQuitMessage( ChatColor.GRAY + "> " + ( team != null ? ( ChatColor.DARK_AQUA + "[" + team.getTeamColor() + team.toString() + ChatColor.DARK_AQUA + "] " ) : "" ) + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.YELLOW + " hat das Spiel verlassen!" );

        if ( Team.hasTeam( uuid ) ) {
            Team.clearTeam( uuid );
        }
    }
}
