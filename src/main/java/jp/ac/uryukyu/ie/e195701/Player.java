package jp.ac.uryukyu.ie.e195701;

import java.util.*;

/**
 * you,comの親クラス。
 * Player共通の機能（メソッド）を定義する。
 * Created by Gitori-01
 */
abstract class Player {
    /**名前*/
    String name;
    /**手札*/
    ArrayList<Card> hands;
    /**上がったらtrue*/
    boolean finished;

    /**
     * Playerのコンストラクタ。
     * handsは手札を格納するリスト。
     * finishedはそのPlayerが終了しているか否かを表す。
     * @param name Playerの名前
     */
    Player (String name) {
        this.name = name;
        hands = new ArrayList<>();
        finished = false;
    }

    /**
     * 現在の手札の状態を表示するメソッド。
     */
    abstract void showStatus();

    /**
     * 親になった際同時に出せるカードの枚数（場の状態）を決定するメソッド。
     * @param maxHand 自分の手札を元にTableが示した最大可能同時破棄枚数。
     * @return int
     */
    abstract int decideTable (int maxHand);

    /**
     * 場に出す手（カード）を選択するメソッド。
     * また、場の状況に合わせて、選んだカード群から出すカードを選択するメソッド。
     * @param choices 自分の手札を元にTableが示した現在出せる手のリスト。もしくは、選んだカード群によって構成されたリスト。
     * @return List(Card)
     */
    abstract List<Card> choiceHand (List<List<Card>> choices);

    /**
     * 選択した手（カード）を場に捨てるメソッド。
     * @param layout 選択した手
     */
    void discard (List<Card> layout) {
        for (Card i: layout)
            hands.remove(i);
    }
}

/**
 * 実行者が実際にPlayerとして操作するPlayerの拡張クラス。
 * Created by Gitori-01
 */
class you extends Player {

    /**
     * コンストラクタ。
     * @param name youの名前。
     */
    you (String name) {
        super(name);
        hands = new ArrayList<>();
    }

    /**
     * 手札の内訳を開示する。
     */
    @Override
    void showStatus() {
        if (!finished) {
            System.out.println(name);
            new OutputCardList(hands, null);
        }
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
        new OutputCardList (null, choices);
        return choices.get(input(choices.size()));
    }

    /**
     * 選択肢に応じて入力を要求し、入力された内容を返すメソッド。
     * @param num 選択肢の数
     * @return int
     */
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
                int selected = Integer.parseInt(sc.next()) -1;
                if (selected >= 0 && selected < num) {
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

/**
 * いわゆるcomとしての振る舞いを行うPlayerの拡張クラス。
 * 各選択処理は全てRandom.nextInt()で処理される。
 * Created by Gitori-01
 */
class com extends Player {
    private Random rnd;

    /**
     * コンストラクタ。
     * ここでRandomのインスタンスも用意しておく。
     * @param name comの名前。
     */
    com (String name) {
        super(name);
        hands = new ArrayList<>();
        rnd = new Random();

    }

    /**
     * 実行者からすれば他のプレイヤーなので枚数のみ表示する。
     */
    @Override
    void showStatus() {
        if (!finished)
            System.out.println(name + "：" + String.format("% 2d", hands.size()) + " 枚");
    }

    @Override
    int decideTable(int maxHand) {
        return rnd.nextInt(maxHand);
    }

    @Override
    List<Card> choiceHand(List<List<Card>> choices) {
        return choices.get(rnd.nextInt(choices.size()));
    }
}


