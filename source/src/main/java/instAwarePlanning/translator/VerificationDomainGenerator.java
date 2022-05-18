package instAwarePlanning.translator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.metacsp.multi.allenInterval.AllenIntervalConstraint;
import org.metacsp.multi.allenInterval.AllenIntervalConstraint.Type;
import org.metacsp.time.Bounds;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.InstDomain;
import instAwarePlanning.framework.InstDomain.Agent;
import instAwarePlanning.framework.InstDomain.Behavior;
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

// The translation here so far, requires, that state-variables are the variables on which temporal constraints are enforced upon
// The better approach will be to first create abstract syntax tree (AST) and then create translate it to .ddl or anything else
// Aproach here is to fill in the ContextNormOperator
public class VerificationDomainGenerator {
	
	private static final String verifyPostfix = "_verification.ddl";

	private class ContextNormOperator {
		private Norms opType; // The type of the operator
		private String opHeadName;
		
		public Map<String, Integer> requirements = new HashMap<>();
		public Vector<OperatorConstraint> constraints = new Vector<OperatorConstraint>();

		public void setType(Norms normType) {
			opType = normType;
		}

		public void setHead(String headStr) {
			this.opHeadName = headStr;
		}
		
		public void addRequiredStateVar(String sensorStr, int reqCount) {
			requirements.put(sensorStr, reqCount);
		}

		public Norms getType() {
			return opType;
		}
		
		public String getHeadName() {
			return opHeadName;
		}

	}

	// STATE VARIABLES
	// TODO: Add state_variables (sensors) that are part part of this domain
	// If state variable is missing, throw an error that the norm semantics does not
	// have required state-variables
	// State variables should be implemented as sepparate class, with enums and
	// methods as addSubject, addPredicate...
	private String stateVar_active = "active";
	// active(human01,Gives) //sHuman01Gives::sponge
	// active(human01,Picks) //sHuman01Picks::battery
	// active(human02,Gives) //sHuman02Gives::battery
	// active(human02,Picks) //sHuman02Picks::sponge

	private List<ContextNormOperator> operators = new ArrayList<>();

	// Planner's domain
	private Institution inst;
	private InstDomain instDomain;
	private Grounding grounding;
	private TranslatorUtils tUtils;

	private List<String> sensors; 
	private List<String> contextVars; 
	private List<String> actuators; 
	
	private List<String> lines;
	private String domainVerificationFilename;

	public VerificationDomainGenerator(Institution inst, InstDomain domain, Grounding grounding)
			throws Exception {
		
		sensors = new ArrayList<>();
		contextVars = new ArrayList<>();
		actuators = new ArrayList<>();
		lines = new ArrayList<>();

		// Create the institution
		this.inst = inst;
		// Create the domain
		this.instDomain = domain;
		// Create the grounding
		this.grounding = grounding;
		// Utils for working with institution and domain
		this.tUtils = new TranslatorUtils(this.inst, this.instDomain, this.grounding);

		// Check if admissible
		checkAdmissible();

		createContextOperators();
		
		createDomainFromInstitution();

	}
	
	public void createDomainFile() throws IOException {
		domainVerificationFilename = inst.getInstName() + verifyPostfix;
		FileUtils.writeToFile(this.lines, domainVerificationFilename);
	}
	
	public String getDomainFilename() {
		return domainVerificationFilename;
	}

	private void createContextOperators() {

		int norm_count = 0; 

		// Go through all OBLIGATION norms
		List<Pair<String, Triple<Role, Act, ?>>> normsOBN = inst.getNormsOBN();
		for (Pair<String, Triple<Role, Act, ?>> norm : normsOBN) {
			
			String normName = norm.getFirst();
			Norms normType = Norms.fromString(normName);

			switch (normType) {
			case Must:
				norm_count = createMustNorm(norm_count, norm, normName);
				break;

			default:
				throw new Error("The norm type '" + normType + "' is not supported (for Obligation norms).");
			}
		}

		// Go through all MODAL norms
		List<Pair<String, List<Triple<Role, Act, ?>>>> normsMOD = inst.getNormsMOD();
		for (Pair<String, List<Triple<Role, Act, ?>>> norm : normsMOD) {
			String normName = norm.getFirst();
			Norms normType = Norms.fromString(normName);

			switch (normType) {
			case Overlaps:
				norm_count = createTemporalOperators(Norms.Overlaps, Type.Overlaps , norm_count, norm, normName);
				break;
			case Before:
				norm_count = createTemporalOperators(Norms.Before, Type.Before , norm_count, norm, normName);
				break;
			case Equals:
				norm_count = createTemporalOperators(Norms.Equals, Type.Equals, norm_count, norm, normName);
			case Use:
				break;
			default:
				throw new Error("The norm type '" + normType + "' is not supported (for Modal norms).");

			}
		}
	}

