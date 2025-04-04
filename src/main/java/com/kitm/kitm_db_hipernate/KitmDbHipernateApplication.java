package com.kitm.kitm_db_hipernate;
import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.List;
import com.kitm.kitm_db_hipernate.DAO.PlayerDAO;
import com.kitm.kitm_db_hipernate.entity.Player;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KitmDbHipernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(KitmDbHipernateApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PlayerDAO playerDAO){
		return runner->{
			//createPlayer(playerDAO);
			//findPlayerByID(playerDAO, 3);
			//System.out.println("List before delete player");
			playerList(playerDAO);

			//deletePlayer(playerDAO,3);
			//updatePlayer(playerDAO, 6);
			//deleteAllPlayers(playerDAO);
			//findPlayerByNickname(playerDAO, "Arnaitis");
			//System.out.println("List after delete player");
			//playerList(playerDAO);

			findBestPlayers(playerDAO);

		};
	}

	public void findBestPlayers(PlayerDAO playerDAO){
		System.out.println("Getting best players");
		List<Player> players = playerDAO.findBestPlayers();
		System.out.println("Unsorted list");
		printList(players);
		System.out.println("Sorted list by score");
		sortByScore(players);
		System.out.println("Sorted list by age");
		sortByAge(players);

	}
	public void printList(List<Player> players){
		for (Player player: players){
			System.out.println(player);
		}
	}
	public void sortByScore(List<Player> players) {
		players.sort(Comparator.comparingInt(Player::getScore).reversed());
		printList(players);
	}

	public void sortByAge(List<Player> players) {
		players.sort(Comparator.comparingInt(Player::getAge).reversed());
		printList(players);
	}

	private void deleteAllPlayers(PlayerDAO playerDAO){
		System.out.println("Deleting all players");
		int numRowsDeleted = playerDAO.deleteAll();
		System.out.println("Delete rows count: " + numRowsDeleted);
	}
	private void updatePlayer(PlayerDAO playerDAO, int id){
		System.out.println("Getting player with id: " + id);
		Player player = playerDAO.findByID(id);

		System.out.println("Updating player...");
		player.setScore(1000);

		playerDAO.update(player);

		System.out.println("Updated player: " + player);

	}
	private void deletePlayer(PlayerDAO playerDAO, int id){
		System.out.println("Deleting player with id: "+ id);
		playerDAO.delete(id);
	}
	private void findPlayerByNickname(PlayerDAO playerDAO, String nickname) {
		List<Player> players = playerDAO.findByNickname(nickname);
		System.out.println("Player with nickname: " + nickname);
		for (Player player: players){
			System.out.println(player);
		}
	}

	private void playerList(PlayerDAO playerDAO){
		List<Player> players = playerDAO.findAll();
		System.out.println("Player List in database: ");
		for (Player player: players){
			System.out.println(player);
		}
	}
	private void findPlayerByID(PlayerDAO playerDAO, int id){
		System.out.println("Retrieve player with id: " + id);
		Player player = playerDAO.findByID(id);
		System.out.println(player);
	}
	private void createPlayer(PlayerDAO playerDao){
		System.out.println("Create player object");
		Player playerOne = new Player("Arnas","Arnaitis",20,100);
		System.out.println("Saving player data");
		playerDao.save(playerOne);
		System.out.println("Saved player. Generated ID: " + playerOne.getId());

		System.out.println("Create player object");
		Player playerTwo = new Player("Kestas","Kestaitis",15,200);
		System.out.println("Saving player data");
		playerDao.save(playerTwo);
		System.out.println("Saved player. Generated ID: " + playerTwo.getId());


		System.out.println("Create player object");
		Player playerThree = new Player("Kestas","Kestaitis",15,30);
		System.out.println("Saving player data");
		playerDao.save(playerThree);
		System.out.println("Saved player. Generated ID: " + playerTwo.getId());


		System.out.println("Create player object");
		Player playerFour = new Player("Kestas","Kestaitis",15,50);
		System.out.println("Saving player data");
		playerDao.save(playerFour);
		System.out.println("Saved player. Generated ID: " + playerTwo.getId());


		System.out.println("Create player object");
		Player playerFive = new Player("Kestas","Kestaitis",15,100);
		System.out.println("Saving player data");
		playerDao.save(playerFive);
		System.out.println("Saved player. Generated ID: " + playerTwo.getId());

	}

}
