package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;

public class ConsoleView implements IView
{
    private InputStream inputStream = System.in;
    private OutputStream outputStream = System.out;
    private String suits = "⚔🍵🪙♣";

    private String suitChar(Card.CardSuit suit)
    {
        switch (suit.ordinal())
        {
            case 0: return "⚔";
            case 1: return "🍵";
            case 2: return "🪙";
            case 3: return "♣";
        }
        return "?";
    }

    private void writeline(String s)
    {
        try
        {
            outputStream.write((s + "\n").getBytes());
            outputStream.flush();
        }
        catch (IOException e)
        {
            System.out.println("Unhandled exception:");
            System.out.println(e);
        }
    }

    public ConsoleView()
    {
    }

    public ConsoleView(InputStream is, OutputStream os)
    {
        this.inputStream = is;
        this.outputStream = os;
    }

    @Override
    public void DisplayErrorMessage(String message)
    {
        writeline("ERROR: " + message);
    }

    @Override
    public void DisplayTutorialMessage(String message)
    {
        writeline("TUTOR: " + message);
    }

    @Override
    public void DisplayBoardCards(ArrayList<Card> cards)
    {
        String msg = "Board Cards:";
        char id = 'a';
        for (Card card : cards)
        {
            msg += " [" + id + "]" + String.valueOf(card.value) + suitChar(card.suit);
            id++;
        }
        writeline(msg);
    }

    @Override
    public void DisplayPlayerCards(ArrayList<Card> cards)
    {
        String msg = "Player Cards:";
        char id = '1';
        for (Card card : cards)
        {
            msg += " [" + id + "]" + String.valueOf(card.value) + suitChar(card.suit);
            id++;
        }
        writeline(msg);
    }

    @Override
    public IView.Play GetPlay(ArrayList<Card> boardCards, ArrayList<Card> playerCards)
    {
        Scanner scanner = new Scanner(inputStream);
        IView.Play play = new IView.Play();

        String prompt = "Available cards: 1";
        if (playerCards.size() > 1)
        {
            prompt += "..." + (char)('0'+playerCards.size());
        }
        if (boardCards.size() > 0)
        {
            prompt += ", a";
            if (boardCards.size() > 1)
            {
                prompt += "..." + (char)('a'+boardCards.size()-1);
            }
        }
        prompt += ".  Enter Q to quit the game or X to exit Escoba.";
        boolean validInput = false;
        while (!validInput)
        {
            writeline(prompt);
            String input = scanner.nextLine().strip();
            if (input.equals("Q"))
            {
                play.quitGame = true;
                validInput = true;
            }
            else if (input.equals("X"))
            {
                play.exitApplication = true;
                validInput = true;
            }
            else if (input.length() <= boardCards.size() + 1)
            {
                int playerCardIndex = (int)(input.charAt(0) - '0');
                if ((playerCardIndex >= 1) && (playerCardIndex <= playerCards.size()))
                {
                    play.playerCardIndex = playerCardIndex;
                    validInput = true;
                }
                if (validInput)
                {
                    ArrayList<Integer> boardCardIndices = new ArrayList<>();
                    for (Integer i = 1; i < input.length(); i++)
                    {
                        int cardIndex = (int)(input.charAt(0) - 'a' + 1);
                        if ((cardIndex < 0) || (cardIndex >= boardCards.size()) || (boardCardIndices.contains(cardIndex)))
                        {
                            validInput = false;
                            break;
                        }
                        boardCardIndices.add(cardIndex);
                    }
                    if (validInput)
                    {
                        play.boardCardIndices = boardCardIndices;
                    }
                }
            }
        }

        scanner.close();
        return play;
    }
}
