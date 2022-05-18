package instAwarePlanning.translator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.metacsp.meta.simplePlanner.SimpleDomain;
import org.metacsp.multi.allenInterval.AllenIntervalConstraint;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.InstDomain;
import instAwarePlanning.framework.InstDomain.Agent;
import instAwarePlanning.framework.InstDomain.Behavior;
import instAwarePlanning.framework.InstDomain.Obj;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.framework.Institution.Act;
import instAwarePlanning.framework.Institution.Role;
import instAwarePlanning.framework.utils.MapUtil;
import instAwarePlanning.framework.utils.Pair;
import instAwarePlanning.framework.utils.Triple;
import instAwarePlanning.translator.utils.FileUtils;
import instAwarePlanning.translator.utils.OperatorConstraint;
import instAwarePlanning.translator.utils.SupportedNorms.Norms;
import instAwarePlanning.translator.utils.TranslatorUtils;

public class PlanningDomaniGenerator {

	private static final String strInstitutionContextVar = "cInstitution";
	private static final String strInstitutionSensorVar = "sInstitution";
	private static final String planningInstCombinedPostfix = "_planning.ddl";
	private static final String planningInstOnlyPostfix = "_inst.ddl";

	// Planner's domain
	private SimpleDomain dom;
	private Institution inst;
	private InstDomain instDomain;
	private Grounding grounding;
	private TranslatorUtils tUtils;

	// Maps the domain triples (from norms) to their indices
	// (Problem: this assumes only one operator for the institution translation)
	// (Solution: use helping operator class to store this per one operator)
	private Map<Triple<Agent, Behavior, ?>, Integer> indicesTriplesMap = new HashMap<>();
	private Integer req_num = 1; // 0 is reserved for the HEAD (in the planner's domain language)

	// Keep information about the constraints
	Vector<OperatorConstraint> constrVector = new Vector<OperatorConstraint>();

	private List<String> sensors;
	private List<String> actuators;

	private List<String> instLines;
	private String InstitutionOnlyDomainName;
	private String InstitutionCombinedDomainName;

	public PlanningDomaniGenerator(Institution inst, InstDomain domain, Grounding grounding) {
		
		this.instLines = new ArrayList<>();

		// Create the institution
		this.inst = inst;
		// Create the domain
		this.instDomain = domain;
		// Create the grounding
		this.grounding = grounding;
		// Create domain (.ddl) text lines
		this.instLines = new ArrayList<>();
		// Utils for working with institution and domain
		this.tUtils = new TranslatorUtils(this.inst, this.instDomain, this.grounding);

		// CHECK ADMISSIBLITY
		checkAdmissible();

		// CREATE VARIABLES (Sensors, Context, Actuators)
		this.sensors = createSensorsVar();
		this.actuators = createActuators();

		// CREATE VARIABLES AND CONSTRAINTS FROM I,D,G
		tUtils.createDomainTriplesFromNorms(indicesTriplesMap, req_num);
		createConstraintInfoFromNorms();

	}

	public String getInstitutionOnlyDomainName() {
		return InstitutionOnlyDomainName;
	}
	
	public String getInstitutionCombinedDomainName() {
		return InstitutionCombinedDomainName;
	}
	

	public SimpleDomain getDomain() {
		return dom;
	}

	// A method for creating planning domain (.ddl) file from the institution,
	// institution domain and grounding
	public void createDomainFromInstitutionAlone() throws IOException {

		instLines.clear();
		String instName = inst.getInstName();
		
		String autoGenerationMsg = "### Auto-generated planning domain from the Institution, Institution Domain and Grounding ###\n";
		instLines.add(autoGenerationMsg);

		// Create lines
		String planningDomainName = "(Domain " + instName + ")";
		instLines.add(planningDomainName);

		// Add sensors
		for (String sensor : sensors) {
			String ddlSensor = "(Sensor " + sensor + ")";
			instLines.add(ddlSensor);
		}

		// Add context
		String ddlContext = "(ContextVariable " + strInstitutionContextVar + ")";
		instLines.add(ddlContext);

		// Add actuators
		for (String actuator : actuators) {
			String ddlActuator = "(Actuator " + actuator + ")";
			instLines.add(ddlActuator);
		}

		createSimpleOperator(instName);
		
		InstitutionOnlyDomainName = inst.getInstName() + planningInstOnlyPostfix;
		FileUtils.writeToFile(instLines, InstitutionOnlyDomainName);

	}

	public void createCombinedDomainFromExisting(String originalFileName) throws IOException {

		instLines.clear(); // reset institution lines 
		List<String> origLines = FileUtils.readExistingDomainAsLines(originalFileName);
		List<String> combinedLines = new ArrayList<String>();
		
		createInstitutionLines();

		for (String origLine : origLines) {

			String currentLine = origLine.trim();
			if (currentLine.startsWith("#"))
				continue;

			combinedLines.add(currentLine);

			if (currentLine.startsWith("(Domain")) {
				for (String instLine : instLines) {
					combinedLines.add(instLine);
				}
			}
		}

		InstitutionCombinedDomainName = inst.getInstName() + planningInstCombinedPostfix;
		FileUtils.writeToFile(combinedLines, InstitutionCombinedDomainName);

	}
	

