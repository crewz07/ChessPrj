package chess;

// right now, pawns can't take pieces, so that needs to be fixed
// eventually

public class Pawn extends ChessPiece {

	public Pawn(Player player) {
		super(player);
	}

	public String type() {
		return "Pawn";
	}

	// determines if the move is valid for a pawn piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean isWhitePiece = player() == Player.WHITE;

		// checks the baseline rules for a valid chess piece move
		if(!super.isValidMove(move, board)) {
			return false;
		}

		// pawns can't move horizontally


		// black pawns and white pawns will have different rules
		if(player() == Player.WHITE) {
			if(move.toRow == move.fromRow - 1 && move.toColumn == move.fromColumn - 1) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() != Player.WHITE) {
						return true;
					}
				}
			}
			if(move.toRow == move.fromRow - 1 && move.toColumn == move.fromColumn + 1) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() != Player.WHITE) {
						return true;
					}
				}
			}
			if(move.fromColumn != move.toColumn) {
				return false;
			}

			// pawns can't move backwards
			if(move.fromRow < move.toRow) {
				return false;
			}

			// a pawn cannot move forward if there is a piece directly in
			// front of it (this rule is really only here to deal with
			// how pawns can move an extra space on their first move)
			if(board[move.fromRow - 1][move.fromColumn] != null) {
				return false;
			}

			// if the pawn is still on its starting row, it can't move more
			// than two spaces forward
			if(move.fromRow == 6) {
				if(move.fromRow - move.toRow > 2) {
					return false;
				}
			}

			// if the pawn ISN'T on its starting row, it can't move more than
			// ONE space forward
			else {
				if(move.fromRow - move.toRow > 1) {
					return false;
				}
			}

			// if the move passed all of the previous checks, then its valid
			return true;
		}

		// black pawns have the similar rules, but can only move in the
		// opposite direction of white pawns
		else {
			if(move.toRow == move.fromRow + 1 && move.toColumn == move.fromColumn - 1) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() != Player.BLACK) {
						return true;
					}
				}
			}
			if(move.toRow == move.fromRow + 1 && move.toColumn == move.fromColumn + 1) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() != Player.BLACK) {
						return true;
					}
				}
			}
			if(move.fromColumn != move.toColumn) {
				return false;
			}
			// pawns can't move backwards
			if(move.fromRow > move.toRow) {
				return false;
			}
			/*
			if(board[move.fromRow + (this.player() == Player.WHITE ? -1 : 1)]
			[move.fromColumn] != null) {

			}
			*/
			// a pawn cannot move forward if there is a piece directly in
			// front of it (this rule is really only here to deal with
			// how pawns can move an extra space on their first move)
			if(board[move.fromRow + 1][move.fromColumn] != null) {
				return false;
			}

			// if the pawn is still on its starting row, it can't move more
			// than two spaces forward
			// (i can probably use absolute values here to simplify things)
			if(move.fromRow == 1) {
				if(move.fromRow - move.toRow < -2) {
					return false;
				}
			}

			// if the pawn ISN'T on its starting row, it can't move more than
			// ONE space forward
			else {
				if(move.fromRow - move.toRow < -1) {
					return false;
				}
			}

			return true;
		}

		// i'll need to add rules for en passants later on
		//i think this will take place in the model not in this-ak
	}
}

