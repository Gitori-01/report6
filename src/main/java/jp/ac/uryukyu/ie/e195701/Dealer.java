package jp.ac.uryukyu.ie.e195701;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dealer {
    int[] pass;
    Table table;
    List<Card> deck;

    Dealer(ArrayList<Player> players) {
        pass = new int[]{0, players.size() - 1};
        table = new Table();
        deck = new ArrayList<>();

        for (int i=0 ; i<4; i++){
            for (int j=0 ; j<12 ; j++){
                deck.add(new Card(i, j)); }
        }
        for (int i=0 ; i>2 ; i++){
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

    public void turn (Player player) {
        table.initHash(player);
        if (table.tableNum == 0) {
            table.prepareTable(player);
        }
        table.initChoices();
        if (table.isPass())
            pass[0] ++;
        else if (table.SelectDiscard(player))
            pass[0] ++;
        else {
            System.out.println(player.name + " ▶︎ ");
            new OutputCardList(table.layout, null);
        }
        System.out.println();
    }

    void checkResetTable() {
        if (pass[0] == pass[1]) {
            table = new Table();
            pass[0] = 0;
        }
    }
}


