package com.example.rules;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.example.ApplicationComponent;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 *
 * <pre>
 * A {@link TestRule} to be used for testing a {@link Fragment}.
 * This rule is configured with an {@link AppCompatActivity} and a {@link Fragment} classes
 *     and an optional {@link ApplicationComponent}. It will:
 * <ul>
 *   <li>- Launch the {@link AppCompatActivity} from the provided class,
 *   <li>- unlock the emulator screen,
 *   <li>- optionally replace the {@link ApplicationComponent} in the {@link com.example.MainActivity} with the provided one,
 *   <li>- and open the provided fragment
 * </ul>
 * before all test code is executed.
 * Test code can get the {@link AppCompatActivity} and the {@link Fragment}
 *     using {@code getActivity()} and {@code getFragment()} methods.
 * To use this rule add the following code to the test:
 * </pre>
 *
 * <pre>{@code
 * @Rule public final FragmentTestRule&lt;MainActivity, LaunchFragment&gt; fragmentRule =
 *     new FragmentTestRule&lt;&gt;(MainActivity.class, new MainFragment(), TestComponent.create(), 10);
 * }</pre>
 *
 * @param <A> - the {@link Activity} class to launch.
 * @param <F> - the {@link Fragment} class to open.
 */
public class FragmentTestRule<A extends AppCompatActivity, F extends Fragment> implements TestRule {

  private ActivityTestRule<A> activityRule;
  private F fragment;
  private RuleChain ruleChain;

  private RuleChain init(Class<A> activityClass, F fragment) {
    this.fragment = fragment;
    this.activityRule = new ActivityTestRule<A>(activityClass, true, true);
    return RuleChain.outerRule(activityRule).around(new UnlockScreenRule(activityRule));
  }

  /**
   * Create FragmentTestRule instance that doesn't replace dagger component.
   *
   * @param activityClass - class of activity that will be launched.
   * @param fragment - fragment for opening.
   */
  public FragmentTestRule(Class<A> activityClass, F fragment) {
    ruleChain =
        init(activityClass, fragment).around(new OpenFragmentRule<>(activityRule, fragment));
  }

  /**
   * Create FragmentTestRule instance that replace dagger component.
   *
   * @param activityClass - class of activity that will be launched.
   * @param fragment - fragment for opening.
   * @param component - dagger test component for replacing.
   */
  public FragmentTestRule(Class<A> activityClass, F fragment, ApplicationComponent component) {
    ruleChain =
        init(activityClass, fragment)
            .around(new TestDaggerComponentRule<>(activityRule, component))
            .around(new OpenFragmentRule<>(activityRule, fragment));
  }

  /**
   * Get activity rule.
   *
   * @return current launched ActivityTestRule instance.
   */
  public ActivityTestRule<A> getActivityRule() {
    return activityRule;
  }

  /**
   * Get fragment that will be opened.
   *
   * @return fragment for opening.
   */
  public F getFragment() {
    return fragment;
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
