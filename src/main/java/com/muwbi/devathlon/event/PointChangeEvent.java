package com.muwbi.devathlon.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Muwbi
 */
public class PointChangeEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    @Getter
    private final Player player;

    @Getter
    @Setter
    private final int value;

    public PointChangeEvent( Player player, int value ) {
        this.player = player;
        this.value = value;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
