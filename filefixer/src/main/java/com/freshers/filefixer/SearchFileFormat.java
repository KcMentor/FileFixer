package com.freshers.filefixer;

import java.util.ArrayList;

public class SearchFileFormat implements Search {

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
