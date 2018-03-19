package com.testing.user.dagger;

import static org.mockito.Mockito.verify;

import android.support.test.runner.AndroidJUnit4;
import com.testing.MainActivity;
import com.testing.TestApplicationComponent;
import com.testing.rules.FragmentTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Tests for {@link UserFragment}. */
@RunWith(AndroidJUnit4.class)
public class UserFragmentTest {

  private static final int WAIT_TIME = 5;
  public final UserFragment userFragment = new UserFragment();

  @Rule
  public final FragmentTestRule<MainActivity, UserFragment> fragmentRule =
      new FragmentTestRule<>(
          MainActivity.class, userFragment, WAIT_TIME, TestApplicationComponent.create());

  @Test
  public void getName() throws Throwable {
    verify(userFragment.userPresenter).getUserName();
  }
}
