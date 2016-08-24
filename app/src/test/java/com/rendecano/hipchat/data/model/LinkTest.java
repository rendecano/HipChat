package com.rendecano.hipchat.data.model;

import junit.framework.TestCase;

/**
 * Created by Ren Decano.
 */
public class LinkTest extends TestCase {

    Link instance;

    public void setUp() throws Exception {
        super.setUp();

        instance = new Link();
    }

    public void testInit() throws Exception {

        assertNotNull(instance.getTitle());
        assertNotNull(instance.getUrl());
    }

    public void testSetGetTitleValue() throws Exception {

        // Add value
        String title = "NBC Olympics | 2014 NBC Olympics in Sochi Russia";
        instance.setTitle(title);

        assertNotNull(instance.getTitle());
        assertTrue(instance.getTitle().equals("NBC Olympics | 2014 NBC Olympics in Sochi Russia"));

    }

    public void testSetGetUrlValue() throws Exception {

        // Set value
        String url = "http://www.nbcolympics.com";
        instance.setUrl(url);

        assertNotNull(instance.getUrl());
        assertTrue(instance.getUrl().equals("http://www.nbcolympics.com"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }
}