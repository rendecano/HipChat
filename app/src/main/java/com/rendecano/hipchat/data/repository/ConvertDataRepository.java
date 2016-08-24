package com.rendecano.hipchat.data.repository;

import android.content.Context;

import com.rendecano.hipchat.data.model.ChatMessage;
import com.rendecano.hipchat.data.model.Link;
import com.rendecano.hipchat.data.network.NetworkData;
import com.rendecano.hipchat.data.util.ChatMessageParser;
import com.rendecano.hipchat.domain.ConvertRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Ren Decano.
 */

public class ConvertDataRepository implements ConvertRepository {

    NetworkData networkData;
    ChatMessageParser chatMessageParser;
    Context context;

    @Inject
    public ConvertDataRepository(Context context, NetworkData networkData, ChatMessageParser chatMessageParser) {
        this.context = context;
        this.networkData = networkData;
        this.chatMessageParser = chatMessageParser;
    }

    @Override
    public Observable<String> convertChatMessage(final String chatMessage) {

        // Extract mentions from the input string
        return chatMessageParser.extractMentions(chatMessage)
                .concatMap(new Func1<List<String>, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(final List<String> mentions) {

                        // Extract emoticons from the input string
                        return chatMessageParser.extractEmoticons(chatMessage)
                                .concatMap(new Func1<List<String>, Observable<? extends String>>() {
                                    @Override
                                    public Observable<? extends String> call(final List<String> emoticons) {

                                        // Extract urls from the input string
                                        return chatMessageParser.extractUrl(chatMessage)
                                                .concatMap(new Func1<List<Link>, Observable<? extends String>>() {
                                                    @Override
                                                    public Observable<? extends String> call(List<Link> links) {

                                                        // Get all titles from network
                                                        return networkData.getTitles(links)
                                                                .concatMap(new Func1<List<Link>, Observable<? extends String>>() {
                                                                    @Override
                                                                    public Observable<? extends String> call(List<Link> links) {

                                                                        // Wrap all objects into one object for conversion to JSON string
                                                                        ChatMessage message = new ChatMessage();
                                                                        message.setEmoticons(emoticons);
                                                                        message.setMentions(mentions);
                                                                        message.setLinks(links);
                                                                        return chatMessageParser.convertObjectToJson(message);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }
}
