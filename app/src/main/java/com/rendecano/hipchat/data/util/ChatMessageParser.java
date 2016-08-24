package com.rendecano.hipchat.data.util;

import com.google.gson.Gson;
import com.rendecano.hipchat.data.model.ChatMessage;
import com.rendecano.hipchat.data.model.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Ren Decano.
 */
public class ChatMessageParser {

    private static final String REGEX_MENTION_PATTERN = "(?:^|[^a-zA-Z0-9_＠!@#$%&*])(?:(?:@|＠)(?!\\/))([\\w]+)(?:\\b(?!@|＠)|$)";
    private static final String REGEX_EMOTICON_PATTERN = "\\(([^())]+[\\w]{1,15}+)\\)";
    private static final String REGEX_URL_PATTERN = "\\(?\\b((https?|ftp|gopher|telnet|file)://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";

    private Gson gson;

    @Inject
    public ChatMessageParser(Gson gson) {
        this.gson = gson;
    }

    public Observable<List<String>> extractMentions(final String chatMessage) {

        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {

                subscriber.onNext(extractItems(chatMessage, REGEX_MENTION_PATTERN));
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<String>> extractEmoticons(final String chatMessage) {

        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(extractItems(chatMessage, REGEX_EMOTICON_PATTERN));
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<Link>> extractUrl(final String chatMessage) {

        return Observable.create(new Observable.OnSubscribe<List<Link>>() {
            @Override
            public void call(Subscriber<? super List<Link>> subscriber) {

                List<Link> links = new ArrayList<Link>();

                Pattern pattern = Pattern.compile(REGEX_URL_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                Matcher matcher = pattern.matcher(chatMessage);

                while (matcher.find()) {

                    String urlStr = matcher.group();

                    if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                        urlStr = urlStr.substring(1, urlStr.length() - 1);
                    }

                    Link link = new Link();
                    link.setUrl(urlStr);
                    links.add(link);
                }

                subscriber.onNext(links);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<String> convertObjectToJson(final ChatMessage message) {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext(formatString(gson.toJson(message)));
                subscriber.onCompleted();
            }
        });
    }

    private List<String> extractItems(String chatMessage, String pPattern) {
        List<String> itemList = new ArrayList<String>();

        Pattern pattern = Pattern.compile(pPattern);
        Matcher matcher = pattern.matcher(chatMessage);

        while (matcher.find()) {

            itemList.add(matcher.group(1));
        }

        return itemList;
    }

    private String formatString(String text){

        StringBuilder json = new StringBuilder();
        String indentString = "";

        boolean inQuotes = false;
        boolean isEscaped = false;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);

            switch (letter) {
                case '\\':
                    isEscaped = !isEscaped;
                    break;
                case '"':
                    if (!isEscaped) {
                        inQuotes = !inQuotes;
                    }
                    break;
                default:
                    isEscaped = false;
                    break;
            }

            if (!inQuotes && !isEscaped) {
                switch (letter) {
                    case '{':
                    case '[':
                        json.append("\n" + indentString + letter + "\n");
                        indentString = indentString + "\t";
                        json.append(indentString);
                        break;
                    case '}':
                    case ']':
                        indentString = indentString.replaceFirst("\t", "");
                        json.append("\n" + indentString + letter);
                        break;
                    case ',':
                        json.append(letter + "\n" + indentString);
                        break;
                    default:
                        json.append(letter);
                        break;
                }
            } else {
                json.append(letter);
            }
        }

        return json.toString();
    }
}
