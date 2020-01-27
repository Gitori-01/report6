package jp.ac.uryukyu.ie.e195701;

/**
 * 1枚のトランプカードをオブジェクト化したクラス。
 * Created by Gitori-01
 */
public class Card {
    /**数字*/
    private int soot;
    /**スート*/
    private int num;

    /**
     * コンストラクタ。
     * @param soot スート
     * @param num 数字
     */
    public Card(int soot, int num){
        this.soot = soot;
        this.num = num;
    }

    int getSoot() { return soot; }

    int getNum() { return num; }

    /**
     * sootを標準出力する際に使用するメソッド。
     * 用意された出力用のリストから、sootに対応する文字が出力される。
     * @return String
     */
    String soot() {
        String[] soots = {"♠️", "❤️", "♦️", "♣️", "🤡"};
        return soots[soot];
    }

    /**
     * numを標準出力する際に使用するメソッド。
     * @return String
     */
    String num() {
        String[] nums = {"4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10", "J ", "Q ", "K ", "A ", "2 ", "  "};
        return nums[num];
    }
}