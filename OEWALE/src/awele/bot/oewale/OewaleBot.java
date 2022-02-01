/**
 * 
 */
package awele.bot.oewale;

import java.util.Random;

import awele.bot.CompetitorBot;
import awele.core.Awele;
import awele.core.Board;
import awele.core.InvalidBotException;
import utils.LongMethod;

public class OewaleBot extends CompetitorBot{
	
	private static final int MAX_DECISION_TIME = 100; // 100ms
	private static final int MAX_LEARNING_TIME = 1000 * 60 * 60 * 1; // 1 h
	private static final int MAX_PHASE1_LEARNING_TIME = 100 * 49 * 60 * 1; // 49 minutes
	private static final int MAX_PHASE2_LEARNING_TIME = 100 * 9 * 60 * 1; // 9 minutes
	
	private static final int MAX_PHASE1_LEARNING_TIME_FOR_TEST = 30000; // 1 minutes
	private static final int MAX_PHASE2_LEARNING_TIME_FOR_TEST = 10000; // 30 secondes
		
	private static final double POSITIVE_INF = 100000;
	private static final double NEGATIVE_INF = -100000;
	
	private double maxLearningScore;
	private double scoreGame;
	private double coefA;
	private double coefB;
	private double coefC;
	private double coefD;
	
	// CONSTRUCTOR
	
	public OewaleBot() throws InvalidBotException {
		this.setBotName ("Oewale");
        this.addAuthor ("Wati team");
        this.coefA = 1;
        this.coefB = 1;
        this.coefC = 1;
        this.coefD = 1;
	}
	
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
	
	// BOT METHODS
	
	@Override
	public void initialize() {
	}

	@Override
	public void finish() {
		
	}

	public void printBoard(long d) {
		System.out.println(LongMethod.getIVal((byte)12, d) + " " + LongMethod.getIVal((byte)11, d) + " " + LongMethod.getIVal((byte)10, d) + " " + LongMethod.getIVal((byte)9, d)+ " " + LongMethod.getIVal((byte)8, d)+ " " + LongMethod.getIVal((byte)7, d));
		System.out.println(LongMethod.getIVal((byte)1, d) + " " + LongMethod.getIVal((byte)2, d) + " " + LongMethod.getIVal((byte)3, d) + " " + LongMethod.getIVal((byte)4, d)+ " " + LongMethod.getIVal((byte)5, d)+ " " + LongMethod.getIVal((byte)6, d));
		System.out.println();
	}
	
	@Override
	public double[] getDecision(Board board) {
		System.out.println("-----------------------------------------------------");
		System.out.println("On recoit le board :");
		printBoard(this.convertBoard(board));
		System.out.println("Notre score : " + board.getScore(0) + " Adversaire score : " + board.getScore(1));
		System.out.println();
		CustomBoard Cboard = new CustomBoard(convertBoard(board), board.getScore(0)); 
		double[] nextBoard = new double[6];  
		for(int i =0; i < 6; i++) { 
			if(Cboard.isPlayable(i)) { 
				CustomBoard copyBoard = Cboard.clone();
				copyBoard.play((byte)i, (byte)-1);
				if(copyBoard.isFinish()) {
					if(copyBoard.isWin()) {
						nextBoard[i] = POSITIVE_INF;
					}
					nextBoard[i] = NEGATIVE_INF;
				}
				nextBoard[i] = negamax(copyBoard, 3, NEGATIVE_INF, POSITIVE_INF, 1);
			}
		}
		System.out.println("Evaluation des coups :");
		System.out.println(nextBoard[0] + " " + nextBoard[1] + " " + nextBoard[2] + " " + nextBoard[3] + " " + nextBoard[4] + " " + nextBoard[5]);
		return nextBoard; 
	}

	@Override
	public void learn() {
		/*
		try {
			OewaleBot trainingBot = new OewaleBot();
			double[] coefBotB = {1,1,1,1};
			trainingBot.setCoefs(coefBotB);
			double remainingTime = 0;
			double start = System.currentTimeMillis();
			while(remainingTime < MAX_PHASE1_LEARNING_TIME_FOR_TEST) {
				findBestCoef((byte)0.5, trainingBot);
				remainingTime += System.currentTimeMillis() - start;
			}
			while(remainingTime < MAX_PHASE2_LEARNING_TIME_FOR_TEST) {
				findBestCoef((byte)0.1, trainingBot);
				remainingTime += System.currentTimeMillis() - start;
			}
		} catch (InvalidBotException e) {
			e.printStackTrace();
		}
		*/
	}
	
	// UTILS METHODS
	
	public double negamax(CustomBoard board, int depth, double alpha, double beta, int player) {
		if(depth == 0 || board.isFinish()) {
			if(board.isFinish()) {
				if(board.isWin()) {
					return player * 50;
				}
				return player * -50;
			}
			//System.out.println("board.getScore() : " + board.getScore());
			return player * (board.getScore() - board.getOpponentScore());
		}
		//TODO generation de tous les coups possibles
		//TODO tri des coups
		double value = NEGATIVE_INF;
		int offset = 0;
		if(player == 1)
			offset = 6;
		for(int i = 0 ; i < 6; i++) {
			if(board.isPlayable(i+offset)) {
				CustomBoard copyBoard = board.clone();
				copyBoard.play((byte)(i+offset), (byte)-1);
				value = Math.max(value,  -negamax(copyBoard, depth - 1, -beta, -alpha, -player));
				alpha = Math.max(alpha, value);
				if(alpha >= beta) {
					break;
				}
			}
		}
		return value;
	}
		
	
	public void findBestCoef(byte variationMax, OewaleBot trainingBot) {
		this.setScoreGame(0); 
		trainingBot.setScoreGame(0);
		Awele awele = new Awele (this, trainingBot);
	    try {
			awele.play();
		} catch (InvalidBotException e) {
			e.printStackTrace();
		}
	    double scoreA = this.getScoreGame();
	    if(scoreA > this.getMaxLearningScore()){
	    	this.setMaxLearningScore(scoreA);
	    	trainingBot.setCoefs(this.getCoefs());
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
	
	private long convertBoard(Board board) {
		long lboard = 0;
		for(byte i = 0; i < 6; i++)
			lboard = LongMethod.setIVal((byte)(i+1), (byte)board.getPlayerHoles()[i], lboard);
		for(byte i = 0; i < 6 ; i++)
			lboard = LongMethod.setIVal((byte)(6+i+1), (byte)board.getOpponentHoles()[i], lboard);
		return lboard;
	}
}
