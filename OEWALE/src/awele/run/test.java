/**
 * 
 */
package awele.run;

import java.util.Comparator;

import awele.bot.oewale.CustomBoard;
import awele.bot.oewaledeprecated.Node;
import awele.core.Board;
import utils.LongMethod;

/**
 * @author toxic
 *
 */
public class test {

	/**
	 * 
	 */
	public test() {
		// TODO Auto-generated constructor stub
	}

	private static long convertBoard(Board board) {
		long lboard = 0;
		for(byte i = 0; i < 6; i++) {
			lboard = LongMethod.setIVal((byte)(i+1), (byte)board.getPlayerHoles()[i], lboard);
		}
		for(byte i = 0; i < 6 ; i++) {
			lboard = LongMethod.setIVal((byte)(6+i+1), (byte)board.getOpponentHoles()[i], lboard);
		}
		return lboard;
	}
	
	public static void printBoard(long d) {
		System.out.println(LongMethod.getIVal((byte)12, d) + " " + LongMethod.getIVal((byte)11, d) + " " + LongMethod.getIVal((byte)10, d) + " " + LongMethod.getIVal((byte)9, d)+ " " + LongMethod.getIVal((byte)8, d)+ " " + LongMethod.getIVal((byte)7, d));
		System.out.println(LongMethod.getIVal((byte)1, d) + " " + LongMethod.getIVal((byte)2, d) + " " + LongMethod.getIVal((byte)3, d) + " " + LongMethod.getIVal((byte)4, d)+ " " + LongMethod.getIVal((byte)5, d)+ " " + LongMethod.getIVal((byte)6, d));
		System.out.println();
	}
	
	private static final double POSITIVE_INF = 100000;
	private static final double NEGATIVE_INF = -100000;
	
