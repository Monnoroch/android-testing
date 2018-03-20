package com.testing.user.rulechain;

import com.testing.common.FileReader;
import com.testing.rules.CreateDirRule;
import com.testing.rules.CreateFileRule;
import com.testing.user.NameRepository;
import java.io.File;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

public class NameRepositoryTest {

  private static final File DIR = new File("test_dir");
  private static final File FILE = Paths.get(DIR.toString(), "test_file").toFile();
  private static final String FILE_CONTENT = "{name : Sasha}";

  @Rule
  public final RuleChain chain =
      RuleChain.outerRule(new CreateDirRule(DIR)).around(new CreateFileRule(FILE, FILE_CONTENT));

  NameRepository nameRepository = new NameRepository(new FileReader(FILE));

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
}
