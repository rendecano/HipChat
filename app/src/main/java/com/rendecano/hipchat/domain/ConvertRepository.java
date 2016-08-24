package com.rendecano.hipchat.domain;

import rx.Observable;

/**
 * Created by Ren Decano.
 */

public interface ConvertRepository {
    Observable<String> convertChatMessage(String chatMessage);
}
