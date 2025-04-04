package com.kitm.kitm_db_hipernate.utility;

import com.kitm.kitm_db_hipernate.entity.RealEstate;
import com.kitm.kitm_db_hipernate.dao.RealEstateDAO;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RealEstateImporter {

    public void importFromCsv(RealEstateDAO realEstateDao, String csvFile) {
        String row;
        String csvSplitBy = ",";
        int errorCount = 0;
        int goodCount = 0;

        File file = new File(csvFile);

        if (!file.exists()) {
            System.out.println("Failas nerastas: " + csvFile);
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            br.readLine();

            while ((row = br.readLine()) != null) {
                String[] fields = row.split(csvSplitBy, -1);

                RealEstate realEstate = parseRealEstate(fields);
                if (realEstate != null) {
                    realEstateDao.save(realEstate);
                    goodCount++;
                } else {
                    errorCount++;
                    System.out.println("Neteisinga eilutė: " + row);
                }
            }
            System.out.println("-------------------------------");
            System.out.println("Neteisingų eilučių importavime: " + errorCount);
            System.out.println("Teisingų eilučių importavime: " + goodCount);
            System.out.println("-------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RealEstate parseRealEstate(String[] fields) {
        try {
            if(fields[1].isEmpty()){
                return null;
            }
            String address = fields[1];
            Double areaSqrM = parseDouble(fields[2], -1.0);
            if (areaSqrM < 0) {
                return null;
            }
            Double priceEur = parseDouble(fields[3], -1.0);
            if (priceEur < 0) {
                return null;
            }
            Integer score = parseInt(fields[4], -1);
            if (score < 0 || score > 10) {
                return null;
            }
            if (fields[5].isEmpty()){
                return null;
            }
            String type = fields[5];

            Boolean vip = fields[6].isEmpty() ? false : Boolean.parseBoolean(fields[6]);

            return new RealEstate(address, areaSqrM, priceEur, score, type, vip);

        } catch (Exception e) {
            return null;
        }
    }
    private Double parseDouble(String value, Double testValue) {
        try {
            double parsed = Double.parseDouble(value);
            return Double.isNaN(parsed) ? testValue : parsed;
        } catch (NumberFormatException e) {
            return testValue;
        }
    }

    private Integer parseInt(String value, Integer testValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return testValue;
        }
    }

}
