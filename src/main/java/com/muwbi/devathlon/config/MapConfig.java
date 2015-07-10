package com.muwbi.devathlon.config;

import com.muwbi.devathlon.SearchAndDestroy;
import lombok.Getter;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.cubespace.Yamler.Config.InvalidConverterException;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muwbi
 */
public class MapConfig extends Config {

    public static final MapConfig DEFAULT;

    static {
        DEFAULT = new MapConfig( new File( new File( SearchAndDestroy.getInstance().getDataFolder(), "maps" ), "default.yml" ) );
        try {
            DEFAULT.init();
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }
    }

    public MapConfig( File configFile ) {
        CONFIG_FILE = configFile;

        try {
            addConverter( net.cubespace.Yamler.Converter.Location.class );
        } catch ( InvalidConverterException e ) {
            e.printStackTrace();
        }
    }

    @Getter
    private String name = "Default";

    @Getter
    private Location bombLocation = new Location( Bukkit.getWorlds().get( 0 ), 0, 0, 0 );

    @Getter
    private Map<String, Location> teamSpawns = new HashMap<String, Location>() {{
        put( "T", new Location( Bukkit.getWorlds().get( 0 ), 0, 0, 0 ) );
        put( "CT", new Location( Bukkit.getWorlds().get( 0 ), 0, 0, 0 ) );
    }};

}
