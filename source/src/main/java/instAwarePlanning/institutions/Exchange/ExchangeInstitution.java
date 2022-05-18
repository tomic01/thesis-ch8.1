package instAwarePlanning.institutions.Exchange;

import java.util.ArrayList;
import java.util.List;

import instAwarePlanning.framework.Institution;
import instAwarePlanning.framework.Institution.Act;
import instAwarePlanning.framework.Institution.Art;
import instAwarePlanning.framework.Institution.Role;
import instAwarePlanning.framework.utils.Triple;

public class ExchangeInstitution extends Institution {

	public ExchangeInstitution() {
		super();
		
		// Institution Name
		this.setInstName("Exchange");

		// ROLES
		Role Trader1 = new Role("Trader1");
		Role Trader2 = new Role("Trader2");
		
		this.roleSet.put(Trader1.getRole(), Trader1);
		this.setRoleCardinality(Trader1, 1, 1);
		this.roleSet.put(Trader2.getRole(), Trader2);
		this.setRoleCardinality(Trader2, 1, 1);
		
		// ACTS
		Act Gives = new Act("Gives");
		Act Gets = new Act("Gets");
		
		this.actsSet.put(Gives.getAct(), Gives);
		this.actsSet.put(Gets.getAct(), Gets);
		
		// ARTS
		Art Item1 = new Art("Item1");
		Art Item2 = new Art("Item2");
		
		this.artsSet.put(Item1.getArt(), Item1);
		this.artsSet.put(Item2.getArt(), Item2);
		
		// Relations in the institution
		Triple<Role,Act,Art> tTrader1GivesItem1 = new Triple<>(Trader1, Gives, Item1);
		Triple<Role,Act,Art> tTrader1GetsItem2 	= new Triple<>(Trader1, Gets, Item2);
		Triple<Role,Act,Art> tTrader2GivesItem2 = new Triple<>(Trader2, Gives, Item2);
		Triple<Role,Act,Art> tTrader2GetsItem1 	= new Triple<>(Trader2, Gets, Item1);
		
		// OBLIGATION NORMS //
		this.addNormOBN("must", tTrader1GivesItem1);
		this.addNormOBN("must", tTrader1GetsItem2);
		this.addNormOBN("must", tTrader2GivesItem2);
		this.addNormOBN("must", tTrader2GetsItem1);
		
		// MODAL NORMS //
		List<Triple<Role, Act, ?>> lTrader1UseItem1 = new ArrayList<>();
		lTrader1UseItem1.add(tTrader1GivesItem1);
		this.addNormMOD("use", lTrader1UseItem1);

		List<Triple<Role, Act, ?>> lTrader1UseItem2 = new ArrayList<>();
		lTrader1UseItem2.add(tTrader1GetsItem2);
		this.addNormMOD("use", lTrader1UseItem2);
		
		List<Triple<Role, Act, ?>> lTrader2GivesItem2 = new ArrayList<>();
		lTrader2GivesItem2.add(tTrader2GivesItem2);
		this.addNormMOD("use", lTrader2GivesItem2);

		List<Triple<Role, Act, ?>> lTrader2GetsItem1 = new ArrayList<>();
		lTrader2GetsItem1.add(tTrader2GetsItem1);
		this.addNormMOD("use", lTrader2GetsItem1);

		// TEMPORAL NORMS
		List<Triple<Role,Act,?>> lExchange1 = new ArrayList<>();
		lExchange1.add(tTrader1GivesItem1);
		lExchange1.add(tTrader2GetsItem1);
		this.addNormMOD("overlaps", lExchange1);
		
		List<Triple<Role,Act,?>> lExchange2 = new ArrayList<>();
		lExchange2.add(tTrader2GivesItem2);
		lExchange2.add(tTrader1GetsItem2);
		this.addNormMOD("overlaps", lExchange2);
	}

};
