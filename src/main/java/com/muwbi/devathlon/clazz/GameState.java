package com.muwbi.devathlon.clazz;

/**
 * Created by Muwbi
 * <p/>
 * Represents the different game states
 */
public enum GameState {

    LOBBY,
    WARMUP,
    INGAME,
    INGAME_PLANTED;

    /**
     * Checks if a given game state is ingame
     *
     * @param gameState
     * @return whether the game state is ingame or not
     */
    public static boolean isIngame( GameState gameState ) {
        return gameState == GameState.WARMUP || gameState == GameState.INGAME || gameState == GameState.INGAME_PLANTED;
    }

}
