package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Muwbi
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        Player player = event.getPlayer();

        if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.LOBBY ) {
            if ( !Team.hasTeam( player.getUniqueId() ) ) {
                Team team = Team.getLessPopulatedTeam();
                if ( team.addMember( player.getUniqueId() ) ) {
                    event.setJoinMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + player.getName() + " schließt sich den " + team.getTeamColor() + team.getFullTeamName() + ChatColor.YELLOW + " an" );
                } else {
                    player.kickPlayer( ChatColor.RED + "Error!" );
                }
            }
        }
    }

}
