package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Muwbi
 */
public class PlantTask implements Runnable {

    private final Player planter;
    private final Block blockPlaced;

    private AtomicInteger counter;
    private BukkitTask task;

    public PlantTask( Player player, Block placedBlock ) {
        planter = player;
        blockPlaced = placedBlock;

        counter = new AtomicInteger( 5 );
    }

    @Override
    public void run() {
        if ( SearchAndDestroy.getInstance().getGame().isPlanting() ) {
            if ( counter.get() < SearchAndDestroy.getInstance().getGameConfig().getPlantTime() ) {
                counter.incrementAndGet();
            } else if ( counter.get() == SearchAndDestroy.getInstance().getGameConfig().getPlantTime() ) {
                blockPlaced.setType( Material.TNT );

                Bukkit.broadcastMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + "Die Bombe wurde von " + ChatColor.GOLD + planter.getName() + ChatColor.YELLOW + " geplantet!" );

                SearchAndDestroy.getInstance().getGame().setPlanting( false );
                SearchAndDestroy.getInstance().getGame().changeGameState( GameState.INGAME_PLANTED );

                stop();
            }
        } else {
            planter.sendMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + "Das Planten wurde abgebrochen, da du dich bewegt hast!" );
            stop();
        }
    }

    public void start() {
        task = Bukkit.getScheduler().runTaskTimer( SearchAndDestroy.getInstance(), this, 20, 20 );
    }

    public void stop() {
        task.cancel();
    }


}
