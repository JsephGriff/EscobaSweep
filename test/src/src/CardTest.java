package src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    @Test
    void testCanConstructCardWithMinimalValue() {
        Card card = new Card(Card.MIN_VALUE, Card.CardSuit.Swords);
    }

    @Test
    void testCanConstructCardWithMaximumValue() {
        Card card = new Card(Card.MAX_VALUE, Card.CardSuit.Swords);
    }

    @Test
    void testCannotConstructCardWithValueBelowMinValue_IllegalArgumentExceptionIsThrown() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Card card = new Card(Card.MIN_VALUE-1, Card.CardSuit.Swords);
                }
        );
    }

    @Test
    void testCannotConstructCardWithValueAboveMaxValue_IllegalArgumentExceptionIsThrown() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Card card = new Card(Card.MAX_VALUE+1, Card.CardSuit.Swords);
                }
        );
    }
}