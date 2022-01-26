/**
 * 
 */
package awele.run;

import awele.bot.oewale.Node;

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
		Node n = new Node(0);
		n.addChildren(new Node(13));
		if(n.isPlayable((byte)0)) System.out.println("YES");
		else System.out.println("NO");
	}

}
