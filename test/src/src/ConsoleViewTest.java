package src;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleViewTest
{
    @Test
    void testConsoleViewDisplaysSuitsCorrectly()
    {
        String inputData = "";
        InputStream inputStream = new ByteArrayInputStream(inputData.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();

        ConsoleView consoleView = new ConsoleView(inputStream, outputStream);
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1, Card.CardSuit.Swords));
        cards.add(new Card(2, Card.CardSuit.Cups));
        cards.add(new Card(3, Card.CardSuit.Coins));
        cards.add(new Card(4, Card.CardSuit.Clubs));
        consoleView.DisplayBoardCards(cards);
        String result = outputStream.toString();

        String expectedResult = "Board Cards: [a]1⚔ [b]2🍵 [c]3🪙 [d]4♣\n";
        assertEquals(expectedResult, result);
    }
}