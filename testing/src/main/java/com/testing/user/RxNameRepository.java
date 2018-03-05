package com.testing.user;

import com.google.gson.Gson;
import com.testing.common.FileReader;
import io.reactivex.Observable;

/**
 * Reads user name from file.
 */
public class RxNameRepository {

    private final FileReader fileReader;

    /**
     * Create {@link NameRepository} instance.
     *
     * @param fileReader - for reading string from file content.
     */
    public RxNameRepository(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * Get user name from file.
     *
     * @return RxJava observable that read user name from file when subscribe to it.
     */
    public Observable<String> getName() {
        return Observable.create(emitter -> {
            Gson gson = new Gson();
            emitter.onNext(gson.fromJson(fileReader.readFile(), User.class).getName());
        });
    }
}
