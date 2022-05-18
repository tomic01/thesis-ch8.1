package instAwarePlanning.translator.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.InstDomain;
import instAwarePlanning.framework.InstDomain.Agent;
import instAwarePlanning.framework.InstDomain.Behavior;
import instAwarePlanning.framework.InstDomain.Obj;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.framework.Institution.Act;
import instAwarePlanning.framework.Institution.Art;
import instAwarePlanning.framework.Institution.Role;
import instAwarePlanning.framework.utils.Pair;
import instAwarePlanning.framework.utils.Triple;

public class TranslatorUtils {

	Institution inst;
	InstDomain instDomain;
	Grounding grounding;

	public TranslatorUtils(Institution inst, InstDomain instDomain, Grounding grounding) {
		this.inst = inst;
		this.instDomain = instDomain;
		this.grounding = grounding;
	}

	public void addAffordancesFromStm(List<Triple<Agent, Behavior, ?>> listAffordances,
			Triple<Role, Act, ?> statement) {

		Role role = statement.getFirst();
		Act act = statement.getSecond();

		List<Agent> agents = grounding.getAgentsFromGa(role);
		List<Behavior> behaviors = grounding.getBehFromGb(act);

		// Create requirements
		for (Agent ag : agents) {
			for (Behavior beh : behaviors) {

				if (statement.getThird() instanceof Art) {
					Art art = (Art) statement.getThird();
					List<Obj> objects = grounding.getObjFromGo(art);
					for (Obj obj : objects) {
						Triple<Agent, Behavior, Obj> triple = new Triple<>(ag, beh, obj);
						if (checkAffordance(triple)) {
							listAffordances.add(triple);
						}
					}
				} else if (statement.getThird() instanceof Role) {
					Role role2 = (Role) statement.getThird();
					List<Agent> agents2 = grounding.getAgentsFromGa(role2);
					for (Agent ag2 : agents2) {
						Triple<Agent, Behavior, Agent> triple = new Triple<>(ag, beh, ag2);
						if (checkAffordance(triple)) {
							listAffordances.add(triple);
						}
					}
				} else {
					throw new Error("Third parameter in the institution statement triple must be either art or role");
				}
			}
		}

		return;
	}

	public boolean checkAffordance(Triple<Agent, Behavior, ?> triple) {
		return instDomain.getAffordances().contains(triple);
	}

	public void createDomainTriplesFromNorms(Map<Triple<Agent, Behavior, ?>, Integer> indicesTriplesMap,
			Integer req_num) {

		// Go through the institution obligation norms and crate domain triples
		// e.g.(ag1,beh2,obj1)
		List<Triple<Agent, Behavior, ?>> listStatements = new ArrayList<>();

		List<Pair<String, Triple<Role, Act, ?>>> normsOBN = inst.getNormsOBN();
		for (Pair<String, Triple<Role, Act, ?>> norm : normsOBN) {
			Triple<Role, Act, ?> stm = norm.getSecond();
			addAffordancesFromStm(listStatements, stm);
		}

		for (Triple<Agent, Behavior, ?> domTriple : listStatements) {
			indicesTriplesMap.put(domTriple, req_num);
			req_num++;
		}

		checkUsabilityNorms(indicesTriplesMap);

		return;
	}

	private void checkUsabilityNorms(Map<Triple<Agent, Behavior, ?>, Integer> indicesTriplesMap) {

		// All 'use' norms have to be already contained in the triples
		List<Pair<String, List<Triple<Role, Act, ?>>>> normsMOD = inst.getNormsMOD();
		for (Pair<String, List<Triple<Role, Act, ?>>> norm : normsMOD) {
			String normModality = norm.getFirst();

			if (normModality == "use") {

				List<Triple<Role, Act, ?>> normTriples = norm.getSecond();

				// "use" norm supports only one triple
				if (normTriples.size() != 1) {
					throw new Error("Number of statements is not supported for the 'use' norm");
				}

				// Get the statement from this norm
				Triple<Role, Act, ?> stm = normTriples.get(0);
				List<Triple<Agent, Behavior, ?>> domainTriples = new ArrayList<>();
				addAffordancesFromStm(domainTriples, stm);

				// Check if domain statements are already contained in the indicesMap
				for (Triple<Agent, Behavior, ?> domTriple : domainTriples) {
					if (!indicesTriplesMap.containsKey(domTriple)) {
						throw new Error("'Use' norm for statement (" + stm.toString()
								+ " does not exists in the planning domain");
					}
				}
			}
		}
	}

}
