package com.testing.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/** Reads string from file content. */
public class FileReader {

    private final File file;

    /**
     * Create {@link FileReader} instance.
     *
     * @param file - file for reading content.
     */
    public FileReader(File file) {
        this.file = file;
    }

    /**
     * Read string from file content.
     *
     * @return file content string.
     */
    public String readFile() throws IOException {
        byte[] bytes = new byte[(int) file.length()];
        try (FileInputStream in = new FileInputStream(file)) {
            in.read(bytes);
        }
        return new String(bytes, Charset.defaultCharset());
    }
}
