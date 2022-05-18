package instAwarePlanning.verify;

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
import instAwarePlanning.institutions.Exchange.ExchangeGroundingHumanHuman2;
import instAwarePlanning.institutions.Exchange.ExchangeInstitution;
import instAwarePlanning.institutions.Trade.TradeGroundingTwoHumans;
import instAwarePlanning.institutions.Trade.TradeInstitution;
import instAwarePlanning.planning.dispatch.DispatchManager;
import instAwarePlanning.translator.VerificationDomainGenerator;

public class Verifier {

	private static final String domainDir = "domains/";
	//private static final String institutionVerifierDomain = "Exchange_verification.ddl";
	

	public static ConstraintNetworkAnimator animator;
	public static DispatchManager dispatchMngr;

	public static void main(String[] args) throws Exception {

		// Create planner
		SimplePlanner planner = new SimplePlanner(0, 10000000, 0);
		MetaCSPLogging.setLevel(planner.getClass(), Level.INFO);

		// Create Institutions
		Institution inst = new TradeInstitution();
		TheDomain theDomain = new TheDomain();
		//Grounding grounding = new ExchangeGrounding01(inst, theDomain);
		Grounding grounding = new TradeGroundingTwoHumans(inst, theDomain);

		// Crate the domain file from the institution
		VerificationDomainGenerator verificationDomain = new VerificationDomainGenerator(inst, theDomain, grounding);
		verificationDomain.createDomainFile();

		// Meta-CSP Domain
		String verificationDomainFilePath = domainDir + verificationDomain.getDomainFilename();
		SimpleDomain ddlKnowledge = SimpleDomain.parseDomain(planner, verificationDomainFilePath, ProactivePlanningDomain.class);

		ActivityNetworkSolver ans = (ActivityNetworkSolver) planner.getConstraintSolvers()[0];
		SimplePlannerInferenceCallback cb = new SimplePlannerInferenceCallback(planner);
		animator = new ConstraintNetworkAnimator(ans, 1000, cb);

		// Sensors (state variables)
		Sensor active_human01_give = new Sensor("active_human01_give", animator); 		//active(human01,give)
		Sensor active_human01_pick = new Sensor("active_human01_pick", animator); 	//active(human01,pickUp)
		Sensor active_human02_give = new Sensor("active_human02_give", animator); 		//active(human02,give)
		Sensor active_human02_pick = new Sensor("active_human02_pick", animator); 	//active(human02,pickUp)
		
		// Create a communication handler
		Comm comm = new Comm();

		// Situational Awareness Module
		SamSimple sam = new SamSimple(comm);
		sam.registerSensor("active_human01_give", active_human01_give);
		sam.registerSensor("active_human01_pick", active_human01_pick);
		sam.registerSensor("active_human02_give", active_human02_give);
		sam.registerSensor("active_human02_pick", active_human02_pick);
		
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
		//Collections.addAll(sensorsActuatorsList, domain.getContextVars());

		// Actuators, remove <actuator>_used
		Collections.addAll(sensorsActuatorsList, domain.getActuators());

		String[] sensorsActuatorsArray = sensorsActuatorsList.toArray(new String[sensorsActuatorsList.size()]);
		TimelinePublisher tp = new TimelinePublisher(planner.getConstraintSolvers()[0].getConstraintNetwork(),
				new Bounds(0, 60000), true, sensorsActuatorsArray);
		TimelineVisualizer tv = new TimelineVisualizer(tp);
		tv.startAutomaticUpdate(1000);

	}

}


