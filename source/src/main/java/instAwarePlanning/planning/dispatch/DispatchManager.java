package instAwarePlanning.planning.dispatch;

import org.metacsp.dispatching.DispatchingFunction;
import org.metacsp.multi.activity.SymbolicVariableActivity;

import instAwarePlanning.SAM.SamSimple;
import instAwarePlanning.communication.Comm;
import instAwarePlanning.planning.InstPlanner;

public class DispatchManager extends DispatchAbstract {
	
	Comm comm;
	
	class MessageInfo {
		String agentName;
		String behaviorName;
		String[] params;
		
		MessageInfo(SymbolicVariableActivity act){
			
			agentName = act.getComponent();
			behaviorName = null;
			
			String fullBehaviorName = act.getSymbolicVariable().getSymbols()[0];
			Integer brackIndex = fullBehaviorName.lastIndexOf("(");
			if (brackIndex != -1) { // some value returned
				behaviorName = fullBehaviorName.substring(0, brackIndex);
				String allParams = fullBehaviorName.substring(brackIndex + 1, fullBehaviorName.length() - 1);
				params = allParams.split(",");

			} else {
				behaviorName = fullBehaviorName;
			}

		}
	}

	// Planner components
	private final String[] actuators;
	boolean testingMode;

	public DispatchManager(final String[] actuators, final Comm comm, Boolean testingMode) {
		this.actuators = actuators;
		this.comm = comm;
		this.testingMode = testingMode;
	}

	public void dispatcherSetup() {
		
		// Get through all robots and setup dispatch function
		for (final String actuator : this.actuators) {
			if (actuator.equals("used"))
				continue;
			dispatcherSetup(actuator);
		}
		
		if (!testingMode) {
			new BehaviorMonitor(comm);
		}
	}

	private void dispatcherSetup(final String agentName) {
		
		// Dispatching function. Called when the behavior is dispatched.
		final DispatchingFunction dispatchBehavior = new DispatchingFunction(agentName) {
			
			@Override
			public void dispatch(SymbolicVariableActivity activity) {
				
				MessageInfo msgInfo = new MessageInfo(activity);
				
				if (msgInfo.behaviorName.equals("leaveObject")) {
					String agent_has = msgInfo.agentName + "_has";
					if (SamSimple.stateVars.get(agent_has).equals("none")) {
						System.out.println("!!!**** STOPING INTERNALLY");
						System.out.println("[Dispatcher:] Skipped: " + msgInfo.agentName + "::" + msgInfo.behaviorName);
						System.out.println("pepper01_has: " + SamSimple.stateVars.get("pepper01_has"));
						System.out.println("pepper02_has: " + SamSimple.stateVars.get("pepper02_has"));
						
						DispatchUtils.stopActivity(activity);
						return;
					}
				}
				
				// String uniqueName = agentName.toLowerCase() + behaviorName.toLowerCase();
				String uid = createUID(msgInfo.agentName, msgInfo.behaviorName, msgInfo.params);
				ID_Activity_Map.put(uid, activity);
				
				Dispatcher.Dispatch(comm, uid, msgInfo.agentName, msgInfo.behaviorName, msgInfo.params);
				
			}

			@Override
			public boolean skip(SymbolicVariableActivity act) {
				//System.out.print(";");
				
//				MessageInfo msgInfo = new MessageInfo(act);
//			
//				// If an agent doesn't have an object skip this behavior
//				if (msgInfo.behaviorName.equals("leaveObject")) {
//					String agent_has = msgInfo.agentName + "_has";
//					if (SamSimple.stateVars.get(agent_has).equals("none")) {
//						System.out.println("x");
//						//System.out.println("[Dispatcher:] Skipped: " + msgInfo.agentName + "::" + msgInfo.behaviorName);
//						//System.out.println("pepper01_has: " + SamSimple.stateVars.get("pepper01_has"));
//						//System.out.println("pepper02_has: " + SamSimple.stateVars.get("pepper02_has"));
//						return true;
//					}
//				}
				
				
				return false;
			}
		};

		InstPlanner.animator.addDispatchingFunctions(dispatchBehavior);
	}

	private static String createUID(String agentName, String behName, String[] params) {
		String id_raw = agentName + behName;
		for (String param : params) {
			id_raw += param;
		}
		Integer id = id_raw.hashCode();
		return id.toString();
	}

	// // TESTING ONLY: Automatically finishes behaviors after some delay
	// @SuppressWarnings("unused")
	// private void monitorResultTest() {
	// while (true) {
	// // Sleep for X seconds
	// try {
	// Thread.sleep(11000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// // Now get through each behavior, find one and stop it
	// if (!DispatchAbstract.ID_Activity_Map.isEmpty()) {
	// for (Entry<String, SymbolicVariableActivity> entry :
	// DispatchAbstract.ID_Activity_Map.entrySet()) {
	// String behaviorID = entry.getKey();
	// SymbolicVariableActivity act = entry.getValue();
	// DispatchUtils.stopExecutingActivity(behaviorID);
	// break;
	// }
	// }
	// }
	// }

}
