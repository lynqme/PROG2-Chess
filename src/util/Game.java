package util;

import java.util.Scanner;

public class Game {
	private Piece[][] board;
	private Player[] players;
	private boolean whiteTurn;
	public Game(String player1Name, int player1Rating, String player2Name, int player2Rating) {
		whiteTurn = true;
		players = new Player[2];
		players[0] = new Player(player1Name, player1Rating);
		players[1] = new Player(player2Name, player2Rating);
		board = new Piece[8][8];
		for(int i = 0; i < 8;i++) {//rank: 8-i
			for(int j = 0; j < 8; j++) {//file: j+'A'
				if(i > 1 && i < 6)//4 middle ranks
					board[i][j] = new Empty();
				else if (i == 6 || i == 1)//ranks 2 and 7: only pawns
					board[i][j] = new Pawn(i == 6);
				else if (j == 1 || j == 6)// ranks B1 & G1 &&B7 and G7 are knights
					board[i][j] = new Knight(i == 7);
				else {
					if(i==0 && j==0 || i==7 && j==7 || i==0 && j ==7 || i==7 &&j==0) {
						board[i][j] = new Piece (i == 7,false, "Rok");
					} else if (i==0 && j==2 || i==0 && j==5 || i==7 && j==2 || i==7 && j==5) {
						board[i][j] = new Piece (i==7, false, "Bsp");
					}else if ((i==0 || i==7) && j==3 ) {
						board[i][j] = new Piece (i==7, false, "Qun");}
					else if ((i==0 || i==7) && j==4 ) {
							board[i][j] = new Piece (i==7, false, "Kng");
							}
			}
			}
			}
				
	}
	public Piece promotion(String src) {
		 int srcJ = src.charAt(0)-'a';
			int srcI = 8-src.charAt(1)+'0';
		    Piece picked = board[srcI][srcJ];
		    Scanner input= new Scanner (System.in);
		    String rankselection;
		    int ranknum;
		    if (picked.toString().contains("Pwn")) {
		    	if(srcI==0 || srcI==7) {
		    		if(picked.white) {
		    			System.out.println("What rank would you like to select? Input integer./n 1.Pawn /n 2.Rook /n 3.Bishop /n 4.Knight /n 5. Queen"); 
		    			ranknum = input.nextInt();
		    			rankselection="";
		    			switch (ranknum) {
		                case 1:  rankselection = "Pawn";
		                System.out.println("Your pawn has chosen to remain a pawn");
		                return board[srcI][srcJ];
		                      
		                case 2:  rankselection = "Rook";
		                board[srcI][srcJ] = new Piece(false, true,"Rok"); 
	                	System.out.println("Your pawn has been promoted to Rook");
	                	 return board[srcI][srcJ];
		                      
		                case 3:  rankselection = "Bishop";
		                board[srcI][srcJ] = new Piece(false, true,"Bsp"); 
		                System.out.println("Your pawn has been promoted to Bishop");
		                return board[srcI][srcJ];
		                       
		                case 4:  rankselection = "Knight";
		                board[srcI][srcJ] = new Knight(false);
		                System.out.println("Your pawn has been promoted to Knight");
		                return board[srcI][srcJ];
		                        
		                case 5:  rankselection = "Queen";
		                board[srcI][srcJ] = new Piece(false, true,"Qun");
		                System.out.println("Your pawn has been promoted to Queen");
		                return board[srcI][srcJ];
		                      
		                default: rankselection = "Pawn";
		                System.out.println("Your pawn has chosen to remain a pawn");
		                         break;
		    		}
		    		if(!picked.white) {
		    			System.out.println("What rank would you like to select? Input interger./n1.Pawn /n2.Rook /n3.Bishop /n4.Knight /n5. Queen"); 
		    			ranknum = input.nextInt();
		    			rankselection=" ";
		    			switch (ranknum) {
		                case 1:  rankselection = "Pwn";
		                System.out.println("Your pawn has chosen to remain a pawn");
		                return board[srcI][srcJ];
		                         
		                case 2:  rankselection = "Rok";
		                	board[srcI][srcJ] = new Piece(false, true,"Rok"); 
		                	System.out.println("Your pawn has been promoted to Rook");
		                	 return board[srcI][srcJ];
		                     
		                case 3:  rankselection = "Bsp";
		                board[srcI][srcJ] = new Piece(false, true,"Bsp"); 
		                System.out.println("Your pawn has been promoted to Bishop");
		                return board[srcI][srcJ];
		                       
		                case 4:  rankselection = "Kng";
		                board[srcI][srcJ] = new Knight(false);
		                System.out.println("Your pawn has been promoted to Knight");
		                return board[srcI][srcJ];
		                      
		                case 5:  rankselection = "Qun";
		                board[srcI][srcJ] = new Piece(false, true,"Qun");
		                System.out.println("Your pawn has been promoted to Queen");
		                return board[srcI][srcJ];
		                       
		                default: rankselection = "Pwn";
		                System.out.println("Your pawn has chosen to remain a pawn");
		                         break;  
		    			}
		    		}}}}
		    	return picked;
		    
		       
	}
	public String toString() {
		String out = players[1]+"\n";
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 9;j++)
				out += (((j == 0)?((8-i)+""): (board[i][j-1]+""))+(j==8?'\n':'\t'));
		out += "\tA\tB\tC\tD\tE\tF\tG\tH";
		return out+"\n"+players[0];
	}
	public void move(String src, String trg) throws IllegalChessMoveException {
	    if(!isLegalMove(src,trg))
		throw new IllegalChessMoveException();
	    int srcI = 8-src.charAt(1)+'0';
	    int trgI = 8-trg.charAt(1)+'0';
	    int srcJ = src.charAt(0)-'a';
	    int trgJ = trg.charAt(0)-'a';
	    board[trgI][trgJ] = board[srcI][srcJ]; 
	    board[srcI][srcJ] = new Empty();
	    if(trgI==0 ||trgI==7) {
	    	board[trgI][trgJ]=promotion(trg);
	    }
	    whiteTurn = !whiteTurn;
	}
	public void capture(String src, String trg) throws IllegalChessMoveException {
	    if(!isLegalCapture(src,trg))
		throw new IllegalChessMoveException();
	    int srcI = 8-src.charAt(1)+'0';
	    int trgI = 8-trg.charAt(1)+'0';
	    int srcJ = src.charAt(0)-'a';
	    int trgJ = trg.charAt(0)-'a';
	    board[trgI][trgJ] = board[srcI][srcJ]; 
	    board[srcI][srcJ] = new Empty();
	    whiteTurn = !whiteTurn;
	    
	}
	private boolean isLegalMove(String src, String trg) {
	    int srcJ = src.charAt(0)-'a';
	    int trgJ = trg.charAt(0)-'a';
		int srcI = 8-src.charAt(1)+'0';
	    int trgI = 8-trg.charAt(1)+'0';
	    if(srcI == srcJ && trgI == trgJ)
		return false;
	    if(board[srcI][srcJ].isEmpty() || !board[trgI][trgJ].isEmpty())
		return false;
	    if(board[srcI][srcJ].white != whiteTurn)//turn violation
		return false;
	    Piece picked = board[srcI][srcJ];
	    if(picked.toString().contains("Knt")) {//knight
		return Math.abs((srcI-trgI)*(srcJ-trgJ))== 2;
	    }else if(picked.toString().contains("Pwn")) {//pawn
		if(trgJ != srcJ)//not a vertical move
		    return false;
		if(picked.white != srcI > trgI)//moving to the wrong direction
		    return false;
		if(Math.abs(srcI-trgI) == 1)//moving one square...
		    return true;
		if(picked.white && srcI == 6 || !picked.white && srcI == 1)//starting position
		    return Math.abs(srcI-trgI) == 2 && board[srcI+(picked.white?-1:1)][srcJ].isEmpty();}
	    else if(picked.getPiece().equalsIgnoreCase("Rok")) {
				if(trgJ == srcJ)// a vertical move
				    return true;
				else if(srcI ==trgI) {
					return true;
				}else return false;
		}else if(picked.getPiece().equalsIgnoreCase("Bsp")) {
				if(srcJ!=trgJ){//not a diagonal move
						    return true;} else return false;}
		else if(picked.getPiece().equalsIgnoreCase("Qun")) {
			if((srcJ-trgJ)==(srcI-trgI)) {
				return true;
			} else if (srcJ == trgJ || srcI==trgI) {
				return true;
			} else {return false;}
		}else if(picked.getPiece().equalsIgnoreCase("Kng")) {
			if((trgJ-srcJ)>1||(srcJ-trgJ)>1 || (trgI-srcI)>1||(srcI-trgI)>1)//can move one square any direction
				return false;
		}
	    return true;
	}
	private boolean isLegalCapture(String src, String trg) {
	    int srcI = 8-src.charAt(1)+'0';
	    int trgI = 8-trg.charAt(1)+'0';
	    int srcJ = src.charAt(0)-'a';
	    int trgJ = trg.charAt(0)-'a';
	    if(srcI == srcJ && trgI == trgJ)
		return false;
	    if(board[srcI][srcJ].isEmpty() || board[trgI][trgJ].isEmpty())
		return false;
	    if(board[srcI][srcJ].white != whiteTurn)//turn violation
		return false;
	    if(board[srcI][srcJ].white == board[trgI][trgJ].white)//capturing same color
		return false;
	    Piece picked = board[srcI][srcJ];
	    if(picked.toString().contains("Knt")) {//knight
		return Math.abs((srcI-trgI)*(srcJ-trgJ))== 2;
	    }else if(picked.toString().contains("Pwn")) {//pawn
		if(Math.abs((srcJ-trgJ)*(srcI-trgI)) != 1)
		    return false;
		else if(picked.white != srcI > trgI)//moving to the wrong direction
		    return false;
		else if(Math.abs(srcI-trgI) == 1)//moving one square...
		    return true;
		else if(picked.white && srcI == 6 || !picked.white && srcI == 1)//starting position
		    return Math.abs(srcI-trgI) == 2 && board[srcI+(picked.white?-1:1)][srcJ].isEmpty();
	    }else if(picked.getPiece().equalsIgnoreCase("Rok")) {
			if(trgJ == srcJ)// a vertical move
			    return true;
			else if(srcI ==trgI) {
				return true;
			}else return false;
	}else if(picked.getPiece().equalsIgnoreCase("Bsp")) {
			if(srcJ!=trgJ){//not a diagonal move
					    return true;} else return false;}
	else if(picked.getPiece().equalsIgnoreCase("Qun")) {
		if((srcJ-trgJ)==(srcI-trgI)) {
			return true;
		} else if (srcJ == trgJ || srcI==trgI) {
			return true;
		} else {return false;}
	}else if(picked.getPiece().equalsIgnoreCase("Kng")) {
		if((trgJ-srcJ)>1||(srcJ-trgJ)>1 || (trgI-srcI)>1||(srcI-trgI)>1)//can move one square any direction
			return false;
	}
	    return true;
	}
}
