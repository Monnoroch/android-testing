package com.testing.user.mockitorunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import com.testing.common.FileReader;
import com.testing.user.mockito.NameRepository;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NameRepositoryTest {

  @Mock FileReader fileReader;
  NameRepository nameRepository;

  @Before
  public void setUp() throws IOException {
    when(fileReader.readFile()).thenReturn("{name : Sasha}");
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
