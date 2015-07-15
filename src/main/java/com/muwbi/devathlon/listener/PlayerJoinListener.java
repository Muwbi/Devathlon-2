package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.event.GameStateChangeEvent;
import com.muwbi.devathlon.scheduler.WarmupCountdown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Muwbi
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.setHealth( player.getMaxHealth() );
        player.setSaturation( 20 );
        player.getActivePotionEffects().forEach( potionEffect -> player.removePotionEffect( potionEffect.getType() ) );

        player.setScoreboard( Team.getScoreboard() );

        if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.LOBBY ) {
            player.setGameMode( GameMode.SURVIVAL );

            player.teleport( SearchAndDestroy.getInstance().getGameConfig().getLobbySpawn() );

            event.setJoinMessage( ChatColor.GRAY + "> " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " tritt dem Spiel bei" );

            if( Bukkit.getOnlinePlayers().size() >= 2 ) {
                Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(SearchAndDestroy.getInstance().getGame().getGameState(), GameState.WARMUP));
            }

        } else {
            player.setGameMode( GameMode.SPECTATOR );

            // Maths magic
            Location tSpawn = SearchAndDestroy.getInstance().getGame().getMapConfig().getTeamSpawns().get( "T" );
            Location ctSpawn = SearchAndDestroy.getInstance().getGame().getMapConfig().getTeamSpawns().get( "CT" );

            Location middle = new Location( tSpawn.getWorld(), ( tSpawn.getX() + ctSpawn.getX() ) / 2, tSpawn.getY() + 10, ( tSpawn.getZ() + ctSpawn.getZ() ) / 2 );
            player.teleport( middle );

            Team.getSpectatorTeam().addEntry( player.getName() );

            player.setAllowFlight( true );
            player.setFlying( true );

            player.addPotionEffect( new PotionEffect( PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, Integer.MAX_VALUE ) );

            event.setJoinMessage( null );
        }
    }

}
