/**
 * 
 */
package awele.bot.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;
import utils.LongMethod;

public class OewaleBot2 extends CompetitorBot{
	
	public static final int OEWALE = 1;
	public static final int OPPONENT = -1;
	
	private static final double POSITIVE_INF = 100000;  //  100 000
	private static final double NEGATIVE_INF = -100000; // -100 000
	private static final double UNPLAYABLE = -1000000;  // -1 000 000
	
	private static int DEPTH = 8;
	private static double PREVIOUS_DECISION_TIME_DURATION = -100;
	private static int CPT_DEPTH = 0;

	public OewaleBot2() throws InvalidBotException {
		//this.setBotName ("test");
        //this.addAuthor ("test team");
	}
	
	@Override
	public void initialize() {
		DEPTH = 8;
		PREVIOUS_DECISION_TIME_DURATION = -100;
		CPT_DEPTH = 0;
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
				
	public int nbNodePrunned;
	
	@Override
	public double[] getDecision(Board board) throws Exception {
		double start = System.currentTimeMillis();
		CustomBoard2 Cboard = new CustomBoard2(convertBoard(board), board.getScore(board.getCurrentPlayer())); 
		double[] decision = {UNPLAYABLE,UNPLAYABLE,UNPLAYABLE,UNPLAYABLE,UNPLAYABLE,UNPLAYABLE};
		for(int i = 1; i <= 6; i++) { 
			if(Cboard.isPlayable(i, 1)) {
				CustomBoard2 copyBoard = Cboard.clone();
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
					decision[i-1] = alphabeta(copyBoard, 6, NEGATIVE_INF, POSITIVE_INF, false);
				}
			}
		}
		//updateDepth(System.currentTimeMillis() - start);
		//printBoard(convertBoard(board));
		//System.out.println("["+ decision[0] +", "+ decision[1] + ", "+ decision[2] +  ", "+ decision[3] + ", "+ decision[4] + ", "+ decision[5] +"]");
		//System.out.println("Node visited : " + nbNodeVisited);

		return decision;
	}
	
	private void updateDepth(double currentDecisionTimeDuration) {
		if(between(currentDecisionTimeDuration, PREVIOUS_DECISION_TIME_DURATION - 20, PREVIOUS_DECISION_TIME_DURATION + 20)) {
			CPT_DEPTH++;
		}else {
			CPT_DEPTH--;
		}
		PREVIOUS_DECISION_TIME_DURATION = currentDecisionTimeDuration;
		if(CPT_DEPTH == 3) {
			CPT_DEPTH = 0;
			DEPTH++;
		}else if(CPT_DEPTH == -3) {
			CPT_DEPTH = 0;
			DEPTH--;
		}
		if(PREVIOUS_DECISION_TIME_DURATION < 30) {
			DEPTH = 10;
		}
		if(PREVIOUS_DECISION_TIME_DURATION > 500) {
			DEPTH = 8;
		}
	}
	
