package org.jage.variation.mutation.impls;

import it.unimi.dsi.fastutil.booleans.BooleanList;

import java.util.Random;

import org.jage.solution.IVectorSolution;
import org.jage.variation.mutation.binary.ICommaMutation;

public class SwapCommaMutation implements ICommaMutation{
	
	Random rand = new Random(System.currentTimeMillis());

	@Override
	public void doMutate(IVectorSolution<Boolean> solution, int distance) {
		
		BooleanList representation = (BooleanList) solution.getRepresentation();
		int x = rand.nextInt(representation.size()-distance);
		
		boolean tmp = representation.get(x);
		representation.set(x, representation.get(x+distance));
		representation.set(x+distance, tmp);
	}


	
}
