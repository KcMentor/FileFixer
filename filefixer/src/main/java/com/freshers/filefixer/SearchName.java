package com.freshers.filefixer;

import java.util.ArrayList;

/**
 * This class searches for the student by their Name
 */
public class SearchName implements Search {

    /**
     * Parses through ArrayList of PDF's and searched for student via Name
     * @param key This is what is being searched for.
     * @param files This is an ArrayList of all the PDF files that are currently in the filesToRename folder
     * @return The index of the file found
     */
    @Override
    public int search(String key, ArrayList<PDF> files) {
        int index = 0, ind = 0, check = 0;
        String regex = "(?i)(.*[^\\p{Alpha}])?" + key.replaceAll("\\s", "([^\\p{Alpha}]?)") + ".*";
        for (PDF pdf : files) {
            if (pdf.getName().matches(regex)) {
                ind = index;
                check++;
            }
            index++;
        }

        if (check > 1) {
            return -2;
        } else if (check == 1) {
            return ind;
        } else {
            return -1;
        }
    }

}
