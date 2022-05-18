package instAwarePlanning.tests;

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

import instAwarePlanning.planning.dispatch.DispatchManager;

public class TestSensorValue {

	public static ConstraintNetworkAnimator animator;
	public static DispatchManager dispatchMngr;
	
	public static void main(String[] args) {
		
		SimplePlanner planner = new SimplePlanner(0, 60000, 0);
		MetaCSPLogging.setLevel(planner.getClass(), Level.INFO);

		// Meta-CSP Domain
		SimpleDomain.parseDomain(planner, "domains/sensortest.ddl", ProactivePlanningDomain.class);
		
		ActivityNetworkSolver ans = (ActivityNetworkSolver) planner.getConstraintSolvers()[0];
		SimplePlannerInferenceCallback cb = new SimplePlannerInferenceCallback(planner);
		animator = new ConstraintNetworkAnimator(ans, 1000, cb);

		// Sensors (state variables)
		Sensor sensor1 = new Sensor("sensor1", animator);
		sensor1.postSensorValue("soap", animator.getTimeNow());

		String[] sensors = new String[] {"sensor1", "cInstitution"};
		TimelinePublisher tp = new TimelinePublisher(planner.getConstraintSolvers()[0].getConstraintNetwork(),
				new Bounds(0, 60000), true, sensors);
		TimelineVisualizer tv = new TimelineVisualizer(tp);
		tv.startAutomaticUpdate(1000);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sensor1.postSensorValue("none", animator.getTimeNow());
		
	}

}
