package chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessPanel extends JPanel {

    private JButton[][] board;
    private ChessModel model;

    //JButton objects for castling
    private JButton wCastleLeft = new JButton();
    private JButton wCastleRight = new JButton();
    private JButton bCastleLeft = new JButton();
    private JButton bCastleRight = new JButton();

    //Undo JButton
    private JButton undoButton = new JButton();

    //ImageIcon objects for white pieces
    private ImageIcon wRook;
    private ImageIcon wBishop;
    private ImageIcon wQueen;
    private ImageIcon wKing;
    private ImageIcon wPawn;
    private ImageIcon wKnight;

    //ImageIcon objects for black pieces
    private ImageIcon bRook;
    private ImageIcon bBishop;
    private ImageIcon bQueen;
    private ImageIcon bKing;
    private ImageIcon bPawn;
    private ImageIcon bKnight;

    //Used to move icons
    private boolean firstClickFlag;
    private int fromRow;
    private int toRow;
    private int fromCol;
    private int toCol;

    private listener listener;

    /******************************************************************
     * Default constructor for the ChessPanel
     *****************************************************************/
    public ChessPanel() {
        model = new ChessModel();
        board = new JButton[model.numRows()][model.numColumns()];
        listener = new listener();
        createIcons();
        setLayout(new GridBagLayout());

        JPanel boardpanel = new JPanel();
        boardpanel.setLayout(new GridLayout(model.numRows(), model.numColumns(), 1, 1));

        for (int r = 0; r < model.numRows(); r++) {
            for (int c = 0; c < model.numColumns(); c++) {
                if (model.pieceAt(r, c) == null) {
                    board[r][c] = new JButton("", null);
                    board[r][c].addActionListener(listener);
                } else if (model.pieceAt(r, c).player() == Player.WHITE) {
                    placeWhitePieces(r, c);
                } else {
                    placeBlackPieces(r, c);
                }

                setBackGroundColor(r, c);
                boardpanel.add(board[r][c]);
            }
        }

        // set the preferred size of the castling buttons
        wCastleRight.setPreferredSize(new Dimension(25, 25));
        bCastleLeft.setPreferredSize(new Dimension(25, 25));
        wCastleLeft.setPreferredSize(new Dimension(25, 25));
        bCastleRight.setPreferredSize(new Dimension(25, 25));

        // create the constraints object for the gridbaglayout
        GridBagConstraints gbc = new GridBagConstraints();

        // position and set the size of the boardpanel
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(boardpanel, gbc);
        boardpanel.setPreferredSize(new Dimension(600, 600));

        // position the right castling buttons
        gbc.insets = new Insets(25, 25, 25, 25);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(wCastleRight, gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        add(bCastleRight, gbc);

        //add undoButton
        add(undoButton);
        undoButton.setText("Undo");
        undoButton.addActionListener(listener);

        // position the left castling buttons
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(wCastleLeft, gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        add(bCastleLeft, gbc);

        // add action listeners to the castling buttons
        wCastleRight.addActionListener(listener);
        wCastleLeft.addActionListener(listener);
        bCastleRight.addActionListener(listener);
        bCastleLeft.addActionListener(listener);

        // disable the black castling buttons
        bCastleRight.setEnabled(false);
        bCastleLeft.setEnabled(false);
        wCastleLeft.setEnabled(false);
        wCastleRight.setEnabled(false);

        firstClickFlag = true;
    }

    /******************************************************************
     * Colors alternating spaces of the chess board
     * @param r - row of a board space to color
     * @param c - column of a board space to color
     *****************************************************************/
    private void setBackGroundColor(int r, int c) {
        if ((c % 2 == 1 && r % 2 == 0) || (c % 2 == 0 && r % 2 == 1)) {
            board[r][c].setBackground(Color.LIGHT_GRAY);
        } else if ((c % 2 == 0 && r % 2 == 0) || (c % 2 == 1 && r % 2 == 1)) {
            board[r][c].setBackground(Color.WHITE);
        }
    }

    /******************************************************************
     * Places the white pieces on the board and their buttons
     * @param r - row of a board space to color
     * @param c - column of a board space to color
     *****************************************************************/
    private void placeWhitePieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, wPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, wRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, wKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, wBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, wQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, wKing);
            board[r][c].addActionListener(listener);
        }
    }

    /******************************************************************
     * Places the black pieces on the board and their buttons
     * @param r - row of a board space to color
     * @param c - column of a board space to color
     *****************************************************************/
    private void placeBlackPieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, bPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, bRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, bKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, bBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, bQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, bKing);
            board[r][c].addActionListener(listener);
        }
    }

    /******************************************************************
     * Sets the images for the corresponding white and black pieces
     *****************************************************************/
    private void createIcons() {
        // Sets the Image for white player pieces
        String path = "src/chess/";
        wRook = new ImageIcon(path + "wRook.png");
        wBishop = new ImageIcon(path + "wBishop.png");
        wQueen = new ImageIcon(path + "wQueen.png");
        wKing = new ImageIcon(path + "wKing.png");
        wPawn = new ImageIcon(path + "wPawn.png");
        wKnight = new ImageIcon(path + "wKnight.png");
        bRook = new ImageIcon(path + "bRook.png");
        bBishop = new ImageIcon(path + "bBishop.png");
        bQueen = new ImageIcon(path + "bQueen.png");
        bKing = new ImageIcon(path + "bKing.png");
        bPawn = new ImageIcon(path + "bPawn.png");
        bKnight = new ImageIcon(path + "bKnight.png");
    }


    /******************************************************************
     * Method that updates and displays the game board
     *****************************************************************/
    private void displayBoard() {
        // determine which castling buttons will be active according to the board state and current player
        //need to add that if there are pieces in the way, you can just query model class, im already doing that

        //0 = lw, 1 = rw, 2 = lb, 3 = rw
        boolean[] castleEnable = model.castleEnable();
        wCastleLeft.setEnabled(castleEnable[0]);
        wCastleRight.setEnabled(castleEnable[1]);
        bCastleLeft.setEnabled(castleEnable[2]);
        bCastleRight.setEnabled(castleEnable[3]);


        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++)
                if (model.pieceAt(r, c) == null)
                    board[r][c].setIcon(null);
                else if (model.pieceAt(r, c).player() == Player.WHITE) {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(wPawn);

                    if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(wRook);

                    if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(wKnight);

                    if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(wBishop);

                    if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(wQueen);

                    if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(wKing);

                }
                else if (model.pieceAt(r, c).player() == Player.BLACK) {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(bPawn);

                    if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(bRook);

                    if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(bKnight);

                    if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(bBishop);

                    if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(bQueen);

                    if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(bKing);
                }
        }
        repaint();
    }

    // inner class that represents action listener for buttons
    private class listener implements ActionListener {

        /******************************************************************
         * Method that listens for button presses and updates the game board
         * as needed
         *****************************************************************/
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == wCastleRight) {
                model.moveCastle(false);
                displayBoard();
                model.AI();
                displayBoard();
            }
            else if(event.getSource() == wCastleLeft) {
                model.moveCastle(true);
                displayBoard();
                model.AI();
                displayBoard();
            }
            else if(event.getSource() == bCastleRight) {
                model.moveCastle(false);
                displayBoard();
            }
            else if(event.getSource() == bCastleLeft) {
                model.moveCastle(true);
                displayBoard();
            }
            else if(event.getSource() == undoButton) {
                // if the player is undoing a castling move, undo an extra time, and set the player back to white
                // for the second undo
                if(model.moveList.size() >= 2) {
                    if (model.moveList.get(0).pieceMoved.player() == model.moveList.get(1).pieceMoved.player()) {
                        model.undo();
                        model.setNextPlayer();
                    }
                }
                model.undo();
                displayBoard();
            }
            else {
                for (int r = 0; r < model.numRows(); r++)
                    for (int c = 0; c < model.numColumns(); c++)
                        if (board[r][c] == event.getSource()) {
                            // if it's the first click...
                            if (firstClickFlag) {
                                // ...and if there's a piece at (r, c)...
                                if (model.pieceAt(r, c) != null) {
                                    // ...and if that piece belongs to the current player...
                                    if (model.pieceAt(r, c).player() == model.currentPlayer()) {
                                        board[r][c].setBackground(Color.PINK);
                                        fromRow = r;
                                        fromCol = c;
                                        firstClickFlag = false;
                                    }
                                }
                            } else {
                                // set the background color of the square selected on the first click back to its normal color
                                setBackGroundColor(fromRow, fromCol);

                                toRow = r;
                                toCol = c;
                                firstClickFlag = true;
                                Move m = new Move(fromRow, fromCol, toRow, toCol);
                                // if the move to (r, c) is valid...
                                if ((model.isValidMove(m))) {
                                    // ...move that piece to (r, c) in the model
                                    model.move(m);
                                    displayBoard();
                                    model.AI();
                                    displayBoard();
                                }
                            }

                        }
            }
        }
    }
}
