package com.muwbi.devathlon.command;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
import com.muwbi.devathlon.clazz.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Muwbi
 */
public class GameStateCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String label, String[] args ) {
        if ( command.getName().equalsIgnoreCase( "gamestate" ) ) {
            if ( args.length > 0 ) {
                GameState gameState;
                switch ( args[0].toLowerCase() ) {
                    case "lobby":
                    case "warmup":
                    case "ingame":
                    case "ingame_planted":
                        gameState = GameState.valueOf( args[0].toUpperCase() );
                        break;
                    case "addpoint":
                        if ( commandSender instanceof Player ) {
                            Team.addPoints( ( (Player) commandSender ).getUniqueId(), 3 );
                        }
                    default:
                        gameState = GameState.LOBBY;
                        break;
                }

                SearchAndDestroy.getInstance().getGame().changeGameState( gameState );
            } else {
                commandSender.sendMessage( ChatColor.RED + "Verwendung: /gamestate [LOBBY|WARMUP|INGAME|INGAME_PLANTED]" );
                return true;
            }
        }

        return true;
    }

}
