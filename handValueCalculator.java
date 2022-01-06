package PokerOdds;

import java.util.ArrayList;
import java.util.Collections;

public class handValueCalculator {

    //The type of hand held, ex: two pair
    private RankType myRank;
    //The value of the hand. Differentiates within the same type. ex: pair of kings > pair of threes
    //TODO could be an arraylist
    private int myInnerRank;

    public handValueCalculator(ArrayList<Card> allCards)    {
        this.myRank = this.findRank(allCards);
    }

    public RankType getRank()   {
        return this.myRank;
    }

    public int getInnerRank()   {
        return this.myInnerRank;
    }

    private RankType findRank(ArrayList<Card> allCards) {

        //creates an ArrayList of card values and sorts them
        ArrayList<Integer> cardValues = new ArrayList();
        for (Card card : allCards)
            cardValues.add(card.getValue().toInt());
        Collections.sort(cardValues);

        //creates an ArrayList for cards of each suit
        ArrayList<Card> clubCards = new ArrayList();
        ArrayList<Card> diamondCards = new ArrayList();
        ArrayList<Card> heartCards = new ArrayList();
        ArrayList<Card> spadeCards = new ArrayList();
        for (Card card : allCards) {
            if (card.getSuit() == Suit.CLUBS)
                clubCards.add(card);
            if (card.getSuit() == Suit.DIAMONDS)
                diamondCards.add(card);
            if (card.getSuit() == Suit.HEARTS)
                heartCards.add(card);
            if (card.getSuit() == Suit.SPADES)
                spadeCards.add(card);
        }

        //Now that everything is sorted. Check for poker hands in descending order.

        //Straight Flush
        ArrayList<Integer> flushCards = new ArrayList();
        if (clubCards.size() == 5) {
            for (Card card : clubCards)
                flushCards.add(card.getValue().toInt());
        }
        if (diamondCards.size() == 5) {
            for (Card card : clubCards)
                flushCards.add(card.getValue().toInt());
        }
        if (heartCards.size() == 5) {
            for (Card card : clubCards)
                flushCards.add(card.getValue().toInt());
        }
        if (spadeCards.size() == 5) {
            for (Card card : clubCards)
                flushCards.add(card.getValue().toInt());
        }

        if (!flushCards.isEmpty())  {
            Collections.sort(flushCards);
            if (flushCards.get(4) - flushCards.get(0) == 4) {
                if (flushCards.get(4) == Value.ACE.toInt())
                    return RankType.ROYAL_FLUSH;

                return RankType.STRAIGHT_FLUSH;
            }

            //edge case where ace is used as low card in the flush.
            if (flushCards.get(4) == Value.ACE.toInt() && flushCards.get(3) == Value.FIVE.toInt())
                return RankType.STRAIGHT_FLUSH;
        }


        //Quads
        ArrayList<Integer> numOfEachValue = this.numOfEachValue(cardValues);
        Collections.sort(numOfEachValue);
        if (numOfEachValue.get(numOfEachValue.size() - 1) == 4)
            return RankType.QUADS;


        //Full House










        return null;
    }

    //Takes cards and returns an arraylist with the number of each type of card present.
    //Ex: the hand K K Q 4 2 returns an ArrayList with 2 1 1 1.
    private ArrayList<Integer> numOfEachValue(ArrayList<Integer> values) {
        Collections.sort(values);
        ArrayList<Integer> numOfEachValue = new ArrayList();
        int numOfCurrentType;
        numOfCurrentType = 1;
        for (int i = 0; i < 5; i++) {
            if (values.get(i).equals(values.get(i + 1)))
                numOfCurrentType++;
            else {
                numOfEachValue.add(numOfCurrentType);
                numOfCurrentType = 1;
            }
        }
        Collections.sort(numOfEachValue);
        return numOfEachValue;
    }
}


