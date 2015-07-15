package com.muwbi.devathlon.inventory;

import com.muwbi.devathlon.clazz.Team;
import com.muwbi.devathlon.event.PointChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Muwbi
 */

public class ShopManager {

    enum SwordType {

        WOOD( Material.WOOD_SWORD ),
        STONE( Material.STONE_SWORD ),
        IRON( Material.IRON_SWORD );

        private final Material swordMaterial;

        private SwordType( Material material ) {
            swordMaterial = material;
        }

        public ItemStack getItemStack() {
            return new ItemStack( swordMaterial, 1 );
        }

    }

    enum BowType {

        NO_POWER( 0 ),
        POWER1( 1 ),
        POWER2( 2 );

        private static final Enchantment ENCHANTMENT = Enchantment.ARROW_DAMAGE;

        private final int enchantmentLevel;

        private BowType( int level ) {
            enchantmentLevel = level;
        }

        public ItemStack getItemStack() {
            ItemStack itemStack = new ItemStack( Material.BOW, 1 );

            if ( enchantmentLevel > 0 ) {
                itemStack.addEnchantment( ENCHANTMENT, enchantmentLevel );
            }

            return itemStack;
        }

    }

    private static final ItemStack DEFUSEKIT = ShopInventory.setLore( ShopInventory.setDisplayName( new ItemStack( Material.SHEARS ), ChatColor.RED + "Defusekit" ), ChatColor.GRAY + "Halbiert die benötigte Zeit zum Defusen" );

    private Map<UUID, SwordType> swordTypeMap = new HashMap<>();
    private Map<UUID, BowType> bowTypeMap = new HashMap<>();
    private Map<UUID, Boolean> defuseKitsMap = new HashMap<>();

    public void initialize( Player player ) {
        UUID uuid = player.getUniqueId();

        player.getInventory().clear();

        swordTypeMap.put( uuid, SwordType.WOOD );
        bowTypeMap.put( uuid, BowType.NO_POWER );
        defuseKitsMap.put( uuid, false );

        player.getInventory().setItem( 0, swordTypeMap.get( uuid ).getItemStack() );
        player.getInventory().setItem( 1, bowTypeMap.get( uuid ).getItemStack() );
    }

    public void upgradeSword( Player player ) {
        SwordType swordType = swordTypeMap.get( player.getUniqueId() );
        int index = 0;
        for ( int i = 0; i < SwordType.values().length; i++ ) {
            if ( SwordType.values()[i] == swordType ) {
                index = i;
            }
        }

        if ( index + 1 >= SwordType.values().length ) {
            player.sendMessage( ChatColor.RED + "Du besitzt bereits das höchstes Upgrade des Schwerts" );
            return;
        }

        swordType = SwordType.values()[index + 1];

        if ( hasBalance( player, 3 ) ) {
            withdrawBalance( player, 3 );

            swordTypeMap.put( player.getUniqueId(), swordType );

            player.getInventory().setItem( 0, swordType.getItemStack() );
            player.updateInventory();
            player.sendMessage( ChatColor.GREEN + "Du hast das nächste Upgrade des Schwerts gekauft. Punkte: " + ChatColor.YELLOW + getBalance( player ) );
        } else {
            player.sendMessage( ChatColor.RED + "Dein Punktekonto ist leider nicht gedeckt. Punkte: " + ChatColor.YELLOW + getBalance( player ) );
        }
    }

    public void upgradeBow( Player player ) {
        BowType bowType = bowTypeMap.get( player.getUniqueId() );
        int index = 0;
        for ( int i = 0; i < SwordType.values().length; i++ ) {
            if ( BowType.values()[i] == bowType ) {
                index = i;
            }
        }

        if ( index + 1 >= SwordType.values().length ) {
            player.sendMessage( ChatColor.RED + "Du besitzt bereits das höchstes Upgrade des Bogens" );
            return;
        }

        bowType = BowType.values()[index + 1];

        if ( hasBalance( player, 3 ) ) {
            withdrawBalance( player, 3 );

            bowTypeMap.put( player.getUniqueId(), bowType );

            player.getInventory().setItem( 1, bowType.getItemStack() );
            player.updateInventory();
            player.sendMessage( ChatColor.GREEN + "Du hast das nächste Upgrade des Bogens gekauft. Punkte: " + ChatColor.YELLOW + getBalance( player ) );
        } else {
            player.sendMessage( ChatColor.RED + "Dein Punktekonto ist leider nicht gedeckt. Punkte: " + ChatColor.YELLOW + getBalance( player ) );
        }
    }

    public void buyDefuseKit( Player player ) {
        boolean bought = defuseKitsMap.get( player.getUniqueId() );

        if ( bought ) {
            player.sendMessage( ChatColor.RED + "Du besitzt bereits ein Defusekit" );
        } else {
            if ( hasBalance( player, 3 ) ) {
                withdrawBalance( player, 3 );

                defuseKitsMap.put( player.getUniqueId(), true );

                player.getInventory().setItem( 7, DEFUSEKIT );
                player.sendMessage( ChatColor.GREEN + "Du hast ein Defusekit gekauft. Punkte: " + ChatColor.YELLOW + getBalance( player ) );
            } else {
                player.sendMessage( ChatColor.RED + "Dein Punktekonto ist leider nicht gedeckt. Punkte: " + ChatColor.YELLOW + getBalance( player ) );
            }
        }
    }

    public boolean hasDefusekit( Player player ) {
        return defuseKitsMap.get( player.getUniqueId() );
    }

    private boolean hasBalance( Player player, int amount ) {
        return Team.getPointsObjective().getScore( player.getName() ).getScore() >= amount;
    }

    private int getBalance( Player player ) {
        return Team.getPointsObjective().getScore( player.getName() ).getScore();
    }

    private void withdrawBalance( Player player, int amount ) {
        Bukkit.getPluginManager().callEvent( new PointChangeEvent( player, -amount ) );
    }

}
