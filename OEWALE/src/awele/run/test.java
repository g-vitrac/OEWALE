/**
 * 
 */
package awele.run;

import java.util.Comparator;

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
		
		
		Node root = new Node(0b0000001000010000100001000010000100001000010000100001000010000100L);
		//System.out.println("root avant MinMax :");
		//System.out.println(root);
		byte nodeValue = root.MinMax((byte)2, (byte)-1);
		//System.out.println("nodeValue : " + nodeValue);
		//System.out.println("root apres MinMax :");
		
		root.setNodeScore((byte)0);
		root.getChildren(0).setNodeScore((byte)10);
		root.getChildren(1).setNodeScore((byte)3);
		root.getChildren(2).setNodeScore((byte)5);
		root.getChildren(3).setNodeScore((byte)7);
		root.getChildren(4).setNodeScore((byte)6);
		root.getChildren(5).setNodeScore((byte)2);
		
		root.getChildren(0).getChildren(0).setNodeScore((byte)1);
		root.getChildren(0).getChildren(1).setNodeScore((byte)3);
		root.getChildren(0).getChildren(2).setNodeScore((byte)5);
		root.getChildren(0).getChildren(3).setNodeScore((byte)7);
		root.getChildren(0).getChildren(4).setNodeScore((byte)2);
		root.getChildren(0).getChildren(5).setNodeScore((byte)10);
		
		root.getChildren(1).getChildren(0).setNodeScore((byte)2);
		root.getChildren(1).getChildren(1).setNodeScore((byte)4);
		root.getChildren(1).getChildren(2).setNodeScore((byte)9);
		root.getChildren(1).getChildren(3).setNodeScore((byte)3);
		root.getChildren(1).getChildren(4).setNodeScore((byte)2);
		root.getChildren(1).getChildren(5).setNodeScore((byte)5);
		
		root.getChildren(2).getChildren(0).setNodeScore((byte)1);
		root.getChildren(2).getChildren(1).setNodeScore((byte)5);
		root.getChildren(2).getChildren(2).setNodeScore((byte)5);
		root.getChildren(2).getChildren(3).setNodeScore((byte)2);
		root.getChildren(2).getChildren(4).setNodeScore((byte)8);
		root.getChildren(2).getChildren(5).setNodeScore((byte)3);
		
		root.getChildren(3).getChildren(0).setNodeScore((byte)-2);
		root.getChildren(3).getChildren(1).setNodeScore((byte)-10);
		root.getChildren(3).getChildren(2).setNodeScore((byte)3);
		root.getChildren(3).getChildren(3).setNodeScore((byte)0);
		root.getChildren(3).getChildren(4).setNodeScore((byte)2);
		root.getChildren(3).getChildren(5).setNodeScore((byte)4);
		
		root.getChildren(4).getChildren(0).setNodeScore((byte)-1);
		root.getChildren(4).getChildren(1).setNodeScore((byte)-3);
		root.getChildren(4).getChildren(2).setNodeScore((byte)-5);
		root.getChildren(4).getChildren(3).setNodeScore((byte)-7);
		root.getChildren(4).getChildren(4).setNodeScore((byte)-2);
		root.getChildren(4).getChildren(5).setNodeScore((byte)-10);
		
		root.getChildren(5).getChildren(0).setNodeScore((byte)8);
		root.getChildren(5).getChildren(1).setNodeScore((byte)-3);
		root.getChildren(5).getChildren(2).setNodeScore((byte)5);
		root.getChildren(5).getChildren(3).setNodeScore((byte)9);
		root.getChildren(5).getChildren(4).setNodeScore((byte)2);
		root.getChildren(5).getChildren(5).setNodeScore((byte)1);
		
		
		System.out.println("Avant tri");
		System.out.println(root);
		System.out.println();
		System.out.println("fils 1 " + root.getChildren(0) + " " + root.getChildren(0).getNodeScore()); 
		System.out.println("fils 2 " + root.getChildren(1) + " " + root.getChildren(1).getNodeScore());
		System.out.println("fils 3 " + root.getChildren(2) + " " + root.getChildren(2).getNodeScore()); 
		System.out.println("fils 4 " + root.getChildren(3) + " " + root.getChildren(3).getNodeScore()); 
		System.out.println("fils 5 " + root.getChildren(4) + " " + root.getChildren(4).getNodeScore()); 
		System.out.println("fils 6 " + root.getChildren(5) + " " + root.getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 1.1 " + root.getChildren(0).getChildren(0) + " " + root.getChildren(0).getChildren(0).getNodeScore());
		System.out.println("fils 1.2 " + root.getChildren(0).getChildren(1) + " " + root.getChildren(0).getChildren(1).getNodeScore());
		System.out.println("fils 1.3 " + root.getChildren(0).getChildren(2) + " " + root.getChildren(0).getChildren(2).getNodeScore()); 
		System.out.println("fils 1.4 " + root.getChildren(0).getChildren(3) + " " + root.getChildren(0).getChildren(3).getNodeScore()); 
		System.out.println("fils 1.5 " + root.getChildren(0).getChildren(4) + " " + root.getChildren(0).getChildren(4).getNodeScore()); 
		System.out.println("fils 1.6 " + root.getChildren(0).getChildren(5) + " " + root.getChildren(0).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 2.1 " + root.getChildren(1).getChildren(0) + " " + root.getChildren(1).getChildren(0).getNodeScore());
		System.out.println("fils 2.2 " + root.getChildren(1).getChildren(1) + " " + root.getChildren(1).getChildren(1).getNodeScore());
		System.out.println("fils 2.3 " + root.getChildren(1).getChildren(2) + " " + root.getChildren(1).getChildren(2).getNodeScore()); 
		System.out.println("fils 2.4 " + root.getChildren(1).getChildren(3) + " " + root.getChildren(1).getChildren(3).getNodeScore()); 
		System.out.println("fils 2.5 " + root.getChildren(1).getChildren(4) + " " + root.getChildren(1).getChildren(4).getNodeScore()); 
		System.out.println("fils 2.6 " + root.getChildren(1).getChildren(5) + " " + root.getChildren(1).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 3.1 " + root.getChildren(2).getChildren(0) + " " + root.getChildren(2).getChildren(0).getNodeScore());
		System.out.println("fils 3.2 " + root.getChildren(2).getChildren(1) + " " + root.getChildren(2).getChildren(1).getNodeScore());
		System.out.println("fils 3.3 " + root.getChildren(2).getChildren(2) + " " + root.getChildren(2).getChildren(2).getNodeScore()); 
		System.out.println("fils 3.4 " + root.getChildren(2).getChildren(3) + " " + root.getChildren(2).getChildren(3).getNodeScore()); 
		System.out.println("fils 3.5 " + root.getChildren(2).getChildren(4) + " " + root.getChildren(2).getChildren(4).getNodeScore()); 
		System.out.println("fils 3.6 " + root.getChildren(2).getChildren(5) + " " + root.getChildren(2).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 4.1 " + root.getChildren(3).getChildren(0) + " " + root.getChildren(3).getChildren(0).getNodeScore());
		System.out.println("fils 4.2 " + root.getChildren(3).getChildren(1) + " " + root.getChildren(3).getChildren(1).getNodeScore());
		System.out.println("fils 4.3 " + root.getChildren(3).getChildren(2) + " " + root.getChildren(3).getChildren(2).getNodeScore()); 
		System.out.println("fils 4.4 " + root.getChildren(3).getChildren(3) + " " + root.getChildren(3).getChildren(3).getNodeScore()); 
		System.out.println("fils 4.5 " + root.getChildren(3).getChildren(4) + " " + root.getChildren(3).getChildren(4).getNodeScore()); 
		System.out.println("fils 4.6 " + root.getChildren(3).getChildren(5) + " " + root.getChildren(3).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 5.1 " + root.getChildren(4).getChildren(0) + " " + root.getChildren(4).getChildren(0).getNodeScore());
		System.out.println("fils 5.2 " + root.getChildren(4).getChildren(1) + " " + root.getChildren(4).getChildren(1).getNodeScore());
		System.out.println("fils 5.3 " + root.getChildren(4).getChildren(2) + " " + root.getChildren(4).getChildren(2).getNodeScore()); 
		System.out.println("fils 5.4 " + root.getChildren(4).getChildren(3) + " " + root.getChildren(4).getChildren(3).getNodeScore()); 
		System.out.println("fils 5.5 " + root.getChildren(4).getChildren(4) + " " + root.getChildren(4).getChildren(4).getNodeScore()); 
		System.out.println("fils 5.6 " + root.getChildren(4).getChildren(5) + " " + root.getChildren(4).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 6.1 " + root.getChildren(5).getChildren(0) + " " + root.getChildren(5).getChildren(0).getNodeScore());
		System.out.println("fils 6.2 " + root.getChildren(5).getChildren(1) + " " + root.getChildren(5).getChildren(1).getNodeScore());
		System.out.println("fils 6.3 " + root.getChildren(5).getChildren(2) + " " + root.getChildren(5).getChildren(2).getNodeScore()); 
		System.out.println("fils 6.4 " + root.getChildren(5).getChildren(3) + " " + root.getChildren(5).getChildren(3).getNodeScore()); 
		System.out.println("fils 6.5 " + root.getChildren(5).getChildren(4) + " " + root.getChildren(5).getChildren(4).getNodeScore()); 
		System.out.println("fils 6.6 " + root.getChildren(5).getChildren(5) + " " + root.getChildren(5).getChildren(5).getNodeScore());
		
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		root.getChildren(0).getChildren(0).sortNode((byte)1);
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Après tri");
		System.out.println(root + " " + root.getNodeScore());
		System.out.println();
		System.out.println("fils 1 " + root.getChildren(0) + " " + root.getChildren(0).getNodeScore()); 
		System.out.println("fils 2 " + root.getChildren(1) + " " + root.getChildren(1).getNodeScore());
		System.out.println("fils 3 " + root.getChildren(2) + " " + root.getChildren(2).getNodeScore()); 
		System.out.println("fils 4 " + root.getChildren(3) + " " + root.getChildren(3).getNodeScore()); 
		System.out.println("fils 5 " + root.getChildren(4) + " " + root.getChildren(4).getNodeScore()); 
		System.out.println("fils 6 " + root.getChildren(5) + " " + root.getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 1.1 " + root.getChildren(0).getChildren(0) + " " + root.getChildren(0).getChildren(0).getNodeScore());
		System.out.println("fils 1.2 " + root.getChildren(0).getChildren(1) + " " + root.getChildren(0).getChildren(1).getNodeScore());
		System.out.println("fils 1.3 " + root.getChildren(0).getChildren(2) + " " + root.getChildren(0).getChildren(2).getNodeScore()); 
		System.out.println("fils 1.4 " + root.getChildren(0).getChildren(3) + " " + root.getChildren(0).getChildren(3).getNodeScore()); 
		System.out.println("fils 1.5 " + root.getChildren(0).getChildren(4) + " " + root.getChildren(0).getChildren(4).getNodeScore()); 
		System.out.println("fils 1.6 " + root.getChildren(0).getChildren(5) + " " + root.getChildren(0).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 2.1 " + root.getChildren(1).getChildren(0) + " " + root.getChildren(1).getChildren(0).getNodeScore());
		System.out.println("fils 2.2 " + root.getChildren(1).getChildren(1) + " " + root.getChildren(1).getChildren(1).getNodeScore());
		System.out.println("fils 2.3 " + root.getChildren(1).getChildren(2) + " " + root.getChildren(1).getChildren(2).getNodeScore()); 
		System.out.println("fils 2.4 " + root.getChildren(1).getChildren(3) + " " + root.getChildren(1).getChildren(3).getNodeScore()); 
		System.out.println("fils 2.5 " + root.getChildren(1).getChildren(4) + " " + root.getChildren(1).getChildren(4).getNodeScore()); 
		System.out.println("fils 2.6 " + root.getChildren(1).getChildren(5) + " " + root.getChildren(1).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 3.1 " + root.getChildren(2).getChildren(0) + " " + root.getChildren(2).getChildren(0).getNodeScore());
		System.out.println("fils 3.2 " + root.getChildren(2).getChildren(1) + " " + root.getChildren(2).getChildren(1).getNodeScore());
		System.out.println("fils 3.3 " + root.getChildren(2).getChildren(2) + " " + root.getChildren(2).getChildren(2).getNodeScore()); 
		System.out.println("fils 3.4 " + root.getChildren(2).getChildren(3) + " " + root.getChildren(2).getChildren(3).getNodeScore()); 
		System.out.println("fils 3.5 " + root.getChildren(2).getChildren(4) + " " + root.getChildren(2).getChildren(4).getNodeScore()); 
		System.out.println("fils 3.6 " + root.getChildren(2).getChildren(5) + " " + root.getChildren(2).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 4.1 " + root.getChildren(3).getChildren(0) + " " + root.getChildren(3).getChildren(0).getNodeScore());
		System.out.println("fils 4.2 " + root.getChildren(3).getChildren(1) + " " + root.getChildren(3).getChildren(1).getNodeScore());
		System.out.println("fils 4.3 " + root.getChildren(3).getChildren(2) + " " + root.getChildren(3).getChildren(2).getNodeScore()); 
		System.out.println("fils 4.4 " + root.getChildren(3).getChildren(3) + " " + root.getChildren(3).getChildren(3).getNodeScore()); 
		System.out.println("fils 4.5 " + root.getChildren(3).getChildren(4) + " " + root.getChildren(3).getChildren(4).getNodeScore()); 
		System.out.println("fils 4.6 " + root.getChildren(3).getChildren(5) + " " + root.getChildren(3).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 5.1 " + root.getChildren(4).getChildren(0) + " " + root.getChildren(4).getChildren(0).getNodeScore());
		System.out.println("fils 5.2 " + root.getChildren(4).getChildren(1) + " " + root.getChildren(4).getChildren(1).getNodeScore());
		System.out.println("fils 5.3 " + root.getChildren(4).getChildren(2) + " " + root.getChildren(4).getChildren(2).getNodeScore()); 
		System.out.println("fils 5.4 " + root.getChildren(4).getChildren(3) + " " + root.getChildren(4).getChildren(3).getNodeScore()); 
		System.out.println("fils 5.5 " + root.getChildren(4).getChildren(4) + " " + root.getChildren(4).getChildren(4).getNodeScore()); 
		System.out.println("fils 5.6 " + root.getChildren(4).getChildren(5) + " " + root.getChildren(4).getChildren(5).getNodeScore());
		System.out.println();
		System.out.println("fils 6.1 " + root.getChildren(5).getChildren(0) + " " + root.getChildren(5).getChildren(0).getNodeScore());
		System.out.println("fils 6.2 " + root.getChildren(5).getChildren(1) + " " + root.getChildren(5).getChildren(1).getNodeScore());
		System.out.println("fils 6.3 " + root.getChildren(5).getChildren(2) + " " + root.getChildren(5).getChildren(2).getNodeScore()); 
		System.out.println("fils 6.4 " + root.getChildren(5).getChildren(3) + " " + root.getChildren(5).getChildren(3).getNodeScore()); 
		System.out.println("fils 6.5 " + root.getChildren(5).getChildren(4) + " " + root.getChildren(5).getChildren(4).getNodeScore()); 
		System.out.println("fils 6.6 " + root.getChildren(5).getChildren(5) + " " + root.getChildren(5).getChildren(5).getNodeScore());
		
		/*
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
