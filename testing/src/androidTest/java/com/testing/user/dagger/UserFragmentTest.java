package com.testing.user.dagger;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import com.testing.ApplicationComponent;
import com.testing.MainActivity;
import com.testing.rules.FragmentAsyncTestRule;
import com.testing.rules.FragmentTestRule;
import com.testing.user.UserComponent;
import com.testing.user.rx.NameRepository;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
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
  public void awaitTextViewHasText() {
    await()
        .atMost(5, SECONDS)
        .ignoreExceptions()
        .untilAsserted(() -> onView(ViewMatchers.withText("Sasha")).check(matches(isDisplayed())));
  }

  private ApplicationComponent createTestApplicationComponent() {
    ApplicationComponent component = mock(ApplicationComponent.class);
    when(component.createUserComponent())
        .thenReturn(DaggerUserFragmentTest_TestUserComponent.create());
    return component;
  }

  @Singleton
  @Component(modules = {TestUserModule.class})
  interface TestUserComponent extends UserComponent {}

  @Module
  static class TestUserModule {
    @Provides
    public NameRepository provideNameRepository() {
      NameRepository nameRepository = mock(NameRepository.class);
      when(nameRepository.getName()).thenReturn(Single.fromCallable(() -> "Sasha"));
      return nameRepository;
    }
  }
}
