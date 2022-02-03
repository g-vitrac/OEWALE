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
	
	public static boolean end = false;
	
	@Override
	public double[] getDecision(Board board) {
		nbCoup = 0;
		System.out.println("-------------"+board.getCurrentPlayer()+"---------------------------------------");
		System.out.println("On recoit le board :");
		//System.out.println(LongMethod.toBinaryString(convertBoard(board)));
		printBoard(this.convertBoard(board));
		//if(end) System.exit(0);
		//System.out.println("Notre score : " + board.getScore(board.getCurrentPlayer()) + " Adversaire score : " + board.getScore(1-board.getCurrentPlayer()));
		System.out.println();
		CustomBoard Cboard = new CustomBoard(convertBoard(board), board.getScore(board.getCurrentPlayer())); 
		double[] decision = new double[6];
		for(int i = 0; i < 6; i++) decision[i] = NEGATIVE_INF -1;
		for(int i = 1; i <= 6; i++) { 
			if(Cboard.isPlayable(i, 1)) { 
				nbCoup++;
				CustomBoard copyBoard = Cboard.clone();
				boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, 1);
				copyBoard.play(i, 1, !canTakeSeeds);
				if(copyBoard.isFinish(1)) {
					if(copyBoard.isWin()) {
						end = true;
						decision[i-1] = POSITIVE_INF;
					}else if(copyBoard.isLose()) {
						decision[i-1] = NEGATIVE_INF;
					}else {
						decision[i-1] = 0;
					}
				}else {
					//decision[i-1] = negamax(copyBoard, 6, NEGATIVE_INF, POSITIVE_INF, 1);
					decision[i-1] = minmax(copyBoard, 6, false);
				}
			}
		}
		System.out.println("Evaluation des coups :");
		System.out.println(decision[0] + " " + decision[1] + " " + decision[2] + " " + decision[3] + " " + decision[4] + " " + decision[5]);
		//System.out.println("NbCoup exploré : " + nbCoup);
		
		return decision;
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
	
	public int nbCoup = 0;
	
	
	public double minmax(CustomBoard board, int depth, boolean max) {
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
						double eval = minmax(copyBoard, depth -1, false);
						value = Math.max(value, eval);
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
						double eval = minmax(copyBoard, depth -1, true);
						value = Math.min(value, eval);
					}
				}
			}
			return value;
		}
	}
	
	public double negamax(CustomBoard board, int depth, double alpha, double beta, int player) {
		
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
		if(player == -1)
			offset = 6;
		for(int i = 1 ; i <= 6; i++) {
			if(board.isPlayable(i+offset, player)) {
				nbCoup ++;
				CustomBoard copyBoard = board.clone();
				boolean canTakeSeeds = copyBoard.isOpponentStarvingAfterPlaying(i, player);
				copyBoard.play(i+offset, player, !canTakeSeeds);
				value = Math.max(value,  -negamax(copyBoard, depth - 1, -beta, -alpha, -player));
				alpha = Math.max(alpha, value);
				if(alpha >= beta) {
					//break;
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
