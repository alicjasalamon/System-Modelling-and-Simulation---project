package org.jage.variation.mutation.binary;

import org.jage.solution.IVectorSolution;

public abstract class CommaMutation {
	
	public int distance;
	public double diversityMark;
	public boolean fixedDistance;
	public CommaMutation(int distance, double diversityMark) {
		this.distance = distance;
		this.diversityMark = diversityMark;
		fixedDistance = true;
		diversityMark = 1.0;
	}
	
	public abstract void doMutate(IVectorSolution<Boolean> solution, int distance);
	
	public void doMutate(IVectorSolution<Boolean> solution)
	{
		doMutate(solution, distance);
	}

}
