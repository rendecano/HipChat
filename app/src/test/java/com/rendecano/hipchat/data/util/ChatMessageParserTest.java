package com.rendecano.hipchat.data.util;

import com.google.gson.Gson;
import com.rendecano.hipchat.data.model.Link;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;

/**
 * Created by Ren Decano.
 */
public class ChatMessageParserTest extends TestCase {

    ChatMessageParser instance;
    Gson gson;

    public void setUp() throws Exception {
        super.setUp();
        gson = new Gson();
        instance = new ChatMessageParser(gson);
    }

    public void testMentionRegex() throws Exception {

        String mentionString = "@chris you around?";

        List<String> items = new ArrayList<>();
        items.add("chris");

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        instance.extractMentions(mentionString).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValues(items);
        testSubscriber.assertUnsubscribed();
    }

    public void testMultipleValidMentionRegex() throws Exception {

        String mentionString = "@chris @ren @frappe you around?";

        List<String> items = new ArrayList<>();
        items.add("chris");
        items.add("ren");
        items.add("frappe");

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        instance.extractMentions(mentionString).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValues(items);
        testSubscriber.assertUnsubscribed();
    }

    public void testMultipleInValidMentionRegex() throws Exception {

        String mentionString = "@chris @ren@michael.d @frappe you around?";

        List<String> items = new ArrayList<>();
        items.add("chris");
        items.add("frappe");

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        instance.extractMentions(mentionString).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValues(items);
        testSubscriber.assertUnsubscribed();
    }

    public void testNonWordCharacterMentionRegex() throws Exception {

        String mentionString = "@ren.decano you around?";

        List<String> items = new ArrayList<>();
        items.add("ren");

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        instance.extractMentions(mentionString).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValues(items);
        testSubscriber.assertUnsubscribed();
    }

    public void testEmoticonRegex() throws Exception {

        String emoticonString = "(coffee)";

        List<String> items = new ArrayList<>();
        items.add("coffee");

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        instance.extractEmoticons(emoticonString).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValues(items);
        testSubscriber.assertUnsubscribed();
    }

    public void testUrlRegex() throws Exception {

        String urlString = "@chris Olympics are starting soon; http://www.nbcolympics.com https://www.google.com";

        Link linkOne = new Link();
        linkOne.setUrl("http://www.nbcolympics.com");

        Link linkTwo = new Link();
        linkTwo.setUrl("https://www.google.com");

        List<Link> items = new ArrayList<>();
        items.add(linkOne);
        items.add(linkTwo);

        TestSubscriber<List<Link>> testSubscriber = new TestSubscriber<>();
        instance.extractUrl(urlString).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValues(items);
        testSubscriber.assertUnsubscribed();
    }
}