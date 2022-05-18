package instAwarePlanning.planning.dispatch;

import java.util.Map;

import instAwarePlanning.communication.Comm;
import instAwarePlanning.communication.IFeedbackObserver;
import instAwarePlanning.communication.utils.FeedReader;

public class BehaviorMonitor implements IFeedbackObserver {
	
	public BehaviorMonitor(Comm comm) {
		comm.registerToFeedback(this);
	}
	
	@Override
	public void Update(String data) {

		System.out.println("[BehaviorMonitor] Data recieved: " + data);
		String[] feed = data.split("#");
		if (feed[0].equals("subscribed") && feed[1].equals("D6.3")) {
			
			Map<String, String> params = FeedReader.getFeedParams(feed[2]);
			
			if (params.get("status").equals("finished")){
				String id = params.get("id");
				if (DispatchAbstract.ID_Activity_Map.containsKey(id)) {
					System.out.println("[BehaviorMonitor] Stopping activity id: " + id);
				} else {
					System.out.println("[BehaviorMonitor] ID is not recognized!" + id);
				}
				
				DispatchUtils.stopExecutingActivity(id);
			}
			
		}
	}


}
