package com.testing.user.rx.timeout;

import com.testing.user.rx.NameRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/** Presenter that get information related to user. */
public class UserPresenter {

  /** Listener interface for interaction with views. */
  public interface Listener {

    /** When user name successfully loaded. */
    void onUserNameLoaded(String name);

    /** When an error occurred while getting user name. */
    void onGettingUserNameError(String message);
  }

  private static final int TIMEOUT_SEC = 2;

  private final Listener listener;
  private final NameRepository nameRepository;

  /**
   * Create {@link UserPresenter} instance.
   *
   * @param listener - listener interface for interaction with views.
   * @param nameRepository - for getting user name.
   */
  public UserPresenter(Listener listener, NameRepository nameRepository) {
    this.listener = listener;
    this.nameRepository = nameRepository;
  }

  /** Get user name. */
  public void getUserName() {
    Disposable disposable =
        nameRepository
            .getName()
            .timeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                listener::onUserNameLoaded,
                error -> listener.onGettingUserNameError(error.getMessage()));
  }
}
