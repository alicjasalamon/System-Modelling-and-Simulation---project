package org.jage.variation.mutation.impls;

import it.unimi.dsi.fastutil.booleans.BooleanList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jage.solution.IVectorSolution;
import org.jage.variation.mutation.binary.ICommaMutation;

public class FlipCommaMutation implements ICommaMutation {

	Random rand = new Random(System.currentTimeMillis());

	@Override
	public void doMutate(IVectorSolution<Boolean> solution, int distance) {

		BooleanList representation = (BooleanList) solution.getRepresentation();
		List<Integer> usedIndexes = new ArrayList<Integer>();
		
//		System.out.println("BEFORE");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);
		
		while (usedIndexes.size() < distance) {
			int x;
			do {
				x = rand.nextInt(representation.size());
			} while(usedIndexes.contains(x));
			usedIndexes.add(x);
		
			boolean tmp = representation.get(x);
			representation.set(x, !tmp);
		}
		
//		System.out.println("AFTER");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);

	}

}
