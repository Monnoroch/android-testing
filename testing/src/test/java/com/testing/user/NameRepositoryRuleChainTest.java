package com.testing.user;

import com.testing.common.FileReader;
import com.testing.rules.CreateFileRule;
import com.testing.rules.CreateTestDirRule;
import java.io.File;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

/** Tests for {@link NameRepository}. */
public class NameRepositoryRuleChainTest {

  private static final File FILE = Paths.get(CreateTestDirRule.getDirPath(), "test_file").toFile();
  private static final String FILE_CONTENT = "{name : Sasha}";

  @Rule
  public final RuleChain chain =
      RuleChain.outerRule(new CreateTestDirRule()).around(new CreateFileRule(FILE, FILE_CONTENT));

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
