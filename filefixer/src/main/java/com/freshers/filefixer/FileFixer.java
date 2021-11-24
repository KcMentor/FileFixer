package com.freshers.filefixer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.ListIterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class FileFixer {
    private ArrayList<Search> searches;
    private ArrayList<Record> records;
    private ArrayList<PDF> PDFs;
    private ZipReader zipReader;
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
        this.zipReader = new ZipReader();
        this.readerCSV = new ReaderCSV();
        this.readerPDF = new FileNameReaderPDF();

        searches.add(new SearchParticipantID());
        searches.add(new SearchStudentID());
        searches.add(new SearchName());
    }

    /* Searches for files in the PDF array and renames the files that are found */
    public void fixFiles() {
        int count = 0, index;
        emptySubFolder();
        zipReader.unzip(dir);
        if (readerCSV.readData(dir) == -1) {
            System.out.println("Error! Could not read from CSV");
            return;
        }
        readerPDF.readData(dir);
        records = readerCSV.getRecords();
        PDFs = readerPDF.getPdfs();
        Record r = null;
        checkFormatted();

        for (Search s : searches) {
            if (PDFs.isEmpty()) {
                break;
            }

            ListIterator<Record> recordIter = records.listIterator();
            while (recordIter.hasNext()) {
                r = recordIter.next();
                if (count == 0) {
                    index = s.search(r.getParticipantID(), PDFs);
                } else if (count == 1) {
                    index = s.search(r.getStudentID(), PDFs);
                } else {
                    index = s.search(r.getFullName(), PDFs);
                }

                if (index > -1) {
                    renameFile(r, PDFs.get(index));
                    PDFs.remove(index);
                    recordIter.remove();
                }
            }

            count++;
        }

        createTxt();

    }

    /*
     * Creates the a text file with un-submitted pdf files
     */
    public void createTxt() {
        if (records.size() > 0) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH;mm;ss");
                Date date = new Date();
                File file = new File(dir + "\\Missing PDF Submittions " + formatter.format(date) + ".txt");
                FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write("These are the missing files: \n");
                System.out.println("These are the missing files: ");
                for (Record remRecords : records) {
                    System.out.println("Name: " + remRecords.getFullName() + "\tStudentID: " + remRecords.getStudentID()
                            + "\tParticipantID" + remRecords.getParticipantID());
                    bw.write("Name: " + remRecords.getFullName() + "\tStudentID: " + remRecords.getStudentID()
                            + "\tParticipantID" + remRecords.getParticipantID() + "\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Checks to see if file is already properly formatted then moves it to the
     * renamedFiles folder
     */
    public boolean checkFormatted() {
        boolean changed = false;
        int index;
        SearchFileFormat searchFileFormat = new SearchFileFormat();
        Record r = null;

        String regex = "";
        ListIterator<Record> recordIter = records.listIterator();

        while (recordIter.hasNext()) {
            r = recordIter.next();
            regex = "^" + r.getFullName() + "_" + r.getParticipantID() + "_assignsubmission_file_.*\\.pdf$";
            index = searchFileFormat.search(regex, PDFs);
            if (index > -1) {
                addFile(PDFs.get(index), PDFs.get(index).getName());
                PDFs.remove(index);
                recordIter.remove();
                changed = true;
            }
        }

        return changed;
    }

    /* Renames the file to the correct naming convetion */
    public boolean renameFile(Record record, PDF pdf) {
        String newName = record.getFullName() + "_" + record.getParticipantID() + "_assignsubmission_file_"
                + pdf.getName();
        return addFile(pdf, newName);
    }

    /* Moves file to the correct nested folder after they have been renamed */
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

    public void emptySubFolder() {
        Path dest = Paths.get(dir, subfolderName);
        if (Files.exists(dest)) {
            for (File file : dest.toFile().listFiles())
                file.delete();
        }
    }

}
