package com.freshers.filefixer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import net.lingala.zip4j.ZipFile;

public class ZipReader {
    
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
                return;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
