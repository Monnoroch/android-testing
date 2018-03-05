package com.testing.user;

import com.testing.common.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link NameRepository}.
 */
public class NameRepositoryWithAnnotationsTest {

    private static final File FILE = new File("test_file");

    NameRepository nameRepository = new NameRepository(new FileReader(FILE));

    /**
     * Set up.
     */
    @Before
    public void setUp() throws Exception {
        PrintWriter writer = new PrintWriter(new FileOutputStream(FILE), true);
        writer.println("{name : Sasha}");
        writer.close();
    }

    @Test
    public void getName_isSasha() throws Exception {
        String name = nameRepository.getName();
        Assert.assertEquals(name, "Sasha");
    }

    @Test
    public void getName_notMia() throws Exception {
        String name = nameRepository.getName();
        Assert.assertNotEquals(name, "Mia");
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        FILE.delete();
    }
}
