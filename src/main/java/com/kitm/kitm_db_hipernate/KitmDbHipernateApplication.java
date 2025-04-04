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

		};
	}

	public void findBestRealEstates(RealEstateDAO realEstateDAO){
		System.out.println("Gauname geriausius NT objektus (įvertinimas >= 8)");
		List<RealEstate> realEstates = realEstateDAO.findBestRealEstates();
		System.out.println("Nerūšiuotas sąrašas");
		printList(realEstates);
		System.out.println("Rūšiuotas pagal įvertinimus");
		sortByScore(realEstates);
		System.out.println("Rūšiuotas pagal kainą");
		sortByPrice(realEstates);
		System.out.println("Rūšiuotas pagal plotą");
		sortByArea(realEstates);

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

	public void sortByArea(List<RealEstate> realEstates) {
		realEstates.sort(Comparator.comparingDouble(RealEstate::getAreaSqrM).reversed());
		printList(realEstates);
	}

	private void deleteAllRealEstates(RealEstateDAO realEstateDAO){
		System.out.println("Triname visą nekilnojamajį turtą iš duomenų bazės");
		int numRowsDeleted = realEstateDAO.deleteAll();
		System.out.println("Ištrintų įrašų skaičius: " + numRowsDeleted);
	}
	private void updateRealEstate(RealEstateDAO realEstateDAO, int id){
		System.out.println("Gauname nekilnojamajį turtą su id: " + id);
		RealEstate realEstate = realEstateDAO.findByID(id);

		System.out.println("Atnaujinamas nekilnojamas turtas į VIP...");
		realEstate.setVip(true);

		realEstateDAO.update(realEstate);

		System.out.println("Atnaujintas nekilnojamas turtas: " + realEstate);

	}
	private void deleteRealEstate(RealEstateDAO realEstateDAO, int id){
		System.out.println("Triname nekilnojamajį turtą su id: "+ id);
		realEstateDAO.delete(id);
	}

	private void realEstateList(RealEstateDAO realEstateDAO){
		List<RealEstate> realEstates = realEstateDAO.findAll();
		System.out.println("Nekilnojamo turto sąrašas duomenų bazėje: ");
		for (RealEstate realEstate: realEstates){
			System.out.println(realEstate);
		}
	}

	private void findRealEstateByType(RealEstateDAO realEstateDao, String type) {
		List<RealEstate> realEstates = realEstateDao.findByType(type);
		System.out.println("Nekilnojamas turtas su tipu : " + type);
		for (RealEstate realEstate: realEstates){
			System.out.println(realEstate);
		}
	}
	private void findRealEstateByID(RealEstateDAO realEstateDAO, int id){
		System.out.println("Gauname nekilnojamą turtą pagal id: " + id);
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
		int choice2 = -1;

		while (choice != 6) {
			System.out.println("===== NT Sistemos Meniu =====");
			System.out.println("1. Importuoti NT duomenis iš CSV");
			System.out.println("2. Rodyti visus NT objektus");
			System.out.println("3. Rodyti geriausius NT objektus (įvertinimas >= 8)");
			System.out.println("4. Pažymėti geriausius objektus kaip VIP");
			System.out.println("5. Ištrinti visus duomenis");
			System.out.println("6. Išeiti");
			System.out.print("Pasirinkite: ");

			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {
					case 1:
						String csvFile = "nt_duomenys.csv";
						System.out.println("Ar norite įkelti duomenis iš nt_duomenys.csv? taip/ne");
						if (scanner.hasNextLine()) {
							if(scanner.nextLine().equals("ne")){
								System.out.println("Įveskite duomenų failo panadinimą su keliu iki jo:");
								if (scanner.hasNextLine()) {
									csvFile = scanner.nextLine();
								}
							}
						}
						System.out.println("Importuojami NT duomenys iš " + csvFile);
						importRealEstateFromCSV(realEstateDao, csvFile);
						break;
					case 2:
						System.out.println("Visi NT objektai:");
						realEstateList(realEstateDao);

						System.out.println("===== Rūšiavimas ir paieška =====");
						System.out.println("1. Rūšiuoti pagal kainą");
						System.out.println("2. Rūšiuoti pagal įvertinimą");
						System.out.println("3. Rūšiuoti pagal plotą");
						System.out.println("4. Paieška pagal tipą");
						System.out.println("5. Grįžti į pagrindinį meniu");
						if (scanner.hasNextInt()) {
							choice2 = scanner.nextInt();
							scanner.nextLine();

							switch (choice2) {
								case 1:
									System.out.print("Rūšiavimas pagal kainą");
									sortByPrice(realEstateDao.findAll());
									break;
								case 2:
									System.out.println("Rūšiavimas pagal įvertinimą:");
									sortByScore(realEstateDao.findAll());
									break;
								case 3:
									System.out.println("Rūšiavimas pagal plotą");
									sortByArea(realEstateDao.findBestRealEstates());
									break;
								case 4:
									System.out.println("Paieška pagal tipą");
									System.out.println("Kokio tipo nekilnojamojo turto tipo ieškote?");
									if (scanner.hasNextLine()) {
										findRealEstateByType(realEstateDao, scanner.nextLine());
									}
									break;
								case 5:
									System.out.println("Grįžtame į pagrindinį menių");
									break;
								default:
									System.out.println("Neteisingas pasirinkimas.");
							}
						}
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
						System.out.println("Ištriname visus duomenis iš db");
						realEstateDao.deleteAll();
						break;
					case 6:
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
