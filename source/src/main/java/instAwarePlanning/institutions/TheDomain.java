package instAwarePlanning.institutions;

import instAwarePlanning.framework.InstDomain;
import instAwarePlanning.framework.InstDomain.Agent;
import instAwarePlanning.framework.InstDomain.Behavior;
import instAwarePlanning.framework.InstDomain.Obj;
import instAwarePlanning.framework.utils.Triple;

public class TheDomain extends InstDomain {

	public TheDomain() {
		super();

		// AGENTS
		Agent aMbot11 = new Agent("mbot11");
		Agent aMbot03 = new Agent("mbot03");
		Agent aPepper01 = new Agent("pepper01");
		Agent aPepper02 = new Agent("pepper02"); // new
		Agent aHuman01 = new Agent("human01");
		Agent aHuman02 = new Agent("human02");
		Agent aHuman03 = new Agent("human03");
		Agent aHuman04 = new Agent("humanPer");

		this.addAgent(aMbot11);
		this.addAgent(aMbot03);
		this.addAgent(aPepper01);
		this.addAgent(aPepper02);
		this.addAgent(aHuman01);
		this.addAgent(aHuman02);
		this.addAgent(aHuman03);
		this.addAgent(aHuman04);

		// BEHAVIORS
		Behavior bMoveOnTrajectory = new Behavior("moveOnTrajectory");
		Behavior bDescribe = new Behavior("describe");
		Behavior bWayPointNavigation = new Behavior("wayPointNavigation");
		Behavior bMoveInFormation = new Behavior("moveInFormation");
		Behavior bGive = new Behavior("give");
		Behavior bPickUp = new Behavior("pick");
		Behavior bSpeak = new Behavior("speak");
		Behavior bSpeechRecog = new Behavior("speech_recog");

		this.addBehavior(bMoveOnTrajectory);
		this.addBehavior(bDescribe);
		this.addBehavior(bWayPointNavigation);
		this.addBehavior(bMoveInFormation);
		this.addBehavior(bGive);
		this.addBehavior(bPickUp);
		this.addBehavior(bSpeak);
		this.addBehavior(bSpeechRecog);

		// OBJECTS (parameters)
		Obj oPositionsLab = new Obj("labStart,vr,tracker,labExit"); // array of waypoints
		Obj oPositionsLab2 = new Obj("labStart,girafe,tracker,labExit"); // array of waypoints
		Obj oLab = new Obj("lab");
		Obj oPeisHome = new Obj("peisHome");
		Obj oMap = new Obj("map");
		Obj oSponge = new Obj("sponge");
		Obj oBattery = new Obj("battery");
		Obj oSpongeY = new Obj("yellowsponge");
		Obj oSpongeB = new Obj("bluesponge");
		Obj oSpongeR = new Obj("redsponge");
		Obj oKeyword1 = new Obj("keyword1");
		Obj oKeyword2 = new Obj("keyword2");
		Obj oPin = new Obj("pin");

		this.addObject(oPositionsLab);
		this.addObject(oPositionsLab2);
		this.addObject(oLab);
		this.addObject(oPeisHome);
		this.addObject(oMap);
		this.addObject(oSponge);
		this.addObject(oBattery);
		this.addObject(oSpongeY);
		this.addObject(oSpongeB);
		this.addObject(oSpongeR);
		this.addObject(oKeyword1);
		this.addObject(oKeyword2);
		this.addObject(oPin);

		// AFFORDANCES //
		
		// Pepper robots
		this.affordances.add(new Triple<>(aPepper01, bGive, oSponge));
		this.affordances.add(new Triple<>(aPepper02, bGive, oSponge));
		this.affordances.add(new Triple<>(aPepper01, bGive, oBattery));
		this.affordances.add(new Triple<>(aPepper02, bGive, oBattery));
		this.affordances.add(new Triple<>(aPepper01, bPickUp, oSponge));
		this.affordances.add(new Triple<>(aPepper02, bPickUp, oSponge));
		this.affordances.add(new Triple<>(aPepper01, bPickUp, oBattery));
		this.affordances.add(new Triple<>(aPepper02, bPickUp, oBattery));
		// human
		this.affordances.add(new Triple<>(aHuman01, bGive, oSponge));
		this.affordances.add(new Triple<>(aHuman01, bGive, oSpongeY));
		this.affordances.add(new Triple<>(aHuman01, bGive, oSpongeB));
		this.affordances.add(new Triple<>(aHuman01, bGive, oSpongeR));
		this.affordances.add(new Triple<>(aHuman01, bGive, oBattery));
		this.affordances.add(new Triple<>(aHuman01, bPickUp, oSponge));
		this.affordances.add(new Triple<>(aHuman01, bPickUp, oSpongeY));
		this.affordances.add(new Triple<>(aHuman01, bPickUp, oSpongeB));
		this.affordances.add(new Triple<>(aHuman01, bPickUp, oSpongeR));
		this.affordances.add(new Triple<>(aHuman01, bPickUp, oBattery));
		this.affordances.add(new Triple<>(aHuman01, bSpeak, oKeyword1));
		this.affordances.add(new Triple<>(aHuman01, bSpeak, oKeyword2));
		this.affordances.add(new Triple<>(aHuman01, bSpeechRecog, oPin));
		this.affordances.add(new Triple<>(aHuman02, bGive, oSponge));
		this.affordances.add(new Triple<>(aHuman02, bGive, oSpongeY));
		this.affordances.add(new Triple<>(aHuman02, bGive, oSpongeB));
		this.affordances.add(new Triple<>(aHuman02, bGive, oSpongeR));
		this.affordances.add(new Triple<>(aHuman02, bGive, oBattery));
		this.affordances.add(new Triple<>(aHuman02, bPickUp, oSponge));
		this.affordances.add(new Triple<>(aHuman02, bPickUp, oSpongeY));
		this.affordances.add(new Triple<>(aHuman02, bPickUp, oSpongeB));
		this.affordances.add(new Triple<>(aHuman02, bPickUp, oSpongeR));
		this.affordances.add(new Triple<>(aHuman02, bPickUp, oBattery));
		this.affordances.add(new Triple<>(aHuman02, bPickUp, oBattery));
		this.affordances.add(new Triple<>(aHuman02, bSpeak, oKeyword1));
		this.affordances.add(new Triple<>(aHuman02, bSpeak, oKeyword2));
		this.affordances.add(new Triple<>(aHuman02, bSpeechRecog, oPin));
		// mbot11
//		this.affordances.add(new Triple<>(aMbot11, bSpeak, oKeyword1));
//		this.affordances.add(new Triple<>(aMbot11, bSpeak, oKeyword2));
		this.affordances.add(new Triple<>(aMbot11, bSpeak, oPin)); 
		this.affordances.add(new Triple<>(aMbot11, bSpeechRecog, oKeyword1)); 
		this.affordances.add(new Triple<>(aMbot11, bSpeechRecog, oKeyword2));

	}

}


// Affordances from previous experiments:
/*
 * this.affordances.add(new Triple<>(aMbot11, bMoveOnTrajectory, oMap));
 * this.affordances.add(new Triple<>(aMbot03, bMoveInFormation, aMbot11));
 * this.affordances.add(new Triple<>(aPepper01, bDescribe, oLab));
 * this.affordances.add(new Triple<>(aPepper01, bDescribe, oPeisHome));
 * this.affordances.add(new Triple<>(aMbot11, bWayPointNavigation,
 * oPositionsLab)); // this.affordances.add(new Triple<>(aMbot11,
 * bWayPointNavigation, oPositionsLab2)); // this.affordances.add(new
 * Triple<>(aHuman04, bDescribe, oLab)); this.affordances.add(new
 * Triple<>(aHuman01, bMoveInFormation, aMbot11));
 */
//
