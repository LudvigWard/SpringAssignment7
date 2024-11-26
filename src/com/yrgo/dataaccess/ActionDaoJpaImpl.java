package com.yrgo.dataaccess;

import com.yrgo.domain.Action;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ActionDaoJpaImpl implements ActionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Action newAction) {
        em.persist(newAction);
    }

    @Override
    public List<Action> getIncompleteActions(String userId) {
        TypedQuery<Action> query = em.createQuery(
                "SELECT a FROM Action a WHERE a.owningUser = :userId AND a.complete = :complete", Action.class);
        query.setParameter("userId", userId);
        query.setParameter("complete", false);
        return query.getResultList();
    }

    @Override
    public void update(Action actionToUpdate) throws RecordNotFoundException {
        Action existingAction = em.find(Action.class, actionToUpdate.getActionId());
        if (existingAction == null) {
            throw new RecordNotFoundException();
        }
        em.merge(actionToUpdate);
    }

    @Override
    public void delete(Action oldAction) throws RecordNotFoundException {
        Action action = em.find(Action.class, oldAction.getActionId());
        if (action == null) {
            throw new RecordNotFoundException();
        }
        em.remove(action);
    }
}
