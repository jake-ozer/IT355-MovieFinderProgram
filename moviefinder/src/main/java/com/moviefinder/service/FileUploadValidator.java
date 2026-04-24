/**
 *
 *  CWE-434 (Unrestricted Upload of File with Dangerous Type)
 *  CWE-178 (Improper Handling of Case Sensitivity)
 */

package com.moviefinder.service;

import java.util.Set;

public class FileUploadValidator {

    // CWE-434: only these extensions are allowed, everything else is rejected
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".txt", ".csv", ".dat");

    // CWE-434: check file extension against the allowlist before accepting it
    // CWE-178: extension is lowercased so ".TXT" and ".txt" are treated the same
    public boolean isSafeFile(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            System.out.println("File name is empty, rejected.");
            return false;
        }

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            System.out.println("No file extension found — rejected.");
            return false;
        }

        // CWE-178: normalize to lowercase before checking
        String extension = fileName.substring(dotIndex).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            // CWE-134: file name goes into %s, not used as the format string
            System.out.printf("File type \"%s\" is not allowed — rejected.%n", extension);
            return false;
        }

        return true;
    }
}