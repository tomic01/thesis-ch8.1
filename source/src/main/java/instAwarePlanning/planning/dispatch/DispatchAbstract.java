package instAwarePlanning.planning.dispatch;

import java.util.HashMap;

import org.metacsp.multi.activity.SymbolicVariableActivity;

public abstract class DispatchAbstract {

	// <Behaviors ID - Planner Activity>
	public static HashMap<String, SymbolicVariableActivity> ID_Activity_Map = new HashMap<String, SymbolicVariableActivity>();

	//protected abstract void dispatch(SymbolicVariableActivity activity);
}
