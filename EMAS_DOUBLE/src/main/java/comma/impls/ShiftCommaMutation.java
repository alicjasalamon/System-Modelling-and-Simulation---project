package comma.impls;

import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.Random;

import org.jage.solution.IVectorSolution;

import comma.CommaMutation;

public class ShiftCommaMutation extends CommaMutation {

	public ShiftCommaMutation(int distance, double diversityMark) {
		super(distance, diversityMark);
	}

	Random rand = new Random(System.currentTimeMillis());
	
	@Override
	public void doMutate(IVectorSolution<Double> solution, int distance) {
		// TODO Auto-generated method stub
		DoubleList representation = (DoubleList) solution.getRepresentation();
		int x = rand.nextInt(representation.size()-distance);
		int y = x+distance-1;
		
//		System.out.println("BEFORE");
//		for(int i=0; i<representation.size(); i++)
//			System.out.println(representation);
		
		double tmp = representation.get(x);
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
