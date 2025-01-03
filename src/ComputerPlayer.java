package src;
import java.util.*;
public class ComputerPlayer extends Player
{
    ComputerPlayer()
    {
        super();
    }

    public IView.Play playCard(Card[] boardCards)
        //First attempt logic at game choices
    {
        List<Card> computerCards = Arrays.asList(boardCards);
        List<Card> cardOrder = Arrays.asList(boardCards);
        List<String> suitOrder = Arrays.asList("Coins", "Swords", "Cups", "Clubs");
        ArrayList<Integer> cardsToRemove = new ArrayList<>();
        for(Card card : super.hand)
        {
            int scoreVal = 15 - card.value;

            //Find and sort matches descending order.
            Comparator<Card> cardComparator = (Card c1, Card c2) ->
            {
                int valueComparison = Integer.compare(c2.value, c1.value);
                if (valueComparison != 0)
                {
                    return valueComparison;
                }
                else
                {
                    return Integer.compare(suitOrder.indexOf(c1.suit), suitOrder.indexOf(c2.suit));
                }
            };
            computerCards.sort(cardComparator);

            for (Card option : boardCards)
            {
                if (scoreVal == option.value ) { //Hardcoded to only pick a single card.
                    cardsToRemove.add(cardOrder.indexOf(option));
                    return new IView.Play(hand.indexOf(card),
                            cardsToRemove);
                }
            }
        }

        return  new IView.Play(0);
    }

}
