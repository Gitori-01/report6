package jp.ac.uryukyu.ie.e195701;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Gitori-01
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> result = new ArrayList<>();
        players.add(new you("tori"));
        players.add(new com("hoge"));
        players.add(new com("fuga"));
        players.add(new com("higa"));
        Collections.shuffle(players);

        Dealer dealer = new Dealer(players);

        while (players.size() != result.size()) {
            for (Player i : players) {
                if (!i.finished) {
                    dealer.checkResetTable();
                    for (Player j : players)
                        j.showStatus();
                    dealer.turn(i);
                    dealer.waitMoment();
                }
                if (i.hands.size() == 0) {
                    i.finished = true;
                    if (!result.contains(i)) {
                        result.add(i);
                        dealer.finish(i);
                    }
                }

            }

        }
        System.out.println("Result ▶︎▶︎▶︎");
        for (int i=0; i<result.size(); i++) {
            System.out.println( i+1 + "位" + result.get(i).name);
        }

    }
}