	private List<String> createSensorsVar() {
		return Arrays.asList(strInstitutionSensorVar);
	}

	private List<String> createActuators() {
		// Actuators
		List<String> addedAgents = new ArrayList<String>();
		List<Pair<Role, Agent>> relationsGa = grounding.Ga.getRelation();

		// Go through grounding and add agents (as actuators in planning domain)
		for (Pair<Role, Agent> gaPair : relationsGa) {
			Agent ag = gaPair.getSecond();
			if (!addedAgents.contains(ag.getAg())) {
				addedAgents.add(ag.getAg());
			}
		}

		return addedAgents;
	}

	private void addRequirementStrings(List<String> requirments) throws Error {

		// Sort requirements so that values are 1,2,3,4...
		indicesTriplesMap = MapUtil.sortByValue(indicesTriplesMap);

		List<Triple<Agent, Behavior, ?>> listTriples = new ArrayList<>(indicesTriplesMap.keySet());
		for (Triple<Agent, Behavior, ?> triple : listTriples) {
			String agStr = triple.getFirst().getAg();
			String behStr = triple.getSecond().getBeh();
			String objRolStr;

			if (triple.getThird() instanceof Obj) {
				Obj obj = (Obj) triple.getThird();
				objRolStr = obj.getObj();
			} else if (triple.getThird() instanceof Agent) {
				Agent ag = (Agent) triple.getThird();
				objRolStr = ag.getAg();
			} else
				throw new Error("Type error. Thirt element in a truple must be eather Object or Role");

			String req = agStr + "::" + behStr + "(" + objRolStr + ")";
			requirments.add(req);
		}

		return;
	}

	private void checkAdmissible() throws Error {
		// Check if the grounding is admissible
		if (grounding.isAdmissibleGrounding()) {
			System.out.println("\nThe grounding is Admissible!!!");
		} else {
			System.out.println("\nThe grounding is NOT Admissible!!! Stopping...");
			throw new Error("The institution is not admissible");
		}
	}

	// private void createDomainTriplesFromNorms() {
	//
	// // Go through the institution obligation norms and crate domain triples
	// // e.g.(ag1,beh2,obj1)
	// List<Triple<Agent, Behavior, ?>> listStatements = new ArrayList<>();
	//
	// List<Pair<String, Triple<Role, Act, ?>>> normsOBN = inst.getNormsOBN();
	// for (Pair<String, Triple<Role, Act, ?>> norm : normsOBN) {
	// Triple<Role, Act, ?> stm = norm.getSecond();
	// traUtils.addDomainTriplesFromStm(listStatements, stm);
	// }
	//
	// for (Triple<Agent, Behavior, ?> domTriple : listStatements) {
	// indicesTriplesMap.put(domTriple, req_num);
	// req_num++;
	// }
	//
	// checkUsabilityNorms();
	//
	// return;
	// }
	//
	// private void checkUsabilityNorms() {
	//
	// // All 'use' norms have to be already contained in the triples
	// List<Pair<String, List<Triple<Role, Act, ?>>>> normsMOD = inst.getNormsMOD();
	// for (Pair<String, List<Triple<Role, Act, ?>>> norm : normsMOD) {
	// String normModality = norm.getFirst();
	//
	// if (normModality == "use") {
	//
	// List<Triple<Role, Act, ?>> normTriples = norm.getSecond();
	//
	// // "use" norm supports only one triple
	// if (normTriples.size() != 1) {
	// throw new Error("Number of statements is not supported for the 'use' norm");
	// }
	//
	// // Get the statement from this norm
	// Triple<Role, Act, ?> stm = normTriples.get(0);
	// List<Triple<Agent, Behavior, ?>> domainTriples = new ArrayList<>();
	// traUtils.addDomainTriplesFromStm(domainTriples, stm);
	//
	// // Check if domain statements are already contained in the indicesMap
	// for (Triple<Agent, Behavior, ?> domTriple : domainTriples) {
	// if (!indicesTriplesMap.containsKey(domTriple)) {
	// throw new Error("'Use' norm for statement (" + stm.toString()
	// + " does not exists in the planning domain");
	// }
	// }
	// }
	// }
	// }

