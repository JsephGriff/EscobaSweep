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
        public Play () {}
        public Play (int playerCardIndex) {
            this.playerCardIndex = playerCardIndex;
        }
        public Play (int playerCardIndex, ArrayList<Integer> boardCardIndices) {
            this.playerCardIndex = playerCardIndex;
            this.boardCardIndices = boardCardIndices;
        }
    };


    void DisplayErrorMessage(String message);
    void DisplayTutorialMessage(String message);

    void DisplayBoardCards(ArrayList<Card> cards);
    void DisplayPlayerCards(ArrayList<Card> cards);
    Play GetPlay(ArrayList<Card> boardCards, ArrayList<Card> playerCards);
}
