package com.freshers.filefixer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.nio.file.Paths;

import org.assertj.core.api.WithAssertions;

public class ReaderCSVTest implements WithAssertions{
    String repoDir = "./src/test/java/com/freshers/filefixer/testData/fileRepo".replace("/", File.separator);
    String csvName = "Test.csv";
    @Test
    public void testReadFromCSV () {
        ReaderCSV reader = new ReaderCSV();

        int rowCount = reader.readData(Paths.get(repoDir, csvName).toString());

        assertThat(rowCount).isEqualTo(3);
        
    }
}
