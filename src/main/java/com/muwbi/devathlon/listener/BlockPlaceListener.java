package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.config.MapConfig;
import com.muwbi.devathlon.scheduler.PlantTask;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gelox_.
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace( BlockPlaceEvent event ) {
        Player bombCarrier = event.getPlayer();
        Block blockPlaced = event.getBlockPlaced();

        int plantTime = SearchAndDestroy.getInstance().getGameConfig().getPlantTime();

        if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.INGAME ) {
            if ( Team.getTeam( bombCarrier.getUniqueId() ) == Team.T ) {
                if ( blockPlaced.getType() == Material.TNT ) {
                    if ( MapConfig.DEFAULT.getBombLocations().contains( blockPlaced.getLocation() ) ) {
                        SearchAndDestroy.getInstance().getGame().setPlanting( true );
                        blockPlaced.setType( Material.REDSTONE_BLOCK );

                        new PlantTask( bombCarrier, blockPlaced ).start();
                    }
                }
            }
        } else
            if (!event.getPlayer().isOp())
                event.setCancelled(true);
    }

}
