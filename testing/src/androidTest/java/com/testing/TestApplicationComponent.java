package com.testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.testing.user.NameRepository;
import com.testing.user.UserComponent;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

public class TestApplicationComponent {

  private TestApplicationComponent() {}

  public static ApplicationComponent create() {
    ApplicationComponent component = mock(ApplicationComponent.class);
    when(component.createUserComponent())
        .thenReturn(DaggerTestApplicationComponent_TestUserComponent.create());
    return component;
  }

  @Singleton
  @Component(modules = {TestUserModule.class})
  interface TestUserComponent extends UserComponent {}

  @Module
  static class TestUserModule {
    @Provides
    public NameRepository provideNameRepository() {
      return mock(NameRepository.class);
    }
  }
}
