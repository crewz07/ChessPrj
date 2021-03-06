/******************************************************************
 * A class for the functionality of the Pawn piece
 *
 * @author Andrew Kruse
 * @author Justin Walter
 * @author Ian Wilkewitz
 *
 * @version 3/26/2019
 *****************************************************************/

package chess;

// right now, pawns can't take pieces, so that needs to be fixed
// eventually

public class Pawn extends ChessPiece {
	private int direction;

	/******************************************************************
	 * Default constructor for the pawn piece
	 * @param player for the player the piece should be created for
	 *****************************************************************/
	public Pawn(Player player) {
		super(player);
		direction = player == Player.WHITE ? -1 : 1;
	}

	/******************************************************************
	 * Getter for a string of the piece's type
	 * @Return string "Pawn" for the piece's type
	 *****************************************************************/
	public String type() {
		return "Pawn";
	}

	/**
	 * determines if a move is valid for a pawn given the move and the current
	 * game board
	 * @param move the move initiated by the player
	 * @param board the current board of the chess game
	 * @return whether or not the given move is a valid move for a pawn
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) return false;

		if (move.fromColumn == move.toColumn) {
			if (move.fromRow + direction < 0 || board[move.fromRow + direction][move.toColumn] != null)
				return false;

			boolean isInitialMove = player() == Player.WHITE ? move.fromRow == 6 : move.fromRow == 1;
			if (isInitialMove && move.fromRow + direction * 2 == move.toRow)
				return board[move.toRow][move.toColumn] == null;

			if (move.fromRow + direction == move.toRow)
				return true;
		} else if (Math.abs(move.fromColumn - move.toColumn) == 1) {
			if (move.fromRow + direction == move.toRow)
				return true; //board[move.toRow][move.toColumn] != null;

		}
		return false;
	}
}

