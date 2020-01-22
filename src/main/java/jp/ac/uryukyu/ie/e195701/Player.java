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

class you extends Player {

    you (String name) {
        super(name);
        hands = new ArrayList<>();
    }

    @Override
    void showStatus() {
        System.out.println(name);
        for (Card i : hands) { System.out.print(i.soot() + " "); }
        System.out.print("\n");
        for (Card i : hands) { System.out.print(i.num() + " "); }
        System.out.print("\n");
    }

    @Override
    int decideTable (int maxHand) {
        String[] tableList = {"シングル", "ダブル", "トリプル", "革命"};
        List<String> available = new ArrayList<>(Arrays.asList(tableList).subList(0, maxHand));
        System.out.println(available);
        return input(available.size());
    }

    @Override
    List<Card> choiceHand (List<List<Card>> choices) {
        StringBuilder strSoot = new StringBuilder();
        StringBuilder strNum = new StringBuilder();
        for (List<Card> i : choices) {
            if (i == null) {
                strSoot.append("パス");
            }else for (Card j : i) {
                strSoot.append(j.soot());
                strNum.append(j.num());
            }
            strSoot.append("  ");
            strNum.append("  ");
        }
        System.out.println(strSoot);
        System.out.println(strNum);
        return choices.get(input(choices.size()));
    }

    private int input(int num) {
        ArrayList<Integer> choicesNum = new ArrayList<>();
        for (int i=1; i<=num; i++) {
            choicesNum.add(i); }
        System.out.println("コード一覧 ▶︎ " + choicesNum);
        System.out.println("番号を入力して下さい。");
        Scanner sc = new Scanner(System.in);
        String retry = "正しい番号を入力してください。";
        while (true) {
            try {
                int selected = Integer.parseInt(sc.next());
                if (selected > 0 && selected < num) {
                    return selected;
                }else {
                    System.out.println(retry);
                }
            }catch (NumberFormatException e) {
                System.out.println(retry);
            }
        }
    }
}

class com extends Player {
    Random rnd;

    com (String name) {
        super(name);
        hands = new ArrayList<>();
        rnd = new Random();

    }

    @Override
    void showStatus() {
        System.out.println(name + "：" + String.format("% 2d", hands.size()) + " 枚");
    }

    @Override
    int decideTable(int maxHand) {
        return rnd.nextInt(maxHand);
    }

    @Override
    List<Card> choiceHand(List<List<Card>> choices) {
        return choices.get(rnd.nextInt(choices.size())-1);
    }
}


