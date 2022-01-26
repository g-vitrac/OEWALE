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
		for(int i = 1; i <= 12; i++) {
			long l = 0;
			//l = LongMethod.setIVal((byte)i, (byte)48, l);
												//System.out.println(LongMethod.toBinaryString(l));
			byte b = LongMethod.getIVal((byte)i, l);
												//System.out.println(b);
												//System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(b)));
		}
		long l = 0b0000001000010000100001000010000100001000010000100001000010000100L;
		LongMethod.setIVal((byte)4, (byte)0, l);
		System.out.println(LongMethod.toBinaryString(l));
	}
}
