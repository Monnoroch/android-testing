package com.example;

import com.example.user.UserComponent;
import com.example.user.UserModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ContextModule.class})
public interface ApplicationComponent {

  UserComponent createUserComponent(UserModule userModule);
}
