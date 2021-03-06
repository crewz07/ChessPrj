/******************************************************************
 * A class to test all of the functionality of the game of chess
 *
 * @author Andrew Kruse
 * @author Justin Walter
 * @author Ian Wilkewitz
 *
 * @version 3/26/2019
 *****************************************************************/

package chess;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ModelTests {
    public ChessModel chess = new ChessModel();

    //Test for king movement
    @Test
    public void moveKing(){
        //set piece in front of king to null
        chess.setPiece(6,4,null);
        Move king = new Move(7,4,6,4);
        chess.move(king);
        Assert.assertTrue("White King should move",chess.wKingMoved);
    }

    //Test for disabling the white king after it moves
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

    //Test the basic functionality of the kingPositions method
    @Test
    public void countKings(){
        ArrayList<int[]> kingPositions = chess.findKing();
        Assert.assertEquals("There should be two kings on board",
                2,kingPositions.size());
    }

    //Tests functionality of the formattedKingPositions ArrayList
    @Test
    public void checkKingPositions(){
        ArrayList<int[]> formattedKingPositions = chess.findKing();
        int[] blackKing = formattedKingPositions.get(0);
        Assert.assertEquals("First King should be black",
                Player.BLACK,
                chess.pieceAt(blackKing[0],blackKing[1]).player());
    }

    //Tests the check functionality
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

    //Tests to make sure king is not in check when it shouldn't be
    @Test
    public void checkNotInCheck(){
        this.setUpBoard();
        Assert.assertFalse("New board, no one should be in check",
                chess.inCheck(chess.currentPlayer()));
    }

    //Test to prevent the king from not moving out of check
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

    //Test if the white king has moved
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

    //Preparation for white queen side castling
    public void setUpWhiteQueenSideCastle(){
        this.setUpBoard();
        chess.setPiece(7,1,null);
        chess.setPiece(7,2,null);
        chess.setPiece(7,3,null);
    }
    //Preparation for white king side castling
    public void setUpWhiteKingSideCastle(){
        setUpBoard();
        chess.setPiece(7,6,null);
        chess.setPiece(7,5,null);
    }

    //Test for white king side castling
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

    //Test interactions with castling and the white king in check
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

    //Preparation for black king side castling
    public void setUpBlackKingSideCastle(){
        setUpBoard();
        chess.setPiece(0,6,null);
        chess.setPiece(0,5,null);
    }

    //Test for black king side castling
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

    //Testing interactions with the black king side castling and check
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

    //Preparation for black queen side castling
    public void setUpBlackQueenSideCastle(){
        this.setUpBoard();
        chess.setPiece(0,1,null);
        chess.setPiece(0,2,null);
        chess.setPiece(0,3,null);

    }

    //Testing for black queen side castling
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

    //Testing interaction with castling black queen side and check
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

    //Testing interactions with white queen side castling and check
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

    //Test queen side white castling into check
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

    //Used to clear the game board (except kings) for further tests
    public void clearBoard() {
        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                if(chess.pieceAt(r, c) != null && !(chess.pieceAt(r, c) instanceof King)) {
                    chess.setPiece(r, c, null);
                }
            }
        }
    }

    //Test that no one is in checkmate after clearing the board
    @Test
    public void testIsComplete_notInCheck() {
        clearBoard();
        Assert.assertFalse("isComplete should return false.",chess.isComplete());
    }

    //Testing that white is in check and can move out
    @Test
    public void testIsComplete_inCheckCanMoveOut() {
        clearBoard();
        chess.setPiece(7, 7, new Rook(Player.BLACK));
        Assert.assertTrue("White should be in check.", chess.inCheck(Player.WHITE));
        Assert.assertFalse("White should not be in checkmate", chess.isComplete());
    }

    //Test that white is in checkmate
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

    //Test that white can take a piece to avoid checkmate
    @Test
    public void testIsComplete_inCheckCanTakePiece() {
        clearBoard();
        chess.setPiece(6, 7, new Rook(Player.WHITE));
        Assert.assertFalse("White should not be in checkmate.", chess.isComplete());
    }

    //Test that white can avoid checkmate by moving a piece to block
    @Test
    public void testIsComplete_inCheckCanBlock() {
        clearBoard();
        chess.setPiece(6, 6, new Rook(Player.WHITE));
        Assert.assertFalse("White should not be in checkmate", chess.isComplete());
    }

    //Test getters
    @Test
    public void numOfRowNumOfCols(){
        Assert.assertEquals("Num of rows should be 8",
                8,chess.numRows());
    }
    @Test
    public void NumOfCols(){
        Assert.assertEquals("Num of rows should be 8",
                8,chess.numColumns());
    }

