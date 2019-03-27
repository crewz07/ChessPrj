package chess;

public class Knight extends ChessPiece {

	/******************************************************************
	 * Default constructor for the knight piece
	 * @param player for the player the piece should be created for
	 *****************************************************************/
	public Knight(Player player) {

		super(player);

	}

	/******************************************************************
	 * Getter for a string of the piece's type
	 * @Return string "Knight" for the piece's type
	 *****************************************************************/
	public String type() { return "Knight"; }

	/******************************************************************
	 * Checks to see if the piece is making a valid move for its type
	 * @param move - the move being made
	 * @param board - where the piece is on the board
	 * @return boolean if the move is valid or not
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// checks the baseline rules for each chess piece
		if(!super.isValidMove(move, board)) {
			return false;
		}

		// knights will never end up on the same row/column they started on
		if(move.fromRow == move.toRow || move.fromColumn == move.toColumn) {
			return false;
		}

		// knights can never move more than 2 spaces for all directions
		if(Math.abs(move.fromRow - move.toRow) > 2 || Math.abs(move.fromColumn
				- move.toColumn) > 2) {
			return false;
		}

		// if the knight moves two spaces vertically, it can only move once
		// space horizontally
		if(Math.abs(move.fromRow - move.toRow) == 2) {
			if(Math.abs(move.fromColumn - move.toColumn) != 1) {
				return false;
			}
		}

		if(Math.abs(move.fromRow - move.toRow) == 1) {
			if(Math.abs(move.fromColumn - move.toColumn) != 2) {
				return false;
			}
		}

		// if the knight moves two spaces horizontally, it can only move one
		// space vertically
		if(Math.abs(move.fromColumn - move.toColumn) == 2) {
			if(Math.abs(move.fromRow - move.toRow) != 1) {
				return false;
			}
		}

		if(Math.abs(move.fromColumn - move.toColumn) == 1) {
			if(Math.abs(move.fromRow - move.toRow) != 2) {
				return false;
			}
		}

		// return true if all rules are passed
		return true;
	}

}