package com.muwbi.devathlon.config;

import com.muwbi.devathlon.SearchAndDestroy;
import lombok.Getter;
import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import org.bukkit.Location;

import java.io.File;

/**
 * Created by Muwbi
 */
public class GameConfig extends Config {

    @Getter
    @Comment( "Spawn location for lobby phase" )
    private Location lobbySpawn;

    @Getter
    @Comment( "Enables/disables fall damage" )
    private boolean fallDamage;

    @Getter
    @Comment( "Specifies whether other players' names should be visible above their head" )
    private boolean nameVisible;

    @Getter
    @Comment( "Time needed to plant the bomb (in seconds)" )
    private double plantTime;

    @Getter
    @Comment( "Time needed to defuse the bomb (in seconds)" )
    private double defuseTime;

    public GameConfig() {
        CONFIG_FILE = new File( SearchAndDestroy.getInstance().getDataFolder(), "game.yml" );
    }

}
