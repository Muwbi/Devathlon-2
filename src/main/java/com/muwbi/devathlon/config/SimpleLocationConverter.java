package com.muwbi.devathlon.config;

import net.cubespace.Yamler.Config.ConfigSection;
import net.cubespace.Yamler.Config.Converter.Converter;
import net.cubespace.Yamler.Config.InternalConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muwbi
 */
public class SimpleLocationConverter implements Converter {

    public SimpleLocationConverter( InternalConverter converter ) {

    }

    @Override
    public Object toConfig( Class<?> type, Object object, ParameterizedType genericType ) throws Exception {
        Location location = (Location) object;

        Map<String, Object> keyValueStore = new HashMap<>();
        keyValueStore.put( "world", location.getWorld().getName() );
        keyValueStore.put( "x", location.getX() );
        keyValueStore.put( "y", location.getY() );
        keyValueStore.put( "z", location.getZ() );

        return keyValueStore;
    }

    @Override
    public Object fromConfig( Class type, Object section, ParameterizedType genericType ) throws Exception {
        Map<String, Object> locationMap = (Map<String, Object>) ((ConfigSection) section).getRawMap();

        return new Location( Bukkit.getWorld( (String) locationMap.get( "world" ) ), (Double) locationMap.get( "x" ), (Double) locationMap.get( "y" ), (Double) locationMap.get( "z" ) );
    }

    @Override
    public boolean supports(  Class<?> type ) {
        return Location.class.isAssignableFrom( type );
    }

}
