package instAwarePlanning.institutions.Guide;

import instAwarePlanning.framework.Grounding;
import instAwarePlanning.framework.Institution;
import instAwarePlanning.institutions.TheDomain;

public class GuideGrounding01 extends Grounding {

	public GuideGrounding01(Institution instGuide, TheDomain instDomain) {
		super(instGuide, instDomain);

		// Ga
		addRelationGa("Guide", "mbot11");
		addRelationGa("Helper", "mbot03");
		
		// Gb
		addRelationGb("DoGuidance", "moveOnTrajectory");
		addRelationGb("DoGuidanceHelp", "moveInFormation");
		
		// Go
		addRelationGo("Place", "map");

	}

}
