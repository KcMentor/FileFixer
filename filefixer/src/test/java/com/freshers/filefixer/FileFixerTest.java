package com.freshers.filefixer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.assertj.core.api.WithAssertions;

public class FileFixerTest implements WithAssertions {
    String repoDir = "./src/test/java/com/freshers/filefixer/testData/fileRepo".replace("/", File.separator);
    String testDir = "./src/test/java/com/freshers/filefixer/testData/testFiles".replace("/", File.separator);
    Path testPath = Paths.get(testDir).toAbsolutePath().normalize();
    Path repoPath = Paths.get(repoDir).toAbsolutePath().normalize();
    String subName = "renamedFiles";
    FileFixer fixer = new FileFixer(testDir, subName);
  
    void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
      }

    @BeforeEach
    public void setup() {
        
        try {
            deleteDirectoryStream(testPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Path renamedPath = testPath.resolve("renamedFiles");
        if (!Files.exists(renamedPath)) {
            try {
                Files.createDirectories(renamedPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

        try {
            List<Path> repoFiles = Files.list(Paths.get(repoDir)).toList();
            for (Path file: repoFiles) {
                Files.copy(file, testPath.resolve(file.getFileName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        
        
    }

    
    public static void createFile(String name) {
        
    }


    @ParameterizedTest
    @CsvSource({
        "601683,Beth Morales-Horton,81305512,1409121490-602637_Beth_Morales-Horton_601683_Assignment1_81305512.pdf",
        "601702,Guadalupe De La Vega,81378665,Guadalupe De La Vega_601702_assignsubmission_file_81378665 INFO2603 Assignment 1.pdf"
        
    })
    public void renameFileValid(String pID, String name, String sID, String fName) {
        Record rec = new Record(pID, name, sID);
        PDF pdf = new PDF(new File(testDir, fName));
        FileFixer fixer = new FileFixer(testDir, subName);
        List<Path> renamedFile = null;
        
        fixer.renameFile(rec, pdf);

        try {
            renamedFile = Files.list(Paths.get(testDir, "renamedFiles")).toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        assertThat(renamedFile).extracting(Path::getFileName).containsExactly(Paths.get(name + "_" + pID + "_assignsubmission_file_" + fName));
    }
    
}
