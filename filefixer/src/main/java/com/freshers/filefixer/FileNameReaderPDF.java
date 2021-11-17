package com.freshers.filefixer;

import java.io.File;
import java.util.ArrayList;

public class FileNameReaderPDF {
    private ArrayList<PDF> pdfs;
    File folder;

    public FileNameReaderPDF(){
        pdfs = new ArrayList<PDF>();
    }

    public int readData(){
        int counter=0;

        String path = "C:/Users/shane/Desktop/UWI/Year 3/Semester 1/COMP 3607/Sample files and CSVs-20211114/sample3/sample3/";

        folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for(File f : listOfFiles){
            if(f.getName().endsWith(".pdf")){
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