	public static double negamax(CustomBoard board, int depth, double alpha, double beta, int player) {
		if(depth == 0 || board.isFinish(player)) {
			/*if(board.isFinish(player)) {
				if(board.isWin()) {
					return player * 50;
				}else if(board.isLose())
					return player * -50;
				else
					return 0;
			}*/
			//this.printBoard(board.getBoardData());
			//System.out.println("board.getScore() : " + board.getScore()  + "  board.getOpponentScore() : " + board.getOpponentScore() + " player : " + player);
			//System.out.println();
			return player * (board.getScore() - board.getOpponentScore());
		}
		//TODO generation de tous les coups possibles
		//TODO tri des coups
		double value = NEGATIVE_INF;
		int offset = 0;
		if(player == 1)
			offset = 6;
		for(int i = 1 ; i <= 6; i++) {
			if(board.isPlayable(i+offset, player)) {
				CustomBoard copyBoard = board.clone();
				boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, player);
				copyBoard.play(i+offset, player, canTakeSeeds);
				value = Math.max(value,  -negamax(copyBoard, depth - 1, -beta, -alpha, -player));
				alpha = Math.max(alpha, value);
				if(alpha >= beta) {
					break;
				}
			}
		}
		return value;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CustomBoard Cboard = new CustomBoard(0b0000001000010000100001000010000100001000010000100001000010000100L, 0); 
		double[] decision = new double[6];  
		for(int i = 1; i <= 6; i++) { 
			if(Cboard.isPlayable(i, 1)) { 
				CustomBoard copyBoard = Cboard.clone();
				boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, 1);
				copyBoard.play(i, 1, canTakeSeeds);
				if(copyBoard.isFinish(1)) {
					if(copyBoard.isWin()) {
						decision[i-1] = POSITIVE_INF;
					}else if(copyBoard.isLose()) {
						decision[i-1] = NEGATIVE_INF;
					}else {
						decision[i-1] = 0;
					}
				}else {
					decision[i-1] = negamax(copyBoard, 6, NEGATIVE_INF, POSITIVE_INF, 1);
				}
			}
		}
		
		/*
		Node root = new Node(0b0000000110001100011000110001100011000110001100011011110001100011L);
		System.out.println(LongMethod.toBinaryString(root.getData()));
		root.setData(LongMethod.setIVal((byte)2, (byte)5, root.getData()));
		System.out.println(LongMethod.toBinaryString(root.getData()));
		*/
		
		/*Board board = new Board();
		board.currentPlayer = 0;
		board.holes[0][0] = 1;
		board.holes[0][1] = 1;
		board.holes[0][2] = 1;
		board.holes[0][3] = 1;
		board.holes[0][4] = 1;
		board.holes[0][5] = 1;
		
		board.holes[1][0] = 1;
		board.holes[1][1] = 1;
		board.holes[1][2] = 1;
		board.holes[1][3] = 1;
		board.holes[1][4] = 1;
		board.holes[1][5] = 5;
		CustomBoard Cboard = new CustomBoard(convertBoard(board), board.getScore(board.getCurrentPlayer())); 
		printBoard(Cboard.getBoardData());
		boolean canTakeSeeds = Cboard.isOpponentStarvingAfterPlaying(12, 1);
		System.out.println("CanTake : " + canTakeSeeds);
		Cboard.play(12, 1, canTakeSeeds);
		printBoard(Cboard.getBoardData());*/
		
		
		
		/*
		Node root = new Node(0b0000001000010000100001000010000100001000010000100001000010000100L);
		//System.out.println("root avant MinMax :");
		//System.out.println(root);
		byte nodeValue = root.MinMax((byte)2, (byte)-1);
		//System.out.println("nodeValue : " + nodeValue);
		//System.out.println("root apres MinMax :");
		
		root.setNodeScore((byte)0);
		root.getChildren(0).setNodeScore((byte)10);
		root.getChildren(1).setNodeScore((byte)3);
		root.getChildren(2).setNodeScore((byte)5);
		root.getChildren(3).setNodeScore((byte)7);
		root.getChildren(4).setNodeScore((byte)6);
		root.getChildren(5).setNodeScore((byte)2);
		
		root.getChildren(0).getChildren(0).setNodeScore((byte)1);
		root.getChildren(0).getChildren(1).setNodeScore((byte)3);
		root.getChildren(0).getChildren(2).setNodeScore((byte)5);
		root.getChildren(0).getChildren(3).setNodeScore((byte)7);
		root.getChildren(0).getChildren(4).setNodeScore((byte)2);
		root.getChildren(0).getChildren(5).setNodeScore((byte)10);
		
		root.getChildren(1).getChildren(0).setNodeScore((byte)2);
		root.getChildren(1).getChildren(1).setNodeScore((byte)4);
		root.getChildren(1).getChildren(2).setNodeScore((byte)9);
		root.getChildren(1).getChildren(3).setNodeScore((byte)3);
		root.getChildren(1).getChildren(4).setNodeScore((byte)2);
		root.getChildren(1).getChildren(5).setNodeScore((byte)5);
		
		root.getChildren(2).getChildren(0).setNodeScore((byte)1);
		root.getChildren(2).getChildren(1).setNodeScore((byte)5);
		root.getChildren(2).getChildren(2).setNodeScore((byte)5);
		root.getChildren(2).getChildren(3).setNodeScore((byte)2);
		root.getChildren(2).getChildren(4).setNodeScore((byte)8);
		root.getChildren(2).getChildren(5).setNodeScore((byte)3);
		
		root.getChildren(3).getChildren(0).setNodeScore((byte)-2);
		root.getChildren(3).getChildren(1).setNodeScore((byte)-10);
		root.getChildren(3).getChildren(2).setNodeScore((byte)3);
		root.getChildren(3).getChildren(3).setNodeScore((byte)0);
		root.getChildren(3).getChildren(4).setNodeScore((byte)2);
		root.getChildren(3).getChildren(5).setNodeScore((byte)4);
		
		root.getChildren(4).getChildren(0).setNodeScore((byte)-1);
		root.getChildren(4).getChildren(1).setNodeScore((byte)-3);
		root.getChildren(4).getChildren(2).setNodeScore((byte)-5);
		root.getChildren(4).getChildren(3).setNodeScore((byte)-7);
		root.getChildren(4).getChildren(4).setNodeScore((byte)-2);
		root.getChildren(4).getChildren(5).setNodeScore((byte)-10);
		
		root.getChildren(5).getChildren(0).setNodeScore((byte)8);
		root.getChildren(5).getChildren(1).setNodeScore((byte)-3);
		root.getChildren(5).getChildren(2).setNodeScore((byte)5);
		root.getChildren(5).getChildren(3).setNodeScore((byte)9);
		root.getChildren(5).getChildren(4).setNodeScore((byte)2);
		root.getChildren(5).getChildren(5).setNodeScore((byte)1);
		
		
		System.out.println("Avant tri");
		System.out.println(root);
		System.out.println();
		System.out.println("fils 1 " + root.getChildren(0) + " " + root.getChildren(0).getNodeScore()); 
		System.out.println("fils 2 " + root.getChildren(1) + " " + root.getChildren(1).getNodeScore());
		System.out.println("fils 3 " + root.getChildren(2) + " " + root.getChildren(2).getNodeScore()); 
		System.out.println("fils 4 " + root.getChildren(3) + " " + root.getChildren(3).getNodeScore()); 
		System.out.println("fils 5 " + root.getChildren(4) + " " + root.getChildren(4).getNodeScore()); 
		System.out.println("fils 6 " + root.getChildren(5) + " " + root.getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 1.1 " + root.getChildren(0).getChildren(0) + " " + root.getChildren(0).getChildren(0).getNodeScore());
		System.out.println("fils 1.2 " + root.getChildren(0).getChildren(1) + " " + root.getChildren(0).getChildren(1).getNodeScore());
		System.out.println("fils 1.3 " + root.getChildren(0).getChildren(2) + " " + root.getChildren(0).getChildren(2).getNodeScore()); 
		System.out.println("fils 1.4 " + root.getChildren(0).getChildren(3) + " " + root.getChildren(0).getChildren(3).getNodeScore()); 
		System.out.println("fils 1.5 " + root.getChildren(0).getChildren(4) + " " + root.getChildren(0).getChildren(4).getNodeScore()); 
		System.out.println("fils 1.6 " + root.getChildren(0).getChildren(5) + " " + root.getChildren(0).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 2.1 " + root.getChildren(1).getChildren(0) + " " + root.getChildren(1).getChildren(0).getNodeScore());
		System.out.println("fils 2.2 " + root.getChildren(1).getChildren(1) + " " + root.getChildren(1).getChildren(1).getNodeScore());
		System.out.println("fils 2.3 " + root.getChildren(1).getChildren(2) + " " + root.getChildren(1).getChildren(2).getNodeScore()); 
		System.out.println("fils 2.4 " + root.getChildren(1).getChildren(3) + " " + root.getChildren(1).getChildren(3).getNodeScore()); 
		System.out.println("fils 2.5 " + root.getChildren(1).getChildren(4) + " " + root.getChildren(1).getChildren(4).getNodeScore()); 
		System.out.println("fils 2.6 " + root.getChildren(1).getChildren(5) + " " + root.getChildren(1).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 3.1 " + root.getChildren(2).getChildren(0) + " " + root.getChildren(2).getChildren(0).getNodeScore());
		System.out.println("fils 3.2 " + root.getChildren(2).getChildren(1) + " " + root.getChildren(2).getChildren(1).getNodeScore());
		System.out.println("fils 3.3 " + root.getChildren(2).getChildren(2) + " " + root.getChildren(2).getChildren(2).getNodeScore()); 
		System.out.println("fils 3.4 " + root.getChildren(2).getChildren(3) + " " + root.getChildren(2).getChildren(3).getNodeScore()); 
		System.out.println("fils 3.5 " + root.getChildren(2).getChildren(4) + " " + root.getChildren(2).getChildren(4).getNodeScore()); 
		System.out.println("fils 3.6 " + root.getChildren(2).getChildren(5) + " " + root.getChildren(2).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 4.1 " + root.getChildren(3).getChildren(0) + " " + root.getChildren(3).getChildren(0).getNodeScore());
		System.out.println("fils 4.2 " + root.getChildren(3).getChildren(1) + " " + root.getChildren(3).getChildren(1).getNodeScore());
		System.out.println("fils 4.3 " + root.getChildren(3).getChildren(2) + " " + root.getChildren(3).getChildren(2).getNodeScore()); 
		System.out.println("fils 4.4 " + root.getChildren(3).getChildren(3) + " " + root.getChildren(3).getChildren(3).getNodeScore()); 
		System.out.println("fils 4.5 " + root.getChildren(3).getChildren(4) + " " + root.getChildren(3).getChildren(4).getNodeScore()); 
		System.out.println("fils 4.6 " + root.getChildren(3).getChildren(5) + " " + root.getChildren(3).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 5.1 " + root.getChildren(4).getChildren(0) + " " + root.getChildren(4).getChildren(0).getNodeScore());
		System.out.println("fils 5.2 " + root.getChildren(4).getChildren(1) + " " + root.getChildren(4).getChildren(1).getNodeScore());
		System.out.println("fils 5.3 " + root.getChildren(4).getChildren(2) + " " + root.getChildren(4).getChildren(2).getNodeScore()); 
		System.out.println("fils 5.4 " + root.getChildren(4).getChildren(3) + " " + root.getChildren(4).getChildren(3).getNodeScore()); 
		System.out.println("fils 5.5 " + root.getChildren(4).getChildren(4) + " " + root.getChildren(4).getChildren(4).getNodeScore()); 
		System.out.println("fils 5.6 " + root.getChildren(4).getChildren(5) + " " + root.getChildren(4).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 6.1 " + root.getChildren(5).getChildren(0) + " " + root.getChildren(5).getChildren(0).getNodeScore());
		System.out.println("fils 6.2 " + root.getChildren(5).getChildren(1) + " " + root.getChildren(5).getChildren(1).getNodeScore());
		System.out.println("fils 6.3 " + root.getChildren(5).getChildren(2) + " " + root.getChildren(5).getChildren(2).getNodeScore()); 
		System.out.println("fils 6.4 " + root.getChildren(5).getChildren(3) + " " + root.getChildren(5).getChildren(3).getNodeScore()); 
		System.out.println("fils 6.5 " + root.getChildren(5).getChildren(4) + " " + root.getChildren(5).getChildren(4).getNodeScore()); 
		System.out.println("fils 6.6 " + root.getChildren(5).getChildren(5) + " " + root.getChildren(5).getChildren(5).getNodeScore());
		
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		root.getChildren(0).getChildren(0).sortNode((byte)1);
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Apr�s tri");
		System.out.println(root + " " + root.getNodeScore());
		System.out.println();
		System.out.println("fils 1 " + root.getChildren(0) + " " + root.getChildren(0).getNodeScore()); 
		System.out.println("fils 2 " + root.getChildren(1) + " " + root.getChildren(1).getNodeScore());
		System.out.println("fils 3 " + root.getChildren(2) + " " + root.getChildren(2).getNodeScore()); 
		System.out.println("fils 4 " + root.getChildren(3) + " " + root.getChildren(3).getNodeScore()); 
		System.out.println("fils 5 " + root.getChildren(4) + " " + root.getChildren(4).getNodeScore()); 
		System.out.println("fils 6 " + root.getChildren(5) + " " + root.getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 1.1 " + root.getChildren(0).getChildren(0) + " " + root.getChildren(0).getChildren(0).getNodeScore());
		System.out.println("fils 1.2 " + root.getChildren(0).getChildren(1) + " " + root.getChildren(0).getChildren(1).getNodeScore());
		System.out.println("fils 1.3 " + root.getChildren(0).getChildren(2) + " " + root.getChildren(0).getChildren(2).getNodeScore()); 
		System.out.println("fils 1.4 " + root.getChildren(0).getChildren(3) + " " + root.getChildren(0).getChildren(3).getNodeScore()); 
		System.out.println("fils 1.5 " + root.getChildren(0).getChildren(4) + " " + root.getChildren(0).getChildren(4).getNodeScore()); 
		System.out.println("fils 1.6 " + root.getChildren(0).getChildren(5) + " " + root.getChildren(0).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 2.1 " + root.getChildren(1).getChildren(0) + " " + root.getChildren(1).getChildren(0).getNodeScore());
		System.out.println("fils 2.2 " + root.getChildren(1).getChildren(1) + " " + root.getChildren(1).getChildren(1).getNodeScore());
		System.out.println("fils 2.3 " + root.getChildren(1).getChildren(2) + " " + root.getChildren(1).getChildren(2).getNodeScore()); 
		System.out.println("fils 2.4 " + root.getChildren(1).getChildren(3) + " " + root.getChildren(1).getChildren(3).getNodeScore()); 
		System.out.println("fils 2.5 " + root.getChildren(1).getChildren(4) + " " + root.getChildren(1).getChildren(4).getNodeScore()); 
		System.out.println("fils 2.6 " + root.getChildren(1).getChildren(5) + " " + root.getChildren(1).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 3.1 " + root.getChildren(2).getChildren(0) + " " + root.getChildren(2).getChildren(0).getNodeScore());
		System.out.println("fils 3.2 " + root.getChildren(2).getChildren(1) + " " + root.getChildren(2).getChildren(1).getNodeScore());
		System.out.println("fils 3.3 " + root.getChildren(2).getChildren(2) + " " + root.getChildren(2).getChildren(2).getNodeScore()); 
		System.out.println("fils 3.4 " + root.getChildren(2).getChildren(3) + " " + root.getChildren(2).getChildren(3).getNodeScore()); 
		System.out.println("fils 3.5 " + root.getChildren(2).getChildren(4) + " " + root.getChildren(2).getChildren(4).getNodeScore()); 
		System.out.println("fils 3.6 " + root.getChildren(2).getChildren(5) + " " + root.getChildren(2).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 4.1 " + root.getChildren(3).getChildren(0) + " " + root.getChildren(3).getChildren(0).getNodeScore());
		System.out.println("fils 4.2 " + root.getChildren(3).getChildren(1) + " " + root.getChildren(3).getChildren(1).getNodeScore());
		System.out.println("fils 4.3 " + root.getChildren(3).getChildren(2) + " " + root.getChildren(3).getChildren(2).getNodeScore()); 
		System.out.println("fils 4.4 " + root.getChildren(3).getChildren(3) + " " + root.getChildren(3).getChildren(3).getNodeScore()); 
		System.out.println("fils 4.5 " + root.getChildren(3).getChildren(4) + " " + root.getChildren(3).getChildren(4).getNodeScore()); 
		System.out.println("fils 4.6 " + root.getChildren(3).getChildren(5) + " " + root.getChildren(3).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 5.1 " + root.getChildren(4).getChildren(0) + " " + root.getChildren(4).getChildren(0).getNodeScore());
		System.out.println("fils 5.2 " + root.getChildren(4).getChildren(1) + " " + root.getChildren(4).getChildren(1).getNodeScore());
		System.out.println("fils 5.3 " + root.getChildren(4).getChildren(2) + " " + root.getChildren(4).getChildren(2).getNodeScore()); 
		System.out.println("fils 5.4 " + root.getChildren(4).getChildren(3) + " " + root.getChildren(4).getChildren(3).getNodeScore()); 
		System.out.println("fils 5.5 " + root.getChildren(4).getChildren(4) + " " + root.getChildren(4).getChildren(4).getNodeScore()); 
		System.out.println("fils 5.6 " + root.getChildren(4).getChildren(5) + " " + root.getChildren(4).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 6.1 " + root.getChildren(5).getChildren(0) + " " + root.getChildren(5).getChildren(0).getNodeScore());
		System.out.println("fils 6.2 " + root.getChildren(5).getChildren(1) + " " + root.getChildren(5).getChildren(1).getNodeScore());
		System.out.println("fils 6.3 " + root.getChildren(5).getChildren(2) + " " + root.getChildren(5).getChildren(2).getNodeScore()); 
		System.out.println("fils 6.4 " + root.getChildren(5).getChildren(3) + " " + root.getChildren(5).getChildren(3).getNodeScore()); 
		System.out.println("fils 6.5 " + root.getChildren(5).getChildren(4) + " " + root.getChildren(5).getChildren(4).getNodeScore()); 
		System.out.println("fils 6.6 " + root.getChildren(5).getChildren(5) + " " + root.getChildren(5).getChildren(5).getNodeScore());
		
		/*
		System.out.println("------------------------------");

		Node a = root.getChildren(0).getChildren(5).play((byte)8, (byte)1);

		System.out.println(LongMethod.toBinaryString(a.getData()));
		*/
		
		/*
		Node a = new Node(0b0000001010000000000000000000000000000110001000010000100001000010L);
		Node b = a.play((byte)11, (byte)1);
		System.out.println(LongMethod.toBinaryString(b.getData()));
		*/
		
		/* 
		Node a = new Node(0b0000000000000000000000000000000000000000000000000000000000000011L);
		System.out.println(a.getNbSeedInAnyHole((byte)1));
		System.out.println(a.getOpponentNbSeeds());
		System.out.println(a.isPlayable((byte)0));
		System.out.println(a.isFinish());
		System.out.println(a.isWin());
		System.out.println(a.getOurScore());
		System.out.println(a.getOpponentScore());
		*/
	}

}
