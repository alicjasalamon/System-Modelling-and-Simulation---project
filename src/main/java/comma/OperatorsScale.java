package comma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comma.impls.FlipCommaMutation;
import comma.impls.InvertCommaMutation;
import comma.impls.ShiftCommaMutation;
import comma.impls.SwapCommaMutation;

public class OperatorsScale {

	List<CommaMutation> operatorSelectionScale;

	public OperatorsScale() {

		operatorSelectionScale = new ArrayList<CommaMutation>();

		operatorSelectionScale.add(new SwapCommaMutation(1, 0.2));
		operatorSelectionScale.add(new SwapCommaMutation(2, 0.25));
		operatorSelectionScale.add(new SwapCommaMutation(3, 0.3));

		operatorSelectionScale.add(new InvertCommaMutation(2, 0.7));
		operatorSelectionScale.add(new InvertCommaMutation(3, 0.8));
		operatorSelectionScale.add(new InvertCommaMutation(4, 0.9));
		
		operatorSelectionScale.add(new FlipCommaMutation(1, 0.4));
		operatorSelectionScale.add(new FlipCommaMutation(2, 0.5));
		operatorSelectionScale.add(new FlipCommaMutation(3, 0.6));
		
		operatorSelectionScale.add(new ShiftCommaMutation(2, 0.7));
		operatorSelectionScale.add(new ShiftCommaMutation(3, 0.75));
		operatorSelectionScale.add(new ShiftCommaMutation(4, 0.8));


		sortScale();

	}

	private void sortScale() {
		Collections.sort(operatorSelectionScale,
				new Comparator<CommaMutation>() {
					public int compare(CommaMutation a, CommaMutation b) {
						return (int) (b.diversityMark - a.diversityMark);
					}
				});
	}

	public int size() {
		// TODO Auto-generated method stub
		return operatorSelectionScale.size();
	}

	public CommaMutation getMutation(int r_op) {
		return operatorSelectionScale.get(r_op);
		
	}

}
