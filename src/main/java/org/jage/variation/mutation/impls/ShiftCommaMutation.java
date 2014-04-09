package org.jage.variation.mutation.impls;

import it.unimi.dsi.fastutil.booleans.BooleanList;

import java.util.Random;

import org.jage.solution.IVectorSolution;
import org.jage.variation.mutation.binary.ICommaMutation;

public class ShiftCommaMutation implements ICommaMutation {

	Random rand = new Random(System.currentTimeMillis());
	
	@Override
	public void doMutate(IVectorSolution<Boolean> solution, int distance) {
		// TODO Auto-generated method stub
		BooleanList representation = (BooleanList) solution.getRepresentation();
		int x = rand.nextInt(representation.size()-distance);
		int y = x+distance-1;
		
//		System.out.println("BEFORE");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);
		
		boolean tmp = representation.get(x);
		for(int i=0; i<distance-1; i++)
		{
			representation.set(x+i, representation.get(x+i+1));
		}
		representation.set(y, tmp);
		
		
//		System.out.println("AFTER");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);
		
		
	}

}
