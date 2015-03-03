package com.devchronicles.fightclub.model;

import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//@Entity
public class Schedule{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Fighter owner;
    @ManyToOne
    private Set<Game> games;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fighter getOwner() {
        return owner;
    }

    public void setOwner(Fighter owner) {
        this.owner = owner;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setMatches(Set<Game> games) {
        this.games = games;
    }
    
    
}
