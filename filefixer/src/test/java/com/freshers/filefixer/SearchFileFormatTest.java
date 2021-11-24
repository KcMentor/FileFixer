package com.freshers.filefixer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.assertj.core.api.WithAssertions;

public class SearchFileFormatTest implements WithAssertions {
    SearchFileFormat searcher = new SearchFileFormat();

    ArrayList<String> keys = new ArrayList<String>(Arrays.asList(
        "^Beth Morales-Horton_601683_assignsubmission_file_.*\\.pdf$",
        "^Clifton Bowen_601680_assignsubmission_file_.*\\.pdf$",
        "^Guadalupe De La Vega_601702_assignsubmission_file_.*\\.pdf$"
        ));

    private PDF createPDFWithName(String name) {
        return new PDF(new File(name));
    }

    private ArrayList<PDF> createArraylistWithPDFS(PDF...pdfs) {
        ArrayList<PDF> pdfList = new ArrayList<PDF>();

        for(PDF pdf: pdfs) {
            pdfList.add(pdf);
        }

        return pdfList;
    }

    @Test
    public void followsConvention () {
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("Beth Morales-Horton_601683_assignsubmission_file_A1_Example.pdf"),
            createPDFWithName("Clifton Bowen_601680_assignsubmission_file_A1_example.pdf"),
            createPDFWithName("Guadalupe De La Vega_601702_assignsubmission_file_File.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }

    @Test
    public void fileWithRandoms () {
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("1409121490-602637_Beth_Morales-Horton_601683_Assignment1_81305512.pdf"),
            createPDFWithName("1410855605-602322_Clifton_Bowen_601680_81380485.pdf"),
            createPDFWithName("1410854203-601806_Guadalupe_De_La_Vega_601702_81378665 INFO2603 Assignment 1")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(-1));
    }

    @Test
    public void fileWithoutRandoms () {
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("Beth_Morales-Horton_601683_Assignment1_81305512.pdf"),
            createPDFWithName("Clifton_Bowen_601680_81380485.pdf"),
            createPDFWithName("Guadalupe_De_La_Vega_601702_81378665 INFO2603 Assignment 1.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(-1));
    }


    @Test
    public void nameInAllUppercase () {
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("BETH MORALES-HORTON 601683_Assignment1_81305512.pdf"),
            createPDFWithName("Assignment 1 CLIFTON BOWEN 81380485.pdf"),
            createPDFWithName("601702 81378665 INFO2603 Assignment 1 GUADALUPE DE LA VEGA.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(-1));
    }

    @Test
    public void nameInAllLowercase () {
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("beth morales-horton 601683_Assignment1_81305512.pdf"),
            createPDFWithName("Assignment 1 clifton bowen 81380485.pdf"),
            createPDFWithName("601702 81378665 INFO2603 Assignment 1 guadalupe de la vega.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(-1));
    }

    @Test
    public void noParticipantNames () {
         ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("601683_Assignment1_81305512.pdf"),
            createPDFWithName("601680_81380485.pdf"),
            createPDFWithName("601702_81378665 INFO2603 Assignment 1")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(-1));
    }
    
}
