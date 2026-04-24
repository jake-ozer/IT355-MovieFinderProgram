/**
 * Additional CWEs applied in this file:
 *  - CWE-116 (Improper Encoding or Escaping of Output)
 */

package com.moviefinder.model;

import java.util.List;

import com.util.OutputEncoder;

/**
 * CWE-1074 (Avoid Excessively Deep Inheritance)
 * CWE-1086 (Avoid Excessive Child Classes)
 * CWE-1055 (Avoid Multiple Inheritance from Concrete Classes)
 */
public class Movie extends MediaEntry {

    private final String director;
    private final double rating;
    private final String description;

    public Movie(String title,
                 int year,
                 List<String> genres,
                 List<String> actors,
                 String director,
                 double rating,
                 String description) {

        super(title, year, genres, actors);
        this.director = director;
        this.rating = rating;
        this.description = description;
    }

    public String getDirector() { return director; }
    public double getRating() { return rating; }
    public String getDescription() { return description; }

    // CWE-1124 (Avoid Deep Nesting)
    @Override
    public String toDisplayString() {

        String line = "-".repeat(55);

        // CWE-116: Escape output before displaying
        String safeTitle = OutputEncoder.escape(getTitle());
        String safeDirector = OutputEncoder.escape(director);
        String safeDescription = OutputEncoder.escape(description);

        return String.format(
            "%n%s%n Title : %s (%d)%n Director: %s%n Genres : %s%n" +
            " Actors : %s%n Rating : %.1f/10%n Synopsis: %s%n%s",
            line,
            safeTitle, getYear(),
            safeDirector,
            String.join(", ", getGenres()),
            String.join(", ", getActors()),
            rating,
            safeDescription,
            line
        );
    }
}
