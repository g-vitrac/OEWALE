/**
 * 
 */
package awele.bot.oewale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;
import utils.LongMethod;

public class OewaleBot extends CompetitorBot{
	
	public static final int OEWALE = 1;
	public static final int OPPONENT = -1;
	
	private static final int MAX_DECISION_TIME = 100; // 100ms
	private static final int MAX_LEARNING_TIME = 1000 * 60 * 60 * 1; // 1 h
		
	private static final double POSITIVE_INF = 100000;  //  100 000
	private static final double NEGATIVE_INF = -100000; // -100 000
	private static final double UNPLAYABLE = -1000000;  // -1 000 000
	
	
	
	public static int nbNodesVisited = 0; // for debug
	public static int nbNodesPrunned = 0; // for debug
		
	public OewaleBot() throws InvalidBotException {
		this.setBotName ("Oewale");
        this.addAuthor ("Wati team");
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public void finish() {
		
	}

	private long convertBoard(Board board) {
		long lboard = 0;
		for(byte i = 0; i < 6; i++)
			lboard = LongMethod.setIVal((byte)(i+1), (byte)board.getPlayerHoles()[i], lboard);
		for(byte i = 0; i < 6 ; i++)
			lboard = LongMethod.setIVal((byte)(6+i+1), (byte)board.getOpponentHoles()[i], lboard);
		return lboard;
	}
	
	public void printBoard(long d) throws Exception {
		System.out.println(LongMethod.getIVal((byte)12, d) + " " + LongMethod.getIVal((byte)11, d) + " " + LongMethod.getIVal((byte)10, d) + " " + LongMethod.getIVal((byte)9, d)+ " " + LongMethod.getIVal((byte)8, d)+ " " + LongMethod.getIVal((byte)7, d));
		System.out.println(LongMethod.getIVal((byte)1, d) + " " + LongMethod.getIVal((byte)2, d) + " " + LongMethod.getIVal((byte)3, d) + " " + LongMethod.getIVal((byte)4, d)+ " " + LongMethod.getIVal((byte)5, d)+ " " + LongMethod.getIVal((byte)6, d));
		System.out.println();
	}
		
	@Override
	public double[] getDecision(Board board) throws Exception {
		//nbNodesVisited = 0;
		//nbNodesPrunned = 0;
		//System.out.println("-------------"+board.getCurrentPlayer()+"---------------------------------------");
		//System.out.println("On recoit le board :");
		//System.out.println(LongMethod.toBinaryString(convertBoard(board)));
		//printBoard(this.convertBoard(board));
		//System.out.println("Notre score : " + board.getScore(board.getCurrentPlayer()) + " Adversaire score : " + board.getScore(1-board.getCurrentPlayer()));
		//System.out.println();
		CustomBoard Cboard = new CustomBoard(convertBoard(board), board.getScore(board.getCurrentPlayer())); 
		double[] decision = new double[6];
		for(int i = 0; i < 6; i++) decision[i] = UNPLAYABLE;
		for(int i = 1; i <= 6; i++) { 
			if(Cboard.isPlayable(i, 1)) { 
				nbNodesVisited++;
				CustomBoard copyBoard = Cboard.clone();
				boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, OEWALE);
				copyBoard.play(i, OEWALE, !canTakeSeeds);
				if(copyBoard.isFinish(OEWALE)) {
					if(copyBoard.isWin()) {
						decision[i-1] = POSITIVE_INF;
					}else if(copyBoard.isLose()) {
						decision[i-1] = NEGATIVE_INF;
					}else {
						decision[i-1] = 0;
					}
				}else {
					decision[i-1] = alphabeta(copyBoard, 8, NEGATIVE_INF, POSITIVE_INF, false);
				}
			}
		}
		//System.out.println("Evaluation des coups :");
		//System.out.println(decision[0] + " " + decision[1] + " " + decision[2] + " " + decision[3] + " " + decision[4] + " " + decision[5]);
		//System.out.println("Nb noeud explorés : " + nbNodesVisited);
		//System.out.println("Nb noeud élagués  : " + nbNodesPrunned);
		return decision;
	}
	
	public double minmax(CustomBoard board, int depth, double alpha, double beta, boolean max) throws Exception {
		if(max) {
			double value = NEGATIVE_INF;
			if(depth == 0 || board.isFinish(1)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				for(int i = 1 ; i <= 6; i++) {
					if(board.isPlayable(i, 1)) {
						CustomBoard copyBoard = board.clone();
						boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, 1);
						copyBoard.play(i, 1, !canTakeSeeds);
						double eval = minmax(copyBoard, depth -1, alpha, beta, false);
						value = Math.max(value, eval);
						if(value > beta) {
							nbNodesPrunned++;
							return value;
						}
						alpha = Math.max(alpha , value);
					}
				}
			}
			return value;
		}else {
			double value = POSITIVE_INF;
			if(depth == 0 || board.isFinish(-1)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				for(int i = 7 ; i <= 12; i++) {
					if(board.isPlayable(i, -1)) {
						CustomBoard copyBoard = board.clone();
						boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, -1);
						copyBoard.play(i, -1, !canTakeSeeds);
						double eval = minmax(copyBoard, depth -1, alpha, beta, true);
						value = Math.min(value, eval);
						if(alpha > value) {
							nbNodesPrunned++;
							return value;
						}
						beta = Math.min(beta, value);
					}
				}
			}
			return value;
		}
	}
	
	public double alphabeta(CustomBoard board, int depth, double alpha, double beta, boolean max) throws Exception {
		if(max) {
			double value = NEGATIVE_INF;
			if(depth == 0 || board.isFinish(OEWALE)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				ArrayList<Integer> indexPlayableHole = board.getIndexOfPlayableHole(OEWALE, 0);
				HashMap<CustomBoard, Integer> simulatedBoards = board.getSimulatedBoards(indexPlayableHole, OEWALE);
				Map<CustomBoard, Integer> map = simulatedBoards;
				for (Map.Entry<CustomBoard, Integer> entry : map.entrySet()) {
					nbNodesVisited++;
					double eval = alphabeta(entry.getKey(), depth -1, alpha, beta, false);
					value = Math.max(value, eval);
					if(value > beta) {
						NodesScore.getInstance().getHashMap().put(entry.getValue(), NodesScore.getInstance().getHashMap().get(entry.getValue()) + 1);
						nbNodesPrunned++;
						return value;
					}
					alpha = Math.max(alpha , value);				
				}
			}
			return value;
		}else {
			double value = POSITIVE_INF;
			if(depth == 0 || board.isFinish(OPPONENT)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				ArrayList<Integer> indexPlayableHole = board.getIndexOfPlayableHole(OPPONENT, 6);
				HashMap<CustomBoard, Integer> simulatedBoards = board.getSimulatedBoards(indexPlayableHole, OPPONENT);
				Map<CustomBoard, Integer> map = simulatedBoards;				
				for (Map.Entry<CustomBoard, Integer> entry : map.entrySet()) {					
					nbNodesVisited++;
					double eval = alphabeta(entry.getKey(), depth -1, alpha, beta, true);
					value = Math.min(value, eval);
					if(alpha > value) {						
						NodesScore.getInstance().getHashMap().put(entry.getValue(), NodesScore.getInstance().getHashMap().get(entry.getValue()) + 1);
						nbNodesPrunned++;
						return value;
					}
					beta = Math.min(beta, value);
				}
			}
			return value;
		}
	}
	
	@Override
	public void learn() {
		
	}	
}
