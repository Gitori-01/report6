package jp.ac.uryukyu.ie.e195701;

import java.util.List;

public class OutputCardList {

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

