package awele.bot.oewale;

import awele.bot.CompetitorBot;
import awele.core.Board;

public class OewaleBot extends CompetitorBot{
	
	private static final int MAX_DECISION_TIME = 100; // 100ms
	 private static final int MAX_LEARNING_TIME = 1000 * 60 * 60 * 1; // 1 h
	
	private Node root;
	

	@Override
	public void initialize() {
		
	}

	@Override
	public void finish() {
		
	}

	@Override
	public double[] getDecision(Board board) {
		long start = System.currentTimeMillis();
		//board to long conversion 
		long lboard = convertBoard(board);
		//search long in the minmax tree
		Node actualState = root.search(lboard);
		Node bestMove = actualState.findBestMove(); // y'a un monde ou suivant comment on fait notre algo le bestMove est direct placé en 1er place dans la liste des enfants
		double[] nextBoard = convertBestMove(Node);
		bestMove.pruning(); // removing all useless nodes in the tree where best move is now the root 
		bestMove.orderChildren(); // order all the childrens 
		long end = System.currentTimeMillis();
		long remainingTime = MAX_DECISION_TIME - end - start;
		while(remainingTime < MAX_DECISION_TIME - 5) { // to be sure that we have enough time to return a result in less than 100ms
			start = System.currentTimeMillis();
			bestMove.developMinMax();
			remainingTime = MAX_DECISION_TIME - System.currentTimeMillis() - start;
		}
		return nextBoard;
	}

	@Override
	public void learn() {
		root = new Node(0b0000001000010000100001000010000100001000010000100001000010000100L);
	}

}
