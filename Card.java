package PokerOdds;

//Cards are represented as ints 0-51
//Cards go from 2-A in alphabetically suit order
//Ex: 3 of clubs is 1 and 7 of hearts is 31
public class Card {

    private int myValue;
    private int mySuit;
    private int me;

    public Card(int card) {
        this.myValue = card % 13;
        this.mySuit = card / 13;
        this.me = card;
        if (card < 0 || card > 51) {
            this.myValue = -1;
            this.mySuit = -1;
        }
    }

    public int getValue() {
        return this.myValue;
    }

    public int getSuit() {
        return this.mySuit;
    }

    public int toInt()  {
        return this.me;
    }
}
