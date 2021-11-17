package com.freshers.filefixer;

import java.util.ArrayList;

public class SearchParticipantID implements Search {

    @Override
    public int search(String key, ArrayList<PDF> files) {
        int index = 0;
<<<<<<< HEAD
        String regex = "(?!\\d)" + key + "(?!\\d)";
=======
        String regex =  ".*\\D" + key + "\\D.*";

>>>>>>> 5767b786645ce248ebd0549b263aa83ebe746a9d
        for (PDF pdf : files) {
            if (pdf.getName().matches(regex)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
