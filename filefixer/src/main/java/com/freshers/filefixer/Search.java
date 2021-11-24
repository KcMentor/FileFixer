package com.freshers.filefixer;

import java.util.ArrayList;

/**
 * Strategy Design pattern
 * Interface for all the different search strategies
 */
public interface Search {

    /**
     * Method that all search classes must implement
     * @param key Key that need to be found
     * @param files All the files that are being searched through
     * @return Index of file if it is found
     */
    public int search(String key, ArrayList<PDF> files);

}
