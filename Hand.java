package PokerOdds;

public class Hand {

    //Type of hand held ex: two pair
    private RankType myRank;
    //Value within it's rank type ex: 98765 beats 87654
    private int innerRank;
    //Actual 5 cards used of the 7.
    private Card[] hand;

    public Hand(RankType rank, int innerRank, Card[] hand)   {
        this.myRank = rank;
        this.innerRank = innerRank;
        this.hand = hand;
    }

    public RankType getRank() {
        return this.myRank;
    }

    public int getInnerRank()   {
        return this.innerRank;
    }

    public Card[] getHand() {
        return this.hand;
    }
}
