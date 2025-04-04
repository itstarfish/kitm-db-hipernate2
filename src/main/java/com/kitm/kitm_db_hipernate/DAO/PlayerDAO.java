package com.kitm.kitm_db_hipernate.DAO;

import com.kitm.kitm_db_hipernate.entity.Player;

import java.util.List;

public interface PlayerDAO {
    void save (Player player);

    Player findByID(Integer id);
    List<Player> findByNickname(String nickname);
    List<Player> findAll();

    List<Player> findBestPlayers();


    void update(Player player);
    void delete (Integer id);
    int deleteAll();


}
