/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

import javax.swing.*;
import java.awt.Color;
/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
    private AimTrainer board;
    private JLabel clocktime;
    private JLabel currentscore;
    private JLabel topscore;
    private JLabel secondscore;
    private JLabel thirdscore;
    private TreeMap<Integer, String> highscores = new TreeMap<Integer, String>();
    // Game constants
    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 500;
    
    private boolean running;
    private boolean scoresaved;
    
    public GameCourt(JLabel clocktime, JLabel currentscore, JLabel topscore, JLabel secondscore,
            JLabel thirdscore, TreeMap<Integer, String> highscores) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        setFocusable(true);
        
        board = new AimTrainer();
        
        Timer clocktimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (running) {
                    clockdown();
                }
            }
        });
        
        
        clocktimer.start();


        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                
                // updates the model given the coordinates of the mouseclick
                board.playTurn(p.x / 100, p.y / 100);
                if (board.isGameover()) {
                    clocktime.setText("0");
                }
                currentscore.setText(Integer.toString(board.getScore()));
                repaint(); // repaints the game board
            }
        });

        this.clocktime = clocktime;
        this.currentscore = currentscore;
        this.topscore = topscore;
        this.secondscore = secondscore;
        this.thirdscore = thirdscore;
        this.highscores = highscores;
    }
    
    public void setDifficulty(String difficulty) {
        if (difficulty.equals("easy")) { 
            board.setEasy();
            reset();
        } else if (difficulty.equals("medium")) {
            board.setMedium();
            reset();
        } else if (difficulty.equals("hard")) { 
            board.setHard();
            reset();
        }
    }
    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        board.reset();
        clocktime.setText("60");
        currentscore.setText("0");
        running = true; 
        scoresaved = false;
        repaint();
        
        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    
    void clockdown() {
        if (clocktime.getText().equals("0") && !scoresaved) {
            running = false; 
            board.gameover();
            clocktime.setText("0");
            String username = JOptionPane.showInputDialog("Please enter a nickname");
            highscores.put(board.getScore(), username);
            if (highscores.size() > 3) {
                highscores.pollFirstEntry();
                int tempcounter = 0;
                for (int key : highscores.keySet()) {
                    if (tempcounter == 0) {
                        thirdscore.setText(" 3. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                        tempcounter++;
                    } else if (tempcounter == 1) {
                        secondscore.setText(" 2. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                        tempcounter++;
                    } else { 
                        topscore.setText(" 1. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                    }
                }
            } else if (highscores.size() == 3) {
                int tempcounter = 0;
                for (int key : highscores.keySet()) {
                    if (tempcounter == 0) {
                        thirdscore.setText(" 3. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                        tempcounter++;
                    } else if (tempcounter == 1) {
                        secondscore.setText(" 2. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                        tempcounter++;
                    } else { 
                        topscore.setText(" 1. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                    }
                } 
            } else if (highscores.size() == 2) {
                int tempcounter = 0;
                for (int key : highscores.keySet()) {
                    if (tempcounter == 0) {
                        secondscore.setText(" 2. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                        tempcounter++;
                    } else if (tempcounter == 1) {
                        topscore.setText(" 1. " + Integer.toString(key) + "-" +
                                highscores.get(key));
                        tempcounter++;
                    }
                }
            } else {
                topscore.setText(" 1. " + Integer.toString(board.getScore()) + "-" + username);
            }
            File scores = new File("files/scores.txt");
            try {
                scores.createNewFile();
            } catch (IOException e) { }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("files/scores.txt"));
                writer.write(topscore.getText().substring(4));
                writer.newLine();
                writer.write(secondscore.getText().substring(4));
                writer.newLine();
                writer.write(thirdscore.getText().substring(4));
                writer.close();
            } catch (IOException e) { }
            scoresaved = true;
        } else if (clocktime.getText().equals("0") && scoresaved) {
            running = false; 
            board.gameover();
            clocktime.setText("0");
        } else { 
            clocktime.setText(Integer.toString((Integer.parseInt(clocktime.getText()) - 1)));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draws board grid
        g.drawLine(100, 0, 100, 500);
        g.drawLine(200, 0, 200, 500);
        g.drawLine(300, 0, 300, 500);
        g.drawLine(400, 0, 400, 500);
        g.drawLine(500, 0, 500, 500);
        g.drawLine(600, 0, 600, 500);
        g.drawLine(0, 100, 700, 100);
        g.drawLine(0, 200, 700, 200);
        g.drawLine(0, 300, 700, 300);
        g.drawLine(0, 400, 700, 400);
        
        // Draws X's and O's
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                int state = board.getCell(j, i);
                if (state == 1) {
                    g.setColor(Color.green);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 2) {
                    g.setColor(Color.red);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 3) {
                    g.setColor(Color.yellow);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 4) { 
                    g.setColor(Color.GRAY);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 5) { 
                    g.setColor(Color.cyan);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 6) { 
                    g.setColor(Color.black);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 7) { 
                    g.setColor(Color.pink);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}