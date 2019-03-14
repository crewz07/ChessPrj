package chess;

public class Knight extends ChessPiece {

	public Knight(Player player) {

		super(player);

	}

	public String type() { return "Knight"; }

	// determines if the move is valid for a knight piece
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

// this could also probably be cleaned up a lot, so i'll come back to it later