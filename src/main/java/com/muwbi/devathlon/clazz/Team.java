package com.muwbi.devathlon.clazz;

import com.muwbi.devathlon.SearchAndDestroy;
import com.muwbi.devathlon.event.PointChangeEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
 * <p/>
 * Represents the available teams ingame
 */
public enum Team {

    T( "Terrorists", "Terroristen", ChatColor.RED ),
    CT( "Counter-Terrorists", "Counter-Terroristen", ChatColor.BLUE );

    /**
     * Global scoreboard
     */
    private static Scoreboard scoreboard;

    /**
     * Scoreboard team for spectators
     */
    private static org.bukkit.scoreboard.Team spectatorTeam;

    /**
     * The team's scoreboard team
     */
    @Getter
    private final org.bukkit.scoreboard.Team scoreboardTeam;

    /**
     * The objective responsible for point handling
     */
    @Getter
    private static Objective pointsObjective;

    /**
     * The team's name
     */
    @Getter
    private final String teamName;

    /**
     * The team's full name
     */
    @Getter
    private final String fullTeamName;

    /**
     * The team's color
     */
    @Getter
    private final ChatColor teamColor;

    /**
     * List of the team's members
     */
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

    /**
     * Adds a player represented by his UUID to the team
     *
     * @param uuid the player's UUID
     * @return whether the action was successful or not
     */
    public boolean addMember( UUID uuid ) {
        if ( members.contains( uuid ) ) {
            return false;
        }

        Player player = Bukkit.getPlayer( uuid );

        members.add( uuid );
        scoreboardTeam.addEntry( player.getName() );

        getPointsObjective().getScore( player.getName() ).setScore( 0 );

        SearchAndDestroy.getInstance().getGame().getShopManager().initialize( player );

        Bukkit.getPluginManager().callEvent( new PointChangeEvent( player, 0 ) );

        return true;
    }

    /**
     * Removes a player represented by his UUID from a team
     *
     * @param uuid the player's UUID
     * @return whether the action was successful or not
     */
    public boolean removeMember( UUID uuid ) {
        if ( !members.contains( uuid ) ) {
            return false;
        }

        members.remove( uuid );
        scoreboardTeam.removeEntry( Bukkit.getPlayer( uuid ).getName() );

        return true;
    }

    /**
     * Returns a list of all members alive
     *
     * @return a list of all members alive
     */
    public List<UUID> getAliveMembers() {
        return members.stream().filter( uuid -> !Bukkit.getPlayer( uuid ).isDead() ).collect( Collectors.toList() );
    }

    /**
     * Returns the less populated team
     *
     * @return the less populated team
     */
    public static Team getLessPopulatedTeam() {
        return Team.T.getMembers().size() < Team.CT.getMembers().size() ? Team.T : Team.CT;
    }

    /**
     * Returns the opposite team
     *
     * @return the oppsoite team
     */
    public Team getOtherTeam() {
        return this == Team.T ? Team.CT : Team.T;
    }

    /**
     * Specifies whether the given player represented by his UUID is member of any team
     *
     * @param uuid the player's UUID
     * @return whether the player is in any of the teams
     */
    public static boolean hasTeam( UUID uuid ) {
        return Team.T.getMembers().contains( uuid ) || Team.CT.getMembers().contains( uuid );
    }

    /**
     * Returns the player's team
     *
     * @param uuid the player's UUID
     * @return the player's team
     */
    public static Team getTeam( UUID uuid ) {
        return Team.T.getMembers().contains( uuid ) ? Team.T : ( Team.CT.getMembers().contains( uuid ) ? Team.CT : null );
    }

    /**
     * Sets a player's points
     *
     * @param uuid  the player's UUID
     * @param value new value
     */
    public static void setPoints( UUID uuid, int value ) {
        if ( !hasTeam( uuid ) ) {
            return;
        }

        Score score = getPointsObjective().getScore( Bukkit.getPlayer( uuid ).getName() );
        score.setScore( value );
    }

    /**
     * Removes a player from any teams he is a member of
     *
     * @param uuid the player's UUID
     */
    public static void clearTeam( UUID uuid ) {
        for ( Team team : values() ) {
            team.removeMember( uuid );
        }
    }

    /**
     * Returns the global scoreboard and creates it first if necessary
     *
     * @return the global scoreboard
     */
    public static Scoreboard getScoreboard() {
        if ( scoreboard == null ) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            pointsObjective = getScoreboard().registerNewObjective( "points", "dummy" );
            pointsObjective.setDisplaySlot( DisplaySlot.PLAYER_LIST );
            pointsObjective.setDisplayName( "Punkte" );
        }

        return scoreboard;
    }

    /**
     * Returns the spectators' scoreboard team
     *
     * @return the spectators' scoreboard team
     */
    public static org.bukkit.scoreboard.Team getSpectatorTeam() {
        if ( spectatorTeam == null ) {
            spectatorTeam = getScoreboard().registerNewTeam( "spectator" );
            spectatorTeam.setCanSeeFriendlyInvisibles( true );
        }

        return spectatorTeam;
    }

}
