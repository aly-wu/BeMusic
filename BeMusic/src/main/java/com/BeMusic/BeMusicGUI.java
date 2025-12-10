/*
 * File: BeMusicGUI
 * ------------------------
 * This file implements the BeMusicGUI class, which manages the
 * graphical display.
 * 
 * 
 * feed: song name, artist, which friend, what day
 * scrolls until certain cutoff date
 * 
 * your profile page: 
 * your own history (calendar?) , monthly !!! maybe a fun fact! top artist
 * and your rating
 */

package com.BeMusic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class implements graphics.
 */
public class BeMusicGUI {

    //instance variables
        private TimelineCanvas canvas;
        private BeMusicUser user;


    /**
     * Creates a new window object and displays it on the screen.
     */
    public BeMusicGUI(BeMusicUser user) {
        JFrame frame = new JFrame("BeMusic");
        this.user = user;
        frame.setBackground(Color.RED);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        canvas = new TimelineCanvas();
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}




class TimelineCanvas extends JComponent /*implements KeyListener, MouseListener*/ {
//==wordle canvas
    //CONSTANTS
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 700;
    public static final String username = "test";


    public TimelineCanvas() {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        setFocusable(true); 
        requestFocusInWindow();
    }

/**
 * Repaints the timline canvas.  This method is called automatically when
 * the window contents are repainted.
 *
 * @param g The Graphics object on which the painting occurs
 */

    @Override
    public void paintComponent(Graphics g) {
        /*
        for (int row = 0; row < N_ROWS; row++) {
            for (int col = 0; col < N_COLS; col++) {
                grid[row][col].paint(g);
            }
        }
        for (String label : keys.keySet()) {
            keys.get(label).paint(g);
        }
        g.setColor(Color.BLACK);
        g.setFont(Font.decode(USERNAME_FONT));
        FontMetrics fm = g.getFontMetrics();
        int tx = (CANVAS_WIDTH - fm.stringWidth(username)) / 2;
        g.drawString(username, tx, MESSAGE_Y); 
        
         */
    }



}