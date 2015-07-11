package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.event.GameStateChangeEvent;
import org.bukkit.Bukkit;
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
        if( Bukkit.getOnlinePlayers().size() < 2 ) {
            if ( GameState.isIngame( gameState ) ) {
                Bukkit.broadcastMessage(ChatColor.GRAY + "> " + ChatColor.YELLOW + "Das Team " + team.getTeamColor() + team.getOtherTeam(team).getFullTeamName() + ChatColor.YELLOW + " hat das Spiel gewonnen!");
                Bukkit.getPluginManager().callEvent( new GameStateChangeEvent( gameState, GameState.LOBBY ) );
            }
        }

        event.setQuitMessage( ChatColor.GRAY + "> " + ( team != null ? ( ChatColor.DARK_AQUA + "[" + team.getTeamColor() + team.toString() + ChatColor.DARK_AQUA + "] " ) : "" ) + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.YELLOW + " hat das Spiel verlassen!" );

        if ( Team.hasTeam( uuid ) ) {
            Team.clearTeam( uuid );
        }
    }
}
