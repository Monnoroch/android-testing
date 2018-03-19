package com.testing.user.truth;

import static com.google.common.truth.Truth.assertThat;

import com.testing.common.FileReader;
import com.testing.rules.CreateFileRule;
import com.testing.rules.CreateTestDirRule;
import com.testing.user.NameRepository;
import java.io.File;
import java.nio.file.Paths;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

/** Tests for {@link NameRepository}. */
public class NameRepositoryTest {

  private static final File FILE = Paths.get(CreateTestDirRule.getDirPath(), "test_file").toFile();
  private static final String FILE_CONTENT = "{name : Sasha}";

  @Rule
  public final RuleChain chain =
      RuleChain.outerRule(new CreateTestDirRule()).around(new CreateFileRule(FILE, FILE_CONTENT));

  NameRepository nameRepository = new NameRepository(new FileReader(FILE));

  @Test
  public void getName_isSasha() throws Exception {
    String name = nameRepository.getName();
    assertThat(name).isEqualTo("Sasha");
  }

  @Test
  public void getName_notMia() throws Exception {
    String name = nameRepository.getName();
    assertThat(name).isNotEqualTo("Mia");
  }
}
