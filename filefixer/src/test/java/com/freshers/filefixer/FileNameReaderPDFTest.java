package com.freshers.filefixer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.assertj.core.api.WithAssertions;

public class FileNameReaderPDFTest implements WithAssertions{
    FileNameReaderPDF reader = new FileNameReaderPDF();
    String repoDir = "./src/test/java/com/freshers/filefixer/testData/fileRepo".replace("/", File.separator);
    ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(
        "1409121490-602637_Beth_Morales-Horton_601683_Assignment1_81305512.pdf",
        "Assignment 1-81343013.pdf",
        "Guadalupe De La Vega_601702_assignsubmission_file_81378665 INFO2603 Assignment 1.pdf"
    ));


    @Test
    public void readFilesFromFilesRepo () {
        int fileCount = reader.readData(repoDir);
        assertThat(fileCount).isEqualTo(Paths.get(repoDir).toFile().list().length - 1);

        ArrayList<PDF> pdfs = reader.getPdfs();
        assertThat(pdfs).extracting(PDF::getName).containsAll(fileNames);
    }
    
}
