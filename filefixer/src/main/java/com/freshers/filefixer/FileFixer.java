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

        int count = 0, index, indexR = 0;
        readerCSV.readData(dir);
        readerPDF.readData(dir);
        records = readerCSV.getRecords();
        PDFs = readerPDF.getPdfs();
        checkFormatted();

        for (Search s : searches) {
            if (!PDFs.isEmpty()) {
                for (Record r : records) {
                    if (count == 0) {
                        index = s.search(r.getParticipantID(), PDFs);
                        if (index > -1) {
                            renameFile(r, PDFs.get(index));
                            PDFs.remove(index);
                            records.remove(indexR);
                        }
                    } else if (count == 1) {
                        index = s.search(r.getStudentID(), PDFs);
                        if (index > -1) {
                            renameFile(r, PDFs.get(index));
                            PDFs.remove(index);
                            records.remove(indexR);
                        }
                    } else {
                        index = s.search(r.getFullName(), PDFs);
                        if (index > -1) {
                            renameFile(r, PDFs.get(index));
                            PDFs.remove(index);
                            records.remove(indexR);
                        }
                    }
                    indexR++;
                }
            } else {
                break;
            }
            count++;
        }

    }

    public boolean checkFormatted() {
        boolean changed = false;
        int index;
        SearchFileFormat searchFileFormat = new SearchFileFormat();

        String regex = "";
        for (Record r : records) {
            regex = "^" + r.getFullName() + "_" + r.getParticipantID() + "_assignsubmission_file_.*\\.pdf$";
            index = searchFileFormat.search(regex, PDFs);
            if (index > -1) {
                addFile(PDFs.get(index), PDFs.get(index).getName());
                PDFs.remove(index);
                changed = true;
            }
        }

        return changed;
    }

    public boolean renameFile(Record record, PDF pdf) {
        String newName = record.getFullName() + "_" + record.getParticipantID() + "_assignsubmission_file_"
                + pdf.getName();
        return addFile(pdf, newName);
    }

    public boolean addFile(PDF pdf, String newName) {
        Path dest = Paths.get(dir, subfolderName);
        try {
            Files.move(pdf.getPdf().toPath(), dest.resolve(Paths.get(newName)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
