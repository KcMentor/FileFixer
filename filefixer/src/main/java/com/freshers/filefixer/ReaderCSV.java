package com.freshers.filefixer;

import java.io.FileReader;
import java.util.ArrayList;
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

            FileReader filereader = new FileReader(path);

            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                String[] pID = nextRecord[0].split("\\s");
                Record r = new Record(pID[1], nextRecord[1], nextRecord[2], nextRecord[3]);
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
