package com.moviefinder.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileTypeValidator {

    private FileTypeValidator() { /* no instances */ }

    /**
     * Return true if the file at path looks like text based on a small sample.
     * If the file cannot be read, returns false
     */
    public static boolean isProbablyText(Path path, int sampleSize) {
        if (path == null) return false;
        if (sampleSize <= 0) sampleSize = 512;

        try (InputStream in = Files.newInputStream(path)) {
            byte[] buf = new byte[Math.min(sampleSize, 4096)];
            int read = in.read(buf);
            if (read <= 0) return true;

            int controlCount = 0;
            for (int i = 0; i < read; i++) {
                int b = buf[i] & 0xFF;
                if (b == 0) {
                    return false;
                }

                if (b == 9 || b == 10 || b == 13) continue;
                if (b >= 32 && b <= 126) continue;
                if (b >= 128) continue;
                controlCount++;
            }

            double ratio = (double) controlCount / (double) read;
            return ratio < 0.30;
        } catch (IOException e) {
            return false;
        }
    }
}

