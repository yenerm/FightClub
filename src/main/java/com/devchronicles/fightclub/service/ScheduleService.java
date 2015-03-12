package com.devchronicles.fightclub.service;

import com.devchronicles.fightclub.model.Fighter;
import com.devchronicles.fightclub.model.Game;
import com.devchronicles.fightclub.model.Schedule;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class ScheduleService {

    //@EJB
    //FighterService fighterService;

    @Resource
    TimerService timerService;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    List<Fighter> fighters;

    @Inject
    Event<Game> lastScore;

    @Asynchronous
    public void createSchedule(@Observes Fighter owner) {

        //TODO first step
        //List<Fighter> fighters = fighterService.getAllFighters();

        Schedule schedule = new Schedule();
        schedule.setOwner(owner);
        //TODO Shuffle and remove self

        entityManager.persist(schedule);

        timerService.createTimer(5000, 5000, schedule.getId());
    }

    @Timeout
    public void execute(Timer timer) {
        Long id = (Long) timer.getInfo();
        Schedule schedule = entityManager.find(Schedule.class, id);
        playNextMatch(schedule);
    }


    //@javax.ejb.Schedule(second="*/1", minute="*",hour="*", persistent=false)

    public void playNextMatch(Schedule schedule) {

        //play next in schedule..
        Game[] games = schedule.getGames().toArray(new Game[schedule.getGames().size()]);

        Game current = games[0];
        schedule.getGames().remove(current);

        lastScore.fire(current);
    }


}
