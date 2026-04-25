/**
 *
 *  CWE-125 (Out-of-Bounds Read)
 *  CWE-1080 (Source Code File with Excessive Number of Lines of Code)
 */

package com.moviefinder.ui;

import java.util.List;

import com.moviefinder.model.Movie;
import com.moviefinder.util.SafeMath;

// CWE-1080: this class is kept small and focused, one job, print results
public class MoviePrinter {

    // CWE-125: we check bounds before calling .get() so we never read past the list
    public void printResults(List<Movie> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        // CWE-369: compute average rating safely using SafeMath.safeDivide to avoid divide-by-zero
        double total = 0.0;
        for (Movie m : results) {
            total += m.getRating();
        }
        double avg = SafeMath.safeDivide(total, results.size(), 0.0);
        System.out.printf("Found %d movies. Average rating: %.2f%n", results.size(), avg);

        for (int i = 0; i < results.size(); i++) {
            // CWE-125: i is always < results.size() — bounds guaranteed by loop condition
            System.out.println(results.get(i).toDisplayString());
        }
    }

    // CWE-125: explicit bounds check before accessing by index
    public void printByIndex(List<Movie> results, int index) {
        if (index < 0 || index >= results.size()) {
            System.out.println("Index out of range.");
            return;
        }
        System.out.println(results.get(index).toDisplayString());
    }
}