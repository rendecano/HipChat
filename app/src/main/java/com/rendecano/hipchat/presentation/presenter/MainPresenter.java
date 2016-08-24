package com.rendecano.hipchat.presentation.presenter;

import android.util.Log;

import com.rendecano.hipchat.domain.interactor.ConvertUseCase;
import com.rendecano.hipchat.domain.subscriber.UseCaseSubscriber;
import com.rendecano.hipchat.presentation.presenter.view.MainView;

import javax.inject.Inject;

/**
 * Created by Ren Decano.
 */
public class MainPresenter implements Presenter<MainView> {

    private MainView view;
    private ConvertUseCase useCase;

    @Inject
    public MainPresenter(ConvertUseCase useCase) {
        this.useCase = useCase;
    }

    public void convertString(String inputString) {
        view.showLoading();
        useCase.convertChatMessage(inputString);
        useCase.execute(new ConvertSubscriber());
    }

    @Override
    public void attachView(MainView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        useCase.unsubscribe();
    }

    private class ConvertSubscriber extends UseCaseSubscriber<String> {

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
            view.showError(e.getMessage());
        }

        @Override
        public void onNext(String s) {
            view.hideLoading();
            view.showConvertedString(s);
        }
    }
}
