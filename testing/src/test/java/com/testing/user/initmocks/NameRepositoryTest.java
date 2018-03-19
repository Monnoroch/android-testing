package com.testing.user.initmocks;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.testing.common.FileReader;
import com.testing.user.NameRepository;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/** Tests for {@link NameRepository}. */
public class NameRepositoryTest {

  private static final String FILE_CONTENT = "{name : Sasha}";

  @Mock FileReader fileReader;
  NameRepository nameRepository;

  /** Set up. */
  @Before
  public void setUp() throws IOException {
    initMocks(this);
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
