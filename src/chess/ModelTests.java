package chess;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ModelTests {
    public ChessModel chess = new ChessModel();

    @Test
    public void moveKing(){
        //set piece in front of king to null
        chess.setPiece(6,4,null);
        Move king = new Move(7,4,6,4);
        chess.move(king);
        Assert.assertTrue("White King should have moved",chess.wKingMoved);
    }

    @Test
    public void whiteKingMoveCastleDisabled(){
        boolean[] buttons = chess.castleEnable();
        Assert.assertFalse("King has moved," +
                " castle should not be enabled",buttons[0]
                );
        Assert.assertFalse("King has moved," +
                "white castle should not be enabled",buttons[1]
        );

    }

    @Test
    public void countKings(){
        ArrayList<int[]> kingPositions = chess.findKing();
        Assert.assertEquals("There should be two kings on board",
                2,kingPositions.size());
    }

    @Test
    public void checkKingPositions(){
        ArrayList<int[]> formattedKingPositions = chess.findKing();
        int[] blackKing = formattedKingPositions.get(0);
        Assert.assertEquals("First King should be black",
                Player.BLACK,
                chess.pieceAt(blackKing[0],blackKing[1]).player());
    }

    @Test
    public void checkInCheck(){

        //create new board
        this.setUpBoard();

        //move king to in front of pawns
        Move whiteKing = new Move(7,4,5,4);
        Move blackQueen = new Move(0,3,3,4);

        chess.move(whiteKing);
        chess.move(blackQueen);
        Assert.assertTrue("White king should be in check",
                chess.inCheck(chess.currentPlayer()));
    }

    @Test
    public void checkNotInCheck(){
        this.setUpBoard();
        Assert.assertFalse("New board, no one should be in check",
                chess.inCheck(chess.currentPlayer()));
    }

    @Test
    public void moveKingForwardAndBack(){
        checkInCheck();

        //move whiteKingForward one, still in check
        Move whiteKing = new Move(5,4,4,4);
        IChessPiece king = chess.pieceAt(5,4);
        chess.move(whiteKing);
        Assert.assertEquals("White King should not have moved",
                 king,chess.pieceAt(5,4));
    }

    @Test
    public void resetKingOnFirstMove(){
        setUpBoard();
        Move whiteKing = new Move(7,4,5,4);
        chess.move(whiteKing);

        Assert.assertTrue("King variable holding if king" +
                "has moved should be true",chess.wKingMoved);
        chess.undo();

        Assert.assertFalse("King variable holding if king" +
                " has moved should be false",chess.wKingMoved);
    }

    public void setUpWhiteQueenSideCastle(){
        this.setUpBoard();
        chess.setPiece(7,1,null);
        chess.setPiece(7,2,null);
        chess.setPiece(7,3,null);
    }

    public void setUpWhiteKingSideCastle(){
        setUpBoard();
        chess.setPiece(7,6,null);
        chess.setPiece(7,5,null);
    }

    @Test
    public void testWhiteKingSideCastle(){
        setUpWhiteKingSideCastle();
        IChessPiece rwrook = chess.pieceAt(7,7);
        IChessPiece king = chess.pieceAt(7,4);

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                rwrook instanceof Rook);

        chess.moveCastle(false);

        Assert.assertEquals("King should be at 7,6",
                king,chess.pieceAt(7,6));
        Assert.assertEquals("Left White Rook should be at 7,5",
                rwrook,chess.pieceAt(7,5));

    }

    @Test
    public void testWhiteKingSideCastleCheck(){
        setUpWhiteKingSideCastle();
        IChessPiece rwrook = chess.pieceAt(7,7);
        IChessPiece king = chess.pieceAt(7,4);

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                rwrook instanceof Rook);

        //move queen right above king
        Move blackQueen = new Move(0,3,6,6);
        chess.move(blackQueen);

        //make sure only white piece is being tested
        if(chess.currentPlayer() == Player.BLACK){
            chess.setNextPlayer();
        }
        chess.moveCastle(false);
        Assert.assertEquals("King should not have moved, 7,4",
                king,chess.pieceAt(7,4));
        Assert.assertEquals("Left White Rook should not have moved" +
                        "7,7",
                rwrook,chess.pieceAt(7,7));
    }

    public void setUpBlackKingSideCastle(){
        setUpBoard();
        chess.setPiece(0,6,null);
        chess.setPiece(0,5,null);
    }

    @Test
    public void kingSideBlackCastle(){
        setUpBlackQueenSideCastle();
        IChessPiece rbRook = chess.pieceAt(0,7);
        IChessPiece king = chess.pieceAt(0,4);
        if(chess.currentPlayer() == Player.WHITE){
            chess.setNextPlayer();
        }

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                rbRook instanceof Rook);

        chess.moveCastle(false);

        Assert.assertEquals("King should be at 0,6",
                king,chess.pieceAt(0,6));
        Assert.assertEquals("Left White Rook should be at 0,5",
                rbRook,chess.pieceAt(0,5));
    }

    @Test
    public void testBlackKingSideCastleCheck(){
        setUpBlackKingSideCastle();
        IChessPiece rBrook = chess.pieceAt(0,7);
        IChessPiece king = chess.pieceAt(0,4);

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                rBrook instanceof Rook);

        //move queen right above king
        Move whiteQueen = new Move(7,3,1,5);
        chess.move(whiteQueen);

        //make sure only white piece is being tested
        if(chess.currentPlayer() == Player.WHITE){
            chess.setNextPlayer();
        }
        chess.moveCastle(false);
        Assert.assertEquals("King should not have moved, 0,4",
                king,chess.pieceAt(0,4));
        Assert.assertEquals("Left Black Rook should not have moved" +
                        "0,7",
                rBrook,chess.pieceAt(0,7));

    }


    public void setUpBlackQueenSideCastle(){
        this.setUpBoard();
        chess.setPiece(0,1,null);
        chess.setPiece(0,2,null);
        chess.setPiece(0,3,null);

    }

    @Test
    public void testBlackQueenSideCastle(){
        setUpBlackQueenSideCastle();
        IChessPiece lbRook = chess.pieceAt(0,0);
        IChessPiece king = chess.pieceAt(0,4);
        if(chess.currentPlayer() == Player.WHITE){
            chess.setNextPlayer();
        }

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                lbRook instanceof Rook);

        chess.moveCastle(true);

        Assert.assertEquals("King should be at 7,1",
                king,chess.pieceAt(0,2));
        Assert.assertEquals("Left White Rook should be at 7,2",
                lbRook,chess.pieceAt(0,3));
    }



    @Test
    public void testBlackQueenSideCastleCheck(){
        setUpBlackQueenSideCastle();
        IChessPiece lwrook = chess.pieceAt(0,0);
        IChessPiece king = chess.pieceAt(0,4);

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                lwrook instanceof Rook);

        //move queen right above king
        Move whiteQueen = new Move(7,3,1,2);
        chess.move(whiteQueen);

        //make sure only white piece is being tested
        if(chess.currentPlayer() == Player.WHITE){
            chess.setNextPlayer();
        }
        chess.moveCastle(true);
        Assert.assertEquals("King should not have moved, 0,4",
                king,chess.pieceAt(0,4));
        Assert.assertEquals("Left Black Rook should not have moved" +
                        "0,0",
                lwrook,chess.pieceAt(0,0));
    }

    @Test
    public void testWhiteQueenSideCastle(){
        setUpWhiteQueenSideCastle();
        IChessPiece lwrook = chess.pieceAt(7,0);
        IChessPiece king = chess.pieceAt(7,4);

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                lwrook instanceof Rook);

        chess.moveCastle(true);

        Assert.assertEquals("King should be at 7,1",
                king,chess.pieceAt(7,2));
        Assert.assertEquals("Left White Rook should be at 7,2",
                lwrook,chess.pieceAt(7,3));

    }

    @Test
    public void testQueenSideWhiteCastleMoveIntoCheck(){
        setUpWhiteQueenSideCastle();
        IChessPiece lwrook = chess.pieceAt(7,0);
        IChessPiece king = chess.pieceAt(7,4);

        Assert.assertTrue("Chess piece should be a king",
                king instanceof King);
        Assert.assertTrue("Chess piece should be a rook",
                lwrook instanceof Rook);

        //move queen right above king
        Move blackQueen = new Move(0,3,6,2);
        chess.move(blackQueen);

        //make sure only white piece is being tested
        if(chess.currentPlayer() == Player.BLACK){
            chess.setNextPlayer();
        }
        chess.moveCastle(true);
        Assert.assertEquals("King should not have moved, 7,4",
                king,chess.pieceAt(7,4));
        Assert.assertEquals("Left White Rook should not have moved" +
                        "7,0",
                lwrook,chess.pieceAt(7,0));
    }
    /******************************************************************
     * Method used to reset global board
     *****************************************************************/
    public void setUpBoard(){
        this.chess = new ChessModel();
    }

    public void clearBoard() {
        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                if(chess.pieceAt(r, c) != null && !(chess.pieceAt(r, c) instanceof King)) {
                    chess.setPiece(r, c, null);
                }
            }
        }
    }

    @Test
    public void testIsComplete_notInCheck() {
        clearBoard();
        Assert.assertFalse("isComplete should return false.",chess.isComplete());
    }

    @Test
    public void testIsComplete_inCheckCanMoveOut() {
        clearBoard();
        chess.setPiece(7, 7, new Rook(Player.BLACK));
        Assert.assertTrue("White should be in check.", chess.inCheck(Player.WHITE));
        Assert.assertFalse("White should not be in checkmate", chess.isComplete());
    }

    @Test
    public void testIsComplete_inCheckmate() {
        clearBoard();
        chess.setPiece(7, 7, new Rook(Player.BLACK));
        chess.setPiece(6, 3, new Pawn(Player.WHITE));
        chess.setPiece(6, 4, new Pawn(Player.WHITE));
        chess.setPiece(6, 5, new Pawn(Player.WHITE));
        chess.setPiece(7, 3, new Pawn(Player.WHITE));
        Assert.assertTrue("White should be in checkmate", chess.isComplete());
    }

    @Test
    public void testIsComplete_inCheckCanTakePiece() {
        clearBoard();
        chess.setPiece(6, 7, new Rook(Player.WHITE));
        Assert.assertFalse("White should not be in checkmate.", chess.isComplete());
    }

    @Test
    public void testIsComplete_inCheckCanBlock() {
        clearBoard();
        chess.setPiece(6, 6, new Rook(Player.WHITE));
        Assert.assertFalse("White should not be in checkmate", chess.isComplete());
    }



}
