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
	
	public boolean isPlayable(int i) {
		if(LongMethod.getIVal((byte)(i+1), this.getBoardData()) == 0) {
			return false;
		}
		boolean playable = true;
		if(this.getOpponentNbSeeds() == 0 && this.getNbSeedInAnyHole((byte)(i+1)) + i <= 6) // if our opponent is starving and we can give him seed 
			playable = false;
		return playable;
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
	
	public boolean isWin() {
		return this.score > this.getOpponentScore();  
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
	
	public byte getNbSeedInAnyHole(byte i) {
		return LongMethod.getIVal((byte)i, this.boardData);
	}
	
	public byte getOpponentScore() {
		return (byte) (48 - this.score - this.getOurNbSeeds() - this.getOpponentNbSeeds());  
	}
	
	public void play(byte i, byte max) {
		byte holeIndex =  (byte) (i + 1);
		byte nbSeedInHole = this.getNbSeedInAnyHole(holeIndex);
		byte indexLastHole = (byte)(((holeIndex + nbSeedInHole) % 12));
		byte cpt = (byte) (holeIndex + 1);
		if(cpt > 12) cpt = 1;
		for(byte j = nbSeedInHole; j > 0; j--) {
			this.boardData = LongMethod.setIVal(cpt, (byte)(LongMethod.getIVal(cpt, this.boardData)+1), this.boardData);
			cpt++;
			if(cpt > 12) cpt = 1;
			if(cpt == holeIndex) cpt++;
		}
		this.boardData = LongMethod.setIVal(holeIndex, (byte)0, this.boardData);
		if(max == -1 && indexLastHole > 6) {

			for(byte j = indexLastHole; j >= 7; j--) {
				byte nb = LongMethod.getIVal(j, this.boardData);
				if( nb == 2 || nb == 3) {
					this.score += nb;
					this.boardData = LongMethod.setIVal(j, (byte)0, this.boardData);

				}
				else {
					break;
				}
			}
		}else if(max == 1 && indexLastHole < 6) {
			for(byte j = indexLastHole; j >= 0; j--) {
				byte nb = LongMethod.getIVal(j, this.boardData);
				if( nb == 2 || nb == 3) {
					this.boardData = LongMethod.setIVal(j, (byte)0, this.boardData);
				}
				else {
					break;
				}
			}
		}
	}
	
	public CustomBoard clone() {
		return new CustomBoard(this.boardData, this.score);
	}
}
