package com.testing;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import android.support.test.runner.AndroidJUnit4;
import com.testing.rules.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Tests for {@link ActivityTestRule} that unlock screen. */
@RunWith(AndroidJUnit4.class)
public class MainActivityUnlockScreenTest {

  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class);

  @Test
  public void unlockScreenTest() {
    await()
        .atMost(5, SECONDS)
        .until(
            () ->
                activityTestRule
                    .getActivity()
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.container)
                    .isResumed());
  }
}
