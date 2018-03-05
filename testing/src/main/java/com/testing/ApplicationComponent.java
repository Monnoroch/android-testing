package com.testing;

import com.testing.user.UserComponent;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Application component that provides all common components.
 */
@Singleton
@Component(modules = {ContextModule.class})
public interface ApplicationComponent {

    /**
     * Create user component for injecting components to user fragment.
     *
     * @return user component for injecting components to user fragment.
     */
    UserComponent createUserComponent();
}
