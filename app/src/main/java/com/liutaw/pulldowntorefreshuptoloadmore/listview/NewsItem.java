package com.liutaw.pulldowntorefreshuptoloadmore.listview;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String content;
    private String releasetime;
    private String title;
    private String source;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
