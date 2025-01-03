package src;

public class Card
{
    public int value;

    public CardSuit suit;

    public Card(int value, CardSuit suit)
    {
        if(value > MAX_VALUE)
        {
            throw new IllegalArgumentException("Card value is above Card.MAX_VALUE");
        }

        if(value > MIN_VALUE)
        {
            throw new IllegalArgumentException("Card value is below Card.MIN_VALUE");
        }

        this.value = value;
        this.suit = suit;


    }

    public enum CardSuit
    {
        Swords,
        Cups,
        Coins,
        Clubs
    }

    public static int MAX_VALUE = 10;

    public static int MIN_VALUE = 1;
}
