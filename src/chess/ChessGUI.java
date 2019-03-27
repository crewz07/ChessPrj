/******************************************************************
 * A class to build the GUI of chess
 *
 * @author Andrew Kruse
 * @author Justin Walter
 * @author Ian Wilkewitz
 *
 * @version 3/26/2019
 *****************************************************************/

package chess;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ChessGUI {

    /******************************************************************
     * Main method to build and run the chess game
     * @param args - arguments
     *****************************************************************/
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessPanel panel = new ChessPanel();
        frame.getContentPane().add(panel);

        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(850, 700));
        frame.pack();
        frame.setVisible(true);
    }
}
