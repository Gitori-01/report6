package jp.ac.uryukyu.ie.e195701;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Dealer {
    private int[] pass;
    private Table table;

    Dealer(ArrayList<Player> players) {
        pass = new int[]{0, players.size() - 1};
        table = new Table();
        List<Card> deck = new ArrayList<>();

        for (int i=0 ; i<4; i++){
            for (int j=0 ; j<12 ; j++){
                deck.add(new Card(i, j)); }
        }
        for (int i=0 ; i<2 ; i++){
            deck.add(new Card(4, 12));
        }
        Collections.shuffle(deck);
        while (true){
            try {
                for (Player i : players){
                    i.hands.add(deck.remove(0));
                }
            }catch (IndexOutOfBoundsException none){
                break;
            }
        }
        for (Player i: players) {
            i.hands.sort(new CompCard());
        }
    }

    void turn(Player player) {
        String strPass = player.name + " さんがパスしました。";
        System.out.println(player.name + " さんの番です。");
        table.initHash(player);
        if (table.getTableNum() == 0) {
            table.prepareTable(player);
        }
        table.initChoices();
        if (table.isPass()) {
            pass[0] ++;
            System.out.println(strPass);
        }
        else if (table.SelectDiscard(player)) {
            pass[0] ++;
            System.out.println(strPass);
        }
        else
            pass[0] = 0;
    }

    void checkResetTable() {
        if (pass[0] == pass[1]) {
            table = new Table();
            pass[0] = 0;
            System.out.println("\n場が流れました。");
        }
    }

    void finish(Player player) {
        pass[0] = 0;
        pass[1] --;
        table = new Table();
        System.out.println(player.name + " さんが上がりました。\n");
    }

    void waitMoment() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


