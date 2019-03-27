/******************************************************************
 * A class for the functionality of the King Piece
 *
 * @author Andrew Kruse
 * @author Justin Walter
 * @author Ian Wilkewitz
 *
 * @version 3/26/2019
 *****************************************************************/

package chess;

import java.util.ArrayList;

public class King extends ChessPiece {

	private int[] rowMod = new int[] {-1,0,1,1,1,0,-1,-1};
	private int[] colMod = new int[] {-1,-1,-1,0,1,1,1,0};


    /******************************************************************
     * Default constructor for the King piece object
     * @param player for the player the piece should be created for
     *****************************************************************/
	public King(Player player) {
		super(player);

	}

    /******************************************************************
     * Getter for a string of the piece's type
     * @Return string "King" for the piece's type
     *****************************************************************/
	public String type() {
		return "King";
	}

    /******************************************************************
     * Checks to see if the piece is making a valid move for its type
     * @param move - the move being made
     * @param board - where the piece is on the board
     * @return boolean if the move is valid or not
     *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = false;
		ArrayList<int[]> validMoves = new ArrayList<int[]>();

		//go through all possible moves the king could make
		for(int i = 0; i < 8; i++) {

			if (move.fromRow + rowMod[i] > -1 &&
					move.fromRow + rowMod[i] < 8 &&
					move.fromColumn + colMod[i] > -1 &&
					move.fromColumn + colMod[i] < 8) {
				if (board[
						move.fromRow + rowMod[i]]
						[move.fromColumn + colMod[i]] == null) {

					//if would put into check, do not allow step 10, might be model level...
					int[] a = {
							move.fromRow + rowMod[i],
							move.fromColumn + colMod[i]};
					validMoves.add(a);
				} else if (board[
						move.fromRow + rowMod[i]]
						[move.fromColumn + colMod[i]].player() !=
						this.player()) {

					//if would put into check, do not allow step 10, might be model level...
					int[] a = {
							move.fromRow + rowMod[i],
							move.fromColumn + colMod[i]};
					validMoves.add(a);
				}
			}
		}

		for(int i = 0; i < validMoves.size(); i++){
			int[] a = validMoves.get(i);
			if(move.toRow == a[0] && move.toColumn == a[1]){
				valid = true;
			}
		}


		if(!super.isValidMove(move,board))
			valid = false;

		return valid;
	}
}
