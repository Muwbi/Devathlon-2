package com.muwbi.devathlon;

import com.google.common.reflect.ClassPath;
import com.muwbi.devathlon.clazz.Game;
import com.muwbi.devathlon.command.GameStateCommand;
import com.muwbi.devathlon.config.GameConfig;
import com.muwbi.devathlon.config.MapConfig;
import com.muwbi.devathlon.listener.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


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

    @Getter
    @Setter
    private boolean planting;

    @Override
    public void onEnable() {
        instance = this;

        getCommand( "gamestate" ).setExecutor( new GameStateCommand() );

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        PluginManager pluginManager = Bukkit.getPluginManager();
        try {
            for ( ClassPath.ClassInfo classInfo : ClassPath.from( getClassLoader() ).getTopLevelClasses( "com.muwbi.devathlon.listener" ) ) {
                Class clazz = Class.forName( classInfo.getName() );

                if ( Listener.class.isAssignableFrom( clazz ) ) {
                    pluginManager.registerEvents( (Listener) clazz.newInstance(), this );
                }
            }
        } catch ( IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e ) {
            e.printStackTrace();
        }

        File lobbyFolder = new File( "Devathlon" );
        File mapFolder = new File( "DevathlonMap" );

        if ( !lobbyFolder.exists() || !mapFolder.exists() ) {
            getLogger().warning( "Devathlon or DevathlonMap does not exist. Disabling..." );
            pluginManager.disablePlugin( this );
            return;
        }

        World devathlonWorld = new WorldCreator( "Devathlon" ).environment( World.Environment.NORMAL ).createWorld();
        World devathlonMapWorld = new WorldCreator( "DevathlonMap" ).environment( World.Environment.NORMAL ).createWorld();

        devathlonWorld.setGameRuleValue( "keepInventory", "true" );
        devathlonMapWorld.setGameRuleValue( "keepInventory", "true" );

        Bukkit.getScheduler().runTaskTimer( this, () -> {
            devathlonWorld.setTime( 6000 );
            devathlonMapWorld.setTime( 0 );
        }, 20, 20 );

        getLogger().info( "Devathlon and DevathlonMap loaded" );

        gameConfig = new GameConfig();

        game = new Game( MapConfig.DEFAULT );
        System.out.println( "Loaded " + getDescription().getName() + " | Version: " + getDescription().getVersion() + " | Description: " + getDescription().getDescription() );
    }

    @Override
    public void onDisable() {

    }

}
