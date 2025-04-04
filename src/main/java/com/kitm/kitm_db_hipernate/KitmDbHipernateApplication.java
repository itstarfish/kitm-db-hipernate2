package com.kitm.kitm_db_hipernate;
import java.util.Comparator;
import java.util.List;
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
	public CommandLineRunner commandLineRunner(RealEstateDAO realEstateDAO){
		return runner->{
			importRealEstateFromCSV(realEstateDAO, "nt_duomenys.csv");

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

}
