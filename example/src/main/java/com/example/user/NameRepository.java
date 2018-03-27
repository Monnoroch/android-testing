package com.example.user;

import com.example.common.FileReader;
import com.google.gson.Gson;
import io.reactivex.Single;

public class NameRepository {

  private final FileReader fileReader;

  public NameRepository(FileReader fileReader) {
    this.fileReader = fileReader;
  }

  public Single<String> getName() {
    return Single.create(
        emitter -> {
          Gson gson = new Gson();
          emitter.onSuccess(gson.fromJson(fileReader.readFile(), User.class).name);
        });
  }

  private static final class User {
    String name;
  }
}
