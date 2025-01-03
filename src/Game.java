package src;

import java.sql.Time;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Game
{
    int players;

    Stack<Card> deck;

    Random random;

    long seed;

    public Game()
    {
        deck = new Stack<>();
        seed = Time.valueOf(LocalTime.now()).getTime();
        random = new Random(seed);
    }

    public void createDeck()
    {
        LinkedList<Card> unshuffled = new LinkedList<>();

        // Add Card to Unshuffled Deck
        for (int i = Card.MIN_VALUE; i < Card.MAX_VALUE; ++i)
        {
            for (int j = 0; j < Card.CardSuit.values().length; ++j)
            {
                unshuffled.add(
                        new Card(i, Card.CardSuit.values()[j])
                );
            }
        }

        // Randomly remove from unshuffled and add to deck
        while (!unshuffled.isEmpty())
        {
            Card popped =
                    unshuffled.remove(random.nextInt(0, unshuffled.size()));

            deck.add(popped);
        }
    }
}
