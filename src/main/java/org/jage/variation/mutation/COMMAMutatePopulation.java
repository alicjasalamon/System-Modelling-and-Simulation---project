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
	
		//TODO: posortowac populacje
		//znalezc maxGen, gen, fixedDistancOe
		//jak wziac fitness z solutiona
		
		
		//pozycje daloby sie wziac z populacji
		for (final S solution : population) {
			population.getEvaluation(solution);
			{
				
			}
				
				mutate.mutateSolution(solution);
		}
	}
}
