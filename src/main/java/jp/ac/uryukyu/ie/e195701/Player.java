package jp.ac.uryukyu.ie.e195701;

import java.util.*;

abstract class Player {
    String name;
    ArrayList<Card> hands;

    Player (String name) {
        this.name = name;
        hands = new ArrayList<>();
    }

    abstract void showStatus();
    abstract int decideTable (int maxHand);
    abstract List<Card> choiceHand (List<List<Card>> choices);
}

