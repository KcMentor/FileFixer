package com.freshers.filefixer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.WithAssertions;

public class ZipReaderTest implements WithAssertions{
    String zipRepo =  "./src/test/java/com/freshers/filefixer/testData/zipRepo".replace("/", File.separator);
    String repoDir = "./src/test/java/com/freshers/filefixer/testData/fileRepo".replace("/", File.separator);
    String testDir = "./src/test/java/com/freshers/filefixer/testData/testFiles".replace("/", File.separator);
    Path testPath = Paths.get(testDir).toAbsolutePath().normalize();
    Path repoPath = Paths.get(repoDir).toAbsolutePath().normalize();
    Path zipPath = Paths.get(zipRepo).toAbsolutePath().normalize();
    
    @BeforeEach
    public void copyZip() {
        try {
            Files.copy(zipPath.resolve("fileRepo.zip"), testPath.resolve("fileRepo.zip"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }

    private List<String> getFileList(Path path) {
        List<String> files = null;
        try {
            files = Files.list(path)
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .collect(Collectors.toList());             
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    @AfterEach
    public void clearFolder() {
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
    }

    @Test
    public void unzipFileTest_zipExists () {
        ZipReader reader = new ZipReader();

        reader.unzip(testDir);

        List<String> files = getFileList(testPath);
        
        assertThat(files).containsExactlyInAnyOrder(
            "1409121490-602637_Beth_Morales-Horton_601683_Assignment1_81305512.pdf",
            "Assignment 1-81343013.pdf",
            "Guadalupe De La Vega_601702_assignsubmission_file_81378665 INFO2603 Assignment 1.pdf",
            "Test.csv",
            "fileRepo.zip"
        );
    }

    @Test
    public void unzipFileTest_noZipExists () {
        ZipReader reader = new ZipReader();

        reader.unzip(testPath.resolve("renamedFiles").toString());

        List<String> files = getFileList(testPath.resolve("renamedFiles"));
        
        assertThat(files).isEmpty();
    }
}
