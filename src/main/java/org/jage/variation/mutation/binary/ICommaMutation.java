package org.jage.variation.mutation.binary;

import javax.inject.Inject;

import org.jage.random.INormalizedDoubleRandomGenerator;
import org.jage.solution.IVectorSolution;

public abstract class ICommaMutation {
	
	@Inject
	protected INormalizedDoubleRandomGenerator rand;

	public abstract void doMutate(IVectorSolution<Boolean> solution,
			int distance);


}
