package chess;


import org.junit.Assert;
import org.junit.Test;

public class ModelTests {
    public ChessModel chess = new ChessModel();

    @Test
    public void moveKing(){
        //set piece in front of king to null
        chess.setPiece(6,3,null);
        Move king = new Move(7,3,6,3);
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

    /******************************************************************
     * Method used to reset global board
     *****************************************************************/
    public void setUpBoard(){
        this.chess = new ChessModel();
    }


}
