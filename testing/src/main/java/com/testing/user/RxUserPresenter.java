package com.testing.user;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Var;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter that get information related to user.
 */
public class RxUserPresenter {

    /**
     * Listener interface for interaction with views.
     */
    public interface Listener {

        /**
         * When user name successfully loaded.
         */
        void onUserNameLoaded(String name);

        /**
         * When an error occurred while getting user name.
         */
        void onGettingUserNameError(String message);
    }

    private final Listener listener;
    private final RxNameRepository nameRepository;
    @Var private Optional<Disposable> userNameDisposable = Optional.absent();

    /**
     * Create {@link UserPresenter} instance.
     *
     * @param listener - listener interface for interaction with views.
     * @param nameRepository - for getting user name.
     */
    public RxUserPresenter(Listener listener, RxNameRepository nameRepository) {
        this.listener = listener;
        this.nameRepository = nameRepository;
    }

    /**
     * Get user name.
     */
    void getUserName() {
        Preconditions.checkState(!userNameDisposable.isPresent(), "Presenter has already loading user name.");
        userNameDisposable = Optional.of(nameRepository.getName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> userNameDisposable = Optional.absent())
                .subscribe(listener::onUserNameLoaded, error -> listener.onGettingUserNameError(error.getMessage())));
    }

    /**
     * Stop all active loadings.
     */
    void stopLoading() {
        if (userNameDisposable.isPresent()) {
            userNameDisposable.get().dispose();
        }
    }
}
