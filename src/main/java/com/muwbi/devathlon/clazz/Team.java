package com.muwbi.devathlon.clazz;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Muwbi
 */
public enum Team {

    T( "Terrorists" ),
    CT( "Counter-Terrorists" );

    @Getter
    private final String teamName;

    @Getter
    private final List<UUID> members = new ArrayList<>();

    private Team( String name ) {
        teamName = name;
    }

    public boolean addMember( UUID uuid ) {
        if ( members.contains( uuid ) ) {
            return false;
        }

        return members.add( uuid );
    }

    public List<UUID> getAliveMembers() {
        return members.stream().filter( uuid -> !Bukkit.getPlayer( uuid ).isDead() ).collect( Collectors.toList() );
    }

}
