package com.muwbi.devathlon.inventory;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.Callback;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Muwbi
 */
public class ShopInventory implements Listener {

    @Getter
    private List<UUID> openedInventories = new ArrayList<>();

    private Map<Integer, Callback<Player>> callbackMap = new HashMap<>();

    private Inventory inventory;

    public ShopInventory( int size, String title ) {
        inventory = Bukkit.createInventory( null, size, title );

        Bukkit.getPluginManager().registerEvents( this, SearchAndDestroy.getInstance() );
    }

    public void open( Player player ) {
        player.openInventory( inventory );

        openedInventories.add( player.getUniqueId() );
    }

    public void addItem( int slot, ItemStack itemStack ) {
        inventory.setItem( slot, itemStack );
    }

    public void addItem( int slot, ItemStack itemStack, Callback<Player> callback) {
        addItem( slot, itemStack );
        callbackMap.put( slot, callback );
    }

    @EventHandler
    public void onInventoryClose( InventoryCloseEvent event ) {
        UUID uuid = event.getPlayer().getUniqueId();
        if ( openedInventories.contains( uuid ) ) {
            openedInventories.remove( uuid );
        }
    }

    @EventHandler
    public void onInventoryDrag( InventoryDragEvent event ) {
        if ( openedInventories.contains( event.getWhoClicked().getUniqueId() ) ) {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void onInventoryClick( InventoryClickEvent event ) {
        if ( callbackMap.containsKey( event.getSlot() ) ) {
            callbackMap.get( event.getSlot() ).done( (Player) event.getWhoClicked() );
        }

        event.setCancelled( true );
    }

}
