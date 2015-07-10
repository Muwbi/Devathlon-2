package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/**
 * Created by Muwbi
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.LOBBY ) {
            if ( !Team.hasTeam( uuid ) ) {
                Team team = Team.getLessPopulatedTeam();
                if ( team.addMember( player.getUniqueId() ) ) {
                    event.setJoinMessage( ChatColor.GRAY + "> " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " schließt sich den " + team.getTeamColor() + team.getFullTeamName() + ChatColor.YELLOW + " an" );
                    player.setGameMode( GameMode.ADVENTURE );
                } else {
                    Team.clearTeam( uuid );
                    player.setGameMode( GameMode.SPECTATOR );
                }
            }
        } else {
            event.setJoinMessage( null );
            player.setGameMode( GameMode.SPECTATOR );
        }
    }

}
