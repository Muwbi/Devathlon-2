package com.muwbi.devathlon.config;

import lombok.Getter;
import net.cubespace.Yamler.Config.Config;

import java.io.File;

/**
 * Created by Muwbi
 */
public class GameConfig extends Config {

    public GameConfig() {
        CONFIG_FILE = new File( "game.yml" );
    }

}
