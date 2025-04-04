package com.kitm.kitm_db_hipernate.dao;

import com.kitm.kitm_db_hipernate.entity.RealEstate;

import java.util.List;

public interface RealEstateDAO {
    void save (RealEstate realEstate);

    RealEstate findByID(Integer id);

    List<RealEstate> findAll();
    List<RealEstate> findByType(String type);

    List<RealEstate> findBestRealEstates();


    void update(RealEstate realEstate);
    void delete (Integer id);
    int deleteAll();


}
