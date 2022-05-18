package instAwarePlanning.tests.domains;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;
import instAwarePlanning.institutions.Trade.TradeGroundingGateKeeper;
import instAwarePlanning.institutions.Trade.TradeGroundingTwoRobots;
import instAwarePlanning.institutions.Trade.TradeInstitution;
import instAwarePlanning.translator.PlanningDomaniGenerator;

public class TestPlanningDomainGeneration {
	
	private static String robotDomainName = "humanRobotsKnowledge.ddl";

	public static void main(String[] args) throws Exception {

		// Create Institutions
		// Institution inst = new ExchangeInstitution();
		Institution inst = new TradeInstitution();
		TheDomain theDomain = new TheDomain();
		// Grounding grounding = new ExchangeGrounding01(inst, theDomain);
		//Grounding grounding = new TradeGroundingTwoRobots(inst, theDomain);
		Grounding grounding = new TradeGroundingGateKeeper(inst, theDomain);
		
		grounding.isAdmissibleGrounding();

		PlanningDomaniGenerator planningDomain = new PlanningDomaniGenerator(inst, theDomain, grounding);
		planningDomain.createDomainFromInstitutionAlone();
		planningDomain.createCombinedDomainFromExisting(robotDomainName);
		
		return;
	}

}
