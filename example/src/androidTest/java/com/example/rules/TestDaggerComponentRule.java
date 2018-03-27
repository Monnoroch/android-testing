package com.example.rules;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import com.example.ApplicationComponent;
import com.example.MainApplication;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A rule to be used for replace dagger component. Component will be replaced before each test
 * annotated with @Test and before methods annotated with @Before. Original component will be
 * returned after the test is completed.
 */
class TestDaggerComponentRule<A extends AppCompatActivity> implements TestRule {

  private final ActivityTestRule<A> activityRule;
  private final ApplicationComponent component;

  /**
   * Create {@link TestDaggerComponentRule} instance.
   *
   * @param activityRule - activity rule for getting application to replace dagger component.
   * @param component - test component for setting before evaluate.
   */
  TestDaggerComponentRule(ActivityTestRule<A> activityRule, ApplicationComponent component) {
    this.activityRule = activityRule;
    this.component = component;
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        MainApplication application =
            ((MainApplication) activityRule.getActivity().getApplication());
        ApplicationComponent originalComponent = application.getComponent();

        application.setComponentForTest(component);
        try {
          statement.evaluate();
        } finally {
          application.setComponentForTest(originalComponent);
        }
      }
    };
  }
}
