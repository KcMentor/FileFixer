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
        File file = reader.getCSVFile("./src/test/java/com/freshers/filefixer/testData/renamedFiles".replace("/", File.separator));
        assertThat(file).isNull();
    }

    @Test void readFromDirectoryWithoutMoreThanOneCSV () {
        ReaderCSV reader = new ReaderCSV();
        try {
            Files.copy(Paths.get(repoDir, "Test.csv"), Paths.get(testDir, "Test.csv"));
            Files.copy(Paths.get(repoDir, "Test.csv"), Paths.get(testDir, "Copy.csv"));
        } catch (IOException e) {
            assert false;
        }
        
        File file = reader.getCSVFile("./src/test/java/com/freshers/filefixer/testData".replace("/", File.separator));
        
        try {
            Files.delete(Paths.get(testDir, "Test.csv"));
            Files.delete(Paths.get(testDir, "Copy.csv"));
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
