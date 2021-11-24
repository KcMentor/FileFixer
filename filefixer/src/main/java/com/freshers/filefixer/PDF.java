package com.freshers.filefixer;

import java.io.File;


/* This class is used to create objects which represent each PDF file in the filesToFix folder. */
public class PDF {
    private File pdf;
    private String name;

    public PDF(File file) {
        this.pdf = file;
        this.name = file.getName();
    }

    public String getName() {
        return name;
    }

    public File getPdf() {
        return pdf;
    }
}
