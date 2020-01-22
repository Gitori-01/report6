package jp.ac.uryukyu.ie.e195701;

import java.util.*;
import java.util.stream.Collectors;

class Table {
    List<Card> layout;
    List<List<Card>> choices;
    Map<Integer, List<Card>> hashHand;
    int tableNum;

    void initHash (Player player) {
        int currentNum = layout.get(0).getNum();
        hashHand = player.hands.stream()
                .filter(i -> i.getNum() > currentNum)
                .collect(Collectors.groupingBy(Card::getNum));
    }

    void prepareTable (Player player) {
        int hashmax = hashHand.values().stream()
                .mapToInt(List::size)
                .max().orElseThrow(NoSuchElementException::new);
        if (hashHand.containsKey(13)) {
            hashmax = hashmax + hashHand.get(13).size();
            if (hashmax > 4) hashmax = 4;
        }
        tableNum = player.decideTable(hashmax);
    }

    void initChoices () {
        choices.clear();
        for (int i: hashHand.keySet()) {
            if (hashHand.get(i).size() >= tableNum) {
                choices.add(hashHand.get(i));

            }else if (i == 13) {
                for (int j: hashHand.keySet()) {
                    if (hashHand.get(j).size() >= tableNum - hashHand.get(i).size() && j != i) {
                        List<Card> jokers = new ArrayList<>();
                        for (int k=0; k < hashHand.get(i).size(); k++) {
                            jokers.add(hashHand.get(i).get(0));
                            choices.add(jokers);
                        }
                        break; }
                }
            }
        }
        choices.add(null);
    }

    private void initChoices (int num) {
        choices.clear();
        for (int i : hashHand.keySet()) {
            if (hashHand.get(i).size() >= num) {
                choices.add(hashHand.get(i));
            }
        }
    }

    boolean isPass () {
        return choices.size() == 0;
    }

    boolean discard (Player player) {
        List<Card> selected = new ArrayList<>(player.choiceHand(choices));
        if (selected.get(0) == null) {
            return true;
        } else if (selected.get(0).getSoot() == 5) {
            initChoices(tableNum - selected.size());
            List<Card> result = selectCard(selected, tableNum - selected.size(), player);
            layout.addAll(result);
            layout.addAll(selected);
        } else {
            layout = selectCard(selected, tableNum, player);
        }
        return false;
    }

    private List<Card> selectCard(List<Card> cards, int num, Player player) {
        if (cards.size() == num)
            return cards;
        else {
            List<List<Card>> cardList = new ArrayList<>();
            List<Card> results = new ArrayList<>();
            for (Card i: cards){
                cardList.add(new ArrayList<>(Collections.singletonList(i)));
            }
            System.out.println("選んだ手札から" + num + "枚選択して下さい。");
            for (int i=0; i<num; i++){
                System.out.println(i + "枚目");
                List<Card> result = player.choiceHand(cardList);
                results.addAll(result);
                cardList.remove(result);
            }
            return results;
        }


    }
}