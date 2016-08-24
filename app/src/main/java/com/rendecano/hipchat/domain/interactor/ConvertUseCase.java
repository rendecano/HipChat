package com.rendecano.hipchat.domain.interactor;

import com.rendecano.hipchat.domain.ConvertRepository;
import com.rendecano.hipchat.domain.schedulers.ObserveOn;
import com.rendecano.hipchat.domain.schedulers.SubscribeOn;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Ren Decano.
 */
public class ConvertUseCase extends UseCase<String> {

    private String chatMessage;

    @Inject
    public ConvertUseCase(ConvertRepository repository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(repository, subscribeOn, observeOn);
    }

    public void convertChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return convertRepository.convertChatMessage(chatMessage);
    }
}
