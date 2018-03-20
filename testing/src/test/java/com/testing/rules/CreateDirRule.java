package com.testing.rules;

import java.io.File;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/** Class that creates test dir before test and remove this dir after test. */
public class CreateDirRule implements TestRule {

  private final File dir;

  /**
   * Create {@link CreateDirRule} instance.
   *
   * @param dir - dir that will be created.
   */
  public CreateDirRule(File dir) {
    this.dir = dir;
  }

  @Override
  public Statement apply(final Statement s, Description d) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        dir.mkdir();
        try {
          s.evaluate();
        } finally {
          dir.delete();
        }
      }
    };
  }
}
