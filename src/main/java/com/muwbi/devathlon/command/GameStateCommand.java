package com.muwbi.devathlon.command;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.clazz.GameState;
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
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("gamestate")) {
            if (args.length > 0) {
                if (!args[0].equalsIgnoreCase("get")) {
                    GameState gameState;
                    if (args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("warmup") || args[0].equalsIgnoreCase("ingame") || args[0].equalsIgnoreCase("ingame_planted")) {
                        switch (args[0].toLowerCase()) {
                            case "lobby":
                            case "warmup":
                            case "ingame":
                            case "ingame_planted":
                                gameState = GameState.valueOf(args[0].toUpperCase());
                                break;
                            default:
                                gameState = GameState.LOBBY;
                                break;

                        }
                        SearchAndDestroy.getInstance().getGame().changeGameState(gameState);
                    } else {
                        commandSender.sendMessage(ChatColor.RED + "Verwendung: /gamestate [LOBBY|WARMUP|INGAME|INGAME_PLANTED]");
                        return true;
                    }
                } else {
                    commandSender.sendMessage("Current gamestate: " + SearchAndDestroy.getInstance().getGame().getGameState());
                }


            }
        }
            return true;

        }

    }

