package com.freshers.filefixer;

import java.io.File;

/**
 * This class is used to create objects which represent each PDF file in the folesToRename folder
 */
public class PDF {
    /**
     * The raw PDF file that will be renamed
     */
    private File pdf;

    /**
     * The original of the PDF file
     */
    private String name;

    /**
     * PDF Class constructor
     * @param file The PDF file that is being used
     */
    public PDF(File file) {
        this.pdf = file;
        this.name = file.getName();
    }

    /**
     * Gets the orginal name of the file
     * @return The name of the file
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the PDF file
     * @return The PDF file
     */
    public File getPdf() {
        return pdf;
    }
}
