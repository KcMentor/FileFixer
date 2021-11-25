package com.freshers.filefixer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.assertj.core.api.WithAssertions;

public class ReaderCSVTest implements WithAssertions{
    String repoDir = "./src/test/java/com/freshers/filefixer/testData/fileRepo".replace("/", File.separator);
    String testDir = "./src/test/java/com/freshers/filefixer/testData/testFiles".replace("/", File.separator);
    Path testPath = Paths.get(testDir).toAbsolutePath().normalize();
    Path repoPath = Paths.get(repoDir).toAbsolutePath().normalize();
    String csvName = "Test.csv";


    @Test void readFromDirectoryWithCSV () {
        ReaderCSV reader = new ReaderCSV();
        File file = reader.getCSVFile(repoDir);
        assertThat(file).isNotNull();
    }

    @Test void readFromDirectoryWithoutCSV () {
        ReaderCSV reader = new ReaderCSV();
        File file = reader.getCSVFile(testPath.resolve("renamedFiles").toString());
        assertThat(file).isNull();
    }

    @Test void readFromDirectoryWithMoreThanOneCSV () {
        ReaderCSV reader = new ReaderCSV();
   
        try {
            Files.deleteIfExists(testPath.resolve("Test.csv"));
            Files.deleteIfExists(testPath.resolve("Copy.csv"));
            Files.copy(repoPath.resolve("Test.csv"), testPath.resolve("Test.csv"));
            Files.copy(repoPath.resolve("Test.csv"), testPath.resolve("Copy.csv"));
        } catch (IOException e) {
            assert false;
        }
        
        File file = reader.getCSVFile(testPath.toString());
        
        try {
            Files.delete(testPath.resolve("Test.csv"));
            Files.delete(testPath.resolve("Copy.csv"));
        } catch (IOException e) {
            assert false;
        }

        assertThat(file).isNull();
    }    

    @Test
    public void testReadFromCSV () {
        ReaderCSV reader = new ReaderCSV();

        int rowCount = reader.readData(repoDir);

        assertThat(rowCount).isEqualTo(2);

    }
}
