package com.testing.user.rx;

import com.google.gson.Gson;
import com.testing.common.FileReader;
import com.testing.user.User;
import io.reactivex.Observable;

public class NameRepository {

  private final FileReader fileReader;

  public NameRepository(FileReader fileReader) {
    this.fileReader = fileReader;
  }

  public Observable<String> getName() {
    return Observable.create(
        emitter -> {
          Gson gson = new Gson();
          emitter.onNext(gson.fromJson(fileReader.readFile(), User.class).getName());
        });
  }
}
