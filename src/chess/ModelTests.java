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
        chess.setNextPlayer();
        chess.move(blackQueen);
        chess.setNextPlayer();
        Assert.assertTrue("White king should be in check",
                chess.inCheck(chess.currentPlayer()));
    }

    @Test
    public void checkNotInCheck(){
        this.setUpBoard();
        Assert.assertFalse("New board, no one should be in check",
                chess.inCheck(chess.currentPlayer()));
    }
    /******************************************************************
     * Method used to reset global board
     *****************************************************************/
    public void setUpBoard(){
        this.chess = new ChessModel();
    }


}
