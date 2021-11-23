package com.freshers.filefixer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileFixer {
    private ArrayList<Search> searches;
    private ArrayList<Record> records;
    private ArrayList<PDF> PDFs;
    private ReaderCSV readerCSV;
    private FileNameReaderPDF readerPDF;
    private String dir;
    private String subfolderName;

    public FileFixer(String dir, String subfolderName) {
        this.dir = dir;
        this.subfolderName = subfolderName;
        this.searches = new ArrayList<Search>();
        this.records = new ArrayList<Record>();
        this.PDFs = new ArrayList<PDF>();
        this.readerCSV = new ReaderCSV();
        this.readerPDF = new FileNameReaderPDF();

        searches.add(new SearchParticipantID());
        searches.add(new SearchStudentID());
        searches.add(new SearchName());
    }

    public void fixFiles() {
        ArrayList<PDF> fixedPDFs = new ArrayList<PDF>();
        int count = 0, index;
        readerCSV.readData(dir);
        readerPDF.readData(dir);
        records = readerCSV.getRecords();
        PDFs = readerPDF.getPdfs();
        String key = null;

        for (Search s : searches) {
            if(PDFs.isEmpty()) {
                break;
            }

            for (Record r : records) {
                if (count == 0) {
                    key = r.getParticipantID();
                } else if (count == 1) {
                    key = r.getStudentID();
                } else {
                    key = r.getFullName();
                }

                index = s.search(key, PDFs);
                if (index > -1) {
                    renameFile(r, PDFs.get(index));
                    PDFs.remove(index);
                }

            }

            count++;
        }

    }

    public boolean renameFile(Record record, PDF pdf) {
        Path dest = Paths.get(dir, subfolderName);
        String newName = record.getFullName() + "_" + record.getParticipantID() + "_assignsubmission_file_"
                + pdf.getName();
        try {
            Files.move(pdf.getPdf().toPath(), dest.resolve(Paths.get(newName)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
