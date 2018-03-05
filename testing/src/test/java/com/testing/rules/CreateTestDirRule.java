package com.testing.rules;

import java.io.File;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Class that creates test dir before test and remove this dir after test.
 */
public class CreateTestDirRule implements TestRule {

    private static final File DIR = new File("tests");

    /**
     * Get full path to test dir.
     *
     * @return path of test dir.
     */
    public static String getDirPath() {
        return DIR.getAbsolutePath();
    }

    @Override
    public Statement apply(final Statement s, Description d) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                boolean isDirCreated = DIR.mkdir();
                try {
                    s.evaluate();
                } finally {
                    boolean isDirDeleted = DIR.delete();
                }
            }
        };
    }
}
