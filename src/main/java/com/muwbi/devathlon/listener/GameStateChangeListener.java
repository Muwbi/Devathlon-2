package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.event.GameStateChangeEvent;
import com.muwbi.devathlon.inventory.ShopInventory;
import com.muwbi.devathlon.scheduler.WarmupCountdown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

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

            for ( Player player : Bukkit.getOnlinePlayers() ) {
                player.teleport( SearchAndDestroy.getInstance().getGame().getMapConfig().getTeamSpawns().get( Team.getTeam( player.getUniqueId() ).name() ) );
                player.setHealth( player.getMaxHealth() );
                player.setSaturation( 20 );
            }

            final Random random = new Random();
            Player bombCarrier = Bukkit.getPlayer( Team.T.getMembers().get( random.nextInt( Team.T.getMembers().size() ) ) );
            bombCarrier.getInventory().setItem( 4, ShopInventory.setLore( ShopInventory.setDisplayName( new ItemStack( Material.TNT ), ChatColor.RED + "Bombe" ), ChatColor.GRAY + "An einem Bombenplatz befestigen" ) );

            new WarmupCountdown( 5 ).start();
        } else if ( event.getPreviousState() == GameState.WARMUP && event.getNewState() == GameState.INGAME ) {
            Bukkit.broadcastMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + "Game is live!" );
        }
    }

}
