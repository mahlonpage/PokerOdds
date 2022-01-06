package PokerOdds;

public class Card {

    private Value myValue;
    private Suit mySuit;

    public Card(Value value, Suit suit) {
        this.myValue = value;
        this.mySuit = suit;
    }

    public Value getValue() {
        return this.myValue;
    }

    public Suit getSuit() {
        return this.mySuit;
    }
}
