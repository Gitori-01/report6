package jp.ac.uryukyu.ie.e195701;

import java.util.Comparator;

/**
 * Dealerのコンストラクタにて手札（Player.hands）を並べる際の条件を定義するComparatorの拡張クラス。
 * Updated by Gitori-01
 */
public class CompCard implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        if (o1.getNum() < o2.getNum())
            return -1;
        else if (o1.getNum() > o2.getNum())
            return 1;
        else return Integer.compare(o1.getSoot(), o2.getSoot());
    }
}
