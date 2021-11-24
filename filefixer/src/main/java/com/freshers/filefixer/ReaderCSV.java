package com.freshers.filefixer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

/**
 * This class reads a CSV file and creates record objects for each row in the file.
 * The records are stored in an array after being made
 */
public class ReaderCSV {
    /**
     * A collection of record objects created from the CSV data
     */
    private ArrayList<Record> records;

    /**
     * Class constructor
     * Initilizes the ArrayList of records
     */
    public ReaderCSV() {
        records = new ArrayList<Record>();
    }

    /**
     * Locates all the CSV files inside the folder and returns the CSV file that is found
     * If there is more than one CSV file the program cannot work so an error message is displayed
     * @param path Path to the folder containing all the files
     * @return The CSV file that is found id retured
     */
    public File getCSVFile(String path) {
        List<File> files = null;
        try {
            files = Files.list(Paths.get(path))
                .filter(Files::isRegularFile)
                .filter(name -> name.toString().endsWith(".csv"))
                .map(Path::toFile)
                .collect(Collectors.toList());           
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if(files.size() > 1){
            System.out.println("Error! More than one CSV present");
            return null;
        }

        if(files.size() == 0){
            System.out.println("Error! No CSV present");
            return null;
        }

        return files.get(0);
    }

    /**
     * Locates a CSV file and creates a record for each row in the CSV file.
     * All records are then added to the ArrayList
     * @param path Path to folder containing all the PDF files and the CSV
     * @return The amount of records created from the CSV file.
     */
    public int readData(String path) {
        int counter = 0;
        File file = null;
        FileReader filereader = null;


        file = getCSVFile(path);

        if (file == null)
            return -1;

        try {
            filereader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        

        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
        String[] nextRecord;

        try {
            while ((nextRecord = csvReader.readNext()) != null) {
                if(!nextRecord[0].contains("Participant")){
                    continue;
                }
                String[] pID = nextRecord[0].split("\\s");
                Record r = new Record(pID[1], nextRecord[1], nextRecord[2]);
                records.add(r);
                counter++;
            }            
        } catch (IOException|CsvValidationException e) {
            e.printStackTrace();
            return -1;
        }

        return counter;
    }

    /**
     * Gets the collection of records
     * @return The ArrayList of records
     */
    public ArrayList<Record> getRecords() {
        return records;
    }

}
