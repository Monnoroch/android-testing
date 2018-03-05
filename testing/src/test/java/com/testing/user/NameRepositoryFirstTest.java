package com.testing.user;

import com.testing.common.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link NameRepository}.
 */
public class NameRepositoryFirstTest {

    private static final File FILE = new File("test_file");

    NameRepository nameRepository = new NameRepository(new FileReader(FILE));

    @Test
    public void getName_isSasha() throws Exception {
        PrintWriter writer = new PrintWriter(new FileOutputStream(FILE), true);
        writer.println("{name : Sasha}");
        writer.close();

        String name = nameRepository.getName();
        Assert.assertEquals(name, "Sasha");

        FILE.delete();
    }

    @Test
    public void getName_notMia() throws Exception {
        PrintWriter writer = new PrintWriter(new FileOutputStream(FILE), true);
        writer.println("{name : Sasha}");
        writer.close();

        String name = nameRepository.getName();
        Assert.assertNotEquals(name, "Mia");

        FILE.delete();
    }
}
