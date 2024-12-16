package System;

import Enums.NewsTopic;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

public class News implements Serializable {

    private String author;
    private Date date;
    private String title;
    private String content;
    private NewsTopic newsTopic;
    private Vector<Comment> comment;


//    constructors
    public News() {

    }

    public News(String author, String title, String content, NewsTopic newsTopic) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = new Date();
        this.newsTopic = newsTopic;

        this.comment = new Vector<>();
    }



//    accessors

    public NewsTopic getNewsTopic() {
        return newsTopic;
    }

    public Vector<Comment> getComment() {
        return comment;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return newsTopic == news.newsTopic && Objects.equals(comment, news.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsTopic, comment);
    }

    @Override
    public String toString() {
        return "News{" +
                "newsTopic=" + newsTopic +
                ", title='" + title +
                ", content='" + content +
                ", author='" + author +
                ", date=" + date +
                '}';
    }
}

