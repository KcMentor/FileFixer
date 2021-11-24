package com.freshers.filefixer;

import java.io.File;
import java.util.ArrayList;

public class FileNameReaderPDF {
    private ArrayList<PDF> pdfs;
    File folder;

    public FileNameReaderPDF() {
        pdfs = new ArrayList<PDF>();
    }

    /* Parses through the folder filesToFix and addes all PDF files to an ArrayList*/
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

    public ArrayList<PDF> getPdfs() {
        return pdfs;
    }

}
