package instAwarePlanning.framework;

import java.util.List;
import java.util.Map.Entry;

import instAwarePlanning.framework.InstDomain.Agent;
import instAwarePlanning.framework.InstDomain.Behavior;
import instAwarePlanning.framework.InstDomain.Obj;
import instAwarePlanning.framework.Institution.Act;
import instAwarePlanning.framework.Institution.Art;
import instAwarePlanning.framework.Institution.Role;
import instAwarePlanning.framework.utils.Pair;
import instAwarePlanning.framework.utils.Relation;
import instAwarePlanning.framework.utils.Triple;

public class Grounding {

	public Relation<Role, Agent> Ga;
	public Relation<Act, Behavior> Gb;
	public Relation<Art, Obj> Go;
	
	private Institution institution = null;
	private InstDomain instDomain = null;

	@SuppressWarnings("unused")
	private Grounding() {
	}

	public Grounding(Institution institution, InstDomain instDomain) {
		super();
		this.institution = institution;
		this.instDomain = instDomain;

		Ga = new Relation<Role, Agent>();
		Gb = new Relation<Act, Behavior>();
		Go = new Relation<Art, Obj>();
	}

	// Get the existing role and agent from the institution by the name and add
	protected void addRelationGa(String roleToGet, String agentToGet) {
		Role role = (Role) institution.getRoleSet().get(roleToGet);
		Agent ag = (Agent) instDomain.getAgentSet().get(agentToGet);

		if (role == null) {
			throw new Error("There is no Role with the name: " + roleToGet);
		} else if (ag == null) {
			throw new Error("There is no Agent with the name: " + agentToGet);
		}

		Ga.addRelation(role, ag);
	}

	protected void addRelationGa(Role roleToGet, Agent agentToGet) {
		Role role = (Role) institution.getRoleSet().get(roleToGet.getRole());
		Agent ag = (Agent) instDomain.getAgentSet().get(agentToGet);

		if (role == null) {
			throw new Error("There is no Role with the name: " + roleToGet);
		} else if (ag == null) {
			throw new Error("There is no Agent with the name: " + agentToGet);
		}

		Ga.addRelation(role, ag);
	}

	protected void addRelationGb(String actToGet, String behToGet) {
		Act act = (Act) this.institution.getActsSet().get(actToGet);
		Behavior beh = (Behavior) this.instDomain.getBehaviorSet().get(behToGet);

		if (act == null) {
			throw new Error("There is no Act with the name: " + actToGet);
		} else if (beh == null) {
			throw new Error("There is no Behavior with the name: " + behToGet);
		}

		Gb.addRelation(act, beh);
	}

	protected void addRelationGo(String artToGet, String objToGet) {
		Art art = (Art) this.institution.getArtsSet().get(artToGet);
		Obj obj = (Obj) this.instDomain.getObjectSet().get(objToGet);

		if (art == null) {
			throw new Error("There is no Art with the name" + artToGet);
		} else if (obj == null) {
			throw new Error("There is no Object with the name: " + objToGet);
		}

		Go.addRelation(art, obj);
	}

	private boolean checkCardinality(Role role) {
		Integer roleCard = Ga.countKeyCardinality(role);
		Integer minCard = institution.getRoleMinCard(role);
		Integer maxCard = institution.getRoleMaxCard(role);

		if (roleCard < minCard || roleCard > maxCard) {
			System.out.println("Cardinality fails! \nRole: " + role.getRole().toString() + "\nRole Card: "
					+ roleCard.toString() + "\nMin Card: " + minCard.toString() + "\nMax Card: " + maxCard.toString());
			return false;
		}

		return true;
	}

	public boolean isAdmissibleGrounding() {

		List<Pair<String, Triple<Role, Act, ?>>> normsOBN = this.institution.getNormsOBN();

		for (Pair<String, Triple<Role, Act, ?>> norm : normsOBN) {

			System.out.println("Checking norm: " + norm.getFirst() + norm.getSecond().toString());

			Role role = norm.getSecond().getFirst();
			if (!checkCardinality(role))
				return false;

			Act act = norm.getSecond().getSecond();
			Object artOrRole = norm.getSecond().getThird();

			List<Agent> groundedAgents = Ga.getAllSeconds(role);
			for (Agent ag : groundedAgents) {
				if (!capable(ag, act, artOrRole)) {
					System.out.println("Not Admissible! Not capable (" + ag.getAg() + "," + act.getAct() + "," + artOrRole.toString() + ")");
					return false;
				}
			}

			System.out.println("--");
		}

		System.out.println();
		return true;
	}
	
	private boolean capable(Agent ag, Act act, Object artOrRole) {

		List<Behavior> behaviorsFromAct = Gb.getAllSeconds(act);
		for (Behavior beh : behaviorsFromAct) {
			// Is artOrRole instance of Art or Role?
			if (artOrRole instanceof Art) {
				List<Obj> objectsFromArt = Go.getAllSeconds((Art)artOrRole);
				for (Obj obj : objectsFromArt) {
					if (instDomain.affordances.contains(new Triple<Agent, Behavior, Obj>(ag, beh, obj))) {
						return true;
					}
				}
			} else if (artOrRole instanceof Role) {
				List<Agent> agentsFromRole = Ga.getAllSeconds((Role)artOrRole); 
				for (Agent ag2 : agentsFromRole) {
					if (instDomain.affordances.contains(new Triple<Agent, Behavior, Agent>(ag, beh, ag2))) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public List<Agent> getAgentsFromGa(Role rol) {
		return Ga.getAllSeconds(rol);
	}

	public List<Role> getRolesFromGa(Agent ag) {
		return Ga.getAllFirsts(ag);
	}

	public List<Behavior> getBehFromGb(Act act) {
		return Gb.getAllSeconds(act);
	}

	public List<Act> getActFromGb(Behavior beh) {
		return Gb.getAllFirsts(beh);
	}

	public List<Obj> getObjFromGo(Art art) {
		return Go.getAllSeconds(art);
	}

	public List<Art> getArtFromGo(Obj obj) {
		return Go.getAllFirsts(obj);
	}

}