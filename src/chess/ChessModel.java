package chess;

import javax.swing.*;
import java.util.ArrayList;

public class ChessModel implements IChessModel {
	private boolean testing;

	//variables related to building the game and board
	private IChessPiece[][] board;
	private Player player;
	private int numRows = 8;
	private int numColumns = 8;

	//booleans related to keeping track of castling
	public boolean wKingMoved;
	public boolean bKingMoved;
	public boolean rbRookMoved;
	public boolean lbRookMoved;
	public boolean rwRookMoved;
	public boolean lwRookMoved;

	//ints related to keeping track of castling
	int ctWhiteKingMoved;
	int ctBlackKingMoved;
	int ctrbRookMoved;
	int ctlbRookMoved;
	int ctrwRookMoved;
	int ctlwRookMoved;

	//int and ArrayList tracking moves for the undo feature
	public static int moveCount = 0;
	ArrayList<MoveList>moveList = new ArrayList<>();

	// declare other instance variables as needed

	/******************************************************************
	 * Default constructor for ChessModel
	 *****************************************************************/
	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;
		testing = false;

		// white piece creation and set up
		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight (Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
		for(int i = 0; i < numColumns; i++) {
			board[6][i] = new Pawn(Player.WHITE);
		}

		// black piece creation and set up
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
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
	 * @return results boolean[] to determine what push buttons to enable
	 *****************************************************************/
	public boolean[] castleEnable() {
		boolean lWhiteState = false;
		boolean rWhiteState = false;
		boolean lBlackState = false;
		boolean rBlackState = false;

		//check conditions for white
		if(player == Player.WHITE) {
			//if king has not moved
			if (!wKingMoved) {
				//queen side
				//if lRook has not moved and pieces empty enable castle
				if (!lwRookMoved) {
					if (board[7][1] == null &&
							board[7][2] == null &&
							board[7][3] == null) {
						lWhiteState = true;
					}
				}

				//if rRook has not moved and pieces empty enable castle
				if (!rwRookMoved) {
					if (board[7][5] == null &&
							board[7][6] == null) {
						rWhiteState = true;
					}
				}
			}
		}
		//check conditions for black
		if(player == Player.BLACK){
			//if king has not moved
			if (!bKingMoved) {

				//queen side
				//if lRook has not moved and pieces empty enable castle
				if (!lbRookMoved) {
					if (board[0][1] == null &&
							board[0][2] == null &&
							board[0][3] == null) {
						lBlackState = true;
					}
				}

				//if rRook has not moved and pieces empty enable castle
				if (!rbRookMoved) {
					if (board[0][5] == null &&
							board[0][6] == null) {
						rBlackState = true;
					}
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

		IChessPiece movedRook;
		IChessPiece movedKing;
		MoveList aMove;

		//queen side castling
		if(left) {
			if(player == Player.WHITE){

				Move lrook = new Move(7,0,7,3);
				Move king = new Move(7,4,7,2);

				//move lrook
				movedRook = board[lrook.fromRow][lrook.fromColumn];

				//attempt to move rook
				board[lrook.toRow][lrook.toColumn] =
						board[lrook.fromRow][lrook.fromColumn];
				board[lrook.fromRow][lrook.fromColumn] = null;

				lwRookMoved = true;
				ctlwRookMoved = moveCount + 1;
				moveCount++;

				movedKing = board[king.fromRow][king.fromColumn];
				//attempt to move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;

				wKingMoved = true;
				ctWhiteKingMoved = moveCount + 1;
				moveCount++;

				aMove = new MoveList(moveCount-1,movedRook,lrook);
				moveList.add(0,aMove);

				aMove = new MoveList(moveCount,movedKing,king);
				moveList.add(0,aMove);

				if(inCheck(player)){
					undo();
					undo();
				}
				else {
					this.setNextPlayer();
				}
			}

			//black players turn
			else if(player == Player.BLACK){
				Move lrook = new Move(0,0,0,3);
				Move king = new Move(0,4,0,2);

				movedRook = board[lrook.fromRow][lrook.fromColumn];

				//move lrook
				board[lrook.toRow][lrook.toColumn] =
						board[lrook.fromRow][lrook.fromColumn];
				board[lrook.fromRow][lrook.fromColumn] = null;
				lbRookMoved = true;
				ctlbRookMoved = moveCount + 1;
				moveCount++;

				movedKing = board[king.fromRow][king.fromColumn];

				//move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;
				bKingMoved = true;
				ctBlackKingMoved = moveCount + 1;
				moveCount++;

				aMove = new MoveList(moveCount-1,movedRook,lrook);
				moveList.add(0,aMove);

				aMove = new MoveList(moveCount,movedKing,king);
				moveList.add(0,aMove);

				if(inCheck(player)){
					undo();
					undo();
				}
				else {
					this.setNextPlayer();
				}
			}
		}

		//king side castling
		else{
			if(player == Player.WHITE){

				//king side castling
				Move rRook = new Move(7,7,7,5);
				Move king = new Move(7,4,7,6);

				movedRook = board[rRook.fromRow][rRook.fromColumn];

				//move rRook
				board[rRook.toRow][rRook.toColumn] =
						board[rRook.fromRow][rRook.fromColumn];
				board[rRook.fromRow][rRook.fromColumn] = null;

				rwRookMoved = true;
				ctrwRookMoved = moveCount + 1;
				moveCount++;

				movedKing = board[king.fromRow][king.fromColumn];

				//move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;

				wKingMoved = true;
				ctWhiteKingMoved = moveCount + 1;
				moveCount++;

				aMove = new MoveList(moveCount-1,movedRook,rRook);
				moveList.add(0,aMove);

				aMove = new MoveList(moveCount,movedKing,king);
				moveList.add(0,aMove);

				if(inCheck(player)){
					undo();
					undo();
				}
				else {
					this.setNextPlayer();
				}
			}

			//black players move
			else if(player == Player.BLACK){

				//king side castling
				Move rRook = new Move(0,7,0,5);
				Move king = new Move(0,4,0,6);

				movedRook = board[rRook.fromRow][rRook.fromColumn];

				//move rRook
				board[rRook.toRow][rRook.toColumn] =
						board[rRook.fromRow][rRook.fromColumn];
				board[rRook.fromRow][rRook.fromColumn] = null;

				rbRookMoved = true;
				ctrbRookMoved = moveCount + 1;
				moveCount++;

				movedKing = board[king.fromRow][king.fromColumn];

				//move king
				board[king.toRow][king.toColumn] =
						board[king.fromRow][king.fromColumn];
				board[king.fromRow][king.fromColumn] = null;

				bKingMoved = true;
				ctBlackKingMoved = moveCount + 1;
				moveCount++;

				aMove = new MoveList(moveCount-1,movedRook,rRook);
				moveList.add(0,aMove);

				aMove = new MoveList(moveCount,movedKing,king);
				moveList.add(0,aMove);

				if(inCheck(player)){
					undo();
					undo();
				}
				else {
					this.setNextPlayer();
				}
			}
		}
	}

	/******************************************************************
	 * Method used to check if a player is in checkmate by looking at
	 * the possible moves of every piece on the board
	 * @return boolean, true if the player is in checkmate
	 *****************************************************************/
	public boolean isComplete() {
		for(int fromRow = 0; fromRow < numRows; fromRow++) {
			for(int fromCol = 0; fromCol < numColumns; fromCol++) {
				if(board[fromRow][fromCol] != null) {
					if(board[fromRow][fromCol].player() == player) {
						for(int toRow = 0; toRow < numRows; toRow++) {
							for(int toCol = 0; toCol < numColumns; toCol++) {
								Move move = new Move(fromRow, fromCol, toRow, toCol);
								if(isValidMove(move)) {
									move(move);
									if(inCheck(player.next())) {
										undo();
									} else {
										undo();
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Checkmate! " + player.next() + " wins!");
		return true;
	}

	/******************************************************************
	 * Checks to see ifa piece's move is legal
	 * @param move - an inputted move to be checked if it is valid
	 * @return boolean on whether or not the move is legal
	 *****************************************************************/
	public boolean isValidMove(Move move) {
		boolean valid = false;
		if (board[move.fromRow][move.fromColumn] != null) {
            if (board[move.fromRow][move.fromColumn].isValidMove(move, board)) {
                    if (board[move.fromRow][move.fromColumn].type().equals("Pawn")) {
                        if (Math.abs(move.fromColumn - move.toColumn) == 1) {

                            if (board[move.toRow][move.toColumn] == null) {


                                if(!moveList.isEmpty()) {
                                    Move previousMove = moveList.get(0).getMove();
                                    IChessPiece previousMovedPiece = moveList.get(0).getPieceMoved();
                                    if (previousMovedPiece.type().equals("Pawn")) {
                                        if (previousMovedPiece.player() != player) {
                                            if (Math.abs(previousMove.fromRow - previousMove.toRow) == 2) {
                                                if (previousMove.toRow == move.fromRow) {
                                                    if (previousMove.fromColumn == move.toColumn) {
                                                        valid = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                            } else {
                                valid = true;
                            }

                        } else {
                            valid = true;
                        }
                    } else {
                        valid = true;
                    }
            }
        }
		return valid;
	}

	/******************************************************************
	 * Make a piece move, with updating to the pertinent castling
	 * values if needed, and update the moveList and moveCount
	 * @param move - an inputted move to be made
	 *****************************************************************/
	public void move(Move move) {

	    IChessPiece taken = null;
        IChessPiece moved;
        MoveList aMove;
        boolean enPassant = false;

		//if king is being moved
		if(board[move.fromRow][move.fromColumn] instanceof King){
			if(board[move.fromRow][move.fromColumn].player() ==
					Player.WHITE){

				//if king hasn't moved before
				if(!wKingMoved){
					wKingMoved = true;
					ctWhiteKingMoved = moveCount + 1;
				}


			}
			else{
				if(!bKingMoved){
					bKingMoved = true;
					ctBlackKingMoved = moveCount + 1;
				}
			}
		}

		//if it was a rook that moved,
		else if(board[move.fromRow][move.fromColumn] instanceof Rook){

			//if it was a white pieces turn
			if(board[move.fromRow][move.fromColumn].player() ==
					Player.WHITE){

				//if it was left rook
				if(move.fromColumn == 0){
					if(!lwRookMoved){
						lwRookMoved = true;
						ctlwRookMoved = moveCount + 1;
					}
				}

				//if it was right rook
				else if(move.fromColumn == 7){
					if(!rwRookMoved){
						rwRookMoved = true;
						ctrwRookMoved = moveCount + 1;
					}
				}
			}

			//black piece moved
			else{
				//if it was left rook
				if(move.fromColumn == 0){
					if(!lbRookMoved){
						lbRookMoved = true;
						ctlbRookMoved = moveCount + 1;
					}
				}

				//if it was right rook
				else if(move.fromColumn == 7){
					if(!rbRookMoved){
						rbRookMoved = true;
						ctrbRookMoved = moveCount + 1;
					}
				}
			}
		}

		//en passant code
		if(board[move.fromRow][move.fromColumn] instanceof Pawn) {
            if (move.fromColumn != move.toColumn) {
                if (board[move.toRow][move.toColumn] == null) {
                    taken = board[move.fromRow][move.toColumn];
                    board[move.fromRow][move.toColumn] = null;
                    enPassant = true;
                } else {
					taken = board[move.toRow][move.toColumn];
				}
            } else {
				taken = board[move.toRow][move.toColumn];
			}
        } else {
            taken = board[move.toRow][move.toColumn];
        }
		moved = board[move.fromRow][move.fromColumn];


        //attempt move
		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;

		//moved = board[move.toRow][move.toColumn];
		//increase moveCount
		moveCount++;


		if(taken == null){
            aMove = new MoveList(moveCount,moved,move);
        }
		else{
            aMove = new MoveList(moveCount,moved,taken,move,enPassant);
        }
		//logMoves that were made
        if(moveList.isEmpty())
		    moveList.add(aMove);
        else
            moveList.add(0,aMove);

        if(!testing) {
			if(player == Player.WHITE) {

				if(inCheck(Player.WHITE)) {
					undo();
					JOptionPane.showMessageDialog(null, (inCheck(player.next()) ? "You have to make a move that gets your King out of check." : "Making that move would put your King into check."));
				}
			} else {
				if(inCheck(Player.WHITE)) {
					JOptionPane.showMessageDialog(null, "Check!");
				}
			}
		}

		setNextPlayer();
	}

	/******************************************************************
	 * Method to determine if an inputted player is in check, uses the
	 * ArrayList formattedKingPositions to determine the locations of
	 * both kings and if an opposing piece is threatening these
	 * locations
	 * @param p - the player to be determined if they have been placed
	 *          into check
	 * @return boolean on whether or not the inputted player is in check
	 *****************************************************************/
	public boolean inCheck(Player p) {

		//find king positions returned in format:
			//blackKing[r,c]
			//whiteKing[r,c]
		ArrayList<int[]> formattedKingPositions = this.findKing();
		int[] blackKingLoc = formattedKingPositions.get(0);
		int[] whiteKingLoc = formattedKingPositions.get(1);
		int blackRow = blackKingLoc[0];
		int blackCol = blackKingLoc[1];
		int whiteRow = whiteKingLoc[0];
		int whiteCol = whiteKingLoc[1];
		Move move;
		boolean valid = false;
		//META:at start of turn are you in check, yes must move out
		//META:current player moves, did current player put themselves
		//		into check

		//check p color, check if opposing piece's isValid method can get
		//	to p color king.
		if(p == Player.BLACK) {
			for (int r = 0; r < numRows; r++)
				for (int c = 0; c < numColumns; c++) {

					//if not an empty space and piece attacking king is
					//	on opposite team
					if (board[r][c] != null &&
							board[r][c].player() != p) {
						move = new Move(r, c, blackRow, blackCol);
						if (/*board[r][c].isValidMove(move, board)*/isValidMove(move)) {
							valid = true;
							break;
						}
					}
				}
		}

		else if(p == Player.WHITE) {
			for (int r = 0; r < numRows; r++)
				for (int c = 0; c < numColumns; c++) {

					//if not an empty space and piece attacking king is
					//	on opposite team
					if (board[r][c] != null &&
							board[r][c].player() != p) {
						move = new Move(r, c, whiteRow, whiteCol);
						if (/*board[r][c].isValidMove(move, board)*/ isValidMove(move)) {
							valid = true;
							break;
						}
					}
				}
		}

		return valid;
	}

	/******************************************************************
	 * Getter for the current player object
	 * @return Player of the current player
	 *****************************************************************/
	public Player currentPlayer() {
		return player;
	}

	/******************************************************************
	 * Getter for the number of rows on the board
	 * @return int of the number of rows on the board
	 *****************************************************************/
	public int numRows() {
		return numRows;
	}

	/******************************************************************
	 * Getter for the number of columns on the board
	 * @return int of the number of columns on the board
	 *****************************************************************/
	public int numColumns() {
		return numColumns;
	}

	/******************************************************************
	 * Getter of a chess piece at a specified location
	 * @param row - piece at inputted row on the board
	 * @param column - piece at inputted column on the board
	 * @return IChessPiece of the piece at the row/column location
	 *****************************************************************/
	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	/******************************************************************
	 * Changes the active player, from black to white for vice-versa
	 *****************************************************************/
	public void setNextPlayer() {
		player = player.next();
	}

	/******************************************************************
	 * Sets a certain piece at a certain location
	 * @param row - row of a piece
	 * @param column - column of a piece
	 * @param piece - IChessPiece object
	 *****************************************************************/
	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	/******************************************************************
	 * Undoes the last made move and updates the moveCount int and
	 * the moveList ArrayList
	 *****************************************************************/
	public void undo(){
	    if(moveCount > 0) {
            IChessPiece movedPiece;
            IChessPiece takenPiece;
            Move move;
            int lastMoveCount;

            //get top object in array;
            MoveList lastMove = moveList.get(0);
            movedPiece = lastMove.getPieceMoved();
            takenPiece = lastMove.getPieceTaken();
            move = lastMove.getMove();
            lastMoveCount = lastMove.getMoveCount();


            //check for pieces that hold different options
            //King
            if (movedPiece instanceof King) {
                if (movedPiece.player() == Player.WHITE) {

                    //was this white kings first time being moved?
                    if (ctWhiteKingMoved == lastMoveCount) {

                        //reset whiteKing boolean to false
                        wKingMoved = false;
                        ctWhiteKingMoved = 0;
                    }
                }
                else{
                    if(ctBlackKingMoved == lastMoveCount){
                        bKingMoved = false;
                        ctBlackKingMoved = 0;
                    }
                }
            }

            //Rooks
            if (movedPiece instanceof Rook){
                if(movedPiece.player() == Player.WHITE){

                    //if the place I'm trying to go back to is column 0 or 7
                    if(move.fromColumn == 0){
                        if(ctlwRookMoved == lastMoveCount){
                            lwRookMoved = false;
                            ctlwRookMoved = 0;
                        }
                    }
                    else if(move.fromColumn == 7){
                        if(ctrwRookMoved == lastMoveCount){
                            rwRookMoved = false;
                            ctrwRookMoved = 0;
                        }
                    }
                }
                else{
                    if(move.fromColumn == 0){
                        if(ctlbRookMoved == lastMoveCount){
                            lbRookMoved = false;
                            ctlbRookMoved = 0;
                        }
                    }
                    else if(move.fromColumn == 7){
                        if(ctrbRookMoved == lastMoveCount){
                            rbRookMoved = false;
                            ctrwRookMoved = 0;
                        }
                    }
                }
            }

            //swap pieces back
            board[move.fromRow][move.fromColumn] = movedPiece;
            if(lastMove.enPassant()) {
                board[move.toRow][move.toColumn] = null;
                board[move.fromRow][move.toColumn] = takenPiece;
            } else {
                board[move.toRow][move.toColumn] = takenPiece;
            }

            moveCount--;

            //remove top move from list
            moveList.remove(0);

            if(!inCheck(player.WHITE) || !inCheck(player.BLACK)){
            	setNextPlayer();
			}
        }
    }


	/******************************************************************
	 * Finds the location of both kings on the gameboard
	 * ArrayList return format: blackKing[row,col]
	 * 							 whiteKing[row,col]
	 * 	have to check player color to make sure you get the one you want
	 * @return array list containing an integer arrays size 2 with
	 * white king first, integer array holds[row,col]
	 *****************************************************************/
	public ArrayList<int[]> findKing(){

		//create arrayList
		ArrayList<int[]> kingPositions = new ArrayList<>();
		ArrayList<int[]> formattedkingPositions = new ArrayList<>();
		for(int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				if (board[r][c] instanceof King) {
					int[] position = {r, c};
					kingPositions.add(position);
				}
			}
		}
		int[] kingPosition = kingPositions.get(0);
			if(board[kingPosition[0]][kingPosition[1]].player() ==
					Player.WHITE){
				formattedkingPositions.add(kingPositions.get(1));
				formattedkingPositions.add(kingPositions.get(0));
			}
			else{
				formattedkingPositions.add(kingPositions.get(0));
				formattedkingPositions.add(kingPositions.get(1));
			}
		return formattedkingPositions;
	}

	public void AI() {
		/*
		 * Write a simple AI set of rules in the following order.
		 * A. Check to see if you are in check. (COMPLETE)
		 * 		i. If so, get out of check by moving the king or placing a piece to block the check (COMPLETE)
		 *
		 * B. Attempt to put opponent into check (or checkmate). (COMPLETE)
		 * 		i. Attempt to put opponent into check without losing your piece (COMPLETE)
		 *		ii. Perhaps you have won the game. (COMPLETE)
		 *
		 * C. Determine if any of your pieces are in danger, (COMPLETE)
		 *		i. Move them if you can. (COMPLETE)
		 *		ii. Attempt to protect that piece. (COMPLETE)
		 *
		 * D. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

		// maybe instead of making/undoing moves to find the best one, we
		// could just keep track of positions and use isValidMove to check
		// everything?

		if(currentPlayer() != Player.BLACK) {
			return;
		}

		ArrayList<int[]> whitePieceLocations = new ArrayList<>();
		ArrayList<int[]> blackPieceLocations = new ArrayList<>();

		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numColumns; c++) {
				if(board[r][c] != null) {
					int[] location = {r, c};
					if(board[r][c].player() == Player.WHITE) {
						whitePieceLocations.add(location);
					} else {
						blackPieceLocations.add(location);
					}
				}
			}
		}

		// A.
		if(inCheck(Player.BLACK)) {
			// i.
			if (!isComplete()) {
				for (int[] location : blackPieceLocations) {
					int fromRow = location[0];
					int fromColumn = location[1];
					for (int toRow = 0; toRow < numRows; toRow++) {
						for (int toColumn = 0; toColumn < numColumns; toColumn++) {
							Move move = new Move(fromRow, fromColumn, toRow, toColumn);
							if (isValidMove(move)) {
								testing = true;
								move(move);
								if (inCheck(Player.BLACK)) {
									undo();
									testing = false;
								} else {
									testing = false;
									return;
								}
							}
						}
					}
				}
			} else {
				testing = false;
				return;
			}
		}

		// B.
		Move checkMove = null;
		for(int[] blackLocation : blackPieceLocations) {
			int fromRow = blackLocation[0];
			int fromColumn = blackLocation[1];
			for(int toRow = 0; toRow < numRows; toRow++) {
				for(int toColumn = 0; toColumn < numColumns; toColumn++) {
					Move move = new Move(fromRow, fromColumn, toRow, toColumn);
					if(isValidMove(move)) {
						testing = true;
						move(move);
						if(inCheck(Player.WHITE)) {
							// ii.
							if(isComplete()) {
								testing = false;
								return;
							}
							boolean whiteCanTake = false;
							// i.
							for(int[] whiteLocation : whitePieceLocations) {
								int fromRow2 = whiteLocation[0];
								int fromColumn2 = whiteLocation[1];
								Move move2 = new Move(fromRow2, fromColumn2, toRow, toColumn);
								if(isValidMove(move2)) {
									whiteCanTake = true;
								}
							}
							if(whiteCanTake) {
								undo();
								testing = false;
							} else {
								undo();
								testing = false;
								checkMove = move;
							}
						} else {
							undo();
							testing = false;
						}
					}
				}
			}
		}
		if(checkMove != null) {
			testing = false;
			move(checkMove);
			return;
		}

		// C.
		// for every black piece...
        for(int[] blackLocation : blackPieceLocations) {
			ArrayList<Move> attackingMoves = new ArrayList<>();
			int attackers = 0;
			int defenders = 0;
            int toRow = blackLocation[0];
            int toColumn = blackLocation[1];
            // ...count how many attackers there are
            for(int[] attackerLocation : whitePieceLocations) {
            	Move move = new Move(attackerLocation[0], attackerLocation[1], toRow, toColumn);
            	if(isValidMove(move)) {
            		attackers++;
            		attackingMoves.add(move);
				}
			}
            // and for every attacking move white has...
            for(Move move : attackingMoves) {
            	defenders = 0;
            	testing = true;
            	move(move);
            	// ...count how many defenders there are (black pieces that could retake afterwards)
				for(int[] defenderLocation : blackPieceLocations) {
					if(isValidMove(new Move(defenderLocation[0], defenderLocation[1], toRow, toColumn))) {
						defenders++;
					}
				}
				undo();
				testing = false;
				// if there are less defenders than there are attackers, try to move the piece away
				if(defenders < attackers) {
					System.out.println("piece: " + pieceAt(toRow, toColumn));
					System.out.println("attackers: " + attackers);
					System.out.println("defenders: " + defenders);
					for(int toRow2 = 0; toRow2 < numRows; toRow2++) {
						for(int toColumn2 = 0; toColumn2 < numColumns; toColumn2++) {
							Move move2 = new Move(toRow, toColumn, toRow2, toColumn2);
							// if the piece can move away...
							if(isValidMove(move2)) {
								testing = true;
								move(move2);
								// ...recheck if white can take it
								for(int[] whiteLocation2 : whitePieceLocations) {
									Move move3 = new Move(whiteLocation2[0], whiteLocation2[1], toRow2, toColumn2);
									if(isValidMove(move3)){
										undo();
										testing = false;
										break;
									}
								}
								// if the piece was moved without being retaken, check if it put the black king in check
								if(testing && inCheck(Player.BLACK)) {
									undo();
									testing = false;
								}
								// if the piece was safely moved and didn't put black in check, return
								if(testing) {
									testing = false;
									System.out.println("MOVED OUT OF HARM");
									return;
								}
							}
						}
					}
					// if there are no available places for the piece to move, try to defend it
					for(Move move4 : attackingMoves) {
						for(int[] blackLocation2 : blackPieceLocations) {
							for(int toRow3 = 0; toRow3 < numRows; toRow3++) {
								for(int toColumn3 = 0; toColumn3 < numColumns; toColumn3++) {
									Move move5 = new Move(blackLocation2[0], blackLocation2[1], toRow3, toColumn3);
									if(isValidMove(move5)) {
										testing = true;
										move(move5);
										move(move4);
										if(isValidMove(new Move(toRow3, toColumn3, move4.toRow, move4.toColumn))) {
											undo();
											testing = false;
											System.out.println("DEFENDED WITH ANOTHER PIECE");
											return;
										} else {
											undo();
											undo();
											testing = false;
										}
									}
								}
							}
						}
					}
				}
			}
        }




	}

	public class MoveList{


		//Counter for the amount of moves taken
        int moveCount;

        //IChessPiece object for the piece moved and the piece taken
	    IChessPiece pieceMoved;
	    IChessPiece pieceTaken;

	    Move move;
	    boolean enPassant;

		/******************************************************************
		 * The following are constructors with different parameters
		 * for the MoveList object
		 *****************************************************************/

	    MoveList(int moveCount,IChessPiece pieceMoved, IChessPiece pieceTaken,Move move, boolean enPassant){
	        this.moveCount = moveCount;
	        this.pieceMoved = pieceMoved;
	        this.pieceTaken = pieceTaken;
	        this.move = move;
	        this.enPassant = enPassant;
        }

        MoveList(int moveCount,IChessPiece pieceMoved,Move move, boolean enPassant){
            this.moveCount = moveCount;
            this.pieceMoved = pieceMoved;
            this.move = move;
            this.enPassant = enPassant;
        }

        MoveList(int moveCount,IChessPiece pieceMoved, IChessPiece pieceTaken,Move move){
            this.moveCount = moveCount;
            this.pieceMoved = pieceMoved;
            this.pieceTaken = pieceTaken;
            this.move = move;
            this.enPassant = false;
        }

        MoveList(int moveCount,IChessPiece pieceMoved,Move move){
            this.moveCount = moveCount;
            this.pieceMoved = pieceMoved;
            this.move = move;
            this.enPassant = false;
        }

		/******************************************************************
		 * The following are getters for the instance variables of the
		 * MoveLists class
		 *****************************************************************/

        public int getMoveCount() {
            return moveCount;
        }

        public IChessPiece getPieceMoved() {
            return pieceMoved;
        }

        public IChessPiece getPieceTaken() {
            return pieceTaken;
        }

        public Move getMove() {
            return move;
        }

        public boolean enPassant() { return enPassant; }
    }
}
