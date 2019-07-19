package com.example.khaled.deployarticle;

/**
 * Created by khaled on 7/16/2019.
 */

public class ArticleModel
{
    private String title , username , content;

    public ArticleModel(String title, String username, String content) {
        this.title = title;
        this.username = username;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
