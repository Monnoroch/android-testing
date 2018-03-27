package com.example.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.support.test.runner.AndroidJUnit4;
import com.example.ApplicationComponent;
import com.example.MainActivity;
import com.example.rules.FragmentAsyncTestRule;
import com.example.rules.FragmentTestRule;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserFragmentTest {

  @ClassRule
  public static TestRule asyncRule =
      new FragmentAsyncTestRule<>(MainActivity.class, new UserFragment());

  @Rule
  public final FragmentTestRule<MainActivity, UserFragment> fragmentRule =
      new FragmentTestRule<>(
          MainActivity.class, new UserFragment(), createTestApplicationComponent());

  @Test
  public void assertGetNameMethodWasCalled() {
    verify(fragmentRule.getFragment().userPresenter).getUserName();
  }

  private ApplicationComponent createTestApplicationComponent() {
    ApplicationComponent component = mock(ApplicationComponent.class);
    when(component.createUserComponent(any(UserModule.class)))
        .thenReturn(DaggerUserFragmentTest_TestUserComponent.create());
    return component;
  }

  @Singleton
  @Component(modules = {TestUserModule.class})
  interface TestUserComponent extends UserComponent {}

  @Module
  static class TestUserModule {
    @Provides
    public UserPresenter provideUserPresenter() {
      return mock(UserPresenter.class);
    }
  }
}
