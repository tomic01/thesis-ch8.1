package instAwarePlanning.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instAwarePlanning.framework.utils.Triple;

// Domain holds set of agents, their behaviors and objects in the environment
// It also holds relations between those sets (affordances) 
public class InstDomain {

	public class Agent {
		private String ag;

		public Agent(String agent) {
			this.ag = agent;
		}

		public String getAg() {
			return ag;
		}
		
		public String toString() {
			return ag;
		}
	}
	
	public class Behavior {
		private String beh;

		public Behavior(String beh) {
			this.beh = beh;
		}

		public String getBeh() {
			return beh;
		}
		
		public String toString() {
			return beh;
		}
	}

	public class Obj {
		private String obj;

		public Obj(String obj) {
			this.obj = obj;
		}

		public String getObj() {
			return obj;
		}
		
		public String toString() {
			return obj;
		}
	}
	
	
	// SETS -- implemented as MAP for convenience
	private Map<String, Agent> agentSet;
	private Map<String, Behavior> behaviorSet;
	private Map<String, Obj> objectSet;

	protected List<Triple<Agent, Behavior, ?>> affordances;
	
	public InstDomain() {
		super();

		this.agentSet = new HashMap<String, Agent>();
		this.behaviorSet = new HashMap<String, Behavior>();
		this.objectSet = new HashMap<String, Obj>();
		
		// Affordances
		this.affordances = new ArrayList<Triple<Agent, Behavior, ?>>();
	}
	
	public void addAgent(Agent agent) {
		String agentName = agent.getAg();
		if (!agentSet.containsKey(agentName))
		{
			this.agentSet.put(agentName, agent);
		}
		else {
			throw new Error("Agent name: " + agentName + " already exists");
		}
	}
	
	public void addBehavior(Behavior behavior) {
		String behaviorName = behavior.getBeh();
		if (!behaviorSet.containsKey(behaviorName))
		{
			this.behaviorSet.put(behaviorName, behavior);
		}
		else {
			throw new Error("Behavior name: '" + behaviorName + "' already exists");
		}
	}
	
	public void addObject(Obj obj) {
		String objName = obj.getObj();
		if (!objectSet.containsKey(objName))
		{
			this.objectSet.put(objName, obj);
		}
		else {
			throw new Error("Object name: '" + objName + "' already exists");
		}
	}
	
	public Map<String, Agent> getAgentSet() {
		return agentSet;
	}
	
	public Map<String, Behavior> getBehaviorSet() {
		return behaviorSet;
	}
	
	public Map<String, Obj> getObjectSet() {
		return objectSet;
	}

	public List<Triple<Agent, Behavior, ?>> getAffordances() {
		return affordances;
	}
	


}
