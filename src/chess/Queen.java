package chess;

public class Queen extends ChessPiece {

	/******************************************************************
	 * Default constructor for the queen piece
	 * @param player for the player the piece should be created for
	 *****************************************************************/
	public Queen(Player player) {
		super(player);

	}

	/******************************************************************
	 * Getter for a string of the piece's type
	 * @Return string "Queen" for the piece's type
	 *****************************************************************/
	public String type() {
		return "Queen";
		
	}

	/******************************************************************
	 * Checks to see if the piece is making a valid move for its type
	 * @param move - the move being made
	 * @param board - where the piece is on the board
	 * @return boolean if the move is valid or not
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		Bishop move1 = new Bishop(board[move.fromRow][move.fromColumn].player());
		Rook move2 = new Rook(board[move.fromRow][move.fromColumn].player());
		return (move1.isValidMove(move, board) || move2.isValidMove(move, board));
	}
}
