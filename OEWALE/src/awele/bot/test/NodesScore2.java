package awele.bot.test;

import java.util.HashMap;

public final class NodesScore2 {
	
	private static NodesScore2 instance;
	
	private HashMap<Integer, Integer> nodesScore = new HashMap<Integer, Integer>();

    private NodesScore2() {
        
    }

    public static NodesScore2 getInstance() {
        if (instance == null) {
            instance = new NodesScore2();
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
