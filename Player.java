package PokerOdds;

import java.util.ArrayList;

public class Player {

    public Player(ArrayList<Card> hand) {
        handValueCalculator calc = new handValueCalculator(hand);
    }
}
