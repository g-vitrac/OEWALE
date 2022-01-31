/**
 * 
 */
package utils;

import awele.bot.oewaledeprecated.Node;
import awele.core.Board;

/**
 * @author toxic
 *
 */
public class test {

	private static long convertBoard(Board board) {
		long lboard = 0;
		for(byte i = 0; i < 6; i++)
			lboard = LongMethod.setIVal((byte)(i+1), (byte)board.getPlayerHoles()[i], lboard);
		for(byte i = 0; i < 6 ; i++)
			lboard = LongMethod.setIVal((byte)(6+i+1), (byte)board.getOpponentHoles()[i], lboard);
		return lboard;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*					  
		Node root = new Node(0b0000000110001100011000110001100011000110001100011011110001100011L);
		System.out.println(LongMethod.toBinaryString(root.getData()));
		Node a = root.play((byte)2);
		System.out.println(LongMethod.toBinaryString(a.getData()));
		Node b = a.play((byte)4);
		System.out.println(LongMethod.toBinaryString(b.getData()));

		                      
		Node root = new Node(0b0000000110001100011000110001100011000110001100011011110001100011L);
		root.setData(LongMethod.setIVal((byte)2, (byte)5, root.getData()));
		System.out.println(LongMethod.toBinaryString(root.getData()));
		
		/*                     
		Node root = new Node(0b0000000110001100011000110001100011000110001100011011110001100011L);
		System.out.println(LongMethod.toBinaryString(root.getData()));
		Node a = root.play((byte)2);
		System.out.println(LongMethod.toBinaryString(a.getData()));
		*/
		
		/*
		Node root = new Node(0b0000000110001100011000110001100011000110001100011000110001100011L);
		System.out.println(LongMethod.toBinaryString(root.getData()));
		Node a = root.play((byte)2);
		System.out.println(LongMethod.toBinaryString(a.getData()));
		//a = root.play((byte)0);
		//System.out.println(LongMethod.toBinaryString(a.getData()));

		*/
	}
}
