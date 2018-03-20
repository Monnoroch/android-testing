package com.testing;

import com.testing.user.UserComponent;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ContextModule.class})
public interface ApplicationComponent {

  UserComponent createUserComponent();
}
