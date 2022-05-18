package instAwarePlanning.institutions.Trade;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;

public class TradeGroundingGateKeeper extends Grounding {

	// TODO: should be not strings but corresponding objects
	public TradeGroundingGateKeeper(Institution inst, TheDomain instDomain) {
		super(inst, instDomain);

		// Ga
		addRelationGa("Buyer", "human01");
		addRelationGa("Seller", "mbot11");
		
		// Gb
		addRelationGb("Pay", "speak");
		addRelationGb("ReceiveGoods", "speechRecog");
		addRelationGb("ReceivePayment", "speechRecog");
		addRelationGb("GiveGoods", "speak");
		
		// Go
		addRelationGo("PayForm", "keyword1");
		addRelationGo("Goods", "pin");

	}

}
