package awele.bot.oewale;

import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;
import utils.LongMethod;

public class OewaleBot extends CompetitorBot{
	
	private static final int MAX_DECISION_TIME = 100; // 100ms
	 private static final int MAX_LEARNING_TIME = 1000 * 60 * 60 * 1; // 1 h
	
	private Node root;
	
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

	@Override
	public double[] getDecision(Board board) {
		long start = System.currentTimeMillis();
		long lboard = convertBoard(board); //board to long conversion 
		Node actualState = root.search(lboard); //search long in the minmax tree
		Node bestMove = actualState.getChildren(0); // because our best move is always the first
		double[] nextBoard = new double[6];
		nextBoard[bestMove.getIndexPlayedHole()] = 1;
		root = bestMove.pruning(); // removing all useless nodes in the tree where best move is now the root and update root field
		long end = System.currentTimeMillis();
		long remainingTime = MAX_DECISION_TIME - end - start - 5;
		bestMove.developMinMax(remainingTime, (byte)1);	
		return nextBoard;
	}

	@Override
	public void learn() {
		root = new Node(0b0000001000010000100001000010000100001000010000100001000010000100L);
		root.developMinMax(50, (byte)-1);
	}
	
	private long convertBoard(Board board) {
		long lboard = 0;
		for(byte i = 0; i < 6; i++)
			lboard = LongMethod.setIVal((byte)(i+1), (byte)board.getPlayerHoles()[i], lboard);
		for(byte i = 0; i < 6 ; i++)
			lboard = LongMethod.setIVal((byte)(6+i+1), (byte)board.getOpponentHoles()[i], lboard);
		return lboard;
	}
}
