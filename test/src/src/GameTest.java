package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void testCanConstructGame()
    {
        ConsoleView c_view = new ConsoleView();
        
        Game g = new Game(c_view);
    }
}