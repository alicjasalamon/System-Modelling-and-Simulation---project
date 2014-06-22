package comma.impls;

import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.Random;

import org.jage.solution.IVectorSolution;

import comma.CommaMutation;

public class TotallyRandomCommaMutation extends CommaMutation {

	public TotallyRandomCommaMutation(int distance, double diversityMark) {
		super(distance, diversityMark);
	}

	Random rand = new Random(System.currentTimeMillis());

	@Override
	public void doMutate(IVectorSolution<Double> solution, int distance) {

		DoubleList representation = (DoubleList) solution.getRepresentation();

		for (int i = 0; i < representation.size(); i++) {
			representation.set(i, rand.nextDouble());
		}

		// System.out.println("AFTER");
		// for(int i=0; i<representation.size(); i++)
		// System.out.println(representation);

	}

}