	public boolean between(double val, double inf, double sup) {
		return inf <= val && val <= sup;
	}
	
	
	public double minmax(CustomBoard2 board, int depth, double alpha, double beta, boolean max) throws Exception {
		if(max) {
			double value = NEGATIVE_INF;
			if(depth == 0 || board.isFinish(1)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				for(int i = 1 ; i <= 6; i++) {
					if(board.isPlayable(i, 1)) {
						CustomBoard2 copyBoard = board.clone();
						boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, 1);
						copyBoard.play(i, 1, !canTakeSeeds);
						double eval = minmax(copyBoard, depth -1, alpha, beta, false);
						value = Math.max(value, eval);
						if(value > beta) {
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
				return (board.getOpponentScore() - board.getScore());
			}else {
				for(int i = 7 ; i <= 12; i++) {
					if(board.isPlayable(i, -1)) {
						CustomBoard2 copyBoard = board.clone();
						boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, -1);
						copyBoard.play(i, -1, !canTakeSeeds);
						double eval = minmax(copyBoard, depth -1, alpha, beta, true);
						value = Math.min(value, eval);
						if(alpha > value) {
							return value;
						}
						beta = Math.min(beta, value);
					}
				}
			}
			return value;
		}
	}

	public double test(CustomBoard2 board, int depth, double alpha, double beta, boolean max) throws Exception {
		if(max) {
			double value = NEGATIVE_INF;
			if(depth == 0 || board.isFinish(OEWALE)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				ArrayList<Integer> indexPlayableHole = board.getIndexOfPlayableHole(OEWALE, 0);
				HashMap<CustomBoard2, Integer> simulatedBoards = board.getSimulatedBoards(indexPlayableHole, OEWALE);
				int i = 0;
				for (Map.Entry<CustomBoard2, Integer> entry : simulatedBoards.entrySet()) {
					double eval = test(entry.getKey(), depth -1, alpha, beta, false);
					if(eval > value) {
						value = eval;
						NodesScore2.getInstance().getHashMap().put(entry.getValue(), NodesScore2.getInstance().getHashMap().get(entry.getValue()) + i);
						int j = i;
						for (Map.Entry<CustomBoard2, Integer> e : simulatedBoards.entrySet()) {
							if (j == 0 ) break;
							NodesScore2.getInstance().getHashMap().put(e.getValue(), NodesScore2.getInstance().getHashMap().get(e.getValue()) - 1);
							j--;
						}
						if(value > beta) {
							NodesScore2.getInstance().getHashMap().put(entry.getValue(), NodesScore2.getInstance().getHashMap().get(entry.getValue()) + i);
							int j2 = i;
							for (Map.Entry<CustomBoard2, Integer> e : simulatedBoards.entrySet()) {
								if (j2 == 0 ) break;
								NodesScore2.getInstance().getHashMap().put(e.getValue(), NodesScore2.getInstance().getHashMap().get(e.getValue()) - 1);
								j2--;
							}
							return value;
						}
					}
					alpha = Math.max(alpha , value);	
					i++;
				}
			}
			return value;
		}else {
			double value = POSITIVE_INF;
			if(depth == 0 || board.isFinish(OPPONENT)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				ArrayList<Integer> indexPlayableHole = board.getIndexOfPlayableHole(OPPONENT, 6);
				HashMap<CustomBoard2, Integer> simulatedBoards = board.getSimulatedBoards(indexPlayableHole, OPPONENT);
				int i = 0;
				for (Map.Entry<CustomBoard2, Integer> entry : simulatedBoards.entrySet()) {					
					double eval = test(entry.getKey(), depth -1, alpha, beta, true);
					if(eval < value) {
						value = eval;
						NodesScore2.getInstance().getHashMap().put(entry.getValue(), NodesScore2.getInstance().getHashMap().get(entry.getValue()) + i);
						int j = i;
						for (Map.Entry<CustomBoard2, Integer> e : simulatedBoards.entrySet()) {
							if (j == 0 ) break;
							NodesScore2.getInstance().getHashMap().put(e.getValue(), NodesScore2.getInstance().getHashMap().get(e.getValue()) - 1);
							j--;
						}
						if(alpha > value) {						
							NodesScore2.getInstance().getHashMap().put(entry.getValue(), NodesScore2.getInstance().getHashMap().get(entry.getValue()) + i);
							int j2 = i;
							for (Map.Entry<CustomBoard2, Integer> e : simulatedBoards.entrySet()) {
								if (j2 == 0 ) break;
								NodesScore2.getInstance().getHashMap().put(e.getValue(), NodesScore2.getInstance().getHashMap().get(e.getValue()) - 1);
								j2--;
							}
							return value;
						}
					}
					beta = Math.min(beta, value);
					i++;
				}
			}
			return value;
		}
	}
	
	public double alphabeta(CustomBoard2 board, int depth, double alpha, double beta, boolean max) throws Exception {
		if(max) {
			double value = NEGATIVE_INF;
			if(depth == 0 || board.isFinish(OEWALE)) {
				return (board.getScore() - board.getOpponentScore());
			}else {
				ArrayList<Integer> indexPlayableHole = board.getIndexOfPlayableHole(OEWALE, 0);
				HashMap<CustomBoard2, Integer> simulatedBoards = board.getSimulatedBoards(indexPlayableHole, OEWALE);
				for (Map.Entry<CustomBoard2, Integer> entry : simulatedBoards.entrySet()) {
					double eval = alphabeta(entry.getKey(), depth -1, alpha, beta, false);
					value = Math.max(value, eval);
					if(value > beta) {
						NodesScore2.getInstance().getHashMap().put(entry.getValue(), NodesScore2.getInstance().getHashMap().get(entry.getValue()) + 1);
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
				HashMap<CustomBoard2, Integer> simulatedBoards = board.getSimulatedBoards(indexPlayableHole, OPPONENT);
				for (Map.Entry<CustomBoard2, Integer> entry : simulatedBoards.entrySet()) {
					double eval = alphabeta(entry.getKey(), depth -1, alpha, beta, true);
					value = Math.min(value, eval);
					if(alpha > value) {						
						NodesScore2.getInstance().getHashMap().put(entry.getValue(), NodesScore2.getInstance().getHashMap().get(entry.getValue()) + 1);
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
