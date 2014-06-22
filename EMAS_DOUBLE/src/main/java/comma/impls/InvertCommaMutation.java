package comma.impls;

import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.Random;

import org.jage.solution.IVectorSolution;

import comma.CommaMutation;

public class InvertCommaMutation extends CommaMutation{

	public InvertCommaMutation(int distance, double diversityMark) {
		super(distance, diversityMark);
	}

	Random rand = new Random(System.currentTimeMillis());

	@Override
	public void doMutate(IVectorSolution<Double> solution, int distance) {

		DoubleList representation = (DoubleList) solution.getRepresentation();
		int x = rand.nextInt(representation.size()-distance);
		int y = x+distance-1;
		
//		System.out.println("BEFORE");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);
		
		for(int i=0; i<distance/2; i++)
		{
			double tmp = representation.get(x+i);
			representation.set(x+i, representation.get(y-i));
			representation.set(y-i, tmp);
		}
		
//		System.out.println("AFTER");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);

	}
	

}
