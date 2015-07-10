package com.muwbi.devathlon.event;

import com.muwbi.devathlon.clazz.GameState;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Muwbi
 */
public class GameStateChangeEvent extends Event implements Cancellable {

    private HandlerList handlerList = new HandlerList();

    private boolean cancelled;

    @Getter
    private final GameState previousState;

    @Getter
    private final GameState newState;

    public GameStateChangeEvent( GameState previousState, GameState newState ) {
        this.previousState = previousState;
        this.newState = newState;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled( boolean b ) {
        cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
