package System;

import Enums.NewsTopic;

import java.util.Objects;
import java.util.Vector;

public class News {
    private NewsTopic newsTopic;
    private Vector<Comment> comment;

    public News() {
    }

    public News(NewsTopic newsTopic) {
        this.newsTopic = newsTopic;
    }

    public News(NewsTopic newsTopic, Vector<Comment> comment) {
        this.newsTopic = newsTopic;
        this.comment = comment;
    }

    public NewsTopic getNewsTopic() {
        return newsTopic;
    }

    public void setNewsTopic(NewsTopic newsTopic) {
        this.newsTopic = newsTopic;
    }

    public Vector<Comment> getComment() {
        return comment;
    }

    public void setComment(Vector<Comment> comment) {
        this.comment = comment;
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
                ", comment=" + comment +
                '}';
    }
}

