package com.testing.user.rx;

import com.google.gson.Gson;
import com.testing.common.FileReader;
import com.testing.user.User;
import io.reactivex.Observable;

/** Reads user name from file. */
public class NameRepository {

  private final FileReader fileReader;

  /**
   * Create {@link com.testing.user.NameRepository} instance.
   *
   * @param fileReader - for reading string from file content.
   */
  public NameRepository(FileReader fileReader) {
    this.fileReader = fileReader;
  }

  /**
   * Get user name from file.
   *
   * @return RxJava observable that read user name from file when subscribe to it.
   */
  public Observable<String> getName() {
    return Observable.create(
        emitter -> {
          Gson gson = new Gson();
          emitter.onNext(gson.fromJson(fileReader.readFile(), User.class).getName());
        });
  }
}
