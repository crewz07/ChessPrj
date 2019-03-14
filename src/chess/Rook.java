package chess;

public class Rook extends ChessPiece {

	public Rook(Player player) {
		
		super(player);
		
	}

	public String type() {
		
		return "Rook";
		
	}
	
	// determines if the move is valid for a rook piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// check the baseline rules for a valid chess piece move
		if(!super.isValidMove(move, board)) {
			return false;
		}

		// when a rook moves it must either end up in the same row OR the same
		// column it started out on
		if(move.fromRow != move.toRow && move.fromColumn != move.toColumn) {
			return false;
		}

		int upperRowLimit = 7;
		int lowerRowLimit = 0;
		int upperColumnLimit = 7;
		int lowerColumnLimit = 0;

		// set upper row limit
		for(int i = 7; i > move.fromRow; i--) {
			if(board[i][move.fromColumn] != null) {
				upperRowLimit = (this.player() !=
				board[i][move.fromColumn].player()) ? i : i - 1;
			}
		}

		// set lower row limit
		for(int i = 0; i < move.fromRow; i++) {
			if(board[i][move.fromColumn] != null) {
				lowerRowLimit = (this.player() !=
				board[i][move.fromColumn].player()) ? i : i + 1;
			}
		}

		// set upper column limit
		for(int i = 7; i > move.fromColumn; i--) {
			if(board[move.fromRow][i] != null) {
				upperColumnLimit = (this.player() !=
				board[move.fromRow][i].player()) ? i : i - 1;
			}
		}

		// set lower column limit
		for(int i = 0; i < move.fromColumn; i++) {
			if(board[move.fromRow][i] != null) {
				lowerColumnLimit = (this.player() !=
				board[move.fromRow][i].player()) ? i : i + 1;
			}
		}

		// makes sure the move isn't outside of the limits
		if(move.toRow > upperRowLimit || move.toRow < lowerRowLimit) {
			return false;
		} else if (move.toColumn > upperColumnLimit || move.toColumn <
		lowerColumnLimit) {
			return false;
		} else {
			return true;
		}
	}
	
}

// there's probably a cleaner/more efficient way to implement this class,
// might come back to this later