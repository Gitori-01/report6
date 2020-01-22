package jp.ac.uryukyu.ie.e195701;

public class Card {
    private int soot;
    private int num;

    public Card(int soot, int num){
        this.soot = soot;
        this.num = num;
    }

    public int getSoot() { return soot; }

    public void setSoot(int soot) { this.soot = soot; }

    public int getNum() { return num; }

    public void setNum(int num) { this.num = num; }

    public String soot() {
        String[] soots = {"♠️", "❤️", "♦️", "♣️", "🤡"};
        return soots[soot];
    }

    public String num() {
        String[] nums = {"4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10", "J ", "Q ", "K ", "A ", "2 ", "  "};
        return nums[num];
    }
}