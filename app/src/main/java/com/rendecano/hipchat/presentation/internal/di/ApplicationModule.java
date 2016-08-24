package com.rendecano.hipchat.presentation.internal.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rendecano.hipchat.data.repository.ConvertDataRepository;
import com.rendecano.hipchat.data.util.CollectionAdapter;
import com.rendecano.hipchat.domain.ConvertRepository;
import com.rendecano.hipchat.domain.schedulers.ObserveOn;
import com.rendecano.hipchat.domain.schedulers.SubscribeOn;
import com.rendecano.hipchat.presentation.AndroidApplication;

import java.util.Collection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ren Decano.
 */
@Module
public class ApplicationModule {
    private AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    public ConvertRepository provideConvertRepository(ConvertDataRepository convertDataRepository) {
        return convertDataRepository;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder().registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter()).create();
    }

    @Singleton
    @Provides
    SubscribeOn provideSubscribeOn() {
        return (new SubscribeOn() {
            @Override
            public Scheduler getScheduler() {
                return Schedulers.newThread();
            }
        });
    }

    @Singleton
    @Provides
    ObserveOn provideObserveOn() {
        return (new ObserveOn() {
            @Override
            public Scheduler getScheduler() {
                return AndroidSchedulers.mainThread();
            }
        });
    }
}
