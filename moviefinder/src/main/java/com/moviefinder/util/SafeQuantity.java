package com.moviefinder.util;

public final class SafeQuantity {

    private SafeQuantity() { /* no instances */ }

    /**
     * Parse a quantity to [minAllowed, maxAllowed]. If parsing
     * fails or the value is outside the allowed range, returns the fallback.
     */
    public static int parseAndClamp(String raw, int fallback, int minAllowed, int maxAllowed) {
        if (raw == null) return fallback;
        String s = raw.trim();
        if (s.isEmpty()) return fallback;
        try {
            long v = Long.parseLong(s); // detect values outside int range
            if (v < minAllowed) return fallback;
            if (v > maxAllowed) return maxAllowed;
            return (int) v;
        } catch (NumberFormatException e) {
            // fallback when not an integer
            return fallback;
        }
    }
}

