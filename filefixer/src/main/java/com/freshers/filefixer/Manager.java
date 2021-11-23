package com.freshers.filefixer;

public class Manager {
    private String dir;
    private String folder;
    private FileFixer fixer;
    public Manager(String dir, String folder) {
        this.dir = dir;
        this.folder = folder;
        this.fixer = new FileFixer(this.dir, this.folder);
    }

    public void run() {
        fixer.fixFiles();
    }
}
