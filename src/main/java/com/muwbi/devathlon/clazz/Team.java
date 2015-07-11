package com.muwbi.devathlon.clazz;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.event.PointChangeEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Muwbi
 */
public enum Team {

    T( "Terrorists", "Terroristen", ChatColor.RED ),
    CT( "Counter-Terrorists", "Counter-Terroristen", ChatColor.BLUE );

    private static Scoreboard scoreboard;

    @Getter
    private final org.bukkit.scoreboard.Team scoreboardTeam;

    @Getter
    private static Objective pointsObjective;

    @Getter
    private final String teamName;

    @Getter
    private final String fullTeamName;

    @Getter
    private final ChatColor teamColor;

    @Getter
    private final List<UUID> members = new ArrayList<>();


    private Team( String name, String fullName, ChatColor color ) {
        teamName = name;
        fullTeamName = fullName;
        teamColor = color;

        scoreboardTeam = getScoreboard().registerNewTeam( name() );
        scoreboardTeam.setPrefix( getTeamColor() + name() + ChatColor.GRAY + " | " + ChatColor.RESET );
        scoreboardTeam.setAllowFriendlyFire( false );
    }

    public boolean addMember( UUID uuid ) {
        if ( members.contains( uuid ) ) {
            return false;
        }

        Player player = Bukkit.getPlayer( uuid );

        members.add( uuid );
        scoreboardTeam.addEntry( player.getName() );

        getPointsObjective().getScore( player.getName() ).setScore( 0 );

        player.setScoreboard( getScoreboard() );

        SearchAndDestroy.getInstance().getGame().getShopManager().initialize( player );

        Bukkit.getPluginManager().callEvent( new PointChangeEvent( player, 0 ) );

        return true;
    }

    public boolean removeMember( UUID uuid ) {
        if ( !members.contains( uuid ) ) {
            return false;
        }

        members.remove( uuid );
        scoreboardTeam.removeEntry( Bukkit.getPlayer( uuid ).getName() );

        return true;
    }

    public List<UUID> getAliveMembers() {
        return members.stream().filter( uuid -> !Bukkit.getPlayer( uuid ).isDead() ).collect( Collectors.toList() );
    }

    public static Team getLessPopulatedTeam() {
        return Team.T.getMembers().size() < Team.CT.getMembers().size() ? Team.T : Team.CT;
    }

    public static boolean hasTeam( UUID uuid ) {
        return Team.T.getMembers().contains( uuid ) || Team.CT.getMembers().contains( uuid );
    }

    public static Team getTeam( UUID uuid ) {
        return Team.T.getMembers().contains( uuid ) ? Team.T : ( Team.CT.getMembers().contains( uuid ) ? Team.CT : null );
    }

    public static void setPoints( UUID uuid, int value ) {
        if ( !hasTeam( uuid ) ) {
            return;
        }

        Score score = getPointsObjective().getScore( Bukkit.getPlayer( uuid ).getName() );
        score.setScore( value );
    }

    public static void clearTeam( UUID uuid ) {
        for ( Team team : values() ) {
            team.removeMember( uuid );
        }
    }

    private static Scoreboard getScoreboard() {
        if ( scoreboard == null ) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            pointsObjective = getScoreboard().registerNewObjective( "points", "dummy" );
            pointsObjective.setDisplaySlot( DisplaySlot.PLAYER_LIST );
            pointsObjective.setDisplayName( "Punkte" );
        }

        return scoreboard;
    }

}
