package com.freshers.filefixer;

import java.util.ArrayList;

public class SearchParticipantID implements Search {

    @Override
    public int search(String key, ArrayList<PDF> files) {
        int index = 0;
        String regex = "(?!\\d)" + key + "(?!\\d)";
        for (PDF pdf : files) {
            if (pdf.getName().matches(regex)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
