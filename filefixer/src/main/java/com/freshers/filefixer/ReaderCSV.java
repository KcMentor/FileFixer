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

public class ReaderCSV {
    private ArrayList<Record> records;

    public ReaderCSV() {
        records = new ArrayList<Record>();
    }


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

    /* Creates a record for each row in the CSV file. 
    All records are stored in an ArrayList and the amount of files is returned */
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

    public ArrayList<Record> getRecords() {
        return records;
    }

}
