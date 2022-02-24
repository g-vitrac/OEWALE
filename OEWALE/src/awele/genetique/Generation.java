/**
 * 
 */
package awele.genetique;

import java.util.ArrayList;

/**
 * @author toxic
 *
 */
public class Generation {
	
	public ArrayList<Genome> generation = new ArrayList<Genome>();
	public ArrayList<Genome> nextGeneration = new ArrayList<Genome>();
	
	public Generation() {
		gen_test();
	}
	
	public void gen_test() {
		generation.add( new Genome(5));
		generation.add(new Genome(10));
		generation.add(new Genome(6));
		generation.add(new Genome(3));
		generation.add(new Genome(9));
		generation.add(new Genome(1));
		
	}
	
	public void nextGeneration() {
		nextGeneration.clear();
		for(int i = 0; i < generation.size(); i++) {
			if(nextGeneration.size() == generation.size()) break; 
			if(i < generation.size() / 2) {
				nextGeneration.add(generation.get(i));
			}else {
				if(Math.random() > 0.5 ) {
					nextGeneration.add(generation.get(i).croisement(generation.get(0)));
				}else {
					generation.get(i).muter(-0.1, 0.1, 3, 0.5);
					nextGeneration.add(generation.get(i));
				}
			}
		}
	}
	
	public void evaluate() {
		
	}
	
	public void selection() {
		for(int i = 0; i < generation.size()-1; i++) {
			for(int j = i; j < (generation.size()); j++) {
				if(generation.get(i).score < generation.get(j).score ) {
					Genome tmp = generation.get(i);
					generation.set(i, generation.get(j));
					generation.set(j, tmp);
				}
			}
		}
	}

}
