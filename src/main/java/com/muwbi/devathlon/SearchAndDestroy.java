package com.muwbi.devathlon;

import com.muwbi.devathlon.clazz.Game;
import com.muwbi.devathlon.config.MapConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by Muwbi
 */
public class SearchAndDestroy extends JavaPlugin {

    @Getter
    private static SearchAndDestroy instance;

    @Getter
    private Game game;

    @Override
    public void onEnable() {
        instance = this;

        game = new Game( MapConfig.DEFAULT );
        System.out.println("Loaded " + getDescription().getName() + " | Version: " + getDescription().getVersion() + " | Description: " + getDescription().getDescription());
    }


    @Override
    public void onDisable() {

    }

}
