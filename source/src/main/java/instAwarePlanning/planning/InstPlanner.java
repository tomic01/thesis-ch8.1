package instAwarePlanning.planning;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;

import org.metacsp.meta.simplePlanner.ProactivePlanningDomain;
import org.metacsp.meta.simplePlanner.SimpleDomain;
import org.metacsp.meta.simplePlanner.SimplePlanner;
import org.metacsp.meta.simplePlanner.SimplePlannerInferenceCallback;
import org.metacsp.multi.activity.ActivityNetworkSolver;
import org.metacsp.sensing.ConstraintNetworkAnimator;
import org.metacsp.sensing.Sensor;
import org.metacsp.time.Bounds;
import org.metacsp.utility.logging.MetaCSPLogging;
import org.metacsp.utility.timelinePlotting.TimelinePublisher;
import org.metacsp.utility.timelinePlotting.TimelineVisualizer;

import instAwarePlanning.SAM.SamSimple;
import instAwarePlanning.communication.Comm;
import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;
import instAwarePlanning.institutions.Exchange.ExchangeGroundingTwoRobots;
import instAwarePlanning.institutions.Exchange.ExchangeGroundingHumanRobot;
import instAwarePlanning.institutions.Exchange.ExchangeInstitution;
import instAwarePlanning.institutions.Trade.TradeGroundingRobotHuman;
import instAwarePlanning.institutions.Trade.TradeGroundingTwoRobots;
import instAwarePlanning.institutions.Trade.TradeInstitution;
import instAwarePlanning.planning.dispatch.DispatchManager;
import instAwarePlanning.translator.PlanningDomaniGenerator;

public class InstPlanner {

	private static final String domainDir = "domains/";
	// private static final String robotsDomainOriginal = "robotsKnowledge.ddl";
	//private static final String robotsDomainOriginal = "humanRobotKnowledge.ddl";
	private static final String robotsDomainOriginal = "humanRobotKnowledge3.ddl";

	public static ConstraintNetworkAnimator animator;
	public static DispatchManager dispatchMngr;

	public static void main(String[] args) throws IOException {

		// Testing Mode
		Boolean testingMode = false;

		// Create planner
		SimplePlanner planner = new SimplePlanner(0, 10000000, 0);
		MetaCSPLogging.setLevel(planner.getClass(), Level.INFO);

		// Create Institutions
		Institution inst = new TradeInstitution();
		TheDomain theDomain = new TheDomain();
		Grounding grounding = new TradeGroundingRobotHuman(inst, theDomain);
		//Grounding grounding = new TradeGroundingTwoRobots(inst, theDomain);

		// Translate Inst. Spec. into a domain file
		PlanningDomaniGenerator ddlInstKnowledge = new PlanningDomaniGenerator(inst, theDomain, grounding);
		ddlInstKnowledge.createDomainFromInstitutionAlone();
		ddlInstKnowledge.createCombinedDomainFromExisting(robotsDomainOriginal);

		// Planner
		String domainName = domainDir + ddlInstKnowledge.getInstitutionCombinedDomainName();
		SimpleDomain ddlKnowledge = SimpleDomain.parseDomain(planner, domainName, ProactivePlanningDomain.class);

		ActivityNetworkSolver ans = (ActivityNetworkSolver) planner.getConstraintSolvers()[0];
		SimplePlannerInferenceCallback cb = new SimplePlannerInferenceCallback(planner);
		animator = new ConstraintNetworkAnimator(ans, 1000, cb);

		// Sensors
		Sensor s_institution = new Sensor("sInstitution", animator);
		// Sensor s_humanGives = new Sensor("sHumanGives", animator);
		// Sensor s_humanPicks = new Sensor("sHumanPicks", animator);
		// Sensor s_p1Has = new Sensor("sPepper01Has", animator);
		// Sensor s_p2Has = new Sensor("sPepper02Has", animator);

		s_institution.registerSensorTrace("testing/institution.st");
		// s_humanGives.registerSensorTrace("testing/humanGives.st");
		// s_humanPicks.registerSensorTrace("testing/humanPicks.st");
		// s_p1Has.registerSensorTrace("testing/pepper01Has.st");
		// s_p2Has.registerSensorTrace("testing/pepper02Has.st");

		// Create a communication handler
		Comm comm = new Comm();

		// Situational Awareness Module
		SamSimple sam = new SamSimple(comm);

		// Dispatching
		//dispatchMngr = new DispatchManager(ddlKnowledge.getActuators(), comm, testingMode);
		//dispatchMngr.dispatcherSetup();

		// Display
		displayTimelineAnimator(planner, ddlKnowledge);

	}

	public static void displayTimelineAnimator(SimplePlanner planner, SimpleDomain domain) {

		LinkedList<String> sensorsActuatorsList = new LinkedList<String>();

		// Time
		sensorsActuatorsList.add("Time");

		// Sensors
		Collections.addAll(sensorsActuatorsList, domain.getSensors());

		// Context Variables
		Collections.addAll(sensorsActuatorsList, domain.getContextVars());

		// Actuators, remove <actuator>_used
		Collections.addAll(sensorsActuatorsList, domain.getActuators());

		String[] sensorsActuatorsArray = sensorsActuatorsList.toArray(new String[sensorsActuatorsList.size()]);
		TimelinePublisher tp = new TimelinePublisher(planner.getConstraintSolvers()[0].getConstraintNetwork(),
				new Bounds(0, 4 * 60000), true, sensorsActuatorsArray);
		TimelineVisualizer tv = new TimelineVisualizer(tp);
		tv.startAutomaticUpdate(1000);

	}

}
