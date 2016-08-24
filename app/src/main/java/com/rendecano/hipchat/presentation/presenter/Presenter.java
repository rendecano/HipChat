package com.rendecano.hipchat.presentation.presenter;

import com.rendecano.hipchat.presentation.presenter.view.DefaultView;

/**
 * Created by Ren Decano.
 */

public interface Presenter<T extends DefaultView> {

    void attachView(T view);

    void destroy();

}