//    @Test
//    public void MoveListConstructor2(){
//        int moveCount = 12;
//        IChessPiece pieceMoved = new King(Player.BLACK);
//        move(0,0,)
//        ChessModel.MoveList move = new ChessModel.MoveList(moveCount,pieceMoved,move,enPassant)
//    }

    //Testing of the en passant functionality
    @Test
    public void testEnPassant() {
        clearBoard();
        chess.setPiece(3, 1, new Pawn(Player.WHITE));
        chess.setPiece(1, 2, new Pawn(Player.BLACK));
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.move(new Move(1, 2, 3, 2));
        Assert.assertTrue(chess.isValidMove(new Move(3, 1, 2, 2)));
        chess.move(new Move(3, 1, 2, 2));
        int count = 0;
        for(int r = 0; r < chess.numRows(); r++) {
            for(int c = 0; c < chess.numColumns(); c++) {
                if(chess.pieceAt(r, c) != null) {
                    count++;
                }
            }
        }
        Assert.assertEquals(3, count);
        Assert.assertNotNull(chess.pieceAt(2, 2));
    }

    //Testing undoing an en passant move
    @Test
    public void testUndo_enPassant() {
        testEnPassant();
        chess.undo();
        Assert.assertNotNull(chess.pieceAt(3, 1));
        Assert.assertNotNull(chess.pieceAt(3, 2));
    }

    //Testing AI and check functionality
    @Test
    public void testAI_inCheck() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(0, 0, new Rook(Player.WHITE));
        chess.AI();
        Assert.assertFalse(chess.inCheck(Player.BLACK));
        Assert.assertEquals(1, chess.moveList.size());
        Assert.assertEquals(1, chess.moveList.get(0).getMove()
        .toRow);
        Assert.assertTrue(Math.abs(chess.moveList.get(0)
        .getMove().toRow - chess.moveList.get(0).getMove().fromRow) <=
        1);
        Assert.assertTrue(chess.moveList.get(0)
        .getPieceMoved().type() == "King");
        Assert.assertTrue(chess.moveList.get(0)
        .getPieceMoved().player() == Player.BLACK);
    }

    //Testing AI can put us in check without sacrificing pieces
    @Test
    public void testAI_canPutEnemyInCheck_WithoutLosingPiece() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(0, 0, new Rook(Player.BLACK));
        chess.AI();
        Assert.assertTrue(chess.inCheck(Player.WHITE));
        Assert.assertEquals(1, chess.moveList.size());
    }

    //Testing AI can put us in check while losing pieces
    @Test
    public void testAI_canPutEnemyInCheck_ButWillLosePiece() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(0, 3, new Rook(Player.BLACK));
        chess.AI();
        Assert.assertFalse(chess.inCheck(Player.WHITE));
        Assert.assertEquals(1, chess.moveList.size());
    }

    //Testing AI will move a piece at risk of being taken
    @Test
    public void testAI_pieceInDanger_canMove() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(5, 7, new Pawn(Player.BLACK));
        chess.setPiece(6, 6, new Knight(Player.WHITE));
        chess.setPiece(2, 2, new Bishop(Player.BLACK));
        chess.setPiece(4, 2, new Rook(Player.WHITE));
        chess.setPiece(6, 3, new Pawn(Player.WHITE));
        chess.setPiece(6, 5, new Pawn(Player.WHITE));
        chess.AI();
        Assert.assertEquals(1, chess.moveList.size());
        Assert.assertEquals("Bishop", chess.moveList.get(0)
        .pieceMoved.type());
        Assert.assertEquals(Player.BLACK, chess.moveList.get(0)
        .pieceMoved.player());
    }

    //Testing AI has to defend a piece
    @Test
    public void testAI_pieceInDanger_hasToDefend() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(5, 1, new Pawn(Player.WHITE));
        chess.setPiece(3, 0, new Pawn(Player.BLACK));
        chess.setPiece(1, 1, new Pawn(Player.BLACK));
        chess.setPiece(3, 7, new Rook(Player.WHITE));
        chess.AI();
        Assert.assertEquals(1, chess.moveList.size());
        Assert.assertNull(chess.pieceAt(1, 1));
        Assert.assertEquals("Pawn", chess
        .pieceAt(2, 1).type());
        Assert.assertEquals(Player.BLACK, chess
        .pieceAt(2, 1).player());
    }

    //Testing AI stalemate
    @Test
    public void testAI_gameStalemated() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(0, 7, new King(Player.BLACK));
        chess.setPiece(0, 4, null);
        chess.setPiece(0, 6, new Pawn(Player.BLACK));
        chess.setPiece(0, 0, new Rook(Player.WHITE));
        chess.setPiece(1, 0, new Rook(Player.WHITE));
        chess.AI();
        Assert.assertFalse(chess.inCheck(Player.BLACK));
        Assert.assertNotNull(chess.pieceAt(0, 7));
        Assert.assertEquals(0, chess.moveList.size());
    }

    //Testing AI taking our pieces
    @Test
    public void testAI_canTakeEnemyPiece_withoutLosingPiece() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(1, 2, new Rook(Player.BLACK));
        chess.setPiece(3, 2, new Knight(Player.WHITE));
        chess.setPiece(6, 4, new Pawn(Player.WHITE));
        chess.AI();
        Assert.assertEquals(Player.BLACK, chess.
        pieceAt(3, 2).player());
        Assert.assertEquals("Rook", chess.
        pieceAt(3, 2).type());
        Assert.assertEquals(1, chess.moveList.size());
    }

    @Test
    public void testAI_canDefendByTakingEnemyPiece() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(2, 1, new Pawn(Player.BLACK));
        chess.setPiece(3, 1, new Pawn(Player.WHITE));
        chess.setPiece(4, 7, new Rook(Player.BLACK));
        chess.setPiece(7, 6, new Pawn(Player.WHITE));
        chess.setPiece(4, 0, new Knight(Player.WHITE));
        chess.setPiece(6, 4, new Pawn(Player.WHITE));
        chess.AI();
        Assert.assertEquals(Player.BLACK, chess.
        pieceAt(4, 0).player());
        Assert.assertEquals("Rook", chess.
        pieceAt(4, 0).type());
        Assert.assertEquals(1, chess.moveList.size());
    }

    @Test
    public void testAI_canDefendByTakingEnemyPiece_butWillLosePiece() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(2, 1, new Pawn(Player.BLACK));
        chess.setPiece(3, 1, new Pawn(Player.WHITE));
        chess.setPiece(4, 7, new Rook(Player.BLACK));
        chess.setPiece(7, 6, new Pawn(Player.WHITE));
        chess.setPiece(4, 0, new Knight(Player.WHITE));
        chess.setPiece(6, 4, new Pawn(Player.WHITE));
        chess.setPiece(5, 1, new Pawn(Player.WHITE));
        chess.AI();
        Assert.assertEquals(Player.WHITE, chess.
        pieceAt(4, 0).player());
        Assert.assertEquals("Knight", chess.
        pieceAt(4, 0).type());
        Assert.assertEquals(1, chess.moveList.size());
    }

    @Test
    public void testAI_canTakeEnemyPiece_butWillLosePiece() {
        clearBoard();
        if(chess.currentPlayer() != Player.BLACK) {
            chess.setNextPlayer();
        }
        chess.setPiece(3, 1, new Pawn(Player.WHITE));
        chess.setPiece(4, 7, new Rook(Player.BLACK));
        chess.setPiece(7, 6, new Pawn(Player.WHITE));
        chess.setPiece(4, 0, new Knight(Player.WHITE));
        chess.setPiece(6, 4, new Pawn(Player.WHITE));
        chess.setPiece(5, 1, new Pawn(Player.WHITE));
        chess.AI();
        Assert.assertEquals(Player.WHITE, chess.
                pieceAt(4, 0).player());
        Assert.assertEquals("Knight", chess.
                pieceAt(4, 0).type());
        Assert.assertEquals(1, chess.moveList.size());
    }
}
