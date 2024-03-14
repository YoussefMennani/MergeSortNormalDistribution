package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultSaver {

    private final String directoryPath;


    public ResultSaver(String directoryPath) {
        this.directoryPath = directoryPath;
        // Ensure the directory exists
        new File(directoryPath).mkdirs();
    }

    /**
     * Saves the sorting result to a text file.
     * @param key The key associated with the result, used as the filename.
     * @param result The sorting result to be saved.
     */
    public void saveResult(String key, SortResult result) {
        // Replace characters that are invalid in filenames
        String fileName = key.replaceAll("[^a-zA-Z0-9.\\-]", "_") + ".txt";

        File file = new File(directoryPath, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(result.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + fileName);
            e.printStackTrace();
        }
    }
}
