package chess;

public abstract class ChessPiece implements IChessPiece {

	private Player owner;

	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		return owner;
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if ((move.fromRow == move.toRow) && (move.fromColumn == move.toColumn)) {
			return false;
		}
		if(board[move.fromRow][move.fromColumn] != this) {
			return false;
		}
		if(board[move.toRow][move.toColumn] != null) {
		    if(board[move.toRow][move.toColumn].player() == this.player()) {
                return false;
            }
		}
		return true;
	}
}