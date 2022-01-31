package awele.bot.oewaledeprecated;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import utils.LongMethod;

public class Node {
	
	public void sort() {
		
	}
	
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
	 *  So we have a gain of 32 bit (33% less bit) which is in both case a huge gain of data storage ! 
	 * 
	 */
	private long data;
	private byte ourScore;
	private byte nodeScore;
	private byte indexPlayedHole;
	private Node father = null;
	private List<Node> childrens = new ArrayList<Node>();
	
	public Node(long data) {
		this.data = data;	
	}
	
	public Node(long data, byte score) {
		this.data = data;
		this.ourScore = score;
	}
	
	/*DATA FIELD*/
	
	public byte getOurScore() {
		return this.ourScore;
	}
	
	public void setOurScore(byte s) {
		this.ourScore = s;
	}
	
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
	
	public Node search(long board) {
		if(this.getData() == board)
			return this;
		for(Node n : this.getChildrens()) {
			n.search(board);
		}
		return null;
	}
	

	public void developMinMax(long remainingTime, byte max) {
		long currentTime = 0;
		while(currentTime < remainingTime) {
			long start = System.currentTimeMillis();
			if(this.childrens.isEmpty()) {
				byte nodeValue = this.MinMax((byte)2,(byte)-max);
				this.setNodeScore(nodeValue);
				this.sortNode((byte)-max);
				long endtmp = System.currentTimeMillis();
				remainingTime -= endtmp - start;
			}
			else {
				for(Node n : this.getChildrens()) {
					long endtmp = System.currentTimeMillis();
					remainingTime -= endtmp - start;
					if(remainingTime > 5) {
						n.developMinMax(remainingTime, (byte)-max);
					}
				}
			}
		}
	}
	
	public void sortNode(byte max) {
		if(this.getFather() != null) {
			if(max == -1)
				this.getFather().getChildrens().sort(Comparator.comparing(Node::getNodeScore));
			else
				this.getFather().getChildrens().sort(Comparator.comparing(Node::getNodeScore).reversed());
			this.getFather().setNodeScore(this.getFather().getChildrens().get(0).getNodeScore());
			this.getFather().sortNode((byte)-max);
		}
	}
	
	public byte MinMax(byte depth, byte max) {
		byte score = (byte)(127 * max);			//if max == -1 we are maximizing so score is equal to -127 otherwise it equals 127;
		if(depth == 0) {
			if(this.isFinish()) {
				if(this.isWin()) {
					return 10;
				}
				return -10;
			}
			else
				return 0;
		}
		byte offset = 0;
		if(max == 1) offset = 6; // si on est sur un min c'est l'adversaire qui joue donc ses trous sont � +6 si on a une boucle qui va de 0 � 5
		for(byte i = 0; i < 6; i++) {
			if(this.isPlayable((byte)(i+ offset))) {
				Node n = this.play((byte)(i+offset), (byte)-max);
				byte eval = n.MinMax((byte)(depth - 1), (byte)-max);
				n.setNodeScore(eval);
				byte storeScore = score;
				score = (byte)( max * Math.min(max * eval, max * score) );
				if((storeScore < score && max == 1) || (storeScore > score && max == -1)) {
					this.indexPlayedHole = i;
				}
			}
		}
		return score;
	}
	
