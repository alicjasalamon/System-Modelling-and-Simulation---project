package ranking;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jage.emas.agent.IndividualAgent;

public final class Ranking {

	private final static Ranking ourInstance = new Ranking();
	private IndividualAgent[] agents;

	public static Ranking getInstance() {
		return ourInstance;
	}

	private Ranking() {
	}

	/*public List<IndividualAgent> getRanking() {
		sort();
		return agents;
	}*/

	public void setRanking(List<IndividualAgent> agents) {
		this.agents = new IndividualAgent[agents.size()];
		int i = 0;
		for(IndividualAgent agent : agents) {
			this.agents[i++] = agent;
		}
		sort();
	}
	
	//miejsce 0 				- najlepszy fitness
	//miejsce agents.length-1 	- najgorszy fitness
	public int getMiejsceWRankingu(double fitness) {
		int i = 0;
		while(i < agents.length && fitness > this.agents[i].getEffectiveFitness()) ++i;
		return agents.length - i - 1;
	}

	public void printRanking() {
		//System.out.println("=======================" + agents.size());
		for (IndividualAgent agent : agents) {
			System.out.println("-------" + agent.getEffectiveFitness());
		}
	}

	private void sort() {
		Arrays.sort(agents, new Comparator<IndividualAgent>() {
			@Override
			public int compare(IndividualAgent o1, IndividualAgent o2) {
				Double o1fit = o1.getEffectiveFitness();
				Double o2fit = o2.getEffectiveFitness();
				return o1fit.compareTo(o2fit);
			}
		});
	}
}
