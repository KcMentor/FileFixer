package com.freshers.filefixer;

import java.util.ArrayList;

/**
 * This class searches for the student file in the collection of PDF's by looking for their ParticipantID
 */
public class SearchParticipantID implements Search {

    /**
     * Parses through ArrayList of PDF's and searches for student via ParticipantID, returning index if found
     * 
     * @param key This is what is being searched for.
     * @param files This is an ArrayList of all the PDF files that are currently in the filesToRename folder
     * @return The index of the element found 
     */
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
