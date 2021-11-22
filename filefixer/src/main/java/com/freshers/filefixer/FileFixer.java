package com.freshers.filefixer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class FileFixer {
    private ArrayList<Search> Searches;
    private ArrayList<Record> Records;
    private ArrayList<PDF> PDFs;
    private ReaderCSV readerCSV;
    private FileNameReaderPDF readerPDF;
    private String dir;

    public FileFixer(String dir) {
        this.dir = dir;
        this.Searches = new ArrayList<Search>();
        this.Records = new ArrayList<Record>();
        this.PDFs = new ArrayList<PDF>();

    }

}
