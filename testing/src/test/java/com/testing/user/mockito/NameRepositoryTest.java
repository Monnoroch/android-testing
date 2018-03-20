package com.testing.user.mockito;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.testing.common.FileReader;
import com.testing.user.NameRepository;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class NameRepositoryTest {

  private static final String FILE_CONTENT = "{name : Sasha}";

  FileReader fileReader = mock(FileReader.class);
  NameRepository nameRepository = new NameRepository(fileReader);

  @Before
  public void setUp() throws IOException {
    when(fileReader.readFile()).thenReturn(FILE_CONTENT);
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
