package com.muwbi.devathlon.listener;

import com.google.common.base.Optional;
import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.config.GameConfig;
import com.muwbi.devathlon.config.MapConfig;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.text.html.Option;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gelox_.
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace( BlockPlaceEvent event ) {

        Player bombCarrier = event.getPlayer();
        Block bomb = event.getBlockPlaced();
        int plantTime = (int) GameConfig.getInstance().getPlantTime();

        if( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.INGAME ) {
            if( Team.getTeam( bombCarrier.getUniqueId() ) == Team.T ) {
                if( bomb.getType() == Material.TNT ) {
                    if( MapConfig.DEFAULT.getBombLocations().contains( bomb.getLocation() ) ) {
                        SearchAndDestroy.getInstance().setPlanting(true);
                        bomb.setType(Material.REDSTONE_BLOCK);
                        final AtomicInteger counter = new AtomicInteger(0);
                        Bukkit.getScheduler().scheduleSyncRepeatingTask(SearchAndDestroy.getInstance(), new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (SearchAndDestroy.getInstance().isPlanting()) {
                                    if (counter.get() < plantTime) {
                                        counter.set(counter.get() + 1);
                                    }
                                    if (counter.get() == plantTime) {
                                        bomb.setType(Material.TNT);
                                        Bukkit.broadcastMessage(ChatColor.GRAY + "> " + ChatColor.YELLOW + "Die Bombe wurde von " + ChatColor.GOLD + bombCarrier.getName() + ChatColor.YELLOW + " geplantet!");
                                    }
                                } else {
                                    bombCarrier.sendMessage(ChatColor.GRAY + "> " + ChatColor.YELLOW + "Das Planten wurde abgebrochen, da du dich bewegt hast!");
                                    cancel();
                                }
                            }
                        }, 0, 20);
                    }
                }
            }
        }
        if ( !event.getPlayer().isOp() ) {
            event.setCancelled( true );
        }
    }

}
