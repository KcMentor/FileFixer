package com.freshers.filefixer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileFixer {
    private ArrayList<Search> Searches;
    private ArrayList<Record> Records;
    private ArrayList<PDF> PDFs;
    private ReaderCSV readerCSV;
    private FileNameReaderPDF readerPDF;
    private String dir;
    private String subfolderName;

    public FileFixer(String dir, String subfolderName) {
        this.dir = dir;
        this.subfolderName = subfolderName;
        this.Searches = new ArrayList<Search>();
        this.Records = new ArrayList<Record>();
        this.PDFs = new ArrayList<PDF>();

    }

    public boolean renameFile(Record record, PDF pdf) {
        Path dest = Paths.get(dir, subfolderName);
        String newName = record.getFullName() + "_" + record.getParticipantID() + "_assignsubmission_file_" + pdf.getName();
        try {
            Files.move(pdf.getPdf().toPath(), dest.resolve(Paths.get(newName)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