	// For now this supports norms that have two statements.
	// Obligation norms are added as requirements, so they should not be here
	// Usability norms check if obligation norms are added so that the corresponding
	// artifact is used
	// Temporal norms are checked here (supports only two parameters)
	// support Duration with one param
	private void createConstraintInfoFromNorms() {

		// Go through MOD norms (ones that can be implemented as constraints in the
		// planning domain)
		List<Pair<String, List<Triple<Role, Act, ?>>>> normsMOD = inst.getNormsMOD();
		for (Pair<String, List<Triple<Role, Act, ?>>> norm : normsMOD) {

			// Norm name (modality)
			String normModality = norm.getFirst();

			// Norm 'stm' triples
			List<Triple<Role, Act, ?>> normTriples = norm.getSecond();
			if (normTriples.size() != 2) {
				continue;
			}

			Triple<Role, Act, ?> stm1 = normTriples.get(0);
			Triple<Role, Act, ?> stm2 = normTriples.get(1);

			// Get domain affordances from norms
			List<Triple<Agent, Behavior, ?>> listDomTriples1 = new ArrayList<>();
			List<Triple<Agent, Behavior, ?>> listDomTriples2 = new ArrayList<>();

			tUtils.addAffordancesFromStm(listDomTriples1, stm1);
			tUtils.addAffordancesFromStm(listDomTriples2, stm2);

			for (Triple<Agent, Behavior, ?> domTriple1 : listDomTriples1) {
				if (indicesTriplesMap.containsKey(domTriple1)) {
					int tripleIndexFirst = indicesTriplesMap.get(domTriple1);
					// We have a first index, let's find the next one...
					for (Triple<Agent, Behavior, ?> domTriple2 : listDomTriples2) {
						if (indicesTriplesMap.containsKey(domTriple2)) {
							int tripleIndexSecond = indicesTriplesMap.get(domTriple2);

							// Create the constraint:
							Norms normType = Norms.fromString(normModality);
							AllenIntervalConstraint cContains;
							switch (normType) {
							case Starts:
								cContains = new AllenIntervalConstraint(AllenIntervalConstraint.Type.Starts);
								break;
							case Before:
								cContains = new AllenIntervalConstraint(AllenIntervalConstraint.Type.Before);
								break;
							case Equals:
								cContains = new AllenIntervalConstraint(AllenIntervalConstraint.Type.Equals);
								break;
							case Overlaps:
								cContains = new AllenIntervalConstraint(AllenIntervalConstraint.Type.Overlaps);
								break;
							default:
								throw new Error("The type of the constraint is not implemented...");
							}

							OperatorConstraint con = new OperatorConstraint(cContains, tripleIndexFirst,
									tripleIndexSecond);
							constrVector.addElement(con);

						} else
							throw new Error("You are trying to refer to a domain triple that does not exists...");
					}
				} else
					throw new Error("You are trying to refer to a domain triple that does not exists...");
			}

		}
	}

	private void createInstitutionLines() {
		
		String instName = inst.getInstName();
		this.instLines = new ArrayList<>();

		String autoGenerationMsg1 = "###### BEGIN: 	institution knowledge ######";
		String autoGenerationMsg2 = "###### END: 	institution knowledge ######";

		instLines.add("\n");
		instLines.add(autoGenerationMsg1);

		// Add sensors
		for (String sensor : sensors) {
			String ddlSensor = "(Sensor " + sensor + ")";
			instLines.add(ddlSensor);
		}

		// Add context
		String ddlContext = "(ContextVariable " + strInstitutionContextVar + ")";
		instLines.add(ddlContext);

		// Operators
		createSimpleOperator(instName);

		instLines.add(autoGenerationMsg2);
		instLines.add("\n");

	}

	private void createSimpleOperator(String instName) throws Error {
		// Start of simple operator
		instLines.add("(SimpleOperator");

		String ddlHead = "(Head " + strInstitutionContextVar + "::" + instName + ")";
		instLines.add(ddlHead);

		// Requirements
		List<String> requirments = new ArrayList<>();
		addRequirementStrings(requirments);

		// Add command requirement also
		requirments.add(strInstitutionSensorVar + "::" + instName.toLowerCase() + "_start");

		String strReq = "req";
		Integer count = 1;
		for (String req : requirments) {
			String ddlReq = "(RequiredState " + strReq + count.toString() + " " + req + ")";
			count++;
			instLines.add(ddlReq);
		}

		// Constraints
		for (OperatorConstraint ac : constrVector) {
			String ddlConstr = "(Constraint " + ac.con.getTypes()[0] + "(" + strReq + Integer.toString(ac.from) + ","
					+ strReq + Integer.toString(ac.to) + "))";
			instLines.add(ddlConstr);
		}

		// End of simple operator
		instLines.add(")");
	}

	
	// private String readExistingDomainAsString(String existingDomain) {
	//
	// String everything = "";
	// String filename = "domains/" + existingDomain;
	// try {
	// BufferedReader br = new BufferedReader(new FileReader(filename));
	// try {
	// StringBuilder sb = new StringBuilder();
	// String line = br.readLine();
	// while (line != null) {
	// if (!line.trim().startsWith("#")) {
	// sb.append(line);
	// sb.append('\n');
	// }
	// line = br.readLine();
	// }
	// everything = sb.toString();
	// } finally {
	// br.close();
	// }
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// return everything;
	// }

}
