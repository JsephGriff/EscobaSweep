package src;

public class Card
{
    public int value;

    public CardSuit suit;

    

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
