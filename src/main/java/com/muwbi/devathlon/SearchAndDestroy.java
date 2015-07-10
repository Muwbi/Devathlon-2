package com.muwbi.devathlon;

import com.muwbi.devathlon.clazz.Game;
import com.muwbi.devathlon.config.MapConfig;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by Muwbi
 */
public class SearchAndDestroy extends JavaPlugin {

    Game game;

    @Override
    public void onEnable() {
        game = new Game( MapConfig.DEFAULT );
    }

    @Override
    public void onDisable() {

    }


}
