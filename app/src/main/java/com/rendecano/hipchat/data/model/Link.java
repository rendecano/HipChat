package com.rendecano.hipchat.data.model;

/**
 * Created by Ren Decano.
 */
public class Link {

    private String title = "";
    private String url = "";

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Link) {
            Link that = (Link) o;
            return (this.title.equals(that.title))
                    && (this.url.equals(that.url));
        }
        return false;
    }
}
