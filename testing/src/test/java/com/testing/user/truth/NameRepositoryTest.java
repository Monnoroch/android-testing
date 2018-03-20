package com.testing.user.truth;

import static com.google.common.truth.Truth.assertThat;

import com.testing.common.FileReader;
import com.testing.rules.CreateFileRule;
import com.testing.user.NameRepository;
import java.io.File;
import org.junit.Rule;
import org.junit.Test;

public class NameRepositoryTest {

  private static final File FILE = new File("test_file");
  private static final String FILE_CONTENT = "{name : Sasha}";
  @Rule public final CreateFileRule fileRule = new CreateFileRule(FILE, FILE_CONTENT);

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
