package com.freshers.filefixer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.assertj.core.api.WithAssertions;

public class SearchNameTest implements WithAssertions {
    SearchName searcher = new SearchName();

    public PDF createPDFWithName(String name) {
        return new PDF(new File(name));
    }

    public ArrayList<PDF> createArraylistWithPDFS(PDF...pdfs) {
        ArrayList<PDF> pdfList = new ArrayList<PDF>();

        for(PDF pdf: pdfs) {
            pdfList.add(pdf);
        }

        return pdfList;
    }

    @Test
    public void followsConventionWithRandoms () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("Beth Morales-Horton", "Clifton Bowen", "Guadalupe De La Vega"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("1409121490-602637_Beth_Morales-Horton_601683_Assignment1_81305512.pdf"),
            createPDFWithName("1410855605-602322_Clifton_Bowen_601680_81380485.pdf"),
            createPDFWithName("1410854203-601806_Guadalupe_De_La_Vega_601702_81378665 INFO2603 Assignment 1")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }

    @Test
    public void followsConventionWithoutRandoms () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("Beth Morales-Horton", "Clifton Bowen", "Guadalupe De La Vega"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("Beth_Morales-Horton_601683_Assignment1_81305512.pdf"),
            createPDFWithName("Clifton_Bowen_601680_81380485.pdf"),
            createPDFWithName("Guadalupe_De_La_Vega_601702_81378665 INFO2603 Assignment 1")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }

    @Test
    public void nameInAllUppercase () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("Beth Morales-Horton", "Clifton Bowen", "Guadalupe De La Vega"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("BETH MORALES-HORTON 601683_Assignment1_81305512.pdf"),
            createPDFWithName("Assignment 1 CLIFTON BOWEN 81380485.pdf"),
            createPDFWithName("601702 81378665 INFO2603 Assignment 1 GUADALUPE DE LA VEGA.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }

    @Test
    public void nameInAllLowercase () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("Beth Morales-Horton", "Clifton Bowen", "Guadalupe De La Vega"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("beth morales-horton 601683_Assignment1_81305512.pdf"),
            createPDFWithName("Assignment 1 clifton bowen 81380485.pdf"),
            createPDFWithName("601702 81378665 INFO2603 Assignment 1 guadalupe de la vega.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }

    @Test
    public void noParticipantNames () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("Beth Morales-Horton", "Clifton Bowen", "Guadalupe De La Vega"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("601683_Assignment1_81305512.pdf"),
            createPDFWithName("601680_81380485.pdf"),
            createPDFWithName("601702_81378665 INFO2603 Assignment 1")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(-1));
    }
    
}
