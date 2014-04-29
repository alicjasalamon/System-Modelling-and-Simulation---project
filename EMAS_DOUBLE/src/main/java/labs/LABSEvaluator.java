package labs;

import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

import org.jage.evaluation.ISolutionEvaluator;
import org.jage.solution.IVectorSolution;

public class LABSEvaluator implements
		ISolutionEvaluator<IVectorSolution<Double>, Double> {

	@Override
	public Double evaluate(IVectorSolution<Double> solution) {

		final DoubleList representation = (DoubleList) solution.getRepresentation();

		double E = 0.0;
		double C = 0.0;

		int L = representation.size();

		for (int distance = 1; distance < L - 1; distance++) {
			C = 0.0;
			for (int i = 0; i < representation.size() - distance; i++) {
				C += (representation.getDouble(i) > 0 ? 1 : -1)
						* (representation.getDouble(i + distance) > 0 ? 1 : -1);
			}
			E += C * C;

		}
		// System.out.println(L*L/(2*E));
		return L * L / (2 * E);
	}

}
