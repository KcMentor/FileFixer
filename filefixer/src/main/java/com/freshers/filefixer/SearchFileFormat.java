package com.freshers.filefixer;

import java.util.ArrayList;

public class SearchFileFormat implements Search {

    /* Parses through ArrayList of PDF's and searches for a specific file name, returning index if found */
    @Override
    public int search(String key, ArrayList<PDF> files) {
        int index = 0;
        for (PDF pdf : files) {
            if (pdf.getName().matches(key)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
