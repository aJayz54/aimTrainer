import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {

    @Test
    public void emptyBoard() {
        AimTrainer board = new AimTrainer();
        assertFalse(board.playTurn(0, 0));
    }
    
    @Test
    public void gamesOver() { 
        AimTrainer board = new AimTrainer();
        board.gameover();
        assertFalse(board.playTurn(0, 0));
    }
    
    @Test
    public void hit() { 
        AimTrainer board = new AimTrainer(); 
        int counter = 0; 
        while (counter < 34) { 
            board.spawn();
            counter++;
        }
        assertTrue(board.playTurn(0, 0));
    }
    
    @Test 
    public void testDifficulty() { 
        AimTrainer board = new AimTrainer(); 
        board.setEasy(); 
        int counter = 0;
        while (counter < 34) {
            board.spawn(); 
            counter++;
        }
        for (int i = 0; i < 5; i++) { 
            for (int j = 0; j < 7; j++) { 
                assertTrue(board.getCell(i, j) == 7 || board.getCell(i,  j) < 4);
            }
        }
    }

}
