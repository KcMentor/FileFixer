package com.freshers.filefixer;

import java.util.ArrayList;

public class SearchParticipantID implements Search {

    /* Parses through ArrayList of PDF's and searches for student via ParticipantID, returning index if found */
    @Override
    public int search(String key, ArrayList<PDF> files) {
        int index = 0;
        String regex = "^(.*\\D)?" + key + "\\D.*$";
        for (PDF pdf : files) {
            if (pdf.getName().matches(regex)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
