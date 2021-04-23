package com.availity.homework.services;


import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reads in CSV records
 *
 * @author roy.gustafson
 */
@Service
public class EnrolleeReaderService {


    /**
     * Defines the expected format of incoming CSV
     * <p>
     * Default is ',' as separator, '"' as quote, and '\' as escape
     */
    private static final CSVParser PARSER = new CSVParserBuilder().build();

    /**
     * Defines the expected order of columns
     */
    @Data
    @AllArgsConstructor
    public static class Enrollee {

        static final String[] HEADERS = new String[]{"User Id", "First Name", "Last Name", "Version", /* "Insurance Company" */};

        String userId;
        String firstName;
        String lastName;
        Integer version;
        String insuranceCompany;

        public String[] toRow() {
            return new String[]{userId, firstName, lastName, String.valueOf(version), /*, insuranceCompany */};
        }
    }

    /**
     * Reads a CSV stream
     *
     * @param inputStreamReader
     */
    public void writeToFile(InputStreamReader inputStreamReader) {

        Map<String, List<Enrollee>> enrolleesByInsuranceCompany;
        try {
            enrolleesByInsuranceCompany = readFromStream(inputStreamReader);
        } catch (IOException e) {
            /* logger.log("Error while reading from file", e); */
            return;
        }

        try {
            writeToFile(enrolleesByInsuranceCompany);
        } catch (IOException e) {
            /* logger.log("Error while writing to file", e); */
            return;
        }

    }

    /**
     * Reads bytes of InputStreamReader line by line. If multiple users share the same ID, only keep the highest
     * numbered version, or the most recent record. Next, return the Enrollees mapped by their Insurance Company.
     *
     * @param inputStreamReader the byte stream to read from
     * @return a Collection of Enrollees indexed by Insurance Company and User ID
     * @throws IOException if error while reading CSV data
     */
    private Map<String, List<Enrollee>> readFromStream(InputStreamReader inputStreamReader) throws IOException {

        Map<String, Enrollee> usersById = new HashMap<>();

        try (BufferedReader br = new BufferedReader(inputStreamReader);
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(PARSER).build()) {

            /*
            // Parse the header row for field names
            String[] headers = reader.readNext();
            */

            for (String[] line = reader.readNext(); line != null; line = reader.readNext()) {
                // Pass each field into a predefined Enrollee object
                Enrollee enrollee = new Enrollee(line[0], line[1], line[2], Integer.parseInt(line[4]), line[5]);

                // Keep only the userId with the highest version
                usersById.compute(enrollee.getUserId(), (String k, Enrollee v) -> (v == null || enrollee.version >= v.version) ? enrollee : v);
            }
        } catch (CsvValidationException e) {
            throw new IOException(e);
        }

        // Group by Insurance Company
        return usersById.values().stream().collect(Collectors.groupingBy(Enrollee::getInsuranceCompany));
    }

    private void writeToFile(Map<String, List<Enrollee>> enrolleesByInsuranceCompany) throws IOException {

        // Sort by Last and First
        final Comparator<Enrollee> lastAndFirstSort = Comparator.comparing(Enrollee::getLastName).thenComparing(Enrollee::getFirstName);

        // Iterate over Enrollees
        for (Map.Entry<String, List<Enrollee>> entry : enrolleesByInsuranceCompany.entrySet()) {

            String insuranceCompany = entry.getKey();
            List<Enrollee> enrollees = entry.getValue();

            // For each insurance company, create a new file
            File file = new File(insuranceCompany + ".csv");

            // Open a CSVWriter for that file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                 ICSVWriter writer = new CSVWriterBuilder(bw).build()) {

                // Write header row
                writer.writeNext(Enrollee.HEADERS);

                // Sort Enrollees, convert to String[], then write to file
                enrollees.stream()
                        .sorted(lastAndFirstSort)
                        .map(Enrollee::toRow)
                        .forEach(writer::writeNext);
            }
        }
    }
}
