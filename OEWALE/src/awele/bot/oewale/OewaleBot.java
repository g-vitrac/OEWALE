/**
 * 
 */
package awele.bot.oewale;

import java.util.Random;

import awele.bot.CompetitorBot;
import awele.bot.oewaledeprecated.Node;
import awele.core.Awele;
import awele.core.Board;
import awele.core.InvalidBotException;
import utils.LongMethod;

public class OewaleBot extends CompetitorBot{
	
	private static final int MAX_DECISION_TIME = 100; // 100ms
	private static final int MAX_LEARNING_TIME = 1000 * 60 * 60 * 1; // 1 h
	private static final int MAX_PHASE1_LEARNING_TIME = 100 * 49 * 60 * 1; // 49 minutes
	private static final int MAX_PHASE2_LEARNING_TIME = 100 * 9 * 60 * 1; // 9 minutes
		
	private static final int inf = 1000000000;
	
	private double maxLearningScore;
	private double scoreGame;
	private double coefA;
	private double coefB;
	private double coefC;
	private double coefD;
	
	private OewaleBot botForTraining;
	
	// GETTERS and SETTERS
	
	public double getMaxLearningScore() {
		return maxLearningScore;
	}


	public void setMaxLearningScore(double maxLearningScore) {
		this.maxLearningScore = maxLearningScore;
	}


	public double getScoreGame() {
		return scoreGame;
	}


	public void setScoreGame(double scoreGame) {
		this.scoreGame = scoreGame;
	}


	public double getCoefA() {
		return coefA;
	}


	public void setCoefA(double coefA) {
		this.coefA = coefA;
	}


	public double getCoefB() {
		return coefB;
	}


	public void setCoefB(double coefB) {
		this.coefB = coefB;
	}


	public double getCoefC() {
		return coefC;
	}


	public void setCoefC(double coefC) {
		this.coefC = coefC;
	}


	public double getCoefD() {
		return coefD;
	}


	public void setCoefD(double coefD) {
		this.coefD = coefD;
	}
	
	public double[] getCoefs() {
		double[] coefs = new double[4];
		coefs[0] = this.coefA;
		coefs[1] = this.coefB;
		coefs[2] = this.coefC;
		coefs[3] = this.coefD;
		return coefs;
	}
	
	public void setCoefs(double[] coefs) {
		this.coefA = coefs[0];
		this.coefB = coefs[1];
		this.coefC = coefs[2];
		this.coefD = coefs[3];
	}

	// CONSTRUCTOR
	
	public OewaleBot() throws InvalidBotException {
		this.setBotName ("Oewale");
        this.addAuthor ("Wati team");
	}
	
	// BOT METHODS
	
	@Override
	public void initialize() {
		this.setScoreGame(0);
		this.botForTraining.setScoreGame(0);
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
		double remainingTime = 0;
		double start = System.currentTimeMillis();
		while(remainingTime < MAX_PHASE1_LEARNING_TIME) {
			findBestCoef((byte)0.5);
			remainingTime += start - System.currentTimeMillis();
		}
		while(remainingTime < MAX_PHASE2_LEARNING_TIME) {
			findBestCoef((byte)0.1);
			remainingTime += start - System.currentTimeMillis();
		}
	}
	
	// UTILS METHODS
	
	public void findBestCoef(byte variationMax) {
		this.setScoreGame(0); 
		botForTraining.setScoreGame(0);
		Awele awele = new Awele (this, botForTraining);
	    try {
			awele.play();
		} catch (InvalidBotException e) {
			e.printStackTrace();
		}
	    double scoreA = this.getScoreGame();
	    if(scoreA > this.getMaxLearningScore()){
	    	this.setMaxLearningScore(scoreA);
	    	botForTraining.setCoefs(this.getCoefs());
		}
		this.coefVariation(variationMax);
	}
	
	private void coefVariation(byte variationMax) {
		this.coefA = this.newCoef(this.coefA, variationMax);
		this.coefB = this.newCoef(this.coefB, variationMax);
		this.coefC = this.newCoef(this.coefC, variationMax);
		this.coefD = this.newCoef(this.coefD, variationMax);
	}
	
	private double newCoef(double coef, byte variationMax) {
		double minLimit = coef - variationMax;
	    double maxLimit = coef + variationMax;
	    return minLimit + new Random().nextDouble() * (maxLimit - minLimit);
	}
	
	public void play(long board, byte i, byte max) {
		byte holeIndex =  (byte) (i + 1);
		long newData = board;
		byte nbSeedInHole = this.getNbSeedInAnyHole(board, holeIndex);
		byte indexLastHole = (byte)(((holeIndex + nbSeedInHole) % 12));
		byte cpt = (byte) (holeIndex + 1);
		if(cpt > 12) cpt = 1;
		for(byte j = nbSeedInHole; j > 0; j--) {
			newData = LongMethod.setIVal(cpt, (byte)(LongMethod.getIVal(cpt, newData)+1), newData);
			cpt++;
			if(cpt > 12) cpt = 1;
			if(cpt == holeIndex) cpt++;
		}
		newData = LongMethod.setIVal(holeIndex, (byte)0, newData);
		if(max == -1 && indexLastHole > 6) {

			for(byte j = indexLastHole; j >= 7; j--) {
				byte nb = LongMethod.getIVal(j, newData);
				if( nb == 2 || nb == 3) {
					this.scoreGame += nb;
					newData = LongMethod.setIVal(j, (byte)0, newData);

				}
				else {
					break;
				}
			}
		}else if(max == 1 && indexLastHole < 6) {
			for(byte j = indexLastHole; j >= 0; j--) {
				byte nb = LongMethod.getIVal(j, newData);
				if( nb == 2 || nb == 3) {
					newData = LongMethod.setIVal(j, (byte)0, newData);
				}
				else {
					break;
				}
			}
		}
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
	
	public byte getOurNbSeeds(long board) {
		byte sum = 0;
		for(byte i = 1; i <= 6; i++) {
			sum += LongMethod.getIVal((byte)i, board);
		}
		return sum;
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
	
	public byte getOpponentScore(long board) {
		return (byte) (48 - this.getScoreGame() - this.getOurNbSeeds(board) - this.getOpponentNbSeeds(board));  
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
					// TU TEST DEUX FOIS IF(MAX) C PAS OPTI MEME SI Y A REDONDANCE DE CODE OSEF MAIS FAIT PEUT ETRE UN IF(MAX) ELSE ET TU DUPLIQUES OSEF
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
