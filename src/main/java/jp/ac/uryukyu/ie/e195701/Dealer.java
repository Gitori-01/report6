package jp.ac.uryukyu.ie.e195701;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 各プレイヤーのターン毎の振る舞いを定義するクラス。
 * プレイヤーと場（Table）との間のカードも流れも管理する。
 * Created by Gitori-01
 */
class Dealer {
    /**全体のパス回数を管理する*/
    private int[] pass;
    /**カードを出す場*/
    private Table table;

    /**
     * コンストラクタ。山札を生成し、引数として渡された各プレイヤーにカードを配る。
     * @param players プレイヤーが格納されたリスト。
     */
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

    /**
     * 毎ターンの「捨てるカードを選んで捨てる部分」を担うメソッド。
     * パスの判定と進行への反映も同時に行う。
     * @param player 順番が回ってきたプレイヤー
     */
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

    /**
     * パスが一周続いた時に場を流すメソッド。
     */
    void checkResetTable() {
        if (pass[0] >= pass[1]) {
            table = new Table();
            pass[0] = 0;
            System.out.println("\n場が流れました。");
        }
    }

    /**
     * 誰かが上がった時にMainから呼び出され、場を流す等の処理を行う。
     * @param player 上がったプレイヤー
     */
    void finish(Player player) {
        pass[0] = 0;
        pass[1] --;
        table = new Table();
        System.out.println(player.name + " さんが上がりました。\n");
    }

    /**
     * 毎ターンごとに2秒ほど時間を置くメソッド。誰が何を出したか分かりやすくするため。
     */
    void waitMoment() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


