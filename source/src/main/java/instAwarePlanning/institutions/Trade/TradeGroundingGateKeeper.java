package instAwarePlanning.institutions.Trade;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;

public class TradeGroundingGateKeeper extends Grounding {

	public TradeGroundingGateKeeper(Institution inst, TheDomain instDomain) {
		super(inst, instDomain);
		
		// Ga
		addRelationGa("Buyer", "human01");
		addRelationGa("Seller", "mbot11");
		
		// Gb
		addRelationGb("Pay", "speak"); // Buyer is giving/saying the keyword
		addRelationGb("ReceiveGoods", "speech_recog"); // Buyer is getting the PIN (recognizing what is said)
		addRelationGb("ReceivePayment", "speech_recog"); // Seller is recognizing the Buyers keyword
		addRelationGb("GiveGoods", "speak"); // Sellers is giving the PIN
		
		// Go
		addRelationGo("PayForm", "keyword1");
		addRelationGo("Goods", "pin");
	}

}
