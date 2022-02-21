package awele.bot.oewale;

import java.util.HashMap;

public final class NodesScore {
	
	private static NodesScore instance;
	
	private HashMap<Integer, Integer> nodesScore = new HashMap<Integer, Integer>();

    private NodesScore() {
        
    }

    public static NodesScore getInstance() {
        if (instance == null) {
            instance = new NodesScore();
        }
        return instance;
    }
    
    public HashMap<Integer, Integer> getHashMap(){
    	return nodesScore;
    }
    
    public int getValueOfHash(int hash) {
    	return nodesScore.get(hash);
    }
}
