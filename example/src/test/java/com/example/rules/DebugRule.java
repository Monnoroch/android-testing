package com.example.rules;

import com.example.BuildConfig;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/** A class that run tests only if build type equals to debug. */
public class DebugRule implements TestRule {

  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        if (BuildConfig.DEBUG) {
          base.evaluate();
        }
      }
    };
  }
}
