package Comparators;

import System.News;

import java.io.Serializable;
import java.util.Comparator;

public class NewsComparator implements Comparator<News>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(News news1, News news2) {
        return news2.getDate().compareTo(news1.getDate());
    }
}