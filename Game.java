package PokerOdds;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

    public Game()   {
        ArrayList<Card> deck = new ArrayList();
        this.fillDeck(deck);
        Collections.shuffle(deck);
        ArrayList<Card> cards = new ArrayList();
        //adds Players to the Poker game and deals two cards to each.
        Player player1 = new Player(deck.get(0), deck.get(2));
        Player player2 = new Player(deck.get(1), deck.get(3));
        //removes the dealt cards from the deck
        System.out.println("Player 1: " + deck.get(0).getValueString() + deck.get(0).getSuitString() + " " + deck.get(2).getValueString() + deck.get(2).getSuitString() + " ");
        System.out.println("-------");
        System.out.println("Player 2: " + deck.get(1).getValueString() + deck.get(1).getSuitString() + " " + deck.get(3).getValueString() + deck.get(3).getSuitString() + " ");
        System.out.println("-------");


        ArrayList<Card> p1Hand = new ArrayList<>();
        ArrayList<Card> p2Hand = new ArrayList<>();
        p1Hand.add(deck.get(0));
        p2Hand.add(deck.get(1));
        p1Hand.add(deck.get(2));
        p2Hand.add(deck.get(3));
        deck.subList(0, Constants.numPlayers * 2).clear();
        ArrayList<Card> p1TotalHand = new ArrayList<>();
        ArrayList<Card> p2TotalHand = new ArrayList<>();

        //This may seem like a large runtime problem but poker computers need to be FAST and this is the best way.
        //Even though it ignores many design principles.
        int numP1Wins = 0;
        int numP2Wins = 0;
        int numTies = 0;
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 48; j++) {
                if (i == j)
                    break;
                for (int k = 0; k < 48; k++) {
                    if (i == k || j == k)
                        break;
                    for (int l = 0; l < 48; l++) {
                        if (i == l || j == l || k == l)
                            break;
                        for (int m = 0; m < 48; m++) {
                            if (i == m || j == m || k == m || l == m)
                                break;
                            p1TotalHand.clear();
                            p2TotalHand.clear();
                            p1TotalHand.add(deck.get(i));
                            p1TotalHand.add(deck.get(j));
                            p1TotalHand.add(deck.get(k));
                            p1TotalHand.add(deck.get(l));
                            p1TotalHand.add(deck.get(m));
                            p1TotalHand.add(p1Hand.get(0));
                            p1TotalHand.add(p1Hand.get(1));
                            p2TotalHand.add(deck.get(i));
                            p2TotalHand.add(deck.get(j));
                            p2TotalHand.add(deck.get(k));
                            p2TotalHand.add(deck.get(l));
                            p2TotalHand.add(deck.get(m));
                            p2TotalHand.add(p2Hand.get(0));
                            p2TotalHand.add(p2Hand.get(1));
                            handValueCalculator p1Calc = new handValueCalculator(p1TotalHand);
                            handValueCalculator p2Calc = new handValueCalculator(p2TotalHand);
                            if (p1Calc.getHand().getRank().toInt() > p2Calc.getHand().getRank().toInt())
                                numP1Wins++;
                            if (p1Calc.getHand().getRank().toInt() < p2Calc.getHand().getRank().toInt())
                                numP2Wins++;
                            if (p1Calc.getHand().getRank().toInt() == p2Calc.getHand().getRank().toInt())   {
                                if (p1Calc.getHand().getInnerRank() > p2Calc.getHand().getInnerRank())
                                    numP1Wins++;
                                if (p1Calc.getHand().getInnerRank() < p2Calc.getHand().getInnerRank())
                                    numP2Wins++;
                                if (p1Calc.getHand().getInnerRank() == p2Calc.getHand().getInnerRank())
                                    numTies++;
                            }

                        }
                    }
                }
            }
        }
        float p1WinRate = (numP1Wins * 100.0f) / (numP1Wins + numP2Wins + numTies);
        float p2WinRate = (numP2Wins * 100.0f) / (numP1Wins + numP2Wins + numTies);
        float tieRate = (numTies * 100.0f) / (numP1Wins + numP2Wins + numTies);
        System.out.println("Player 1 wins " + numP1Wins + " times! | " + p1WinRate + "% Win Rate");
        System.out.println("Player 2 wins " + numP2Wins + " times! | " + p2WinRate + "% Win Rate");
        System.out.println("There is a tie " + numTies + " times! | " + tieRate + "% Tie Rate");
    }

    private void fillDeck(ArrayList<Card> deck) {
        for (int i = 0; i < 52; i++)    {
            deck.add(new Card(i));
        }
    }
}
