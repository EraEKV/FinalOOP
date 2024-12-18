package Comparators;

import System.Log;

import java.io.Serializable;
import java.util.Comparator;

public class LogComparator implements Comparator<Log>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Log log1, Log log2) {
        return log2.getDate().compareTo(log1.getDate());
    }
}