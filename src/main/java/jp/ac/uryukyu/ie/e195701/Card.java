package jp.ac.uryukyu.ie.e195701;

public class Card {
    private int soot;
    private int num;

    public Card(int soot, int num){
        this.soot = soot;
        this.num = num;
    }

    int getSoot() { return soot; }

    int getNum() { return num; }

    String soot() {
        String[] soots = {"♠️", "❤️", "♦️", "♣️", "🤡"};
        return soots[soot];
    }

    String num() {
        String[] nums = {"4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10", "J ", "Q ", "K ", "A ", "2 ", "  "};
        return nums[num];
    }
}