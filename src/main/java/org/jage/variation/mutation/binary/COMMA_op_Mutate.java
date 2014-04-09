package org.jage.variation.mutation.binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.jage.solution.IVectorSolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.variation.mutation.IMutateSolution;
import org.jage.variation.mutation.impls.FlipCommaMutation;
import org.jage.variation.mutation.impls.InvertCommaMutation;
import org.jage.variation.mutation.impls.SwapCommaMutation;

public class COMMA_op_Mutate extends AbstractStrategy implements
		IMutateSolution<IVectorSolution<Boolean>> {
	
	List<CommaMutation> operatorSelectionScale;
	Random rand = new Random();
	int distance;
	
	
	public COMMA_op_Mutate() {
		
		operatorSelectionScale = new ArrayList<CommaMutation>();
		
		for(double diversityMark = 0.1; diversityMark < 0.9; diversityMark += 0.1) {
			for(int distance = 1; distance < 3; ++distance) {
				operatorSelectionScale.add(new SwapCommaMutation(distance, diversityMark));
				operatorSelectionScale.add(new FlipCommaMutation(distance, diversityMark));
				operatorSelectionScale.add(new InvertCommaMutation(distance, diversityMark));
				operatorSelectionScale.add(new SwapCommaMutation(distance, diversityMark));
			}
		}
				
		sortScale();
		
	}

	@Override
	public void mutateSolution(IVectorSolution<Boolean> solution) {
	
		selectOperator().doMutate(solution, distance);
	}
	
	private CommaMutation selectOperator()
	{
		/*
		 * to bedzie podane z gory
		 * jakos to trzeba odczytać
		 */
		double gen=50, maxGen=100;
		double rate = 1-(gen/(maxGen+1));
		boolean fixedRate = true;
		int r = 2;
		/*
		 * wlasciwe obliczenia
		 */
		int agentsCount=100;
		int scaleSize = operatorSelectionScale.size();
		int lowerBound = (int) Math.max(0, ((r*scaleSize)/agentsCount)-rate*scaleSize);
		int upperBound = (int) Math.min(scaleSize-1, ((r*scaleSize)/agentsCount)+rate*scaleSize);
		
		int algoPosition = rand.nextInt(upperBound-lowerBound+1)+lowerBound;
		
		CommaMutation selectedOperator = operatorSelectionScale.get(algoPosition);
		
		if(fixedRate)
			distance = selectedOperator.distance;
		else
			distance = rand.nextInt(selectedOperator.distance)+1;
			
		return selectedOperator;
			
	}

	private void sortScale()
	{
		Collections.sort(operatorSelectionScale, new Comparator<CommaMutation>() {
		    public int compare(CommaMutation a, CommaMutation b) {
		        return (int) (b.diversityMark - a.diversityMark);
		    }
		});
	}

}