	public Node play(byte i, byte max) {
		byte holeIndex =  (byte) (i + 1);
		long newData = this.getData();

		byte nbSeedInHole = this.getNbSeedInAnyHole(holeIndex);
		byte indexLastHole = (byte)(((holeIndex + nbSeedInHole) % 12));
		byte cpt = (byte) (holeIndex + 1);
		if(cpt > 12) cpt = 1;

		
				

		for(byte j = nbSeedInHole; j > 0; j--) {
			newData = LongMethod.setIVal(cpt, (byte)(LongMethod.getIVal(cpt, newData)+1), newData);
			cpt++;
			if(cpt > 12) cpt = 1;
			if(cpt == holeIndex) cpt++;
		}
		newData = LongMethod.setIVal(holeIndex, (byte)0, newData);
		if(max == -1 && indexLastHole > 6) {

			for(byte j = indexLastHole; j >= 7; j--) {
				byte nb = LongMethod.getIVal(j, newData);
				if( nb > 1 && nb <= 3) {
					this.ourScore += nb;
					newData = LongMethod.setIVal(j, (byte)0, newData);

				}
				else {
					break;
				}
			}
		}else if(max == 1 && indexLastHole < 6) {
			for(byte j = indexLastHole; j >= 0; j--) {
				byte nb = LongMethod.getIVal(j, newData);
				if( nb > 1 && nb <= 3) {
					this.ourScore += nb;
					newData = LongMethod.setIVal(j, (byte)0, newData);
				}
				else {
					break;
				}
			}
		}
		Node next = new Node(newData);
		this.addChildren(next);
		return next;
	}
	
	public boolean isFinish() {
		if(this.getAllRemainingSeeds() < 6)
			return true;
		Boolean finish = true;
		for(byte i = 0; i < 6; i++) {
			if(this.isPlayable(i)) {
				finish = false;
			}
		}
		return finish;
	}
	
	public boolean isPlayable(byte i) {
		
		if(LongMethod.getIVal((byte)(i+1), this.getData()) == 0) {
			return false;
		}
		boolean playable = true;
		if(this.getOpponentNbSeeds() == 0 && this.getNbSeedInAnyHole((byte)(i+1)) + i <= 6) // if our opponent is starving and we can give him seed 
			playable = false;
		return playable;
	}
	
	public boolean isWin() {
		return this.getOurScore() > this.getOpponentScore() ? true : false;  
	}
	
	public byte getOpponentScore() {
		return (byte) (48 - this.getOurScore() - this.getOurNbSeeds() - this.getOpponentNbSeeds());  
	}
	
	public byte getNbSeedInAnyHole(byte i) {
		return LongMethod.getIVal((byte)i, this.data);
	}
	
	public byte[] getOurHoles() {
		byte[] holes = new byte[6];
		for(byte i = 1; i <= 6; i++) {
			holes[i] = LongMethod.getIVal((byte)i, this.data);
		}
		return holes;
	}
	
	public byte[] getOpponentHoles() {
		byte[] holes = new byte[6];
		for(byte i = 7; i <= 12; i++) {
			holes[i] = LongMethod.getIVal((byte)i, this.data);
		}
		return holes;
	}
	
	public byte getOurNbSeeds() {
		byte sum = 0;
		for(byte i = 1; i <= 6; i++) {
			sum += LongMethod.getIVal((byte)i, this.data);
		}
		return sum;
	}
	
	public byte getOpponentNbSeeds() {
		byte sum = 0;
		for(byte i = 7; i <= 12; i++) {
			sum += LongMethod.getIVal((byte)i, this.data);
		}
		return sum;
	}
	
	public byte getAllRemainingSeeds() {
		byte sum = 0;
		for(byte i = 1; i <= 12; i++) {
			sum += LongMethod.getIVal((byte)i, this.data);
		}
		return sum;
	}
	
	public Node pruning() {
		this.setFather(null);
		return this;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == this) { 
            return true; 
        } 
        if (!(obj instanceof Node)) {
            return false; 
        } 
        Node node = (Node) obj; 
        return node.getData() != this.data;
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


	/**
	 * @return the nodeScore
	 */
	public byte getNodeScore() {
		return nodeScore;
	}

	/**
	 * @param nodeScore the nodeScore to set
	 */
	public void setNodeScore(byte nodeScore) {
		this.nodeScore = nodeScore;
	}

	/**
	 * @return the indexPlayedHole
	 */
	public byte getIndexPlayedHole() {
		return indexPlayedHole;
	}

	/**
	 * @param indexPlayedHole the indexPlayedHole to set
	 */
	public void setIndexPlayedHole(byte indexPlayedHole) {
		this.indexPlayedHole = indexPlayedHole;
	}
}

