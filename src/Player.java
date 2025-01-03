package src;

import java.util.*;

public abstract class Player
{

    public int score;
    public List<Card> hand;
    public List<Card> point_pile;
    public int round_escobas;

    public Player()
    {
        this.score = 0;
        hand = new ArrayList<Card>();
        point_pile = new ArrayList<>();
        round_escobas = 0;
    }

    public void draw(Card card)
    {
        hand.add(card);
    }

    public Card discard(int idx)
    {
        if(idx > hand.size())
        {
            throw new IllegalArgumentException("Index " + idx + " is out of bounds for this hand");
        }

        if(hand.isEmpty())
        {
            throw new IllegalArgumentException("Player's hand is empty");
        }

        return hand.remove(idx);
    }
    
    public void clear_hand()
    {
        hand.clear();
    }

    public void clear_point_pile()
    {
        point_pile.clear();
    }

    //Either finds a series of cards to match, or empty and needs to discard.
    abstract IView.Play playCard(Card[] boardCards);

    static int HAND_MAX_SIZE = 3;
}
