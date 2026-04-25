package com.moviefinder.util;

/**
 * This class provides small helpers to safely parse year like integers and
 * perform simple checked arithmetic without risking silent wraparounds.
 */
public final class SafeInteger {

    // Earliest commonly-accepted film year (first motion pictures): 1878
    // Latest sane upper bound for movie release year (a little into the future): 2100
    private static final int MIN_MOVIE_YEAR = 1878;
    private static final int MAX_MOVIE_YEAR = 2100;

    private SafeInteger() { /* no instances */ }

    /**
     * Parse a string into an integer year with overflow protection and clamping.
     * If parsing fails, returns the provided fallback.
     */
    public static int parseYearClamped(String raw, int fallback) {
        if (raw == null) return fallback;
        String s = raw.trim();
        if (s.isEmpty()) return fallback;

        try {
            // parse into a wider type to detect values outside int range
            long v = Long.parseLong(s);
            if (v < MIN_MOVIE_YEAR) return MIN_MOVIE_YEAR;
            if (v > MAX_MOVIE_YEAR) return MAX_MOVIE_YEAR;
            return (int) v;
        } catch (NumberFormatException e) {
            // try a loose double parse (handles things like "1999.0") then round
            try {
                double d = Double.parseDouble(s);
                long v = Math.round(d);
                if (v < MIN_MOVIE_YEAR) return MIN_MOVIE_YEAR;
                if (v > MAX_MOVIE_YEAR) return MAX_MOVIE_YEAR;
                return (int) v;
            } catch (NumberFormatException ex) {
                // give up and return fallback — prefer a safe known value over throwing
                return fallback;
            }
        }
    }

    /**
     * Simple checked addition that returns fallback on overflow.
     * Demonstrates how to avoid wraparound when doing small arithmetic.
     */
    public static int safeAdd(int a, int b, int fallback) {
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            return fallback;
        }
    }
}

