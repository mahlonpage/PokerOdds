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

    public String getValueString()  {
        switch (this.myValue)   {
            case 0:
                return "2";
            case 1:
                return "3";
            case 2:
                return "4";
            case 3:
                return "5";
            case 4:
                return "6";
            case 5:
                return "7";
            case 6:
                return "8";
            case 7:
                return "9";
            case 8:
                return "10";
            case 9:
                return "J";
            case 10:
                return "Q";
            case 11:
                return "K";
            default:
                return "A";
        }
    }

    public String getSuitString()   {
        switch (this.mySuit)    {
            case 0:
                return "C";
            case 1:
                return "D";
            case 2:
                return "H";
            default:
                return "S";
        }
    }

    public int toInt()  {
        return this.me;
    }
}
