package com.freshers.filefixer;

import java.io.File;


/* This class is used to create objects which represent each PDF file in the filesToFix folder. */
public class PDF {
    private File pdf;
    private String name;
    private boolean modified;

    public PDF(File file) {
        this.pdf = file;
        this.name = file.getName();
        this.modified = false;
    }

    public String getName() {
        return name;
    }

    public File getPdf() {
        return pdf;
    }

    public boolean isModified() {
        return modified;
    }
}
