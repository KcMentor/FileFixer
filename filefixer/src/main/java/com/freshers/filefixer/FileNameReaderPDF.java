package com.freshers.filefixer;

import java.io.File;
import java.util.ArrayList;

public class FileNameReaderPDF {
    /**
     * Collection of PDF files that are inside the folder
     */
    private ArrayList<PDF> pdfs;

    /**
     * Folder that contains all the PDF files
     */
    File folder;

    /**
     * FileNameReacherPDF constructor.
     * Initilizes the ArrayList of PDF
     */
    public FileNameReaderPDF() {
        pdfs = new ArrayList<PDF>();
    }

    /* Parses through the folder filesToFix and addes all PDF files to an ArrayList*/

    /**
     * Parses through the folder filesToRename and adds all the PDF files to an ArrayList
     * @param path This is the path to the folder containing all the PDF files
     * @return The count of PDF files inside the folder
     */
    public int readData(String path) {
        int counter = 0;

        folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File f : listOfFiles) {
            if (f.getName().endsWith(".pdf")) {
                PDF pdf = new PDF(f);
                pdfs.add(pdf);
                counter++;
            }
        }

        return counter;
    }

    /**
     * Gets the collection of PDF files
     * @return The ArrayList of PDF files from the folder
     */
    public ArrayList<PDF> getPdfs() {
        return pdfs;
    }

}
