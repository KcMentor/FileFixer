package com.freshers.filefixer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;

import org.assertj.core.api.WithAssertions;

public class PDFTest implements WithAssertions{
    String repoDir = "./src/test/java/com/freshers/filefixer/testData/fileRepo".replace("/", File.separator);
    
    
    @ParameterizedTest
    @CsvSource({
        "1409121490-602637_Beth_Morales-Horton_601683_Assignment1_81305512.pdf",
        "Assignment 1-81343013.pdf",
        "Guadalupe De La Vega_601702_assignsubmission_file_81378665 INFO2603 Assignment 1.pdf"
    })
    public void getPDFName(String fileName) {
        File file = new File(repoDir, fileName);
        PDF pdf = new PDF(file);

        assertThat(pdf).extracting(PDF::getName).isEqualTo(fileName);
    }
    
}
