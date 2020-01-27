package jp.ac.uryukyu.ie.e195701;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 現在の場の状況、ルールを形成するクラス。
 * 主にPlayerの手札を元に出せる手を選定したり場のルールを決定したりする。
 * 現在場に出ているカードもここで管理する。
 *Created by Gitori-01
 */
class Table {
    /**現在の場のカード*/
    private ArrayList<Card> layout;
    /**選択肢の格納場所兼フォーマット*/
    private List<List<Card>> choices;
    /**Playerの手札を数字でグルーピングしたマップ*/
    private Map<Integer, List<Card>> hashHand;
    /**出すカードの枚数*/
    private int tableNum;

    /**
     * コンストラクタ。
     * 場が流れた際に呼び出されることで場を初期化する。
     */
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

    public List<List<Card>> getChoices() {
        return choices;
    }

    /**
     * 引数に渡されたプレイヤーの手札(hands)を数字でグルーピングしてMap化するメソッド。
     * この際場に出ているカード(layout)の数字より弱いカードはfilterで弾かれる。
     * @param player 順番が回ってきたプレイヤー
     */
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

    /**
     * 場が流れた直後の際、場に出すカードの枚数を決定するメソッド。
     * hashHandの値のサイズを取り出し、その最大値を最大可能同時破棄枚数としてPlayerのdecideTableに渡す。
     * @param player 順番が回ってきたプレイヤー
     */
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

    /**
     * 実際に場に出す手の選択肢(choices)を選定するメソッド。
     * hashHandから値の枚数が現在の場の破棄枚数(tableNum)より大きい要素を選定し、その値をchoicesに格納する。
     * またJOKERをワイルドカードとして使えるかどうかも判定し、使えれば格納する。
     */
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
                            choices.add(new ArrayList<>(jokers));
                        }
                        break;
                    }
                }
            }

        }
        if (layout.size() != 0)
            choices.add(null);
    }

    /**
     * selectDiscardにてワイルドカードとしてのJOKERが選ばれた際、一緒に出すカードの候補を選定するメソッド。
     * hashHand中の指定枚数(num)より多い値をchoicesに格納する。
     * @param num tableNumから選択したジョーカーの枚数を引いた数字
     */
    private void initChoices (int num) {
        choices.clear();
        for (int i : hashHand.keySet()) {
            if (hashHand.get(i).size() >= num && i != 12) {
                choices.add(hashHand.get(i));
            }
        }
    }

    /**
     * 出せるカードがあるかどうかを返すメソッド。
     * もしなかった場合選択肢(choices)にはパス(null)しか入っていないはずなので、それを判定する。
     * @return boolean
     */
    boolean isPass () {
        return choices.get(0) == null;
    }

    /**
     * initChoicesにて作成した選択肢をplayerに渡し、出す手を選ばせる。
     * 帰ってきた結果に応じて二重、三重に選択肢の組み直しと選択を繰り返し、最終的に出す手(layout)を決定する。
     * また同時にパスを選んだかどうかも判定し、その結果を返す。
     * @param player 順番が回ってきたプレイヤー
     * @return boolean
     */
    boolean SelectDiscard(Player player) {
        try {
            List<Card> selected = new ArrayList<>(player.choiceHand(choices));
            if (selected.get(0).getNum() == 12 && selected.size() < tableNum) {
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

    /**
     * もしselectDiscardで選択した手の枚数が指定より多かった場合、その指定枚数分出すカードを選択するメソッド。
     * @param cards 選択した手(リスト)
     * @param num 指定枚数
     * @param player 順番が回ってきたプレイヤー(ここでも選択はプレイヤーが行う)
     * @return List(Card)
     */
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
