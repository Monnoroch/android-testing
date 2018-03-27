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
  public void getName() {
    TestObserver<String> observer = nameRepository.getName().test();
    observer.assertValue("Sasha");
  }
}
