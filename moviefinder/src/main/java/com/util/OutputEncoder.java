/**
 * CWEs applied in this file:
 *  - CWE-116 (Improper Encoding or Escaping of Output)
 */

package com.util;

public final class OutputEncoder {

    private OutputEncoder() {}

    // CWE-116: Basic escaping for console output
    public static String escape(String s) {
        if (s == null) return "";
        return s.replaceAll("\\p{Cntrl}", "");
    }
}
