package com.freshers.filefixer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.assertj.core.api.WithAssertions;

public class SearchStudentIDTest implements WithAssertions {
    SearchStudentID searcher = new SearchStudentID();

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
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("81305512", "81380485"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("1409121490-602637_Beth_Morales-Horton_601683_Assignment1_81305512.pdf"),
            createPDFWithName("1410855605-602322_Clifton_Bowen_601680_81380485.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }

    @Test
    public void followsConventionWithoutRandoms () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("81305512", "81380485"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("Beth_Morales-Horton_601683_Assignment1_81305512.pdf"),
            createPDFWithName("Clifton_Bowen_601680_81380485.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }

    @Test
    public void noStudentIDS () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("81305512", "81380485"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("Beth_Morales-Horton_Assignment1_.pdf"),
            createPDFWithName("Clifton_Bowen_.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(-1));
    }

    @Test
    public void onlyStudentIDS () {
        ArrayList<String> keys = new ArrayList<String>(Arrays.asList("81305512", "81380485"));
        ArrayList<PDF> files = createArraylistWithPDFS(
            createPDFWithName("81305512.pdf"),
            createPDFWithName("81380485.pdf")
        );

        assertThat(keys).allSatisfy(key -> assertThat(searcher.search(key, files)).isEqualTo(keys.indexOf(key)));
    }
    
}
