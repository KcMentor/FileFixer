package com.freshers.filefixer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import net.lingala.zip4j.ZipFile;


/**
 * This class extracts any zip file found in the filesToRead folder
 */
public class ZipReader {
    
    /**
     * Searches for any zipped files and extracts everything to the same folder.
     * This is done before the other files are processed 
     * 
     * @param path This is the path to the hot folder containing all files
     */
    public void unzip(String path){
        try{
            List<File> files = Files.list(Paths.get(path))
                                    .filter(Files::isRegularFile)
                                    .filter(name -> name.toString().endsWith(".zip"))
                                    .map(Path::toFile)
                                    .collect(Collectors.toList());

            
            if(files.size() > 0){
                for(File f : files){
                    ZipFile zipFile = new ZipFile(f.getAbsolutePath());
                    zipFile.extractAll(path);
                }
            }else{
                System.out.println("Warning! Zip file is empty.");
                return;
            }
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error! Cannot find Zip file path.");
        }
    }

}
