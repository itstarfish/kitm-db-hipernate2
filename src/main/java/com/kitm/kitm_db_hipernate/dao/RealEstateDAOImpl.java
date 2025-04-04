package com.kitm.kitm_db_hipernate.dao;

import com.kitm.kitm_db_hipernate.entity.RealEstate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RealEstateDAOImpl implements RealEstateDAO {
   private EntityManager entityManager;

   @Autowired
   public RealEstateDAOImpl(EntityManager entityManager){
       this.entityManager = entityManager;
   }
   @Override
   @Transactional
   public void save(RealEstate realEstate){
       entityManager.persist(realEstate);
   }

    @Override
    public RealEstate findByID(Integer id) {
        return entityManager.find(RealEstate.class, id);
    }

    @Override
    public List<RealEstate> findAll() {
        return entityManager.createQuery("SELECT s FROM RealEstate s", RealEstate.class).getResultList();
    }

    @Override
    public List<RealEstate> findByType(String type) {
        //Create query
        TypedQuery<RealEstate> query = entityManager.createQuery("FROM RealEstate WHERE type=:data",RealEstate.class);
        //set parameter
        query.setParameter("data", type);
        //return results
        return query.getResultList();
    }

    @Override
    public List<RealEstate> findBestRealEstates() {
        //Create query
        TypedQuery<RealEstate> query = entityManager.createQuery("FROM RealEstate WHERE score >= 8 ORDER BY score DESC",RealEstate.class);
        //return results
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(RealEstate realEstate) {
        entityManager.merge(realEstate);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
       //retreive realEstate
        RealEstate realEstate = entityManager.find(RealEstate.class,id);
        //delete realEstate
        entityManager.remove(realEstate);
    }

    @Override
    @Transactional
    public int deleteAll() {
        int numberDeleted = entityManager.createQuery("DELETE FROM RealEstate").executeUpdate();

        return numberDeleted;
   }

}
