package com.testing.user;

import java.io.IOException;

/** Presenter that get information related to user. */
public class UserPresenter {

    private final NameRepository nameRepository;

    /**
     * Create {@link UserPresenter} instance.
     *
     * @param nameRepository - for getting user name.
     */
    public UserPresenter(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    /**
     * Get user name.
     */
    String getUserName() throws IOException {
        return nameRepository.getName();
    }
}
