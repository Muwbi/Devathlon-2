package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.event.PointChangeEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Muwbi
 */
public class PointChangeListener implements Listener {

    private Map<UUID, Integer> points = new HashMap<>();

    @EventHandler
    public void onPointChange( PointChangeEvent event ) {
        UUID uuid = event.getPlayer().getUniqueId();

        if ( !points.containsKey( uuid ) ) {
            points.put( uuid, 1 );
        }

        Integer newPoints = points.put( uuid, points.get( uuid ) + event.getValue() );

        Team.setPoints( uuid, newPoints );

        event.getPlayer().getInventory().setItem( 8, new ItemStack( Material.EMERALD, newPoints ) );
        event.getPlayer().updateInventory(); // refresh inventory
    }

}
