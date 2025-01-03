package src;

public class Card
{
    public int value;

    public CardSuit suit;

    public Card(int value, CardSuit suit)
    {

        this.value = value;
        this.suit = suit;

        if (this.value > MAX_VALUE)
        {
            this.value = MAX_VALUE;
        }

        if (this.value < MIN_VALUE)
        {
            this.value = MIN_VALUE;
        }
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
