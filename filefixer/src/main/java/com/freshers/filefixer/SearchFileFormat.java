package com.freshers.filefixer;

import java.util.ArrayList;

/**
 * This class searches for a very specific file name
 */
public class SearchFileFormat implements Search {

    /**
     * Searches for a specific file name
     * @param key This is what is being searched for
     * @param files This is the ArrayList of PDF files in the folder
     * @return The index of the file if found successfully 
     */
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
