package comma;


import java.util.List;

import org.jage.solution.IVectorSolution;
import org.jage.variation.mutation.AbstractStochasticMutate;

public final class COMMAMutatePopulation extends AbstractStochasticMutate<Boolean>
{

	CommaMutation commaMutation;
	int distance;
	
	public void prepare(CommaMutation commaMutation, int distance) {
		this.commaMutation = commaMutation;
		this.distance = distance;
		
	}

	@Override
	protected void doMutate(List<Boolean> representation, int index) {
		//commaMutation.doMutate(solution, distance);
	}
	
}
/*

	private int GEN = 0;
	private int MAXGEN;
	private int agents;
	private boolean fixedDistance;
	private int distance;
	
	@Inject
	private IMutateSolution<S> mutate;
	
	Random rand = new Random();
	
	private OperatorsScale scale = new OperatorsScale();
	private CommaMutation commaMutation;
	
    public void setMAXGEN (final int MAXGEN) {
	    this.MAXGEN = MAXGEN;
    }
	
    public void setFixedDistance(boolean fixedDistance) {
    	this.fixedDistance = fixedDistance;
    }
    
    public void setAgents(int agents) {
		this.agents = agents;
	}

	@Override
    public void mutatePopulation(final IPopulation<S, ?> population) {

		double rate = 1 - (1.0*GEN/(MAXGEN+1));
		int alpha = scale.size();
		int r=agents-1;
		
		LABSEvaluator eval = new LABSEvaluator();
		Map<S, ?> map = population.asMap();
		
		Map<S, Double> sols = new HashMap<S, Double>();
		
		for(S s : map.keySet()) {
			Object val = map.get(s);
			double x = eval.evaluate((IVectorSolution)s);
			sols.put(s, x);
		}

		Map<S, Double> sorted = MapUtil.sortByValue(sols);
		
		
		//for (final S solution : population)
		for(Entry<S, Double> entry : sorted.entrySet()) {
			int r_lower = (int) Math.max(0, (1.0*r*alpha/agents) - rate * alpha);
			int r_upper = (int) Math.min(alpha-1, (1.0 * r*alpha/agents) + rate * alpha);
			
			int r_op = r_lower == r_upper ? r_lower: rand.nextInt(r_upper-r_lower)+r_lower;
			
			commaMutation = scale.getMutation(r_op);
			
		
			if(fixedDistance)
				distance = commaMutation.distance;
			else
				distance = rand.nextInt(commaMutation.distance)+1;	
			
			
			((COMMAMutateSolution)mutate).prepare(commaMutation, distance);
			mutate.mutateSolution(entry.getKey()); 
			r--;
		}
		
		++GEN;
		
	}
	
	static class MapUtil
	{
	    public static <K, V extends Comparable<? super V>> Map<K, V> 
	        sortByValue( Map<K, V> map )
	    {
	        List<Map.Entry<K, V>> list =
	            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	        {
	            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	            {
	                return (o1.getValue()).compareTo( o2.getValue() );
	            }
	        } );

	        Map<K, V> result = new LinkedHashMap<K, V>();
	        for (Map.Entry<K, V> entry : list)
	        {
	            result.put( entry.getKey(), entry.getValue() );
	        }
	        return result;
	    }
	}

}*/