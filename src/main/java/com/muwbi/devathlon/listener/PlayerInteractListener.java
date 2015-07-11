package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.inventory.ShopInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Muwbi
 */
public class PlayerInteractListener implements Listener {

    private ShopInventory shopInventory;

    public PlayerInteractListener() {
        shopInventory = new ShopInventory( 9, ChatColor.YELLOW + "Shop" );
        shopInventory.addItem( 0, ShopInventory.setLore( ShopInventory.setDisplayName( new ItemStack( Material.IRON_SWORD ), ChatColor.YELLOW + "Schwert upgraden" ), ChatColor.GRAY + "Kosten: 3 Punkte" ), ( player ) -> SearchAndDestroy.getInstance().getGame().getShopManager().upgradeSword( player ) );
        shopInventory.addItem( 4, ShopInventory.setLore( ShopInventory.setDisplayName( new ItemStack( Material.BOW ), ChatColor.YELLOW + "Bogen upgraden" ), ChatColor.GRAY + "Kosten: 3 Punkte" ), ( player ) -> SearchAndDestroy.getInstance().getGame().getShopManager().upgradeBow( player ) );
        shopInventory.addItem( 8, ShopInventory.setLore( ShopInventory.setDisplayName( new ItemStack( Material.SHEARS ), ChatColor.YELLOW + "Defusekit kaufen" ), ChatColor.GRAY + "Kosten: 3 Punkte" ), ( player ) -> SearchAndDestroy.getInstance().getGame().getShopManager().buyDefuseKit( player ) );
    }

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        if ( event.hasItem() && event.getItem().getType() == Material.EMERALD && ( event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ) ) {
            shopInventory.open( event.getPlayer() );
        }
    }

}
