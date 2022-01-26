/**
 * 
 */
package awele.run;

import awele.bot.oewale.Node;
import utils.LongMethod;

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
		/*
		Node root = new Node(0b0000000110001100011000110001100011000110001100011011110001100011L);
		System.out.println(LongMethod.toBinaryString(root.getData()));
		root.setData(LongMethod.setIVal((byte)2, (byte)5, root.getData()));
		System.out.println(LongMethod.toBinaryString(root.getData()));
		*/
		
		/*
		Node root = new Node(0b0000001000010000100001000010000100001000010000100001000010000100L);
		System.out.println("root avant MinMax :");
		System.out.println(root);
		byte nodeValue = root.MinMax((byte)2, (byte)-1);
		System.out.println("nodeValue : " + nodeValue);
		System.out.println("root apres MinMax :");
		System.out.println(root);
		System.out.println("fils 1 " + root.getChildren(0)); // 0000 00100 00100 00100 00100 00100 00100 00100 00101 00101 00101 00101 00000
		System.out.println("fils 2 " + root.getChildren(1)); // 0000 00100 00100 00100 00100 00100 00100 00101 00101 00101 00101 00000 00100
		System.out.println("fils 3 " + root.getChildren(2)); // 0000 00100 00100 00100 00100 00100 00101 00101 00101 00101 00000 00100 00100
		System.out.println("fils 4 " + root.getChildren(3)); // 0000 00100 00100 00100 00100 00101 00101 00101 00101 00000 00100 00100 00100
		System.out.println("fils 5 " + root.getChildren(4)); // 0000 00100 00100 00100 00101 00101 00101 00101 00000 00100 00100 00100 00100
		System.out.println("fils 6 " + root.getChildren(5)); // 0000 00100 00100 00101 00101 00101 00101 00000 00100 00100 00100 00100 00100
		
		System.out.println("fils 1.1 " + root.getChildren(0).getChildren(0)); // 0000 00100 00101 00101 00101 00101 00000 00100 00101 00101 00101 00101 00000
		System.out.println("fils 1.2 " + root.getChildren(0).getChildren(1)); // 0000 00101 00101 00101 00101 00000 00100 00100 00101 00101 00101 00101 00000
		System.out.println("fils 1.3 " + root.getChildren(0).getChildren(2)); // 0000 00101 00101 00101 00000 00100 00100 00100 00101 00101 00101 00101 00001
		System.out.println("fils 1.4 " + root.getChildren(0).getChildren(3)); // 0000 00101 00101 00000 00100 00100 00100 00100 00101 00101 00101 00110 00001
		System.out.println("fils 1.5 " + root.getChildren(0).getChildren(4)); // 0000 00101 00000 00100 00100 00100 00100 00100 00101 00101 00110 00110 00001
		System.out.println("fils 1.6 " + root.getChildren(0).getChildren(5)); // 0000 00000 00100 00100 00100 00100 00100 00100 00101 00110 00110 00110 00001
		
		
		System.out.println("------------------------------");

		Node a = root.getChildren(0).getChildren(5).play((byte)8, (byte)1);

		System.out.println(LongMethod.toBinaryString(a.getData()));
		*/
		
		/*
		Node a = new Node(0b0000001010000000000000000000000000000110001000010000100001000010L);
		Node b = a.play((byte)11, (byte)1);
		System.out.println(LongMethod.toBinaryString(b.getData()));
		*/
		
		/* 
		Node a = new Node(0b0000000000000000000000000000000000000000000000000000000000000011L);
		System.out.println(a.getNbSeedInAnyHole((byte)1));
		System.out.println(a.getOpponentNbSeeds());
		System.out.println(a.isPlayable((byte)0));
		System.out.println(a.isFinish());
		System.out.println(a.isWin());
		System.out.println(a.getOurScore());
		System.out.println(a.getOpponentScore());
		*/
	}

}
