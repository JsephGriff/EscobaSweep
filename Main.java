import src.*;

public class Main
{
    public static void main(String [] args)
    {
        ConsoleView c_view = new ConsoleView();
        Game game = new Game(c_view);
        game.createDeck();
    }
}
