package com.moviefinder.model;

import java.util.List;
 
/**
 * CWE-1055 (Avoid Multiple Inheritance from Concrete Classes)
 */

public interface Searchable {
    boolean matchesQuery(String query);
    List<String> getSearchableFields();
}
