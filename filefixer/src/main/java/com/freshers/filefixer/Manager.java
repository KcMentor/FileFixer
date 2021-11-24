package com.freshers.filefixer;

/**
 * Facade class that runs FileFixer
 */
public class Manager {
    private String dir;
    private String folder;
    private FileFixer fixer;

    /**
     * Manager Class constructor
     * @param dir Path to filesToRename, folder containing all the PDF files 
     * @param folder Path to subfolder renamedFiles, folder that will contain all the newly names files
     * 
     */
    public Manager(String dir, String folder) {
        this.dir = dir;
        this.folder = folder;
        this.fixer = new FileFixer(this.dir, this.folder);
    }

    /**
     * Runs the filefixer object
     */
    public void run() {
        fixer.fixFiles();
    }
}
