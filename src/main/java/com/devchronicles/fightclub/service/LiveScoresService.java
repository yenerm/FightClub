package com.devchronicles.fightclub.service;

import com.devchronicles.fightclub.model.Fighter;
import com.devchronicles.fightclub.model.Game;
import com.devchronicles.fightclub.model.Scoreboard;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;


@Stateless
public class LiveScoresService {

        
    @PersistenceContext
    private EntityManager entityManager;
    
	public void addScore(@Observes Game match){
            //Pick random winner
            Fighter owner = match.getHome();
            //Pick random winner
            Fighter winner = match.getHome();
            
            List<Scoreboard> scoreboards= entityManager.createQuery("SELECT c FROM Scoreboard c WHERE c.fighter = :fighterId")
                    .setParameter("fighterId", winner.getId()).getResultList();
            Scoreboard scoreboard=new Scoreboard();
            if (scoreboards!=null && scoreboards.size()>0)
                scoreboard=scoreboards.get(0);
            
            scoreboard.setPoints(scoreboard.getPoints()+3);
            entityManager.merge(scoreboard);
            
	}
	
	public Scoreboard getLatestScores(){
		
		return null;
	}
}
