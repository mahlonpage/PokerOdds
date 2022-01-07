package PokerOdds;

import java.util.ArrayList;
import java.util.Collections;

public class handValueCalculator {

    private Hand myHand;

    public handValueCalculator(ArrayList<Card> allCards)    {
        this.myHand = this.findHand(allCards);
    }

    public Hand getHand()   {
        return this.myHand;
    }

    private Hand findHand(ArrayList<Card> allCards) {

        //Our hand of best 5 cards of 7 possible.
        Card[] bestFive = new Card[5];
        int innerRank = 0;

        //creates an ArrayList for cards of each suit
        int clubs = 0;
        int diamonds = 0;
        int hearts = 0;
        int spades = 0;
        for (Card card : allCards) {
            if (card.getSuit() == 0)
                clubs++;
            if (card.getSuit() == 1)
                diamonds++;
            if (card.getSuit() == 2)
                hearts++;
            if (card.getSuit() == 3)
                spades++;
        }

        //creates an ArrayList of 13 ArrayLists each containing the cards by value.
        ArrayList<ArrayList<Integer>> binsByValue = this.binByValue(allCards);

        ArrayList<Integer> sortByValue = this.binsToOne(binsByValue);

        //Now that everything is sorted. Check for poker hands in descending order.

        //Straight Flush
        //InnerRank is high card of the straight.

        ArrayList<Card> flushCards = new ArrayList<>();

        int suit = -1;
        if (clubs >= 5) {
            suit = 0;
            for (Card card : allCards)
                if (card.getSuit() == 0)
                    flushCards.add(card);
        }
        if (diamonds >= 5) {
            suit = 1;
            for (Card card : allCards)
                if (card.getSuit() == 1)
                    flushCards.add(card);
        }
        if (hearts >= 5) {
            suit = 2;
            for (Card card : allCards)
                if (card.getSuit() == 2)
                    flushCards.add(card);
        }
        if (spades >= 5) {
            suit = 3;
            for (Card card : allCards)
                if (card.getSuit() == 3)
                    flushCards.add(card);
        }

        if (!flushCards.isEmpty())  {
            ArrayList<Integer> flushValues = new ArrayList<>();
            for (Card card : flushCards)    {
                flushValues.add(card.getValue());
            }

            Collections.sort(flushValues);

            if (this.isStraight(flushValues)) {
                boolean done;
                done = false;
                //A2345 Straight edge case.
                if (flushValues.get(flushValues.size() - 1) == 12 && flushValues.get(2) == 2)    {
                    //Note this represents 5 as it is the 4th card in sequential order and 5 is high in this case
                    innerRank = 3;
                    done = true;
                }
                int i = 1;
                while (!done)   {
                    //checks to see if cards at the end of the potential straight are 4 apart, if so, it is a straight.
                    if (flushValues.get(flushValues.size() - i) == (flushValues.get(flushValues.size() - i - 4) + 4)) {
                        innerRank = flushValues.get(flushValues.size() - i);
                        done = true;
                    }
                    i++;
                }
                //Add cards in descending order to the hand.
                bestFive[0] = new Card(suit * 4 + innerRank - 4);
                bestFive[1] = new Card(suit * 4 + innerRank - 3);
                bestFive[2] = new Card(suit * 4 + innerRank - 2);
                bestFive[3] = new Card(suit * 4 + innerRank - 1);
                //adds ace in the case of the A2345 edge case.
                if (innerRank == 3)
                    bestFive[4] = new Card(suit * 4 + 12);
                else
                    //adds the lowest card elsewise
                    bestFive[4] = new Card(suit * 4 + innerRank);
                return new Hand(RankType.STRAIGHT_FLUSH, innerRank, bestFive);
            }
        }


        //Quads
        //InnerRank is 100*Quads value + fifth card value

        //finds card value with highest frequency in the hand. Ex: KKKK977 yields 4 kings.
        int tempAmt;
        int valueOfTempAmt;
        int maxAmt = 0;
        int valueOfMaxAmt = -1;
        for (int i = 0; i < Constants.numValues; i++)   {
            if (!binsByValue.get(i).isEmpty()) {
                tempAmt = binsByValue.get(i).size();
                valueOfTempAmt = i;
                if (tempAmt >= maxAmt) {
                    maxAmt = tempAmt;
                    valueOfMaxAmt = valueOfTempAmt;
                }
            }
        }
        //if there are quads add the proper cards to hand
        if (maxAmt == 4)    {
            bestFive[0] = new Card(valueOfMaxAmt);
            bestFive[1] = new Card(valueOfMaxAmt + Constants.numSuits);
            bestFive[2] = new Card(valueOfMaxAmt + Constants.numSuits * 2);
            bestFive[3] = new Card(valueOfMaxAmt + Constants.numSuits * 3);
            //the final card is the highest value remaining card
            for (int i = sortByValue.size() - 1; i >= 0; i--) {
                if (sortByValue.get(i) % 13 != valueOfMaxAmt) {
                    bestFive[4] = new Card(sortByValue.get(i));
                    break;
                }
            }
            innerRank = 100 * valueOfMaxAmt + bestFive[4].getValue();
            return new Hand(RankType.QUADS, innerRank, bestFive);
        }


        //Full House
        //InnerRank is 100*Trips value + pair value.

        //Calculates the value of card with the second most amount ex: KKKQQ32 would yield 2 queens.
        int tempAmt2;
        int valueOfTempAmt2;
        int maxAmt2 = 0;
        int valueOfMaxAmt2 = -1;
        for (int i = 0; i < Constants.numValues; i++)   {
            if (!binsByValue.get(i).isEmpty() && i != valueOfMaxAmt) {
                tempAmt2 = binsByValue.get(i).size();
                valueOfTempAmt2 = i;
                if (tempAmt2 >= maxAmt2) {
                    maxAmt2 = tempAmt2;
                    valueOfMaxAmt2 = valueOfTempAmt2;
                }
            }
        }

        //If there is 3 of one card and 2 or more of another we have a full house
        if (maxAmt == 3 && maxAmt2 >= 2)    {
            //adds the trips cards to our best five.
            int j = 0;
            for (int i = 0; i < Constants.numSuits; i++) {
                if (allCards.contains(new Card(valueOfMaxAmt + 13 * i))) {
                    bestFive[j] = new Card(valueOfMaxAmt + 13 * i);
                    j++;
                }
            }
            //adds the pair cards to our best five.
            for (int i = 0; i < Constants.numSuits; i++) {
                if (allCards.contains(new Card(valueOfMaxAmt2 + 13 * i))) {
                    bestFive[j] = new Card(valueOfMaxAmt2 + 13 * i);
                    j++;
                }
            }
            innerRank = (100 * valueOfMaxAmt + valueOfMaxAmt2);
            return new Hand(RankType.FULL_HOUSE, innerRank, bestFive);
        }


        //Flush
        //InnerRank is value of the values strung together in descending order. Ex: AK543 yields 1211050403

        //Will only enter statement if there is a flush as determined in straight flush step.
        if (!flushCards.isEmpty())  {
            //extracts card values and adds them to a sorted arraylist.
            ArrayList<Integer> flushValues = new ArrayList<>();
            for (Card card : flushCards)    {
                flushValues.add(card.getValue());
            }

            Collections.sort(flushValues);

            //adds the first 5 cards from high to low to the hand constructing the best flush possible.
            for (int i = 0; i < 5; i++) {
                int highValue;
                highValue = flushValues.get(flushValues.size() - 1 - i);
                bestFive[4 - i] = new Card(13 * suit + highValue);
            }
            innerRank = bestFive[0].getValue() + 100 * bestFive[1].getValue() + 10000 * bestFive[2].getValue()
                    + 1000000 * bestFive[3].getValue() + 100000000 * bestFive[4].getValue();
            return new Hand(RankType.FLUSH, innerRank, bestFive);
        }


        //Straight
        //InnerRank is high card of the straight.

        //extracts the values of all the cards and sorts.
        ArrayList<Integer> allCardValues = new ArrayList<>();
        for (Card card : allCards)  {
            allCardValues.add(card.getValue());
        }
        Collections.sort(allCardValues);

        //if there is a straight, check to find the length of the straight and its high card.
        if (isStraight(allCardValues)) {
            int straightLength = 1;
            int maxStraightLength = 0;
            int straightHighCard;
            int maxStraightHighCard = -1;
            for (int i = 0; i < allCardValues.size() - 1; i++)  {
                //if current card is one less than the next card (i.e. a straight)
                if ((allCardValues.get(i) + 1) == (allCardValues.get(i + 1))) {
                    straightLength++;
                    straightHighCard = allCardValues.get(i + 1);
                    if (straightLength >= maxStraightLength) {
                        maxStraightHighCard = straightHighCard;
                        maxStraightLength = straightLength;
                    }

                } //If the next card is of the same value, skip it.
                else {
                    if (allCardValues.get(i) != (allCardValues.get(i + 1))) {
                        straightLength = 1;
                    }
                }
            }

            //Using the values, checks to see which suit it was in our original hand since that data was thrown out.
            //Note: this will favor cards in reverse alphabetical suit order, but it doesn't matter.
            for (int i = 0; i < 4; i++) {
                //Counts down from high card of the straight.
                if (allCards.contains((13 * i) + maxStraightHighCard))
                    bestFive[0] = new Card(13 * i + maxStraightHighCard);
                if (allCards.contains((13 * i) + maxStraightHighCard - 1))
                    bestFive[1] = new Card(13 * i + maxStraightHighCard);
                if (allCards.contains((13 * i) + maxStraightHighCard - 2))
                    bestFive[2] = new Card(13 * i + maxStraightHighCard);
                if (allCards.contains((13 * i) + maxStraightHighCard - 3))
                    bestFive[3] = new Card(13 * i + maxStraightHighCard);
                //A2345 edge case adds an ace as the final card.
                if (maxStraightHighCard == 3)   {
                    bestFive[4] = new Card(13 * i + 12);
                } else {
                    if (allCards.contains((13 * i) + maxStraightHighCard - 4))
                        bestFive[4] = new Card(13 * i + maxStraightHighCard);
                }
            }
            return new Hand(RankType.STRAIGHT, maxStraightHighCard, bestFive);
        }



        //Trips
        //InnerRank Trips value * 10,000 + high card * 100 + second high card.
        if (maxAmt == 3)    {
            //adds the trips cards to our best five.
            int j = 0;
            for (int i = 0; i < Constants.numSuits; i++) {
                if (allCards.contains(new Card(valueOfMaxAmt + 13 * i))) {
                    bestFive[j] = new Card(valueOfMaxAmt + 13 * i);
                    j++;
                }
            }
            int k = 0;
            for (int i = sortByValue.size() - 1; i >= 0; i--) {
                if (sortByValue.get(i) % 13 != valueOfMaxAmt) {
                    bestFive[4 - k] = new Card(sortByValue.get(i));
                    k++;
                    if (k > 1)
                        break;
                }
            }
            innerRank = valueOfMaxAmt * 10000 + bestFive[4].getValue() * 100 + bestFive[3].getValue();
            return new Hand(RankType.TRIPS, innerRank, bestFive);
        }


        //Two Pair and Pair
        if (maxAmt == 2)    {
            //adds first pair cards to our set.
            int j = 0;
            for (int i = 0; i < Constants.numSuits; i++) {
                if (allCards.contains(new Card(valueOfMaxAmt + 13 * i))) {
                    bestFive[j] = new Card(valueOfMaxAmt + 13 * i);
                    j++;
                }
            }
            //Need 3 more cards if we have one pair
            int cardsToFill;
            cardsToFill = 3;
            if (maxAmt2 == 2)   {
                //k = 2 because first two card slots are filled by first pair
                int k = 2;
                for (int i = 0; i < Constants.numSuits; i++) {
                    if (allCards.contains(new Card(valueOfMaxAmt + 13 * i))) {
                        bestFive[k] = new Card(valueOfMaxAmt + 13 * i);
                        k++;
                    }
                }
                //Need one more card if we have two pair.
                cardsToFill = 1;
            }
            int k = 0;
            for (int i = sortByValue.size() - 1; i >= 0; i--) {
                if (sortByValue.get(i) % 13 != valueOfMaxAmt) {
                    //ignore cards of second highest amt if second pair is in play.
                    if (maxAmt2 == 1 || sortByValue.get(i) % 13 != valueOfMaxAmt2) {
                        bestFive[4 - k] = new Card(sortByValue.get(i));
                        k++;
                        if (k >= cardsToFill)
                            break;
                    }
                }
            }
            if (maxAmt2 == 2)   {
                innerRank = 10000 * valueOfMaxAmt + 100 * valueOfMaxAmt2 + bestFive[4].getValue();
                return new Hand(RankType.TWO_PAIR, innerRank, bestFive);
            }
            innerRank = 1000000 * valueOfMaxAmt + 10000 * bestFive[4].getValue() +
                    100 * bestFive[3].getValue() + bestFive[2].getValue();
            return new Hand(RankType.PAIR, innerRank, bestFive);

        }


        //High Card
        //InnerRank is card values in descending order placed next to each other
        //Ex; A396Q becomes 1210070401 (12 10 07 04 01)
        int k = 0;
        for (int i = sortByValue.size() - 1; i >= 0; i--) {
            bestFive[4 - k] = new Card(sortByValue.get(i));
            k++;
            if (k >= 5)
                break;
        }
        innerRank = bestFive[4].getValue() * 100000000 + bestFive[3].getValue() * 1000000 +
                bestFive[2].getValue() * 10000 + bestFive[1].getValue() * 100 + bestFive[0].getValue();
        return new Hand(RankType.HIGH_CARD, innerRank, bestFive);
    }

