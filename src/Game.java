package src;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Game
{
    ArrayList<Player> players;

    int player_idx;

    Stack<Card> deck;

    public ArrayList<Card> board_cards;

    Random random;

    long seed;

    IView user_interface;

    public Game(IView ui)
    {
        deck = new Stack<>();
        seed = Time.valueOf(LocalTime.now()).getTime();
        random = new Random(seed);
        board_cards = new ArrayList<>();

        user_interface = ui;

        player_idx = 0;
    }

    public void begin_new_game(ArrayList<Player> player_list)
    {
        players.addAll(player_list);

        for(Player p : players)
        {
            p.score = 0;
        }

        begin_round();

    }

    public void begin_round()
    {
        player_idx = random.nextInt(players.size());

        board_cards.clear();

        // Clear each player's hand
        for(Player p: players)
        {
            p.clear_hand();
            p.clear_point_pile();
            p.round_escobas = 0;
        }

        createDeck();
        deal_to_board();
        deal_to_players();
    }

    public void process_round()
    {
        boolean can_play_cards = true;

        for(Player p: players)
        {
            can_play_cards = can_play_cards && p.hand.size() != Player.HAND_MAX_SIZE;
        }

        if(!can_play_cards)
        {
            throw new RuntimeException("Cannot begin round. Player's hands are not empty.");
        }

        while (can_play_cards)
        {
            // Turn Setup
            Player turn_player = players.get(player_idx);

            // All Hands Are Empty
            if(turn_player.hand.isEmpty())
            {
                if (deck.isEmpty())
                {
                    can_play_cards = false;
                    break;
                } else
                {
                    deal_to_players();
                }
            }

            user_interface.DisplayPlayerCards((ArrayList<Card>) turn_player.hand);
            user_interface.DisplayBoardCards(board_cards);


            // Take and validate player turn
            boolean is_valid_turn = false;
            IView.Play player_turn;

            do
            {
                player_turn = user_interface.GetPlay(board_cards, (ArrayList<Card>) turn_player.hand);

                int turn_value = turn_player.hand.get(player_turn.playerCardIndex).value;

                if(player_turn.boardCardIndices.isEmpty())
                {
                    is_valid_turn = true;
                    break;
                }

                for(int i : player_turn.boardCardIndices)
                {
                    turn_value += i;
                }

                is_valid_turn = turn_value == TARGET_SCORE;

                if(!is_valid_turn)
                {
                    user_interface.DisplayErrorMessage("Card Total Doesn't Add to " + TARGET_SCORE);
                }
            } while (!is_valid_turn);


            // Process Player Turn
            if(player_turn.boardCardIndices.isEmpty()) // Discard Case
            {
                Card discarded_card = turn_player.discard(player_turn.playerCardIndex);

                board_cards.add(discarded_card);
            } else // Play Case
            {
                Card player_card = turn_player.discard(player_turn.playerCardIndex);

                ArrayList<Card> board_take = new ArrayList<>();

                for(int idx: player_turn.boardCardIndices)
                {
                    board_take.add(board_cards.get(idx));
                }

                for(Card c: board_take)
                {
                    board_cards.remove(c);
                    turn_player.point_pile.add(c);
                }

                turn_player.point_pile.add(player_card);

                // Escoba Case
                if(board_cards.isEmpty())
                {
                    turn_player.round_escobas += 1;
                }
            }

            // Close turn
            ++player_idx;

            if(player_idx >= players.size())
            {
                player_idx = 0;
            }
        }

        tally_player_points();
    }

    public void tally_player_points()
    {
        int most_coins = -1;
        int most_sevens = -1;
        int most_cards = -1;

        ArrayList<Player> top_coins = new ArrayList<>();
        ArrayList<Player> top_sevens = new ArrayList<>();
        ArrayList<Player> top_cards = new ArrayList<>();

        for(Player p: players)
        {
            p.score += p.round_escobas;

            int count_coins = 0;
            int count_sevens = 0;
            int count_cards = 0;

            for(Card c: p.hand)
            {
                if(c.value == 7)
                {
                    ++count_sevens;

                    if(c.suit == Card.CardSuit.Coins)
                    {
                        p.score += 1;
                    }
                }

                if(c.suit == Card.CardSuit.Coins)
                {
                    ++count_coins;
                }

                ++count_cards;
            }

            if(count_sevens > most_sevens)
            {
                top_sevens.clear();
                top_sevens.add(p);
            } else if(count_sevens == most_sevens)
            {
                top_sevens.add(p);
            }

            if(count_coins > most_coins)
            {
                top_coins.clear();
                top_coins.add(p);
            } else if(count_coins == most_coins)
            {
                top_coins.add(p);
            }

            if(count_cards > most_cards)
            {
                top_cards.clear();
                top_cards.add(p);
            } else if(count_cards == most_cards)
            {
                top_cards.add(p);
            }
        }

        if(top_sevens.size() == 1)
        {
            top_sevens.getFirst().score++;
        }

        if(top_coins.size() == 1)
        {
            top_coins.getFirst().score++;
        }

        if(top_cards.size() == 1)
        {
            top_cards.getFirst().score++;
        }
    }

    public void deal_to_board()
    {
        for(int i = 0; i < 4; ++i)
        {
            Card dealt_card = deck.pop();
            board_cards.add(dealt_card);
        }
    }

    public void deal_to_players()
    {
        // Deal cards out to players
        for (int i = 0; i < 3 && !deck.isEmpty(); ++i)
        {
            for(int p = 0; p < players.size() && !deck.isEmpty(); ++p)
            {
                Card dealt_card = deck.pop();
                players.get(p).draw(dealt_card);
            }
        }
    }

    public void createDeck()
    {
        deck.clear();
        LinkedList<Card> unshuffled = new LinkedList<>();

        // Add Card to Unshuffled Deck
        for (int i = Card.MIN_VALUE; i <= Card.MAX_VALUE; ++i)
        {
            for (int j = 0; j < Card.CardSuit.values().length; ++j)
            {
                unshuffled.add(
                        new Card(i, Card.CardSuit.values()[j])
                );
            }
        }
        System.out.println("Unshuffled Size: " + unshuffled.size());

        // Randomly remove from unshuffled and add to deck
        while (!unshuffled.isEmpty())
        {
            Card popped =
                    unshuffled.remove(random.nextInt(0, unshuffled.size()));

            deck.add(popped);
        }
    }

    public static int TARGET_SCORE = 15;
}
