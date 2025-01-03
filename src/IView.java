package src;

import java.util.ArrayList;

public interface IView
{
    class Play
    {
        int playerCardIndex;
        ArrayList<int> cards;
    };

    void DisplayBoardCards(ArrayList<Card> cards);
    void DisplayPlayerCards(ArrayList<Card> cards);
}
