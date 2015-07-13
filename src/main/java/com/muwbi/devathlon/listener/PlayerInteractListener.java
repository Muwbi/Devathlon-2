package com.muwbi.devathlon.listener;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.inventory.ShopInventory;
import com.muwbi.devathlon.scheduler.DefuseTask;
import com.muwbi.devathlon.scheduler.PlantTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
        } else if ( Team.getTeam( event.getPlayer().getUniqueId() ) == Team.CT && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.TNT ) {
            final int defuseTime = SearchAndDestroy.getInstance().getGameConfig().getDefuseTime();
            SearchAndDestroy.getInstance().getGame().setDefusing(true);
            new DefuseTask( event.getPlayer(), event.getClickedBlock(), ( SearchAndDestroy.getInstance().getGame().getShopManager().hasDefusekit( event.getPlayer() ) ) ? defuseTime / 2 : defuseTime ).start();
        } else if ( event.hasItem() && event.getItem().getType() == Material.TNT && event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            Player bombCarrier = event.getPlayer();
            Block blockPlaced = event.getClickedBlock().getWorld().getBlockAt( event.getClickedBlock().getLocation().add( 0, 1, 0 ) );

            if ( SearchAndDestroy.getInstance().getGame().getGameState() == GameState.INGAME ) {
                if ( Team.getTeam( bombCarrier.getUniqueId() ) == Team.T ) {
                    if ( blockPlaced.getType() == Material.TNT ) {
                        if ( SearchAndDestroy.getInstance().getGame().getMapConfig().getBombLocations().contains( blockPlaced.getLocation() ) ) {
                            SearchAndDestroy.getInstance().getGame().setPlanting( true );
                            blockPlaced.setType( Material.REDSTONE_BLOCK );
                            new PlantTask( bombCarrier, blockPlaced ).start();
                        }
                    }
                }
            }
        }

        event.setCancelled( true );
    }

}
