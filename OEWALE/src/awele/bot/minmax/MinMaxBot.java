package awele.bot.minmax;

import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;
import utils.LongMethod;

/**
 * @author Alexandre Blansché
 * Bot qui prend ses décisions selon le MinMax
 */
public class MinMaxBot extends DemoBot
{
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 3;
	
    /**
     * @throws InvalidBotException
     */
    public MinMaxBot () throws InvalidBotException
    {
        this.setBotName ("MinMax");
        this.addAuthor ("Alexandre Blansché");
    }

    /**
     * Rien à faire
     */
    @Override
    public void initialize ()
    {
    }

    /**
     * Pas d'apprentissage
     */
    @Override
    public void learn ()
    {
    }

    /**
     * Sélection du coup selon l'algorithme MinMax
     */
    @Override
    public double [] getDecision (Board board)
    {
    	printBoard(this.convertBoard(board));
    	System.out.println(LongMethod.toBinaryString(convertBoard(board)));
        MinMaxNode.initialize (board, MinMaxBot.MAX_DEPTH);
        //return new MaxNode (board).getDecision ();
        double[] decision = new MaxNode (board).getDecision ();
		System.out.println(decision[0] + " " + decision[1] + " " + decision[2] + " " + decision[3] + " " + decision[4] + " " + decision[5]);
        return  decision ;
    }
    
    public void printBoard(long d) {
		System.out.println(LongMethod.getIVal((byte)12, d) + " " + LongMethod.getIVal((byte)11, d) + " " + LongMethod.getIVal((byte)10, d) + " " + LongMethod.getIVal((byte)9, d)+ " " + LongMethod.getIVal((byte)8, d)+ " " + LongMethod.getIVal((byte)7, d));
		System.out.println(LongMethod.getIVal((byte)1, d) + " " + LongMethod.getIVal((byte)2, d) + " " + LongMethod.getIVal((byte)3, d) + " " + LongMethod.getIVal((byte)4, d)+ " " + LongMethod.getIVal((byte)5, d)+ " " + LongMethod.getIVal((byte)6, d));
		System.out.println();
	}
    
    private long convertBoard(Board board) {
		long lboard = 0;
		for(byte i = 0; i < 6; i++)
			lboard = LongMethod.setIVal((byte)(i+1), (byte)board.getOpponentHoles()[i], lboard);
		for(byte i = 0; i < 6 ; i++)
			lboard = LongMethod.setIVal((byte)(6+i+1), (byte)board.getPlayerHoles()[i], lboard);
		return lboard;
	}
    
    /**
     * Rien à faire
     */
    @Override
    public void finish ()
    {
    }
}
