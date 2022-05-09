import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyServerTest {
    MorraInfo c1;
    MorraInfo c2;
    MorraInfo game;

    @BeforeEach
    void init() {
        c1 = new MorraInfo();
        c2 = new MorraInfo();

        c1.isPlayerOne = true;
    }

    @Test
    void winnerOne() {
        // Test where P1 wins

        c1.p1Num = 3;
        c1.p1Guess = 7;

        c2.p2Num = 4;
        c2.p2Guess = 8;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p1Num, game.p1Num);
        assertEquals(c1.p1Guess, game.p1Guess);
        assertEquals(c2.p2Num, game.p2Num);
        assertEquals(c2.p2Guess, game.p2Guess);
        assertEquals(1, game.p1Points);
        assertEquals(0, game.p2Points);

        c1.p1Num = 1;
        c1.p1Guess = 6;
        c1.p1Points = 1;

        c2.p2Num = 5;
        c2.p2Guess = 7;
        c2.p1Points = 1;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p1Num, game.p1Num);
        assertEquals(c1.p1Guess, game.p1Guess);
        assertEquals(c2.p2Num, game.p2Num);
        assertEquals(c2.p2Guess, game.p2Guess);
        assertEquals(2, game.p1Points);
        assertEquals(0, game.p2Points);
    }

    @Test
    void winnerTwo() {
        // Test where P2 wins

        c1.p1Num = 2;
        c1.p1Guess = 6;

        c2.p2Num = 3;
        c2.p2Guess = 5;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p1Num, game.p1Num);
        assertEquals(c1.p1Guess, game.p1Guess);
        assertEquals(c2.p2Num, game.p2Num);
        assertEquals(c2.p2Guess, game.p2Guess);
        assertEquals(0, game.p1Points);
        assertEquals(1, game.p2Points);

        c1.p1Num = 4;
        c1.p1Guess = 7;
        c1.p2Points = 1;

        c2.p2Num = 5;
        c2.p2Guess = 9;
        c2.p2Points = 1;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p1Num, game.p1Num);
        assertEquals(c1.p1Guess, game.p1Guess);
        assertEquals(c2.p2Num, game.p2Num);
        assertEquals(c2.p2Guess, game.p2Guess);
        assertEquals(0, game.p1Points);
        assertEquals(2, game.p2Points);
    }

    @Test
    void winnerThree() {
        // Test where neither wins

        c1.p1Num = 1;
        c1.p1Guess = 2;

        c2.p2Num = 5;
        c2.p2Guess = 7;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p1Num, game.p1Num);
        assertEquals(c1.p1Guess, game.p1Guess);
        assertEquals(c2.p2Num, game.p2Num);
        assertEquals(c2.p2Guess, game.p2Guess);
        assertEquals(0, game.p1Points);
        assertEquals(0, game.p2Points);

        c1.p1Num = 2;
        c1.p1Guess = 5;

        c2.p2Num = 3;
        c2.p2Guess = 5;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p1Num, game.p1Num);
        assertEquals(c1.p1Guess, game.p1Guess);
        assertEquals(c2.p2Num, game.p2Num);
        assertEquals(c2.p2Guess, game.p2Guess);
        assertEquals(0, game.p1Points);
        assertEquals(0, game.p2Points);
    }

    @Test
    void winnerFour() {
        // Switch p1 and p2
        c2.isPlayerOne = true;
        c1.isPlayerOne = false;

        c1.p2Num = 2;
        c1.p2Guess = 5;

        c2.p1Num = 2;
        c2.p1Guess = 4;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p2Num, game.p2Num);
        assertEquals(c1.p2Guess, game.p2Guess);
        assertEquals(c2.p1Num, game.p1Num);
        assertEquals(c2.p1Guess, game.p1Guess);
        assertEquals(1, game.p1Points);
        assertEquals(0, game.p2Points);

        c1.p2Num = 5;
        c1.p2Guess = 8;
        c1.p1Points = 1;

        c2.p1Num = 3;
        c2.p1Guess = 7;
        c2.p1Points = 1;

        game = Server.calcWinner(c1, c2);

        assertEquals(c1.p2Num, game.p2Num);
        assertEquals(c1.p2Guess, game.p2Guess);
        assertEquals(c2.p1Num, game.p1Num);
        assertEquals(c2.p1Guess, game.p1Guess);
        assertEquals(1, game.p1Points);
        assertEquals(1, game.p2Points);
    }
}
