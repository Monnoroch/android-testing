package com.testing.user;

import com.google.gson.Gson;
import com.testing.common.FileReader;
import java.io.IOException;

public class NameRepository {

  private final FileReader fileReader;

  public NameRepository(FileReader fileReader) {
    this.fileReader = fileReader;
  }

  public String getName() throws IOException {
    Gson gson = new Gson();
    User user = gson.fromJson(fileReader.readFile(), User.class);
    return user.getName();
  }
}
