package org.jage.variation.mutation;

import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import org.jage.population.IPopulation;
import org.jage.solution.ISolution;
import org.jage.strategy.AbstractStrategy;

public final class COMMAMutatePopulation<S extends ISolution> extends AbstractStrategy implements
        IMutatePopulation<S> {

	@Inject
	private IMutateSolution<S> mutate;


	@Override
	public void mutatePopulation(final IPopulation<S, ?> population) {
	
		//1. znalezc maxGen, gen, fixedDistancOe
		//2. jak wziac fitness z solutiona??
		//3. jakos przekazac to nizej
		
		population.asEvaluationList();
		population.asSolutionList();
		
		Collections.sort(population.asSolutionList(), new Comparator<S>() {

			@Override
			public int compare(S o1, S o2) {
				return 0;
			}
		});
		
		//pozycje daloby sie wziac z populacji
		for (final S solution : population) {
			mutate.mutateSolution(solution);
		}
	}
}
