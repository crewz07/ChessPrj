package chess;

public class ChessModel implements IChessModel {
	private IChessPiece[][] board;
	private Player player;
	private int numRows = 8;
	private int numColumns = 8;

	private boolean wKingMoved;
	private boolean bKingMoved;
	private boolean rbRookMoved;
	private boolean lbRookMoved;
	private boolean rwRookMoved;
	private boolean lwRookMoved;
	// declare other instance variables as needed

	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;

		// white pieces
		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new King(Player.WHITE);
		board[7][4] = new Queen(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight (Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
		for(int i = 0; i < numColumns; i++) {
			board[6][i] = new Pawn(Player.WHITE);
		}

		// black pieces
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new King(Player.BLACK);
		board[0][4] = new Queen(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight (Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		for(int i = 0; i < numColumns; i++) {
			board[1][i] = new Pawn(Player.BLACK);
		}

		wKingMoved = false;
		bKingMoved = false;
		rbRookMoved = false;
		lbRookMoved = false;
		rwRookMoved = false;
		lwRookMoved = false;
	}

	/******************************************************************
	 * Used to check state on castling conditions, returns an integer
	 * to be used by gui to determine which buttons should be enabled
	 * return array format is as follows 1 for enable 0 for disabled
	 * [white L, white R, black L, black right]
	 * @return int[] used to determine what push buttons to enable
	 *****************************************************************/
	public boolean[] castleEnable() {
		boolean lWhiteState = false;
		boolean rWhiteState = false;
		boolean lBlackState = false;
		boolean rBlackState = false;

		//check conditions for white

		//if king has not moved
		if (!wKingMoved) {

			//if lRook has not moved and pieces empty enable castle
			if (!lwRookMoved) {
				if (board[7][1] == null &&
						board[7][2] == null) {
					lWhiteState = true;
				}
			}

			//if rRook has not moved and pieces empty enable castle
			if (!rwRookMoved) {
				if (board[7][4] == null &&
						board[7][5] == null &&
						board[7][6] == null) {
					rWhiteState = true;
				}
			}
		}

		//check conditions for black

		//if king has not moved
		if (!bKingMoved) {

			//if lRook has not moved and pieces empty enable castle
			if (!lbRookMoved) {
				if (board[0][1] == null &&
						board[0][2] == null) {
					lBlackState = true;
				}
			}

			//if rRook has not moved and pieces empty enable castle
			if (!rbRookMoved) {
				if (board[0][4] == null &&
						board[0][5] == null &&
						board[0][6] == null) {
					rBlackState = true;
				}
			}
		}
		boolean[] results = {
				lWhiteState,
				rWhiteState,
				lBlackState,
				rBlackState};
		return results;
	}
	/**************************************************************
	 * This function is used to call the move method and castle
	 * if the push buttons for castle are pushed if left 0, if
	 * right 1;
	 * @param left the side of the board castling is to occur
	 *************************************************************/
	public void moveCastle(boolean left){

		//king side castling
		if(left) {
			if(player == Player.WHITE){

				Move lrook = new Move(7,0,7,2);
				Move king = new Move(7,3,7,1);

				//move lrook
				board[lrook.toRow][lrook.toColumn] =
						board[lrook.fromRow][lrook.fromColumn];
				board[lrook.fromRow][lrook.fromColumn] = null;

				//move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;
			}

			//black players turn
			else{
				Move lrook = new Move(0,0,0,2);
				Move king = new Move(0,3,0,1);
				this.move(lrook);
				this.move(king);

				//move lrook
				board[lrook.toRow][lrook.toColumn] =
						board[lrook.fromRow][lrook.fromColumn];
				board[lrook.fromRow][lrook.fromColumn] = null;

				//move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;
			}
		}

		//queen side castling
		else{
			if(player == Player.WHITE){

				//queen side castling
				Move rRook = new Move(7,7,7,5);
				Move king = new Move(7,3,7,6);
				this.move(rRook);
				this.move(king);

				//move rRook
				board[rRook.toRow][rRook.toColumn] =
						board[rRook.fromRow][rRook.fromColumn];
				board[rRook.fromRow][rRook.fromColumn] = null;

				//move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;
			}

			//black players move
			else{

				//queen side castling
				Move rRook = new Move(0,7,0,5);
				Move king = new Move(0,3,0,6);
				this.move(rRook);
				this.move(king);

				//move rRook
				board[rRook.toRow][rRook.toColumn] =
						board[rRook.fromRow][rRook.fromColumn];
				board[rRook.fromRow][rRook.fromColumn] = null;

				//move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;
			}
		}
	}

	public boolean isComplete() {
		boolean valid = false;
		return valid;
	}

	public boolean isValidMove(Move move) {
		boolean valid = false;

		if (board[move.fromRow][move.fromColumn] != null)

			if (board[move.fromRow][move.fromColumn]
					.isValidMove(move, board) == true)
				valid = true;

		return valid;
	}

	public void move(Move move) {

		if(board[move.fromRow][move.fromColumn] instanceof King){
			if(board[move.fromRow][move.fromColumn].player() ==
					Player.WHITE){
				wKingMoved = true;
			}
			else{
				bKingMoved = true;
			}
		}

		//if it was a rook that moved,
		else if(board[move.fromRow][move.fromColumn] instanceof Rook){

			//if it was a white pieces turn
			if(board[move.fromRow][move.fromColumn].player() ==
					Player.WHITE){

				//if it was left rook
				if(move.fromColumn == 0){
					lwRookMoved = true;
				}

				//if it was right rook
				else if(move.fromColumn == 7){
					rwRookMoved = true;
				}
			}

			//black piece moved
			else{
				//if it was left rook
				if(move.fromColumn == 0){
					lbRookMoved = true;
				}

				//if it was right rook
				else if(move.fromColumn == 7){
					rbRookMoved = true;
				}
			}
		}

		board[move.toRow][move.toColumn] =
				board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	public boolean inCheck(Player p) {
		boolean valid = false;
		return valid;
	}


	public Player currentPlayer() {
		return player;
	}

	public int numRows() {
		return numRows;
	}

	public int numColumns() {
		return numColumns;
	}

	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	public void setNextPlayer() {
		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	public void AI() {
		/*
		 * Write a simple AI set of rules in the following order.
		 * a. Check to see if you are in check.
		 * 		i. If so, get out of check by moving the king or placing a piece to block the check
		 *
		 * b. Attempt to put opponent into check (or checkmate).
		 * 		i. Attempt to put opponent into check without losing your piece
		 *		ii. Perhaps you have won the game.
		 *
		 *c. Determine if any of your pieces are in danger,
		 *		i. Move them if you can.
		 *		ii. Attempt to protect that piece.
		 *
		 *d. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

	}
}
