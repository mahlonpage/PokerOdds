package PokerOdds;

public enum RankType {
    STRAIGHT_FLUSH, QUADS, FULL_HOUSE, FLUSH, STRAIGHT, TRIPS, TWO_PAIR, PAIR, HIGH_CARD;

    public String toString()    {
        switch (this) {
            case STRAIGHT_FLUSH:
                return "Straight Flush";
            case QUADS:
                return "Four of a Kind";
            case FULL_HOUSE:
                return "Full House";
            case FLUSH:
                return "Flush";
            case STRAIGHT:
                return "Straight";
            case TRIPS:
                return "Three of a Kind";
            case TWO_PAIR:
                return "Two Pair";
            case PAIR:
                return "Pair";
            default:
                return "High Card";
        }
    }

    //TODO REPLACE WITH A COMPARE
    public int toInt()    {
        switch (this) {
            case STRAIGHT_FLUSH:
                return 8;
            case QUADS:
                return 7;
            case FULL_HOUSE:
                return 6;
            case FLUSH:
                return 5;
            case STRAIGHT:
                return 4;
            case TRIPS:
                return 3;
            case TWO_PAIR:
                return 2;
            case PAIR:
                return 1;
            default:
                return 0;
        }
    }
}
