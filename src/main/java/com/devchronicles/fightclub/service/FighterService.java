package com.devchronicles.fightclub.service;

import com.devchronicles.fightclub.model.Fighter;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.enterprise.event.Event;

@Stateless
@Path("fighter")
public class FighterService {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject 
    Event<Fighter> event;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Fighter entity) {

        Fighter fighter = new Fighter();
        fighter.setName("Murat");
        fighter.setLastname("Yener");

        entityManager.persist(fighter);
        event.fire(fighter);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        entityManager.remove(entityManager.find(Fighter.class, id));
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void update(Fighter fighter) {
        entityManager.merge(fighter);
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Fighter getFighter(@PathParam("id") Long id){
        return entityManager.find(Fighter.class, id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @javax.enterprise.inject.Produces
    public List<Fighter> getAllFighters(){
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Fighter.class));
        return entityManager.createQuery(cq).getResultList();
    }
}
