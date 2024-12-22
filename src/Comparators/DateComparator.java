package Comparators;

import java.util.Comparator;
import java.io.Serializable;

import System.News;
import System.Notification;
import System.Log;

public class DateComparator<T> implements Comparator<T>, Serializable {
    @Override
    public int compare(T o1, T o2) {
        if (o1 instanceof News && o2 instanceof News) {
            News news1 = (News) o1;
            News news2 = (News) o2;
            return news2.getDate().compareTo(news1.getDate());
        } else if (o1 instanceof Notification && o2 instanceof Notification) {
            Notification notification1 = (Notification) o1;
            Notification notification2 = (Notification) o2;
            return notification2.getDate().compareTo(notification1.getDate());
        } else if (o1 instanceof Log && o2 instanceof Log) {
            Log log1 = (Log) o1;
            Log log2 = (Log) o2;
            return log2.getDate().compareTo(log1.getDate());
        }
        return 0;
    }
}

