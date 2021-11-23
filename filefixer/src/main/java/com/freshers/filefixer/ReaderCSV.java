package com.freshers.filefixer;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ReaderCSV {
    private ArrayList<Record> records;

    public ReaderCSV() {
        records = new ArrayList<Record>();
    }

    public int readData(String path) {
        int counter = 0;

        // String path = "C:/Users/shane/Desktop/UWI/Year 3/Semester 1/COMP 3607/Sample
        // files and CSVs-20211114/sample3/sample3/Sample 3 CSV.csv";

        try {

            List<File> files = Files.list(Paths.get(path))
                                    .filter(Files::isRegularFile)
                                    .filter(name -> name.toString().endsWith(".csv"))
                                    .map(Path::toFile)
                                    .collect(Collectors.toList());
                                    
            FileReader filereader = new FileReader(files.get(0));

            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                String[] pID = nextRecord[0].split("\\s");
                Record r = new Record(pID[1], nextRecord[1], nextRecord[2]);
                records.add(r);
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return counter;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

}
