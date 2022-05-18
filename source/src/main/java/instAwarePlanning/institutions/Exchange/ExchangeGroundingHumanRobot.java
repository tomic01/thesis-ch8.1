package instAwarePlanning.institutions.Exchange;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;

public class ExchangeGroundingHumanRobot extends Grounding {

	// TODO: should be not strings but corresponding objects
	public ExchangeGroundingHumanRobot(Institution inst, TheDomain instDomain) {
		super(inst, instDomain);

		// Ga
		addRelationGa("Trader1", "pepper01");
		addRelationGa("Trader2", "human01");
		
		// Gb
		addRelationGb("Gives", "give");
		addRelationGb("Gets", "pick");
		
		// Go
		addRelationGo("Item1", "sponge");
		addRelationGo("Item2", "battery");

	}

}
