/******************************************************************
 * A class to make the game pieces move
 *
 * @author Andrew Kruse
 * @author Justin Walter
 * @author Ian Wilkewitz
 *
 * @version 3/26/2019
 *****************************************************************/

package chess;

public class Move {
	public int fromRow, fromColumn, toRow, toColumn;

	/******************************************************************
	 * Default constructor for the Move object
	 *****************************************************************/
	public Move() {
	}

	/******************************************************************
	 * Constructor for the Move object from one spot to another
	 * @param fromRow row that the piece is at
	 * @param fromColumn column that the piece is at
	 * @param toRow row that the piece is moving to
	 * @param toColumn column that the piece is moving to
	 *****************************************************************/
	public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}

	/******************************************************************
	 * Returns a string of the move
	 * @return String of the made move
	 *****************************************************************/
	@Override
	public String toString() {
		return "Move [fromRow=" + fromRow + ", fromColumn=" + fromColumn + ", toRow=" + toRow + ", toColumn=" + toColumn
				+ "]";
	}
	
	
}
