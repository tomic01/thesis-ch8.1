package instAwarePlanning.institutions.Trade;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;

public class TradeGroundingTwoHumans extends Grounding {

	// TODO: should be not strings but corresponding objects
	public TradeGroundingTwoHumans(Institution inst, TheDomain instDomain) {
		super(inst, instDomain);

		// Ga
		addRelationGa("Buyer", "human01");
		addRelationGa("Seller", "human02");
		
		// Gb
		addRelationGb("Pay", "give");
		addRelationGb("ReceiveGoods", "pick");
		addRelationGb("ReceivePayment", "pick");
		addRelationGb("GiveGoods", "give");
		
		// Go
		addRelationGo("PayForm", "yellowsponge");
		addRelationGo("Goods", "bluesponge");

	}

}
