/**
 * Additional CWEs applied in this file:
 *  - CWE-20 (Improper Input Validation)
 */

package com.moviefinder.model;

import java.util.List;

/**
 * CWE-1074 (Avoid Excessively Deep Inheritance)
 * CWE-1086 (Avoid Excessive Child Classes)
 * CWE-1055 (Avoid Multiple Inheritance from Concrete Classes)
 */
public abstract class MediaEntry implements Searchable, Filterable {

    private final String title;
    private final int year;
    private final List<String> genres;
    private final List<String> actors;

    protected MediaEntry(String title, int year, List<String> genres, List<String> actors) {
        this.title = title;
        this.year = year;
        this.genres = List.copyOf(genres);
        this.actors = List.copyOf(actors);
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public List<String> getGenres() { return genres; }
    public List<String> getActors() { return actors; }

    // CWE-1124 (Avoid Deep Nesting)
    // CWE-20: Validate query before using it
    @Override
    public boolean matchesQuery(String query) {
        if (query == null) return false;
        String q = query.toLowerCase().trim();
        if (q.isEmpty()) return false;

        for (String field : getSearchableFields()) {
            if (fieldContains(field, q)) {
                return true;
            }
        }
        return false;
    }

    private boolean fieldContains(String field, String query) {
        if (field == null) return false;
        return field.toLowerCase().contains(query);
    }

    @Override
    public List<String> getSearchableFields() {
        return List.of(
            title,
            String.join(" ", actors),
            String.join(" ", genres)
        );
    }

    // CWE-1124: each criterion is its own flat method no nested ifs.
    @Override
    public boolean matchesGenre(String genre) {
        String target = genre.toLowerCase().trim();
        for (String g : genres) {
            if (g.toLowerCase().equals(target)) return true;
        }
        return false;
    }

    @Override
    public boolean matchesActor(String actorName) {
        String target = actorName.toLowerCase().trim();
        for (String a : actors) {
            if (a.toLowerCase().contains(target)) return true;
        }
        return false;
    }

    @Override
    public boolean matchesYearRange(int fromYear, int toYear) {
        return year >= fromYear && year <= toYear;
    }

    public abstract String toDisplayString();
}
