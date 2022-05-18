package instAwarePlanning.institutions.Exchange;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;

public class ExchangeGroundingHumanHuman extends Grounding {

	// TODO: should be not strings but corresponding objects
	public ExchangeGroundingHumanHuman(Institution inst, TheDomain instDomain) {
		super(inst, instDomain);

		// Ga
		addRelationGa("Trader1", "human01");
		addRelationGa("Trader2", "human02");
		
		// Gb
		addRelationGb("Gives", "give");
		addRelationGb("Gets", "pick");
		
		// Go
		addRelationGo("Item1", "sponge");
		addRelationGo("Item2", "battery");

	}

}
