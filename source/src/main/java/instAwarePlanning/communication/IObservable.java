package instAwarePlanning.communication;

public interface IObservable {
	void register(IFeedbackObserver o);
	void unregister(IFeedbackObserver o);
	
}
