package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Marco_2 on 10.07.2015.
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        GameState gameState = SearchAndDestroy.getInstance().getGame().getGameState();
        if (gameState == GameState.INGAME || gameState == GameState.INGAME_PLANTED) {
            //TODO: Win-Message
        }
        event.setQuitMessage(ChatColor.GRAY + "> " + ChatColor.YELLOW + event.getPlayer().getName() + " hat das Spiel verlassen!");
    }
}
