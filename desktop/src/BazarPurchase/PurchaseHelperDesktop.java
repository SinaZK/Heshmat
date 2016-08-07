package BazarPurchase;


import java.util.ArrayList;

import PurchaseIAB.purchaseIAB;

public class PurchaseHelperDesktop implements purchaseIAB.IABInterface
{

	@Override
	public void init() 
	{
	}

	@Override
	public void makePurchase(int id) 
	{
	}


	@Override
	public boolean isSthPurchased() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPurchasedID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void givenPurchaseFlowDone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Integer> getPrices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReadyForPurchase() {
		// TODO Auto-generated method stub
		return false;
	}
	
}//class
