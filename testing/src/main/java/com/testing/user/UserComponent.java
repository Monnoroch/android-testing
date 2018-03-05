package com.testing.user;

import dagger.Subcomponent;

@SuppressWarnings("SingleLineJavadoc")
/** Launch subcomponent that injects components to user fragment. */
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

    /**
     * Inject components to user fragment.
     *
     * @param userFragment - fragment for injecting components.
     */
    void injectsUserFragment(UserFragment userFragment);
}
