package com.freshers.filefixer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Path dir = Paths.get(".", "filefixer", "filesToRename").toAbsolutePath().normalize();

        Manager fileFixerManager = new Manager(dir.toString(), "renamedFiles");
        fileFixerManager.run();

    }
}
