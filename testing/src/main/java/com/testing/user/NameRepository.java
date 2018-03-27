package com.testing.user;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class NameRepository {

  private final File file;

  public NameRepository(File file) {
    this.file = file;
  }

  public String getName() throws IOException {
    Gson gson = new Gson();
    User user = gson.fromJson(readFile(), User.class);
    return user.name;
  }

  public String readFile() throws IOException {
    byte[] bytes = new byte[(int) file.length()];
    try (FileInputStream in = new FileInputStream(file)) {
      in.read(bytes);
    }
    return new String(bytes, Charset.defaultCharset());
  }

  private static final class User {
    String name;
  }
}
