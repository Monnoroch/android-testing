package com.testing.user;

import com.google.gson.Gson;
import com.testing.common.FileReader;
import java.io.IOException;

/** Reads user name from file. */
public class NameRepository {

    private final FileReader fileReader;

    /**
     * Create {@link NameRepository} instance.
     *
     * @param fileReader - for reading string from file content.
     */
    public NameRepository(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * Get user name from file.
     *
     * @return user name.
     */
    public String getName() throws IOException {
        Gson gson = new Gson();
        User user = gson.fromJson(fileReader.readFile(), User.class);
        return user.getName();
    }
}
