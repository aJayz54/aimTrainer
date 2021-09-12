/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.
        TreeMap<Integer, String> highscores = new TreeMap<Integer, String>();
        String filePath = "files/scores.txt";
        BufferedReader reader; 
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) { 
                String[] list = line.split("-");
                highscores.put(Integer.parseInt(list[0]), list[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {  }
        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Aim Trainer");
        frame.setLocation(300, 300);
        
        final JPanel clock_panel = new JPanel();
        frame.add(clock_panel, BorderLayout.NORTH);
        final JLabel clocktime = new JLabel("60");
        clock_panel.add(clocktime);
        final JLabel timeremaining = new JLabel("seconds remaining!");
        clock_panel.add(timeremaining);
        
        final JLabel scoretext = new JLabel("          Your current score is: ");
        clock_panel.add(scoretext);
        final JLabel currentscore = new JLabel("0");
        clock_panel.add(currentscore);
        
        final JPanel scores = new JPanel();
        frame.add(scores, BorderLayout.WEST);
        scores.setLayout(new BoxLayout(scores, BoxLayout.Y_AXIS));
        
        final JLabel title = new JLabel("      High Scores!       ");
        scores.add(title);
        scores.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        final JLabel one = new JLabel(" 1. ");
        final JLabel two = new JLabel(" 2. ");
        final JLabel three = new JLabel(" 3. ");
        if (highscores.size() == 3) {
            int tempcounter = 0;
            for (int key : highscores.keySet()) {
                if (tempcounter == 0) {
                    three.setText(" 3. " + Integer.toString(key) + "-" +
                            highscores.get(key));
                    tempcounter++;
                } else if (tempcounter == 1) {
                    two.setText(" 2. " + Integer.toString(key) + "-" +
                            highscores.get(key));
                    tempcounter++;
                } else { 
                    one.setText(" 1. " + Integer.toString(key) + "-" +
                            highscores.get(key));
                }
            }
        } else if (highscores.size() == 2) {
            int tempcounter = 0;
            for (int key : highscores.keySet()) {
                if (tempcounter == 0) {
                    two.setText(" 2. " + Integer.toString(key) + "-" +
                            highscores.get(key));
                    tempcounter++;
                } else if (tempcounter == 1) {
                    one.setText(" 1. " + Integer.toString(key) + "-" +
                            highscores.get(key));
                    tempcounter++;
                }
            }
        } else if (highscores.size() == 1) {
            one.setText(" 1. " + Integer.toString(highscores.lastKey()) + "-" + 
                    highscores.get(highscores.lastKey()));
        }
        scores.add(one);
        scores.add(two);
        scores.add(three);
        
        // Main playing area
        final GameCourt court = new GameCourt(clocktime, currentscore, one, two, three, highscores);
        frame.add(court, BorderLayout.CENTER); 

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);
        
        final JPanel legend = new JPanel();
        frame.add(legend, BorderLayout.EAST);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        
        final JLabel greendot = new JLabel("  Green = 10 points!  ");
        final JLabel reddot = new JLabel("  Red = -10 points!  ");
        final JLabel golddot = new JLabel("  Yellow = 20 points!  ");
        final JLabel pinkdot = new JLabel("  Pink turns all orbs green!  ");
        final JLabel greydot = new JLabel("  Grey will split into 8 green dots!  ");
        final JLabel cyandot = new JLabel("  Cyan will flip the previous scored dot!  ");
        final JLabel blackdot = new JLabel("  Black will end your game.  ");
        
        legend.add(greendot);
        legend.add(reddot);
        legend.add(golddot);
        legend.add(pinkdot);
        legend.add(greydot);
        legend.add(cyandot);
        legend.add(blackdot);
        legend.setAlignmentX(Component.CENTER_ALIGNMENT);
        legend.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);
        
        final JButton easy = new JButton("Easy"); 
        easy.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                court.setDifficulty("easy");
            }
        });
        
        final JButton medium = new JButton("Medium"); 
        medium.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                court.setDifficulty("medium");
            }
        });
        
        final JButton hard = new JButton("Hard"); 
        hard.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                court.setDifficulty("hard");
            }
        });
       
        
        control_panel.add(easy); 
        control_panel.add(medium);
        control_panel.add(hard);
        
        final JButton instructions = new JButton("Instructions"); 
        instructions.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, 
                        "Welcome to Aim Trainer! At its core, this game is super simple: \n"
                        + "you just have to click the dots as they appear within the time limit \n"
                        + "of sixty seconds, which you can see at the top. The game is split \n"
                        + "into three different difficulties: Easy, Medium, and Hard. In Easy\n"
                        + "mode, you'll only encounter 4 types of dots: Green dots are dots \n"
                        + "that you can click for 10 points, red dots are dots that you should \n"
                        + "avoid (you'll lose 10 points!), yellow dots are worth DOUBLE (for 20 \n"
                        + "points), and the pink dots are dots that will turn all dots on the \n"
                        + "screen into green dots. Medium mode adds on two types of dots: \n"
                        + "Grey dots split into 8 separate green dots surrounding the original \n"
                        + "spot, and cyan dots flip the score value of the previously scored dot\n"
                        + "(so if you last clicked a red dot, instead of losing ten points, \n"
                        + "you'll get ten points). Finally, hard mode adds the black dot: If you\n"
                        + "click this dot, it's an instant gameover. All of these colors will be \n"
                        + "shown on the side of the screen! Have fun!");
            }
        });
        
        control_panel.add(instructions);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}