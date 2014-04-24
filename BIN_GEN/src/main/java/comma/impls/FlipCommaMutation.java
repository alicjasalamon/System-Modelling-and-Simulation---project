package comma.impls;

import it.unimi.dsi.fastutil.booleans.BooleanList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jage.solution.IVectorSolution;

import comma.CommaMutation;

public class FlipCommaMutation extends CommaMutation {

	public FlipCommaMutation(int distance, double diversityMark) {
		super(distance, diversityMark);
	}

	Random rand = new Random(System.currentTimeMillis());

	@Override
	public void doMutate(IVectorSolution<Boolean> solution, int distance) {

		BooleanList representation = (BooleanList) solution.getRepresentation();
		List<Integer> usedIndexes = new ArrayList<Integer>();
		List<Integer> notUsedIndexes = new ArrayList<Integer>();
		for(int i = 0; i < representation.size(); ++i){
			notUsedIndexes.add(i);
		}
		for(int used = 0; used < distance; ++used){
			int notUsedIndex = rand.nextInt(notUsedIndexes.size());
			int notUsed = notUsedIndexes.get(notUsedIndex);
			notUsedIndexes.remove(notUsedIndex);
			boolean tmp = representation.get(notUsed);
			representation.set(notUsed, !tmp);
		}
		
		
		
//		System.out.println("BEFORE");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);
		/*
		while (usedIndexes.size() < distance) {
			int x;
			do {
				x = rand.nextInt(representation.size());
				//System.out.println("ziom");
			} while(!usedIndexes.contains(x));
			usedIndexes.add(x);
		
			boolean tmp = representation.get(x);
			representation.set(x, !tmp);
		}
		*/
//		System.out.println("AFTER");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);

	}

}
