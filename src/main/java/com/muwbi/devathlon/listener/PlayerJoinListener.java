package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

        player.getInventory().clear();
        player.setHealth( player.getMaxHealth() );
        player.getActivePotionEffects().forEach( potionEffect -> player.removePotionEffect( potionEffect.getType() ) );

        if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.LOBBY ) {
            player.setGameMode( GameMode.ADVENTURE );
        } else {
            player.setGameMode( GameMode.SPECTATOR );
        }

        event.setJoinMessage( ChatColor.GRAY + "> " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " tritt dem Spiel bei" );
    }

}
