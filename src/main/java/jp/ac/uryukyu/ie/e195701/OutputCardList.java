package jp.ac.uryukyu.ie.e195701;

import java.util.List;

/**
 * 現在場に出てるカード(Table.layout)やyouの手札(you.hands)、選択肢(Table.choices)を標準出力する際のフォーマットを定義するクラス。
 * 渡されたリストをリストの形態に応じて指定のフォーマットに落とし込み出力する。
 * Created by Gitori-01
 */
class OutputCardList {

    /**
     * コンストラクタ
     * @param list Table.layoutやYou.list等はここに代入。
     * @param listList Table.choicesなどのカードが格納されたリストを選択肢として格納するリストはここに代入。
     */
    OutputCardList(List<Card> list, List<List<Card>> listList) {

        if (list == null) {
            StringBuilder strSoot = new StringBuilder();
            StringBuilder strNum = new StringBuilder();
            for (List<Card> i : listList) {
                if (i == null) {
                    strSoot.append("パス");
                }else for (Card j : i) {
                    strSoot.append(j.soot()).append(" ");
                    strNum.append(j.num()).append(" ");
                }
                strSoot.append("   ");
                strNum.append("   ");
            }
            System.out.println(strSoot);
            System.out.println(strNum);

        }else if (listList == null) {
            for (Card i : list) { System.out.print(i.soot() + " "); }
            System.out.print("\n");
            for (Card i : list) { System.out.print(i.num() + " "); }
            System.out.print("\n");
        }
    }
}

