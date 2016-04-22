package PurchaseIAB;

import java.util.ArrayList;


public class purchaseIAB 
{
	public interface IABInterface
	{
		public void init();
		public void makePurchase(int id);
		
		public void givenPurchaseFlowDone();
		public boolean isSthPurchased();
		public int getPuchasedID();
		public ArrayList<Integer> getPrices();
		public boolean isReadyForPurchase();
	}
}
