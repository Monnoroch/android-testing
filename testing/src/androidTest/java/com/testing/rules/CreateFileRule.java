package com.testing.rules;

import static com.google.common.base.Charsets.UTF_8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/** Class that creates file before test and remove this file after test. */
public class CreateFileRule implements TestRule {

  private final File file;
  private final String text;

  /**
   * Create {@link CreateFileRule} instance.
   *
   * @param file - file that will be created.
   * @param text - created file content.
   */
  public CreateFileRule(File file, String text) {
    this.file = file;
    this.text = text;
  }

  @Override
  public Statement apply(final Statement s, Description d) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        PrintWriter writer =
            new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8)),
                true);
        writer.println(text);
        writer.close();
        try {
          s.evaluate();
        } finally {
          file.delete();
        }
      }
    };
  }
}
