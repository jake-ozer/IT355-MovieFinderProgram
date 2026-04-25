package com.moviefinder.util;


public final class SafeConverter {

    private SafeConverter() { /* no instances */ }

    /**
     * Parse a double from text.
     * If parsing fails, the fallback is returned.
     */
    public static double parseDoubleClamped(String raw, double fallback, double min, double max) {
        if (raw == null) return fallback;
        String s = raw.trim();
        if (s.isEmpty()) return fallback;
        try {
            double v = Double.parseDouble(s);
            if (Double.isNaN(v) || Double.isInfinite(v)) return fallback;
            if (v < min) return min;
            if (v > max) return max;
            return v;
        } catch (NumberFormatException e) {
            return fallback;
        }
    }


    public static int longToIntClamped(long v) {
        if (v > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (v < Integer.MIN_VALUE) return Integer.MIN_VALUE;
        return (int) v;
    }

    /**
     * Convert double to int by rounding ato the provided [min,max]
     * range.
     */
    public static int doubleToIntRoundedClamped(double v, int min, int max) {
        long rounded = Math.round(v);
        if (rounded > max) return max;
        if (rounded < min) return min;
        return (int) rounded;
    }
}

