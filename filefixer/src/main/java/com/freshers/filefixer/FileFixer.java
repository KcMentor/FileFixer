package com.freshers.filefixer;

import java.util.ArrayList;
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

/**
 * This class deals with parsing through the list of PDF files and renaming them to the proper convention.
 * The files that are renamed successfully are moved to a nested folder
 */
public class FileFixer {
    /**
     * This list contains all the different types of seaches used
     */
    private ArrayList<Search> searches;

    /**
     * This list contains all the records created from the CSV file
     */
    private ArrayList<Record> records;

    /**
     * This list contains all the PDF files that are in the folder filesToRename
     */
    private ArrayList<PDF> PDFs;

    /**
     * ZipReader object that is used to check for any zip files before processing
     */
    private ZipReader zipReader;

    /**
     * ReacherCSV object that parses the CSV files and creates a list of records
     */
    private ReaderCSV readerCSV;

    /**
     * FileNameReaderPDF object that parses through all the PDF files in the folder and adds them to an ArrayList
     */
    private FileNameReaderPDF readerPDF;

    /**
     * Directory/Path to the folder containing all the PDF files
     */
    private String dir;

    /**
     * Path to the nested folder where renamed files are moved to
     */
    private String subfolderName;

    /**
     * FileFixer constructor
     * @param dir Path to folder containing all PDF files
     * @param subfolderName Path to nested folder where renamed files are moved
     */
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

    /**
     * Searches for files in the PDF array and renames the files that are found
     */
    public void fixFiles() {
        int count = 0, index;
        emptySubFolder();
        zipReader.unzip(dir);
        if (readerCSV.readData(dir) == -1) {
            System.out.println("Error! Could not read from CSV");
            return;
        }
        if (readerPDF.readData(dir) == 0) {
            System.out.println("Error! No PDFs found");
            return;
        }
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

     /**
      * Created a text file that shows un-submitted PDF files so it can be reviewed
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

     /**
      * Checks to see if files is already properly formatted then moves it to the renamedFiles folder
      * @return True if file is properly formatted
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

    /**
     * Renames the files to the correct naming convention
     * @param record Record object containing the correct data of a student
     * @param pdf PDF file that must be renamed to correct convention
     * @return True if file is renamed
     */
    public boolean renameFile(Record record, PDF pdf) {
        String newName = record.getFullName() + "_" + record.getParticipantID() + "_assignsubmission_file_"
                + pdf.getName();
        return addFile(pdf, newName);
    }

    /**
     * Moves file to the correct nested folder after they have been renamed
     * @param pdf PDF file that needs to be moved
     * @param newName New Name of the file after reformatted to convention
     * @return
     */
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

    /**
     * Checks if subfolder contains files and clears it since that is old data
     */
    public void emptySubFolder() {
        Path dest = Paths.get(dir, subfolderName);
        if (Files.exists(dest)) {
            for (File file : dest.toFile().listFiles())
                file.delete();
        }
        else{
            try {
                Files.createDirectories(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
