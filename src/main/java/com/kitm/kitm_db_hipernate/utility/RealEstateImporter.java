package com.kitm.kitm_db_hipernate.utility;

import com.kitm.kitm_db_hipernate.entity.RealEstate;
import com.kitm.kitm_db_hipernate.dao.RealEstateDAO;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RealEstateImporter {

    public void importFromCsv(RealEstateDAO realEstateDao, String csvFile) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSplitBy, -1);

                RealEstate realEstate = parseRealEstate(fields);
                System.out.println(realEstate);
                if (realEstate != null) {
                    //realEstateDao.save(realEstate);
                    System.out.println("Saved realEstate. ID: " + realEstate.getId());
                } else {
                    System.out.println("Skipping invalid row: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        private RealEstate parseRealEstate(String[] fields) {
        try {
            String address = fields[1].isEmpty() ? "Adresas nežinomas" : fields[1];

            Double areaSqrM = parseDouble(fields[2], 0.0);
            Double priceEur = parseDouble(fields[3], 0.0);
            Integer score = parseInt(fields[4], 0);
            String type = fields[5].isEmpty() ? "Nežinomas" : fields[5];
            Boolean vip = fields[6].isEmpty() ? false : Boolean.parseBoolean(fields[6]);

            return new RealEstate(address, areaSqrM, priceEur, score, type, vip);
        } catch (Exception e) {
            return null;
        }
    }
    private Double parseDouble(String value, Double defaultValue) {
        try {
            double parsed = Double.parseDouble(value);
            return Double.isNaN(parsed) ? defaultValue : parsed;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Integer parseInt(String value, Integer defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
