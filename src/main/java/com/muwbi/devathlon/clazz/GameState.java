package com.muwbi.devathlon.clazz;

/**
 * Created by Muwbi
 */
public enum GameState {

    LOBBY,
    WARMUP,
    INGAME,
    INGAME_PLANTED;

    public static boolean isIngame( GameState gameState ) {
        return gameState == GameState.WARMUP || gameState == GameState.INGAME || gameState == GameState.INGAME_PLANTED;
    }

}