	private int createMustNorm(int norm_count, Pair<String, Triple<Role, Act, ?>> norm, String normName) {
		// Get stm triple
		Triple<Role, Act, ?> stm = norm.getSecond();

		// Get domain affordance from the norms
		List<Triple<Agent, Behavior, ?>> listAffordances = new ArrayList<>();
		tUtils.addAffordancesFromStm(listAffordances, stm);

		for (Triple<Agent, Behavior, ?> aff : listAffordances) {
			norm_count++;
			
			// Add context variable //
			String contextVar = "cNorm" + norm_count;
			contextVars.add(contextVar);
			
			// Add sensor variable //
			String stVar = stateVar_active + "_" + aff.getFirst().toString() + "_" + aff.getSecond().toString();
			if (!sensors.contains(stVar)) {
				sensors.add(stVar);
			}
			
			// Add actuator //
			String actuatorVar = "aNorm" + norm_count;
			actuators.add(actuatorVar);
			
			// Head //
			String headName = contextVar + "::" + normName + aff.toString();
			
			// Requirements // 
			String reqSensor = stVar + "::" + aff.getThird();
			String reqActuator = actuatorVar + "::" + normName + aff.toString();
			
			// Operator //
			ContextNormOperator operator = new ContextNormOperator();
			operator.setType(Norms.Must);
			operator.setHead(headName);
			operator.addRequiredStateVar(reqSensor,1);
			operator.addRequiredStateVar(reqActuator,2);
			
			// Constraint for 'MUST' semantics //
			AllenIntervalConstraint cConMust = new AllenIntervalConstraint(AllenIntervalConstraint.Type.StartedBy);
			OperatorConstraint opConMust = new OperatorConstraint(cConMust, 0, 1);
			operator.constraints.addElement(opConMust);
			
			// Constraint for duration to show the norm //
			AllenIntervalConstraint cConDuration = new AllenIntervalConstraint(AllenIntervalConstraint.Type.Duration, new Bounds(5000, 20000));
			OperatorConstraint opConDuration = new OperatorConstraint(cConDuration, 2, 2);
			operator.constraints.addElement(opConDuration);
			
			operators.add(operator);
		}
		return norm_count;
	}

	// Supports Binary Allen Interval Constraints
	private int createTemporalOperators(Norms normType, AllenIntervalConstraint.Type allenType, int norm_count, Pair<String, List<Triple<Role, Act, ?>>> norm, String normName)
			throws Error {
		// Get stm triples
		List<Triple<Role, Act, ?>> stms = norm.getSecond();

		if (stms.size() > 2) {
			throw new Error("More than two parameters for normType: " + normName + " is not supported");
		}

		// Handle Institution Statements
		Triple<Role, Act, ?> stm1 = stms.get(0);
		Triple<Role, Act, ?> stm2 = stms.get(1);

		List<Triple<Agent, Behavior, ?>> listAffordancesStm1 = new ArrayList<>();
		List<Triple<Agent, Behavior, ?>> listAffordancesStm2 = new ArrayList<>();

		tUtils.addAffordancesFromStm(listAffordancesStm1, stm1);
		tUtils.addAffordancesFromStm(listAffordancesStm2, stm2);

		for (Triple<Agent, Behavior, ?> aff_stm1 : listAffordancesStm1) {
			for (Triple<Agent, Behavior, ?> aff_stm2 : listAffordancesStm2) {
				norm_count++;
				
				// Add context variables
				String contextVar = "cNorm" + norm_count;
				contextVars.add(contextVar);
				
				// Add sensor variables
				String stVar1 = stateVar_active + "_" + aff_stm1.getFirst() + "_" + aff_stm1.getSecond();
				if (!sensors.contains(stVar1)) {
					sensors.add(stVar1);
				}
				String stVar2 = stateVar_active + "_" + aff_stm2.getFirst() + "_" + aff_stm2.getSecond();
				if (!sensors.contains(stVar2)) {
					sensors.add(stVar2);
				}
				
				// Add actuator //
				String actuatorVar = "aNorm" + norm_count;
				actuators.add(actuatorVar);
				
				// Head //
				String headName = contextVar + "::" + normName;
				
				// Requirements // 
				String reqSensor1 = stVar1 + "::" + aff_stm1.getThird();
				String reqSensor2 = stVar2 + "::" + aff_stm2.getThird();
				String reqActuator = actuatorVar + "::" + normName;
				
				// Operator //
				ContextNormOperator operator = new ContextNormOperator();
				operator.setType(normType);
				operator.setHead(headName);
				operator.addRequiredStateVar(reqSensor1,1);
				operator.addRequiredStateVar(reqSensor2,2);
				operator.addRequiredStateVar(reqActuator,3);
				
				// Constraint for 'OVERLAPS' semantics //
				AllenIntervalConstraint cCon = new AllenIntervalConstraint(allenType);
				OperatorConstraint opCon = new OperatorConstraint(cCon, 1, 2);
				operator.constraints.addElement(opCon);
				
				// Constraint for duration to show the norm //
				AllenIntervalConstraint cConDuration = new AllenIntervalConstraint(AllenIntervalConstraint.Type.Duration,new Bounds(5000, 20000));
				OperatorConstraint opConDuration = new OperatorConstraint(cConDuration, 3, 3);
				operator.constraints.addElement(opConDuration);
				
				operators.add(operator);
			}
		}
		return norm_count;
	}

