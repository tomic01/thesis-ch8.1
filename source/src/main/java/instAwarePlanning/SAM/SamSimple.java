package instAwarePlanning.SAM;

import java.util.HashMap;
import java.util.Map;

import org.metacsp.sensing.Sensor;

import instAwarePlanning.communication.Comm;
import instAwarePlanning.communication.FeedbackClient;
import instAwarePlanning.communication.IFeedbackObserver;
import instAwarePlanning.communication.utils.FeedReader;
import instAwarePlanning.planning.InstPlanner;
import instAwarePlanning.verify.Verifier;

// SAM - Situation Awareness Module 
// The goal of SAM is to update the states, on which planner can reason upon,
// by receiving sensors data and interpreting it to meaningful states
public class SamSimple implements IFeedbackObserver {

	public static Map<String, String> stateVars = new HashMap<>();
	static {
		// Define state variables here with their initial values
		stateVars.put("active_human01_give", "none");
		stateVars.put("active_human01_pick", "none");
		stateVars.put("active_human02_give", "none");
		stateVars.put("active_human02_pick", "none");

		// This is used for planning part:
		// if e.g. 'leave object' is dispatched, and pepper does not have anything, then
		// the action is skipped
		stateVars.put("pepper01_has", "none");
		stateVars.put("pepper02_has", "none");

	}

	private Map<String, Sensor> sensors = new HashMap<>();

	public SamSimple(Comm comm) {
		comm.registerToFeedback(this);
	}

	public void registerSensor(String sensorName, Sensor sensor) {
		if (stateVars.containsKey(sensorName)) {
			sensors.put(sensorName, sensor);
			return;
		} else {
			throw new Error("SensorName does not exists in stateVariable map. SensorName: " + sensorName);
		}
	}

	@Override
	public void Update(String data) {
		String[] feed = data.split("#");
		if (feed[0].equals("subscribed") && feed[1].equals("D6.3")) {
			System.out.println("[SamSimple] Correct feedback recieved...");
			Map<String, String> params = FeedReader.getFeedParams(feed[2]);
			params.forEach((key, value) -> System.out.println("Key/Value: " + key + ":" + value));
			updateStateVars(params);
		}
	}

	private void updateStateVars(Map<String, String> params) {

		// If this is action recognition behavior
		if (params.containsKey("type") && params.get("type").equals("behRecog")) {
			String agent = params.get("actionAgent");
			String beh = params.get("actionName");
			String obj = params.get("actionObject");
			String start = params.get("start");

			String stateVar = "";
			if (agent.equals("agent1")) {
				if (beh.equals("give")) {
					stateVar = "active_human01_give";
				} else if (beh.equals("pick")) {
					stateVar = "active_human01_pick";
				}
			} else if (agent.equals("agent2")) {
				if (beh.equals("give")) {
					stateVar = "active_human02_give";
				} else if (beh.equals("pick")) {
					stateVar = "active_human02_pick";
				}
			} else {
				throw new Error("Behavior Recognition message is not supported...");
			}

			updateStateVariablesAndSensors(stateVar, obj, start);
		}
		// [ (id 2321) (robot pepper01) (type behRecog) (status running) (actionName
		// give) (actionObject yellow) (actionAgent human01) (start true) ]

		// Check if 'hasObject' is updated
		String robot = params.get("robot");
		String object = params.get("hasobject");
		

		if (object != null && !object.isEmpty()) {
			switch (robot) {
			case "pepper01":
				System.out.println("[SAM] Updating: pepper01_has = " + object);
				stateVars.put("pepper01_has", object);
				break;
			case "pepper02":
				System.out.println("[SAM] Updating: pepper02_has = " + object);
				stateVars.put("pepper02_has", object);
				break;
			default:
				System.out.println("Unknown agent name");
			}
		}

	}

	private void updateStateVariablesAndSensors(String stateVariableName, String newValue, String startFlag) {

		// If the flag is false, reset sensor to 'none'
		if (startFlag.equals("false")) {
			stateVars.put(stateVariableName, "none");
			Sensor sensor = sensors.get(stateVariableName);
			sensor.postSensorValue("none", Verifier.animator.getTimeNow());
			return;
		}

		String oldValue = stateVars.get(stateVariableName);

		// If old and new variables are different, update
		if (!oldValue.equals(newValue)) {
			stateVars.put(stateVariableName, newValue);
			Sensor sensor = sensors.get(stateVariableName);
			sensor.postSensorValue(newValue, Verifier.animator.getTimeNow());
		}
	}

}
