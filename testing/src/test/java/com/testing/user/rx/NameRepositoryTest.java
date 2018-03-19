package com.testing.user.rx;

import static org.mockito.Mockito.when;

import com.testing.common.FileReader;
import io.reactivex.observers.TestObserver;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/** Tests for {@link NameRepository}. */
@RunWith(MockitoJUnitRunner.class)
public class NameRepositoryTest {

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
  public void getName() {
    TestObserver<String> observer = nameRepository.getName().test();
    observer.assertValue("Sasha");
  }
}
