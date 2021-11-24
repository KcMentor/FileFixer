package com.freshers.filefixer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Runner class
 */
public class App 
{
    /**
     * Main class that starts the application
     * @param args
     */
    public static void main( String[] args )
    {
        Path dir = Paths.get(".", "filefixer", "filesToRename").toAbsolutePath().normalize();

        Manager fileFixerManager = new Manager(dir.toString(), "renamedFiles");
        fileFixerManager.run();

    }
}
