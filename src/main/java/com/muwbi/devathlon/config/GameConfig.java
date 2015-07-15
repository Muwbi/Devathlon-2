package com.muwbi.devathlon.config;

import com.muwbi.devathlon.SearchAndDestroy;
import lombok.Getter;
import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.Yamler.Config.InvalidConverterException;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;

/**
 * Created by Muwbi
 */
public class GameConfig extends Config {

    @Getter
    @Comment( "Spawn location for lobby phase" )
    private Location lobbySpawn = new Location( Bukkit.getWorld( "Devathlon" ), 0, 10, 0 );

    @Getter
    @Comment( "Time needed to plant the bomb (in seconds)" )
    private int plantTime = 5;

    @Getter
    @Comment( "Time needed to defuse the bomb (in seconds)" )
    private int defuseTime = 5;

    public GameConfig() {
        CONFIG_FILE = new File( SearchAndDestroy.getInstance().getDataFolder(), "game.yml" );

        try {
            addConverter( net.cubespace.Yamler.Converter.Location.class );
        } catch ( InvalidConverterException e ) {
            e.printStackTrace();
        }
    }
}
