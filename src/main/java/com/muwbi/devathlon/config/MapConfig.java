package com.muwbi.devathlon.config;

import lombok.Getter;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.cubespace.Yamler.Config.InvalidConverterException;
import org.bukkit.Location;

import java.io.File;
import java.util.List;

/**
 * Created by Muwbi
 */
public class MapConfig extends Config {

    public static final MapConfig DEFAULT;

    static {
        DEFAULT = new MapConfig( new File( "maps", "default.yml" ) );
        try {
            DEFAULT.init();
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }
    }

    public MapConfig( File configFile ) {
        CONFIG_FILE = configFile;

        try {
            addConverter( SimpleLocationConverter.class );
        } catch ( InvalidConverterException e ) {
            e.printStackTrace();
        }
    }

    @Getter
    private String name;

    @Getter
    private Location bombLocation;

    @Getter
    private List<Location> teamSpawns;

}
