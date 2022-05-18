package instAwarePlanning.institutions.Trade;

import java.util.ArrayList;
import java.util.List;

import instAwarePlanning.framework.Institution;
import instAwarePlanning.framework.Institution.Act;
import instAwarePlanning.framework.Institution.Art;
import instAwarePlanning.framework.Institution.Role;
import instAwarePlanning.framework.utils.Triple;

public class TradeInstitution extends Institution {

	public TradeInstitution() {
		super();
		
		// Institution Name
		this.setInstName("Trade");

		// ROLES
		Role Buyer = new Role("Buyer");
		Role Seller = new Role("Seller");
		
		this.roleSet.put(Buyer.getRole(), Buyer);
		this.setRoleCardinality(Buyer, 1, 1);
		this.roleSet.put(Seller.getRole(), Seller);
		this.setRoleCardinality(Seller, 1, 1);
		
		// ACTS
		Act Pay = new Act("Pay");
		Act ReceiveGoods = new Act("ReceiveGoods");
		Act ReceivePayment = new Act("ReceivePayment");
		Act GiveGoods = new Act("GiveGoods");
		
		this.actsSet.put(Pay.getAct(), Pay);
		this.actsSet.put(ReceiveGoods.getAct(), ReceiveGoods);
		this.actsSet.put(ReceivePayment.getAct(), ReceivePayment);
		this.actsSet.put(GiveGoods.getAct(), GiveGoods);
		
		// ARTS
		Art PayForm = new Art("PayForm");
		Art Goods = new Art("Goods");
		
		this.artsSet.put(PayForm.getArt(), PayForm);
		this.artsSet.put(Goods.getArt(), Goods);
		
		// Relations in the institution
		Triple<Role,Act,Art> tBuyerPayPayForm = new Triple<>(Buyer, Pay, PayForm);
		Triple<Role,Act,Art> tBuyerReceiveGoods = new Triple<>(Buyer, ReceiveGoods, Goods);
		Triple<Role,Act,Art> tSellerGiveGoods = new Triple<>(Seller, GiveGoods, Goods);
		Triple<Role,Act,Art> tSellerReceivePayment 	= new Triple<>(Seller, ReceivePayment, PayForm);
		
		// OBLIGATION NORMS - MUST//
		this.addNormOBN("must", tBuyerPayPayForm);
		this.addNormOBN("must", tBuyerReceiveGoods);
		this.addNormOBN("must", tSellerGiveGoods);
		this.addNormOBN("must", tSellerReceivePayment);
		
		// USE //
		List<Triple<Role, Act, ?>> lBuyerUsePayForm = new ArrayList<>();
		lBuyerUsePayForm.add(tBuyerPayPayForm);
		this.addNormMOD("use", lBuyerUsePayForm);

		List<Triple<Role, Act, ?>> lBuyerUseGoods = new ArrayList<>();
		lBuyerUseGoods.add(tBuyerReceiveGoods);
		this.addNormMOD("use", lBuyerUseGoods);
		
		List<Triple<Role, Act, ?>> lSellerUseGoods = new ArrayList<>();
		lSellerUseGoods.add(tSellerGiveGoods);
		this.addNormMOD("use", lSellerUseGoods);

		List<Triple<Role, Act, ?>> lSellerUsePayForm = new ArrayList<>();
		lSellerUsePayForm.add(tSellerReceivePayment);
		this.addNormMOD("use", lSellerUsePayForm);

		// TEMPORAL NORMS - BEFORE
		List<Triple<Role,Act,?>> lExchange1 = new ArrayList<>();
		lExchange1.add(tBuyerPayPayForm);
		lExchange1.add(tBuyerReceiveGoods);
		this.addNormMOD("before", lExchange1);
	}

};
