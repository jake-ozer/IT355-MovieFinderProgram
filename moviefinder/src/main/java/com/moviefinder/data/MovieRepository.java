package com.moviefinder.data;

import com.moviefinder.model.Movie;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
/**
 * Loads movies from a flat file into memory.
 *
 * File format (one movie per line):
 *   Title|Year|Genre1,Genre2|Actor1,Actor2|Director|Rating|Description
 *   Lines starting with '#' and blank lines are ignored.
 *
 * CWE-1095 (Avoid Loop Condition Updated Inside Loop)
 * CWE-1124 (Avoid Deep Nesting)
 */
public class MovieRepository {
 
    private final List<Movie> movies = new ArrayList<>();
 
    public void loadFromFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
 
        // CWE-1095: 'i' is controlled only by the for-header below.
        // It is never modified anywhere inside the loop body.
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
 
            if (isSkippable(line)) continue; 
 
            Movie movie = parseLine(line);
            if (movie != null) {
                movies.add(movie);
            }
        }
    }
 
    private boolean isSkippable(String line) {
        return line.isEmpty() || line.startsWith("#");
    }
 
    
     // CWE-1124
    private Movie parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) {
            System.err.println("[WARN] Skipping malformed line: " + line);
            return null;
        }
 
        String title = parts[0].trim();
        int year = parseIntSafe(parts[1].trim(), 0);
        List<String> genres = splitCSV(parts[2]);
        List<String> actors = splitCSV(parts[3]);
        String director = parts[4].trim();
        double rating = parseDoubleSafe(parts[5].trim(), 0.0);
        String description = parts[6].trim();
 
        return new Movie(title, year, genres, actors, director, rating, description);
    }
 
    /**
     * CWE-1095: The for loop here also only modifies 'i' in the header.
     */
    private List<String> splitCSV(String raw) {
        String[] tokens = raw.split(",");
        List<String> result = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) { 
            result.add(tokens[i].trim());
        }
        return result;
    }
 
    private int parseIntSafe(String raw, int fallback) {
        try { return Integer.parseInt(raw); }
        catch (NumberFormatException e) { return fallback; }
    }
 
    private double parseDoubleSafe(String raw, double fallback) {
        try { return Double.parseDouble(raw); }
        catch (NumberFormatException e) { return fallback; }
    }
 
 
    public List<Movie> getAllMovies() {
        return Collections.unmodifiableList(movies);
    }
 
    public int getCount() {
        return movies.size();
    }
}