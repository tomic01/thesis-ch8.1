package instAwarePlanning.tests;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;
import instAwarePlanning.institutions.Trade.TradeGroundingGateKeeper;
import instAwarePlanning.institutions.Trade.TradeGroundingTwoRobots;
import instAwarePlanning.institutions.Trade.TradeInstitution;

public class TestAdmissible {

	public static void main(String[] args) {
		// Create Institutions
				// Institution inst = new ExchangeInstitution();
				Institution inst = new TradeInstitution();
				TheDomain theDomain = new TheDomain();
				//Grounding grounding = new TradeGroundingTwoRobots(inst, theDomain);
				Grounding grounding = new TradeGroundingGateKeeper(inst, theDomain);
				
				grounding.isAdmissibleGrounding();

	}

}
