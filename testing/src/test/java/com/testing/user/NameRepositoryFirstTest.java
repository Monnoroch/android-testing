package com.testing.user;

import static org.apache.maven.artifact.ant.shaded.WriterFactory.UTF_8;

import com.testing.common.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE), UTF_8)), true);
        writer.println("{name : Sasha}");
        writer.close();

        String name = nameRepository.getName();
        Assert.assertEquals(name, "Sasha");

        boolean isFileDeleted = FILE.delete();
    }

    @Test
    public void getName_notMia() throws Exception {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE), UTF_8)), true);
        writer.println("{name : Sasha}");
        writer.close();

        String name = nameRepository.getName();
        Assert.assertNotEquals(name, "Mia");

        boolean isFileDeleted = FILE.delete();
    }
}
