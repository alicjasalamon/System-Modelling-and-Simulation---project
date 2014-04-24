package comma;

import org.jage.solution.IVectorSolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.variation.mutation.IMutateSolution;

public class COMMAMutateSolution extends AbstractStrategy implements
		IMutateSolution<IVectorSolution<Boolean>> {
	

	CommaMutation commaMutation;
	int distance;
	
	@Override
	public void mutateSolution(IVectorSolution<Boolean> solution) {
	
		commaMutation.doMutate(solution, distance);
	}

	public void prepare(CommaMutation commaMutation, int distance) {
		this.commaMutation = commaMutation;
		this.distance = distance;
		
	}
}
