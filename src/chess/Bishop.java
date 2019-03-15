package chess;


import java.util.ArrayList;

public class Bishop extends ChessPiece {

	public Bishop(Player player) {
		super(player);
	}

	public String type() {
		return "Bishop";
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {

	    //initialize comparitor values
        int startingRow = move.fromRow;
        int startingCol = move.fromColumn;
        int boardEdgeMax = 7;
        int boardEdgeMin = 0;

        //arrayList will hold 1 dimensional arrays with valid moves.
        //each array will have following format [row,col]
        //this will allow for us to find all the moves, then move
        //check to see if move if valid
        ArrayList<int[]> validMoves = new ArrayList<int[]>();
        this.possibleMoves(startingRow,startingCol,board,validMoves);
        for(int i = 0; i < validMoves.size(); i++){
            int[] a = validMoves.get(i);
            if(move.toRow == a[0] && move.toColumn == a[1]){
                return true;
            }

        }
        return false;

        // More code is needed
		
	}
    private void possibleMoves(int startingRow,int startingCol,IChessPiece[][] board,ArrayList validMoves) {
        //valid moves in upper left diagonal
        //start at starting place-1-1, move up to left(toward 0,0)
        for (int row = startingRow - 1, col = startingCol - 1;
             row > -1 && col > -1; row--, col--) {

            //if empty space, add to list of valid moves
            if (board[row][col] == null) {
                int[] a = {row, col};
                validMoves.add(a);
            }

            //if piece is not same color, add to list, but stop looking
            else if (board[row][col].player() != this.player()) {
                int[] a = {row, col};
                validMoves.add(a);
                break;
            }

            //if piece is same color, stop looking
            else if (board[row][col].player() == this.player()) {
                break;
            }
        }
        //valid moves in upper right diagonal
        //start at starting place, move up to right(toward 0,7)
        for (int row = startingRow - 1, col = startingCol + 1;
             row > -1 && col < 8; row--, col++) {

            //if empty space, add to list of valid moves
            if (board[row][col] == null) {
                int[] a = {row, col};
                validMoves.add(a);
            }

            //if piece is not same color, add to list, but stop looking
            else if (board[row][col].player() != this.player()) {
                int[] a = {row, col};
                validMoves.add(a);
                break;
            }

            //if piece is same color, stop looking
            else if (board[row][col].player() == this.player()) {
                break;
            }
        }

        //moving diagonal down left, moving towards (7,0)
        for (int row = startingRow + 1, col = startingCol - 1;
             row < 8 && col > -1; row++, col--) {

            //if empty space, add to list of valid moves
            if (board[row][col] == null) {
                int[] a = {row, col};
                validMoves.add(a);
            }

            //if piece is not same color, add to list, but stop looking
            else if (board[row][col].player() != this.player()) {
                int[] a = {row, col};
                validMoves.add(a);
                break;
            }

            //if piece is same color, stop looking
            else if (board[row][col].player() == this.player()) {
                break;
            }
        }

        //check spaces toward diagonal down right towards(7,7)
        for (int row = startingRow + 1, col = startingCol + 1;
             row < 8 && col < 8; row++, col++) {

            //if empty space, add to list of valid moves
            if (board[row][col] == null) {
                int[] a = {row, col};
                validMoves.add(a);
            }

            //if piece is not same color, add to list, but stop looking
            else if (board[row][col].player() != this.player()) {
                int[] a = {row, col};
                validMoves.add(a);
                break;
            }

            //if piece is same color, stop looking
            else if (board[row][col].player() == this.player()) {
                break;
            }
        }
    }
}
