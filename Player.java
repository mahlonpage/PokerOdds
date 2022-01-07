package PokerOdds;

public class Player {

    private Card card1;
    private Card card2;

    public Player(Card card1, Card card2)   {
        this.card1 = card1;
        this.card2 = card2;
    }

    public Card getCard(boolean isCard1)  {
        if (isCard1)
            return this.card1;
        return this.card2;
    }
}
