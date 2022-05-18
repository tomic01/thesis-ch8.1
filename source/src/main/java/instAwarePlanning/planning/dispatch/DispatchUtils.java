package instAwarePlanning.planning.dispatch;

import org.metacsp.dispatching.DispatchingFunction;
import org.metacsp.multi.activity.SymbolicVariableActivity;

import instAwarePlanning.planning.InstPlanner;

public class DispatchUtils {
	
	public static void stopExecutingActivity(String uniqueBehName) {
		if (DispatchAbstract.ID_Activity_Map.containsKey(uniqueBehName)) {
			SymbolicVariableActivity executingAct = DispatchAbstract.ID_Activity_Map.get(uniqueBehName);
			stopActivity(executingAct);
			DispatchAbstract.ID_Activity_Map.remove(uniqueBehName);
		}
	}
	
	static void stopActivity(SymbolicVariableActivity act) {
		DispatchingFunction functToStop = InstPlanner.animator.getDispatcher().getDispatchingFunction(act.getComponent());
		functToStop.finish(act);
	}



}
