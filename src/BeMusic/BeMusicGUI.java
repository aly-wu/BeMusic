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

package BeMusic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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


    /**
     * Creates a new window object and displays it on the screen.
     */
    public BeMusicGUI() {
        JFrame frame = new JFrame("BeMusic");
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        canvas = new TimelineCanvas();
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}




class TimelineCanvas extends JComponent /*implements KeyListener, MouseListener*/ {

    //CONSTANTS
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 700;


    public TimelineCanvas() {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        setFocusable(true);
        requestFocusInWindow();
    }


}