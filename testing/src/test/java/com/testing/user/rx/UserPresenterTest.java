package com.testing.user.rx;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testing.rules.CreateFileRule;
import com.testing.rules.RxImmediateSchedulerRule;
import io.reactivex.Observable;
import java.io.File;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class UserPresenterTest {

  private static final File FILE = new File("test_file");
  private static final String FILE_CONTENT = "{name : Sasha}";
  @Rule public final MockitoRule rule = MockitoJUnit.rule();
  @Rule public final RxImmediateSchedulerRule immediateSchedulerRule = new RxImmediateSchedulerRule();
  @Rule public final CreateFileRule fileRule = new CreateFileRule(FILE, FILE_CONTENT);

  @Mock UserPresenter.Listener listener;
  @Mock NameRepository nameRepository;
  UserPresenter presenter;

  @Before
  public void setUp() {
    when(nameRepository.getName()).thenReturn(Observable.just("Sasha"));
    presenter = new UserPresenter(listener, nameRepository);
  }

  @Test
  public void getUserName() {
    presenter.getUserName();
    verify(listener).onUserNameLoaded("Sasha");
  }
}
