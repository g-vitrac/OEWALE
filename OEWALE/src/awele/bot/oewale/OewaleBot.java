/**
 * 
 */
package awele.bot.oewale;

import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;
import utils.LongMethod;

public class OewaleBot extends CompetitorBot{
	
	private static final int inf = 1000000000;
	
	private static final int MAX_DECISION_TIME = 100; // 100ms
	 private static final int MAX_LEARNING_TIME = 1000 * 60 * 60 * 1; // 1 h
	
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
		double[] nextBoard = new double[6];long end = System.currentTimeMillis();
		long remainingTime = MAX_DECISION_TIME - end - start - 5;
		
		return nextBoard;
	}

	@Override
	public void learn() {
		
	}
	
	public boolean isPlayable(long board, byte i) {
		
		if(LongMethod.getIVal((byte)(i+1), board) == 0) {
			return false;
		}
		boolean playable = true;
		if(this.getOpponentNbSeeds(board) == 0 && this.getNbSeedInAnyHole(board, (byte)(i+1)) + i <= 6) // if our opponent is starving and we can give him seed 
			playable = false;
		return playable;
	}
	
	public byte getOpponentNbSeeds(long board) {
		byte sum = 0;
		for(byte i = 7; i <= 12; i++) {
			sum += LongMethod.getIVal((byte)i, board);
		}
		return sum;
	}
	
	public byte getNbSeedInAnyHole(long board, byte i) {
		return LongMethod.getIVal((byte)i, board);
	}
	
	
	
	public int alphabeta(long board, boolean max, int alpha, int beta) {
		int bestVal;
		if(max)
			bestVal = -inf;
		else
			bestVal = inf;
		
		for(byte i = 0; i < 6; i ++) {
			if(isPlayable(board, i)) {
				if(max) {
					
				}else {
					
				}
			}
		}
		
		return 0;
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
