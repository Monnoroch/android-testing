package com.example.user;

import com.example.BuildConfig;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class UserPresenter {

  public interface Listener {
    void onUserNameLoaded(String name);

    void onGettingUserNameError(String message);
  }

  private static final int TIMEOUT_SEC = 2;

  private final Listener listener;
  private final NameRepository nameRepository;
  private final Logger logger;
  private Disposable disposable;

  public UserPresenter(Listener listener, NameRepository nameRepository, Logger logger) {
    this.listener = listener;
    this.nameRepository = nameRepository;
    this.logger = logger;
  }

  public void getUserName() {
    disposable =
        nameRepository
            .getName()
            .timeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                name -> {
                  listener.onUserNameLoaded(name);
                  if (BuildConfig.DEBUG) {
                    logger.info(String.format("Name loaded: %s", name));
                  }
                },
                error -> listener.onGettingUserNameError(error.getMessage()));
  }

  public void stopLoading() {
    disposable.dispose();
  }
}
