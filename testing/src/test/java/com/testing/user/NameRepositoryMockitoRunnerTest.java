package com.testing.user;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import com.testing.common.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/** Tests for {@link NameRepository}. */
@RunWith(MockitoJUnitRunner.class)
public class NameRepositoryMockitoRunnerTest {

  private static final String FILE_CONTENT = "{name : Sasha}";

  @Mock FileReader fileReader;
  NameRepository nameRepository;

  /** Set up. */
  @Before
  public void setUp() throws IOException {
    when(fileReader.readFile()).thenReturn(FILE_CONTENT);
    nameRepository = new NameRepository(fileReader);
  }

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
