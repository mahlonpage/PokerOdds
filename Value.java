package PokerOdds;

public enum Value {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    public Value cycle() {
        switch (this) {
            case TWO:
                return THREE;
            case THREE:
                return FOUR;
            case FOUR:
                return FIVE;
            case FIVE:
                return SIX;
            case SIX:
                return SEVEN;
            case SEVEN:
                return EIGHT;
            case EIGHT:
                return NINE;
            case NINE:
                return TEN;
            case TEN:
                return JACK;
            case JACK:
                return QUEEN;
            case QUEEN:
                return KING;
            case KING:
                return ACE;
            default:
                return TWO;
        }
    }

    public int toInt() {
        switch (this) {
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case JACK:
                return 11;
            case QUEEN:
                return 12;
            case KING:
                return 13;
            default:
                return 14;
        }
    }
}
