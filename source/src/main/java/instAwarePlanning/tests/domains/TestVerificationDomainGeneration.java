package instAwarePlanning.tests.domains;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;
import instAwarePlanning.institutions.Exchange.ExchangeGroundingHumanHuman;
import instAwarePlanning.institutions.Exchange.ExchangeInstitution;
import instAwarePlanning.institutions.Trade.TradeGroundingTwoRobots;
import instAwarePlanning.institutions.Trade.TradeInstitution;
import instAwarePlanning.translator.VerificationDomainGenerator;

public class TestVerificationDomainGeneration {

	public static void main(String[] args) throws Exception {

		// Create Institutions
		// Institution inst = new ExchangeInstitution();
		Institution inst = new TradeInstitution();
		TheDomain theDomain = new TheDomain();
		// Grounding grounding = new ExchangeGrounding01(inst, theDomain);
		Grounding grounding = new TradeGroundingTwoRobots(inst, theDomain);

		VerificationDomainGenerator verificationDomain = new VerificationDomainGenerator(inst, theDomain,
				grounding);
		verificationDomain.createDomainFile();
		
		return;

	}

}
