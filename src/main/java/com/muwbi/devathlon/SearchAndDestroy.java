package com.muwbi.devathlon;

import com.muwbi.devathlon.clazz.Game;
import com.muwbi.devathlon.command.GameStateCommand;
import com.muwbi.devathlon.config.GameConfig;
import com.muwbi.devathlon.config.MapConfig;
import com.muwbi.devathlon.listener.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.FileUtil;

import java.io.File;


/**
 * Created by Muwbi
 */
public class SearchAndDestroy extends JavaPlugin {

    @Getter
    private static SearchAndDestroy instance;

    @Getter
    private Scoreboard scoreboard;

    @Getter
    private Game game;

    @Getter
    private GameConfig gameConfig;

    @Override
    public void onEnable() {
        instance = this;

        getCommand( "gamestate" ).setExecutor( new GameStateCommand() );

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents( new GameStateChangeListener(), this );
        pluginManager.registerEvents( new PlayerJoinListener(), this );
        pluginManager.registerEvents( new BlockPlaceListener(), this );
        pluginManager.registerEvents( new BlockBreakListener(), this );
        pluginManager.registerEvents( new AsyncPlayerChatListener(), this );
        pluginManager.registerEvents( new PlayerQuitListener(), this );
        pluginManager.registerEvents( new PointChangeListener(), this );
        pluginManager.registerEvents( new PlayerDeathListener(), this );
        pluginManager.registerEvents( new PlayerInteractListener(), this );

        File lobbyFolder = new File( "Devathlon" );
        File mapFolder = new File( "DevathlonMap" );

        if ( !lobbyFolder.exists() || !mapFolder.exists() ) {
            getLogger().warning( "Devathlon or DevathlonMap does not exist. Disabling..." );
            pluginManager.disablePlugin( this );
        }

        // TODO: Load map

        gameConfig = new GameConfig();

        game = new Game( MapConfig.DEFAULT );
        System.out.println( "Loaded " + getDescription().getName() + " | Version: " + getDescription().getVersion() + " | Description: " + getDescription().getDescription() );
    }

    @Override
    public void onDisable() {

    }

}
