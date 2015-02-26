package com.devchronicles.fightclub.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity (name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Fighter home;
    @ManyToOne(fetch = FetchType.EAGER)
    private Fighter away;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fighter getHome() {
        return home;
    }

    public void setHome(Fighter home) {
        this.home = home;
    }

    public Fighter getAway() {
        return away;
    }

    public void setAway(Fighter away) {
        this.away = away;
    }

}
