package com.moviefinder.util;

import java.util.List;

/**
 * Helper to avoid CWE-466 style mistakes (returning a reference outside
 * of the expected range).
 *
 * Usage: call getElementSafe(list, index) instead of list.get(index) when the
 * index may be untrusted. It returns null when the index is invalid.
 */
public final class SafeReturn {

    private SafeReturn() { /* no instances */ }

    /**
     * Return the element at index, or null if the index is out of range or the
     * list is null.
     */
    public static <T> T getElementSafe(List<T> list, int index) {
        if (list == null) return null;
        if (index < 0 || index >= list.size()) return null;
        return list.get(index);
    }
}

