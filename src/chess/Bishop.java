/******************************************************************
 * A class for the functionality of the Bishop piece
 *
 * @author Andrew Kruse
 * @author Justin Walter
 * @author Ian Wilkewitz
 *
 * @version 3/26/2019
 *****************************************************************/

package chess;


import java.util.ArrayList;

public class Bishop extends ChessPiece {

    /******************************************************************
     * Default constructor for the bishop piece
     * @param player for the player the piece should be created for
     *****************************************************************/
	public Bishop(Player player) {
		super(player);
	}

    /******************************************************************
     * Getter for a string of the piece's type
     * @Return string "Bishop" for the piece's type
     *****************************************************************/
	public String type() {
		return "Bishop";
	}

    /******************************************************************
     * Checks to see if the piece is making a valid move for its type
     * @param move - the move being made
     * @param board - where the piece is on the board
     * @return boolean if the move is valid or not
     *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {

        //arrayList will hold 1 dimensional arrays with valid moves.
        //each array will have following format [row,col]
        //method call possibleMoves populates arrayList
        //check to see if move is valid
        ArrayList<int[]> validMoves = new ArrayList<>();
        boolean valid = false;
        this.possibleMoves(move.fromRow,move.fromColumn,board,validMoves);

        //check if move is part of possibleMoves
        for(int i = 0; i < validMoves.size(); i++){
            int[] a = validMoves.get(i);
            if(move.toRow == a[0] && move.toColumn == a[1]){
                valid = true;
            }
        }
        return valid;
		
	}

    /******************************************************************
     * Used to populate an ArrayList of all possible moves a bishop can
     * make from its current position, adds array to array list
     * with format [row,col]
     * @param startingRow integer starting row position of bishop
     * @param startingCol integer starting col position of bishop
     * @param board double array of chess piece locations
     * @param validMoves array list to be populated with valid row/col
     ******************************************************************/
    private void possibleMoves(int startingRow,int startingCol,
                               IChessPiece[][] board,
                               ArrayList validMoves) {
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
