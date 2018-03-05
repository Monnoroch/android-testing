package com.testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.testing.user.UserComponent;
import com.testing.user.UserPresenter;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * This class creating ApplicationComponent mock that provides mocked components for tests.
 */
public class TestApplicationComponent {

    private TestApplicationComponent() {}

    /**
     * Create ApplicationComponent mock that provides mocked components for tests.
     *
     * @return ApplicationComponent mock.
     */
    public static ApplicationComponent create() {
        ApplicationComponent component = mock(ApplicationComponent.class);
        when(component.createUserComponent())
            .thenReturn(DaggerTestApplicationComponent_TestUserComponent.create());

        return component;
    }

    @Singleton
    @Component(modules = {TestUserModule.class})
    interface TestUserComponent extends UserComponent{}

    @Module
    static class TestUserModule {
        @Provides
        public UserPresenter provideUserPresenter() {
            return mock(UserPresenter.class);
        }
    }
}
