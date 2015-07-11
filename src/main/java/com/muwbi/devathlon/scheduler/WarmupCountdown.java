package com.muwbi.devathlon.scheduler;

import com.muwbi.devathlon.SearchAndDestroy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Muwbi
 */
public class WarmupCountdown implements Runnable {

    private final AtomicInteger countdown;

    private BukkitTask bukkitTask;

    public WarmupCountdown( int seconds ) {
        countdown = new AtomicInteger( seconds + 1 );
    }

    @Override
    public void run() {
        int counter = countdown.decrementAndGet();

        if ( counter > 0 ) {
            Bukkit.broadcastMessage( ChatColor.GRAY + "> " + ChatColor.YELLOW + "Das Spiel startet in " + ChatColor.GOLD + counter + " Sekunden" );
        } else {
            SearchAndDestroy.getInstance().getGame().changeGameState();
            stop();
        }
    }

    public void start() {
        bukkitTask = Bukkit.getScheduler().runTaskTimer( SearchAndDestroy.getInstance(), this, 20, 0 );
    }

    public void stop() {
        bukkitTask.cancel();
    }

}
