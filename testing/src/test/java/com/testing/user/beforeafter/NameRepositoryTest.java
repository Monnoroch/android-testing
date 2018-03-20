package com.testing.user.beforeafter;

import static org.apache.maven.artifact.ant.shaded.WriterFactory.UTF_8;

import com.testing.common.FileReader;
import com.testing.user.NameRepository;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NameRepositoryTest {

  private static final File FILE = new File("test_file");

  NameRepository nameRepository = new NameRepository(new FileReader(FILE));

  @Before
  public void setUp() throws Exception {
    PrintWriter writer =
        new PrintWriter(
            new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE), UTF_8)), true);
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

  @After
  public void tearDown() {
    FILE.delete();
  }
}
