package PokerOdds;

public enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    public Suit cycle() {
        switch (this)   {
            case CLUBS:
                return DIAMONDS;
            case DIAMONDS:
                return HEARTS;
            case HEARTS:
                return SPADES;
            default:
                return CLUBS;
        }
    }
}
