package comma.impls;

import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.Random;

import org.jage.solution.IVectorSolution;

import comma.CommaMutation;

public class SwapCommaMutation extends CommaMutation{
	
	public SwapCommaMutation(int distance, double diversityMark) {
		super(distance, diversityMark);
	}

	Random rand = new Random(System.currentTimeMillis());

	@Override
	public void doMutate(IVectorSolution<Double> solution, int distance) {
		
		DoubleList representation = (DoubleList) solution.getRepresentation();
		int x = rand.nextInt(representation.size()-distance);
		
		double tmp = representation.get(x);
		representation.set(x, representation.get(x+distance));
		representation.set(x+distance, tmp);
	}


	
}
