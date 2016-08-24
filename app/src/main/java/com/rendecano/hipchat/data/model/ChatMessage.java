package com.rendecano.hipchat.data.model;

import java.util.List;

/**
 * Created by Ren Decano.
 */

public class ChatMessage {

    private List<String> mentions;
    private List<String> emoticons;
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public List<String> getEmoticons() {
        return emoticons;
    }

    public void setEmoticons(List<String> emoticons) {
        this.emoticons = emoticons;
    }
}
