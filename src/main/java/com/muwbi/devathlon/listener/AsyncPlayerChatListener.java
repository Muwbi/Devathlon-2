package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.clazz.Team;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Gelox_.
 */
public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat( AsyncPlayerChatEvent event ) {
        Team team = Team.getTeam( event.getPlayer().getUniqueId() );
        if ( team != null ) {
            if ( event.getPlayer().isOp() )
                event.setFormat( ChatColor.GRAY + "> " + ChatColor.DARK_AQUA + "[" + team.getTeamColor() + team.toString() + ChatColor.DARK_AQUA + "] " + ChatColor.GOLD + "%s" + ChatColor.GRAY + ": " + ChatColor.WHITE + "%s" );
            else
                event.setFormat( ChatColor.GRAY + "> " + ChatColor.DARK_AQUA + "[" + team.getTeamColor() + team.toString() + ChatColor.DARK_AQUA + "] " + ChatColor.DARK_GREEN + "%s" + ChatColor.GRAY + ": " + ChatColor.WHITE + "%s" );
        }
    }
}
