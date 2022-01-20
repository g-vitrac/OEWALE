package awele.bot.oewale;

import awele.bot.CompetitorBot;
import awele.core.Board;

public class OewaleBot extends CompetitorBot{
	
	private Node root;

	@Override
	public void initialize() {
		
	}

	@Override
	public void finish() {
		
	}

	@Override
	public double[] getDecision(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void learn() {
		//                0000 
		//                    00100 00100 00100 00100 00100 00100 
		//                    00100 00010 00010 00100 00100 00100
		root = new Node(0b0000001000010000100001000010000100001000010000100001000010000100L);
	}

}
