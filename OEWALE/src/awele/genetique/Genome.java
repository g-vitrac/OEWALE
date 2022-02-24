/**
 * 
 */
package awele.genetique;

/**
 * @author toxic
 *
 */
public class Genome {

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public int score;
	
	private double[] genes;
	
	/**
	 * @return the genes
	 */
	public double[] getGenes() {
		return genes;
	}

	/**
	 * @param genes the genes to set
	 */
	public void setGenes(double[] genes) {
		this.genes = genes;
	}

	public Genome(double[] startingGenes) {
		genes = startingGenes;
	}
	
	public Genome(int score) {
		this.score = score;
	}
	
	/**
	 * Croise les deux genomes donnees en parametre et renvoie le nouveau genome croise
	 * @param g1 le premier gene parent
	 * @param g2 le second gene parent
	 * @return un gene issus du croisement de g1 et g2
	 */
	public Genome croisement(Genome g) {
		
		int nbgenes = genes.length;
		double[] genescroise = new double[nbgenes];
		
		
		for(int i = 0; i < nbgenes; i++) {
			if(Math.round(Math.random()) == 1) {
				genescroise[i] = this.getGenes()[i];
			}else {
				genescroise[i] = g.getGenes()[i];
			}
		}
 		return new Genome(genescroise);
	}
	
	/**
	 * Fais muter un genome aleatoirement sur des genes aleatoires
	 * @param minVar la borne minimale de variation d'un gene lors d'une mutation
	 * @param maxVar la borne maximale de variation d'un gene lors d'une mutation
	 * @param maxnbMutation le nombre maximale de mutation possible lors de cette mutation
	 * @param chanceMutation le pourcentage de chance qu'une mutation apparaisse sur un gene donnee
	 * @return un gene issus de la mutation du gene g donnee en parametre
	 */
	public void muter(double minVar, double maxVar, double maxnbMutation, double chanceMutation) {
		
		for(int i = 0; i < maxnbMutation; i++) {
			
			double test = Math.random();
			
			if(Math.random() < chanceMutation) {
				this.genes[(int) Math.floor((Math.random() * 6) + 1)] += Math.random() * (maxVar - minVar + 1) + minVar;
			}
				
		}

	}
	
	

}
