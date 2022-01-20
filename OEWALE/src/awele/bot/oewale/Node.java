package awele.bot.oewale;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	/**
	 * We store the state of the game in one long. 
	 * A long is 64 bit long and we have to store the number of seed for each hole. 
	 * A hole can contains maximum 48 seeds, if we use 6 bit for each hole, we need 72 bit, which is more than 64.
	 * So we decide to use 5 bit for each hole which mean that a hole can only contains 31 seeds maximum.
	 * In this configuration (12 holes * 5bit/hole = 60 bit) 4 bit left.
	 * These are used to manage the hole containing more than 31 seeds. 
	 * (knowing that only one hole can contain more than 31 seeds because there is a total of 48 seeds)
	 * 
	 * For instance this long  
	 * 
	 *     hole index:        1     2     3     4     5     6     7     8     9     10    11    12
	 *                 0000 00100 00100 00100 00100 00100 00100 00100 00100 00100 00100 00100 00100
	 *                   ^
	 *                   |
	 *             	  overflow
	 *                 index
	 *   		  
	 * represent the initial position of a game, where each hole contains 4 seeds.
	 * 
	 * Now imagine we want to represent a situation where there is 37 seeds in 8th hole and 1 seed in each other hole.
	 * 
	 *     hole index:        1     2     3     4     5     6     7     8     9     10    11    12
	 *                 1000 00001 00001 00001 00001 00001 00001 00001 00101 00001 00001 00001 00001
	 *                   ^
	 *                   |
	 *             	  overflow
	 *                 index
	 *                 
	 *  31 =   11111                      37 can't be written in 5 bit, and as we can see, from 32 to 48 the 6th bit is always 1.
	 *  32 = 1 00000                      So if a hole have more than 31 seeds we just have to write in the 4 bit left the index
	 *  33 = 1 00001                      of the hole where there is overflow, because we already know that the 6th bit that are missing 
	 *  34 = 1 00010                      has the value of 1.
	 *  35 = 1 00011					  So in this case the 4 bit left are 1000 (= 8), so we know that the 8th hole have more than 31 seeds
	 *  36 = 1 00100					  and his 6th bit is 1.
	 *  37 = 1 00101					  We take the 5 bit of the 8th hole -> 00101 and we add 1 high value bit equal to 1.
	 *  38 = 1 00110                      So the binary number of seed for the 8th hole is 1 + 00101 -> 100101 = 37. 
	 *  39 = 1 00111               
	 *  40 = 1 01000
	 *  41 = 1 01001
	 *  42 = 1 01010 
	 *  43 = 1 01011
	 *  44 = 1 01100
	 *  45 = 1 01101
	 *  46 = 1 01110
	 *  47 = 1 01111
	 *  48 = 1 10000 
	 *  
	 *  With this method representation instead of using 1 int for each hole, which represent a total of 12 holes * 32 bit/int = 384 bit,
	 *  we only use one long which is 64 bit long, so we have a gain of 320 bit (83% less bit).
	 *  And even if we use the shortest primitive java type, a byte, 12 holes * 8 bit/byte = 96 bit.
	 *  So we have a gain of 32 bit (66% less bit) which is in both case a huge gain of data storage ! 
	 * 
	 */
	private long data;
	private Node father = null;
	private List<Node> childrens = new ArrayList<Node>();
	
	public Node(long data) {
		this.data = data;	
	}
	
	/*DATA FIELD*/
	
	public long getData() {
		return this.data;
	}
	
	public void setData(long data) {
		this.data = data;
	}
	
	/*FATHER FIELD*/
	
	public Node getFather() {
		return this.father;
	}
	
	public void setFather(Node father) {
		this.father = father;
	}
	
	/*CHILDRENS FIELD*/
	
	public List<Node> getChildrens() {
		return this.childrens;
	}
	
	public void setChildrens(List<Node> childrens) {
		for(Node node : childrens) {
			node.setFather(this);
		}
		this.childrens = childrens;
	}
	
	public Node getChildren(int index) {
		return this.childrens.get(index);
	}
	
	public void addChildren(Node node) {
		node.setFather(this);
		this.childrens.add(node);
	}
	
	public void addChildren(Node children, int index) {
		children.setFather(this);
		this.childrens.add(index, children);
	}
	
	public void removeChildren(Node children) {
		children.setFather(null);
		this.childrens.remove(children);
	}
	
	public void removeChildren(int index) {
		this.childrens.get(index).setFather(null);
		this.childrens.remove(index);
	}
	
	public void removeChildrens(List<Node> childrens) {
		for(Node node : childrens) {
			if (this.childrens.contains(node))
				this.removeChildren(node);
		}
	}
	
	public void removeAllChildrens() {
		for(Node node : this.childrens) {
			this.removeChildren(node);
		}
	}

	
	/*MANIPULATION METHODS*/
	
	public void swapWithoutChildren(Node node) throws CloneNotSupportedException {
		Node tmp = node.clone();
		node.setFather(this.father);
		this.father = tmp.getFather();
		node.setChildrens(this.getChildrens());
		this.setChildrens(tmp.getChildrens());
	}
	
	public void swapWithChildren(Node node) {
		Node tmp = node;
		node.setFather(this.father);
		this.father = tmp.getFather();
		
	}
	
	
	/*UTILS METHODS*/

	@Override
	public boolean equals(Object obj) {
		if (obj == this) { 
            return true; 
        } 
        if (!(obj instanceof Node)) {
            return false; 
        } 
        Node node = (Node) obj; 
        if(node.getData() != this.data)
        	return false;
        if(node.getFather() != this.father)
        	return false;
        if(node.getChildrens().size() != this.childrens.size())
        	return false;
        else {
        	 Boolean equal = true;
        	 for(int i = 0; i < this.childrens.size(); i++) {
             	for(int j = 0; j < this.childrens.size(); j++) {
             		equal = this.childrens.get(i) == node.getChildren(j) ? true : false;
             	}
             }
        	 return equal;
        }       
	}

	@Override
	protected Node clone() {
		Node clonedNode = new Node(this.data);
		clonedNode.setFather(this.father);
		clonedNode.setChildrens(this.childrens);
		return clonedNode;
	}

	@Override
	public String toString() {
		return "[d: " + this.data + " f: " + this.father + " nbC: " + this.getChildrens().size() + "]";
	}
}

