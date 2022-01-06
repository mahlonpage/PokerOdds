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

        //creates an ArrayList of the number of each value of the cards present.
        ArrayList<Integer> numOfEachValue = this.numOfEachValue(cardValues);
        Collections.sort(numOfEachValue);

        //Now that everything is sorted. Check for poker hands in descending order.

        //Straight Flush
        ArrayList<Integer> flushCardsValues = new ArrayList();
        if (clubCards.size() >= 5) {
            for (Card card : clubCards)
                flushCardsValues.add(card.getValue().toInt());
        }
        if (diamondCards.size() >= 5) {
            for (Card card : clubCards)
                flushCardsValues.add(card.getValue().toInt());
        }
        if (heartCards.size() >= 5) {
            for (Card card : clubCards)
                flushCardsValues.add(card.getValue().toInt());
        }
        if (spadeCards.size() >= 5) {
            for (Card card : clubCards)
                flushCardsValues.add(card.getValue().toInt());
        }

        if (!flushCardsValues.isEmpty())  {
            Collections.sort(flushCardsValues);
            if (this.checkStraight(flushCardsValues)) {
                if (flushCardsValues.get(0) == Value.TEN.toInt())
                    return RankType.ROYAL_FLUSH;
                return RankType.STRAIGHT_FLUSH;
            }
        }


        //Quads
        Collections.sort(numOfEachValue);
        if (numOfEachValue.get(numOfEachValue.size() - 1) == 4)
            return RankType.QUADS;

        //Full House
        if (numOfEachValue.get(0) == 2 && numOfEachValue.get(1) == 3)
            return RankType.FULL_HOUSE;

        //Flush
        if (flushCardsValues.size() != 0)
            return RankType.FLUSH;

        //Straight
        if (checkStraight(cardValues))
            return RankType.STRAIGHT;


        //Trips
        if (numOfEachValue.get(numOfEachValue.size() - 1) == 3)
            return RankType.TRIPS;

        //Two Pair and Pair
        if (numOfEachValue.get(numOfEachValue.size() - 1) == 2) {
            if (numOfEachValue.get(numOfEachValue.size() - 2) == 2)
                return RankType.TWO_PAIR;
            return RankType.PAIR;
        }

        //High Card
        return RankType.HIGH_CARD;
    }

    //Takes cards and returns an arraylist with the number of each type of card present.
    //Ex: the hand K K Q 4 2 returns an ArrayList with 2 1 1 1.
    private ArrayList<Integer> numOfEachValue(ArrayList<Integer> values) {
        Collections.sort(values);
        ArrayList<Integer> numOfEachValue = new ArrayList();
        int numOfCurrentType;
        numOfCurrentType = 1;
        for (int i = 0; i < 5; i++) {
            if (i != 4 && values.get(i).equals(values.get(i + 1)))
                numOfCurrentType++;
            else {
                numOfEachValue.add(numOfCurrentType);
                numOfCurrentType = 1;
            }
        }
        Collections.sort(numOfEachValue);
        return numOfEachValue;
    }

    //checks if the inputted hand is a straight
    //TODO make this work for 7 cards
    private boolean checkStraight (ArrayList<Integer> hand)    {
        Collections.sort(hand);
        if (hand.get(4) - hand.get(0) == 4)
            return true;
        //edge case where ace is used as low card in the flush.
        if (hand.get(4) == Value.ACE.toInt() && hand.get(3) == Value.FIVE.toInt())
            return true;
        return false;
    }
}


