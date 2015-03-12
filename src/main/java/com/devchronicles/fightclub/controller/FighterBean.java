package com.devchronicles.fightclub.controller;

import com.devchronicles.fightclub.model.Fighter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mertcaliskan
 * on 12/03/15.
 */
@Named
@SessionScoped
public class FighterBean implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    private Set<Fighter> fighters = new HashSet<>();
    private Fighter newFighter = new Fighter();

    @PostConstruct
    public void setup() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Fighter.class));
        fighters = new HashSet<>(entityManager.createQuery(cq).getResultList());
    }

    public String addFighter() {
        fighters.add(newFighter);
        String msg;
        if (newFighter.getId() != null) {
            entityManager.merge(newFighter);
            msg = "Fighter updated";
        }
        else {
            entityManager.persist(newFighter);
            msg = "Fighter added";
        }

        newFighter = new Fighter();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));

        return null;
    }

    public String updateFighter(Fighter fighter) {
        newFighter = fighter;
        return null;
    }

    public String removeFighter(Fighter fighter) {
        entityManager.remove(fighter);
        fighters.remove(fighter);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fighter removed"));

        return null;
    }

    public Set<Fighter> getFighters() {
        return fighters;
    }

    public Fighter getNewFighter() {
        return newFighter;
    }
}
