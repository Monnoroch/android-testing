package com.testing.rules;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.testing.common.ActivityUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A rule to be used for testing of a single fragment. Fragment will be opened before each test
 * annotated with @Test and before methods annotated with @Before. During the duration of the test
 * you will be able to manipulate Fragment directly.
 *
 * @param <A> - the activity to test.
 */
class OpenFragmentRule<A extends AppCompatActivity> implements TestRule {

  private final ActivityTestRule<A> activityRule;
  private final Fragment fragment;
  private final int timeoutSec;

  /**
   * Create fragment test rule, don't forget to add @Rule.
   *
   * @param activityRule - Activity Test Rule for starting fragment.
   * @param fragment - fragment for opening.
   * @param timeoutSec - timeout for opening fragment.
   */
  OpenFragmentRule(ActivityTestRule<A> activityRule, Fragment fragment, int timeoutSec) {
    this.activityRule = activityRule;
    this.fragment = fragment;
    this.timeoutSec = timeoutSec;
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        ActivityUtils.openFragment(activityRule.getActivity(), fragment);
        await().atMost(timeoutSec, SECONDS).until(fragment::isResumed);
        statement.evaluate();
      }
    };
  }
}
