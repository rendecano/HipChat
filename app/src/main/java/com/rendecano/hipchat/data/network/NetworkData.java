package com.rendecano.hipchat.data.network;

import com.rendecano.hipchat.data.model.Link;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Ren Decano.
 */
public class NetworkData {

    private static final Pattern TITLE_TAG =
            Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final String CONTENT_TYPE = "Content-Type";

    @Inject
    public NetworkData() {
    }

    public Observable<List<Link>> getTitles(final List<Link> links) {

        return Observable.create(new Observable.OnSubscribe<List<Link>>() {
            @Override
            public void call(Subscriber<? super List<Link>> subscriber) {

                // Get all titles for each url
                try {
                    for (Link link : links) {
                        link.setTitle(getPageTitle(link.getUrl()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                subscriber.onNext(links);
                subscriber.onCompleted();
            }
        });
    }

    private String getPageTitle(String url) throws Exception {

        String title = "";

        URL u = new URL(url);
        URLConnection urlConnection = u.openConnection();

        InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        String inputString;
        while ((inputString = bufferedReader.readLine()) != null) {
            builder.append(inputString);
        }
        stream.close();

        String contentType = urlConnection.getHeaderField(CONTENT_TYPE);

        // Check if content type is text/html
        if (contentType.contains("text/html")) {
            Matcher m = TITLE_TAG.matcher(builder.toString());
            while (m.find()) {
                title = m.group(1);
                break;
            }
        }

        return title;
    }
}
