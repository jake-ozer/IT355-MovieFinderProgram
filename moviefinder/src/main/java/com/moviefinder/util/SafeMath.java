
package com.moviefinder.util;

/**
 * Math helpers to avoid common numeric problems.
 * Implements a safeDivide to mitigate CWE-369 (Divide By Zero).
 */
public final class SafeMath {

    private SafeMath() { /* no instances */ }

    /**
     * Safely divide a numerator by denominator. If denominator is 0 (or extremely
     * close to 0 for floating point), returns the fallback value instead.
     */
    public static double safeDivide(double numerator, double denominator, double fallback) {
        if (Math.abs(denominator) < 1e-12) {
            return fallback;
        }
        return numerator / denominator;
    }
}

