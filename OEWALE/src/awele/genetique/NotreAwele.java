package awele.genetique;

import awele.bot.Bot;
import awele.bot.oewale.OewaleBot;
import awele.core.Board;
import awele.output.OutputWriter;
import awele.run.Main;
import utils.LongMethod;

/**
 * @author toxic
 * Classe representant une partie d'NotreAwele entre deux joueurs
 */
public class NotreAwele extends OutputWriter
{
    private static final int MAX_STAGNANT = 1000;
    private Bot [] players;
    private int [] scores;
    private double nbMoves;
    private long runningTime;

    /**
     * @param player1 Le premier joueur
     * @param player2 Le second joueur
     */
    public NotreAwele (Bot player1, Bot player2)
    {
        this.players = new Bot [2];
        this.players [0] = player1;
        this.players [1] = player2;
        this.scores = new int [2];
        this.nbMoves = 0;
        this.runningTime = 0;
    }
    
    private int [] game (int firstPlayer) throws Exception
    {
        boolean end = false;
        Board board = new Board ();
        board.currentPlayer = firstPlayer;
        this.printDebug ();
        this.printDebug (board);
        this.printDebug ("Score : " + board.getScore (0) + " - " + board.getScore (1));
        int nbStagnant = 0;
        while (!end)
        {
            int currentPlayer = board.getCurrentPlayer ();
            this.nbMoves += 1;
            double [] decision = this.players [currentPlayer].getDecision (board);
            int moveScore = board.playMove (currentPlayer, decision);
            if (moveScore > 0)
                nbStagnant = 0;
            else
                nbStagnant++;
            if ((moveScore < 0) ||
                    (board.getScore (Board.otherPlayer (board.getCurrentPlayer ())) >= 25) ||
                    (board.getNbSeeds () <= 6) ||
                    (nbStagnant >= NotreAwele.MAX_STAGNANT)) {
                end = true;
                if(nbStagnant >= NotreAwele.MAX_STAGNANT) {
                	System.out.println("STAGNANT");
                }
            }
            this.printDebug ();
            this.printDebug (board);
            this.printDebug ("Score : " + board.getScore (0) + " - " + board.getScore (1));
            //System.out.println("Score : " + board.getScore (0) + " - " + board.getScore (1));
        }
        this.printDebug ();
        int [] score = new int [2];
        score [0] = board.getScore (0);
        score [1] = board.getScore (1);
        //if(score[0] > score[1])
        	//System.out.println("----------------------Winner : 0");
        //if(score[1] > score[0])
        	//System.out.println("----------------------Winner : 1");
        //if(score[0] == score[1])
        	//System.out.println("---------------------EGALITE");
        return score;
        
    }
    
    /**
     * @return Le nombre de coups joués
     */
    public double getNbMoves ()
    {
        return this.nbMoves;
    }
    
    /**
     * @return La durée de l'affrontement
     */
    public long getRunningTime ()
    {
        return this.runningTime;
    }
    
    /**
     * Fait jouer deux parties d'Awele entre les deux bots
     * @throws Exception 
     */
    public int [] play () throws Exception
    {
        long start = System.currentTimeMillis ();
        this.players [0].initialize ();
        this.players [1].initialize ();
        int [] game1Score = this.game (0);
        this.players [0].finish ();
        this.players [1].finish ();
        
        return game1Score;
    }
    
    /**
     * @return 0 si le premier bot a gagné, 1 si le second a gagné, -1 s'il y a égalité
     */
    public int getWinner ()
    {
        int winner = -1;
        if (this.scores [0] > this.scores [1])
            winner = 0;
        else if (this.scores [1] > this.scores [0])
            winner = 1;
        return winner;
    }
}
