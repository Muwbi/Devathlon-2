package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.event.GameStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Muwbi
 */
public class GameStateChangeListener implements Listener {

    @EventHandler
    public void onGameStateChange( GameStateChangeEvent event ) {
        if ( event.getPreviousState() == GameState.LOBBY && event.getNewState() == GameState.WARMUP ) {
            for ( Player player : Bukkit.getOnlinePlayers() ) {
                Team team = Team.getLessPopulatedTeam();
                team.addMember( player.getUniqueId() );

                Bukkit.broadcastMessage( ChatColor.GRAY + "> " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " schließt sich den " + team.getTeamColor() + team.getFullTeamName() + ChatColor.YELLOW + " an" );
            }
        }
    }

}
