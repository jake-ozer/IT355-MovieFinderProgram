package com.moviefinder.model;

/**
 * CWE-1055 (Avoid Multiple Inheritance from Concrete Classes)
 */

public interface Filterable {
    boolean matchesGenre(String genre);
    boolean matchesActor(String actorName);
    boolean matchesYearRange(int fromYear, int toYear);
}