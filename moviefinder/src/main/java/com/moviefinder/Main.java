/**
 * CWEs applied in this file:
 *  - CWE-20  (Improper Input Validation)
 *  - CWE-116 (Improper Encoding or Escaping of Output)
 *  - CWE-200 (Exposure of Sensitive Information)
 *  - CWE-209 (Error Message Containing Sensitive Information)
 *  - CWE-252 (Unchecked Return Value)
 */

package com.moviefinder;

import java.io.IOException;
import java.util.List;

import com.moviefinder.data.MovieRepository;
import com.moviefinder.model.Movie;
import com.moviefinder.ui.MoviePrinter;

public class Main {

    public static void main(String[] args) {

        // CWE-20: Validate command-line arguments
        if (args.length == 0 || args[0].trim().isEmpty()) {
            System.out.println("Usage: java -jar moviefinder.jar <movies-file> [search term]");
            return;
        }

        String filePath = args[0].trim();
        String query = (args.length > 1) ? args[1].trim() : "";

        if (query.length() > 200) {
            System.out.println("Search term is too long.");
            return;
        }

        MovieRepository repo = new MovieRepository();

        int loadedCount = 0;
        try {
            // CWE-252: Check return value
            loadedCount = repo.loadFromFile(filePath);
        } catch (IllegalArgumentException e) {
            // CWE-209 + CWE-200: Safe, generic error message
            System.out.println("Invalid file path. Please check the input.");
            return;
        } catch (IOException e) {
            // CWE-209 + CWE-200: No stack trace, no internal details
            System.out.println("Unable to read movie data at this time.");
            return;
        }

        if (loadedCount == 0) {
            System.out.println("No movies were loaded.");
            return;
        }

        if (!query.isEmpty()) {
            List<Movie> results = repo.searchByQuery(query);

            // CWE-252: Check list before using it
            if (results.isEmpty()) {
                System.out.println("No movies matched your search.");
            } else {
                MoviePrinter printer = new MoviePrinter();
                printer.printResults(results);
            }
        } else {
            System.out.println("Loaded " + loadedCount + " movies.");
        }
    }
}
