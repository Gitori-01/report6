package jp.ac.uryukyu.ie.e195701;

import java.util.Comparator;

public class CompCard implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        if (o1.getNum() < o2.getNum())
            return -1;
        else if (o1.getNum() > o2.getNum())
            return 1;
        else if (o1.getSoot() < o2.getSoot())
            return -1;
        else if (o1.getSoot() > o2.getSoot())
            return 1;
        else
            return 0;
    }
}