	private void createDomainFromInstitution() throws IOException {

		String instName = inst.getInstName();
		
		String autoGenerationMsg = "### Auto-generated VERIFICATION domain ###\n";
		lines.add(autoGenerationMsg);
		
		// Domain title line
		String planningDomainName = "(Domain " + instName + "_Adherence)";
		lines.add(planningDomainName);
		lines.add("\n");

		// Add sensors
		lines.add("### Sensors (state-variables) ###");
		for (String sensor : sensors) {
			String ddlSensor = "(Sensor " + sensor + ")";
			lines.add(ddlSensor);
		}
		lines.add("\n");

		// Add context variables
		lines.add("### Context variables (norms) ###");
		for (String contextVar : contextVars) {
			String ddlContextVar = "(ContextVariable " + contextVar + ")";
			lines.add(ddlContextVar);
		}
		lines.add("\n");
		
		// Add actuators
		lines.add("### Actuators (shows norms in the timeline) ###");
		for (String actuator : actuators) {
			String ddlActuator = "(Actuator " + actuator + ")";
			lines.add(ddlActuator);
		}
		lines.add("\n");
		
		lines.add("### Norms context operators ###\n");
		for(ContextNormOperator op : operators) {
			createSimpleOperator(op, lines);
			lines.add("\n");
		}
		
	}

	private void createSimpleOperator(ContextNormOperator op, List<String> lines) throws Error {
		// Start of simple operator
		lines.add("(SimpleOperator");

		String ddlHead = "(Head " + op.getHeadName() + ")";
		lines.add(ddlHead);

		// Requirements
		op.requirements = MapUtil.sortByValue(op.requirements);
		for (Map.Entry<String, Integer> req : op.requirements.entrySet()) {
			String fullReqLine = "(RequiredState " + "req" + req.getValue().toString() + " " + req.getKey() + ")";
			lines.add(fullReqLine);
		}

		// Constraints
		for (OperatorConstraint cons : op.constraints) {
			String fullConstraintLine;
			
			// Bounds String
			Bounds[] bounds = cons.con.getBounds();
			String boundsStr = "[";
			for (int i=0;i<bounds.length;i++) {
				long min = bounds[i].min;
				long max = bounds[i].max;
				if (i==0) {
					boundsStr += min + "," + max;	
				}
				else {
					boundsStr += "," + min + "," + max;
				}
				
			}
			boundsStr += "]";
			
			fullConstraintLine = "(Constraint " + cons.con.getTypes()[0];
			if (cons.from == 0) {
				fullConstraintLine += "(" + "Head," + "req" + Integer.toString(cons.to) + "))";
			} else if (cons.from == cons.to) {
				if (cons.con.getTypes()[0] == Type.Duration) {
					fullConstraintLine += boundsStr + "(" + "req" + Integer.toString(cons.from) + "))";
				} else {
					fullConstraintLine += "(" + "req" + Integer.toString(cons.from) + "))";
				}
				
			}
			else {
				fullConstraintLine += "(" + "req" + Integer.toString(cons.from) + "," + "req" + Integer.toString(cons.to) + "))";
			}
			
			lines.add(fullConstraintLine);
		}

		// End of simple operator
		lines.add(")");
	}
	
	//////////////////////////////////////

	private void checkAdmissible() throws Error {
		// Check if the grounding is admissible
		if (grounding.isAdmissibleGrounding()) {
			System.out.println("\nThe grounding is Admissible!!!");
		} else {
			System.out.println("\nThe grounding is NOT Admissible!!! Stopping...");
			throw new Error("The institution is not admissible");
		}
	}

	

}
