package com.kitm.kitm_db_hipernate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.kitm.kitm_db_hipernate.dao.RealEstateDAO;
import com.kitm.kitm_db_hipernate.entity.RealEstate;
import com.kitm.kitm_db_hipernate.utility.RealEstateImporter;
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
	public CommandLineRunner commandLineRunner(RealEstateDAO realEstateDao){
		return runner->{

			menu(realEstateDao);
			//realEstateList(realEstateDAO);

			//findBestRealEstates(realEstateDAO);

		};
	}

	public void findBestRealEstates(RealEstateDAO realEstateDAO){
		System.out.println("Getting best realEstates");
		List<RealEstate> realEstates = realEstateDAO.findBestRealEstates();
		System.out.println("Unsorted list");
		printList(realEstates);
		System.out.println("Sorted list by score");
		sortByScore(realEstates);
		System.out.println("Sorted list by price");
		sortByPrice(realEstates);

	}
	public void printList(List<RealEstate> realEstates){
		for (RealEstate realEstate: realEstates){
			System.out.println(realEstate);
		}
	}
	public void sortByScore(List<RealEstate> realEstates) {
		realEstates.sort(Comparator.comparingInt(RealEstate::getScore).reversed());
		printList(realEstates);
	}

	public void sortByPrice(List<RealEstate> realEstates) {
		realEstates.sort(Comparator.comparingDouble(RealEstate::getPriceEur));
		printList(realEstates);
	}
	private void deleteAllRealEstates(RealEstateDAO realEstateDAO){
		System.out.println("Deleting all realEstates");
		int numRowsDeleted = realEstateDAO.deleteAll();
		System.out.println("Delete rows count: " + numRowsDeleted);
	}
	private void updateRealEstate(RealEstateDAO realEstateDAO, int id){
		System.out.println("Getting realEstate with id: " + id);
		RealEstate realEstate = realEstateDAO.findByID(id);

		System.out.println("Updating realEstate to VIP...");
		realEstate.setVip(true);

		realEstateDAO.update(realEstate);

		System.out.println("Updated realEstate: " + realEstate);

	}
	private void deleteRealEstate(RealEstateDAO realEstateDAO, int id){
		System.out.println("Deleting realEstate with id: "+ id);
		realEstateDAO.delete(id);
	}

	private void realEstateList(RealEstateDAO realEstateDAO){
		List<RealEstate> realEstates = realEstateDAO.findAll();
		System.out.println("RealEstate List in database: ");
		for (RealEstate realEstate: realEstates){
			System.out.println(realEstate);
		}
	}
	private void findRealEstateByID(RealEstateDAO realEstateDAO, int id){
		System.out.println("Retrieve realEstate with id: " + id);
		RealEstate realEstate = realEstateDAO.findByID(id);
		System.out.println(realEstate);
	}
	private void importRealEstateFromCSV(RealEstateDAO realEstateDao, String csvFile){
		RealEstateImporter importer = new RealEstateImporter();
		importer.importFromCsv(realEstateDao, csvFile);
	}

	public void menu(RealEstateDAO realEstateDao){
		Scanner scanner = new Scanner(System.in);

		int choice = -1;

		while (choice != 5) {
			System.out.println("===== NT Sistemos Meniu =====");
			System.out.println("1. Importuoti NT duomenis iš CSV");
			System.out.println("2. Rodyti visus NT objektus");
			System.out.println("3. Rodyti geriausius NT objektus (įvertinimas >= 8)");
			System.out.println("4. Pažymėti geriausius objektus kaip VIP");
			System.out.println("5. Išeiti");
			System.out.print("Pasirinkite: ");

			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {
					case 1:
						System.out.print("Importuojami NT duomenys iš nt_duomenys.csv");
						//String csvFile = scanner.nextLine();
						//importRealEstateFromCSV(realEstateDao,csvFile);
						importRealEstateFromCSV(realEstateDao, "nt_duomenys.csv");
						break;
					case 2:
						System.out.println("Visi NT objektai:");
						realEstateList(realEstateDao);
						break;
					case 3:
						System.out.println("Geriausi NT objektai (įvertinimas >= 8):");
						sortByScore(realEstateDao.findBestRealEstates());
						break;
					case 4:
						System.out.println("Pažymime geriausius obijektus kaip VIP");
						for (RealEstate realEstate : realEstateDao.findBestRealEstates()){
							realEstate.setVip(true);
							realEstateDao.update(realEstate);
						}
						System.out.println("Geriausi objektai pažymėti kaip VIP.");
						break;
					case 5:
						System.out.println("Išeiname");
						break;
					default:
						System.out.println("Neteisingas pasirinkimas.");
				}
			} else {
				System.out.println("Įveskite skaičių");
				scanner.nextLine();
			}
			System.out.println();
		}
		scanner.close();
	}
}
