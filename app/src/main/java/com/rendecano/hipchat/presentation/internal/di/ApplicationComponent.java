package com.rendecano.hipchat.presentation.internal.di;

import com.rendecano.hipchat.presentation.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ren Decano.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainActivity activity);
}



