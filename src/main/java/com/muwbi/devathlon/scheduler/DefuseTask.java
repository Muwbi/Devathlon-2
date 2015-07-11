package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SearchAndDestroy;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Marco_2 on 11.07.2015.
 */
public class DefuseTask implements Runnable {

    private final Player defuser;
    private final Block bomb;

    private int defuseTime = 0;
    private AtomicInteger counter;
    private BukkitTask task;

    public DefuseTask( Player player, Block theBomb, int defuse ) {
        defuser = player;
        bomb = theBomb;
        defuseTime = defuse;
    }

    @Override
    public void run() {
        if ( SearchAndDestroy.getInstance().getGame().isDefusing() ) {
            if ( counter.get() < defuseTime ) {
                counter.incrementAndGet();
            } else if ( counter.get() == defuseTime ) {
                bomb.setType( Material.REDSTONE_BLOCK );
                Bukkit.broadcastMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + "Die Bombe wurde von " + ChatColor.GOLD + defuser.getName() + ChatColor.YELLOW + " entschärft!" );
                bomb.setType( Material.AIR );
                SearchAndDestroy.getInstance().getGame().setPlanting( false );
                stop();
            }
        } else {
            defuser.sendMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + "Das Entschärfen wurde abgebrochen, da du dich bewegt hast!" );
            stop();
        }
    }

    public void start() {
        task = Bukkit.getScheduler().runTaskTimer( SearchAndDestroy.getInstance(), this, 0, 20 );
    }

    public void stop() {
        task.cancel();
    }

}
