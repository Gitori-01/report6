package jp.ac.uryukyu.ie.e195701;

import java.util.ArrayList;
import java.util.Collections;

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

        while (players.size() != 0) {
            for (Player i : players) {
                for (Player j : players)
                    j.showStatus();
                dealer.turn(i);
                dealer.checkResetTable();
                if (i.hands.size() == 0) {
                    result.add(i);
                    players.remove(i);
                }
            }

        }
        System.out.println("Result ▶︎▶︎▶︎");
        for (int i=0; i<result.size(); i++) {
            System.out.println( i+1 + "位" + result.get(i).name);
        }

    }
}
