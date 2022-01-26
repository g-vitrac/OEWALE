/**
 * 
 */
package utils;

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i = 1; i <= 12; i++) {
			long l = 0;
			l = LongMethod.setIVal((byte)i, (byte)32, l);
			//System.out.println(LongMethod.toBinaryString(l));
			byte b = LongMethod.getIVal((byte)i, l);
			//System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(b)));
		}
	}
}
