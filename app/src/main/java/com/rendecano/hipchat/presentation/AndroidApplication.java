package com.rendecano.hipchat.presentation;

import android.app.Application;

import com.rendecano.hipchat.presentation.internal.di.ApplicationComponent;
import com.rendecano.hipchat.presentation.internal.di.ApplicationModule;
import com.rendecano.hipchat.presentation.internal.di.DaggerApplicationComponent;

/**
 * Created by Ren Decano.
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return  applicationComponent;
    }
}
