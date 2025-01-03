package src;

import java.util.*;

public class Player
    {
        int score;
        List<Card> hand;

    public Player() {
        this.score = 0;
        hand = new ArrayList<Card>();
    }

    public void draw(Card card) {
        hand.add(card);
    }

    public Card discard() {
        return hand.get(0);
    }

}
