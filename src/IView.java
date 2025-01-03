package src;

import java.util.ArrayList;

public interface IView
{
    class Play
    {
        boolean quitGame = false;
        boolean exitApplication = false;
        int playerCardIndex = 0;
        ArrayList<Integer> boardCardIndices =  new ArrayList<>();
    };

    void DisplayErrorMessage(String message);
    void DisplayTutorialMessage(String message);

    void DisplayBoardCards(ArrayList<Card> cards);
    void DisplayPlayerCards(ArrayList<Card> cards);
    Play GetPlay();
}
