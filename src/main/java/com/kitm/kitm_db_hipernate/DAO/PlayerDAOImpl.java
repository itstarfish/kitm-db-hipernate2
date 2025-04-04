package com.kitm.kitm_db_hipernate.DAO;

import com.kitm.kitm_db_hipernate.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerDAOImpl implements PlayerDAO {
   private EntityManager entityManager;

   @Autowired
   public PlayerDAOImpl(EntityManager entityManager){
       this.entityManager = entityManager;
   }
   @Override
   @Transactional
   public void save(Player player){
       entityManager.persist(player);
   }

    @Override
    public Player findByID(Integer id) {
        return entityManager.find(Player.class, id);
    }

    @Override
    public List<Player> findByNickname(String nickname) {
       //Create query
        TypedQuery<Player> query = entityManager.createQuery("FROM Player WHERE nickname=:data",Player.class);
        //set parameter
        query.setParameter("data", nickname);
        //return results
        return query.getResultList();
    }

    @Override
    public List<Player> findAll() {
        return entityManager.createQuery("SELECT s FROM Player s", Player.class).getResultList();
    }

    @Override
    public List<Player> findBestPlayers() {
        //Create query
        TypedQuery<Player> query = entityManager.createQuery("FROM Player ORDER BY score DESC LIMIT 3",Player.class);
        //return results
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Player player) {
        entityManager.merge(player);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
       //retreive player
        Player player = entityManager.find(Player.class,id);
        //delete player
        entityManager.remove(player);
    }

    @Override
    @Transactional
    public int deleteAll() {
        int numberDeleted = entityManager.createQuery("DELETE FROM Player").executeUpdate();

        return numberDeleted;
   }

}
