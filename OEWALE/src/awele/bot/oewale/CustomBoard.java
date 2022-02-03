package awele.bot.oewale;

import utils.LongMethod;

public class CustomBoard {

	private long boardData;
	private int score;
	
	public CustomBoard(long boardData, int score) {
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
	
	public boolean isPlayable(int i, int player) {
		if(LongMethod.getIVal((byte)(i), this.getBoardData()) == 0) {
			return false;
		}
		boolean playable = true;
		if(player == 1) {
			if((this.getOpponentNbSeeds() == 0 && 6-i > this.getNbSeedInAnyHole(i)))
				playable = false;
		}else {
			if((this.getOurNbSeeds() == 0 && 12-i > this.getNbSeedInAnyHole(i)))
				playable = false;
		}
		return playable;
	}
	
	public boolean isFinish(int player) {
		if(this.getAllRemainingSeeds() <= 6 || this.score >= 25 || this.getOpponentScore() >= 25) {
			return true;
		}
		int offset = 0;
		if(player == -1) {
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
	
	public boolean isWin() {
		return this.score > this.getOpponentScore();  
	}
	
	public boolean isLose() {
		return this.getOpponentScore() > this.score;
	}
	
	public byte getAllRemainingSeeds() {
		byte sum = 0;
		for(byte i = 1; i <= 12; i++) {
			sum += LongMethod.getIVal((byte)i, this.boardData);
		}
		return sum;
	}
	
	public byte getOurNbSeeds() {
		byte sum = 0;
		for(byte i = 1; i <= 6; i++) {
			sum += LongMethod.getIVal((byte)i, this.boardData);
		}
		return sum;
	}
	
	public byte getOpponentNbSeeds() {
		byte sum = 0;
		for(byte i = 7; i <= 12; i++) {
			sum += LongMethod.getIVal((byte)i, this.boardData);
		}
		return sum;
	}
	
	public byte getNbSeedInAnyHole(int i) {
		return LongMethod.getIVal((byte)i, this.boardData);
	}
	
	public byte getOpponentScore() {
		return (byte) (48 - this.score - this.getOurNbSeeds() - this.getOpponentNbSeeds());  
	}
	
	public boolean isOpponentStarvingAfterPlaying(int i, int max) {
		CustomBoard clone = this.clone();
		int nbSeeds;
		clone.play(i, max, true);
		if(max == 1) {
			nbSeeds = clone.getOpponentNbSeeds();
		} else {
			nbSeeds = clone.getOurNbSeeds();
		}
		return nbSeeds == 0;
	}
	
	public void play(int i, int max, boolean canTakeSeeds) {
		int holeIndex = i;
		int nbSeedInHole = this.getNbSeedInAnyHole(holeIndex);
		int indexLastHole = (holeIndex + nbSeedInHole) % 12;
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
		}
		this.boardData = LongMethod.setIVal((byte)holeIndex, (byte)0, this.boardData);
		if(canTakeSeeds) {
			if(max == 1 && indexLastHole > 6) {
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
			}else if(max == -1 && indexLastHole <= 6) {
				for(int j = indexLastHole; j >= 0; j--) {
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
	
	public CustomBoard clone() {
		return new CustomBoard(this.boardData, this.score);
	}
}
