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
	public int[] castleEnable(){
		int lWhiteState = 0;
		int rWhiteState = 0;
		int lBlackState = 0;
		int rBlackState = 0;

		//check conditions for white

		//if king has not moved
			if(!wKingMoved){

				//if lRook has not moved and pieces empty enable castle
				if(!lwRookMoved){
					if(board[7][1] == null &&
							board[7][2] == null){
						lWhiteState = 1;
					}
				}

				//if rRook has not moved and pieces empty enable castle
				if(!rwRookMoved){
					if(board[7][4] == null &&
					board[7][5] == null &&
					board[7][6] == null){
						rWhiteState = 1;
					}
				}
			}

		//check conditions for black

		//if king has not moved
		if(!bKingMoved){

			//if lRook has not moved and pieces empty enable castle
			if(!lbRookMoved){
				if(board[0][1] == null &&
						board[0][2] == null){
					lBlackState = 1;
				}
			}

			//if rRook has not moved and pieces empty enable castle
			if(!rbRookMoved){
				if(board[0][4] == null &&
						board[0][5] == null &&
						board[0][6] == null){
					rBlackState = 1;
				}
			}
		}

		int[] results = {
				lWhiteState,
				rWhiteState,
				lBlackState,
				rBlackState};
		return results;
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
