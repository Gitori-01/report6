package jp.ac.uryukyu.ie.e195701;

import java.util.*;
import java.util.stream.Collectors;

class Table {
    private ArrayList<Card> layout;
    private List<List<Card>> choices;
    private Map<Integer, List<Card>> hashHand;
    private int tableNum;

    Table () {
        layout = new ArrayList<>();
        choices = new ArrayList<>();
        tableNum = 0;
        hashHand = new HashMap<>();
    }

    int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public ArrayList<Card> getLayout() {
        return layout;
    }

    void initHash (Player player) {
        int currentNum;
        try {
            currentNum = layout.get(0).getNum();
        }catch (IndexOutOfBoundsException e) {
            currentNum = -1;
        }int finalCurrentNum = currentNum;
        hashHand.clear();
        hashHand = player.hands.stream()
                .filter(i -> i.getNum() > finalCurrentNum)
                .collect(Collectors.groupingBy(Card::getNum));
    }

    void prepareTable (Player player) {
        int hashmax = hashHand.values().stream()
                .mapToInt(List::size)
                .max().orElseThrow(NoSuchElementException::new);
        if (hashHand.containsKey(12)) {
            hashmax = hashmax + hashHand.get(12).size();
            if (hashmax > 4) hashmax = 4;
        }
        tableNum = player.decideTable(hashmax) +1;
    }

    void initChoices () {
        choices.clear();
        for (int i: hashHand.keySet()) {
            if (hashHand.get(i).size() >= tableNum) {
                choices.add(hashHand.get(i));
            }
            if (i == 12) {
                for (int j : hashHand.keySet()) {
                    if (hashHand.get(j).size() >= tableNum - hashHand.get(i).size() && j != i) {
                        List<Card> jokers = new ArrayList<>();
                        int jokerNum = hashHand.get(i).size();
                        if (jokerNum >= tableNum)
                            jokerNum --;
                        for (int k=0; k<jokerNum; k++) {
                            jokers.add(hashHand.get(i).get(0));
                            choices.add(jokers);
                        }
                        break;
                    }
                }
            }

        }
        if (layout.size() != 0)
            choices.add(null);
    }

    private void initChoices (int num) {
        choices.clear();
        for (int i : hashHand.keySet()) {
            if (hashHand.get(i).size() >= num && i != 12) {
                choices.add(hashHand.get(i));
            }
        }
    }

    boolean isPass () {
        return choices.get(0) == null;
    }

    boolean SelectDiscard(Player player) {
        try {
            List<Card> selected = new ArrayList<>(player.choiceHand(choices));
            if (selected.get(0).getSoot() == 4 && selected.size() < tableNum) {
                layout.clear();
                layout.addAll(selected);
                int remainCardNum = tableNum - selected.size();
                initChoices(remainCardNum);
                if (player instanceof you)
                    System.out.println("🤡 に代入するカードを選んで下さい。");
                selected = new ArrayList<>(player.choiceHand(choices));
                List<Card> result = selectCard(selected, remainCardNum, player);
                layout.addAll(0, result);
            } else {
                layout.clear();
                layout = (ArrayList<Card>) selectCard(selected, tableNum, player);
            }
            player.discard(layout);
            System.out.println(player.name + " ▶︎ ");
            new OutputCardList(layout, null);
            return false;

        }catch (NullPointerException e) {return true;}
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
            if (player instanceof you) System.out.println("選んだ手札から" + num + "枚選択して下さい。");
            for (int i=0; i<num; i++){
                if (player instanceof you) System.out.println(i+1 + "枚目");
                List<Card> result = player.choiceHand(cardList);
                results.addAll(result);
                cardList.remove(result);
            }
            return results;
        }


    }
}
