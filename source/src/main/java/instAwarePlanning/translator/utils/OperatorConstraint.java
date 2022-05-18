package instAwarePlanning.translator.utils;

import org.metacsp.meta.simplePlanner.SimpleOperator;
import org.metacsp.multi.allenInterval.AllenIntervalConstraint;

public class OperatorConstraint {
	
	public AllenIntervalConstraint con;
	public int from, to;

	public OperatorConstraint(AllenIntervalConstraint con, int from, int to) {
		this.con = con;
		this.from = from;
		this.to = to;
	}

	public void addAdditionalConstraint(SimpleOperator op) {
		op.addConstraint(con, from, to);
	}
}
