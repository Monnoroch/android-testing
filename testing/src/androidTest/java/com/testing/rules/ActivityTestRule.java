package com.testing.rules;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.v7.app.AppCompatActivity;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 *
 * <pre>
 * A {@link TestRule} to be used for testing an {@link AppCompatActivity}.
 * This rule is configured with an {@link android.app.Activity} class. It will:
 * <ul>
 *   <li>- Launch the {@link android.app.Activity} from the provided class,
 *   <li>- unlock the emulator screen
 * </ul>
 * before all test code is executed.
 * Test code can get the {@link android.app.Activity} using {@code getActivity()} method.
 * To use this rule add the following code to the test:
 * </pre>
 *
 * <pre>{@code
 * @Rule public final ActivityTestRule&lt;MainActivity&gt; activityRule =
 *     new ActivityTestRule&lt;&gt;(MainActivity.class);
 * }</pre>
 *
 * @param <A> - the {@link AppCompatActivity} class to launch.
 */
public class ActivityTestRule<A extends AppCompatActivity> implements TestRule {

  private final android.support.test.rule.ActivityTestRule<A> activityRule;
  private final RuleChain ruleChain;

  /**
   * Create ActivityTestRule instance.
   *
   * @param activityClass - class of activity that will be launched.
   */
  public ActivityTestRule(Class<A> activityClass) {
    this.activityRule = new IntentsTestRule<>(activityClass, true, true);
    ruleChain = RuleChain.outerRule(activityRule).around(new UnlockScreenRule(activityRule));
  }

  /**
   * Get activity rule.
   *
   * @return current launched ActivityTestRule instance.
   */
  public android.support.test.rule.ActivityTestRule<A> getActivityRule() {
    return activityRule;
  }

  /**
   * Helper for running portions of a test on the UI thread. Test will wait before runnable will end
   * executing.
   *
   * @param runnable - action that will be executed on UI thread.
   */
  public void runOnUiThread(Runnable runnable) throws Throwable {
    activityRule.runOnUiThread(runnable);
  }

  /**
   * Get activity.
   *
   * @return activity that should be launched.
   */
  public A getActivity() {
    return activityRule.getActivity();
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return ruleChain.apply(statement, description);
  }
}
