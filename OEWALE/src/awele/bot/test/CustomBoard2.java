package awele.bot.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import utils.LongMethod;

public class CustomBoard2 {

	private long boardData;
	private int score;
	
	public CustomBoard2(long boardData, int score) {
		this.boardData = boardData;
		this.score = score;
	}
	
	public long getBoardData() {
		return this.boardData;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean isPlayable(int i, int player) throws Exception {
		if(LongMethod.getIVal((byte)(i), this.getBoardData()) == 0) {
			return false;
		}
		boolean playable = true;
		if(player == OewaleBot2.OEWALE) {
			if((this.getOpponentNbSeeds() == 0 && 6-i > this.getNbSeedInAnyHole(i)))
				playable = false;
		}else {
			if((this.getOurNbSeeds() == 0 && 12-i > this.getNbSeedInAnyHole(i)))
				playable = false;
		}
		return playable;
	}
	
	public boolean isFinish(int player) throws Exception {
		if(this.getAllRemainingSeeds() <= 6 || this.score >= 25 || this.getOpponentScore() >= 25) {
			return true;
		}
		int offset = 0;
		if(player == OewaleBot2.OPPONENT) {
			offset = 6;
		}
		Boolean finish = true;
		for(int i = 1; i <= 6; i++) {
			if(this.isPlayable(i+offset, player)) {
				finish = false;
			}
		}
		return finish;
	}
	
	public boolean isWin() throws Exception {
		return this.score > this.getOpponentScore();  
	}
	
	public boolean isLose() throws Exception {
		return this.getOpponentScore() > this.score;
	}
	
	public byte getAllRemainingSeeds() throws Exception {
		byte sum = 0;
		for(byte i = 1; i <= 12; i++) {
			sum += LongMethod.getIVal((byte)i, this.boardData);
		}
		return sum;
	}
	
	public byte getOurNbSeeds() throws Exception {
		byte sum = 0;
		for(byte i = 1; i <= 6; i++) {
			sum += LongMethod.getIVal((byte)i, this.boardData);
		}
		return sum;
	}
	
	public byte getOpponentNbSeeds() throws Exception {
		byte sum = 0;
		for(byte i = 7; i <= 12; i++) {
			sum += LongMethod.getIVal((byte)i, this.boardData);
		}
		return sum;
	}
	
	public byte getNbSeedInAnyHole(int i) throws Exception {
		return LongMethod.getIVal((byte)i, this.boardData);
	}
	
	public byte getOpponentScore() throws Exception {
		return (byte) (48 - this.score - this.getOurNbSeeds() - this.getOpponentNbSeeds());  
	}
	
	public boolean isOpponentStarvingAfterPlaying(int i, int max) throws Exception {
		CustomBoard2 clone = this.clone();
		int nbSeeds;
		clone.play(i, max, true);
		if(max == OewaleBot2.OEWALE) {
			nbSeeds = clone.getOpponentNbSeeds();
		} else {
			nbSeeds = clone.getOurNbSeeds();
		}
		return nbSeeds == 0;
	}
	
	public void play(int i, int player, boolean canTakeSeeds) throws Exception {
		int holeIndex = i;
		int nbSeedInHole = this.getNbSeedInAnyHole(holeIndex);
		int indexLastHole = (holeIndex + nbSeedInHole) % 12;
		if(indexLastHole == 0) indexLastHole = 12;
		int cpt = holeIndex + 1;
		if(cpt > 12) {
			cpt = 1;
		}
		for(int j = nbSeedInHole; j > 0; j--) {
			this.boardData = LongMethod.setIVal((byte)cpt, (byte)(LongMethod.getIVal((byte)cpt, this.boardData) + 1), this.boardData);
			cpt++;
			if(cpt > 12) {
				cpt = 1;
			}
			if(cpt == holeIndex) {
				cpt++;
			}
			if(cpt > 12) {
				cpt = 1;
			}
		}
		this.boardData = LongMethod.setIVal((byte)holeIndex, (byte)0, this.boardData);
		if(canTakeSeeds) {
			if(player == OewaleBot2.OEWALE && indexLastHole > 6) {
				for(int j = indexLastHole; j >= 7; j--) {
					int nb = LongMethod.getIVal((byte)j, this.boardData);
					if( nb == 2 || nb == 3) {
						this.score += nb;
						this.boardData = LongMethod.setIVal((byte)j, (byte)0, this.boardData);

					}
					else {
						break;
					}
				}
			}else if(player == OewaleBot2.OPPONENT && indexLastHole <= 6) {
				for(int j = indexLastHole; j >= 1; j--) {
					int nb = LongMethod.getIVal((byte)j, this.boardData);
					if( nb == 2 || nb == 3) {
						this.boardData = LongMethod.setIVal((byte)j, (byte)0, this.boardData);
					}
					else {
						break;
					}
				}
			}
		}
	}
	
	public ArrayList<Integer> getIndexOfPlayableHole(int player, int offset) throws Exception {
		ArrayList<Integer> index = new ArrayList<>();
		for(int i = 1; i < 7; i++) {
			if(this.isPlayable(i+offset, player)) {
				index.add(i+offset);
			}
		}
		return index;
	}
	
	public int getHash(int index) throws Exception {
		int nbSeedStartHole = this.getNbSeedInAnyHole(index);
		int lastHole = (index + nbSeedStartHole) % 12;
		if(lastHole == 0) lastHole = 12;
		return index + 6 * nbSeedStartHole + 288 * this.getNbSeedInAnyHole(lastHole);
	}
	
	public HashMap<CustomBoard2, Integer> getSimulatedBoards(ArrayList<Integer> indexPlayableHole, int player) throws Exception {		
		CustomBoard2[] tabBoard = new CustomBoard2[indexPlayableHole.size()];
		Integer[] tabScore = new Integer[indexPlayableHole.size()];		
		Integer[] tabHash = new Integer[indexPlayableHole.size()];	
		int ind = 0;
		HashMap<CustomBoard2, Integer> res = new HashMap<CustomBoard2, Integer>();
		for(int index : indexPlayableHole) {
			CustomBoard2 copyBoard = this.clone();
			boolean canTakeSeeds = !copyBoard.isOpponentStarvingAfterPlaying(index, player);
			copyBoard.play(index, player, canTakeSeeds);
			int hash = this.getHash(index);
			NodesScore2.getInstance().getHashMap().putIfAbsent(hash, 0);
		    tabBoard[ind] = copyBoard;
		    tabScore[ind] = NodesScore2.getInstance().getHashMap().get(hash);
		    tabHash[ind] = hash;
		    ind++;
		}
		for(int i = 0; i < tabBoard.length - 1; i++) {
			int maxScore = tabScore[i];
			int indiceMax = i;
			for(int j = i+1; j < tabBoard.length; j++ ) {
				int score = tabScore[j];
				if(score > maxScore) {
					maxScore = tabScore[j];
					indiceMax = j;
				}
			}
			int tmp = tabScore[indiceMax];
			tabScore[indiceMax] = tabScore[i];
			tabScore[i] = tmp;
			
			CustomBoard2 tmp2 = tabBoard[indiceMax];
			tabBoard[indiceMax] = tabBoard[i];
			tabBoard[i] = tmp2;
			
			int tmp3 = tabHash[indiceMax];
			tabHash[indiceMax] = tabHash[i];
			tabHash[i] = tmp3;
		}
		for(int k = 0; k < tabScore.length; k++) {
			res.put(tabBoard[k], tabHash[k]);
		}
		return res;
	}
	
	public CustomBoard2 clone() {
		return new CustomBoard2(this.boardData, this.score);
	}
}
