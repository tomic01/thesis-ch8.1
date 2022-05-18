package instAwarePlanning.planning.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instAwarePlanning.SAM.SamSimple;
import instAwarePlanning.communication.Comm;

public class Dispatcher {

	// Behaviors to be dispatched (CopyPasted from the domain)
	// (Not using shared enumeration between classes, since packages here could be
	// part of a separate program)
	// However, some shared DATA representation (JSON?) would be suitable
	private static final String strGive = "give";
	private static final String strPick = "pick";
	private static final String strLeaveObject = "leaveObject";
	private static final String strLocate = "locate";

	private static final String pubMsgStart = "publish#D7.1#[{";
	private static final String pubMsgEnd = "}]";
	
	private static final Map<String, String> localVars = new HashMap<>();
	static {
		localVars.put("give_volume","1.0");
		localVars.put("give_speed","100");
		localVars.put("give_pitch","1.1");
	}
	
	public static void Dispatch(final Comm comm, String uid, String agentName, String behName, String[] params) {

		String dispatchMsg = CreateDispatchingMsg(uid, agentName, behName, params);
		System.out.println("***********************");
		System.out.println(dispatchMsg);
		System.out.println("***********************");
		
		comm.addDispatchMsg(dispatchMsg);
		
	}

	
	private static String CreateDispatchingMsg(String uid, String agentName, String behName, String[] params) {
		
		String dispatchMsg = pubMsgStart + "(id " + uid + ") (type " + behName + ") (robot " + agentName + ")";

		List<String> behParams = addBehParams(behName, params);
		String paramsStr = "";
		if (!behParams.isEmpty()) {
			paramsStr = "(apar";
			for (String behParam : behParams) {
				paramsStr += " " + behParam;
			}
			paramsStr += ")";
		}
		
		dispatchMsg+=paramsStr;
		
		dispatchMsg += pubMsgEnd;

		return dispatchMsg;
	}

	private static List<String> addBehParams(String behName, String[] params) {

		List<String> behParams = new ArrayList<String>();
		

		for (String param : params) {
			behParams.add(param);
		}

		switch (behName) {
		case strLocate:
			break;
		case strPick:
			String valueX = SamSimple.stateVars.get("x");
			String valueY = SamSimple.stateVars.get("y");
			String valueHeight = SamSimple.stateVars.get("height");
			String valueWidth = SamSimple.stateVars.get("width");
			
			behParams.add(valueX);
			behParams.add(valueY);
			behParams.add(valueHeight);
			behParams.add(valueWidth);
			
			break;
		case strGive:
			
			String give_volume = localVars.get("give_volume");
			String give_speed = localVars.get("give_speed");
			String give_pitch = localVars.get("give_pitch");
			
			behParams.add(give_volume);
			behParams.add(give_speed);
			behParams.add(give_pitch);
			
			break;
		case strLeaveObject:
			break;

		default:
			throw new IllegalArgumentException("[Dispatcher] Behavior '" + behName + " is not supported! ");
		}

		return behParams;

	}



}
