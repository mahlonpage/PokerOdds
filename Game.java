package PokerOdds;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

    public Game()   {
        ArrayList<Card> deck = new ArrayList();
        this.fillDeck(deck);
        Collections.shuffle(deck);
        //adds Players to the Poker game and deals two cards to each.
        for (int i = 0; i < Constants.numPlayers; i++) {
            ArrayList<Card> hand = new ArrayList();
            hand.add(deck.get(0));
            hand.add(deck.get(0 + Constants.numPlayers));
            //temporary to build handValueCalc
            hand.add(deck.get(2));
            hand.add(deck.get(3));
            hand.add(deck.get(4));
            new Player(hand);
        }
        //removes the dealt cards from the deck
        deck.subList(0, Constants.numPlayers * 2).clear();
    }

    private void fillDeck(ArrayList<Card> deck) {
        Value fillValue = Value.TWO;
        Suit fillSuit = Suit.CLUBS;
        for (int i = 0; i < Constants.numSuits; i++)    {
            fillSuit = fillSuit.cycle();
            for (int j = 0; j < Constants.numValues; j++)   {
                deck.add(new Card(fillValue, fillSuit));
                fillValue = fillValue.cycle();
            }
        }
    }
}
