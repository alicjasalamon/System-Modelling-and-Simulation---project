package org.jage.variation.mutation.binary;

import org.jage.solution.IVectorSolution;

public interface ICommaMutation {

	void doMutate(IVectorSolution<Boolean> solution, int distance);

}
