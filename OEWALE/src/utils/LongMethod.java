/**
 * 
 */
package utils;

/**
 * 
 * @author toxic
 *
 */
public abstract class LongMethod {
	
	private static final int NB_BIT_FAIBLE = 60;
	
	/**
	 * Get the value at the index i inside the long given by the user. This method will either return a 5bit byte or a 6bit byte
	 * depending on the state of the long.
	 * @param i The index of the value that the user wants to recover inside the long value. It must be > 255 otherwise overflow may happen.
	 * @return The integer saved inside the long at the location i. /!\ 1 <= i <= 12 /!\
	 */
	public static byte getIVal(byte i, long l) {
		byte result = (byte)(l >> (5 * (i-1)));
		result = (byte)(result & 31);
		
		// Overflow detected
		if((l >>> NB_BIT_FAIBLE) > 0){
			// add the strong weighed bit
			result = (byte)(result | (1 << 5));
		}
		
		return result; 
	}
	
	/**
	 * Set the value of the byte at the index i inside the long l. The byte must be coded on 6bit at maximum, otherwise data would be lost.
	 * @param i	The index of the value that the user wants to recover inside the long value. /!\ 1 <= i <= 12 /!\
	 * @param value The byte that will be saved inside long 
	 * @param l The long returned by the method
	 * @return A long with the byte saved at the position i inside the long l.
	 */
	public static long setIVal(byte i, byte value, long l) {
		long maskCP = 0b1111111111111111111111111111111111111111111111111111111111111111L;
		long mask = 0b11111;
		
		maskCP = maskCP & ~(mask << ((i-1) * 5));
		
		l = maskCP & l;
		maskCP = maskCP | ~((mask & value) << ((i-1) * 5));
		l = ~maskCP | l;			//set the low weighed bits inside 
		
		if(l >>> 60 > 0 & (l >>> 60) == i) {
			l = l & ~(0b1111 << 60);
		}
		
		//check if their is an overflow
		if((value >>> 5) > 0) {
			mask = i;			//save the position of the index where the overflow occurred
								//System.out.println(LongMethod.toBinaryString(mask));
			l = l | (mask << 60);	//write it in the 4 free bits at the end of the long
								//System.out.println(LongMethod.toBinaryString(l));

		}		
		return l;
	}
	
	public static String toBinaryString(long l) {
														//System.out.println(Long.toBinaryString(l));
		String s = Long.toBinaryString(l);
		String left0 = "";
		for(int i = 64; i > s.length(); i--) {
			left0 += "0";
		}
		s = left0 + s;
		return s;
	}     
}