    //Takes cards and returns an arraylist with the number of each type of card present.
    //Ex: the hand K K K Q 4 2 2 returns an ArrayList with 3 1 1 2.
    private ArrayList<ArrayList<Integer>> binByValue(ArrayList<Card> allCards) {
        ArrayList<ArrayList<Integer>> numOfEachValue = new ArrayList<>();

        for (int i = 0; i < Constants.numValues; i++)
            numOfEachValue.add(new ArrayList<>());

        for (Card card : allCards) {
            int t = card.getValue();
            numOfEachValue.get(t).add(card.toInt());
        }

        for (int i = 0; i < Constants.numValues; i++)
            Collections.sort(numOfEachValue.get(i));

        return numOfEachValue;
    }

    private ArrayList<Integer> binsToOne(ArrayList<ArrayList<Integer>> binsByValue)   {
        ArrayList<Integer> sortedByValue = new ArrayList<>();
        for (ArrayList<Integer> integers : binsByValue) {
            sortedByValue.addAll(integers);
        }
        return sortedByValue;
    }

    //checks if the inputted hand is a straight and returns how long of a straight can be made
    private boolean isStraight (ArrayList<Integer> hand)    {
        Collections.sort(hand);
        int straightLength;
        straightLength = 1;
        int maxStraight;
        maxStraight = 0;
        for (int i = 0; i < hand.size() - 1; i++)   {
            if (hand.get(i) + 1 == hand.get(i + 1)) {
                straightLength++;
                if (straightLength >= maxStraight)  {
                    maxStraight = straightLength;
                }
            }
            else {
                if (hand.get(i) != (hand.get(i + 1))) {
                    straightLength = 1;
                }
            }
            //System.out.println(maxStraight);
        }
        //Checks edge case of the A2345 Straight.
        if (maxStraight == 4 && hand.get(0) == 0 && hand.contains(12) && hand.contains(1) && hand.contains(2) && hand.contains(3))
            return true;

        return maxStraight >= 5;
    }
}