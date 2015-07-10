package com.muwbi.devathlon;

import com.muwbi.devathlon.clazz.Game;
import com.muwbi.devathlon.config.MapConfig;
import com.muwbi.devathlon.listener.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
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

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents( new GameStateChangeListener(), this );
        pluginManager.registerEvents( new PlayerJoinListener(), this );
        pluginManager.registerEvents( new PlayerJoinListener(), this );
        pluginManager.registerEvents( new BlockPlaceListener(), this);
        pluginManager.registerEvents( new BlockBreakListener(), this);
        pluginManager.registerEvents( new AsyncPlayerChatListener(), this);
        pluginManager.registerEvents( new PlayerQuitListener(), this);

        game = new Game( MapConfig.DEFAULT );
        System.out.println("Loaded " + getDescription().getName() + " | Version: " + getDescription().getVersion() + " | Description: " + getDescription().getDescription());
    }


    @Override
    public void onDisable() {

    }

}
