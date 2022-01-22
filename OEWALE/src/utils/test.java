/**
 * 
 */
package utils;

import awele.core.Board;

/**
 * @author toxic
 *
 */
public class test {

	/**
	 * 
	 */
	public test() {
		// TODO Auto-generated constructor stub
	}

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
		for(int i = 1; i <= 12; i++) {
			long l = 0;
			l = LongMethod.setIVal((byte)i, (byte)32, l);
			System.out.println(LongMethod.toBinaryString(l));
			byte b = LongMethod.getIVal((byte)i, l);
			System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(b)));
		}*/
	}
}
