package instAwarePlanning.institutions.Trade;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;

public class TradeGroundingTwoRobots extends Grounding {

	// TODO: do not use strings as it is now
	public TradeGroundingTwoRobots(Institution inst, TheDomain instDomain) {
		super(inst, instDomain);
		
		// Ga
		addRelationGa("Buyer", "pepper01");
		addRelationGa("Seller", "pepper02");

		// Gb
		addRelationGb("Pay", "give");
		addRelationGb("ReceiveGoods", "pick");
		addRelationGb("ReceivePayment", "pick");
		addRelationGb("GiveGoods", "give");

		// Go
		addRelationGo("PayForm", "sponge");
		addRelationGo("Goods", "battery");

	}
}
