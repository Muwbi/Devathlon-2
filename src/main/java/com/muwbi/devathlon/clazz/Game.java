package com.muwbi.devathlon.clazz;

import com.muwbi.devathlon.config.MapConfig;
import com.muwbi.devathlon.event.GameStateChangeEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

/**
 * Created by Muwbi
 */
public class Game {

    @Getter
    private MapConfig mapConfig;

    @Getter
    @Setter
    private GameState gameState = GameState.LOBBY;

    @Getter
    private Team[] teams = new Team[]{Team.T, Team.CT};

    public Game( MapConfig config ) {
        mapConfig = config;
    }

    public void changeGameState() {
        int index = 0;
        for ( int i = 0; i < GameState.values().length; i++ ) {
            if ( GameState.values()[i] == gameState ) {
                index = i;
            }
        }

        if ( index >= GameState.values().length ) {
            index = 0;
        }

        changeGameState( GameState.values()[index] );
    }

    public void changeGameState( GameState newGameState ) {
        GameStateChangeEvent gameStateChangeEvent = new GameStateChangeEvent( gameState, newGameState );

        Bukkit.getPluginManager().callEvent( gameStateChangeEvent );

        if ( !gameStateChangeEvent.isCancelled() ) {
            gameState = newGameState;
        }
    }

}
