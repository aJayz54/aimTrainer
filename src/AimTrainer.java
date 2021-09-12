import java.util.LinkedList;
import java.util.Random;

public class AimTrainer {
    private int [][] board;
    private boolean gameover; 
    private int counter = 0; 
    private static LinkedList<Integer> score = new LinkedList<Integer>();
    private int badcounter = 0;
    private int totalcounter = 0;
    private int difficulty = 0;
    
    public AimTrainer() {
        reset();
    }
    
    public boolean playTurn(int c, int r) {
        if (board[r][c] == 0 || gameover) {
            return false;
        } else if (board[r][c] == 1) {
            board[r][c] = 0;
            score.addLast(10);
            totalcounter--;
            if (totalcounter < 3) {
                spawn();
            }
        } else if (board[r][c] == 2) {
            board[r][c] = 0;
            score.addLast(-10);
            badcounter--;
            totalcounter--; 
            if (totalcounter < 3) { 
                spawn();
            }
        } else if (board[r][c] == 3) {
            board[r][c] = 0; 
            score.addLast(20); 
            totalcounter--; 
            if (totalcounter < 3) {
                spawn();
            }
        } else if (board[r][c] == 4) {
            totalcounter--;
            int minx = r - 1; 
            int maxx = r + 2; 
            int miny = c - 1; 
            int maxy = c + 2;
            if (minx < 0) { 
                minx = 0; 
            } 
            if (maxx > 5) { 
                maxx = 5;
            }
            if (miny < 0) { 
                miny = 0; 
            }
            if (maxy > 7) { 
                maxy = 7; 
            }
            for (int i = minx; i < maxx; i++) { 
                for (int j = miny; j < maxy; j++) { 
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        totalcounter++;
                    }
                }
            }
            board[r][c] = 0; 
        } else if (board[r][c] == 5) {
            board[r][c] = 0; 
            if (score.size() > 0) {
                score.addLast(score.removeLast() * -1);
            }
            totalcounter--; 
            if (totalcounter < 3) {
                spawn();
            }
        } else if (board[r][c] == 6) {
            board[r][c] = 0; 
            gameover = true;
        } else if (board[r][c] == 7) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 7; j++) {
                    if (board[i][j] != 0 && board[i][j] != 3) {
                        board[i][j] = 1;
                    }
                }
            }
            badcounter = 0;
        }
        return true;
    }
    
    public int getCell(int c, int r) {
        return board[r][c];
    }
    
    public void spawn() {
        Random r = new Random(); 
        counter = 0; 
        while (counter == 0) {
            int xcoord = r.nextInt(5);
            int ycoord = r.nextInt(7);
            int type = 0;
            if (difficulty == 0) {
                type = r.nextInt(2);
            } else if (difficulty == 1) {
                type = r.nextInt(5);
            } else {
                type = r.nextInt(6);
            }
            if (board[xcoord][ycoord] == 0) {
                if (badcounter != 2) {
                    board[xcoord][ycoord] = type + 1;
                    if (type == 1 || type == 5) { 
                        badcounter++; 
                    }
                    counter++;
                } else {
                    board[xcoord][ycoord] = 7;
                    counter++;
                }
            }
        }
        totalcounter++;
    }
    
    public void reset() {
        board = new int[5][7];
        gameover = false;
        badcounter = 0;
        totalcounter = 0; 
        score.clear();
        spawn();
        spawn();
        spawn();
    }
    
    public int getScore() { 
        int tempscore = 0; 
        for (int x : score) {
            tempscore += x;
        }
        return tempscore;
    }
    public void gameover() { 
        gameover = true;
    }
    
    public boolean isGameover() { 
        return gameover;
    }
    
    public void setEasy() { 
        difficulty = 0;
    }
    
    public void setMedium() { 
        difficulty = 1;
    }
    
    public void setHard() { 
        difficulty = 2;
    }
}
