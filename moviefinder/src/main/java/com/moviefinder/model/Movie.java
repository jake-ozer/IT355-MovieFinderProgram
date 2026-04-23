package com.moviefinder.model;

import java.util.List;
 
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
        this.director    = director;
        this.rating      = rating;
        this.description = description;
    }
 
    public String getDirector() { return director; }
    public double getRating() { return rating; }
    public String getDescription() { return description; }
 
    
     //CWE-1124 (Avoid Deep Nesting):
    @Override
    public String toDisplayString() {
        String line = "-".repeat(55);
        return String.format(
            "%n%s%n  Title   : %s (%d)%n  Director: %s%n  Genres  : %s%n" +
            "  Actors  : %s%n  Rating  : %.1f/10%n  Synopsis: %s%n%s",
            line,
            getTitle(), getYear(),
            director,
            String.join(", ", getGenres()),
            String.join(", ", getActors()),
            rating,
            description,
            line
        );
    }
}
