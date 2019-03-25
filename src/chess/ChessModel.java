package chess;

import javax.swing.*;
import java.util.ArrayList;

public class ChessModel implements IChessModel {
	private IChessPiece[][] board;
	private Player player;
	private int numRows = 8;
	private int numColumns = 8;

	public boolean wKingMoved;
	public boolean bKingMoved;
	public boolean rbRookMoved;
	public boolean lbRookMoved;
	public boolean rwRookMoved;
	public boolean lwRookMoved;

	int ctWhiteKingMoved;
	int ctBlackKingMoved;
	int ctrbRookMoved;
	int ctlbRookMoved;
	int ctrwRookMoved;
	int ctlwRookMoved;

	public static int moveCount = 0;
	ArrayList<MoveList>moveList = new ArrayList<>();

	// declare other instance variables as needed

	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;

		// white pieces
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

		// black pieces
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
	 * @return int[] used to determine what push buttons to enable
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
		return true;
	}

	public boolean isValidMove(Move move) {
		boolean valid = false;
		if (board[move.fromRow][move.fromColumn] != null) {
            if (board[move.fromRow][move.fromColumn].isValidMove(move, board)) {
                    if (board[move.fromRow][move.fromColumn] instanceof Pawn) {
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
                }
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

		setNextPlayer();
	}

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
						if (board[r][c].isValidMove(move, board)) {
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
						if (board[r][c].isValidMove(move, board)) {
							valid = true;
							break;
						}
					}
				}
		}

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

	//finds the location of both kings, returns array list containing
	//an integer arrays size 2 with white king first, integer array
	//holds[row,col]
	//arraylist return format blackKing[row,col]
	//						  whiteKing[row,col]
	//have to check player color to make sure you get the one you want
	public ArrayList<int[]> findKing(){

		//create arrayList
		ArrayList<int[]> kingPositions = new ArrayList<>();
		ArrayList<int[]> formattedkingPositions = new ArrayList<>();
		for(int r = 0; r < numRows; r++)
			for(int c = 0; c < numColumns; c++){
				if(board[r][c] instanceof King){
					int[] position = {r,c};
					kingPositions.add(position);
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

	public class MoveList{



        int moveCount;
	    IChessPiece pieceMoved;
	    IChessPiece pieceTaken;
	    Move move;
	    boolean enPassant;

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
