package com.devchronicles.fightclub.controller;

import com.devchronicles.fightclub.model.Fighter;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mertcaliskan
 * on 12/03/15.
 */
@Named
@SessionScoped
public class FighterBean implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    private List<Fighter> fighters = new ArrayList<>();
    private Fighter newFighter = new Fighter();

    public String addFighter() {
        fighters.add(newFighter);
        entityManager.persist(newFighter);

        newFighter = new Fighter();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fighter added"));

        return null;
    }

    public String removeFighter(Fighter fighter) {
        entityManager.remove(fighter);
        fighters.remove(fighter);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fighter removed"));

        return null;
    }

    public List<Fighter> getFighters() {
        return fighters;
    }

    public Fighter getNewFighter() {
        return newFighter;
    }
}
