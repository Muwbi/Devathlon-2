package com.muwbi.devathlon.clazz;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
    }

    public boolean addMember( UUID uuid ) {
        return !members.contains( uuid ) && members.add( uuid );
    }

    public boolean removeMember( UUID uuid ) {
        return !members.contains( uuid ) && members.remove( uuid );
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

}
