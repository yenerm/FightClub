package com.devchronicles.fightclub.service;

import com.devchronicles.fightclub.model.Fighter;
import com.devchronicles.fightclub.model.Game;
import com.devchronicles.fightclub.model.Scoreboard;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@Stateless
@ServerEndpoint("/livescores")
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
	
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(Long id, Session session) throws IOException, EncodeException {
        List<Scoreboard> scoreboards= entityManager.createQuery("SELECT c FROM Scoreboard c WHERE c.fighter = :fighterId")
                    .setParameter("fighterId", id).getResultList();
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendObject(scoreboards.get(0));
            }
        }
    }
    
    @OnOpen
    public void onOpen (Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose (Session peer) {
        peers.remove(peer);
    }
}
