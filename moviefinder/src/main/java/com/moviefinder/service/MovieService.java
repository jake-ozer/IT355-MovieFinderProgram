/**
 * 
 *  CWE-178 (Improper Handling of Case Sensitivity)
 *  CWE-134 (Use of Externally-Controlled Format String)
 */

package com.moviefinder.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.moviefinder.model.Movie;

public class MovieService {

    private final List<Movie> movies;

    public MovieService(List<Movie> movies) {
        this.movies = Collections.unmodifiableList(new ArrayList<>(movies));
    }

    // CWE-178: normalize input to lowercase so "Action" and "action" match the same way
    private String normalizeInput(String input) {
        if (input == null) return "";
        return input.trim().toLowerCase();
    }

    // CWE-178: query is normalized before any comparison
    // CWE-134: user input is passed as %s data, never used as the format string itself
    public List<Movie> search(String rawQuery) {
        String query = normalizeInput(rawQuery);

        if (query.isEmpty()) {
            System.out.printf("%s%n", "Empty query: returning all movies.");
            return new ArrayList<>(movies);
        }

        List<Movie> results = new ArrayList<>();
        for (Movie m : movies) {
            if (m.matchesQuery(query)) {
                results.add(m);
            }
        }

        // CWE-134: query is the argument to %s, not the format string
        System.out.printf("Search \"%s\" returned %d result(s).%n", query, results.size());
        return results;
    }

    // CWE-178: genre is normalized before comparison
    public List<Movie> filterByGenre(String rawGenre) {
        String genre = normalizeInput(rawGenre);
        List<Movie> results = new ArrayList<>();
        for (Movie m : movies) {
            if (m.matchesGenre(genre)) {
                results.add(m);
            }
        }
        return results;
    }

    public List<Movie> filterByYearRange(int from, int to) {
        if (from > to) {
            System.out.printf("%s%n", "Invalid year range: returning empty list.");
            return Collections.emptyList();
        }
        List<Movie> results = new ArrayList<>();
        for (Movie m : movies) {
            if (m.matchesYearRange(from, to)) {
                results.add(m);
            }
        }
        return results;
    }

    public List<Movie> getAllMovies() {
        return movies;
    }
}